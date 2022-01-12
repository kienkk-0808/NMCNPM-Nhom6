package com.example.nhahangamthuc.su_kien;

import com.example.nhahangamthuc.mon_an.MonAn;

import java.util.Date;
import java.util.List;

public class SuKien {
    private int id;
    private String ten;
    private Date ngay;
    private List<MonAn> monAnTangKemList;

    public SuKien() {
    }

    public SuKien(int id, String ten, Date ngay, List<MonAn> monAnTangKemList) {
        this.id = id;
        this.ten = ten;
        this.ngay = ngay;
        this.monAnTangKemList = monAnTangKemList;
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

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public List<MonAn> getMonAnTangKemList() {
        return monAnTangKemList;
    }

    public void setMonAnTangKemList(List<MonAn> monAnTangKemList) {
        this.monAnTangKemList = monAnTangKemList;
    }
}
