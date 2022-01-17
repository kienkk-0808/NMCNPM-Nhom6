package com.example.nhahangamthuc.ban_an;

import com.example.nhahangamthuc.mon_an.MonAn;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BanAn implements Serializable {

    private Long idBan;
    private int trangThai;
    private int soNguoi;
    private DatBan dangAn;
    private List<MonAn> danhSachMon;
    private List<DatBan> danhSachDat;

    public BanAn(Long idBan, int soNguoi) {
        this.idBan = idBan;
        this.trangThai = 0;
        this.soNguoi = soNguoi;
    }

    public BanAn() {
    }

    public Long getIdBan() {
        return idBan;
    }

    public void setIdBan(Long idBan) {
        this.idBan = idBan;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(int soNguoi) {
        this.soNguoi = soNguoi;
    }

    public DatBan getDangAn() {
        return dangAn;
    }

    public void setDangAn(DatBan dangAn) {
        this.dangAn = dangAn;
    }

    public List<MonAn> getDanhSachMon() {
        return danhSachMon;
    }

    public void setDanhSachMon(List<MonAn> danhSachMon) {
        this.danhSachMon = danhSachMon;
    }

    public List<DatBan> getDanhSachDat() {
        return danhSachDat;
    }

    public void setDanhSachDat(List<DatBan> danhSachDat) {
        this.danhSachDat = danhSachDat;
    }

    @Override
    public String toString() {
        return "BanAn{" +
                "idBan=" + idBan +
                ", trangThai=" + trangThai +
                ", soNguoi=" + soNguoi +
                ", dangAn=" + dangAn +
                ", danhSachMon=" + danhSachMon +
                ", danhSachDat=" + danhSachDat +
                '}';
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("idBan", idBan);
        map.put("soNguoi", soNguoi);
        return map;
    }
}
