package com.example.lajoya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lajoya.Model.Cart;
import com.example.lajoya.Prevalent.Prevalent;
import com.example.lajoya.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity {


    private TextView AddMore;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount;
    private ImageView pImageCart;


    private int overTotalPrice=0;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView=findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);



        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        AddMore= findViewById(R.id.addMore);
        NextProcessBtn = (Button) findViewById(R.id.next_process_btn);
        txtTotalAmount = (TextView) findViewById(R.id.total_price);

        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(overTotalPrice!=0){



                    Intent intent= new Intent(CartActivity.this, AddressActivity.class);
                    intent.putExtra("cTotal",overTotalPrice);
                    startActivity(intent);
                    overTotalPrice=0;
                }
                else {
                    Toast.makeText(CartActivity.this, "Cart is empty, order can't be placed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent= new Intent(CartActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });







    }

    @Override
    protected void onStart() {
        super.onStart();


        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Products"),Cart.class)
                        .build();



        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {
                holder.txtProductQuantity.setText("Quantity: " +model.getQuantity());
                holder.txtProductPrice.setText("Price : $" + model.getPrice());
                holder.txtProductName.setText(model.getPname());
                Picasso.get().load(model.getImage()).into(holder.imageView1);

                int oneProductPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());

                overTotalPrice= overTotalPrice+oneProductPrice;
                txtTotalAmount.setText("Total Price: $" + String.valueOf(overTotalPrice));



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[]= new CharSequence[]
                                {

                                        "Add More",
                                        "Delete"
                                };
                        AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                                if(i==0){
                                    Intent intent= new Intent(CartActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);
                                }

                                if(i==1){

                                    //   change for badge starts here




                                    final DatabaseReference cartCountRef;
                                    cartCountRef = FirebaseDatabase.getInstance().getReference().child("Cart Quantity").child(Prevalent.currentOnlineUser.getPhone()).child("quantity");


                                    cartCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            String quantity= model.getQuantity();

                                            int QuaInt= Integer.parseInt(quantity);

                                            if(dataSnapshot.exists()){

                                                int quant= Integer.parseInt(dataSnapshot.getValue().toString());
                                                int quantFinal= quant - QuaInt;
                                                cartCountRef.setValue(quantFinal);
                                            }
                                            else{

                                                cartCountRef.setValue(0);


                                            }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });




                                    //change for badge ends here

                                    cartListRef.child("User View")
                                            .child(Prevalent.currentOnlineUser.getPhone())
                                            .child("Products")
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(CartActivity.this, "Item Removed ", Toast.LENGTH_SHORT).show();




                                                    Intent intent= new Intent(CartActivity.this, HomeActivity.class);






                                                    startActivity(intent);
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();
                    }
                });


            }


            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder= new CartViewHolder(view);
                return holder;
            }
        };



        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
