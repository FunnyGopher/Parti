package com.github.funnygopher.parti.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by FunnyGopher
 */
public class DateUtil {

    // A date format to use across the entire application
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("M/d/yyyy h:mm a",
            Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("M/d/yyyy", Locale
            .getDefault());
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("h:mm a", Locale
            .getDefault());

    public static String calendarToString(Calendar calendar) {
        return DATE_TIME_FORMAT.format(calendar.getTime());
    }

    public static Calendar stringToCalendar(String string) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(DATE_TIME_FORMAT.parse(string));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    public static String timeToString(Calendar calendar) {
        return TIME_FORMAT.format(calendar.getTime());
    }

    public static String dateToString(Calendar calendar) {
        return DATE_FORMAT.format(calendar.getTime());
    }
}
