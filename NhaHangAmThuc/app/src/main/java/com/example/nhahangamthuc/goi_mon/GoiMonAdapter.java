package com.example.nhahangamthuc.goi_mon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.don_hang.DonHang;
import com.example.nhahangamthuc.mon_an.MonAn;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GoiMonAdapter extends RecyclerView.Adapter<GoiMonAdapter.GoiMonViewHolder>{

    private List<MonAn> mListMonAn;
    private DonHang donHang;

    public GoiMonAdapter(List<MonAn> mListMonAn, DonHang donHang) {
        this.mListMonAn = mListMonAn;
        this.donHang = donHang;
    }

    @NonNull
    @Override
    public GoiMonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monan_chitietban,parent,false);
        return new GoiMonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoiMonViewHolder holder, int position) {
        MonAn monAn = mListMonAn.get(position);
        String tenmon = monAn.getTenmonan();
        String kieumon = monAn.getKieumonan();
        Long giatien = monAn.getGiatien();
        List<MonAn> monTrongDon = donHang.getMonAnList();

        AtomicInteger count = new AtomicInteger(0);
        holder.btnCong.setOnClickListener(v -> {
            count.getAndIncrement();
            holder.soLuong.setText(count.toString());
            monTrongDon.add(monAn);
            if (count.get() > 0) { holder.btnTru.setEnabled(true); }
        });

        if (count.get() == 0) { holder.btnTru.setEnabled(false); }

        holder.btnTru.setOnClickListener(v -> {
            count.getAndDecrement();
            holder.soLuong.setText(count.toString());
            monTrongDon.remove(monAn);
            if (count.get() == 0) { holder.btnTru.setEnabled(false); }
        });

        holder.tenmonan_txt.setText(tenmon);
        holder.kieumonan_txt.setText(kieumon);
        holder.giatien_txt.setText(String.valueOf(giatien));

        Glide.with(holder.hinhanh.getContext()).load(monAn.getUrl()).into(holder.hinhanh);

    }

    @Override
    public int getItemCount() {
        return mListMonAn.size();
    }


    class GoiMonViewHolder extends RecyclerView.ViewHolder {

        TextView tenmonan_txt, kieumonan_txt, giatien_txt, soLuong;
        ImageView hinhanh;
        ImageButton btnCong, btnTru;

        GoiMonViewHolder(@NonNull View itemView) {
            super(itemView);
            tenmonan_txt = itemView.findViewById(R.id.tv_ten_mon);
            kieumonan_txt = itemView.findViewById(R.id.tv_kieu_mon);
            giatien_txt = itemView.findViewById(R.id.tv_gia);
            hinhanh =itemView.findViewById(R.id.civ_mon_an);
            soLuong = itemView.findViewById(R.id.tv_so_luong);
            btnCong = itemView.findViewById(R.id.imgb_them_mon);
            btnTru = itemView.findViewById(R.id.imgb_bot_mon);
        }
    }
}
