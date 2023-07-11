package com.example.flowerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.flowerapp.Adapter.AdminCategoryAdapter;
import com.example.flowerapp.Adapter.OrderAdapter;
import com.example.flowerapp.Entity.Category;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_admin);
        orderListView = findViewById(R.id.lstOrder);
        orderList = new ArrayList<>();
        databaseorder = FirebaseDatabase.getInstance().getReference("Order");
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
                        Order order = ordersnapshot.getValue(Order.class);
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
}