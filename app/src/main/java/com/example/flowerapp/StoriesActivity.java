package com.example.flowerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.flowerapp.Adapter.CategoryAdapter;

public class StoriesActivity extends AppCompatActivity {

    ImageView home, stories, pay, delivery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        loadLayout();
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
        home= findViewById(R.id.ic_home);
        stories= findViewById(R.id.ic_stories);
        pay= findViewById(R.id.ic_pay);
        delivery= findViewById(R.id.ic_delivery);
    }
}