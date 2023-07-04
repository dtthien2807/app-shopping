package com.example.flowerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.flowerapp.Entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity{
    private Button btnRegister, btnLogin;
    private ProgressBar progressbar;
    private EditText txtNumberphone, txtPassword;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegister = findViewById(R.id.register);
        btnLogin = findViewById(R.id.login);
        txtNumberphone = findViewById(R.id.numberphone);
        txtPassword = findViewById(R.id.password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()) {
                }
                else {
                    checkUser();
                }

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Do something
                openRegisterActivity();
            }
        });
    }
    public void openRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public Boolean validateUsername() {
        String val = txtNumberphone.getText().toString();
        if (val.isEmpty()) {
            txtNumberphone.setError("Username cannot be empty");
            return false;
        } else {
            txtNumberphone.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = txtPassword.getText().toString();
        if (val.isEmpty()) {
            txtPassword.setError("Password cannot be empty");
            return false;
        } else {
            txtPassword.setError(null);
            return true;
        }
    }
    public void checkUser(){
        String userNumberphone = txtNumberphone.getText().toString();
        String userPassword = txtPassword.getText().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(userNumberphone)) {
                    final String passwordFromDB = snapshot.child(userNumberphone).child("password").getValue(String.class);
                    Boolean checkRole = snapshot.child(userNumberphone).child("role").getValue(Boolean.class);
                    if (passwordFromDB.equals(userPassword)) {
                        if (checkRole){
                            Toast.makeText(LoginActivity.this, "You have login successfully!", Toast.LENGTH_SHORT).show();
                            openAdminActivity();
                            String id= snapshot.child(userNumberphone).getKey();
                                    setUserID(snapshot.child(userNumberphone).getKey());
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "You have login successfully!", Toast.LENGTH_SHORT).show();
                            openHomeActivity();
                            String id1= snapshot.child(userNumberphone).getKey();
                            setUserID(snapshot.child(userNumberphone).getKey());
                        }
                    } else {
                        txtPassword.setError("Invalid Credentials");
                        txtPassword.requestFocus();
                    }
                } else {
                    txtNumberphone.setError("User does not exist");
                    txtNumberphone.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void openHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    public void openAdminActivity(){
        Intent intent = new Intent(this, HomeAdminActivity.class);
        startActivity(intent);
    }
    public void setUserID(String userID)
    {
            // Khởi tạo SharedPreferences
               SharedPreferences sharedPreferences = getSharedPreferences("MyCookies", Context.MODE_PRIVATE);

            // Tạo một Editor để chỉnh sửa SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

            // Lưu trữ Cookie
                    editor.putString("userID", userID);

            // Áp dụng các thay đổi
                    editor.apply();

    }

    public void revert(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
