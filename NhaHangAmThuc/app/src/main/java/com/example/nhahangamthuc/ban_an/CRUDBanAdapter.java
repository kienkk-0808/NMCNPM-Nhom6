package com.example.nhahangamthuc.ban_an;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;

import java.util.List;

public class CRUDBanAdapter extends RecyclerView.Adapter<CRUDBanAdapter.BanAnViewHolder> {

    private List<BanAn> banAnList;
    private IItemClick iItemClick;

    public interface IItemClick {
        void onUpdateClick(BanAn banAn);

        void onDeleteClick(BanAn banAn);
    }

    public CRUDBanAdapter(List<BanAn> banAnList, IItemClick iItemClick) {
        this.banAnList = banAnList;
        this.iItemClick = iItemClick;
    }

    @NonNull
    @Override
    public BanAnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crud_ban_an, parent, false);
        return new BanAnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BanAnViewHolder holder, int position) {
        BanAn banAn = banAnList.get(position);
        if (banAn == null) return;
        holder.tvIdBan.setText("Bàn: " + banAn.getIdBan().toString());
        holder.tvSoNguoi.setText("Số lượng: " + banAn.getSoNguoi());
        holder.imgbSua.setOnClickListener(v -> {
            iItemClick.onUpdateClick(banAn);
        });
        holder.imgbXoa.setOnClickListener(v -> {
            iItemClick.onDeleteClick(banAn);
        });
    }

    @Override
    public int getItemCount() {
        if (banAnList != null) return banAnList.size();
        return 0;
    }

    public class BanAnViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdBan;
        TextView tvSoNguoi;
        ImageButton imgbSua;
        ImageButton imgbXoa;

        public BanAnViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdBan = itemView.findViewById(R.id.tv_crudban_id);
            tvSoNguoi = itemView.findViewById(R.id.tv_crudban_songuoi);
            imgbSua = itemView.findViewById(R.id.imgb_crudban_sua);
            imgbXoa = itemView.findViewById(R.id.imgb_crudban_xoa);
        }
    }

}
