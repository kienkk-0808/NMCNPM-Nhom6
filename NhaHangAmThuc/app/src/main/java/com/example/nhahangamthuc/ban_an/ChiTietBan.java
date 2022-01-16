package com.example.nhahangamthuc.ban_an;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhahangamthuc.R;

public class ChiTietBan extends AppCompatActivity {
    private BanAn banAn;
    private Button btnDatBan;
    private Button btnThanhToan;
    private Button btnGoiMon;
    private TextView tvIdBan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ban);
        mapping();

        Intent intent = getIntent();
        banAn = (BanAn) intent.getSerializableExtra("BanAn");
        tvIdBan.setText("BÀN "+banAn.getIdBan().toString());

        btnDatBan.setOnClickListener(v -> lauchDatBanView());
    }

    private void lauchDatBanView() {
        Intent intent = new Intent(ChiTietBan.this, com.example.nhahangamthuc.ban_an.DatBanController.class);
        intent.putExtra("BanAn", banAn);
        startActivity(intent);
    }

    private void mapping() {
        btnDatBan = (Button) findViewById(R.id.btn_dat_ban);
        btnThanhToan = (Button) findViewById(R.id.btn_thanh_toan);
        btnGoiMon = (Button) findViewById(R.id.btn_goi_mon);
        tvIdBan = (TextView) findViewById(R.id.tv_id_ban);
    }

}