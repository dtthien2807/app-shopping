package com.example.flowerapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.flowerapp.Adapter.CategoryAdapter;
import com.example.flowerapp.Entity.Category;
import com.example.flowerapp.Entity.Flower;
import com.example.flowerapp.Interface.OnGetDataListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView rcyCategoy;
    CategoryAdapter categoryAdapter;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        //hiển thị view Category theo chiều dọc
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcyCategoy.setLayoutManager(linearLayoutManager);
        lstCate(new OnGetDataListener() {
            @Override
            public void onSuccess(List<Category> lstCate) {
                // Xử lý dữ liệu ở đây sau khi dữ liệu đã được lấy thành công
                categoryAdapter.setData(lstCate);
                rcyCategoy.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Exception e) {
                // Xử lý lỗi ở đây nếu có
                Toast.makeText(HomeActivity.this, "Đã xảy ra lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void lstCate(OnGetDataListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoryRef = database.getReference("Cate_Flower");
        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Category> lstCate = new ArrayList<>();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    String title = categorySnapshot.child("name_category").getValue(String.class);
                    List<Flower> lstFlower = new ArrayList<>();
                    DataSnapshot flowerSnapshot = categorySnapshot.child("Flower");
                    for (DataSnapshot flowerDocument : flowerSnapshot.getChildren()) {
                        Flower item = new Flower();
                        item.setId_flower(flowerDocument.getKey());
                        item.setUrl(flowerDocument.child("url").getValue(String.class));
                        item.setStatus(flowerDocument.child("status").getValue(Boolean.class));
                        item.setQuantity(flowerDocument.child("quantity").getValue(Long.class));
                        item.setPrice(flowerDocument.child("price").getValue(Float.class));
//
//                        try {
//                            date = format.parse(flowerDocument.child("created_at").getValue(String.class));
//                        } catch (ParseException e) {
//                            throw new RuntimeException(e);
//                        }
 //                       item.setCreated_at(date);
                        item.setName(flowerDocument.child("name_flower").getValue(String.class));
                        item.setDescription(flowerDocument.child("description").getValue(String.class));
                        lstFlower.add(item);

                    }
                    Category cate = new Category(title, lstFlower);
                    lstCate.add(cate);
                }
                listener.onSuccess(lstCate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure(databaseError.toException());
            }
        });
    }

    public void init()
    {
        rcyCategoy= findViewById(R.id.rcyCategoryxml);
        categoryAdapter= new CategoryAdapter(this);

    }
}