package com.example.flowerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.flowerapp.Adapter.OrderAdapter;
import com.example.flowerapp.Entity.Feedback;
import com.example.flowerapp.Entity.ItemsGiohang;
import com.example.flowerapp.Entity.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {
    DatabaseReference databaseorder;
    ListView orderListView;
    List<Order> orderList;
    ImageView home, stories, pay, delivery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        orderList=new ArrayList<>();
        databaseorder = FirebaseDatabase.getInstance().getReference("Order");
        loadLayout();
      //  getlistmyhoadon();
    }
    public String getUserID()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("MyCookies", Context.MODE_PRIVATE);

        // Truy xuất Cookie
        String cookieValue = sharedPreferences.getString("userID", "");

        // Sử dụng Cookie
        return cookieValue;
    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseorder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Order> orderList = new ArrayList<>();

                if (snapshot.exists()) {
                    for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                        if(orderSnapshot.child("id_user").getValue(String.class).equals(getUserID())) {
                            String id_order = orderSnapshot.child("id_order").getValue(String.class);
                            String name_user = orderSnapshot.child("name_user").getValue(String.class);
                            String number_phone = orderSnapshot.child("number_phone").getValue(String.class);
                            String note = orderSnapshot.child("note").getValue(String.class);
                            String id_user = orderSnapshot.child("id_user").getValue(String.class);
                            String address_user = orderSnapshot.child("address_user").getValue(String.class);
                            String order_ship_date = orderSnapshot.child("order_ship_date").getValue(String.class);
                            String ship_date = orderSnapshot.child("ship_date").getValue(String.class);
                            Float total_bill = orderSnapshot.child("total_bill").getValue(Float.class);
                            Integer status = orderSnapshot.child("status").getValue(Integer.class);
                            String created_at = orderSnapshot.child("created_at").getValue(String.class);

                            Feedback feedback = orderSnapshot.child("feedbacks").getValue(Feedback.class);

                            List<ItemsGiohang> itemsGiohangs = new ArrayList<>();
                            DataSnapshot itemsSnapshot = orderSnapshot.child("Items");

                            if (itemsSnapshot.exists()) {
                                for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                                    ItemsGiohang itemsGiohang = itemSnapshot.getValue(ItemsGiohang.class);
                                    itemsGiohangs.add(itemsGiohang);
                                }
                            }

                            Order order = new Order(id_order, name_user, number_phone, note, id_user,
                                    address_user, order_ship_date, ship_date, total_bill, status, created_at,
                                    feedback, itemsGiohangs);
                            orderList.add(order);
                        }
                    }

                    Collections.reverse(orderList);
                    OrderAdapter adapter = new OrderAdapter(DeliveryActivity.this, orderList);
                    orderListView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event if needed
            }
        });
    }

    public void loadLayout(){
        init();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ih= new Intent(DeliveryActivity.this, HomeActivity.class);
                startActivity(ih);
            }
        });
        stories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent is = new Intent(DeliveryActivity.this, StoriesActivity.class);
                startActivity(is);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ip= new Intent(DeliveryActivity.this, GiohangActivity.class);
                startActivity(ip);
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent id= new Intent(DeliveryActivity.this, DeliveryActivity.class);
                startActivity(id);
            }
        });
    }

    public void init()
    {
        home= findViewById(R.id.ic_home);
        stories= findViewById(R.id.ic_stories);
        pay= findViewById(R.id.ic_pay);
        delivery= findViewById(R.id.ic_delivery);
        orderListView=findViewById(R.id.lstorderuser);
    }

}