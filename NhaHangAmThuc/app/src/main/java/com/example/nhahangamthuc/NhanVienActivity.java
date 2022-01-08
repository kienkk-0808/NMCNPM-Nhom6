package com.example.nhahangamthuc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class NhanVienActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerNhanVienAdapter viewPagerNhanVienAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        viewPagerNhanVienAdapter = new ViewPagerNhanVienAdapter(this);
        viewPager2.setAdapter(viewPagerNhanVienAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Gọi món");
                    break;
                case 1:
                    tab.setText("Đặt bàn");
                    break;
                case 2:
                    tab.setText("Hội viên");
                    break;
            }
        }).attach();
    }
}