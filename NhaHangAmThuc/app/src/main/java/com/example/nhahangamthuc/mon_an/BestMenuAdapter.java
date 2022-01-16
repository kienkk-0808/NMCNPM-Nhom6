package com.example.nhahangamthuc.mon_an;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhahangamthuc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class BestMenuAdapter extends RecyclerView.Adapter<BestMenuAdapter.BestMenuHolder>{
    private Context context;
    public ArrayList<MonAn> mListMonAn;

    public BestMenuAdapter(Context context, ArrayList<MonAn> mListMonAn){
        this.context = context;
        this.mListMonAn = mListMonAn;
    }

    @NonNull
    @Override
    public BestMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monanmenu,parent,false);
        return new BestMenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestMenuHolder holder, int position) {
        MonAn monAn = mListMonAn.get(position);
        String tenmon = monAn.getTenmonan();
        String kieumon = monAn.getKieumonan();
        Long giatien = monAn.getGiatien();

        holder.tenmonan_txt.setText(tenmon);
        holder.kieumonan_txt.setText(kieumon);
        holder.giatien_txt.setText(String.valueOf(giatien));

        Glide.with(holder.hinhanh.getContext()).load(monAn.getUrl()).into(holder.hinhanh);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("Xóa món ăn")
                        .setMessage("Bạn có chắc chắn muốn xóa món ăn này không?")
                        .setPositiveButton("Xóa", (dialog1, which1) -> deleteMonAn(monAn, holder))
                        .setNegativeButton("Hủy", null).show();
            }
        });
    }

    private void deleteMonAn(MonAn monAn, BestMenuHolder holder) {
        DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Danh_sach_mon_an_tot_nhat");
        refer.child(""+monAn.getId())
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Đã xóa trong menu tốt nhất ngày!",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Xóa THẤT BẠI trong menu tốt nhất ngày!",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mListMonAn.size();
    }

    class BestMenuHolder extends RecyclerView.ViewHolder {
        TextView tenmonan_txt, kieumonan_txt, giatien_txt;
        ImageView hinhanh;
        ImageButton deleteBtn;

        public BestMenuHolder(@NonNull View itemView) {
            super(itemView);
            tenmonan_txt = itemView.findViewById(R.id.menu_name);
            kieumonan_txt = itemView.findViewById(R.id.menu_type);
            giatien_txt = itemView.findViewById(R.id.menu_gia);
            hinhanh =(ImageView) itemView.findViewById(R.id.menu_image);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
