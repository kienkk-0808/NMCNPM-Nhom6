package com.example.nhahangamthuc;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.nhahangamthuc.fragment.DatBanFragment;
import com.example.nhahangamthuc.fragment.GoiMonFragment;
import com.example.nhahangamthuc.fragment.HoiVienFragment;

public class ViewPagerNhanVienAdapter extends FragmentStateAdapter {

    public ViewPagerNhanVienAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new DatBanFragment();
            case 2:
                return new HoiVienFragment();
            case 0:
            default:
                return new GoiMonFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
