package com.example.lajoya.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lajoya.R;

public class UserOrdersViewHolder extends RecyclerView.ViewHolder {

    public TextView status,orderId,DateTime,Total;
    public Button btnCancel,btnShow;
    public UserOrdersViewHolder(@NonNull View itemView) {
        super(itemView);

        status = itemView.findViewById(R.id.status);
        orderId = itemView.findViewById(R.id.orderid);
        Total = itemView.findViewById(R.id.total);
        DateTime= itemView.findViewById(R.id.datetime);
        btnCancel= itemView.findViewById(R.id.btncancel);
        btnShow = itemView.findViewById(R.id.btnshow_order_products);
    }


}
