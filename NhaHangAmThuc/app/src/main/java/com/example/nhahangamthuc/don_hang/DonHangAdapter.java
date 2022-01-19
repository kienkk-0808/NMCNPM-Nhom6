package com.example.nhahangamthuc.don_hang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.mon_an.MonAn;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.DonHangViewHolder>{

    List<MonAn> monAnList;

    public DonHangAdapter(List<MonAn> monAnList) {
        this.monAnList = monAnList;
    }

    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_don_hang, parent, false);
        return new DonHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangViewHolder holder, int position) {
        MonAn monAn = monAnList.get(position);
        holder.tenMon.setText(monAn.getTenmonan());
        holder.giaTien.setText(monAn.getGiatien() + " VNĐ");
    }

    @Override
    public int getItemCount() {
        if (monAnList != null) return monAnList.size();
        return 0;
    }

    class DonHangViewHolder extends RecyclerView.ViewHolder {

        TextView tenMon, giaTien;

        public DonHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tenMon = itemView.findViewById(R.id.txv_ten_mon);
            giaTien = itemView.findViewById(R.id.txv_gia_tien);
        }
    }

}
