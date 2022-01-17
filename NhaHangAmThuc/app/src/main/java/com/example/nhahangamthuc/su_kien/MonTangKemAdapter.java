package com.example.nhahangamthuc.su_kien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.mon_an.MonAn;

import java.util.List;

public class MonTangKemAdapter extends RecyclerView.Adapter<MonTangKemAdapter.MonTangKemViewHolder>{

    private List<MonAn> monAnList;

    public MonTangKemAdapter(List<MonAn> monAnList) {
        this.monAnList = monAnList;
    }

    @NonNull
    @Override
    public MonTangKemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mon_tang_kem, parent, false);
        return new MonTangKemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonTangKemViewHolder holder, int position) {
        MonAn monAn = monAnList.get(position);
        holder.textViewTenMonAn.setText(monAn.getTenmonan());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> monAn.setChecked(isChecked));
    }

    @Override
    public int getItemCount() {
        if (monAnList != null) return monAnList.size();
        return 0;
    }

    public static class MonTangKemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTenMonAn;
        CheckBox checkBox;

        public MonTangKemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTenMonAn = itemView.findViewById(R.id.text_view_ten_mon_an);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

}
