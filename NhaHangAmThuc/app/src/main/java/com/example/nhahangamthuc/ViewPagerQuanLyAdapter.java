package com.example.nhahangamthuc;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.nhahangamthuc.fragment.BanAnFragment;
import com.example.nhahangamthuc.fragment.DoChoiFragment;
import com.example.nhahangamthuc.fragment.MonAnFragment;
import com.example.nhahangamthuc.fragment.SuKienFragment;

public class ViewPagerQuanLyAdapter extends FragmentStateAdapter {

    public ViewPagerQuanLyAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new BanAnFragment();
            case 2:
                return new SuKienFragment();
            case 3:
                return new DoChoiFragment();
            case 0:
            default:
                return new MonAnFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
