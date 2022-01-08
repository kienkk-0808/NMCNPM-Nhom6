package com.example.nhahangamthuc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class QuanLyActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerQuanLyAdapter viewPagerQuanLyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        viewPagerQuanLyAdapter = new ViewPagerQuanLyAdapter(this);
        viewPager2.setAdapter(viewPagerQuanLyAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Món ăn");
                    break;
                case 1:
                    tab.setText("Bàn ăn");
                    break;
                case 2:
                    tab.setText("Sự kiện");
                    break;
                case 3:
                    tab.setText("Đồ chơi");
                    break;
            }
        }).attach();
    }
}