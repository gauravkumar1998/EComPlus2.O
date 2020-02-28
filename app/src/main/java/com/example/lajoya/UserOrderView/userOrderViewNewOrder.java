package com.example.lajoya.UserOrderView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lajoya.AdminUserProductsActivity;
import com.example.lajoya.Model.UserOrders;
import com.example.lajoya.Prevalent.Prevalent;
import com.example.lajoya.R;
import com.example.lajoya.ViewHolder.UserOrdersViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userOrderViewNewOrder extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef,UserordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);

        ordersList = findViewById(R.id.orders_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void onStart(){
        super.onStart();
        ordersRef = FirebaseDatabase.getInstance().getReference().child("order details");
        UserordersRef = FirebaseDatabase.getInstance().getReference().child("User Order details");




        FirebaseRecyclerOptions<UserOrders> options =
                new FirebaseRecyclerOptions.Builder<UserOrders>()
                        .setQuery(UserordersRef.child(Prevalent.currentOnlineUser.getPhone()).orderByChild("state").equalTo("New Order"), UserOrders.class)
                        .build();


// new orders page data holder starts here
        FirebaseRecyclerAdapter<UserOrders, UserOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<UserOrders, UserOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final UserOrdersViewHolder holder, final int position, @NonNull final UserOrders model) {

                        holder.Total.setText("Total Amount =  $" + model.getTotalAmount());
                        holder.DateTime.setText("Order at: " + model.getDate() + "  " + model.getTime());
                        holder.status.setText(model.getState());
                        holder.orderId.setText("Order ID: " + model.getoId());



                        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ordersRef.child(model.getoId()).child("state").setValue("Cancelled");
                                UserordersRef.child(Prevalent.currentOnlineUser.getPhone()).child(model.getoId()).child("state").setValue("Cancelled");
                            }
                        });






                        //   holder.radio.setVisibility(View.INVISIBLE);
                        // holder.btnStatus.setVisibility(View.INVISIBLE);


                        holder.btnShow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String oID = getRef(position).getKey();
                                String uID = model.getUserPhone();

                                Intent intent = new Intent(userOrderViewNewOrder.this, AdminUserProductsActivity.class);
                                intent.putExtra("oid", oID);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });





                    }

                    @NonNull
                    @Override
                    public UserOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_order_layout, parent, false);
                        return new UserOrdersViewHolder(view);
                    }
                };

        // new orders page data holder ends here
        ordersList.setAdapter(adapter);
        adapter.startListening();



    }
}
