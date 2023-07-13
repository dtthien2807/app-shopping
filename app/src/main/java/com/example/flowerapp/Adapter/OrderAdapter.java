package com.example.flowerapp.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flowerapp.DetailOrderActivity;
import com.example.flowerapp.Entity.Order;
import com.example.flowerapp.ProductAdminActivity;
import com.example.flowerapp.R;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends ArrayAdapter {
    private Activity context;
    private List<Order> orderList;
    int lastPosition = -1;
    String[][] allItems = {};
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
        LinearLayout btnDetailOrder = ListItem.findViewById(R.id.btnDetailOrder);
        TextView btnMore = ListItem.findViewById(R.id.btnMore);
        TextView status = ListItem.findViewById(R.id.statusOrder);
        ListView lstProduct = ListItem.findViewById(R.id.lstProduct);

        Order order = orderList.get(position);

        ItemsOrderAdapter itemsOrderAdapter= new ItemsOrderAdapter(context, order.getItems());
        lstProduct.setAdapter(itemsOrderAdapter);

        int statusOrder = order.getStatus();
        txtId.setText(order.getId_order());
        nameUser.setText("Khách hàng: " +order.getName_user());
        phoneUser.setText("Số điện thoại: " +order.getNumber_phone());
        addressUser.setText("Địa chỉ: " +order.getAddress_user());
        dateOrder.setText("Ngày đặt giao: " +order.getOrder_ship_date());
        dateShip.setText("Ngày thực tế: " +order.getShip_date());
        createdAt.setText(order.getCreate_at());

        switch (statusOrder){
            case 0:
            {
                status.setText("Chờ xét duyệt");
                status.setBackgroundColor(0x56E81C0A);
                status.setTextColor(Color.parseColor("#5A182B"));
                break;
            }
            case 1:
            {
                status.setText("Chờ giao hàng");
                status.setBackgroundColor(0x56ACACA8);
                status.setTextColor(Color.parseColor("#757574"));
                break;
            }
            case 2:
            {
                status.setText("Đang giao hàng");
                status.setBackgroundColor(0x787C05);
                status.setTextColor(Color.parseColor("#787C05"));
                break;
            }
            case 3:
            {
                status.setText("Giao hàng thành công");
                status.setBackgroundColor(0x564CAF50);
                status.setTextColor(Color.parseColor("#185A1B"));
                break;
            }
            case 4:
            {
                status.setText("Giao hàng không thành công");
                status.setBackgroundColor(0xF80E0E0E);
                status.setTextColor(Color.parseColor("#F1F4F1"));
                break;
            }
        }
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailOrderActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id_order",order.getId_order());
                context.startActivity(intent);
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.slide_left);
        ListItem.startAnimation(animation);

        return  ListItem;
    }
}
