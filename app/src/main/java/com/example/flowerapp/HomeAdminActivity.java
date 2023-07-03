package com.example.flowerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeAdminActivity extends AppCompatActivity {
    TextView txtHello;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        txtHello = findViewById(R.id.txtHello);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
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
        Intent intent = new Intent(this, GiohangActivity.class);
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