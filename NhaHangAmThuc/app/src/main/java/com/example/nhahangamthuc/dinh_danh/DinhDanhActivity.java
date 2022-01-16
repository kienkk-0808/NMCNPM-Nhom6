package com.example.nhahangamthuc.dinh_danh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.nhahangamthuc.R;

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