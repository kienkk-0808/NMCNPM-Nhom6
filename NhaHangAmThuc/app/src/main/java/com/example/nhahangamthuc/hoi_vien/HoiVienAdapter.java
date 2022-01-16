package com.example.nhahangamthuc.hoi_vien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;

import java.util.List;

public class HoiVienAdapter extends RecyclerView.Adapter<HoiVienAdapter.HoiVienViewHolder>{

    private List<HoiVien> hoiVienList;
    private IHoiVienClick iHoiVienClick;

    public interface IHoiVienClick {
        void onUpdateClick(HoiVien hoiVien);
        void onDeleteClick(HoiVien hoiVien);
    }

    public HoiVienAdapter(List<HoiVien> hoiVienList, IHoiVienClick iHoiVienClick) {
        this.hoiVienList = hoiVienList;
        this.iHoiVienClick = iHoiVienClick;
    }

    @NonNull
    @Override
    public HoiVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoi_vien, parent, false);
        return new HoiVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoiVienViewHolder holder, int position) {
        HoiVien hoiVien = hoiVienList.get(position);
        if (hoiVien == null) return;
        holder.textViewTen.setText(hoiVien.getTen());
        holder.textViewSoDienThoai.setText("Số điện thoại: " + hoiVien.getSoDienThoai());
        String hang;
        switch (hoiVien.getHang()) {
            case HoiVien.BAC:
                hang = "BẠC";
                break;
            case HoiVien.VANG:
                hang = "VÀNG";
                break;
            case HoiVien.KIM_CUONG:
                hang = "KIM CƯƠNG";
                break;
            case HoiVien.DONG:
            default:
                hang = "ĐỒNG";
                break;
        }
        holder.textViewHang.setText("Hạng: " + hang);
        holder.textViewDiemTichLuy.setText("Điểm tích lũy: " + hoiVien.getDiemTichLuy());
        holder.imageButtonSua.setOnClickListener(v -> {
           iHoiVienClick.onUpdateClick(hoiVien);
        });
        holder.imageButtonXoa.setOnClickListener(v -> {
            iHoiVienClick.onDeleteClick(hoiVien);
        });
    }

    @Override
    public int getItemCount() {
        if (hoiVienList != null) return hoiVienList.size();
        return 0;
    }

    public class HoiVienViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTen;
        TextView textViewSoDienThoai;
        TextView textViewHang;
        TextView textViewDiemTichLuy;
        ImageButton imageButtonSua;
        ImageButton imageButtonXoa;

        public HoiVienViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTen = itemView.findViewById(R.id.text_view_ten);
            textViewSoDienThoai = itemView.findViewById(R.id.text_view_so_dien_thoai);
            textViewHang = itemView.findViewById(R.id.text_view_hang);
            textViewDiemTichLuy = itemView.findViewById(R.id.text_view_diem_tich_luy);
            imageButtonSua = itemView.findViewById(R.id.image_button_sua);
            imageButtonXoa = itemView.findViewById(R.id.image_button_xoa);
        }
    }

}
