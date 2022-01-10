package com.example.nhahangamthuc.do_choi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;

import java.util.List;

public class DoChoiAdapter extends RecyclerView.Adapter<DoChoiAdapter.DoChoiViewHolder>{

    private List<DoChoi> doChoiList;
    private IItemClick iItemClick;

    public interface IItemClick {
        void onUpdateClick(DoChoi doChoi);
        void onDeleteClick(DoChoi doChoi);
    }

    public DoChoiAdapter(List<DoChoi> doChoiList, IItemClick iItemClick) {
        this.doChoiList = doChoiList;
        this.iItemClick = iItemClick;
    }

    @NonNull
    @Override
    public DoChoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_do_choi, parent, false);
        return new DoChoiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoChoiViewHolder holder, int position) {
        DoChoi doChoi = doChoiList.get(position);
        if (doChoi == null) return;
        holder.textViewTen.setText(doChoi.getTen());
        holder.textViewSoLuong.setText("Số lượng: " + doChoi.getSoLuong());
        holder.imageButtonSua.setOnClickListener(v -> {
           iItemClick.onUpdateClick(doChoi);
        });
        holder.imageButtonXoa.setOnClickListener(v -> {
            iItemClick.onDeleteClick(doChoi);
        });
    }

    @Override
    public int getItemCount() {
        if (doChoiList != null) return doChoiList.size();
        return 0;
    }

    public class DoChoiViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTen;
        TextView textViewSoLuong;
        ImageButton imageButtonSua;
        ImageButton imageButtonXoa;

        public DoChoiViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTen = itemView.findViewById(R.id.text_view_ten);
            textViewSoLuong = itemView.findViewById(R.id.text_view_so_luong);
            imageButtonSua = itemView.findViewById(R.id.image_button_sua);
            imageButtonXoa = itemView.findViewById(R.id.image_button_xoa);
        }
    }

}
