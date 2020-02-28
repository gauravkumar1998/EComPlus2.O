package com.example.lajoya.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lajoya.R;

public class AdminOrdersViewHolder extends RecyclerView.ViewHolder
{

    public TextView userName, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress,orderId, state;
    public RadioButton radioNew,radioInTransit,radioCompleted;
    public RadioGroup radio;
    public Button ShowOrdersBtn,btnStatus;

    public AdminOrdersViewHolder(View itemView)
    {
        super(itemView);

        userName = itemView.findViewById(R.id.order_user_name);
        userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
        userTotalPrice = itemView.findViewById(R.id.order_total_price);
        userDateTime = itemView.findViewById(R.id.order_date_time);
        userShippingAddress = itemView.findViewById(R.id.order_address_city);
        ShowOrdersBtn = itemView.findViewById(R.id.show_all_products_btn);
        orderId= itemView.findViewById(R.id.orderId);
        state= itemView.findViewById(R.id.status);
        radioNew= itemView.findViewById(R.id.newOrder);
        radioInTransit= itemView.findViewById(R.id.inTransit);
        radioCompleted= itemView.findViewById(R.id.Completed);
        radio=itemView.findViewById(R.id.radio);
        btnStatus=itemView.findViewById(R.id.btnStatus);
    }
}
