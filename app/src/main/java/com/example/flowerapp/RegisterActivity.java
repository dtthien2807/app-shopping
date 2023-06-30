package com.example.flowerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Button btnLogin, btnRegister;
    private EditText txtFullName, txtUsername, txtNumberphone, txtPassword, txtConfirm, txtAddress;
    FirebaseDatabase database;
    DatabaseReference reference;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnLogin = findViewById(R.id.login);
        btnRegister = findViewById(R.id.register);
        txtFullName = findViewById(R.id.fullname);
        txtUsername = findViewById(R.id.username);
        txtNumberphone = findViewById(R.id.numberphone);
        txtPassword = findViewById(R.id.password);
        txtConfirm= findViewById(R.id.confirm_button);
        txtAddress = findViewById(R.id.address);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword() | !validateFullname() | !validateAddress() | !validateNumberphone()) {
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Do something
                openLoginActivity();
            }
        });
    }
    public Boolean validateFullname() {
        String val = txtFullName.getText().toString();
        if (val.isEmpty()) {
            txtFullName.setError("Full name cannot be empty");
            return false;
        } else {
            txtFullName.setError(null);
            return true;
        }
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
    public Boolean validateNumberphone() {
        String val = txtNumberphone.getText().toString();
        if (val.isEmpty()) {
            txtNumberphone.setError("Number phone cannot be empty");
            return false;
        } else {
            txtNumberphone.setError(null);
            return true;
        }
    }
    public Boolean validateAddress() {
        String val = txtAddress.getText().toString();
        if (val.isEmpty()) {
            txtAddress.setError("Address cannot be empty");
            return false;
        } else {
            txtAddress.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = txtPassword.getText().toString();
        String conf = txtConfirm.getText().toString();
        if (val.isEmpty()) {
            txtPassword.setError("Password cannot be empty");
            return false;
        }else {
            txtPassword.setError(null);
            return true;
        }
    }
    public void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
