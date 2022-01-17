package com.example.nhahangamthuc.su_kien;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.mon_an.MonAn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SuKienFragment extends Fragment {
    private RecyclerView recyclerView;
    private SuKienAdapter adapter;
    private List<SuKien> suKienList;
    Context context;
    private ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference reference;

    public SuKienFragment() {
        // Required empty public constructor
    }

    public static SuKienFragment newInstance() {
        return new SuKienFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_su_kien, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_su_kien);
        view.findViewById(R.id.button_them_su_kien).setOnClickListener(v -> {
            themSuKien();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context = getContext();
        progressDialog = new ProgressDialog(context);

        super.onViewCreated(view, savedInstanceState);

        suKienList = new ArrayList<>();

        setData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        adapter = new SuKienAdapter(suKienList, new SuKienAdapter.IItemClick() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onUpdateClick(SuKien suKien) {
                suaSuKien(suKien);
            }

            @Override
            public void onDeleteClick(SuKien suKien) {
                xoaSuKien(suKien);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void setData() {
        progressDialog.show();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("list_su_kien");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                suKienList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SuKien suKien = dataSnapshot.getValue(SuKien.class);
                    suKienList.add(suKien);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Đã xảy ra lỗi khi lấy danh sách sự kiện!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void themSuKien() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_dialog_su_kien);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        dialog.setCancelable(true);

        RecyclerView rcvMonTangKem;
        List<MonAn> monAnList = new ArrayList<>();
        MonTangKemAdapter monTangKemAdapter = new MonTangKemAdapter(monAnList);;
        rcvMonTangKem = dialog.findViewById(R.id.rcv_mon_tang_kem);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rcvMonTangKem.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        rcvMonTangKem.addItemDecoration(decoration);
        rcvMonTangKem.setAdapter(monTangKemAdapter);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Danh_sach_mon_an");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MonAn monAn = dataSnapshot.getValue(MonAn.class);
                    monAnList.add(monAn);
                }
                monTangKemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        dialog.show();

        dialog.findViewById(R.id.btn_them_su_kien).setOnClickListener( v-> {
            EditText editTextTenSuKien = dialog.findViewById(R.id.edt_ten_su_kien);
            DatePicker datePickerStartDay = dialog.findViewById(R.id.datepicker_start_day);
            DatePicker datePickerEndDay = dialog.findViewById(R.id.datepicker_end_day);

            int id = suKienList.get(suKienList.size() - 1).getId() + 1;

            List<MonAn> monDuocTang = new ArrayList<>();
            for (int i = 0; i < monAnList.size(); i++) {
                MonAn monAn = monAnList.get(i);
                if (monAn.isChecked()) monDuocTang.add(monAn);
            }

            StringBuilder monTang = new StringBuilder();
            for (int i = 0; i < monDuocTang.size(); i++) {
                if (i == monDuocTang.size() - 1) monTang.append(monDuocTang.get(i).getTenmonan());
                else monTang.append(monDuocTang.get(i).getTenmonan()).append(", ");
            }

            SuKien suKien = new SuKien(id, editTextTenSuKien.getText().toString(), makeDate(datePickerStartDay),
                    makeDate(datePickerEndDay), monTang.toString());
            this.reference.child(String.valueOf(id)).setValue(suKien, (error, ref) -> {
                dialog.dismiss();
                Toast.makeText(context, "Thêm sự kiện thành công!", Toast.LENGTH_SHORT).show();
            });
        });

        dialog.findViewById(R.id.btn_huy_su_kien).setOnClickListener(v -> dialog.dismiss());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void suaSuKien(SuKien suKien) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_dialog_su_kien);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        TextView textViewTitle = dialog.findViewById(R.id.text_view_title);
        textViewTitle.setText("Cập nhật thông tin sự kiện");
        dialog.setCancelable(true);

        EditText editTextTenSuKien = dialog.findViewById(R.id.edt_ten_su_kien);
        editTextTenSuKien.setText(suKien.getTen());

        DatePicker datePickerStartDay = dialog.findViewById(R.id.datepicker_start_day);
        LocalDate startDay = LocalDate.parse(suKien.getStartDate(),  DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        datePickerStartDay.updateDate(startDay.getDayOfMonth(), startDay.getMonth().getValue(), startDay.getYear());

        DatePicker datePickerEndDay = dialog.findViewById(R.id.datepicker_end_day);
        LocalDate endDay = LocalDate.parse(suKien.getEndDate(),  DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        datePickerEndDay.updateDate(endDay.getDayOfMonth(), endDay.getMonth().getValue(), endDay.getYear());

        List<MonAn> monAnList = new ArrayList<>();
        RecyclerView rcvMonTangKem;
        MonTangKemAdapter monTangKemAdapter = new MonTangKemAdapter(monAnList);;
        rcvMonTangKem = dialog.findViewById(R.id.rcv_mon_tang_kem);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rcvMonTangKem.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        rcvMonTangKem.addItemDecoration(decoration);
        rcvMonTangKem.setAdapter(monTangKemAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Danh_sach_mon_an");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MonAn monAn = dataSnapshot.getValue(MonAn.class);
                    monAnList.add(monAn);
                }
                monTangKemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        dialog.show();

        Button buttonSua = dialog.findViewById(R.id.btn_them_su_kien);
        buttonSua.setText("Cập nhật");
        buttonSua.setOnClickListener(v -> {
            List<MonAn> monDuocTang = new ArrayList<>();
            for (int i = 0; i < monAnList.size(); i++) {
                MonAn monAn = monAnList.get(i);
                if (monAn.isChecked()) monDuocTang.add(monAn);
            }

            StringBuilder monTang = new StringBuilder();
            for (int i = 0; i < monDuocTang.size(); i++) {
                if (i == monDuocTang.size() - 1) monTang.append(monDuocTang.get(i).getTenmonan());
                else monTang.append(monDuocTang.get(i).getTenmonan()).append(", ");
            }

            suKien.setMonTangKem(monTang.toString());
            suKien.setStartDate(makeDate(datePickerStartDay));
            suKien.setEndDate(makeDate(datePickerEndDay));
            suKien.setTen(editTextTenSuKien.getText().toString());

            this.reference.child(String.valueOf(suKien.getId())).updateChildren(suKien.toMap(), (error, ref) -> {
                dialog.dismiss();
                Toast.makeText(context, "Cập nhật thông tin sự kiện thành công!", Toast.LENGTH_SHORT).show();
            });
        });

        dialog.findViewById(R.id.btn_huy_su_kien).setOnClickListener(v -> dialog.dismiss());
    }

    private void xoaSuKien(SuKien suKien) {
        new AlertDialog.Builder(context)
                .setTitle("Xóa sự kiện")
                .setMessage("Bạn có chắc chắn muốn xóa sự kiện này không?")
                .setPositiveButton("Xoá", (dialog, which) -> reference.child(String.valueOf(suKien.getId()))
                        .removeValue((error, ref) -> Toast.makeText(context, "Xóa sự kiện thành công!", Toast.LENGTH_SHORT).show()))
                .setNegativeButton("Hủy", null).show();
    }

    private String makeDate(DatePicker datePicker) {
        String day, month, year;
        int dayNum = datePicker.getDayOfMonth();
        if (dayNum < 10) day = "0" + dayNum;
        else day = String.valueOf(dayNum);
        int monthNum = datePicker.getMonth() + 1;
        if (monthNum < 10) month = "0" + monthNum;
        else month = String.valueOf(monthNum);
        year = String.valueOf(datePicker.getYear());
        return day + "-" + month + "-" + year;
    }
}