package com.gcalendar.maker.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
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

    public static String getMonthName (LocalDate date) {
        return date.getMonth().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.ENGLISH);
    }

    public static int getNumberOfWeeks (int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }
}
