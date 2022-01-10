package com.example.nhahangamthuc.mon_an;

public class MonAn {
    private Long id;
    private String tenmonan, kieumonan, url;
    private int soLuong;
    private Long giatien;

    public MonAn() {
    }

    public MonAn(Long id, String tenmonan, String kieumonan, String url, Long giatien) {
        this.id = id;
        this.tenmonan = tenmonan;
        this.kieumonan = kieumonan;
        this.url = url;
        this.giatien = giatien;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public String getKieumonan() {
        return kieumonan;
    }

    public void setKieumonan(String kieumonan) {
        this.kieumonan = kieumonan;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Long getGiatien() {
        return giatien;
    }

    public void setGiatien(Long giatien) {
        this.giatien = giatien;
    }
}