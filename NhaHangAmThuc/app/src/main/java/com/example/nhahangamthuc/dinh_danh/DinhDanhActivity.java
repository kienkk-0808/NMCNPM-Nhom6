package com.example.nhahangamthuc.dinh_danh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DinhDanhActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinh_danh);

        findViewById(R.id.button_nhan_vien).setOnClickListener(v -> {
            startActivity(new Intent(this, NhanVienActivity.class));
        });


        findViewById(R.id.button_quan_ly).setOnClickListener(v -> {
            startActivity(new Intent(this, QuanLyActivity.class));
        });
    }
}