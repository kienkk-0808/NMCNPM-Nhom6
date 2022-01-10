package com.example.nhahangamthuc.ban_an;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhahangamthuc.R;

public class DatBanController extends AppCompatActivity {
    private TextView tvIdBan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_ban);
        mapping();
        Intent intent = getIntent();
        BanAn banAn = (BanAn) intent.getSerializableExtra("BanAn");
        tvIdBan.setText("ĐẶT BÀN "+banAn.getIdBan().toString());
    }

    private void mapping() {
//        btnDatBan = (Button) findViewById(R.id.btn_dat_ban);
//        btnThanhToan = (Button) findViewById(R.id.btn_thanh_toan);
//        btnGoiMon = (Button) findViewById(R.id.btn_goi_mon);
        tvIdBan = (TextView) findViewById(R.id.tv_id_dat_ban);
    }
}