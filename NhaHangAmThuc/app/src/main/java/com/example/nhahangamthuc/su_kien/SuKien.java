package com.example.nhahangamthuc.su_kien;

import java.util.HashMap;
import java.util.Map;

public class SuKien {

    private int id;
    private String ten;
    private String startDate;
    private String endDate;
    private String monTangKem;

    public SuKien() {}

    public SuKien(int id, String ten, String startDate, String endDate, String monTangKem) {
        this.id = id;
        this.ten = ten;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monTangKem = monTangKem;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMonTangKem() {
        return monTangKem;
    }

    public void setMonTangKem(String monTangKem) {
        this.monTangKem = monTangKem;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("ten", ten);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("monTangKem", monTangKem);
        return map;
    }

    @Override
    public String toString() {
        return "SuKien{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", monTangKem='" + monTangKem + '\'' +
                '}';
    }
}
