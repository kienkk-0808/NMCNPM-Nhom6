package com.example.nhahangamthuc.ban_an;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.mon_an.MonAn;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class DatBanInfoAdapter extends RecyclerView.Adapter<DatBanInfoAdapter.MyViewHolder> {
    private Context context;
    private List<DatBan> listDatBan;
    private DsBanService dsBanService;
    public DatBanInfoAdapter(Context context) {
        this.context = context;
        dsBanService = new DsBanService();
    }

    public void setData(List<DatBan> listDatBan) {
        this.listDatBan = listDatBan;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dat_ban_info, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DatBan datBan = listDatBan.get(position);
        holder.tvTen.setText(datBan.getTen());
        holder.tvSdt.setText(datBan.getSdt());
        holder.tvThoiGian.setText(datBan.getThoiGian());
        holder.tvSoNguoi.setText(datBan.getSoNguoi() + " Người");

        Timestamp thoiGian = Timestamp.valueOf(datBan.getThoiGian());
        Timestamp curTime = new Timestamp(System.currentTimeMillis());
        Timestamp t2 = new Timestamp(0L);
        t2.setTime(thoiGian.getTime() + dsBanService.millisPH/2 + 1000);//t2 la sau 30p1s
        if (dsBanService.checkTime(curTime,thoiGian, t2)){
            //trong thời gian đặt bàn
           // Log.d("t", t2.toString());
            holder.btnGoiMon.setVisibility(View.VISIBLE);
        }
        else {
            //Log.d("t",curTime.toString()+" "+ thoiGian.toString()+" "+ t2.toString());
            holder.btnGoiMon.setVisibility(View.INVISIBLE);
        }
        holder.btnGoiMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chuyển đến activity gọi món, chuyển thông tin đặt bàn qua Intent
            }
        });
    }


    @Override
    public int getItemCount() {
        return listDatBan.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTen, tvSdt, tvThoiGian, tvSoNguoi;
        Button btnGoiMon;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen =(TextView) itemView.findViewById(R.id.tv_item_dat_ban_ten);
            tvSdt =(TextView) itemView.findViewById(R.id.tv_item_dat_ban_sdt);
            tvThoiGian=(TextView) itemView.findViewById(R.id.tv_item_dat_ban_thoigian);
            btnGoiMon =(Button) itemView.findViewById(R.id.btn_item_dat_ban_goimon);
            tvSoNguoi = (TextView) itemView.findViewById(R.id.tv_item_dat_ban_songuoi);
        }
    }
}
