package com.example.flowerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.flowerapp.Entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity{
    private Button btnRegister, btnLogin;
    private ProgressBar progressbar;
    private EditText txtUsername, txtPassword;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegister = findViewById(R.id.register);
        btnLogin = findViewById(R.id.login);
        txtUsername = findViewById(R.id.username);
        txtPassword = findViewById(R.id.password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()) {
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
        String val = txtUsername.getText().toString();
        if (val.isEmpty()) {
            txtUsername.setError("Username cannot be empty");
            return false;
        } else {
            txtUsername.setError(null);
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
//    public void checkUser(){
//        String userUsername = txtUsername.getText().toString().trim();
//        String userPassword = txtPassword.getText().toString().trim();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
//        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
//        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    txtUsername.setError(null);
//                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
//                    if (passwordFromDB.equals(userPassword)) {
//                        txtUsername.setError(null);
//                        String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
//                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
//                        String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("name", nameFromDB);
//                        intent.putExtra("email", emailFromDB);
//                        intent.putExtra("username", usernameFromDB);
//                        intent.putExtra("password", passwordFromDB);
//                        startActivity(intent);
//                    } else {
//                        txtPassword.setError("Invalid Credentials");
//                        txtPassword.requestFocus();
//                    }
//                } else {
//                    txtUsername.setError("User does not exist");
//                    txtUsername.requestFocus();
//                }
//            }
//        });
//    }

}
