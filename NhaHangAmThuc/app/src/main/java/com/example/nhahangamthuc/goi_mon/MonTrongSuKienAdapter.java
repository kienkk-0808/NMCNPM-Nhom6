package com.example.nhahangamthuc.goi_mon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.don_hang.DonHang;
import com.example.nhahangamthuc.mon_an.MonAn;

import java.util.List;

public class MonTrongSuKienAdapter extends RecyclerView.Adapter<MonTrongSuKienAdapter.MonTrongSuKienViewHolder>{

    private List<MonAn> monAnList;
    private DonHang donHang;

    public MonTrongSuKienAdapter(List<MonAn> monAnList, DonHang donHang) {
        this.monAnList = monAnList;
        this.donHang = donHang;
    }

    @NonNull
    @Override
    public MonTrongSuKienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mon_trong_su_kien,parent,false);
        return new MonTrongSuKienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonTrongSuKienViewHolder holder, int position) {
        MonAn monAn = monAnList.get(position);

        holder.checkBox.setText(monAn.getTenmonan());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            monAn.setChecked(isChecked);
            if (isChecked) {
                monAn.setGiatien(0L);
                donHang.getMonAnList().add(monAn);
            }
        });

    }

    @Override
    public int getItemCount() {
        return monAnList.size();
    }


    class MonTrongSuKienViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        MonTrongSuKienViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox_mon_trong_su_kien);
        }
    }
}
