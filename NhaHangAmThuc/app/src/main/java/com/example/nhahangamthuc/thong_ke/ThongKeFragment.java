package com.example.nhahangamthuc.thong_ke;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.do_choi.DoChoi;
import com.example.nhahangamthuc.don_hang.DonHang;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ThongKeFragment extends Fragment {

    List<DoanhThu> doanhThuList;
    Context context;
    BarChart barChart;

    public ThongKeFragment() {
        // Required empty public constructor
    }

    public static ThongKeFragment newInstance() {
        return new ThongKeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        barChart = view.findViewById(R.id.bar_chart);
        context = getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("list_don_hang");
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doanhThuList = new ArrayList<>();
                List<DonHang> donHangList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DonHang donHang = dataSnapshot.getValue(DonHang.class);
                    donHangList.add(donHang);
                }
                for (int i = 0; i < donHangList.size(); i++) {
                    DonHang donHang = donHangList.get(i);
                    String thang = donHang.getNgay().substring(3);
                    long tien = donHang.getTongTien();
                    DoanhThu doanhThu = new DoanhThu(thang, tien);
                    doanhThuList.add(doanhThu);
                    for (int j = 0; j < doanhThuList.size() - 1; j++) {
                        DoanhThu doanhThu1 = doanhThuList.get(j);
                        if (doanhThu.getThang().equals(doanhThu1.getThang())) {
                            doanhThuList.get(j).setTien(doanhThu1.getTien()+ doanhThu.getTien());
                            doanhThuList.remove(doanhThu);
                            break;
                        }
                    }
                }
                Log.v("TAG", "DTL" + doanhThuList.toString());

                List<String> xText = new ArrayList<>();
                List<BarEntry> entries = new ArrayList<>();
                for (int i = 0; i < doanhThuList.size(); i++) {
                    DoanhThu doanhThu = doanhThuList.get(i);
                    entries.add(new BarEntry(i, doanhThu.getTien()));
                    xText.add(doanhThu.getThang());
                }

                BarDataSet barDataSet = new BarDataSet(entries, "Doanh thu");
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(12f);
                BarData barData = new BarData(barDataSet);
                barChart.setFitBars(true);
                barChart.setData(barData);
                barChart.animateY(2000);
                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xText));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Đã xảy ra lỗi khi lấy danh sách dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}