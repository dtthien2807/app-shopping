package com.example.flowerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.flowerapp.Adapter.CategoryAdapter;
import com.example.flowerapp.Entity.Category;
import com.example.flowerapp.Entity.Flower;
import com.example.flowerapp.Interface.OnGetDataListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView rcyCategoy;
    CategoryAdapter categoryAdapter;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    Date date;
    ImageView home, stories, pay, delivery;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences("MyCookies", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                Toast.makeText(HomeActivity.this, "You have logged out successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //hiển thị view Category theo chiều dọc
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcyCategoy.setLayoutManager(linearLayoutManager);

        lstCate(new OnGetDataListener() {
            @Override
            public void onSuccess(List<Category> lstCate) {
                // Xử lý dữ liệu ở đây sau khi dữ liệu đã được lấy thành công
                categoryAdapter.setData(lstCate);
                rcyCategoy.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Exception e) {
                // Xử lý lỗi ở đây nếu có
                Toast.makeText(HomeActivity.this, "Đã xảy ra lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        loadLayout();
    }

    public void lstCate(OnGetDataListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoryRef = database.getReference("Cate_Flower");
        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Category> lstCate = new ArrayList<>();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    String title = categorySnapshot.child("name_category").getValue(String.class);
                    String id = categorySnapshot.child("id_category").getValue(String.class);
                    List<Flower> lstFlower = new ArrayList<>();
                    DataSnapshot flowerSnapshot = categorySnapshot.child("Flower");
                    for (DataSnapshot flowerDocument : flowerSnapshot.getChildren()) {
                        Flower item = new Flower();
                        item.setId_flower(flowerDocument.getKey());
                        item.setUrl(flowerDocument.child("url").getValue(String.class));
                        item.setStatus(flowerDocument.child("status").getValue(Boolean.class));
                        item.setQuantity(flowerDocument.child("quantity").getValue(Long.class));
                        item.setPrice(flowerDocument.child("price").getValue(Float.class));
                        item.setName(flowerDocument.child("name").getValue(String.class));
                        item.setDescription(flowerDocument.child("description").getValue(String.class));
                        lstFlower.add(item);

                    }
                    Category cate = new Category(id, title, lstFlower);
                    lstCate.add(cate);
                }
                listener.onSuccess(lstCate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure(databaseError.toException());
            }
        });
    }

    public void loadLayout(){
        init();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ih= new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(ih);
            }
        });
        stories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent is = new Intent(HomeActivity.this, StoriesActivity.class);
                startActivity(is);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ip= new Intent(HomeActivity.this, GiohangActivity.class);
                startActivity(ip);
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent id= new Intent(HomeActivity.this, DeliveryActivity.class);
                startActivity(id);
            }
        });
    }
    public void init()
    {
        rcyCategoy= findViewById(R.id.rcyCategoryxml);
        categoryAdapter= new CategoryAdapter(this);
        home= findViewById(R.id.ic_home);
        stories= findViewById(R.id.ic_stories);
        pay= findViewById(R.id.ic_pay);
        delivery= findViewById(R.id.ic_delivery);
        logout= findViewById(R.id.btnlogout);
    }

    public void logOut(View view) {
        SharedPreferences settings = getSharedPreferences("MyCookies", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
        Toast.makeText(this, "You have logged out successful!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
