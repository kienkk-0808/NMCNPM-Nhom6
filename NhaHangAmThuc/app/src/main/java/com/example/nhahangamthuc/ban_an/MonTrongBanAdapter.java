package com.example.nhahangamthuc.ban_an;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.mon_an.MonAn;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MonTrongBanAdapter extends RecyclerView.Adapter<MonTrongBanAdapter.MyViewHolder> {
    private Context context;
    private List<MonAn> mListMonAn;
    private String idBanStr;
    public MonTrongBanAdapter(Context context, String idBanStr) {
        this.context = context;
        this.idBanStr = idBanStr;
    }

    public void setData(List<MonAn> list) {
        mListMonAn = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monan_chitietban, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final int pos = position;
        MonAn monAn = mListMonAn.get(position);
        String tenmon = monAn.getTenmonan();
        String kieumon = monAn.getKieumonan();
        Long giatien = monAn.getGiatien();
        int soluong = monAn.getSoLuong();
        holder.tenmonan_txt.setText(tenmon);
        holder.kieumonan_txt.setText(kieumon);
        holder.giatien_txt.setText(String.valueOf(giatien));
        holder.soLuong_txt.setText(String.valueOf(soluong));

        Glide.with(holder.hinhanh.getContext()).load(monAn.getUrl()).into(holder.hinhanh);

        holder.cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monAn.setSoLuong(monAn.getSoLuong() + 1);
                holder.soLuong_txt.setText(String.valueOf(monAn.getSoLuong()));
                updateDB(pos, mListMonAn);
            }
        });
        holder.tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monAn.setSoLuong(monAn.getSoLuong() - 1);
                if (monAn.getSoLuong() < 1) {
                    mListMonAn.remove(pos);
                    notifyDataSetChanged();
                }
                else
                    holder.soLuong_txt.setText(String.valueOf(monAn.getSoLuong()));
                updateDB(pos, mListMonAn);

            }
        });
    }


    @Override
    public int getItemCount() {
        return mListMonAn.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tenmonan_txt, kieumonan_txt, giatien_txt, soLuong_txt;
        ImageView hinhanh;
        ImageButton cong, tru;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tenmonan_txt = itemView.findViewById(R.id.tv_ten_mon);
            kieumonan_txt = itemView.findViewById(R.id.tv_kieu_mon);
            giatien_txt = itemView.findViewById(R.id.tv_gia);
            hinhanh = (ImageView) itemView.findViewById(R.id.civ_mon_an);
            cong = itemView.findViewById(R.id.imgb_them_mon);
            tru = itemView.findViewById(R.id.imgb_bot_mon);
            soLuong_txt = itemView.findViewById(R.id.tv_so_luong);
        }
    }
    private void updateDB(int position, List<MonAn> list){
        DatabaseReference ref = FirebaseDatabase.getInstance().
                getReference("list_ban_an").child(idBanStr).
                child("danhSachMon");
        ref.setValue(list);
    }
}
