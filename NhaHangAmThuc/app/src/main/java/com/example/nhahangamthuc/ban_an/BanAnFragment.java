package com.example.nhahangamthuc.ban_an;

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
import android.widget.Button;
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

public class BanAnFragment extends Fragment {
    private RecyclerView recyclerView;
    private CRUDBanAdapter adapter;
    private List<BanAn> banAnList;
    Context context;
    private ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference reference;

    public BanAnFragment() {
        // Required empty public constructor
    }

    public static BanAnFragment newInstance() {
        BanAnFragment fragment = new BanAnFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ban_an, container, false);
        recyclerView = view.findViewById(R.id.rcv_crudban_dsban);
        view.findViewById(R.id.btn_them_ban).setOnClickListener(v -> {
            themBanAn();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context = getContext();
        progressDialog = new ProgressDialog(context);

        super.onViewCreated(view, savedInstanceState);

        banAnList = new ArrayList<>();

        setData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        adapter = new CRUDBanAdapter(banAnList, new CRUDBanAdapter.IItemClick() {
            @Override
            public void onUpdateClick(BanAn banAn) {
                suaBanAn(banAn);
            }

            @Override
            public void onDeleteClick(BanAn banAn) {
                xoaBanAn(banAn);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void setData() {
        progressDialog.show();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("list_ban_an");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                banAnList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BanAn banAn = dataSnapshot.getValue(BanAn.class);
                    banAnList.add(banAn);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Đã xảy ra lỗi khi lấy danh sách bàn ăn!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void themBanAn() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_dialog_crudban);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        dialog.setCancelable(true);
        dialog.show();

        dialog.findViewById(R.id.btn_pd_crudban_them).setOnClickListener(v -> {
            EditText editTextTen = dialog.findViewById(R.id.edt_pd_crudban_id);
            EditText editTextSoLuong = dialog.findViewById(R.id.edt_pd_crudban_songuoi);
            if (editTextSoLuong.getText().toString().length() < 1 || editTextTen.getText().toString().length() < 1) {
                Toast.makeText(context, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                BanAn banAn = new BanAn(Long.valueOf(editTextTen.getText().toString()),
                        Integer.parseInt(editTextSoLuong.getText().toString()));
                reference.child(String.valueOf(banAn.getIdBan())).setValue(banAn, (error, ref) -> {
                    dialog.dismiss();
                    Toast.makeText(context, "Thêm bàn thành công!", Toast.LENGTH_SHORT).show();
                });
            }
        });

        dialog.findViewById(R.id.btn_pd_crudban_huy).setOnClickListener(v -> dialog.dismiss());
    }

    private void suaBanAn(BanAn banAn) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_dialog_crudban);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        dialog.setCancelable(true);

        EditText editTextTen = dialog.findViewById(R.id.edt_pd_crudban_id);
        String idBanStrOld = banAn.getIdBan().toString();
        editTextTen.setText(banAn.getIdBan().toString());
        EditText editTextSoLuong = dialog.findViewById(R.id.edt_pd_crudban_songuoi);
        editTextSoLuong.setText(String.valueOf(String.valueOf(banAn.getSoNguoi())));
        dialog.show();
        Button btnSua = (Button) dialog.findViewById(R.id.btn_pd_crudban_them);
        btnSua.setText("Sửa");
        dialog.findViewById(R.id.btn_pd_crudban_them).setOnClickListener(v -> {
            String idBanstr = editTextTen.getText().toString();
            if (editTextSoLuong.getText().toString().length() < 1 || editTextTen.getText().toString().length() < 1) {
                Toast.makeText(context, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
            banAn.setIdBan(Long.valueOf(idBanstr));
            banAn.setSoNguoi(Integer.parseInt(editTextSoLuong.getText().toString()));
                reference.child(String.valueOf(banAn.getIdBan())).updateChildren(banAn.toMap(), (error, ref) -> {
                    dialog.dismiss();
                    if (!idBanstr.equals(idBanStrOld))
                        reference.child(idBanStrOld).removeValue();
                    Toast.makeText(context, "Sửa thông tin bàn thành công!", Toast.LENGTH_SHORT).show();
                });
            }
        });

        dialog.findViewById(R.id.btn_pd_crudban_huy).setOnClickListener(v -> dialog.dismiss());
    }

    private void xoaBanAn(BanAn banAn) {
        new AlertDialog.Builder(context).setTitle("Xóa bàn ăn")
                .setMessage("Bạn có chắc chắn muốn xóa bàn ăn này không?")
                .setPositiveButton("Xóa", (dialog, which) -> reference.child(String.valueOf(banAn.getIdBan())).removeValue((error, ref) ->
                        Toast.makeText(context, "Xóa bàn ăn thành công!", Toast.LENGTH_SHORT).show()))
                .setNegativeButton("Hủy", null).show();
    }
}