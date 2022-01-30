package org.example.weatherBot.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final SimpleDateFormat SIMPLE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static String toNormal(long dt) {
        Date date = new Date(dt * 1000L);
        return SIMPLE_FORMAT.format(date);
    }

    public static String toNormalTime(long dt) {
        return toNormal(dt).substring(11);
    }
}
