package de.iisys.smartgrids.libvdew.service;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Class to create the VDEW encoded Date or Time-String.
  */
public class DateTimeService {

    /**
     * Creates a new String with the given date. The length of the date must be
     * between 6 to 7 characters. The zeros behind are for the time.
     *
     * @param date the date
     * @return the expanded date with the 6 zeros behind
     */
    protected static String expandDateString(String date) {
        StringBuilder expandedDate = new StringBuilder(13);

        switch (date.length()) {
            case 6:
                expandedDate.append('2').append(date);
                break;
            case 7:
                expandedDate.append(date);
                break;
            default:
                throw new IllegalArgumentException("Given VDEW encoded Date-String must have between 6 to 7 characters, but we got " + date.length() + " characters being '" + date + "'");
        }

        expandedDate.append("000000");

        return expandDateTimeString(expandedDate.toString());
    }

    /**
     * Creates a String with the given time. The length of the time must be
     * between 4 to 7 characters. The zeros before shows that the String is for
     * the time.
     *
     * @param time the time
     * @return the expanded time with the 6 zeros before are for the date.
     */
    protected static String expandTimeString(String time) {
        StringBuilder expandedTime = new StringBuilder(13);

        expandedTime.append("000000");

        switch (time.length()) {
            case 4:
                expandedTime.insert(0, '2').append(time).append("00");
                break;
            case 5:
                expandedTime.insert(0, time.substring(0, 1)).append(time.substring(1)).append("00");
                break;
            case 6:
                expandedTime.insert(0, '2').append(time);
                break;
            case 7:
                expandedTime.insert(0, time.substring(0, 1)).append(time.substring(1));
                break;
            default:
                throw new IllegalArgumentException("Given VDEW encoded Time-String must have between 4 to 7 characters, but we got " + time.length() + " characters being '" + time + "'");
        }

        return expandDateTimeString(expandedTime.toString());
    }

    /**
     * Returns the date time, if the length is 13 characters. If the length is
     * between 10 to 12 characters then the adds zeros.
     *
     * @param dateTime the date and time merged in a String
     * @return the date and time String with the added zeros if its necessary
     */
    protected static String expandDateTimeString(String dateTime) {
        if (dateTime.length() == 13) {
            return dateTime;
        }

        StringBuilder expandedDateTime = new StringBuilder(13);

        switch (dateTime.length()) {
            case 10:
                expandedDateTime.append('2').append(dateTime).append("00");
                break;
            case 11:
                expandedDateTime.append(dateTime).append("00");
                break;
            case 12:
                expandedDateTime.append('2').append(dateTime);
                break;
            default:
                throw new IllegalArgumentException("Given VDEW encoded DateTime-String must have between 10 to 13 characters, but we got " + dateTime.length() + " characters being '" + dateTime + "'");
        }

        return expandedDateTime.toString();
    }

    /**
     * Returns the first character of the given String for the date and time as
     * Integer.
     *
     * @param dateTime the date and time merged in a String
     * @return the season
     */
    protected static int getSeason(String dateTime) {
        return Integer.parseInt(dateTime.substring(0, 1));
    }

    /**
     * Returns the year of the date added with 2000, if the year is not between
     * 90 and 99. If the year is between 90 and 99 then it will be added with
     * 1900.
     *
     * @param dateTime the date and time merged in a String
     * @return the year
     */
    protected static int getYear(String dateTime) {
        int year = Integer.parseInt(dateTime.substring(1, 3));
        return year >= 90 && year <= 99 ? 1900 + year : 2000 + year;
    }

    /**
     * Returns the month of the given date and time String subtracted by 1.
     *
     * @param dateTime the date and time merged in a String
     * @return the month
     */
    protected static int getMonth(String dateTime) {
        return Integer.parseInt(dateTime.substring(3, 5)) - 1;
    }

    /**
     * Returns the day of the given date and time String.
     *
     * @param dateTime the date and time added merged in a String
     * @return the day
     */
    protected static int getDay(String dateTime) {
        return Integer.parseInt(dateTime.substring(5, 7));
    }

    /**
     * Returns the hour of the given date and time String.
     *
     * @param dateTime the date and time merged in a String
     * @return the hour
     */
    protected static int getHour(String dateTime) {
        return Integer.parseInt(dateTime.substring(7, 9));
    }

    /**
     * Returns the minute of the given date and time String.
     *
     * @param dateTime the date and time merged in a String
     * @return the minute
     */
    protected static int getMinute(String dateTime) {
        return Integer.parseInt(dateTime.substring(9, 11));
    }

    /**
     * Returns the second of the given date and time String.
     *
     * @param dateTime the date and time merged in a String
     * @return the second
     */
    protected static int getSecond(String dateTime) {
        return Integer.parseInt(dateTime.substring(11, 13));
    }

    /**
     * Returns the given date and calls {@link #parseDateTime(java.lang.String).
     *
     * @param date the date
     * @return the date
     */
    public static Calendar parseDate(String date) {
        date = expandDateString(date);
        return parseDateTime(date);
    }

    /**
     * Returns the given time and calls {@link #parseDateTime(java.lang.String).
     *
     * @param time the time
     * @return the time
     */
    public static Calendar parseTime(String time) {
        time = expandTimeString(time);
        return parseDateTime(time);
    }

    /**
     * Creates a new {@link Calendar} with the given date and time String
     *
     * @param dateTime the date and time merged in a String
     * @return the date and time with the correct time zone
     */
    public static Calendar parseDateTime(String dateTime) {
        dateTime = expandDateTimeString(dateTime);

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeZone(getSeason(dateTime) == 2 ? TimeZone.getTimeZone("UTC") : TimeZone.getDefault());
        calendar.set(getYear(dateTime), getMonth(dateTime), getDay(dateTime), getHour(dateTime), getMinute(dateTime), getSecond(dateTime));

        return calendar;
    }

    /**
     * Return whether it is Summer time or not.
     *
     * @param calendar the date and time
     * @return whether is is Summer time or not
     */
    protected static boolean isSummerTime(Calendar calendar) {
        return calendar.getTimeZone().inDaylightTime(calendar.getTime());
    }

    /**
     * Returns whether it is UTC or not.
     *
     * @param calendar the date and time
     * @return whether is is UTC or not.
     */
    protected static boolean isUTC(Calendar calendar) {
        return calendar.getTimeZone().getID().equals("UTC");
    }

    /**
     * Formats the date.
     *
     * @param calendar the date and time
     * @return the date with a format
     */
    public static String formatDate(Calendar calendar) {
        return String.format("%1$ty%1$tm%1$td", calendar);
    }

    /**
     * Formats the date with season.
     *
     * @param calendar the date and time
     * @return the date with season with a format
     */
    public static String formatDateWithSeason(Calendar calendar) {
        return String.format("%2$d%1$ty%1$tm%1$td", calendar, isUTC(calendar) ? 2 : isSummerTime(calendar) ? 1 : 0);
    }

    /**
     * Formats the time.
     *
     * @param calendar the date and time
     * @return the time with a format
     */
    public static String formatTimeShort(Calendar calendar) {
        return String.format("%1$tH%1$tM", calendar);
    }

    /**
     * Formats the time with season.
     *
     * @param calendar the date and time
     * @return the time with season with a format
     */
    public static String formatTimeShortWithSeason(Calendar calendar) {
        return String.format("%2$d%1$tH%1$tM", calendar, isUTC(calendar) ? 2 : isSummerTime(calendar) ? 1 : 0);
    }

    /**
     * Formats the time.
     *
     * @param calendar the date and time
     * @return the time with a format
     */
    public static String formatTime(Calendar calendar) {
        return String.format("%1$tH%1$tM%1$tS", calendar);
    }

    /**
     * Formats the time with season.
     *
     * @param calendar the date and time
     * @return the time with season with a format
     */
    public static String formatTimeWithSeason(Calendar calendar) {
        return String.format("%2$d%1$tH%1$tM%1$tS", calendar, isUTC(calendar) ? 2 : isSummerTime(calendar) ? 1 : 0);
    }

    /**
     * Formats the date and time.
     *
     * @param calendar the date and time
     * @return the date and time with a format
     */
    public static String formatDateTimeShort(Calendar calendar) {
        return String.format("%1$ty%1$tm%1$td%1$tH%1$tM", calendar);
    }

    /**
     * Formats the date and time with season.
     *
     * @param calendar the date and time
     * @return the date and time with season with a format
     */
    public static String formatDateTimeShortWithSeason(Calendar calendar) {
        return String.format("%2$d%1$ty%1$tm%1$td%1$tH%1$tM", calendar, isUTC(calendar) ? 2 : isSummerTime(calendar) ? 1 : 0);
    }

    /**
     * Formats the date and time.
     *
     * @param calendar the date and time
     * @return the date and time with a format
     */
    public static String formatDateTime(Calendar calendar) {
        return String.format("%1$ty%1$tm%1$td%1$tH%1$tM%1$tS", calendar);
    }

    /**
     * Formats the date and time with season.
     *
     * @param calendar the date and time
     * @return the date and time with season with a format
     */
    public static String formatDateTimeWithSeason(Calendar calendar) {
        return String.format("%2$d%1$ty%1$tm%1$td%1$tH%1$tM%1$tS", calendar, isUTC(calendar) ? 2 : isSummerTime(calendar) ? 1 : 0);
    }

}
