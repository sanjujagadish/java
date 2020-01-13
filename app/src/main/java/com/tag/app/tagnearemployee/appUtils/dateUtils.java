package com.tag.app.tagnearemployee.appUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by anjum on 01/11/19.
 */

public class dateUtils {

    public static String convertToHHSSFormat(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Date date = format.parse(dateString);
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss aa");
            formatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            return formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }
}
