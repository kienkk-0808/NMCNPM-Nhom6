package com.example.nhahangamthuc.su_kien;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SuKienAdapter extends RecyclerView .Adapter<SuKienAdapter.SuKienViewHolder> {
    private List<SuKien> suKienList;
    private IItemClick iItemClick;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull SuKienAdapter.SuKienViewHolder holder, int position) {
        SuKien suKien = suKienList.get(position);
        if (suKien == null) return;
        holder.textViewTenSuKien.setText(suKien.getTen());
        LocalDate startDate = LocalDate.parse(suKien.getStartDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate endDate = LocalDate.parse(suKien.getEndDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        holder.textViewNgayBatDau.setText("Ngày bắt đầu: " + suKien.getStartDate());
        holder.textViewNgayKetThuc.setText("Ngày kết thúc: " + suKien.getEndDate());
        LocalDate currentDate = LocalDate.now();
        if (currentDate.isBefore(startDate)) {
            holder.textViewTrangThai.setText("Chưa diễn ra");
            holder.textViewTrangThai.setTextColor(Color.YELLOW);
        }
        if (currentDate.isAfter(endDate)) {
            holder.textViewTrangThai.setText("Đã trôi qua");
            holder.textViewTrangThai.setTextColor(Color.RED);
        }

        holder.textViewMonTangKem.setText("Các món được tặng: " + suKien.getMonTangKem());

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
        TextView textViewTrangThai;
        TextView textViewNgayBatDau;
        TextView textViewNgayKetThuc;
        TextView textViewMonTangKem;
        ImageButton imageButtonSuaSuKien;
        ImageButton imageButtonXoaSuKien;

        public SuKienViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTenSuKien = itemView.findViewById(R.id.text_view_ten_su_kien);
            textViewTrangThai = itemView.findViewById(R.id.text_view_trang_thai);
            textViewNgayBatDau = itemView.findViewById(R.id.text_view_ngay_bat_dau);
            textViewNgayKetThuc = itemView.findViewById(R.id.text_view_ngay_ket_thuc);
            textViewMonTangKem = itemView.findViewById(R.id.text_view_mon_tang_kem);
            imageButtonSuaSuKien = itemView.findViewById(R.id.image_button_sua_su_kien);
            imageButtonXoaSuKien = itemView.findViewById(R.id.image_button_xoa_su_kien);
        }
    }
}
