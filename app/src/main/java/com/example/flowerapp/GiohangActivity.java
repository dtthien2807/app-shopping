package com.example.flowerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowerapp.Adapter.FlowerAdapter;
import com.example.flowerapp.Adapter.ItemsGiohangAdapter;
import com.example.flowerapp.Entity.Flower;
import com.example.flowerapp.Entity.ItemsGiohang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;

public class GiohangActivity extends AppCompatActivity {
    RecyclerView ryc_items;
    ItemsGiohangAdapter itemsGiohangAdapter;
    EditText ed_name,ed_sdt,ed_address,ed_note;
    TextView tv_total,tv_price_ship;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gio_hang);
        init();
        setInforUser();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        ryc_items.setLayoutManager(linearLayoutManager);
        itemsGiohangAdapter.setData(getDataBill());
        ryc_items.setAdapter(itemsGiohangAdapter);
    }
    public List<ItemsGiohang> getDataBill()
    {
        List<ItemsGiohang> lst_flower= new ArrayList<>();
        return lst_flower;
    }
    public void init()
    {
        ed_name=findViewById(R.id.ed_name);
        ed_sdt=findViewById(R.id.et_contact);
        ed_address=findViewById(R.id.ed_address);
        ed_note=findViewById(R.id.ed_note);
        tv_total=findViewById(R.id.tv_totalbill);
        tv_price_ship=findViewById(R.id.tv_feeship);
        ryc_items=findViewById(R.id.ryc_items);
    }
    public  void setInforUser()
    {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(getUserID())) {
                    ed_sdt.setText(snapshot.child(getUserID()).child("numberphone").getValue(String.class));
                    ed_name.setText(snapshot.child(getUserID()).child("fullname").getValue(String.class));
                    ed_address.setText(snapshot.child(getUserID()).child("address").getValue(String.class));
                }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
            });
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