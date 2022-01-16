package com.example.nhahangamthuc.dinh_danh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.hoi_vien.HoiVien;

public class DinhDanhActivity extends AppCompatActivity {

    private final String MA_NHAN_VIEN = "nhanvien999";
    private final String MA_QUAN_LY = "quanly999";
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinh_danh);
        context = this;

        findViewById(R.id.button_nhan_vien).setOnClickListener(v -> {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.popup_dialog_dinh_danh);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            window.setAttributes(layoutParams);
            dialog.setCancelable(true);
            dialog.show();

            dialog.findViewById(R.id.btn_xac_nhan).setOnClickListener(v1 -> {
                EditText editTextMaDinhDanh = dialog.findViewById(R.id.edt_ma_dinh_danh);
                String code = editTextMaDinhDanh.getText().toString();
                if (code.equals(MA_NHAN_VIEN)) {
                    dialog.dismiss();
                    startActivity(new Intent(context, NhanVienActivity.class));
                }
                else {
                    Toast.makeText(context, "Mã định danh không chính xác!", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.findViewById(R.id.btn_huy).setOnClickListener(v2 -> dialog.dismiss());
        });


        findViewById(R.id.button_quan_ly).setOnClickListener(v -> {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.popup_dialog_dinh_danh);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            window.setAttributes(layoutParams);
            dialog.setCancelable(true);
            dialog.show();

            dialog.findViewById(R.id.btn_xac_nhan).setOnClickListener(v1 -> {
                EditText editTextMaDinhDanh = dialog.findViewById(R.id.edt_ma_dinh_danh);
                String code = editTextMaDinhDanh.getText().toString();
                if (code.equals(MA_QUAN_LY)) {
                    dialog.dismiss();
                    startActivity(new Intent(context, QuanLyActivity.class));
                }
                else {
                    Toast.makeText(context, "Mã định danh không chính xác!", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.findViewById(R.id.btn_huy).setOnClickListener(v2 -> dialog.dismiss());
        });
    }
}