package com.example.flowerapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flowerapp.Entity.Order;
import com.example.flowerapp.R;

import java.util.List;

public class OrderAdapter extends ArrayAdapter {
    private Activity context;
    private List<Order> orderList;
    public OrderAdapter(@NonNull Activity context, List<Order> orderList) {
        super(context, R.layout.activity_listview_order, orderList);
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View ListItem = inflater.inflate(R.layout.activity_listview_order,null,true);
        TextView txtId = ListItem.findViewById(R.id.orderID);
        TextView nameUser = ListItem.findViewById(R.id.nameUser);
        TextView phoneUser = ListItem.findViewById(R.id.phoneUser);
        TextView addressUser = ListItem.findViewById(R.id.addressUser);
        TextView dateOrder = ListItem.findViewById(R.id.dateOrder);
        TextView dateShip = ListItem.findViewById(R.id.dateShip);
        TextView createdAt = ListItem.findViewById(R.id.createdAt);

        Order order = orderList.get(position);

        txtId.setText(order.getId_order());
        nameUser.setText(order.getName_user());
        phoneUser.setText(order.getNumber_phone());
        addressUser.setText(order.getAddress_user());
        dateOrder.setText(order.getOrder_ship_date());
        dateShip.setText(order.getShip_date());
        createdAt.setText(order.getCreate_at());

        return  ListItem;
    }
}
