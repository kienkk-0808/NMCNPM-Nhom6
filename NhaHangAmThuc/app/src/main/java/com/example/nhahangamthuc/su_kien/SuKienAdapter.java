package com.example.nhahangamthuc.su_kien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.utils.NgayThangUtils;

import java.util.List;

public class SuKienAdapter extends RecyclerView .Adapter<SuKienAdapter.SuKienViewHolder> {
    private final List<SuKien> suKienList;
    private final IItemClick iItemClick;

    public interface IItemClick {
        void onUpdateClick(SuKien suKien);
        void onDeleteClick(SuKien suKien);
    }

    public SuKienAdapter(List<SuKien> suKienList, IItemClick iItemClick) {
        this.suKienList = suKienList;
        this.iItemClick = iItemClick;
    }

    @NonNull
    @Override
    public SuKienAdapter.SuKienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_su_kien, parent, false);
        return new SuKienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuKienAdapter.SuKienViewHolder holder, int position) {
        SuKien suKien = suKienList.get(position);
        if (suKien == null) return;
        holder.textViewTenSuKien.setText(suKien.getTen());
        holder.textViewNgaySuKien.setText(NgayThangUtils.hienThiNgayThang(suKien.getNgayThang()));
        holder.textViewMonTangKem.setText(suKien.getMonTangKem());
        holder.imageButtonSuaSuKien.setOnClickListener(v -> {
            iItemClick.onUpdateClick(suKien);
        });
        holder.imageButtonXoaSuKien.setOnClickListener(v -> {
            iItemClick.onDeleteClick(suKien);
        });
    }

    @Override
    public int getItemCount() {
        if (suKienList != null) {
            return suKienList.size();
        }
        return 0;
    }

    public static class SuKienViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTenSuKien;
        TextView textViewNgaySuKien;
        TextView textViewMonTangKem;
        ImageButton imageButtonSuaSuKien;
        ImageButton imageButtonXoaSuKien;

        public SuKienViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTenSuKien = itemView.findViewById(R.id.text_view_ten_su_kien);
            textViewNgaySuKien = itemView.findViewById(R.id.text_view_ngay_su_kien);
            textViewMonTangKem = itemView.findViewById(R.id.text_view_mon_tang_kem);
            imageButtonSuaSuKien = itemView.findViewById(R.id.image_button_sua_su_kien);
            imageButtonXoaSuKien = itemView.findViewById(R.id.image_button_xoa_su_kien);
        }
    }
}
