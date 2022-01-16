package com.example.nhahangamthuc.hoi_vien;

import java.util.HashMap;
import java.util.Map;

public class HoiVien {

    public static final int DONG = 1;
    public static final int BAC = 2;
    public static final int VANG = 3;
    public static final int KIM_CUONG = 4;

    private int id;
    private String soDienThoai;
    private String ten;
    private long diemTichLuy;
    private int hang;

    public HoiVien() {
    }

    public HoiVien(int id, String soDienThoai, String ten, long diemTichLuy, int hang) {
        this.id = id;
        this.soDienThoai = soDienThoai;
        this.ten = ten;
        this.diemTichLuy = diemTichLuy;
        this.hang = hang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public long getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(long diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

    public int getHang() {
        return hang;
    }

    public void setHang(int hang) {
        this.hang = hang;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        hashMap.put("ten", ten);
        hashMap.put("soDienThoai", soDienThoai);
        hashMap.put("diemTichLuy", diemTichLuy);
        hashMap.put("hang", hang);
        return hashMap;
    }
}
