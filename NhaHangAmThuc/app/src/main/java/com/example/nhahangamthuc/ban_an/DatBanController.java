package com.example.nhahangamthuc.ban_an;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhahangamthuc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DatBanController extends AppCompatActivity {
    private TextView tvIdBan;
    private Spinner spinnerNgay;
    private Spinner spinnerGio;
    private Button btnDatBan;
    private EditText edtTen, edtSdt, edtSonguoi;
    private String ngay;
    private String gio;
    private BanAn banAn;
    private DatabaseReference listBanRef = FirebaseDatabase.getInstance().
            getReference("list_ban_an");
    private DsBanService dsBanService;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_ban);
        mapping();
        Intent intent = getIntent();
        String idBanStr = intent.getStringExtra("BanAn");
        dsBanService = new DsBanService();
        listBanRef.child(idBanStr).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                banAn = snapshot.getValue(BanAn.class);
                xuLy();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DatBanController.this, "Loi khi lay thong tin ban",
                        Toast.LENGTH_LONG).show();
            }
        });
        //this.banAn = banAnDAO.getBanById(Long.valueOf(idBanStr));

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void xuLy(){
        tvIdBan.setText("ĐẶT BÀN " + banAn.getIdBan().toString());

        List<String> listNgay;
        //spinner ngay
        listNgay = dsBanService.getListNgay();
        SpinnerAdapter spnNgayAdapter = new SpinnerAdapter(DatBanController.this,
                R.layout.spinner_item_selected, listNgay);

        spinnerNgay.setAdapter(spnNgayAdapter);
        //Bắt sự kiện cho Spinner, khi chọn phần tử nào thì hiển thị lên Toast
        spinnerNgay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ngay = listNgay.get(position);
                //Toast.makeText(DatBanController.this, "aa", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(DatBanController.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });

        //spinner gio
        List<String> listGio = dsBanService.getListGio();
        SpinnerAdapter spnGioAdaper = new SpinnerAdapter(DatBanController.this,
                R.layout.spinner_item_selected, listGio);
        spinnerGio.setAdapter(spnGioAdaper);
        //Bắt sự kiện cho Spinner, khi chọn phần tử nào thì hiển thị lên Toast
        spinnerGio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String msg = "position :" + position + " value :" + list.get(position);
                //Toast.makeText(DatBanController.this, "bb", Toast.LENGTH_SHORT).show();
                gio = listGio.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(DatBanController.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
        //save dat ban
        btnDatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datBan();
            }

        });
    }
    private void datBan() {
        Long idDatBan = 0L;
        String ten = edtTen.getText().toString();
        String sdt = edtSdt.getText().toString();
        if (ten.length() < 1 || sdt.length() < 1 || edtSonguoi.getText().toString().length() < 1) {
            Toast.makeText(DatBanController.this, "Vui lòng nhập đủ thông tin",
                    Toast.LENGTH_LONG).show();
            return;
        }
        int soNguoi = Integer.parseInt(edtSonguoi.getText().toString().trim());
        Timestamp thoiGian = Timestamp.valueOf(ngay + " " + gio + ":00");
        if (thoiGian != null) {
            //kiem tra xem co ban nao da dat vao gio nay chua
            List<DatBan> dsDatBan = banAn.getDanhSachDat();
           // Log.d("a", banAn.toString());
            if (dsDatBan != null) {
                Timestamp t1 = new Timestamp(0L);
                Timestamp t2 = new Timestamp(0L);
                for (int i = 0; i < dsDatBan.size(); i++) {
                    Timestamp t = Timestamp.valueOf(dsDatBan.get(i).getThoiGian());
                    t1.setTime(t.getTime() - DsBanService.millisPH*2);//t1 la truoc 2h
                    t2.setTime(t.getTime() + DsBanService.millisPH*2);//t2 la sau 2h
                    if (checkDatBan(thoiGian, t1, t2) == false) {
                        //Da co nguoi dat -> Khong dat duoc ban vao luc "thoiGian"
                        return;
                    }

                }
                idDatBan = (long) dsDatBan.size();
            }
            if (banAn.getDangAn() != null) {
                //dang an
                DatBan dangAn = banAn.getDangAn();
                if (dangAn != null) {
                    Timestamp t1 = Timestamp.valueOf(dangAn.getThoiGian());
                    Timestamp t2 = new Timestamp(0L);
                    t2.setTime(t1.getTime() + DsBanService.millisPH * 2);//t2 la sau t1 2h
                    if (checkDatBan(thoiGian, t1, t2) == false) {
                        //Da co nguoi dat -> Khong dat duoc ban vao luc "thoiGian"
                        return;
                    }
                }
            }
            //ok -> đặt bàn
            if (dsDatBan == null)
                dsDatBan = new ArrayList<>();
            DatBan datBan = new DatBan(idDatBan, ten, sdt, soNguoi, thoiGian.toString());
            dsDatBan.add(datBan);
            banAn.setDanhSachDat(dsDatBan);
            insertDatBanInfo(dsDatBan);
        } else {
            Toast.makeText(DatBanController.this, "Error: Convert Date Time",
                    Toast.LENGTH_LONG).show();
        }
    }



    private boolean checkDatBan(Timestamp thoiGian, Timestamp t1, Timestamp t2) {
        if (thoiGian.after(t1) && thoiGian.before(t2)) {
            //Da co nguoi dat -> Khong dat duoc ban vao luc "thoiGian"
            Toast.makeText(DatBanController.this, "Bàn đã có người đặt vào lúc này!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void insertDatBanInfo(List<DatBan> dsDatBan) {
        listBanRef.child(String.valueOf(banAn.getIdBan())).child("danhSachDat").
                setValue(dsDatBan, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(DatBanController.this, "Đặt bàn thành công",
                                Toast.LENGTH_LONG).show();
                        //finish();
                    }
                });
    }

    private void mapping() {
        tvIdBan = (TextView) findViewById(R.id.tv_id_dat_ban);
        spinnerNgay = (Spinner) findViewById(R.id.spn_ngay);
        spinnerGio = (Spinner) findViewById(R.id.spn_gio);
        btnDatBan = (Button) findViewById(R.id.btn_save_dat_ban);
        edtTen = (EditText) findViewById(R.id.edt_ten);
        edtSdt = (EditText) findViewById(R.id.edt_sdt);
        edtSonguoi = (EditText) findViewById(R.id.edt_so_nguoi);
    }
}