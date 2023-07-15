package com.example.flowerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.flowerapp.Entity.Category;
import com.example.flowerapp.Entity.Flower;
import com.example.flowerapp.Entity.ItemsGiohang;
import com.example.flowerapp.Entity.Order;
import com.example.flowerapp.Entity.User;
import com.example.flowerapp.Interface.OnGetDataListener;
import com.example.flowerapp.Interface.OnGetDataUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OptionAddActivity extends AppCompatActivity {
    DatabaseReference databasecategory;
    ImageView imageView;
    TextView tv_nameflower;
    TextView tv_description;
    TextView tv_price;
    TextView tv_quanity_cosan;
    TextView tv_quanity_dathang;
    ImageView btn_tang,btn_giam;
    Integer dathang=0;
    String name_pic;
    Button btn_themgiohang;
    User user;
    final String[] id_order = {"null"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_add);
        init();
        user= new User();
        findOrderByUserId(getUserID("userID"));
        btn_tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dathang = Integer.parseInt(tv_quanity_dathang.getText().toString());
                int soluongdat = dathang + 1;
                tv_quanity_dathang.setText(String.valueOf(soluongdat));
            }
        });

        btn_giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dathang = Integer.parseInt(tv_quanity_dathang.getText().toString());
                int soluongdat = dathang - 1;
                tv_quanity_dathang.setText(String.valueOf(soluongdat));
            }
        });

        lstCate(new OnGetDataListener() {
            @Override
            public void onSuccess(List<Category> lstCate) {
                btn_themgiohang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addfinal();
                        Intent intent = new Intent(OptionAddActivity.this, GiohangActivity.class);
                        startActivity(intent);
                    }
                });
            }
            public void onFailure(Exception e) {
            }
        });


    }
    public void addfinal()
    {
        if(id_order[0].equals("null"))
        {
            additemsforbill();
        }
        else {
            databasecategory = FirebaseDatabase.getInstance().getReference("Order/"+id_order[0]+"/Items");
            String key1 = databasecategory.push().getKey();
            databasecategory.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ItemsGiohang itemsGiohang = new ItemsGiohang();
                    itemsGiohang.setNameflower(tv_nameflower.getText().toString());
                    itemsGiohang.setImgFlower(name_pic);
                    itemsGiohang.setSoluongmuahang(Integer.parseInt(tv_quanity_dathang.getText().toString()));
                    itemsGiohang.setId_flower(getUserID("FlowerPickID"));
                    itemsGiohang.setPrice(Float.parseFloat(tv_price.getText().toString()));
                    databasecategory.child(key1).setValue(itemsGiohang);
                    Toast.makeText(OptionAddActivity.this, "You have add items successfully!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
    public void additemsforbill()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String created_at = dateFormat.format(date);
        Order order=new Order();
        databasecategory = FirebaseDatabase.getInstance().getReference("Order");
        String key = databasecategory.push().getKey();
        databasecategory.child(key).removeValue();
        order.setId_order(key);
        order.setStatus(0);
        order.setId_user(getUserID("userID"));
        order.setCreate_at(created_at);
        databasecategory.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databasecategory.child(key).setValue(order);
                databasecategory=databasecategory.child(key).child("Items");
                String key1=databasecategory.push().getKey();
                databasecategory.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ItemsGiohang itemsGiohang= new ItemsGiohang();
                        itemsGiohang.setNameflower(tv_nameflower.getText().toString());
                        itemsGiohang.setImgFlower(name_pic);
                        itemsGiohang.setSoluongmuahang(Integer.parseInt(tv_quanity_dathang.getText().toString()));
                        itemsGiohang.setId_flower(getUserID("FlowerPickID"));
                        itemsGiohang.setPrice(Float.parseFloat(tv_price.getText().toString()));
                        databasecategory.child(key1).setValue(itemsGiohang);
                        Toast.makeText(OptionAddActivity.this, "You have create order successfully!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

    }
    public void findOrderByUserId(String userId) {

        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Order");

        // Sử dụng phương thức orderByChild và equalTo để tìm kiếm đơn hàng có id_user tương ứng
        Query query = ordersRef.orderByChild("id_user").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    long status = orderSnapshot.child("status").getValue(Long.class);
                    if(status==0)
                    {
                        id_order[0] = orderSnapshot.child("id_order").getValue(String.class);
                        Toast.makeText(OptionAddActivity.this, id_order[0], Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }


    public void init() {
        imageView = findViewById(R.id.img_url);
        tv_description = findViewById(R.id.tv_description);
        tv_nameflower = findViewById(R.id.tv_name);
        tv_quanity_cosan = findViewById(R.id.tv_soluongcosan);
        tv_price = findViewById(R.id.tv_price);
        tv_quanity_dathang = findViewById(R.id.tv_soluong);
        btn_tang= findViewById(R.id.btn_tang);
        btn_giam= findViewById(R.id.btn_giam);
        btn_themgiohang=findViewById(R.id.btn_themgiohang);
    }

    public void lstCate(OnGetDataListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoryRef = database.getReference("Cate_Flower");
        Intent intent = getIntent();
        String id_flower = intent.getStringExtra("id_flower_pic");
        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Category> lstCate = new ArrayList<>();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    String title = categorySnapshot.child("name_category").getValue(String.class);
                    String id = categorySnapshot.child("id_category").getValue(String.class);
                    List<Flower> lstFlower = new ArrayList<>();
                    DataSnapshot flowerSnapshot = categorySnapshot.child("Flower");
                    for (DataSnapshot flowerDocument : flowerSnapshot.getChildren()) {
                        if(flowerDocument.getKey().equals(id_flower))
                        {
                            Flower item = new Flower();
                            item.setId_flower(flowerDocument.getKey());
                            setUserID(flowerDocument.getKey());
                            item.setUrl(flowerDocument.child("url").getValue(String.class));
                            item.setStatus(flowerDocument.child("status").getValue(Boolean.class));
                            item.setQuantity(flowerDocument.child("quantity").getValue(Long.class));
                            item.setPrice(flowerDocument.child("price").getValue(Float.class));
                            item.setName(flowerDocument.child("name_flower").getValue(String.class));
                            item.setDescription(flowerDocument.child("description").getValue(String.class));
//                            Resources resources = getResources();
                            String imageResourceName = item.getUrl();
//                            int imageResourceId = resources.getIdentifier(imageResourceName, "drawable", getPackageName());
//                            imageView.setImageResource(imageResourceId);
                            Glide.with(OptionAddActivity.this).load(imageResourceName).into(imageView);
                            //xu ly so nguyen
                            tv_quanity_cosan.setText(String.valueOf(Math.round(item.getQuantity())));
                            tv_price.setText(String.valueOf(Math.round(item.getPrice())));
                            tv_description.setText(item.getDescription());
                            tv_nameflower.setText(item.getName());
                            name_pic=item.getUrl();
                            lstFlower.add(item);
                        }
                    }
                    Category cate = new Category(id,title, lstFlower);
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
    public void setUserID(String FlowerPickID)
    {
        // Khởi tạo SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyCookies", Context.MODE_PRIVATE);

        // Tạo một Editor để chỉnh sửa SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Lưu trữ Cookie
        editor.putString("FlowerPickID", FlowerPickID);

        // Áp dụng các thay đổi
        editor.apply();

    }
    public String getUserID(String FlowerPickID)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("MyCookies", Context.MODE_PRIVATE);

        // Truy xuất Cookie
        String cookieValue = sharedPreferences.getString(FlowerPickID, "");

        // Sử dụng Cookie
        return cookieValue;
    }
}