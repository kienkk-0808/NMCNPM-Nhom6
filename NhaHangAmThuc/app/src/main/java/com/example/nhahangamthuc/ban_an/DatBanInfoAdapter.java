package com.example.nhahangamthuc.ban_an;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DatBanInfoAdapter extends RecyclerView.Adapter<DatBanInfoAdapter.MyViewHolder> {
    private Context context;
    private List<DatBan> listDatBan;
    private DsBanService dsBanService;
    private Long idBan;
    private DatabaseReference listBanRef = FirebaseDatabase.getInstance().
            getReference("list_ban_an");
    private BanAn banAn;
    public DatBanInfoAdapter(Context context, Long idBan) {
        this.context = context;
        dsBanService = new DsBanService();
        this.idBan = idBan;
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
        final int pos = position;
        DatBan datBan = listDatBan.get(position);
        holder.tvTen.setText(datBan.getTen());
        holder.tvSdt.setText(datBan.getSdt());
        holder.tvThoiGian.setText(datBan.getThoiGian());
        holder.tvSoNguoi.setText(datBan.getSoNguoi() + " Người");
//
//        Timestamp thoiGian = Timestamp.valueOf(datBan.getThoiGian());
//        Timestamp curTime = new Timestamp(System.currentTimeMillis());
//        Timestamp t2 = new Timestamp(0L);
//        t2.setTime(thoiGian.getTime() + dsBanService.millisPH / 2 + 1000);//t2 la sau 30p1s
//        if (dsBanService.checkTime(curTime, thoiGian, t2)) {
//            //trong thời gian đặt bàn
//            // Log.d("t", t2.toString());
//            holder.btnGoiMon.setVisibility(View.VISIBLE);
//        } else {
//            //Log.d("t",curTime.toString()+" "+ thoiGian.toString()+" "+ t2.toString());
//            holder.btnGoiMon.setVisibility(View.INVISIBLE);
//        }
        holder.imgbHuyDatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huyDatBan(pos);
            }
        });
    }

    private void huyDatBan(int pos) {

        new AlertDialog.Builder(context).setTitle("Hủy đặt bàn")
                .setMessage("Bạn có chắc chắn muốn hủy đặt bàn này không?")
                .setPositiveButton("Có", ((dialog, which) -> {
                    listBanRef.child(idBan.toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            banAn = snapshot.getValue(BanAn.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    listDatBan.remove(pos);
                   // banAn.setDanhSachDat(listDatBan);
                    listBanRef.child(idBan.toString()).child("danhSachDat").setValue(listDatBan);
                    Toast.makeText(context, "Hủy đặt bàn thành công!", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }))
                .setNegativeButton("Không", null).show();
    }

    @Override
    public int getItemCount() {
        return listDatBan.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTen, tvSdt, tvThoiGian, tvSoNguoi;
        Button btnGoiMon;
        ImageButton imgbHuyDatBan;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = (TextView) itemView.findViewById(R.id.tv_item_dat_ban_ten);
            tvSdt = (TextView) itemView.findViewById(R.id.tv_item_dat_ban_sdt);
            tvThoiGian = (TextView) itemView.findViewById(R.id.tv_item_dat_ban_thoigian);
            btnGoiMon = (Button) itemView.findViewById(R.id.btn_item_dat_ban_goimon);
            tvSoNguoi = (TextView) itemView.findViewById(R.id.tv_item_dat_ban_songuoi);
            imgbHuyDatBan = (ImageButton) itemView.findViewById(R.id.imgb_huy_dat_ban);
        }
    }
}
