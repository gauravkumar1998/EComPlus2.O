package com.example.lajoya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.lajoya.Model.Products;
import com.example.lajoya.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button addToCartButton;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice,productDescription,productName;
    private String productID = "";
    String proImage;
    int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");

        addToCartButton= (Button) findViewById(R.id.pd_add_to_cart_button);
        numberButton= (ElegantNumberButton) findViewById(R.id.elegant);
        productImage=(ImageView) findViewById(R.id.product_image_details);
        productName=(TextView) findViewById(R.id.product_name_details);
        productDescription=(TextView) findViewById(R.id.product_description_details);
        productPrice=(TextView) findViewById(R.id.product_price_details);

        getProductDetails(productID);

        getProductImage(productID);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();
            }
        });
    }
    private void addingToCartList() {





//   change for badge starts here




        final DatabaseReference cartCountRef;
        cartCountRef = FirebaseDatabase.getInstance().getReference().child("Cart Quantity").child(Prevalent.currentOnlineUser.getPhone()).child("quantity");


        cartCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                final String quantity= (numberButton.getNumber());
                final int QuaInt= Integer.parseInt(quantity);


                if(dataSnapshot.exists()){

                    int quant= Integer.parseInt(dataSnapshot.getValue().toString());
                    int quantFinal= QuaInt + quant;
                    cartCountRef.setValue(quantFinal);

                    String quantfinal= Integer.toString(quantFinal);
                    Log.d("finalvalues",quantfinal);
                }
                else{

                    cartCountRef.setValue(QuaInt);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        //change for badge ends here








        final String saveCurrentTime, saveCurrentDate;

        Calendar calForDate= Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate =currentDate.format(calForDate.getTime());



        SimpleDateFormat currentTime = new SimpleDateFormat("HH : mm : ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");


//--------------------------------------------------------------------------------->
        final DatabaseReference cartListRef2 = FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Products").child(productID).child("quantity");

        cartListRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()){

                    final String quantity1= (numberButton.getNumber());
                    final int qua = Integer.valueOf(quantity1);
                    String qua1 = dataSnapshot.getValue().toString();
                    int qua2 = Integer.valueOf(qua1);
                    int finalQua= qua + qua2;
                    final String quantity2= Integer.toString(finalQua);;
                    //cartListRef2.setValue(quantity2);

                    cartListRef2
                            .setValue(quantity2)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if (task2.isSuccessful()) {

                                        Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                    }
                                    });
                }


                else{


//------------------------------------------------------------------------------









//-------------------------------------------------------------------------------

                    final HashMap<String, Object> cartMap = new HashMap<>();

                    cartMap.put("pid", productID);
                    cartMap.put("pname", productName.getText().toString());
                    cartMap.put("price", productPrice.getText().toString());
                    cartMap.put("image",proImage);
                    cartMap.put("date", saveCurrentDate);
                    cartMap.put("time", saveCurrentTime);
                    cartMap.put("quantity", numberButton.getNumber());
                    cartMap.put("discount", "");
                    Log.d("its", "hiii"+proImage);

                    try {

                        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Products").child(productID)
                                .updateChildren(cartMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Products").child(productID)
                                                    .updateChildren(cartMap)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {










                                                                Toast.makeText(ProductDetailsActivity.this, "Added To Cart List", Toast.LENGTH_SHORT).show();


                                                                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }


                                    }
                                });
                    }
                    catch (Exception e){

                        Intent intent = new Intent(ProductDetailsActivity.this, LoginRequestActivity.class);
                        startActivity(intent);

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//--------------------------------------------------------------------------------------------->










        Log.d("jibin", quantity+"");




    }

    private void getProductDetails(String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {


            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products= dataSnapshot.getValue(Products.class);




                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private String getProductImage(String productID) {
        DatabaseReference ImgRef = FirebaseDatabase.getInstance().getReference().child("Products");

        ImgRef.child(productID).child("image").addValueEventListener(new ValueEventListener() {


            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    proImage= dataSnapshot.getValue(String.class);

                    Log.d("its","hiii"+proImage);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return proImage;
    };
}
