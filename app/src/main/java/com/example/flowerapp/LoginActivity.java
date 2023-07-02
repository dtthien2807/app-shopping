package com.example.flowerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
                    String usernameFromDB = snapshot.child(userNumberphone).child("username").getValue(String.class);
                    String fullnameFromDB = snapshot.child(userNumberphone).child("fullname").getValue(String.class);
                    String addressFromDB = snapshot.child(userNumberphone).child("address").getValue(String.class);
                    Boolean checkStatus = snapshot.child(userNumberphone).child("status").getValue(Boolean.class);
                    if (passwordFromDB.equals(userPassword)) {
                        if(checkStatus) {
                            if (checkRole) {
                                Toast.makeText(LoginActivity.this, "You have login successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
                                intent.putExtra("numberphone", userNumberphone);
                                intent.putExtra("username", usernameFromDB);
                                intent.putExtra("fullname", fullnameFromDB);
                                intent.putExtra("password", passwordFromDB);
                                intent.putExtra("address", addressFromDB);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "You have login successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("numberphone", userNumberphone);
                                intent.putExtra("username", usernameFromDB);
                                intent.putExtra("fullname", fullnameFromDB);
                                intent.putExtra("password", passwordFromDB);
                                intent.putExtra("address", addressFromDB);
                                startActivity(intent);
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Your account have been locked! Call admin by hotline", Toast.LENGTH_SHORT).show();
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
}
