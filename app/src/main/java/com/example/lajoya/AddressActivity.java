package com.example.lajoya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lajoya.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class AddressActivity extends AppCompatActivity {
    private EditText textview1, textview2, textview3, textview4, textview5, textview6;
    private TextView txtview1,txtview2, txtview3;
    public String address1, address2, address3, address4, address5, address6, addressa, addressb, addressc,addressk, addressj, addressl, tPrice, cartID;
    private CheckBox chkBox1, chkBox2;
    private Button button3;
    private int total;
    public static final Pattern email= Pattern.compile("^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$");
    public static final Pattern pno=Pattern.compile("^[1-9]{2}[0-9]{3,14}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);


        textview1 = (EditText) findViewById(R.id.editText2);
        textview2 = (EditText) findViewById(R.id.editText3);
        textview3 = (EditText) findViewById(R.id.editText4);
        textview4 = (EditText) findViewById(R.id.editText5);
        textview5 = (EditText) findViewById(R.id.editText6);
        textview6 = (EditText) findViewById(R.id.editText);

        txtview1= (TextView) findViewById(R.id.txtView1);
        txtview2= (TextView) findViewById(R.id.txtView2);
        txtview3= (TextView) findViewById(R.id.txtView3);

        cartID = getIntent().getStringExtra("cartID");
        total=getIntent().getIntExtra("cTotal",0);


        button3= (Button) findViewById(R.id.next_process_btn);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(addressa=="true")
                {
                    String ph= textview6.getText().toString();
                    String em=textview5.getText().toString();
                    if (TextUtils.isEmpty(textview1.getText().toString()))
                    {
                        Toast.makeText(AddressActivity.this, "Please provide your full name.", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(textview2.getText().toString()))
                    {
                        Toast.makeText(AddressActivity.this, "Please provide your address.", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(textview3.getText().toString()))
                    {
                        Toast.makeText(AddressActivity.this, "Please provide your address line 2.", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(textview4.getText().toString()))
                    {
                        Toast.makeText(AddressActivity.this, "Please provide your zip code.", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(textview5.getText().toString()))
                    {
                        Toast.makeText(AddressActivity.this, "Please provide your email.", Toast.LENGTH_SHORT).show();
                    }
                    else if(!email.matcher(em).matches()){
                        Toast.makeText(AddressActivity.this, "Please Enter Valid Email.", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(textview6.getText().toString()))
                    {
                        Toast.makeText(AddressActivity.this, "Please provide your phone number.", Toast.LENGTH_SHORT).show();
                    }
                    else if(!pno.matcher(ph).matches()){
                        Toast.makeText(AddressActivity.this, "Please Enter Valid Phone Number.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        address1 = textview1.getText().toString();
                        address2 = textview2.getText().toString();
                        address3 = textview3.getText().toString();
                        address4 = textview4.getText().toString();
                        address5 = textview5.getText().toString();
                        address6 = textview6.getText().toString();



                        Intent intent = new Intent(AddressActivity.this, ActivityPayment.class);
                        intent.putExtra("address1", address1);
                        intent.putExtra("address2", address2);
                        intent.putExtra("address3", address3);
                        intent.putExtra("cartTotal",total);
                        intent.putExtra("cartId",cartID);

                /*Bundle extras = new Bundle();
                extras.putString("address1", address1);
                extras.putString("address2", address2);
                extras.putString("address3", address3);
                intent.putExtras(extras);*/

                        startActivity(intent);
                    }
                }
                else {
                    if (TextUtils.isEmpty(txtview1.getText().toString())) {
                        Toast.makeText(AddressActivity.this, "Name is Empty.", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(txtview2.getText().toString())) {
                        Toast.makeText(AddressActivity.this, "Phone Number is Empty.", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(txtview1.getText().toString())) {
                        Toast.makeText(AddressActivity.this, "Address is Empty.", Toast.LENGTH_SHORT).show();
                    } else {
                        address1 = addressk;
                        address2 = addressj;
                        address3 = addressl;

                        Intent intent = new Intent(AddressActivity.this, ActivityPayment.class);
                        intent.putExtra("address1", address1);
                        intent.putExtra("address2", address2);
                        intent.putExtra("address3", address3);
                        intent.putExtra("cartTotal", total);

                /*Bundle extras = new Bundle();
                extras.putString("address1", address1);
                extras.putString("address2", address2);
                extras.putString("address3", address3);
                intent.putExtras(extras);*/

                        startActivity(intent);
                    }

                }


            }
        });

        userInfoDisplay(txtview1,txtview2, txtview3);


        textview1.setVisibility(View.INVISIBLE);
        textview2.setVisibility(View.INVISIBLE);
        textview3.setVisibility(View.INVISIBLE);
        textview4.setVisibility(View.INVISIBLE);
        textview5.setVisibility(View.INVISIBLE);
        textview6.setVisibility(View.INVISIBLE);

        chkBox1 = (CheckBox) findViewById(R.id.checkBox);
        chkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(chkBox1.isChecked()){

                    textview1.setVisibility(View.VISIBLE);
                    textview2.setVisibility(View.VISIBLE);
                    textview3.setVisibility(View.VISIBLE);
                    textview4.setVisibility(View.VISIBLE);
                    textview5.setVisibility(View.VISIBLE);
                    textview6.setVisibility(View.VISIBLE);


                    txtview1.setVisibility(View.INVISIBLE);
                    txtview2.setVisibility(View.INVISIBLE);
                    txtview3.setVisibility(View.INVISIBLE);



                    addressa="true";

                }
                else{

                    textview1.setVisibility(View.INVISIBLE);
                    textview2.setVisibility(View.INVISIBLE);
                    textview3.setVisibility(View.INVISIBLE);
                    textview4.setVisibility(View.INVISIBLE);
                    textview5.setVisibility(View.INVISIBLE);
                    textview6.setVisibility(View.INVISIBLE);


                    txtview1.setVisibility(View.VISIBLE);
                    txtview2.setVisibility(View.VISIBLE);
                    txtview3.setVisibility(View.VISIBLE);


                    addressa="false";

                }

            }});

    }

    private void userInfoDisplay( final TextView fullNameEditText, final TextView userPhoneEditText, final TextView addressEditText) {

        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if (dataSnapshot.child("name").exists()&&dataSnapshot.child("phoneOrder").exists() && dataSnapshot.child("address").exists())


                    {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String phone = dataSnapshot.child("phoneOrder").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        fullNameEditText.setText(name);


                        userPhoneEditText.setText(phone);
                        addressEditText.setText(address);

                        addressk=name;
                        addressj=phone;
                        addressl=address;

                    }
                    else{

                        fullNameEditText.setVisibility(View.INVISIBLE);


                        userPhoneEditText.setVisibility(View.INVISIBLE);
                        addressEditText.setVisibility(View.INVISIBLE);

                        chkBox1.setChecked(true);
                        textview1.setVisibility(View.VISIBLE);
                        textview2.setVisibility(View.VISIBLE);
                        textview3.setVisibility(View.VISIBLE);
                        textview4.setVisibility(View.VISIBLE);
                        textview5.setVisibility(View.VISIBLE);
                        textview6.setVisibility(View.VISIBLE);

                    }
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {




            }
        });
    }

    private void Check()
    {
        if (TextUtils.isEmpty(textview1.getText().toString()))
        {
            Toast.makeText(this, "Please provide your full name.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(textview2.getText().toString()))
        {
            Toast.makeText(this, "Please provide your address.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(textview3.getText().toString()))
        {
            Toast.makeText(this, "Please provide your address line 2.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(textview4.getText().toString()))
        {
            Toast.makeText(this, "Please provide your zip code.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(textview5.getText().toString()))
        {
            Toast.makeText(this, "Please provide your email.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(textview6.getText().toString()))
        {
            Toast.makeText(this, "Please provide your phone number.", Toast.LENGTH_SHORT).show();
        }
        else
        {

        }
    }

}