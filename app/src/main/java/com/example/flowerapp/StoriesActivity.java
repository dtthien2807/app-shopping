package com.example.flowerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.flowerapp.Adapter.StoriesAdapter;
import com.example.flowerapp.Entity.Stories;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StoriesActivity extends AppCompatActivity {
    GridView gr_stories;
    DatabaseReference reference;
    StoriesAdapter str_adapter;
    ImageView home, stories, pay, delivery;

    List<Stories> liststr= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);
        init();
        loadLayout();
        loadGridviewImg();
    }

    public void loadGridviewImg(){
        reference= FirebaseDatabase.getInstance().getReference("flower_story");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data_str: snapshot.getChildren()){
                    Stories item= new Stories();
                    item.setId(data_str.getKey());
                    item.setImg(data_str.child("img").getValue(String.class));
                    item.setTenImg(data_str.child("tenImg").getValue(String.class));
                    item.setMota(data_str.child("mota").getValue(String.class));
                    item.setDoituong(data_str.child("doituong").getValue(String.class));
                    item.setDip(data_str.child("dip").getValue(String.class));
                    item.setMoitruong(data_str.child("moitruong").getValue(String.class));
                    liststr.add(item);
                }
                str_adapter=new StoriesAdapter(StoriesActivity.this,liststr);
                gr_stories.setAdapter(str_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadLayout(){
        init();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ih= new Intent(StoriesActivity.this, HomeActivity.class);
                startActivity(ih);
            }
        });
        stories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent is = new Intent(StoriesActivity.this, StoriesActivity.class);
                startActivity(is);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ip= new Intent(StoriesActivity.this, GiohangActivity.class);
                startActivity(ip);
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent id= new Intent(StoriesActivity.this, DeliveryActivity.class);
                startActivity(id);
            }
        });
    }

    public void init()
    {
        gr_stories= findViewById(R.id.gv_stories);
        home= findViewById(R.id.ic_home);
        stories= findViewById(R.id.ic_stories);
        pay= findViewById(R.id.ic_pay);
        delivery= findViewById(R.id.ic_delivery);
    }
}