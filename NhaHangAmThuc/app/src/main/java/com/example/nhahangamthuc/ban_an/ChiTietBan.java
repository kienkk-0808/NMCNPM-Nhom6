package com.example.nhahangamthuc.ban_an;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.goi_mon.GoiMonActivity;
import com.example.nhahangamthuc.mon_an.MonAn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ChiTietBan extends AppCompatActivity {
    private BanAn banAn;
    private Button btnDatBan;
    private Button btnGoiMon;
    private TextView tvIdBan, tvDsMon, tvDangAn;
    private RecyclerView rcvDsMon, rcvDsDatBan;
    private DatBanInfoAdapter datBanInfoAdapter;
    private List<DatBan> listDatBan;
    private MonTrongBanAdapter monTrongBanAdapter;
    private List<MonAn> listMonAn;
    private DatabaseReference listBanRef = FirebaseDatabase.getInstance().
            getReference("list_ban_an");
    private DsBanService dsBanService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ban);
        mapping();

        Intent intent = getIntent();
        String idBanStr = intent.getStringExtra("BanAn");
        dsBanService = new DsBanService();
        //set recycle view ds mon
        listMonAn = new ArrayList<>();
        monTrongBanAdapter = new MonTrongBanAdapter(this, idBanStr);
        monTrongBanAdapter.setData(listMonAn);
        rcvDsMon.setLayoutManager(new LinearLayoutManager(this));
        rcvDsMon.setAdapter(monTrongBanAdapter);

        //set recycle view ds dat ban
        listDatBan = new ArrayList<>();
        datBanInfoAdapter = new DatBanInfoAdapter(this, Long.valueOf(idBanStr));
        datBanInfoAdapter.setData(listDatBan);
        rcvDsDatBan.setLayoutManager(new LinearLayoutManager(this));
        rcvDsDatBan.setAdapter(datBanInfoAdapter);

        //get thong tin ban an
        listBanRef.child(idBanStr).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                banAn = snapshot.getValue(BanAn.class);
                xuly();
                datBanInfoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChiTietBan.this, "Loi khi lay thong tin ban",
                        Toast.LENGTH_LONG).show();
            }
        });
        btnDatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lauchDatBanView();
            }
        });
        btnGoiMon.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, GoiMonActivity.class);
            intent1.putExtra("id ban", String.valueOf(banAn.getIdBan()));
            startActivity(intent1);
        });
    }

    private void xuly() {
        if (banAn ==null)
            return;
        tvIdBan.setText("BÀN " + banAn.getIdBan().toString());

        //xu ly: nguoi dang an - ds mon
        tvDsMon.setVisibility(View.VISIBLE);
        tvDangAn.setVisibility(View.VISIBLE);
        tvDsMon.setText("");
        tvDangAn.setText("");
        if (banAn.getDangAn() != null) {
            String tt = "Thời gian: " + banAn.getDangAn().getThoiGian();
            tvDangAn.setText(tt);
            if (banAn.getDanhSachMon() != null) {
                tvDsMon.setText("Danh sách món ăn");
                setListMon(banAn.getDanhSachMon());
                monTrongBanAdapter.notifyDataSetChanged();
                //monTrongBanAdapter.setData(banAn.getDanhSachMon());
            } else {
                tvDsMon.setVisibility(View.GONE);
            }
        } else {
            tvDangAn.setVisibility(View.GONE);
        }

        //xu ly ds ban
        if (banAn.getDanhSachDat() != null) {
            updateDatBanInfo();
            setListDatBan(banAn.getDanhSachDat());
        }
    }

    private void updateDatBanInfo() {
        List<DatBan> list = new ArrayList<>(banAn.getDanhSachDat());
        for (DatBan datBan : banAn.getDanhSachDat()){
            Timestamp t = new Timestamp(0L);
            t.setTime(Timestamp.valueOf(datBan.getThoiGian()).getTime() +
                    DsBanService.millisPH/2+1000);//t la 30p1s sau thoi gian dat
            if (t.before(new Timestamp(System.currentTimeMillis())))
                list.remove(datBan);
        }
        banAn.setDanhSachDat(list);
        listBanRef.child(banAn.getIdBan().toString()).setValue(banAn);
    }

    private void lauchDatBanView() {
        Intent intent = new Intent(ChiTietBan.this, com.example.nhahangamthuc.ban_an.DatBanController.class);
        intent.putExtra("BanAn", banAn.getIdBan().toString());
        startActivity(intent);
    }

    private void setListMon(List<MonAn> list) {
        listMonAn.clear();
        for (MonAn monAn : list)
            listMonAn.add(monAn);
    }

    private void setListDatBan(List<DatBan> list) {
        listDatBan.clear();
        for (DatBan datBan : list)
            listDatBan.add(datBan);
    }

    private void mapping() {
        btnDatBan = (Button) findViewById(R.id.btn_dat_ban);
        btnGoiMon = (Button) findViewById(R.id.btn_goi_mon);
        tvIdBan = (TextView) findViewById(R.id.tv_id_ban);
        tvDsMon = (TextView) findViewById(R.id.tv_dsmon);
        rcvDsMon = (RecyclerView) findViewById(R.id.rcv_dsmon_chitietban);
        tvDangAn = (TextView) findViewById(R.id.tv_dang_an);
        rcvDsDatBan = (RecyclerView) findViewById(R.id.rcv_ds_dat_ban);
    }

}