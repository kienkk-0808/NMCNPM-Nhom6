package com.example.nhahangamthuc.dinh_danh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.User;
import com.example.nhahangamthuc.ban_an.BanAn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DinhDanhActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinh_danh);

        //
       // init();

        findViewById(R.id.button_nhan_vien).setOnClickListener(v -> {
            startActivity(new Intent(this, NhanVienActivity.class));
        });


        findViewById(R.id.button_quan_ly).setOnClickListener(v -> {
            startActivity(new Intent(this, QuanLyActivity.class));
        });

        TextView textView = findViewById(R.id.tvt_test);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("user");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               // User user = snapshot.getValue(User.class);
                //textView.setText(user.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void init(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("list_ban_an");

        List<BanAn> list = new ArrayList<>();
        for (int i=1;i<4;i++){
            BanAn banAn = new BanAn();
            banAn.setIdBan(100L+i);
            banAn.setTrangThai(0);
            banAn.setSoNguoi(4);
            list.add(banAn);
        }
        for (int i=1;i<7;i++){
            BanAn banAn = new BanAn();
            banAn.setIdBan(200L+i);
            banAn.setTrangThai(0);
            banAn.setSoNguoi(6);
            list.add(banAn);
        }
        for (int i=1;i<7;i++){
            BanAn banAn = new BanAn();
            banAn.setIdBan(300L+i);
            banAn.setTrangThai(1);
            banAn.setSoNguoi(8);
            list.add(banAn);
        }

        reference.setValue(list, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(DinhDanhActivity.this, "aa", Toast.LENGTH_SHORT).show();
            }
        });
    }
}