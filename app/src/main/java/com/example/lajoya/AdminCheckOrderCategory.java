package com.example.lajoya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lajoya.AdminOrderView.AdminOrderViewCancelled;
import com.example.lajoya.AdminOrderView.AdminOrderViewCompleted;
import com.example.lajoya.AdminOrderView.AdminOrderViewInTransit;
import com.example.lajoya.AdminOrderView.AdminOrderViewNewOrder;

public class AdminCheckOrderCategory extends AppCompatActivity {

    Button AllOrders, InTransit,Cancelled,Completed,NewOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_order_category);



        InTransit=findViewById(R.id.InTransit);
        Cancelled=findViewById(R.id.cancelled);
        Completed=findViewById(R.id.completed);
        NewOrder=findViewById(R.id.newOrder);




        InTransit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCheckOrderCategory.this, AdminOrderViewInTransit.class);

                startActivity(intent);
            }
        });

        Cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCheckOrderCategory.this, AdminOrderViewCancelled.class);

                startActivity(intent);
            }
        });

        Completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCheckOrderCategory.this, AdminOrderViewCompleted.class);

                startActivity(intent);
            }
        });

        NewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCheckOrderCategory.this, AdminOrderViewNewOrder.class);

                startActivity(intent);
            }
        });

    }
}
