package com.example.flowerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.flowerapp.Entity.Category;
import com.example.flowerapp.Entity.Flower;
import com.example.flowerapp.Entity.ItemsGiohang;
import com.example.flowerapp.Entity.Order;
import com.example.flowerapp.Interface.OnGetDataListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OptionAddActivity extends AppCompatActivity {
    ImageView imageView;
    TextView tv_nameflower;
    TextView tv_description;
    TextView tv_price;
    TextView tv_quanity_cosan;
    TextView tv_quanity_dathang;
    ImageView btn_tang,btn_giam;
    Integer dathang=0;
    String name_pic,id_user;
    Button btn_themgiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_add);
        init();
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
//                        addBill(getUserID(),0);
                        Intent intent = new Intent(OptionAddActivity.this, GiohangActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
            }
        });

    }
    public void addBill(String searchUserId,int searchStatus)
    {

// Tạo một đối tượng DatabaseReference để tham chiếu đến nút "Order"
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Order");

// Sử dụng truy vấn Query để tìm kiếm dữ liệu
        Query query = orderRef.orderByChild("user_id").equalTo(searchUserId).orderByChild("status").equalTo(searchStatus);

// Thực hiện truy vấn và lắng nghe sự kiện ValueEventListener để nhận kết quả
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Có dữ liệu trả về từ truy vấn
                    for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                        addnewitems(getUserID(),"items");
                    }
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
                Toast.makeText(getApplicationContext(), "Thêm giỏ hàng lỗi khi tìm kiếm", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void addneworder() throws ParseException {
        // Tạo một đối tượng DatabaseReference để truy cập nút "Order"
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Order");

        // Tạo một đối tượng Order mới
                // Tạo một đối tượng Calendar đại diện cho ngày hiện tại
                Calendar calendar = Calendar.getInstance();

        // Định dạng ngày theo định dạng mong muốn, ví dụ: "yyyy-MM-dd"
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Lấy ngày hiện tại
                Date currentDate = dateFormat.parse(dateFormat.format(calendar.getTime())) ;

        //Tạo 1 order mới
                Order newOrder = new Order();
                newOrder.setNote("Phải đổi hoa hồng thành hoa cẩm chướng");
                newOrder.setCreate_at(currentDate);


        // Tạo một đối tượng Item1 và thiết lập giá trị cho các thuộc tính của nó
        Flower newItem = new Flower();
        newItem.setId_flower(getUserID());
        newItem.setPrice(Integer.parseInt(tv_price.getText().toString()));
        newItem.setQuantity(Integer.parseInt(tv_quanity_dathang.getText().toString()));
        newItem.setUrl(name_pic);
        List<Flower> lstFlower= new ArrayList<>();
        lstFlower.add(newItem);
        newOrder.setLstItemsGiohang(lstFlower);

        // Tạo key tự động cho order mới bằng phương thức push()
                String orderKey = orderRef.push().getKey();
        newOrder.setShip_date((java.sql.Date) dateFormat.parse("20/07/2023"));
        newOrder.setOrder_ship_date((java.sql.Date) dateFormat.parse("15/07/2023"));
        newOrder.setStatus(false);
        newOrder.setTotal_bill(4500000);
        newOrder.setId_order(getUserID().toString());

        // Xác định đường dẫn đến nút order mới
                DatabaseReference newOrderRef = orderRef.child(orderKey);

        // Thiết lập giá trị của order mới trong Firebase Realtime Database
                newOrderRef.setValue(newOrder)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Order mới đã được thêm thành công
                                } else {
                                    // Xảy ra lỗi khi thêm order mới
                                }
                            }
                        });

            }
    public void addnewitems( String orderKey, String itemsKey)
    {
    // Tạo một đối tượng DatabaseReference để truy cập nút "items"
            DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference()
                    .child("Order")
                    .child(orderKey)
                    .child(itemsKey);
    // Tạo một đối tượng Item mới
            Flower newItem = new Flower();
            newItem.setId_flower(getUserID());
            newItem.setPrice(Integer.parseInt(tv_price.getText().toString()));
            newItem.setQuantity(Integer.parseInt(tv_quanity_dathang.getText().toString()));
            newItem.setUrl(name_pic);

    // Tạo một khóa mới cho đối tượng Item
            String newItemKey = itemsRef.push().getKey();

    // Thiết lập giá trị của đối tượng Item trong Firebase Realtime Database
            itemsRef.child(newItemKey).setValue(newItem)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                // Đối tượng Item đã được thêm thành công
                            } else {
                                // Xảy ra lỗi khi thêm đối tượng Item
                                Toast.makeText(getApplicationContext(), "Thêm giỏ hàng Lỗi!!!", Toast.LENGTH_SHORT).show();

                            }
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
                            Resources resources = getResources();
                            String imageResourceName = item.getUrl();
                            int imageResourceId = resources.getIdentifier(imageResourceName, "drawable", getPackageName());
                            imageView.setImageResource(imageResourceId);
                            tv_quanity_cosan.setText(String.valueOf(item.getQuantity()));
                            tv_price.setText(String.valueOf(item.getPrice()));
                            tv_description.setText(item.getDescription());
                            tv_nameflower.setText(item.getName());
                            name_pic=item.getUrl();
                            lstFlower.add(item);
                        }
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
    public String getUserID()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("MyCookies", Context.MODE_PRIVATE);

        // Truy xuất Cookie
        String cookieValue = sharedPreferences.getString("FlowerPickID", "");

        // Sử dụng Cookie
        return cookieValue;
    }
}