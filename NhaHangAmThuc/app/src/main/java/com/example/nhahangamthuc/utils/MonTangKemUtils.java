package com.example.nhahangamthuc.utils;

import java.util.ArrayList;
import java.util.List;

public class MonTangKemUtils {
    public static String refactor(String monTangKem) {
        String res = monTangKem;

        // sau dấu chấm phẩy có 1 khoảng trắng
        int i = 0;
        while (i < res.length() - 1) {
            if (res.charAt(i) == ';' && res.charAt(i + 1) != ' ') {
                res = res.substring(0, i + 1) + ' ' + res.substring(i + 1);
            }
            else i++;
        }

        // trước dấu chấm phẩy không có khoảng trắng
        while (i > 0) {
            if (res.charAt(i) == ';'  && res.charAt(i - 1) == ' ') {
                res = res.substring(0, i - 1) + res.substring(i);
            } else {
                i--;
            }
        }

        return res;
    }

    // FIXME cái này chưa test, ai dùng thì test trước nhé
    public static List<String> convertToList(String monTangKem) {
        StringBuilder ref = new StringBuilder(monTangKem);

        // loại bỏ khoảng trắng xung quanh dấu chấm phẩy
        int i = 0;
        while (i < ref.length()) {
            if (ref.charAt(i) == ';') {
                if (i + 1 < ref.length() && ref.charAt(i + 1) == ' ') {
                    ref.deleteCharAt(i + 1);
                }
                else if (i - 1 >= 0 && ref.charAt(i - 1) == ' ') {
                    ref.deleteCharAt(i - 1);
                } else {
                    i++;
                }
            } else {
                i++;
            }
        }

        List<String> res = new ArrayList<>();
        int viTriDauChamPhayTruocDo = -1;
        for (i = 0; i < ref.length(); ++i) {
            if (ref.charAt(i) == ';') {
                res.add(ref.substring(viTriDauChamPhayTruocDo + 1, i));
                viTriDauChamPhayTruocDo = i;
            }
        }
        res.add(ref.substring(viTriDauChamPhayTruocDo, ref.length()));

        return res;
    }
}
