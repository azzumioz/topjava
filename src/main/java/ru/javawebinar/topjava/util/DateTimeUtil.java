package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final LocalDate DATEMIN = LocalDate.of(2000, Month.JANUARY, 1);
    public static final LocalDate DATEMAX = LocalDate.now();
    public static final LocalTime TIMEMIN = LocalTime.of(0, 0);
    public static final LocalTime TIMEMAX = LocalTime.of(23, 59);

    public static <T extends Comparable<T>> boolean isBetween(T lt, T startTime, T endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
