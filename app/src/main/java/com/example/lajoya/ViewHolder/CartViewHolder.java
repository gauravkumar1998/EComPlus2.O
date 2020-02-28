package com.example.lajoya.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lajoya.Interface.ItemClickListener;
import com.example.lajoya.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName,txtProductPrice,txtProductQuantity;
    public ImageView imageView1;
    private ItemClickListener itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName= itemView.findViewById(R.id.cart_product_name);
        txtProductPrice= itemView.findViewById(R.id.cart_product_Price);
        txtProductQuantity= itemView.findViewById(R.id.cart_product_quantity);
        imageView1 = (ImageView) itemView.findViewById(R.id.product_image_cart_details);
    }


    public void setItemClickListner(ItemClickListener itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view,getAdapterPosition(),false);
    }
}
