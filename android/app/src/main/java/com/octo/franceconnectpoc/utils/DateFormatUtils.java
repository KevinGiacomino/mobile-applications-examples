package com.octo.franceconnectpoc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lda on 18/03/15.
 */
public final class DateFormatUtils {

    private DateFormatUtils() {
    }

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    public static final Locale LOCALE_APPLICATION = Locale.FRANCE;

    // ----------------------------------
    // UTILITY METHODS
    // ----------------------------------
    public static SynchronizedSimpleDateFormat getUserInfoDateFormat() {
        return new SynchronizedSimpleDateFormat("yyyy-MM-dd");
    }

    public static SynchronizedSimpleDateFormat getContentDateFormat() {
        return new SynchronizedSimpleDateFormat("dd/MM/yyyy");
    }

    // ----------------------------------
    // INNER CLASSES
    // ----------------------------------

    // http://www.codefutures.com/weblog/andygrove/2007/10/simpledateformat-and-thread-safety.html
    public static final class SynchronizedSimpleDateFormat {

        private SimpleDateFormat sdf;

        private SynchronizedSimpleDateFormat(String pattern) {
            sdf = new SimpleDateFormat(pattern, LOCALE_APPLICATION);
        }

        public synchronized String format(Date date) {
            return sdf.format(date);
        }

        public synchronized String format(long l) {
            return sdf.format(l);
        }

        public synchronized Date parse(String string) throws ParseException {
            return sdf.parse(string);
        }
    }

}
