package com.example.nhahangamthuc.hoi_vien;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HoiVienFragment extends Fragment {

    private RecyclerView recyclerView;
    private HoiVienAdapter adapter;
    private List<HoiVien> hoiVienList;
    Context context;
    private ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference reference;

    public HoiVienFragment() {
        // Required empty public constructor
    }

    public static HoiVienFragment newInstance() {
        return new HoiVienFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoi_vien, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_do_choi);
        view.findViewById(R.id.button_them_do_choi).setOnClickListener(v -> {
            themDoChoi();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context = getContext();
        progressDialog = new ProgressDialog(context);

        super.onViewCreated(view, savedInstanceState);

        hoiVienList = new ArrayList<>();

        setData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        adapter = new HoiVienAdapter(hoiVienList, new HoiVienAdapter.IHoiVienClick() {
            @Override
            public void onUpdateClick(HoiVien hoiVien) {
                suaHoiVien(hoiVien);
            }

            @Override
            public void onDeleteClick(HoiVien hoiVien) {
                xoaHoiVien(hoiVien);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void setData() {
        progressDialog.show();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("list_hoi_vien");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hoiVienList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HoiVien hoiVien = dataSnapshot.getValue(HoiVien.class);
                    hoiVienList.add(hoiVien);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Đã xảy ra lỗi khi lấy danh sách Hội viên!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void themDoChoi() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_dialog_hoi_vien);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        dialog.setCancelable(true);
        dialog.show();

        dialog.findViewById(R.id.btn_them).setOnClickListener(v -> {
            EditText editTextTen = dialog.findViewById(R.id.edt_ten_do_choi);
            EditText editTextSoLuong = dialog.findViewById(R.id.edt_so_luong);
            int id = hoiVienList.get(hoiVienList.size()-1).getId()+1;
            HoiVien hoiVien = new HoiVien(id, editTextSoLuong.getText().toString(), editTextTen.getText().toString(), 0, HoiVien.DONG);
            reference.child(String.valueOf(id)).setValue(hoiVien, (error, ref) -> {
                dialog.dismiss();
                Toast.makeText(context, "Thêm Hội viên thành công!", Toast.LENGTH_SHORT).show();
            });
        });

        dialog.findViewById(R.id.btn_huy).setOnClickListener(v -> dialog.dismiss());
    }

    private void suaHoiVien(HoiVien hoiVien) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_dialog_hoi_vien);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        dialog.setCancelable(true);

        EditText editTextTen = dialog.findViewById(R.id.edt_ten_do_choi);
        editTextTen.setText(hoiVien.getTen());
        EditText editTextSoLuong = dialog.findViewById(R.id.edt_so_luong);
        editTextSoLuong.setText(hoiVien.getSoDienThoai());
        dialog.show();

        dialog.findViewById(R.id.btn_them).setOnClickListener(v -> {
            hoiVien.setTen(editTextTen.getText().toString());
            hoiVien.setSoDienThoai(editTextSoLuong.getText().toString());
            reference.child(String.valueOf(hoiVien.getId())).updateChildren(hoiVien.toMap(), (error, ref) -> {
                dialog.dismiss();
                Toast.makeText(context, "Cập nhật thông tin Hội viên thành công!", Toast.LENGTH_SHORT).show();
            });
        });

        dialog.findViewById(R.id.btn_huy).setOnClickListener(v -> dialog.dismiss());
    }

    private void xoaHoiVien(HoiVien hoiVien) {
        new AlertDialog.Builder(context).setTitle("Xóa Hội viên")
                .setMessage("Bạn có chắc chắn muốn xóa Hội viên này không?")
                .setPositiveButton("Xóa", (dialog, which) -> reference.child(String.valueOf(hoiVien.getId())).removeValue((error, ref) ->
                        Toast.makeText(context, "Xóa Hội viên thành công!", Toast.LENGTH_SHORT).show()))
                .setNegativeButton("Hủy", null).show();
    }
}