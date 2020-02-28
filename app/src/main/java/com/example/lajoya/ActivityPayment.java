package com.example.lajoya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lajoya.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ActivityPayment extends AppCompatActivity {
    private Button button2;
    EditText e1,e2,e3,e4;
    private String address1,address2, address3, exacttime, cartID;
    private TextView tview, tview2, tview3;
    public String key, userPhone;
    int flag,total;
    public static final Pattern CARDNO
                =Pattern.compile("^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
                                "(?<mastercard>5[1-5][0-9]{14})|" +
                                "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
                                "(?<amex>3[47][0-9]{13})|" +
                                "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
                                "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$");
    public static final Pattern CVV=Pattern.compile("^([0-9]{3})$");
    public static final Pattern CDate=Pattern.compile("^0[1-9][0-9]{2}|^(11)[0-9]{2}|^(12)[0-9]{2}$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment1);

        button2= (Button) findViewById(R.id.button2);
        address1= getIntent().getStringExtra("address1");
        address2= getIntent().getStringExtra("address2");
        address3= getIntent().getStringExtra("address3");
        total=getIntent().getIntExtra("cartTotal",0);

        tview= (TextView) findViewById(R.id.textView);
        tview2= (TextView) findViewById(R.id.textView2);
        tview3= (TextView) findViewById(R.id.textView3);

        tview.setText(address1);
        tview2.setText(address2);
        tview3.setText(address3);
        tview.setVisibility(View.INVISIBLE);
        tview2.setVisibility(View.INVISIBLE);
        tview3.setVisibility(View.INVISIBLE);

        e1= findViewById(R.id.editText8);
        e2= findViewById(R.id.editText9);
        e3= findViewById(R.id.editText11);
        e4= findViewById(R.id.editText7);

        cartID = getIntent().getStringExtra("cartID");

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String card= e1.getText().toString().trim();
                String cv= e2.getText().toString().trim();
                String date= e3.getText().toString().trim();
                String cname= e4.getText().toString().trim();

                if (TextUtils.isEmpty(card))
                {
                    Toast.makeText(ActivityPayment.this, "Please provide your Card details.", Toast.LENGTH_SHORT).show();
                }
                else if(!CARDNO.matcher(card).matches()){
                    Toast.makeText(ActivityPayment.this, "Please Enter Valid Card Number.", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(cv))
                {
                    Toast.makeText(ActivityPayment.this, "Please provide your CVV.", Toast.LENGTH_SHORT).show();
                }
                else if(!CVV.matcher(cv).matches()){
                    Toast.makeText(ActivityPayment.this, "Please Enter Valid CVV.", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(date))
                {
                    Toast.makeText(ActivityPayment.this, "Please provide Date.", Toast.LENGTH_SHORT).show();
                }
                else if(!CDate.matcher(date).matches()){
                    Toast.makeText(ActivityPayment.this, "Please Enter Valid Date.", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(cname))
                {
                    Toast.makeText(ActivityPayment.this, "Please Provide Your Name on Card.", Toast.LENGTH_SHORT).show();
                }
                else {
                    final String saveCurrentDate, saveCurrentTime;

                    Calendar calForDate = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                    saveCurrentDate = currentDate.format(calForDate.getTime());

                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                    saveCurrentTime = currentTime.format(calForDate.getTime());
                    exacttime = saveCurrentDate.toString() + ":" + saveCurrentTime.toString();
                    cartID = getIntent().getStringExtra("cartID");
                    final DatabaseReference newRef = FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("Admin View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .child("Products")
                            .push();


                    key = newRef.getKey();


                    final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                            .child("order details");
                    final DatabaseReference userOrdersRef = FirebaseDatabase.getInstance().getReference()
                            .child("User Order details");

                    userPhone = Prevalent.currentOnlineUser.getPhone();


                    final DatabaseReference oldRef = FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone());


                    HashMap<String, Object> ordersMap = new HashMap<>();
                    ordersMap.put("totalAmount", total);
                    ordersMap.put("oId", key);
                    ordersMap.put("UserPhone", userPhone);
                    ordersMap.put("name", address1);
                    ordersMap.put("phone", address2);
                    ordersMap.put("address", address3);
                    ordersMap.put("date", saveCurrentDate);
                    ordersMap.put("time", saveCurrentTime);
                    ordersMap.put("state", "New Order");


                    //-----------------------------------------------------------------------


                    oldRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            Log.d("jibin", dataSnapshot.getValue() + "");

                            Log.d("jibin", key + "");


                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


                            if (flag == 1) {
                                return;

                            }
                            flag = 1;


                            newRef.setValue(dataSnapshot.getValue());


                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
//----------------------------------------------------------
                    key = newRef.getKey();
                    ordersRef.child(key).updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("Cart List")
                                        .child("User View")
                                        .child(Prevalent.currentOnlineUser.getPhone())
                                        .removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ActivityPayment.this, "your final order has been placed successfully.", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(ActivityPayment.this, HomeActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();

                                                    FirebaseDatabase.getInstance().getReference()
                                                            .child("Cart Quantity")
                                                            .child(Prevalent.currentOnlineUser.getPhone())
                                                            .removeValue();
                                                }
                                            }
                                        });
                            }
                        }
                    });

                    userOrdersRef.child(Prevalent.currentOnlineUser.getPhone()).child(key).updateChildren(ordersMap);


                    Toast.makeText(ActivityPayment.this, "Order Confirmed", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }





}
