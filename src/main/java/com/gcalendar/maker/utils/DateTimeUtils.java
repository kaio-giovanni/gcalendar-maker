package com.gcalendar.maker.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public enum Periodicity {
        DAILY,
        WEEKLY,
        MONTHLY;
    }

    public static Periodicity getPeriodicity (LocalDateTime startDate, LocalDateTime endDate) {
        long diffDays = ChronoUnit.DAYS.between(startDate, endDate);
        if (diffDays <= 1) {
            return Periodicity.DAILY;
        } else if (diffDays > 2 && diffDays <= 7) {
            return Periodicity.WEEKLY;
        } else {
            return Periodicity.MONTHLY;
        }
    }

    public static int getNumberOfWeeks (int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }

    public static String getDate (int year, int month, int weekOfMonth, int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.WEEK_OF_MONTH, weekOfMonth);
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        Date day = calendar.getTime();
        LocalDate date = day.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date.toString();
    }

    private static LocalDate getLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String getDayOfWeek(String date) {
        LocalDate localDate = getLocalDate(date);
        return localDate.getDayOfWeek().toString();
    }

    public static int getDayOfMonth(String date) {
        LocalDate localDate = getLocalDate(date);
        return localDate.getDayOfMonth();
    }

    public static String getMonth(String date) {
        LocalDate localDate = getLocalDate(date);
        return localDate.getMonth().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.ENGLISH);
    }

    public static String getMonthName (LocalDate date) {
        return date.getMonth().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.ENGLISH);
    }

}
