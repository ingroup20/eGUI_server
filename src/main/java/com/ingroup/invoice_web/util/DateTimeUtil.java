package com.ingroup.invoice_web.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }


    public static String getYearMonthROC(String invoiceDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(invoiceDate, formatter);

        String rocYear = String.valueOf(localDateTime.getYear() - 1911); // 轉民國年
        int month = localDateTime.getMonthValue();    // 取出月份
        return rocYear + (month / 2 == 0 ? month : month + 1); //期別月份只有偶數月
    }

    public static String getYearMonthROC(LocalDate localDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        localDate.format(formatter);

        String rocYear = String.valueOf(localDate.getYear() - 1911); // 轉民國年
        int month = localDate.getMonthValue();    // 取出月份
        int eGUIMonth = month % 2 == 0 ? month : month + 1; //期別月份只有偶數月
        return rocYear + String.format("%02d", eGUIMonth); //補0到2位數
    }
}