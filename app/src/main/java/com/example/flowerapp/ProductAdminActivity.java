package com.example.flowerapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.flowerapp.Adapter.ProductAdapter;
import com.example.flowerapp.Entity.Flower;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductAdminActivity extends AppCompatActivity {

    DatabaseReference databasecategory;
    GridView productListView;
    List<Flower> flowerList;
    ImageView imgUpload;
    ImageView home_ad, goods, oder, user;
    private final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    public static final int SELECT_PICTURE = 200;
    ProgressBar progressUpload;
    Uri imgUrl;
    Uri path;
    private String pathImg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_admin);
        productListView = findViewById(R.id.list_product);
        flowerList = new ArrayList<>();
        databasecategory = FirebaseDatabase.getInstance().getReference("Cate_Flower");

        loadMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String id_category = intent.getStringExtra("id_category");
        databasecategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                flowerList.clear();
                if(snapshot.exists()) {
                    for (DataSnapshot categorysnapshot : snapshot.getChildren()) {
                        if (categorysnapshot.getKey().equals(id_category)) {
                            DataSnapshot flowerSnapshot = categorysnapshot.child("Flower");
                            for (DataSnapshot flowerDocument : flowerSnapshot.getChildren()) {
                                Boolean checkStatus = flowerDocument.child("status").getValue(Boolean.class);
                                if(checkStatus) {
                                    Flower flower = flowerDocument.getValue(Flower.class);
                                    flowerList.add(flower);
                                }
                            }
                        }
                    }
                    ProductAdapter adaptor = new ProductAdapter(ProductAdminActivity.this, flowerList, new ProductAdapter.IClickListener() {
                        @Override
                        public void onClickEditProduct(Flower flower) {
                            openDialogEdit(flower);
                        }

                        @Override
                        public void onClickDeleteProduct(Flower flower) {
                            openDialogDelete(flower);
                        }
                    });
                    adaptor.notifyDataSetChanged();
                    productListView.setAdapter(adaptor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openDialogDelete(Flower flower) {
        Intent intent = getIntent();
        String id_category = intent.getStringExtra("id_category");
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_delete_flower);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button btn_del = dialog.findViewById(R.id.btn_del);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databasecategory = FirebaseDatabase.getInstance().getReference("Cate_Flower").child(id_category).child("Flower");
                flower.setStatus(false);
                databasecategory.child(String.valueOf(flower.getId_flower())).updateChildren(flower.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(ProductAdminActivity.this, "You have deleted this product!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }

    private void openDialogEdit(Flower flower) {
        Intent intent = getIntent();
        String id_category = intent.getStringExtra("id_category");
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_add_flower);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button btn_add = dialog.findViewById(R.id.btn_add);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        EditText txtName = dialog.findViewById(R.id.txtName);
        EditText txtDescription = dialog.findViewById(R.id.txtDescription);
        EditText txtQuantity = dialog.findViewById(R.id.txtQuantity);
        EditText txtPrice = dialog.findViewById(R.id.txtPrice);
        Button btnUpload = dialog.findViewById(R.id.btnUpload);
        imgUpload = dialog.findViewById(R.id.imgUpload);

        txtName.setText(flower.getName());
        txtDescription.setText(flower.getDescription());
        txtQuantity.setText(String.valueOf(flower.getQuantity()));
        txtPrice.setText(String.valueOf(flower.getPrice()));
        Glide.with(this).load(flower.getUrl()).into(imgUpload);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }

        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databasecategory = FirebaseDatabase.getInstance().getReference("Cate_Flower").child(id_category).child("Flower");
                String newName = txtName.getText().toString().trim();
                String description = txtDescription.getText().toString().trim();
                Float quantity = Float.valueOf(txtQuantity.getText().toString().trim());
                Float price = Float.valueOf(txtPrice.getText().toString().trim());

                StorageReference fileRef = storageReference.child(System.currentTimeMillis()+"."+ getFileExtension(imgUrl));
                fileRef.putFile(imgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(ProductAdminActivity.this, "Upload success!", Toast.LENGTH_SHORT).show();
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadPhotoUrl) {
                                flower.setName(newName);
                                flower.setDescription(description);
                                flower.setQuantity(quantity);
                                flower.setPrice(price);
                                flower.setUrl(downloadPhotoUrl.toString());

                                progressUpload.setVisibility(View.INVISIBLE);
                                Toast.makeText(ProductAdminActivity.this, "Uploaded ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        progressUpload.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressUpload.setVisibility(View.INVISIBLE);
                        Toast.makeText(ProductAdminActivity.this, "Uploading failed!", Toast.LENGTH_SHORT).show();
                    }
                });
                databasecategory.child(String.valueOf(flower.getId_flower())).updateChildren(flower.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(ProductAdminActivity.this, "You have updated successful!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show();

    }

    public void openDialogAddNewFlower(View view) {
        Intent intent = getIntent();
        String id_category = intent.getStringExtra("id_category");
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_add_flower);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button btn_add = dialog.findViewById(R.id.btn_add);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        EditText txtName = dialog.findViewById(R.id.txtName);
        EditText txtDescription = dialog.findViewById(R.id.txtDescription);
        EditText txtQuantity = dialog.findViewById(R.id.txtQuantity);
        EditText txtPrice = dialog.findViewById(R.id.txtPrice);
        Button btnUpload = dialog.findViewById(R.id.btnUpload);
        progressUpload = dialog.findViewById(R.id.progressUpload);
        imgUpload = dialog.findViewById(R.id.imgUpload);

        progressUpload.setVisibility(View.INVISIBLE);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }

        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databasecategory = FirebaseDatabase.getInstance().getReference("Cate_Flower").child(id_category).child("Flower");
                String key = databasecategory.push().getKey();
                databasecategory.child(key).removeValue();
                String newName = txtName.getText().toString().trim();
                String description = txtDescription.getText().toString().trim();
                Float quantity = Float.valueOf(txtQuantity.getText().toString().trim());
                Float price = Float.valueOf(txtPrice.getText().toString().trim());
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String created_at = dateFormat.format(date);

                databasecategory.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        StorageReference fileRef = storageReference.child(System.currentTimeMillis()+"."+ getFileExtension(imgUrl));
                        fileRef.putFile(imgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(ProductAdminActivity.this, "Upload success!", Toast.LENGTH_SHORT).show();
                                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri downloadPhotoUrl) {
                                        Flower flower = new Flower();
                                        flower.setId_flower(key);
                                        flower.setName(newName);
                                        flower.setDescription(description);
                                        flower.setQuantity(quantity);
                                        flower.setPrice(price);
                                        flower.setCreated_at(created_at);
                                        flower.setStatus(true);
                                        flower.setUrl(downloadPhotoUrl.toString());
                                        databasecategory.child(key).setValue(flower);

                                        progressUpload.setVisibility(View.INVISIBLE);
                                        Toast.makeText(ProductAdminActivity.this, "Uploaded ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                progressUpload.setVisibility(View.VISIBLE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressUpload.setVisibility(View.INVISIBLE);
                                Toast.makeText(ProductAdminActivity.this, "Uploading failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        dialog.show();
    }
    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(i, 2);
        launchSomeActivity.launch(i);
    }
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null && data.getData() != null) {
                        imgUrl = data.getData();
                        imgUpload.setImageURI(imgUrl);
                    }
                }
            });

    private String getFileExtension(Uri u)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(u));
    }

    public void revert(View view) {
        Intent intent = new Intent(this, CategoryAdminActivity.class);
        startActivity(intent);
    }

    public void loadMenu(){
        init();
        home_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ih= new Intent(ProductAdminActivity.this, HomeAdminActivity.class);
                startActivity(ih);
            }
        });
        goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ig= new Intent(ProductAdminActivity.this, CategoryAdminActivity.class);
                startActivity(ig);
            }
        });
        oder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent io= new Intent(ProductAdminActivity.this, OrderAdminActivity.class);
                startActivity(io);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iu= new Intent(ProductAdminActivity.this, UserAdminActivity.class);
                startActivity(iu);
            }
        });
    }

    public void init(){
        home_ad= findViewById(R.id.home_ad);
        goods= findViewById(R.id.goods_ad);
        oder= findViewById(R.id.oder_ad);
        user= findViewById(R.id.user_ad);
    }
}