package com.example.flowerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.flowerapp.Adapter.AdminCategoryAdapter;
import com.example.flowerapp.Adapter.OrderAdapter;
import com.example.flowerapp.Entity.Category;
import com.example.flowerapp.Entity.Feedback;
import com.example.flowerapp.Entity.Flower;
import com.example.flowerapp.Entity.ItemsGiohang;
import com.example.flowerapp.Entity.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderAdminActivity extends AppCompatActivity {

    DatabaseReference databaseorder;
    ListView orderListView;
    List<Order> orderList;
    ImageView home_ad, classify, goods, oder, user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_admin);
        orderListView = findViewById(R.id.lstOrder);
        orderList = new ArrayList<>();
        databaseorder = FirebaseDatabase.getInstance().getReference("Order");

        loadMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseorder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                if(snapshot.exists()) {
                    for (DataSnapshot ordersnapshot : snapshot.getChildren()) {
//                        Order order = ordersnapshot.getValue(Order.class);
                        String id_order = ordersnapshot.child("id_order").getValue(String.class);
                        String name_user = ordersnapshot.child("name_user").getValue(String.class);
                        String number_phone = ordersnapshot.child("number_phone").getValue(String.class);
                        String note = ordersnapshot.child("note").getValue(String.class);
                        String id_user = ordersnapshot.child("id_user").getValue(String.class);
                        String address_user = ordersnapshot.child("address_user").getValue(String.class);
                        String order_ship_date = ordersnapshot.child("order_ship_date").getValue(String.class);
                        String ship_date = ordersnapshot.child("ship_date").getValue(String.class);
                        Float total_bill = ordersnapshot.child("total_bill").getValue(Float.class);
                        Integer status = ordersnapshot.child("status").getValue(Integer.class);
                        String create_at = ordersnapshot.child("create_at").getValue(String.class);

                        Feedback feedbacks = new Feedback();
                        List<ItemsGiohang> itemsGiohangs = new ArrayList<>();
                        DataSnapshot flowerSnapshot = ordersnapshot.child("Items");
                        if (flowerSnapshot != null) {
                            for (DataSnapshot item : flowerSnapshot.getChildren()) {
                                ItemsGiohang itemsGiohang = item.getValue(ItemsGiohang.class);
                                itemsGiohangs.add(itemsGiohang);
                            }
                        }
                        Order order = new Order(id_order,name_user,number_phone,note,id_user,
                                address_user,order_ship_date,ship_date,total_bill,status,create_at,
                                feedbacks,itemsGiohangs);
                        orderList.add(order);
                    }
                    Collections.reverse(orderList);
                    OrderAdapter adaptor = new OrderAdapter(OrderAdminActivity.this, orderList);
                    adaptor.notifyDataSetChanged();
                    orderListView.setAdapter(adaptor);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadMenu(){
        init();
        home_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ih= new Intent(OrderAdminActivity.this, HomeAdminActivity.class);
                startActivity(ih);
            }
        });
        classify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent is= new Intent(OrderAdminActivity.this, CategoryAdminActivity.class);
                startActivity(is);
            }
        });
        goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ig= new Intent(OrderAdminActivity.this, ProductAdminActivity.class);
                startActivity(ig);
            }
        });
        oder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent io= new Intent(OrderAdminActivity.this, OrderAdminActivity.class);
                startActivity(io);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iu= new Intent(OrderAdminActivity.this, UserAdminActivity.class);
                startActivity(iu);
            }
        });
    }

    public void init(){
        home_ad= findViewById(R.id.home_ad);
        classify= findViewById(R.id.classify_ad);
        goods= findViewById(R.id.goods_ad);
        oder= findViewById(R.id.goods_ad);
        user= findViewById(R.id.user_ad);
    }
}