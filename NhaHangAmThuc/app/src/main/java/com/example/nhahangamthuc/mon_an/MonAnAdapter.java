package com.example.nhahangamthuc.mon_an;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.example.nhahangamthuc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.MyViewHolder> implements Filterable {
    private Context context;
    public ArrayList<MonAn> mListMonAn, filterList;

    private static final String TAG = "MonAn_ADAPTER_TAG";

    private ProgressDialog progressDialog;
    private FilterMonAn filter;

    public MonAnAdapter(Context context, ArrayList<MonAn> mListMonAn){
        this.context = context;
        this.mListMonAn = mListMonAn;
        this.filterList = mListMonAn;

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
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

        Glide.with(holder.hinhanh.getContext()).load(monAn.getUrl()).into(holder.hinhanh);

        holder.moreBtn.setOnClickListener(v -> moreOptionsDialog(monAn, holder));
    }

    private void moreOptionsDialog(MonAn monAn, MyViewHolder holder) {
        String[] options = {"Sửa", "Xóa", "Thêm vào menu tốt nhất"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Options")
                .setItems(options, (dialog, which) -> {
                    if (which == 0){
                        editMonAn(monAn,holder);
                    }
                    else if(which == 1){
                        new AlertDialog.Builder(context).setTitle("Xóa món ăn")
                                .setMessage("Bạn có chắc chắn muốn xóa món ăn này không?")
                                .setPositiveButton("Xóa", (dialog1, which1) -> deleteMonAn(monAn, holder))
                                .setNegativeButton("Hủy", null).show();
                    }
                    else if(which == 2){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("monanId", ""+monAn.getId());
                        DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Danh_sach_mon_an_tot_nhat");
                        refer.child(""+monAn.getId())
                                .setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Đã thêm vào menu tốt nhất ngày!",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Thêm THẤT BẠI vào menu tốt nhất ngày!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .show();
    }

    private void editMonAn(MonAn monAn, MyViewHolder holder) {
        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.popup_dialog_monan);
        Window window = dialog2.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        dialog2.setCancelable(true);

        EditText editTextTen = dialog2.findViewById(R.id.edt_ten_mon_an);
        editTextTen.setText(monAn.getTenmonan());

        TextView textViewKieu = dialog2.findViewById(R.id.edt_kieu_mon_an);
        textViewKieu.setText(monAn.getKieumonan());
        textViewKieu.setOnClickListener(v -> {
            String[] kieumonanArray = new String[5];
            kieumonanArray[0] = "Khai vị";
            kieumonanArray[1] = "Món chính";
            kieumonanArray[2] = "Món phụ ăn kèm";
            kieumonanArray[3] = "Món tráng miệng";
            kieumonanArray[4] = "Đồ uống";

            androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
            builder1.setTitle("Chọn kiểu món ăn")
                    .setItems(kieumonanArray, (dialog12, which12) -> {
                        String tenkieumonan = kieumonanArray[which12];
                        textViewKieu.setText(tenkieumonan);
                    })
                    .show();
        });

        EditText editTextGia = dialog2.findViewById(R.id.edt_gia_tien);
        editTextGia.setText(String.valueOf(monAn.getGiatien()));
        dialog2.show();

        dialog2.findViewById(R.id.btn_sua).setOnClickListener(v -> {
            monAn.setTenmonan((editTextTen.getText().toString()));
            monAn.setKieumonan(textViewKieu.getText().toString());
            monAn.setGiatien(Long.parseLong(editTextGia.getText().toString()));

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Danh_sach_mon_an");
            reference.child(String.valueOf(monAn.getId())).updateChildren(monAn.toMap(), (error, ref) -> {
                dialog2.dismiss();
                Toast.makeText(context, "Sửa thông tin món ăn thành công!", Toast.LENGTH_SHORT).show();
            });
        });

        dialog2.findViewById(R.id.btn_huy).setOnClickListener(v -> dialog2.dismiss());
    }

    private void deleteMonAn(MonAn monAn, MyViewHolder holder) {
        Long MonAnid = monAn.getId();
        String MonAnUrl = monAn.getUrl();
        String MonAnTen = monAn.getTenmonan();

        Log.d(TAG, "XoaMonAn: Deleting...");
        progressDialog.setMessage("Deleting " + MonAnTen+" ...");
        progressDialog.show();

        Log.d(TAG,"deleteMonAn: Deleting from storage");
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(MonAnUrl);
        storageReference.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: Deleted from storage");

                        Log.d(TAG,"onSuccess: Now deleting info from db");
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Danh_sach_mon_an");
                        ref.child(""+MonAnid)
                                .removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG,"onSuccess: Deleted from db too");
                                        progressDialog.dismiss();
                                        Toast.makeText(context,"Món ăn đã được xóa thành công!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG,"onFailure: Failed to delete from db due to "+ e.getMessage());
                                        progressDialog.dismiss();
                                        Toast.makeText(context,"" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"onFailure: Failed to delete from storage due to" + e.getMessage());
                        progressDialog.dismiss();
                        Toast.makeText(context,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mListMonAn.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new FilterMonAn(filterList, this);
        }
        return filter;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tenmonan_txt, kieumonan_txt, giatien_txt;
        ImageView hinhanh;
        ImageButton moreBtn;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tenmonan_txt = itemView.findViewById(R.id.tenmonan);
            kieumonan_txt = itemView.findViewById(R.id.kieumonan);
            giatien_txt = itemView.findViewById(R.id.giatien);
            hinhanh =(ImageView) itemView.findViewById(R.id.all_menu_image);
            moreBtn = itemView.findViewById(R.id.moreBtn);
        }
    }
}
