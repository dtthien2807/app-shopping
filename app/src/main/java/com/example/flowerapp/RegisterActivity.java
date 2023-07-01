package com.example.flowerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flowerapp.Entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                else {
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("User");
                    String fullname = txtFullName.getText().toString();
                    String username = txtUsername.getText().toString();
                    String password = txtPassword.getText().toString();
                    String address = txtAddress.getText().toString();
                    String numberphone = txtNumberphone.getText().toString();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String created_at = dateFormat.format(date);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(numberphone)) {
                                txtNumberphone.setError("Number phone already exist");
                                txtNumberphone.requestFocus();
                            } else {
                                User user = new User(username, fullname, password, address, false, true, numberphone, created_at);
                                reference.child(numberphone).setValue(user);
                                Toast.makeText(RegisterActivity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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
        } else if (!val.equals(conf)) {
            txtPassword.setError("Password not match with confirm password");
            return false;
        } else {
            txtPassword.setError(null);
            return true;
        }
    }
    public void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
