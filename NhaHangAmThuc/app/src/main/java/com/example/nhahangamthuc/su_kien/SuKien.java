package com.example.nhahangamthuc.su_kien;

import java.util.HashMap;
import java.util.Map;

public class SuKien {
    private int id;
    private String ten;
    private String ngayThang;
    private String monTangKem;

    public SuKien() {
    }

    public SuKien(int id, String ten, String ngayThang, String monTangKem) {
        this.id = id;
        this.ten = ten;
        this.ngayThang = ngayThang;
        this.monTangKem = monTangKem;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("ten", ten);
        map.put("ngayThang", ngayThang);
        map.put("monTangKem", monTangKem);
        return map;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }

    public String getMonTangKem() {
        return monTangKem;
    }

    public void setMonTangKem(String monTangKem) {
        this.monTangKem = monTangKem;
    }
}
