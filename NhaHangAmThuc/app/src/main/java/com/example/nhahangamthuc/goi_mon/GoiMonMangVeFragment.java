package com.example.nhahangamthuc.goi_mon;

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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
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
import com.example.nhahangamthuc.do_choi.DoChoi;
import com.example.nhahangamthuc.don_hang.ChonDoChoiAdapter;
import com.example.nhahangamthuc.don_hang.DonHang;
import com.example.nhahangamthuc.don_hang.DonHangAdapter;
import com.example.nhahangamthuc.hoi_vien.HoiVien;
import com.example.nhahangamthuc.mon_an.MonAn;
import com.example.nhahangamthuc.su_kien.SuKien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GoiMonMangVeFragment extends Fragment {

    RecyclerView recyclerView2, recyclerView1;
    GoiMonAdapter adapter2, adapter1;
    Context context;
    List<MonAn> monAnList, monDuocGoi, monNoiBat, monTrongSuKien;
    List<String> tenCacMonSK;
    ProgressDialog progressDialog;
    DonHang donHang;
    List<DoChoi> doChoiDuocChon;
    List<SuKien> suKienList, suKienDangCo;
    CheckBox checkBoxDoChoi, checkBoxHoiVien;
    HoiVien hoiVienDuocChon;
    Long tongTien = 0L;
    TextView textViewTongTien;

    public GoiMonMangVeFragment() {
        // Required empty public constructor
    }

    public static GoiMonMangVeFragment newInstance() {
        return new GoiMonMangVeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goi_mon_mang_ve, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        monAnList = new ArrayList<>();
        monDuocGoi = new ArrayList<>();
        suKienList = new ArrayList<>();
        tenCacMonSK = new ArrayList<>();
        monTrongSuKien = new ArrayList<>();
        monNoiBat = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading...");
        donHang = new DonHang(monDuocGoi);
        adapter1 = new GoiMonAdapter(monNoiBat, donHang);
        adapter2 = new GoiMonAdapter(monAnList, donHang);

        recyclerView1 = view.findViewById(R.id.rcv_cac_mon_noi_bat);
        recyclerView1.setLayoutManager(new LinearLayoutManager(context));
        recyclerView1.setAdapter(adapter1);
        recyclerView2 = view.findViewById(R.id.rcv_goi_mon_mang_ve);
        recyclerView2.setLayoutManager(new LinearLayoutManager(context));
        recyclerView2.setAdapter(adapter2);
        setData();

        view.findViewById(R.id.txv_don_hang).setOnClickListener(v -> {
            popupDonHang();
        });

        view.findViewById(R.id.button_su_kien).setOnClickListener(v -> {
            popupSuKien();
        });

    }

    private void setData() {
        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference2 = database.getReference("Danh_sach_mon_an");
        DatabaseReference reference1 = database.getReference("Danh_sach_mon_an_tot_nhat");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                monAnList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MonAn monAn = dataSnapshot.getValue(MonAn.class);
                    monAnList.add(monAn);
                }
                adapter2.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Đã xảy ra lỗi khi lấy danh sách món ăn!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                monNoiBat.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    String monanId = ""+ds.child("monanId").getValue();
                    reference2.child(monanId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            MonAn monAn = snapshot.getValue(MonAn.class);
                            monNoiBat.add(monAn);
                            adapter1.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void popupDonHang() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_dialog_don_hang);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        dialog.setCancelable(true);

        List<MonAn> monTrongDon = donHang.getMonAnList();
        RecyclerView rcvDonHang = dialog.findViewById(R.id.rcv_don_hang);
        DonHangAdapter donHangAdapter = new DonHangAdapter(monTrongDon);
        rcvDonHang.setLayoutManager(new LinearLayoutManager(context));
        rcvDonHang.setAdapter(donHangAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        rcvDonHang.addItemDecoration(decoration);

        for (int i = 0; i < monTrongDon.size(); i++) tongTien += monTrongDon.get(i).getGiatien();
        textViewTongTien = dialog.findViewById(R.id.txv_tong_tien);
        textViewTongTien.setText("Tổng tiên: " + tongTien + " VNĐ");

        checkBoxDoChoi = dialog.findViewById(R.id.checkbox_do_choi);
        checkBoxDoChoi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                popupDoChoi();
            }
        });

        checkBoxHoiVien = dialog.findViewById(R.id.checkbox_hoi_vien);
        checkBoxHoiVien.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) popupHoiVien();
        });

        List<DonHang> donHangList = new ArrayList<>();
        donHang.setTongTien(tongTien);
        LocalDate date = LocalDate.now();
        donHang.setNgay(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("list_don_hang");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donHangList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DonHang donHang = dataSnapshot.getValue(DonHang.class);
                    donHangList.add(donHang);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Đã xảy ra lỗi khi lấy danh sách đồ chơi!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();

        dialog.findViewById(R.id.btn_them).setOnClickListener(v -> {
            String newId = String.valueOf(donHangList.size());
            reference.child(newId).setValue(donHang, (error, ref) -> {
                dialog.dismiss();
                Toast.makeText(context, "Xuất hóa đơn thành công!", Toast.LENGTH_SHORT).show();
            });
        });

        dialog.findViewById(R.id.btn_huy).setOnClickListener(v -> dialog.dismiss());
    }

    private void popupDoChoi() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_dialog_chon_do_choi);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        dialog.setCancelable(true);

        List<DoChoi> doChoiList = new ArrayList<>();
        RecyclerView rcvChonDoChoi = dialog.findViewById(R.id.rcv_chon_do_choi);
        ChonDoChoiAdapter chonDoChoiAdapter = new ChonDoChoiAdapter(doChoiList);
        rcvChonDoChoi.setLayoutManager(new LinearLayoutManager(context));
        rcvChonDoChoi.setAdapter(chonDoChoiAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        rcvChonDoChoi.addItemDecoration(decoration);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("list_do_choi");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doChoiList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DoChoi doChoi = dataSnapshot.getValue(DoChoi.class);
                    doChoiList.add(doChoi);
                }
                chonDoChoiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Đã xảy ra lỗi khi lấy danh sách đồ chơi!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();

        dialog.findViewById(R.id.btn_them).setOnClickListener(v -> {
            doChoiDuocChon = new ArrayList<>();
            for (int i = 0; i < doChoiList.size(); i++) {
                DoChoi doChoi = doChoiList.get(i);
                if (doChoi.isChecked()) doChoiDuocChon.add(doChoi);
            }
            dialog.dismiss();
            StringBuilder checkBoxText = new StringBuilder("Đồ chơi: ");
            int size = doChoiDuocChon.size();
            for (int i = 0; i < size; i++) {
                if (i == size-1) checkBoxText.append(doChoiDuocChon.get(i).getTen());
                else checkBoxText.append(doChoiDuocChon.get(i).getTen()).append(", ");
            }
            checkBoxDoChoi.setText(checkBoxText);
        });

        dialog.findViewById(R.id.btn_huy).setOnClickListener(v -> dialog.dismiss());
    }

    private void popupHoiVien() {
        final Dialog dialog = new Dialog(context);
        hoiVienDuocChon = new HoiVien();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_dialog_chon_hoi_vien);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        dialog.setCancelable(true);

        List<String> hv = new ArrayList<>();
        AutoCompleteTextView autoCompleteTextView = dialog.findViewById(R.id.act_hoi_vien);
        List<HoiVien> hoiVienList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("list_hoi_vien");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hoiVienList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HoiVien hoiVien1 = dataSnapshot.getValue(HoiVien.class);
                    hoiVienList.add(hoiVien1);
                }
                for (int i = 0; i < hoiVienList.size(); i++) {
                    HoiVien hoiVien2 = hoiVienList.get(i);
                    hv.add(hoiVien2.getTen() + ", " + hoiVien2.getSoDienThoai());
                }

                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_list_item_1, hv);
                autoCompleteTextView.setThreshold(1);
                autoCompleteTextView.setAdapter(stringArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Đã xảy ra lỗi khi lấy danh sách Hội viên!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();

        dialog.findViewById(R.id.btn_them).setOnClickListener(v -> {
            String hoiVienText = autoCompleteTextView.getText().toString();
            List<String> stringList = Arrays.asList(hoiVienText.split(", "));
            for (int i = 0; i < hoiVienList.size(); i++) {
                HoiVien hoiVien3 = hoiVienList.get(i);
                if (hoiVien3.getTen().equals(stringList.get(0)) && hoiVien3.getSoDienThoai().equals(stringList.get(1)))
                    hoiVienDuocChon = hoiVien3;
            }
            checkBoxHoiVien.setText("Hội viên: " + hoiVienDuocChon.getTen() + "\n" + "Số điện thoại: "
            +hoiVienDuocChon.getSoDienThoai());
            dialog.dismiss();
            tongTien = tinhTien(hoiVienDuocChon, tongTien);
            textViewTongTien.setText("Tổng tiên: " + tongTien + " VNĐ");
        });

        dialog.findViewById(R.id.btn_huy).setOnClickListener(v -> dialog.dismiss());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void popupSuKien() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_dialog_su_kien_goi_mon);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        dialog.setCancelable(true);

        RecyclerView rcvMonTrongSK = dialog.findViewById(R.id.rcv_mon_trong_su_kien);
        MonTrongSuKienAdapter adapter = new MonTrongSuKienAdapter(monTrongSuKien, donHang);
        rcvMonTrongSK.setLayoutManager(new LinearLayoutManager(context));
        rcvMonTrongSK.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        rcvMonTrongSK.addItemDecoration(decoration);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("list_su_kien");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                suKienList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SuKien suKien = dataSnapshot.getValue(SuKien.class);
                    suKienList.add(suKien);
                }
                for(int i = 0; i < suKienList.size(); i++) {
                    SuKien suKien = suKienList.get(i);
                    LocalDate startDate = LocalDate.parse(suKien.getStartDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    LocalDate endDate = LocalDate.parse(suKien.getEndDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    LocalDate currentDate = LocalDate.now();
                    suKienDangCo = new ArrayList<>();
                    if (currentDate.isAfter(startDate) && currentDate.isBefore(endDate)) {
                        suKienDangCo.add(suKien);
                    }
                    StringBuilder tenSK = new StringBuilder("Sự kiện đang diễn ra: ");
                    int size = suKienDangCo.size();
                    for (int j = 0; j < size; j++) {
                        if (j == size - 1) tenSK.append(suKienDangCo.get(j).getTen());
                        tenSK.append(suKienDangCo.get(j).getTen()).append(", ");
                        List<String> tenCacMon = Arrays.asList(suKien.getMonTangKem().split(", "));
                        tenCacMonSK.addAll(tenCacMon);
                    }
                    TextView tenSuKien = dialog.findViewById(R.id.txv_su_kien_dang_dien_ra);
                    tenSuKien.setText(tenSK);

                    for (int t = 0; t < monAnList.size(); t++) {
                        MonAn monAn = monAnList.get(t);
                        for (int k = 0; k < tenCacMonSK.size(); k++) {
                            if (monAn.getTenmonan().equals(tenCacMonSK.get(k))) monTrongSuKien.add(monAn);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Đã xảy ra lỗi khi lấy danh sách sự kiện!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();

        dialog.findViewById(R.id.btn_them).setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.findViewById(R.id.btn_huy).setOnClickListener(v -> dialog.dismiss());
    }

    private Long tinhTien(HoiVien hoiVien, Long tongTien) {

        long diemTichLuy = hoiVien.getDiemTichLuy();
        if (diemTichLuy >= 500000) tongTien -= 500000;
        else tongTien -= diemTichLuy;

        return tongTien >= 0 ? tongTien : 0;
    }
}