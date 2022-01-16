package com.example.nhahangamthuc.ban_an;


public class DatBan {
    private Long id;
    private String ten;
    private String sdt;
    private int soNguoi;
    private String thoiGian;
    //private Long idBan;

    public DatBan() {
    }

    public DatBan(Long id, String ten, String sdt, int soNguoi, String thoiGian) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
        this.soNguoi = soNguoi;
        this.thoiGian = thoiGian;
    }

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

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

//    public Long getIdBan() {
//        return idBan;
//    }
//
//    public void setIdBan(Long idBan) {
//        this.idBan = idBan;
//    }
}
