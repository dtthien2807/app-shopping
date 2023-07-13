package com.example.flowerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flowerapp.Adapter.OrderAdapter;
import com.example.flowerapp.Entity.Feedback;
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

public class HomeAdminActivity extends AppCompatActivity {
    TextView txtHello;
    DatabaseReference reference ;
    ListView lstOrderNew;
    List<Order> orderList;
    LinearLayout layout_new_order;
    ImageView home_ad, goods, oder, user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        txtHello = findViewById(R.id.txtHello);
        layout_new_order = findViewById(R.id.layout_new_order);
        layout_new_order.setVisibility(View.INVISIBLE);
        reference = FirebaseDatabase.getInstance().getReference();

        loadMenu();
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
                        String checkStatus = String.valueOf(ordersnapshot.child("status").getValue(Integer.class));
                        if (checkStatus.equals("0")) {
                            layout_new_order.setVisibility(View.VISIBLE);
//                            Order order = ordersnapshot.getValue(Order.class);
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
                            if(flowerSnapshot != null) {
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
        Intent intent = new Intent(this, OrderAdminActivity.class);
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

    public void loadMenu(){
        init();
        home_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ih= new Intent(HomeAdminActivity.this, HomeAdminActivity.class);
                startActivity(ih);
            }
        });
        goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ig= new Intent(HomeAdminActivity.this, ProductAdminActivity.class);
                startActivity(ig);
            }
        });
        oder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent io= new Intent(HomeAdminActivity.this, OrderAdminActivity.class);
                startActivity(io);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iu= new Intent(HomeAdminActivity.this, UserAdminActivity.class);
                startActivity(iu);
            }
        });
    }

    public void init(){
        home_ad= findViewById(R.id.home_ad);
        goods= findViewById(R.id.goods_ad);
        oder= findViewById(R.id.goods_ad);
        user= findViewById(R.id.user_ad);
    }
}