package com.example.flowerapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.flowerapp.Entity.Flower;
import com.example.flowerapp.Entity.ItemsGiohang;
import com.example.flowerapp.Entity.Order;
import com.example.flowerapp.R;

import java.util.List;

public class ItemsOrderAdapter extends ArrayAdapter {
    private Activity context;
    private List<ItemsGiohang> itemsGiohangs;
    public ItemsOrderAdapter(@NonNull Activity context, List<ItemsGiohang> itemsGiohangs) {
        super(context, R.layout.activity_list_item_product_of_order, itemsGiohangs);
        this.context = context;
        this.itemsGiohangs = itemsGiohangs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListItem = inflater.inflate(R.layout.activity_list_item_product_of_order,null,true);

        TextView quantityOrder = ListItem.findViewById(R.id.quantityOrder);
        TextView nameProduct = ListItem.findViewById(R.id.nameProduct);
        ImageView imgProduct = ListItem.findViewById(R.id.imgProduct);
        TextView sumOrder = ListItem.findViewById(R.id.sumOrder);
        TextView idFlower = ListItem.findViewById(R.id.idFlower);

        ItemsGiohang itemsGiohang = itemsGiohangs.get(position);

        nameProduct.setText(itemsGiohang.getNameflower());
        quantityOrder.setText("x"+String.valueOf(itemsGiohang.getSoluongmuahang()));
        sumOrder.setText(String.valueOf(itemsGiohang.getPrice()));
        idFlower.setText(itemsGiohang.getId_flower());
        Glide.with(context).load(itemsGiohang.getImgFlower()).into(imgProduct );

        return ListItem;

    }
}
