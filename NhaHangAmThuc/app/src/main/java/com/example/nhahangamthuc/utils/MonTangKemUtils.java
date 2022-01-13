package com.example.nhahangamthuc.utils;

public class MonTangKemUtils {
    public static String refactor(String monTangKem) {
        String res = monTangKem;
        int i = 0;
        while (i < res.length() - 1) {
            if (res.charAt(i) == ';' && res.charAt(i + 1) != ' ') {
                res = res.substring(0, i + 1) + ' ' + res.substring(i + 1);
            }
            else i++;
        }

        return res;
    }

    // TODO chuyển monTangKem từ String sang List<MonAn> hay gì gì đấy
}
