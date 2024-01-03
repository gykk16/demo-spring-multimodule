package io.glory.mcore.util.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    private static final String TIME_FORMAT      = "HH:mm:ss";
    private static final String DATE_FORMAT      = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private DateFormatter() {
    }

    /**
     * 전달된 날짜를 yyyy-MM-dd 형식으로 반환
     * <p>
     * date 가 null 인 경우 "" 를 반환
     *
     * @param date 날짜
     * @return yyyy-MM-dd 형식의 날짜
     */
    public static String formatDate(LocalDate date) {
        return formatDate(date, DATE_FORMAT);
    }

    /**
     * 전달된 날짜를 format 형식으로 반환
     * <p>
     * date 가 null 인 경우 "" 를 반환
     *
     * @param date   날짜
     * @param format 날짜 포맷
     * @return format 형식의 날짜
     */
    public static String formatDate(LocalDate date, String format) {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 전달된 일시를 yyyy-MM-dd HH:mm:ss 형식으로 반환
     * <p>
     * dateTime 이 null 인 경우 "" 를 반환
     *
     * @param dateTime 일시
     * @return yyyy-MM-dd HH:mm:ss 형식의 날짜
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return formatDateTime(dateTime, DATE_TIME_FORMAT);
    }

    /**
     * 전달된 일시를 format 형식으로 반환
     * <p>
     * dateTime 이 null 인 경우 "" 를 반환
     *
     * @param dateTime 일시
     * @param format   날짜 포맷
     * @return format 형식의 날짜
     */
    public static String formatDateTime(LocalDateTime dateTime, String format) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public static String getTimeStr() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    public static String getDateStr() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static String getDateTimeStr() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    public static String getTimeStr(String format) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static String getDateStr(String format) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static String getDateTimeStr(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

}
