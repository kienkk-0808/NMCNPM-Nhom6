package com.example.nhahangamthuc.ban_an;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhahangamthuc.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

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
        //banAn = (BanAn) intent.getSerializableExtra("BanAn");
        String idBanStr = intent.getStringExtra("BanAn");
        banAn = new BanAn();
        banAn.setIdBan(Long.valueOf(idBanStr));
        tvIdBan.setText("BÀN "+banAn.getIdBan().toString());

        btnDatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lauchDatBanView();
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add danh sach ban len db
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference reference = database.getReference("list_ban_an");
//                List<BanAn> list = new ArrayList<>();
//                for (int i = 0; i<6;i++){
//                    BanAn banAn = new BanAn(Long.valueOf(101L+i), 0, 4);
//                    list.add(banAn);
//                }
//
//                for (int i = 0; i < 6; i++) {
//                    BanAn banAn = new BanAn(201L + i, 0, 6);
//                    list.add(banAn);
//                }
//                for (int i = 0; i < 3; i++) {
//                    BanAn banAn = new BanAn(301L + i, 0, 6);
//                    list.add(banAn);
//                }
//                for (int i = 3; i < 6; i++) {
//                    BanAn banAn = new BanAn(301L + i, 0, 8);
//                    list.add(banAn);
//                }
//                for (int i = 0; i < list.size(); i++) {
//                    reference.child(String.valueOf(list.get(i).getIdBan())).setValue(list.get(i));
//                    //Log.d("b",banAn.toString());
//                }
            }
            });

    }

    private void lauchDatBanView() {
        Intent intent = new Intent(ChiTietBan.this, com.example.nhahangamthuc.ban_an.DatBanController.class);
        intent.putExtra("BanAn", banAn.getIdBan().toString());
        startActivity(intent);
    }

    private void mapping() {
        btnDatBan = (Button) findViewById(R.id.btn_dat_ban);
        btnThanhToan = (Button) findViewById(R.id.btn_thanh_toan);
        btnGoiMon = (Button) findViewById(R.id.btn_goi_mon);
        tvIdBan = (TextView) findViewById(R.id.tv_id_ban);

    }

}