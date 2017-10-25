package com.njnu.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final String DATE_DIVISION = "-";

    public static final String TIME_PATTON_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTON_DEFAULT = "yyyy-MM-dd";
    public static final String DATE_PATTON_MONTH_DAY_CN = "MM月dd日";
    public static final String DATA_PATTON_YYYYMMDD = "yyyyMMdd";
    public static final String TIME_PATTON_HHMMSS = "HH:mm:ss";

    public static final int ONE_SECOND = 1000;
    public static final int ONE_MINUTE = 60 * ONE_SECOND;
    public static final int ONE_HOUR = 60 * ONE_MINUTE;
    public static final long ONE_DAY = 24 * ONE_HOUR;
    
    public static final String TIME_PATTON_NEW = "yyyy/MM/dd HH:mm:ss";
    
    
    

    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();

        return currDate;
    }

    public static String getCurrentDateStr() {
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();

        return format(currDate);
    }
    
    
    public static String getCurrentDateNewStr() {
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();

        return format_New(currDate);
    }

    public static String getCurrentDateStr(String strFormat) {
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();

        return format(currDate, strFormat);
    }

    public static Date parseDate(String dateValue) {
        return parseDate(DATE_PATTON_DEFAULT, dateValue);
    }

    public static Date parseDateTime(String dateValue) {
        return parseDate(TIME_PATTON_DEFAULT, dateValue);
    }
    
    public static Date parseDateNewTime(String dateValue) {
        return parseDate(TIME_PATTON_NEW, dateValue);
    }

    public static Date parseDate(String strFormat, String dateValue) {
        if (dateValue == null)
            return null;

        if (strFormat == null)
            strFormat = TIME_PATTON_DEFAULT;

        SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
        Date newDate = null;

        try {
            newDate = dateFormat.parse(dateValue);
        } catch (ParseException pe) {
            newDate = null;
        }

        return newDate;
    }

    public static String format(Date aTs_Datetime) {
        return format(aTs_Datetime, DATE_PATTON_DEFAULT);
    }
    
    public static String format_New(Date aTs_Datetime) {
        return format(aTs_Datetime, TIME_PATTON_NEW);
    }

    public static String formatTime(Date aTs_Datetime) {
        return format(aTs_Datetime, TIME_PATTON_DEFAULT);
    }

    public static String format(Date aTs_Datetime, String as_Pattern) {
        if (aTs_Datetime == null || as_Pattern == null)
            return "";

        SimpleDateFormat dateFromat = new SimpleDateFormat();
        dateFromat.applyPattern(as_Pattern);

        return dateFromat.format(aTs_Datetime);
    }

    public static String formatTime(Date aTs_Datetime, String as_Format) {
        if (aTs_Datetime == null || as_Format == null)
            return null;

        SimpleDateFormat dateFromat = new SimpleDateFormat();
        dateFromat.applyPattern(as_Format);

        return dateFromat.format(aTs_Datetime);
    }

    public static String getFormatTime(Date dateTime) {
        return formatTime(dateTime, TIME_PATTON_HHMMSS);
    }

    public static String format(Timestamp aTs_Datetime, String as_Pattern) {
        if (aTs_Datetime == null || as_Pattern == null)
            return null;

        SimpleDateFormat dateFromat = new SimpleDateFormat();
        dateFromat.applyPattern(as_Pattern);

        return dateFromat.format(aTs_Datetime);
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.DAY_OF_MONTH, days);

        return cal.getTime();
    }

    public static int daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static boolean isSameWeek(Calendar calendar1, Calendar calendar2, int firstDayOfWeek) {
        calendar1.setFirstDayOfWeek(firstDayOfWeek);
        calendar2.setFirstDayOfWeek(firstDayOfWeek);

        int taskPlanWeek = calendar2.get(Calendar.WEEK_OF_YEAR);
        int currentWeek = calendar1.get(Calendar.WEEK_OF_YEAR);

        boolean isSameWeek = (taskPlanWeek == currentWeek);
        return isSameWeek;
    }

    public static Date now() {
        return new Date();
    }
    
    
    /**
     * 日期转化
     * @param date1
     * @param date2
     * @return
     */
    public static String timesBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        String text = null;
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_seconds = (time2 - time1) / ONE_SECOND;
        long between_minutes = (time2 - time1) / ONE_MINUTE;
        long between_hours = (time2 - time1) / ONE_HOUR;
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        int second = Integer.parseInt(String.valueOf(between_seconds));
        int minute = Integer.parseInt(String.valueOf(between_minutes));
        int hour = Integer.parseInt(String.valueOf(between_hours));
        int day = Integer.parseInt(String.valueOf(between_days));
        if(second<60){
        	 text = between_seconds+"秒前";
        }
        if(minute<60&&minute!=0){
        	text = between_minutes+"分钟前";
        }
        if(hour<60&&hour!=0){
        	text = between_hours+"小时前";
        }
        if(day<31&&day!=0){
        	text = between_days+"天前";
        }else if(day<365&&day!=0){
        	int yue = day/31;
        	text = yue+"月前";
        }else if(day>365&&day!=0){
        	int nian = day/365;
        	text = nian+"年前";
        }

        return text;
    }
    
    //------------------------------------------------------------------

    /**
     * A week range, starting on Sunday.
     */
    public final static int RANGE_WEEK_SUNDAY = 1;

    /**
     * A week range, starting on Monday.
     */
    public final static int RANGE_WEEK_MONDAY = 2;

    /**
     * A week range, starting on the day focused.
     */
    public final static int RANGE_WEEK_RELATIVE = 3;

    /**
     * A week range, centered around the day focused.
     */
    public final static int RANGE_WEEK_CENTER = 4;

    /**
     * A month range, the week starting on Sunday.
     */
    public final static int RANGE_MONTH_SUNDAY = 5;

    /**
     * A month range, the week starting on Monday.
     */
    public final static int RANGE_MONTH_MONDAY = 6;

    /**
     * Constant marker for truncating 
     */
    private final static int MODIFY_TRUNCATE = 0;

    /**
     * Constant marker for rounding
     */
    private final static int MODIFY_ROUND = 1;

    /**
     * Constant marker for ceiling
     */
    private final static int MODIFY_CEILING= 2;

    //-----------------------------------------------------------------------
    /**
     * <p>Truncate this date, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the datetime of 28 Mar 2002
     * 13:45:01.231, if you passed with HOUR, it would return 28 Mar
     * 2002 13:00:00.000.  If this was passed with MONTH, it would
     * return 1 Mar 2002 0:00:00.000.</p>
     * 
     * @param date  the date to work with
     * @param field  the field from <code>Calendar</code>
     *  or <code>SEMI_MONTH</code>
     * @return the rounded date
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Date truncate(Date date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar gval = Calendar.getInstance();
        gval.setTime(date);
        modify(gval, field, MODIFY_TRUNCATE);
        return gval.getTime();
    }

    /**
     * <p>Truncate this date, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the datetime of 28 Mar 2002
     * 13:45:01.231, if you passed with HOUR, it would return 28 Mar
     * 2002 13:00:00.000.  If this was passed with MONTH, it would
     * return 1 Mar 2002 0:00:00.000.</p>
     * 
     * @param date  the date to work with
     * @param field  the field from <code>Calendar</code>
     *  or <code>SEMI_MONTH</code>
     * @return the rounded date (a different object)
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Calendar truncate(Calendar date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar truncated = (Calendar) date.clone();
        modify(truncated, field, MODIFY_TRUNCATE);
        return truncated;
    }

    /**
     * <p>Truncate this date, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the datetime of 28 Mar 2002
     * 13:45:01.231, if you passed with HOUR, it would return 28 Mar
     * 2002 13:00:00.000.  If this was passed with MONTH, it would
     * return 1 Mar 2002 0:00:00.000.</p>
     * 
     * @param date  the date to work with, either <code>Date</code>
     *  or <code>Calendar</code>
     * @param field  the field from <code>Calendar</code>
     *  or <code>SEMI_MONTH</code>
     * @return the rounded date
     * @throws IllegalArgumentException if the date
     *  is <code>null</code>
     * @throws ClassCastException if the object type is not a
     *  <code>Date</code> or <code>Calendar</code>
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Date truncate(Object date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (date instanceof Date) {
            return truncate((Date) date, field);
        } else if (date instanceof Calendar) {
            return truncate((Calendar) date, field).getTime();
        } else {
            throw new ClassCastException("Could not truncate " + date);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Internal calculation method.</p>
     * 
     * @param val  the calendar
     * @param field  the field constant
     * @param modType  type to truncate, round or ceiling
     * @throws ArithmeticException if the year is over 280 million
     */
    private static void modify(Calendar val, int field, int modType) {
        if (val.get(Calendar.YEAR) > 280000000) {
            throw new ArithmeticException("Calendar value too large for accurate calculations");
        }
        
        if (field == Calendar.MILLISECOND) {
            return;
        }

        // ----------------- Fix for LANG-59 ---------------------- START ---------------
        // see http://issues.apache.org/jira/browse/LANG-59
        //
        // Manually truncate milliseconds, seconds and minutes, rather than using
        // Calendar methods.

        Date date = val.getTime();
        long time = date.getTime();
        boolean done = false;

        // truncate milliseconds
        int millisecs = val.get(Calendar.MILLISECOND);
        if (MODIFY_TRUNCATE == modType || millisecs < 500) {
            time = time - millisecs;
        }
        if (field == Calendar.SECOND) {
            done = true;
        }

        // truncate seconds
        int seconds = val.get(Calendar.SECOND);
        if (!done && (MODIFY_TRUNCATE == modType || seconds < 30)) {
            time = time - (seconds * 1000L);
        }
        if (field == Calendar.MINUTE) {
            done = true;
        }

        // truncate minutes
        int minutes = val.get(Calendar.MINUTE);
        if (!done && (MODIFY_TRUNCATE == modType || minutes < 30)) {
            time = time - (minutes * 60000L);
        }

        // reset time
        if (date.getTime() != time) {
            date.setTime(time);
            val.setTime(date);
        }
        // ----------------- Fix for LANG-59 ----------------------- END ----------------

        boolean roundUp = false;
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                if (fields[i][j] == field) {
                    //This is our field... we stop looping
                    if (modType == MODIFY_CEILING || (modType == MODIFY_ROUND && roundUp)) {
                        if (field == DateUtils.SEMI_MONTH) {
                            //This is a special case that's hard to generalize
                            //If the date is 1, we round up to 16, otherwise
                            //  we subtract 15 days and add 1 month
                            if (val.get(Calendar.DATE) == 1) {
                                val.add(Calendar.DATE, 15);
                            } else {
                                val.add(Calendar.DATE, -15);
                                val.add(Calendar.MONTH, 1);
                            }
// ----------------- Fix for LANG-440 ---------------------- START ---------------
                        } else if (field == Calendar.AM_PM) {
                            // This is a special case
                            // If the time is 0, we round up to 12, otherwise
                            //  we subtract 12 hours and add 1 day
                            if (val.get(Calendar.HOUR_OF_DAY) == 0) {
                                val.add(Calendar.HOUR_OF_DAY, 12);
                            } else {
                                val.add(Calendar.HOUR_OF_DAY, -12);
                                val.add(Calendar.DATE, 1);
                            }
// ----------------- Fix for LANG-440 ---------------------- END ---------------
                        } else {
                            //We need at add one to this field since the
                            //  last number causes us to round up
                            val.add(fields[i][0], 1);
                        }
                    }
                    return;
                }
            }
            //We have various fields that are not easy roundings
            int offset = 0;
            boolean offsetSet = false;
            //These are special types of fields that require different rounding rules
            switch (field) {
                case DateUtils.SEMI_MONTH:
                    if (fields[i][0] == Calendar.DATE) {
                        //If we're going to drop the DATE field's value,
                        //  we want to do this our own way.
                        //We need to subtrace 1 since the date has a minimum of 1
                        offset = val.get(Calendar.DATE) - 1;
                        //If we're above 15 days adjustment, that means we're in the
                        //  bottom half of the month and should stay accordingly.
                        if (offset >= 15) {
                            offset -= 15;
                        }
                        //Record whether we're in the top or bottom half of that range
                        roundUp = offset > 7;
                        offsetSet = true;
                    }
                    break;
                case Calendar.AM_PM:
                    if (fields[i][0] == Calendar.HOUR_OF_DAY) {
                        //If we're going to drop the HOUR field's value,
                        //  we want to do this our own way.
                        offset = val.get(Calendar.HOUR_OF_DAY);
                        if (offset >= 12) {
                            offset -= 12;
                        }
                        roundUp = offset >= 6;
                        offsetSet = true;
                    }
                    break;
            }
            if (!offsetSet) {
                int min = val.getActualMinimum(fields[i][0]);
                int max = val.getActualMaximum(fields[i][0]);
                //Calculate the offset from the minimum allowed value
                offset = val.get(fields[i][0]) - min;
                //Set roundUp if this is more than half way between the minimum and maximum
                roundUp = offset > ((max - min) / 2);
            }
            //We need to remove this field
            if (offset != 0) {
                val.set(fields[i][0], val.get(fields[i][0]) - offset);
            }
        }
        throw new IllegalArgumentException("The field " + field + " is not supported");

    }

    /**
     * This is half a month, so this represents whether a date is in the top
     * or bottom half of the month.
     */
    public final static int SEMI_MONTH = 1001;

    private static final int[][] fields = {
            {Calendar.MILLISECOND},
            {Calendar.SECOND},
            {Calendar.MINUTE},
            {Calendar.HOUR_OF_DAY, Calendar.HOUR},
            {Calendar.DATE, Calendar.DAY_OF_MONTH, Calendar.AM_PM 
                /* Calendar.DAY_OF_YEAR, Calendar.DAY_OF_WEEK, Calendar.DAY_OF_WEEK_IN_MONTH */
            },
            {Calendar.MONTH, DateUtils.SEMI_MONTH},
            {Calendar.YEAR},
            {Calendar.ERA}};
}
