package com.example.lajoya;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lajoya.Model.Cart;
import com.example.lajoya.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminUserProductsActivity extends AppCompatActivity {

    private RecyclerView productsList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;
    private String userID, oID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);

        userID = getIntent().getStringExtra("uid");
        oID = getIntent().getStringExtra("oid");

        productsList = findViewById(R.id.products_list);
        productsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productsList.setLayoutManager(layoutManager);


    }


    @Override
    protected void onStart() {
        super.onStart();


    cartListRef = FirebaseDatabase.getInstance().getReference()
            .child("Cart List").child("Admin View").child(userID).child("Products").child(oID);

    FirebaseRecyclerOptions<Cart> options =
            new FirebaseRecyclerOptions.Builder<Cart>()
                    .setQuery(cartListRef, Cart.class)
                    .build();
    FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {

        @Override
        protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
            holder.txtProductQuantity.setText("Quantity = " + model.getQuantity());
            holder.txtProductPrice.setText("Price " + model.getPrice() + "$");
            holder.txtProductName.setText(model.getPname());
            Picasso.get().load(model.getImage()).into(holder.imageView1);

        }

        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
            CartViewHolder holder = new CartViewHolder(view);
            return holder;
        }
    };
    productsList.setAdapter(adapter);
    adapter.startListening();
}
    }


