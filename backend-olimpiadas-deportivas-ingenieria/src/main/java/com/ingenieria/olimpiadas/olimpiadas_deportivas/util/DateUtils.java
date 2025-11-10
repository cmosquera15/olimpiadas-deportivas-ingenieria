package com.ingenieria.olimpiadas.olimpiadas_deportivas.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class DateUtils {

    private DateUtils() {}

    // ===== Parsers básicos ISO =====
    public static LocalDate parseDate(String isoDate) {
        return (isoDate == null || isoDate.isBlank()) ? null : LocalDate.parse(isoDate);
    }

    public static LocalTime parseTime(String isoTime) {
        return (isoTime == null || isoTime.isBlank()) ? null : LocalTime.parse(isoTime);
    }

    // ===== Zona horaria por defecto (Bogotá) =====
    public static ZoneId bogotaZone() {
        return ZoneId.of(Constants.ZONA_BOGOTA);
    }

    // ===== Helpers comunes =====
    public static LocalDateTime combine(LocalDate date, LocalTime time) {
        if (date == null || time == null) return null;
        return LocalDateTime.of(date, time);
    }

    public static ZonedDateTime atBogota(LocalDate date, LocalTime time) {
        LocalDateTime ldt = combine(date, time);
        return ldt == null ? null : ldt.atZone(bogotaZone());
    }

    public static boolean isBetween(LocalDate d, LocalDate from, LocalDate to) {
        if (d == null || from == null || to == null) return false;
        return (d.isEqual(from) || d.isAfter(from)) && (d.isEqual(to) || d.isBefore(to));
    }

    public static boolean sameDay(LocalDate d1, LocalDate d2) {
        return Objects.equals(d1, d2);
    }

    public static String formatDate(LocalDate d, String pattern) {
        if (d == null) return null;
        return d.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatTime(LocalTime t, String pattern) {
        if (t == null) return null;
        return t.format(DateTimeFormatter.ofPattern(pattern));
    }
}
