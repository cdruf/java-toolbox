package util;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;

public class JodaHelper {

    /**
     * @param str
     *            e.g. 2010-06-20
     */
    public static LocalDate createLD(String str) {
        String[] s = str.split("-");
        return new LocalDate(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
    }

    /**
     * @param str
     *            e.g. 14:12:57.001
     */
    public static LocalTime createLT(String str) {
        String[] s = str.split(":.");
        return new LocalTime(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
    }

    /**
     * @param str
     *            e.g. 1999-12-31 14:12:57.001
     */
    public static LocalDateTime createLDT(String str) {
        String[] s = str.split("[-\\s:.]");
        return new LocalDateTime(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]),
                Integer.parseInt(s[3]), Integer.parseInt(s[4]), Integer.parseInt(s[5]));
    }

    public static LocalDateTime createLDT(LocalDate ld, LocalTime lt) {
        return new LocalDateTime(ld.getYear(), ld.getMonthOfYear(), ld.getDayOfMonth(), lt.getHourOfDay(),
                lt.getMinuteOfHour(), lt.getSecondOfMinute());
    }

    public static int diffDays(LocalDate a, LocalDate b) {
        return new Period(a, b).getDays(); // 2. arg - 1. arg
    }

    public static Period diffTimeOfDay(LocalDateTime a, LocalDateTime b) {
        return new Period(a.toLocalTime(), b.toLocalTime()); // 2. arg - 1. arg
    }

    public static int diffInSec(LocalDateTime a, LocalDateTime b) {
        return new Period(a, b).getSeconds();
    }

    public static int distInMin(LocalDateTime a, LocalDateTime b) {
        return Math.abs(new Period(a, b).getMinutes());
    }

}
