package com.example.nhahangamthuc.mon_an;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhahangamthuc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class AddMonAn extends AppCompatActivity {

    EditText tenmonan, giatien;
    TextView kieumonan;
    Button add;
    ImageView hinhanh_input;
    ImageButton imageButton, imageButtonback;

    int REQUEST_CODE_CAMERA = 123;

    private ProgressDialog progressDialog;

    private Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mon_an);

        tenmonan = findViewById(R.id.tenmonan_input);
        giatien = findViewById(R.id.giatien_input);
        kieumonan = findViewById(R.id.kieumonan_input);
        add = findViewById(R.id.add_button);
        hinhanh_input = findViewById(R.id.hinhanh_input);
        imageButton = findViewById(R.id.imageButton1);
        imageButtonback = findViewById(R.id.imageButtonback);

        progressDialog = new ProgressDialog(AddMonAn.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        imageButtonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Thêm dữ liệu vào database
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickAddMonAn();
            }
        });

        // Chọn kiểu món ăn
        kieumonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] kieumonanArray = new String[5];
                kieumonanArray[0] = "Khai vị";
                kieumonanArray[1] = "Món chính";
                kieumonanArray[2] = "Món phụ ăn kèm";
                kieumonanArray[3] = "Món tráng miệng";
                kieumonanArray[4] = "Đồ uống";

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Chọn kiểu món ăn")
                        .setItems(kieumonanArray, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String tenkieumonan = kieumonanArray[which];
                                kieumonan.setText(tenkieumonan);
                            }
                        })
                        .show();
            }
        });

        //Lấy hình ảnh từ thư viện
        imageButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType ("image/*");
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });
    }

    private String ten_mon_an = "", kieu_mon_an = "", gia_tien = "";

    private void OnClickAddMonAn() {
        //Step 1: Validate data

        ten_mon_an = tenmonan.getText().toString().trim();
        kieu_mon_an = kieumonan.getText().toString().trim();
        gia_tien = giatien.getText().toString().trim();

        if(TextUtils.isEmpty(ten_mon_an)){
            Toast.makeText(this,"Nhập tên món ăn...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(kieu_mon_an)){
            Toast.makeText(this,"Chọn kiểu món ăn...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(gia_tien)){
            Toast.makeText(this,"Nhập giá tiền...", Toast.LENGTH_SHORT).show();
        }
        else if(uri == null){
            Toast.makeText(this,"Chọn hình ảnh...", Toast.LENGTH_SHORT).show();
        }
        else{
            uploadimagetoStorage();
        }
    }

    private void uploadimagetoStorage() {
        //Step 2: Đẩy hình ảnh lên database
        progressDialog.setMessage("Đang tải hình ảnh lên...");
        progressDialog.show();

        Long timestamp =  System.currentTimeMillis();
        String filePathAndName = "Food/" + timestamp;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        String uploadedUrl = ""+uriTask.getResult();

                        uploadInfotoDb(uploadedUrl, timestamp);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddMonAn.this,"Image upload failed due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadInfotoDb(String uploadedUrl, Long timestamp) {
        progressDialog.setMessage("Đang tải thông tin lên...");
        progressDialog.show();

        HashMap<String, Object> hashMap = new HashMap<> ();
        hashMap.put("id" , timestamp);
        hashMap.put("tenmonan", ten_mon_an);
        hashMap.put("kieumonan", kieu_mon_an);
        hashMap.put("giatien", Long.valueOf(gia_tien));
        hashMap.put("url", uploadedUrl);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Danh_sach_mon_an");
        ref.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(AddMonAn.this,"Successfully uploaded...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AddMonAn.this,"Failed to upload to db due to" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            try {
                InputStream inpuStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inpuStream);
                hinhanh_input.setImageBitmap(bitmap);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(this,"Cancellled picking image",Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}