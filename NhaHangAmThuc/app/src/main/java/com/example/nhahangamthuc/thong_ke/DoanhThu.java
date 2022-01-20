package com.example.nhahangamthuc.thong_ke;

import java.util.Objects;

public class DoanhThu {
    private String thang;
    private long tien;

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public long getTien() {
        return tien;
    }

    public void setTien(long tien) {
        this.tien = tien;
    }

    public DoanhThu(String thang, long tien) {
        this.thang = thang;
        this.tien = tien;
    }

    @Override
    public String toString() {
        return "DoanhThu{" +
                "thang='" + thang + '\'' +
                ", tien=" + tien +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoanhThu doanhThu = (DoanhThu) o;
        return tien == doanhThu.tien && thang.equals(doanhThu.thang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thang, tien);
    }
}
