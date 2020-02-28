package com.example.lajoya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lajoya.UserOrderView.UserOrderActivity;
import com.example.lajoya.UserOrderView.userOrderViewCancelled;
import com.example.lajoya.UserOrderView.userOrderViewInTransit;
import com.example.lajoya.UserOrderView.userOrderViewNewOrder;

public class userOrderCategoryActivity extends AppCompatActivity {

    private Button Completed,NewOrder,inTransit,Cancelled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_category);

        Completed= findViewById(R.id.completed);
        NewOrder= findViewById(R.id.newOrder);
        Cancelled= findViewById(R.id.cancelled);
        inTransit= findViewById(R.id.inTransit);


        Completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(userOrderCategoryActivity.this, UserOrderActivity.class);

                startActivity(intent);
            }
        });

        NewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(userOrderCategoryActivity.this, userOrderViewNewOrder.class);

                startActivity(intent);
            }
        });
        Cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(userOrderCategoryActivity.this, userOrderViewCancelled.class);

                startActivity(intent);
            }
        });

        inTransit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(userOrderCategoryActivity.this, userOrderViewInTransit.class);

                startActivity(intent);
            }
        });
    }
}
