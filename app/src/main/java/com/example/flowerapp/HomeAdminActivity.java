package com.example.flowerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
    }

    public void getListCategory(View view) {
        Intent intent = new Intent(this, CategoryAdminActivity.class);
        startActivity(intent);
    }

    public void getListProduct(View view) {
        Intent intent = new Intent(this, ProductAdminActivity.class);
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

    public void getListImage(View view) {
    }

    public void getListFeedback(View view) {
    }

    public void signOut(View view) {
    }
}