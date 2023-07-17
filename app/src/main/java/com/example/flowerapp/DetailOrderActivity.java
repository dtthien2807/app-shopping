package com.example.flowerapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.flowerapp.Adapter.ItemsOrderAdapter;
import com.example.flowerapp.Entity.Feedback;
import com.example.flowerapp.Entity.ItemsGiohang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailOrderActivity extends AppCompatActivity {
    ImageView home_ad, goods, oder, user;
    DatabaseReference databaseorder;
    TextView orderID, createdAt, statusOrder, nameUser, phoneUser, addressUser, dateOrder, dateShip, sumBill, noteBill;
    Button btnUpdateBill, btnFeedback,btnFeedbackCustomer;
    CheckBox option0, option1, option2, option3, option4;
    private String id_order;
    ListView lstProduct;
    private ImageView home,stories,pay,delivery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        init();
        loadMenu();
        btnFeedbackCustomer.setVisibility(View.GONE);
        databaseorder = FirebaseDatabase.getInstance().getReference("Order");
        btnUpdateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdateBill();
            }
        });
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogFeedback();
            }
        });
        getQuyen();
    }
    public String getUserID()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("MyCookies", Context.MODE_PRIVATE);

        // Truy xuất Cookie
        String cookieValue = sharedPreferences.getString("userID", "");
        // Sử dụng Cookie
        return cookieValue;
    }
    public void loadLayout(){
        init();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ih= new Intent(DetailOrderActivity.this, HomeActivity.class);
                startActivity(ih);
            }
        });
        stories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent is = new Intent(DetailOrderActivity.this, StoriesActivity.class);
                startActivity(is);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ip= new Intent(DetailOrderActivity.this, GiohangActivity.class);
                startActivity(ip);
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent id= new Intent(DetailOrderActivity.this, DeliveryActivity.class);
                startActivity(id);
            }
        });
    }
    public void getQuyen()
    {
        databaseorder = FirebaseDatabase.getInstance().getReference("User");
        databaseorder.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapshot1:snapshot.getChildren()) {
                        if (snapshot1.child(getUserID()).child("role").getValue(Boolean.class) == false) {
                            btnFeedbackCustomer.setVisibility(View.VISIBLE);
                            btnFeedback.setVisibility(View.GONE);
                            btnUpdateBill.setVisibility(View.GONE);
                            LinearLayout menu = findViewById(R.id.menu);
                            menu.setVisibility(View.GONE);
                            LinearLayout menu_cus = findViewById(R.id.menu_customer);
                            menu_cus.setVisibility(View.VISIBLE);
                            loadLayout();
                            ImageView back = findViewById(R.id.revert);
                            back.setVisibility(View.GONE);
                            btnFeedbackCustomer.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    openDialogFeedbackCustomer();
                                }
                            });
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
    }
    private void openDialogFeedbackCustomer() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_add_feedback);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setCancelable(false);
        ImageView btn_cancle= dialog.findViewById(R.id.btn_cancle_customer);
        TextView txtFeedback = dialog.findViewById(R.id.ed_content_feedback);
        databaseorder.child(id_order).child("feedbacks").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Feedback feedback=new Feedback();
                feedback.setContent(txtFeedback.getText().toString());
                feedback.setImg("hoahuongduong");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void openDialogFeedback() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_feedback);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        ImageView btnCancel = dialog.findViewById(R.id.btCancel);
        ImageView imgFeedback = dialog.findViewById(R.id.imgFeedback);
        TextView txtFeedback = dialog.findViewById(R.id.txtFeedback);
        databaseorder.child(id_order).child("feedbacks").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String path = snapshot.child("img").getValue(String.class);
                Glide.with(DetailOrderActivity.this).load(path).into(imgFeedback);
                txtFeedback.setText(snapshot.child("content").getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void init()
    {
        btnFeedbackCustomer=findViewById(R.id.btnFeedbackkhachhang);
        orderID = findViewById(R.id.orderID);
        createdAt = findViewById(R.id.createdAt);
        statusOrder = findViewById(R.id.statusOrder);
        nameUser = findViewById(R.id.nameUser);
        phoneUser = findViewById(R.id.phoneUser);
        addressUser = findViewById(R.id.addressUser);
        dateOrder = findViewById(R.id.dateOrder);
        dateShip = findViewById(R.id.dateShip);
        sumBill = findViewById(R.id.sumBill);
        noteBill = findViewById(R.id.noteBill);
        btnUpdateBill = findViewById(R.id.btnUpdateBill);
        btnFeedback = findViewById(R.id.btnFeedback);
        lstProduct = findViewById(R.id.lstProduct);
        home_ad= findViewById(R.id.home_ad);
        goods= findViewById(R.id.goods_ad);
        oder= findViewById(R.id.oder_ad);
        user= findViewById(R.id.user_ad);
        home= findViewById(R.id.ic_home);
        stories= findViewById(R.id.ic_stories);
        pay= findViewById(R.id.ic_pay);
        delivery= findViewById(R.id.ic_delivery);
    }

    public void openDialogUpdateBill() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_edit_status_order);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button btn_edit = dialog.findViewById(R.id.btn_edit);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        option0 = dialog.findViewById(R.id.option0);
        option1 = dialog.findViewById(R.id.option1);
        option2 = dialog.findViewById(R.id.option2);
        option3 = dialog.findViewById(R.id.option3);
        option4 = dialog.findViewById(R.id.option4);

        databaseorder.child(id_order).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int status = snapshot.child("status").getValue(Integer.class);
                switch (status)
                {
                    case 0:
                    {
                        option0.setChecked(true);
                        break;
                    }
                    case 1:
                    {
                        option1.setChecked(true);
                        break;
                    }
                    case 2:
                    {
                        option2.setChecked(true);
                        break;
                    }
                    case 3:
                    {
                        option3.setChecked(true);
                        break;
                    }
                    case 4:
                    {
                        option4.setChecked(true);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        option0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    option1.setChecked(false);
                    option2.setChecked(false);
                    option3.setChecked(false);
                    option4.setChecked(false);
                }
            }
        });
        option1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    option0.setChecked(false);
                    option2.setChecked(false);
                    option3.setChecked(false);
                    option4.setChecked(false);
                }
            }
        });

        option2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    option0.setChecked(false);
                    option1.setChecked(false);
                    option3.setChecked(false);
                    option4.setChecked(false);
                }
            }
        });
        option3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    option0.setChecked(false);
                    option1.setChecked(false);
                    option2.setChecked(false);
                    option4.setChecked(false);
                }
            }
        });
        option4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    option0.setChecked(false);
                    option1.setChecked(false);
                    option2.setChecked(false);
                    option3.setChecked(false);
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (option0.isChecked()) {
                        databaseorder.child(id_order).child("status").setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                recreate();
                                Toast.makeText(DetailOrderActivity.this, "You have updated successful!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    } else if (option1.isChecked()) {
                        databaseorder.child(id_order).child("status").setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                recreate();
                                Toast.makeText(DetailOrderActivity.this, "You have updated successful!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    } else if (option2.isChecked()) {
                        databaseorder.child(id_order).child("status").setValue(2).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                recreate();
                                Toast.makeText(DetailOrderActivity.this, "You have updated successful!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    } else if (option3.isChecked()) {
                        databaseorder.child(id_order).child("status").setValue(3).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                Date date = new Date();
                                String ship_date = dateFormat.format(date);
                                databaseorder.child(id_order).child("ship_date").setValue(ship_date);
                                recreate();
                                Toast.makeText(DetailOrderActivity.this, "You have updated successful!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    } else if (option4.isChecked()) {
                        databaseorder.child(id_order).child("status").setValue(4).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                recreate();
                                Toast.makeText(DetailOrderActivity.this, "You have updated successful!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                }
            });
        dialog.show();
    }

    public void loadMenu(){
        init();
        home_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ih= new Intent(DetailOrderActivity.this, HomeAdminActivity.class);
                startActivity(ih);
            }
        });
        goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ig= new Intent(DetailOrderActivity.this, CategoryAdminActivity.class);
                startActivity(ig);
            }
        });
        oder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent io= new Intent(DetailOrderActivity.this, OrderAdminActivity.class);
                startActivity(io);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iu= new Intent(DetailOrderActivity.this, UserAdminActivity.class);
                startActivity(iu);
            }
        });
    }

    @Override
    protected void onStart() {
        Intent intent = getIntent();
        id_order = intent.getStringExtra("id_order");
        databaseorder=FirebaseDatabase.getInstance().getReference("Order");
        super.onStart();
        databaseorder.child(id_order).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    orderID.setText(snapshot.child("id_order").getValue(String.class));
                    createdAt.setText(snapshot.child("create_at").getValue(String.class));
                    int checkStatus = snapshot.child("status").getValue(Integer.class);
                    switch (checkStatus)
                    {
                        case 0:
                        {
                            statusOrder.setText("Chờ xét duyệt");
                            statusOrder.setBackgroundColor(0x56E81C0A);
                            statusOrder.setTextColor(Color.parseColor("#5A182B"));
                            break;
                        }
                        case 1:
                        {
                            statusOrder.setText("Chờ giao hàng");
                            statusOrder.setBackgroundColor(0x56ACACA8);
                            statusOrder.setTextColor(Color.parseColor("#757574"));
                            break;
                        }
                        case 2:
                        {
                            statusOrder.setText("Đang giao hàng");
                            statusOrder.setBackgroundColor(0x787C05);
                            statusOrder.setTextColor(Color.parseColor("#787C05"));
                            break;
                        }
                        case 3:
                        {
                            statusOrder.setText("Giao hàng thành công");
                            statusOrder.setBackgroundColor(0x564CAF50);
                            statusOrder.setTextColor(Color.parseColor("#185A1B"));
                            break;
                        }
                        case 4:
                        {
                            statusOrder.setText("Giao hàng không thành công");
                            statusOrder.setBackgroundColor(0xF80E0E0E);
                            statusOrder.setTextColor(Color.parseColor("#F1F4F1"));
                            break;
                        }
                        case 5:
                        {
                            statusOrder.setText("Chờ đặt hàng");
                            statusOrder.setBackgroundColor(0xBF57DFD2);
                            statusOrder.setTextColor(Color.parseColor("#0E30ED"));
                            break;
                        }
                    }
                    nameUser.setText("Khách hàng: "+snapshot.child("name_user").getValue(String.class));
                    phoneUser.setText("Số điện thoại: " +snapshot.child("number_phone").getValue(String.class));
                    addressUser.setText("Địa chỉ: " +snapshot.child("address_user").getValue(String.class));
                    dateOrder.setText("Ngày yêu cầu giao: " +snapshot.child("order_ship_date").getValue(String.class));
                    dateShip.setText("Ngày giao: "+snapshot.child("ship_date").getValue(String.class));
                    List<ItemsGiohang> itemsGiohangList = new ArrayList<>();
                    DataSnapshot flowerSnapshot = snapshot.child("Items");
                    if (flowerSnapshot != null)
                    {
                        for (DataSnapshot itemsnapshot : flowerSnapshot.getChildren()) {
                            ItemsGiohang itemsGiohang = itemsnapshot.getValue(ItemsGiohang.class);
                            itemsGiohangList.add(itemsGiohang);
                        }
                        ItemsOrderAdapter adaptor = new ItemsOrderAdapter(DetailOrderActivity.this, itemsGiohangList);
                        adaptor.notifyDataSetChanged();
                        lstProduct.setAdapter(adaptor);
                    }

                    sumBill.setText(String.valueOf(snapshot.child("total_bill").getValue(Float.class)));
                    noteBill.setText("Ghi chú: "+snapshot.child("note").getValue(String.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    public void revert(View view) {
        Intent intent = new Intent(this, OrderAdminActivity.class);
        startActivity(intent);
    }
}
