package com.example.nhahangamthuc.mon_an;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
    ImageButton imageButton;

    int REQUEST_CODE_CAMERA = 123;

    private Uri uri;

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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickAddMonAn();
            }
        });

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

        imageButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType ("image/*");
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });
    }

    private void OnClickAddMonAn() {
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
                        Toast.makeText(AddMonAn.this,"PDF upload failed due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadInfotoDb(String uploadedUrl, Long timestamp) {
        HashMap<String, Object> hashMap = new HashMap<> ();
        hashMap.put("id" , timestamp);
        hashMap.put("tenmonan", tenmonan.getText().toString().trim());
        hashMap.put("kieumonan", kieumonan.getText().toString().trim());
        hashMap.put("giatien", Long.valueOf(giatien.getText().toString().trim()));
        hashMap.put("url", uploadedUrl);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Danh_sach_mon_an");
        ref.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddMonAn.this,"Successfully uploaded...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
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
        super.onActivityResult(requestCode, resultCode, data);
    }
}