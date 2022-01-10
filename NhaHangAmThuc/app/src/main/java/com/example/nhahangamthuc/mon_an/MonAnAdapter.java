package com.example.nhahangamthuc.mon_an;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhahangamthuc.R;

import java.util.ArrayList;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<MonAn> mListMonAn;

    public MonAnAdapter(Context contex, ArrayList<MonAn> mListMonAn){
        this.context = context;
        this.mListMonAn = mListMonAn;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monan,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MonAn monAn = mListMonAn.get(position);
        String tenmon = monAn.getTenmonan();
        String kieumon = monAn.getKieumonan();
        Long giatien = monAn.getGiatien();

        holder.tenmonan_txt.setText(tenmon);
        holder.kieumonan_txt.setText(kieumon);
        holder.giatien_txt.setText(String.valueOf(giatien));

        /*String url = monAn.getUrl();

        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        ref.getBytes(20000000)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        holder.hinhanh.setImageBitmap(bitmap);
                    }
                });*/
        Glide.with(holder.hinhanh.getContext()).load(monAn.getUrl()).into(holder.hinhanh);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mListMonAn.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tenmonan_txt, kieumonan_txt, giatien_txt;
        ImageView hinhanh;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tenmonan_txt = itemView.findViewById(R.id.tenmonan);
            kieumonan_txt = itemView.findViewById(R.id.kieumonan);
            giatien_txt = itemView.findViewById(R.id.giatien);
            hinhanh =(ImageView) itemView.findViewById(R.id.all_menu_image);
        }
    }
}
