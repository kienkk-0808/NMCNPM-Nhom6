package com.example.nhahangamthuc.ban_an;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.ban_an.ChiTietBan;

import java.util.List;

public class BanAnAdapter extends RecyclerView.Adapter<BanAnAdapter.BanViewHolder> {
    private Context context;
    private List<BanAn> listBan;

    public BanAnAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BanAn> listBan) {
        this.listBan = listBan;
        notifyDataSetChanged();
    }
    public BanAn getItem(int position){
        return listBan.get(position);
    }

    @NonNull
    @Override
    public BanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ban, parent, false);
        return new BanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BanViewHolder holder, int position) {
        BanAn banAn = listBan.get(position);
        if (banAn != null) {
            if (banAn.getTrangThai() == 0)
                holder.imgvBan.setImageResource(R.drawable.ban_free);
            else
                holder.imgvBan.setImageResource(R.drawable.ban_busy);
            holder.imgvBan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //lauch Chi tiet ban
                    Intent intent = new Intent(context, ChiTietBan.class);
                    intent.putExtra("BanAn", banAn);
                    context.startActivity(intent);
                }
            });
        }
        holder.tvMoTa.setText("Bàn " + banAn.getIdBan() + "\n" + banAn.getSoNguoi() + " người");

    }

    @Override
    public int getItemCount() {
        if (listBan != null)
            return listBan.size();
        return 0;
    }

    public class BanViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgvBan;
        private TextView tvMoTa;

        public BanViewHolder(@NonNull View itemView) {
            super(itemView);
            imgvBan = itemView.findViewById(R.id.imgv_ban);
            tvMoTa = itemView.findViewById(R.id.tv_mo_ta);
        }

    }
}
