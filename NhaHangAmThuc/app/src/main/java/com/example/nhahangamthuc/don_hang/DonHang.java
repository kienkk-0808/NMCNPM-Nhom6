package com.example.nhahangamthuc.don_hang;

import com.example.nhahangamthuc.mon_an.MonAn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonHang {
    private List<MonAn> monAnList;
    private long tongTien = 0;
    private String ngay;

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public DonHang() {}

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }

    public List<MonAn> getMonAnList() {
        return monAnList;
    }

    public void setMonAnList(List<MonAn> monAnList) {
        this.monAnList = monAnList;
    }

    public DonHang(List<MonAn> monAnList) {
        for (int i = 0; i < monAnList.size(); i++) {
            tongTien += monAnList.get(i).getGiatien();
        }
        this.monAnList = monAnList;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("tongTien", tongTien);
        map.put("ngay", ngay);
        return map;
    }
}
