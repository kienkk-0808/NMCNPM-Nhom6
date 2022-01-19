package com.example.nhahangamthuc.mon_an;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MonAn {
    private Long id;
    private String tenmonan, kieumonan, url;
    private int soLuong;
    private Long giatien;
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

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

    @Override
    public String toString() {
        return "MonAn{" +
                "id=" + id +
                ", tenmonan='" + tenmonan + '\'' +
                ", kieumonan='" + kieumonan + '\'' +
                ", url='" + url + '\'' +
                ", soLuong=" + soLuong +
                ", giatien=" + giatien +
                ", isChecked=" + isChecked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonAn monAn = (MonAn) o;
        return tenmonan.equals(monAn.tenmonan) && Objects.equals(giatien, monAn.giatien);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenmonan, giatien);
    }
}