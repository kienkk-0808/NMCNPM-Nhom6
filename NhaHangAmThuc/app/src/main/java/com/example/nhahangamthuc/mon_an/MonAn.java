package com.example.nhahangamthuc.mon_an;

import java.util.HashMap;
import java.util.Map;

public class MonAn {
    private Long id;
    private String tenmonan, kieumonan, url;
    private int soLuong;
    private Long giatien;

    public MonAn() {
    }

    public Long getId() {
        return id;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public String getKieumonan() {
        return kieumonan;
    }

    public String getUrl() {
        return url;
    }

    public Long getGiatien() {
        return giatien;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public void setKieumonan(String kieumonan) {
        this.kieumonan = kieumonan;
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

    public void setGiatien(Long giatien) {
        this.giatien = giatien;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("tenmonan", tenmonan);
        map.put("kieumonan", kieumonan);
        map.put("giatien", giatien);
        map.put("url", url);
        return map;
    }
}