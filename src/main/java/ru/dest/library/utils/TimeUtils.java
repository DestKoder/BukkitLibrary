package ru.dest.library.utils;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static class DEFAULT_TIME_FORMAT {
        public static final String DD_MM_YYYY_POINTS = "dd.MM.yyyy";
        public static final String DD_MM_YYYY_SLASH = "dd/MM/yyyy";
        public static final String HH_MM = "HH:mm";
        public static final String HH_MM_SS = "HH:mm:ss";
        public static final String FULL_POINTS = HH_MM_SS + " " + DD_MM_YYYY_POINTS;
        public static final String FULL_SLASH = HH_MM_SS + " " + DD_MM_YYYY_SLASH;
    }

    public long getTimeForDateString(String date) throws ParseException {
        return new SimpleDateFormat(DEFAULT_TIME_FORMAT.DD_MM_YYYY_POINTS).parse(date).getTime();
    }

    public long getUnixTimeForDateString(String date) throws ParseException {
        return getTimeForDateString(date)/1000;
    }

    @NotNull
    public static String formatUnixTime(long unixTime, String format) {
        return formatTimeInMillis(unixTime*1000, format);
    }
    @NotNull
    public static String formatTimeInMillis(long time, String format){
        return new SimpleDateFormat(format).format(new Date(time));
    }

    public static long getCurrentUnixTime() {
        return getCurrentTime()/1000;
    }
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
