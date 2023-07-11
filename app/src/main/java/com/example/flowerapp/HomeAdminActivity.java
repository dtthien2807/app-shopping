package com.example.flowerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flowerapp.Adapter.OrderAdapter;
import com.example.flowerapp.Entity.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeAdminActivity extends AppCompatActivity {
    TextView txtHello;
    DatabaseReference reference ;
    ListView lstOrderNew;
    List<Order> orderList;
    LinearLayout layout_new_order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        txtHello = findViewById(R.id.txtHello);
        layout_new_order = findViewById(R.id.layout_new_order);
        layout_new_order.setVisibility(View.INVISIBLE);
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(getUserID())) {
                    txtHello.setText("Xin chào, "+snapshot.child(getUserID()).child("fullname").getValue(String.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        lstOrderNew = findViewById(R.id.lstOrderNew);
        orderList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        reference.child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                if(snapshot.exists()) {
                    for (DataSnapshot ordersnapshot : snapshot.getChildren()) {
                        int checkStatus = ordersnapshot.child("status").getValue(int.class);
                        if (checkStatus == 0) {
                            layout_new_order.setVisibility(View.VISIBLE);
                            Order order = ordersnapshot.getValue(Order.class);
                            orderList.add(order);
                        }
                    }
                    Collections.reverse(orderList);
                    OrderAdapter adaptor = new OrderAdapter(HomeAdminActivity.this, orderList);
                    adaptor.notifyDataSetChanged();
                    lstOrderNew.setAdapter(adaptor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getListCategory(View view) {
        Intent intent = new Intent(this, CategoryAdminActivity.class);
        startActivity(intent);
    }

    public void getListOrder(View view) {
        Intent intent = new Intent(this, GiohangActivity.class);
        startActivity(intent);
    }

    public void getListUser(View view) {
        Intent intent = new Intent(this, UserAdminActivity.class);
        startActivity(intent);
    }

    public void signOut(View view) {
        SharedPreferences settings = getSharedPreferences("MyCookies", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
        Toast.makeText(this, "You have logged out successful!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public String getUserID()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("MyCookies", Context.MODE_PRIVATE);

        // Truy xuất Cookie
        String cookieValue = sharedPreferences.getString("userID", "");

        // Sử dụng Cookie
        return cookieValue;
    }
}