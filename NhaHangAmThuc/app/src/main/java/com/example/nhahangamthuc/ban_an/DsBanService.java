package com.example.nhahangamthuc.ban_an;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DsBanService {
    public static final long millisPH = 60 * 60 * 1000L;//so mili giay moi gio
    public boolean checkTime(Timestamp thoiGian, Timestamp t1, Timestamp t2) {
        if (thoiGian.after(t1) && thoiGian.before(t2)) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<String> getListNgay() {
        List<String> list = new ArrayList<>();
        LocalDate cur = LocalDate.now();
        for (long i = 0; i < 3; i++) {
            list.add(cur.plusDays(i).toString());
        }
        return list;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<String> getListGio() {
        List<String> list = new ArrayList<>();
        LocalTime cur = LocalTime.of(8, 0);
        while (cur.isBefore(LocalTime.of(21, 30))) {
            list.add(cur.toString());
            cur = cur.plusMinutes(30);
        }
        return list;
    }
    public void setTrangThaiListBan(List<BanAn> listBan, Timestamp time) {
        if (listBan != null) {
            for (int i = 0; i < listBan.size(); i++) {
                BanAn ban = listBan.get(i);
                Timestamp t1 = new Timestamp(0L);
                Timestamp t2 = new Timestamp(0L);
                if (ban.getDanhSachMon() != null && ban.getDangAn() != null) {
                    //dang an
                    t1 = Timestamp.valueOf(ban.getDangAn().getThoiGian());
                    t2.setTime(t1.getTime() + millisPH * 2);
                    if (checkTime(time, t1, t2)) {
                        ban.setTrangThai(2);
                        continue;
                    }
                }
                if (ban.getDanhSachDat() != null) {
                    //check dat ban
                    int d = 0;
                    List<DatBan> dsDatBan = ban.getDanhSachDat();
                    for (int j = 0; j < dsDatBan.size(); j++) {
                        Timestamp t = Timestamp.valueOf(dsDatBan.get(j).getThoiGian());
                        //t1 = t;
                        t1.setTime(t.getTime() - millisPH * 2);
                        t2.setTime(t.getTime() + millisPH * 2);//t2 la sau 2h
                        if (checkTime(time, t1, t2)) {//neu t1<time<t2 -> ban da co nguoi dat
                            ban.setTrangThai(1);
                            d = 1;
                            continue;
                        }
                    }
                    if (d == 1)
                        continue;
                }
                //ban trong
                ban.setTrangThai(0);
            }
        }
    }
    public List<BanAn> setListBanByKeyword(List<BanAn> listBan, String keyword){

        return listBan;
    }
}
