package com.example.nhahangamthuc.dinh_danh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.ban_an.BanAnFragment;
import com.example.nhahangamthuc.do_choi.DoChoiFragment;
import com.example.nhahangamthuc.mon_an.MonAnFragment;
import com.example.nhahangamthuc.su_kien.SuKienFragment;
import com.example.nhahangamthuc.fragment.ThongKeFragment;
import com.google.android.material.navigation.NavigationView;

public class QuanLyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private static final int FRAGMENT_1 = 1;
    private static final int FRAGMENT_2 = 2;
    private static final int FRAGMENT_3 = 3;
    private static final int FRAGMENT_4 = 4;
    private static final int FRAGMENT_5 = 5;

    private int currentFragment = FRAGMENT_1;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new ThongKeFragment());
        navigationView.getMenu().findItem(R.id.nav_thong_ke).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_thong_ke:
                if (currentFragment != FRAGMENT_1) {
                    replaceFragment(new ThongKeFragment());
                    currentFragment = FRAGMENT_1;
                    navigationView.getMenu().findItem(R.id.nav_thong_ke).setChecked(true);
                    navigationView.getMenu().findItem(R.id.nav_mon_an).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_ban_an).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_su_kien).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_do_choi).setChecked(false);
                }
                break;
            case R.id.nav_mon_an:
                if (currentFragment != FRAGMENT_2) {
                    replaceFragment(new MonAnFragment());
                    currentFragment = FRAGMENT_2;
                    navigationView.getMenu().findItem(R.id.nav_thong_ke).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_mon_an).setChecked(true);
                    navigationView.getMenu().findItem(R.id.nav_ban_an).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_su_kien).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_do_choi).setChecked(false);
                }
                break;
            case R.id.nav_ban_an:
                if (currentFragment != FRAGMENT_3) {
                    replaceFragment(new BanAnFragment());
                    currentFragment = FRAGMENT_3;
                    navigationView.getMenu().findItem(R.id.nav_thong_ke).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_mon_an).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_ban_an).setChecked(true);
                    navigationView.getMenu().findItem(R.id.nav_su_kien).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_do_choi).setChecked(false);
                }
                break;
            case R.id.nav_su_kien:
                if (currentFragment != FRAGMENT_4) {
                    replaceFragment(new SuKienFragment());
                    currentFragment = FRAGMENT_4;
                    navigationView.getMenu().findItem(R.id.nav_thong_ke).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_mon_an).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_ban_an).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_su_kien).setChecked(true);
                    navigationView.getMenu().findItem(R.id.nav_do_choi).setChecked(false);
                }
                break;
            case R.id.nav_do_choi:
                if (currentFragment != FRAGMENT_5) {
                    replaceFragment(new DoChoiFragment());
                    currentFragment = FRAGMENT_5;
                    navigationView.getMenu().findItem(R.id.nav_thong_ke).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_mon_an).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_ban_an).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_su_kien).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_do_choi).setChecked(true);
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