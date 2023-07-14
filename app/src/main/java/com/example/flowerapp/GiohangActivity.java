package com.example.flowerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowerapp.Adapter.FlowerAdapter;
import com.example.flowerapp.Adapter.ItemsGiohangAdapter;
import com.example.flowerapp.Adapter.OrderAdapter;
import com.example.flowerapp.Entity.Flower;
import com.example.flowerapp.Entity.ItemsGiohang;
import com.example.flowerapp.Interface.OnGetDataUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiohangActivity extends AppCompatActivity {
    ListView ryc_items;
    ItemsGiohangAdapter itemsGiohangAdapter;
    EditText ed_name,ed_sdt,ed_address,ed_note;
    TextView tv_total,tv_price_ship;
    ImageButton imageButton;
    DatabaseReference reference;
    final String[] id_order = {"null"};
    ImageView home, stories, pay, delivery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gio_hang);
        init();
        setInforUser();
        findOrderByUserId(getUserID());
    }
    public void thanhtoan()
    {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference("Order/" + id_order[0]);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("path/to/your/data");
                // Tạo một HashMap để lưu trữ các giá trị cần cập nhật
                Map<String, Object> updates = new HashMap<>();
                updates.put("key1", "new value 1");
                updates.put("key2", "new value 2");
                updates.put("key3", "new value 3");
                // Thực hiện cập nhật
                ref.updateChildren(updates)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Cập nhật thành công
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Xảy ra lỗi trong quá trình cập nhật
                            }
                        });
                Intent intent = new Intent(GiohangActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
    public void Load_item()
    {
        getDataBill(new OnGetDataUser() {
            @Override
            public void onSuccess(List<ItemsGiohang> user) {
                ItemsGiohangAdapter itemsGiohangAdapter1 = new ItemsGiohangAdapter(GiohangActivity.this, user);
                itemsGiohangAdapter1.notifyDataSetChanged();
                ryc_items.setAdapter(itemsGiohangAdapter1);
                getTotalBill(user);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
    public void findOrderByUserId(String userId) {

        reference = FirebaseDatabase.getInstance().getReference("Order");

        // Sử dụng phương thức orderByChild và equalTo để tìm kiếm đơn hàng có id_user tương ứng
        Query query = reference.orderByChild("id_user").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    long status = orderSnapshot.child("status").getValue(Long.class);
                    if (status == 0) {
                        id_order[0] = orderSnapshot.child("id_order").getValue(String.class);
                        Load_item();
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
    public void getTotalBill(List<ItemsGiohang> lstItemsGiohang)
    {
        reference = FirebaseDatabase.getInstance().getReference("Order/" + id_order[0]);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Float total= Float.valueOf(0);
                Float fee_ship=Float.parseFloat(tv_price_ship.getText().toString());
                for (ItemsGiohang item : lstItemsGiohang)
                {
                    total+=item.getPrice()*item.getSoluongmuahang();
                }
                total+=fee_ship;
                tv_total.setText(String.valueOf(total));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getDataBill(OnGetDataUser listener) {

        List<ItemsGiohang> itemsGiohangList = new ArrayList<>();
        if (id_order[0].equals("null")) {
            Toast.makeText(GiohangActivity.this, "None find order!", Toast.LENGTH_SHORT).show();
        } else {
            reference = FirebaseDatabase.getInstance().getReference("Order/" + id_order[0] + "/Items");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        ItemsGiohang itemsGiohang= new ItemsGiohang();
                        itemsGiohang.setSoluongmuahang(snapshot1.child("soluongmuahang").getValue(Integer.class));
                        itemsGiohang.setPrice(snapshot1.child("price").getValue(Float.class));
                        itemsGiohang.setNameflower(snapshot1.child("nameflower").getValue(String.class));
                        itemsGiohang.setImgFlower(snapshot1.child("imgFlower").getValue(String.class));
                        itemsGiohangList.add(itemsGiohang);
                    }
                    listener.onSuccess(itemsGiohangList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }


    public void loadLayout(){
        init();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ih= new Intent(GiohangActivity.this, HomeActivity.class);
                startActivity(ih);
            }
        });
        stories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent is = new Intent(GiohangActivity.this, StoriesActivity.class);
                startActivity(is);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ip= new Intent(GiohangActivity.this, GiohangActivity.class);
                startActivity(ip);
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent id= new Intent(GiohangActivity.this, DeliveryActivity.class);
                startActivity(id);
            }
        });
    }
    public void init()
    {
        ed_name=findViewById(R.id.ed_name);
        ed_sdt=findViewById(R.id.et_contact);
        ed_address=findViewById(R.id.ed_address);
        ed_note=findViewById(R.id.ed_note);
        tv_total=findViewById(R.id.tv_totalbill);
        tv_price_ship=findViewById(R.id.tv_feeship);
        ryc_items=findViewById(R.id.lst_itemgiohang);
        imageButton =findViewById(R.id.img_btn_back_from_cart);
        home= findViewById(R.id.ic_home);
        stories= findViewById(R.id.ic_stories);
        pay= findViewById(R.id.ic_pay);
        delivery= findViewById(R.id.ic_delivery);
    }
    public  void setInforUser()
    {
        reference = FirebaseDatabase.getInstance().getReference("User");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(getUserID())) {
                    ed_sdt.setText("Phone: "+snapshot.child(getUserID()).child("numberphone").getValue(String.class));
                    ed_name.setText("FullName: "+snapshot.child(getUserID()).child("fullname").getValue(String.class));
                    ed_address.setText("Address: "+snapshot.child(getUserID()).child("address").getValue(String.class));
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