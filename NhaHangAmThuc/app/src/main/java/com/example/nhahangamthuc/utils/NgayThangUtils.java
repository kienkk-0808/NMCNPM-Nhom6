package com.example.nhahangamthuc.utils;

public class NgayThangUtils {

    public static boolean kiemTraNgayThangHopLe(int ngay, int thang) {
        switch (thang) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return ngay >= 1 && ngay <= 31;
            case 4: case 6: case 9: case 11:
                return ngay >= 1 && ngay <= 30;
            case 2:
                return ngay >= 1 && ngay <= 29;
            default:
                return false;
        }
    }

    public static String hienThiNgayThang(String ddmm) {
        if (ddmm.length() != 4) {
            return "Ngày tháng không hợp lệ! (" + ddmm + ")";
        }
        String dd = ddmm.substring(0, 2);
        String mm = ddmm.substring(2, 4);
        int ngay = Integer.parseInt(dd);
        int thang = Integer.parseInt(mm);

        if (!kiemTraNgayThangHopLe(ngay, thang)) {
            return "Ngày tháng không hợp lệ! (" + ddmm + ")";
        }

        return "Ngày " + ngay + " tháng " + thang;
    }
}
