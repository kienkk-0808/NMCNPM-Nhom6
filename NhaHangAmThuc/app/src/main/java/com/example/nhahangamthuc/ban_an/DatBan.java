package com.example.nhahangamthuc.ban_an;

import java.sql.Timestamp;

public class DatBan {
    private Long id;
    private String ten;
    private String sdt;
    private int soNguoi;
    private Timestamp thoiGian;
    private Long idBan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(int soNguoi) {
        this.soNguoi = soNguoi;
    }

    public Timestamp getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Timestamp thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Long getIdBan() {
        return idBan;
    }

    public void setIdBan(Long idBan) {
        this.idBan = idBan;
    }
}
