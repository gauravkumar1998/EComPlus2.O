package com.example.lajoya.AdminOrderView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lajoya.AdminUserProductsActivity;
import com.example.lajoya.Model.AdminOrders;
import com.example.lajoya.R;
import com.example.lajoya.ViewHolder.AdminOrdersViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminOrderViewCancelled  extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef;
    private TextView orderViewHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_view);

        orderViewHead= findViewById(R.id.orderViewHead);

        orderViewHead.setText("Cancelled Orders");

        ordersList = findViewById(R.id.orders_list);




        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart(){
        super.onStart();
        ordersRef = FirebaseDatabase.getInstance().getReference().child("order details");



        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(ordersRef.orderByChild("state").equalTo("Cancelled"), AdminOrders.class)
                        .build();


// new orders page data holder starts here
        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model) {
                        holder.userName.setText("Name: " + model.getName());
                        holder.userPhoneNumber.setText("Phone: " + model.getPhone());
                        holder.userTotalPrice.setText("Total Amount =  $" + model.getTotalAmount());
                        holder.userDateTime.setText("Order at: " + model.getDate() + "  " + model.getTime());
                        holder.userShippingAddress.setText("Shipping Address: " + model.getAddress());
                        holder.state.setText(model.getState());
                        holder.orderId.setText("Order ID: " + model.getoId());



                        holder.radio.setVisibility(View.INVISIBLE);
                        holder.btnStatus.setVisibility(View.INVISIBLE);


                        holder.ShowOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String oID = getRef(position).getKey();
                                String uID = model.getUserPhone();

                                Intent intent = new Intent(AdminOrderViewCancelled.this, AdminUserProductsActivity.class);
                                intent.putExtra("oid", oID);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new AdminOrdersViewHolder(view);
                    }
                };

        // new orders page data holder ends here
        ordersList.setAdapter(adapter);
        adapter.startListening();



    }



}
