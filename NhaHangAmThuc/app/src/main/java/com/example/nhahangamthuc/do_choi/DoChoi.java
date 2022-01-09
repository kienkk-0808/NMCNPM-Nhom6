package com.example.nhahangamthuc.do_choi;

public class DoChoi {

    private int id;
    private int soLuong;
    private String ten;

    public DoChoi() {
    }

    public DoChoi(int id, int soLuong, String ten) {
        this.id = id;
        this.soLuong = soLuong;
        this.ten = ten;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @Override
    public String toString() {
        return "DoChoi{" +
                ", id=" + id +
                ", soLuong=" + soLuong +
                ", ten='" + ten + '\'' +
                '}';
    }
}
