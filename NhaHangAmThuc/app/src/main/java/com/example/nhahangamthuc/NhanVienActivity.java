package com.example.nhahangamthuc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.nhahangamthuc.fragment.DanhSachBanAnFragment;
import com.example.nhahangamthuc.fragment.GoiMonMangVeFragment;
import com.example.nhahangamthuc.fragment.HoiVienFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class NhanVienActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private static final int FRAGMENT_1 = 1;
    private static final int FRAGMENT_2 = 2;
    private static final int FRAGMENT_3 = 3;

    private int currentFragment = FRAGMENT_1;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new DanhSachBanAnFragment());
        navigationView.getMenu().findItem(R.id.nav_danh_sach_ban_an).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_danh_sach_ban_an:
                if (currentFragment != FRAGMENT_1) {
                    replaceFragment(new DanhSachBanAnFragment());
                    currentFragment = FRAGMENT_1;
                    navigationView.getMenu().findItem(R.id.nav_danh_sach_ban_an).setChecked(true);
                    navigationView.getMenu().findItem(R.id.nav_goi_mon_mang_ve).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_quan_ly_hoi_vien).setChecked(false);
                }
                break;
            case R.id.nav_goi_mon_mang_ve:
                if (currentFragment != FRAGMENT_2) {
                    replaceFragment(new GoiMonMangVeFragment());
                    currentFragment = FRAGMENT_2;
                    navigationView.getMenu().findItem(R.id.nav_danh_sach_ban_an).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_goi_mon_mang_ve).setChecked(true);
                    navigationView.getMenu().findItem(R.id.nav_quan_ly_hoi_vien).setChecked(false);
                }
                break;
            case R.id.nav_quan_ly_hoi_vien:
                if (currentFragment != FRAGMENT_3) {
                    replaceFragment(new HoiVienFragment());
                    currentFragment = FRAGMENT_3;
                    navigationView.getMenu().findItem(R.id.nav_danh_sach_ban_an).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_goi_mon_mang_ve).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_quan_ly_hoi_vien).setChecked(true);
                }
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer( GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}