package org.swrlapi.literal;

import checkers.nullness.quals.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class XSDTimeUtil
{
  @NonNull private static final String xsdDateTimeFormatString = "yyyy-MM-dd'T'hh:mm:ss";
  @NonNull private static final String xsdDateFormatString = "yyyy-MM-dd";
  @NonNull private static final String jdbcDateTimeFormatString = "y-M-d h:m:s.S";
  @NonNull private static final String jdbcDateFormatString = "y-M-d";

  @NonNull private static final DateFormat xsdDateTimeFormat = new SimpleDateFormat(xsdDateTimeFormatString);
  @NonNull private static final DateFormat xsdDateFormat = new SimpleDateFormat(xsdDateFormatString);
  @NonNull private static final DateFormat jdbcDateTimeFormat = new SimpleDateFormat(jdbcDateTimeFormatString);
  @NonNull private static final DateFormat jdbcDateFormat = new SimpleDateFormat(jdbcDateFormatString);

  public static org.apache.axis.types.Duration addDurations(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setYears(getYears(duration1) + getYears(duration2));
    result.setMonths(getMonths(duration1) + getMonths(duration2));
    result.setDays(getDays(duration1) + getDays(duration2));
    result.setHours(getHours(duration1) + getHours(duration2));
    result.setMinutes(getMinutes(duration1) + getMinutes(duration2));
    result.setSeconds(getSeconds(duration1) + getSeconds(duration2));

    return result;
  }

  public static int compareDurations(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    int diff = getYears(duration1) - getYears(duration2);
    if (diff != 0)
      return diff;

    diff = getMonths(duration1) - getMonths(duration2);
    if (diff != 0)
      return diff;

    diff = getDays(duration1) - getDays(duration2);
    if (diff != 0)
      return diff;

    diff = getHours(duration1) - getHours(duration2);
    if (diff != 0)
      return diff;

    diff = getMinutes(duration1) - getMinutes(duration2);
    if (diff != 0)
      return diff;

    // TODO Look at this
    diff = (int)getSeconds(duration1) - (int)getSeconds(duration2);

    return diff;
  }

  public static int compareDateTimes(java.util.@NonNull Date date1, java.util.@NonNull Date date2)
  {
    Calendar calendar1 = new GregorianCalendar();
    Calendar calendar2 = new GregorianCalendar();
    calendar1.setTime(date1);
    calendar2.setTime(date2);

    int diff = calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR);
    if (diff != 0)
      return diff;

    diff = calendar1.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH);
    if (diff != 0)
      return diff;

    diff = calendar1.get(Calendar.DAY_OF_MONTH) - calendar2.get(Calendar.DAY_OF_MONTH);
    if (diff != 0)
      return diff;

    diff = calendar1.get(Calendar.HOUR) - calendar2.get(Calendar.HOUR);
    if (diff != 0)
      return diff;

    diff = calendar1.get(Calendar.MINUTE) - calendar2.get(Calendar.MINUTE);
    if (diff != 0)
      return diff;

    diff = calendar1.get(Calendar.SECOND) - calendar2.get(Calendar.SECOND);

    return diff;
  }

  public static int compareDates(java.util.@NonNull Date date1, java.util.@NonNull Date date2)
  {
    Calendar calendar1 = new GregorianCalendar();
    Calendar calendar2 = new GregorianCalendar();
    calendar1.setTime(date1);
    calendar2.setTime(date2);

    int diff = calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR);
    if (diff != 0)
      return diff;

    diff = calendar1.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH);
    if (diff != 0)
      return diff;

    diff = calendar1.get(Calendar.DAY_OF_MONTH) - calendar2.get(Calendar.DAY_OF_MONTH);

    return diff;
  }

  public static int compareTimes(org.apache.axis.types.@NonNull Time time1, org.apache.axis.types.@NonNull Time time2)
  {
    Calendar calendar1 = time1.getAsCalendar();
    Calendar calendar2 = time2.getAsCalendar();

    int diff = calendar1.get(Calendar.HOUR) - calendar2.get(Calendar.HOUR);
    if (diff != 0)
      return diff;

    diff = calendar1.get(Calendar.MINUTE) - calendar2.get(Calendar.MINUTE);
    if (diff != 0)
      return diff;

    diff = calendar1.get(Calendar.SECOND) - calendar2.get(Calendar.SECOND);
    if (diff != 0)
      return diff;

    return diff;
  }

  public static org.apache.axis.types.@NonNull Duration subtractDurations(
    org.apache.axis.types.@NonNull Duration duration1, org.apache.axis.types.@NonNull Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setYears(getYears(duration1) - getYears(duration2));
    result.setMonths(getMonths(duration1) - getMonths(duration2));
    result.setDays(getDays(duration1) - getDays(duration2));
    result.setHours(getHours(duration1) - getHours(duration2));
    result.setMinutes(getMinutes(duration1) - getMinutes(duration2));
    result.setSeconds(getSeconds(duration1) - getSeconds(duration2));

    return result;
  }

  public static org.apache.axis.types.@NonNull Duration multiplyDurations(
    org.apache.axis.types.@NonNull Duration duration1, org.apache.axis.types.@NonNull Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setYears(getYears(duration1) * getYears(duration2));
    result.setMonths(getMonths(duration1) * getMonths(duration2));
    result.setDays(getDays(duration1) * getDays(duration2));
    result.setHours(getHours(duration1) * getHours(duration2));
    result.setMinutes(getMinutes(duration1) * getMinutes(duration2));
    result.setSeconds(getSeconds(duration1) * getSeconds(duration2));

    return result;
  }

  public static org.apache.axis.types.@NonNull Duration divideDurations(org.apache.axis.types.Duration duration1,
    org.apache.axis.types.Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setYears(getYears(duration1) / getYears(duration2));
    result.setMonths(getMonths(duration1) / getMonths(duration2));
    result.setDays(getDays(duration1) / getDays(duration2));
    result.setHours(getHours(duration1) / getHours(duration2));
    result.setMinutes(getMinutes(duration1) / getMinutes(duration2));
    result.setSeconds(getSeconds(duration1) / getSeconds(duration2));

    return result;
  }

  public static org.apache.axis.types.@NonNull Duration addDayTimeDurations(
    org.apache.axis.types.@NonNull Duration duration1, org.apache.axis.types.@NonNull Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getDays(duration1) + getDays(duration2));
    result.setHours(getHours(duration1) + getHours(duration2));
    result.setMinutes(getMinutes(duration1) + getMinutes(duration2));
    result.setSeconds(getSeconds(duration1) + getSeconds(duration2));

    return result;
  }

  public static org.apache.axis.types.@NonNull Duration subtractDayTimeDurations(
    org.apache.axis.types.@NonNull Duration duration1, org.apache.axis.types.@NonNull Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getDays(duration1) - getDays(duration2));
    result.setHours(getHours(duration1) - getHours(duration2));
    result.setMinutes(getMinutes(duration1) - getMinutes(duration2));
    result.setSeconds(getSeconds(duration1) - getSeconds(duration2));

    return result;
  }

  public static org.apache.axis.types.@NonNull Duration multiplyDayTimeDurations(
    org.apache.axis.types.@NonNull Duration duration1, org.apache.axis.types.@NonNull Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getDays(duration1) * getDays(duration2));
    result.setHours(getHours(duration1) * getHours(duration2));
    result.setMinutes(getMinutes(duration1) * getMinutes(duration2));
    result.setSeconds(getSeconds(duration1) * getSeconds(duration2));

    return result;
  }

  public static org.apache.axis.types.@NonNull Duration divideDayTimeDurations(org.apache.axis.types.Duration duration1,
    org.apache.axis.types.Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getDays(duration1) / getDays(duration2));
    result.setHours(getHours(duration1) / getHours(duration2));
    result.setMinutes(getMinutes(duration1) / getMinutes(duration2));
    result.setSeconds(getSeconds(duration1) / getSeconds(duration2));

    return result;
  }

  public static org.apache.axis.types.@NonNull Duration addYearMonthDurations(
    org.apache.axis.types.@NonNull Duration duration1, org.apache.axis.types.@NonNull Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getYears(duration1) + getYears(duration2));
    result.setHours(getMonths(duration1) + getMonths(duration2));

    return result;
  }

  public static org.apache.axis.types.Duration subtractYearMonthDurations(org.apache.axis.types.Duration duration1,
    org.apache.axis.types.Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getYears(duration1) - getYears(duration2));
    result.setHours(getMonths(duration1) - getMonths(duration2));

    return result;
  }

  public static org.apache.axis.types.Duration multiplyYearMonthDurations(org.apache.axis.types.Duration duration1,
    org.apache.axis.types.Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getYears(duration1) * getYears(duration2));
    result.setHours(getMonths(duration1) * getMonths(duration2));

    return result;
  }

  public static org.apache.axis.types.Duration divideYearMonthDurations(org.apache.axis.types.Duration duration1,
    org.apache.axis.types.Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getYears(duration1) / getYears(duration2));
    result.setHours(getMonths(duration1) / getMonths(duration2));

    return result;
  }

  public static org.apache.axis.types.Duration subtractDates(java.util.Date date1, java.util.Date date2)
  {
    Calendar calendar1 = new GregorianCalendar();
    Calendar calendar2 = new GregorianCalendar();
    calendar1.setTime(date1);
    calendar2.setTime(date2);

    int years = calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR);
    int months = calendar1.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH);
    int days = calendar1.get(Calendar.DAY_OF_MONTH) - calendar2.get(Calendar.DAY_OF_MONTH);
    int hours = calendar1.get(Calendar.HOUR) - calendar2.get(Calendar.HOUR);
    int minutes = calendar1.get(Calendar.MINUTE) - calendar2.get(Calendar.MINUTE);
    double seconds = calendar1.get(Calendar.SECOND) - calendar2.get(Calendar.SECOND);

    return new org.apache.axis.types.Duration(false, years, months, days, hours, minutes, seconds);
  }

  public static org.apache.axis.types.Duration subtractTimes(org.apache.axis.types.Time time1,
    org.apache.axis.types.Time time2)
  {
    Calendar calendar1 = time1.getAsCalendar();
    Calendar calendar2 = time2.getAsCalendar();
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    int hours = calendar1.get(Calendar.HOUR) - calendar2.get(Calendar.HOUR);
    int minutes = calendar1.get(Calendar.MINUTE) - calendar2.get(Calendar.MINUTE);
    double seconds = calendar1.get(Calendar.SECOND) - calendar2.get(Calendar.SECOND);

    result.setHours(hours);
    result.setMinutes(minutes);
    result.setSeconds(seconds);

    return result;
  }

  public static java.util.Date addYearMonthDurationToUtilDateTime(java.util.Date date,
    org.apache.axis.types.Duration duration)
  {
    Calendar calendar = new GregorianCalendar(), result = new GregorianCalendar();

    calendar.setTime(date);

    result.set(calendar.get(Calendar.YEAR) + getYears(duration), calendar.get(Calendar.MONTH) + getMonths(duration),
      calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
      calendar.get(Calendar.SECOND));

    return result.getTime();
  }

  public static java.util.Date addYearMonthDurationToUtilDate(java.util.Date date,
    org.apache.axis.types.Duration duration)
  {
    Calendar calendar = new GregorianCalendar(), resultCalendar = new GregorianCalendar();

    calendar.setTime(date);

    resultCalendar
      .set(calendar.get(Calendar.YEAR) + getYears(duration), calendar.get(Calendar.MONTH) + getMonths(duration),
        calendar.get(Calendar.DAY_OF_MONTH));

    return resultCalendar.getTime();
  }

  public static java.util.Date subtractYearMonthDurationFromUtilDateTime(java.util.Date date,
    org.apache.axis.types.Duration duration)
  {
    Calendar calendar = new GregorianCalendar(), result = new GregorianCalendar();

    calendar.setTime(date);

    result.set(calendar.get(Calendar.YEAR) - getYears(duration), calendar.get(Calendar.MONTH) - getMonths(duration),
      calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
      calendar.get(Calendar.SECOND));

    return result.getTime();
  }

  public static java.util.@NonNull Date subtractYearMonthDurationFromUtilDate(java.util.@NonNull Date date,
    org.apache.axis.types.@NonNull Duration duration)
  {
    Calendar calendar = new GregorianCalendar(), resultCalendar = new GregorianCalendar();

    calendar.setTime(date);

    resultCalendar
      .set(calendar.get(Calendar.YEAR) - getYears(duration), calendar.get(Calendar.MONTH) - getMonths(duration),
        calendar.get(Calendar.DAY_OF_MONTH));

    return resultCalendar.getTime();
  }

  public static java.util.@NonNull Date addDayTimeDurationToUtilDate(java.util.@NonNull Date date,
    org.apache.axis.types.@NonNull Duration duration)
  {
    Calendar calendar = new GregorianCalendar(), resultCalendar = new GregorianCalendar();

    calendar.setTime(date);

    resultCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
      calendar.get(Calendar.DAY_OF_MONTH) + getDays(duration), calendar.get(Calendar.HOUR) + getHours(duration),
      calendar.get(Calendar.MINUTE) + getMinutes(duration),
      (int)(calendar.get(Calendar.SECOND) + getSeconds(duration)));

    return resultCalendar.getTime();
  }

  public static java.util.@NonNull Date subtractDayTimeDurationFromUtilDate(java.util.@NonNull Date date,
    org.apache.axis.types.@NonNull Duration duration)
  {
    Calendar calendar = new GregorianCalendar(), resultCalendar = new GregorianCalendar();

    calendar.setTime(date);

    resultCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
      calendar.get(Calendar.DAY_OF_MONTH) - getDays(duration), calendar.get(Calendar.HOUR) - getHours(duration),
      calendar.get(Calendar.MINUTE) - getMinutes(duration),
      (int)(calendar.get(Calendar.SECOND) - getSeconds(duration)));

    return resultCalendar.getTime();
  }

  public static org.apache.axis.types.@NonNull Time addDayTimeDurationToTime(org.apache.axis.types.@NonNull Time time,
    org.apache.axis.types.@NonNull Duration duration)
  {
    Calendar calendar = time.getAsCalendar(), resultCalendar = new GregorianCalendar();

    resultCalendar
      .set(0, calendar.get(Calendar.DAY_OF_MONTH) + getDays(duration), calendar.get(Calendar.HOUR) + getHours(duration),
        calendar.get(Calendar.MINUTE) + getMinutes(duration),
        (int)(calendar.get(Calendar.SECOND) + getSeconds(duration)));

    return new org.apache.axis.types.Time(resultCalendar);
  }

  public static org.apache.axis.types.Time subtractDayTimeDurationFromTime(org.apache.axis.types.@NonNull Time time,
    org.apache.axis.types.@NonNull Duration duration)
  {
    Calendar calendar = time.getAsCalendar(), resultCalendar = new GregorianCalendar();

    resultCalendar
      .set(0, calendar.get(Calendar.DAY_OF_MONTH) - getDays(duration), calendar.get(Calendar.HOUR) - getHours(duration),
        calendar.get(Calendar.MINUTE) - getMinutes(duration),
        (int)(calendar.get(Calendar.SECOND) - getSeconds(duration)));

    return new org.apache.axis.types.Time(resultCalendar);
  }

  public static org.apache.axis.types.@NonNull Duration subtractUtilDatesYieldingYearMonthDuration(
    java.util.@NonNull Date date1, java.util.@NonNull Date date2)
  {
    Calendar calendar1 = new GregorianCalendar();
    Calendar calendar2 = new GregorianCalendar();
    calendar1.setTime(date1);
    calendar2.setTime(date2);

    int years = calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR);
    int months = calendar1.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH);

    return new org.apache.axis.types.Duration(false, years, months, 0, 0, 0, 0);
  }

  public static org.apache.axis.types.@NonNull Duration subtractUtilDatesYieldingDayTimeDuration(
    java.util.@NonNull Date date1, java.util.@NonNull Date date2)
  {
    Calendar calendar1 = new GregorianCalendar();
    Calendar calendar2 = new GregorianCalendar();
    calendar1.setTime(date1);
    calendar2.setTime(date2);

    int days = calendar1.get(Calendar.DAY_OF_MONTH) - calendar2.get(Calendar.DAY_OF_MONTH);
    int hours = calendar1.get(Calendar.HOUR) - calendar2.get(Calendar.HOUR);
    int minutes = calendar1.get(Calendar.MINUTE) - calendar2.get(Calendar.MINUTE);
    double seconds = calendar1.get(Calendar.SECOND) - calendar2.get(Calendar.SECOND);

    return new org.apache.axis.types.Duration(false, 0, 0, days, hours, minutes, seconds);
  }

  @NonNull public static String utilDate2XSDDateTimeString(java.util.@NonNull Date date)
  {
    return xsdDateTimeFormat.format(date);
  }

  @NonNull public static String utilDate2XSDDateString(java.util.@NonNull Date date)
  {
    return xsdDateFormat.format(date);
  }

  @NonNull public static String utilDate2XSDTimeString(java.util.@NonNull Date date)
  {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    org.apache.axis.types.Time time = new org.apache.axis.types.Time(calendar);
    return time.toString();
  }

  public static org.apache.axis.types.@NonNull Time utilDate2XSDTime(java.util.@NonNull Date date)
  {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    return new org.apache.axis.types.Time(calendar);
  }

  public static java.util.@NonNull Date xsdDateTimeString2Date(@NonNull String content)
  {
    try {
      return xsdDateTimeFormat.parse(content);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Invalid xsd:DateTime " + content);
    }
  }

  public static java.util.@NonNull Date xsdDateString2Date(@NonNull String content)
  {
    try {
      return xsdDateFormat.parse(content);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Invalid xsd:Date " + content);
    }
  }

  public static java.util.@NonNull Date xsdTimeString2Date(@NonNull String content)
  {
    org.apache.axis.types.Time time = new org.apache.axis.types.Time(content);

    return time.getAsCalendar().getTime();
  }

  public static org.apache.axis.types.@NonNull Time xsdTimeString2AxisTime(@NonNull String content)
  {
    return new org.apache.axis.types.Time(content);
  }

  public static org.apache.axis.types.@NonNull Duration xsdDurationString2AxisDuration(@NonNull String content)
  {
    return new org.apache.axis.types.Duration(content);
  }

  @NonNull public static String date2JDBCDateTimeString(java.util.@NonNull Date date)
  {
    return jdbcDateTimeFormat.format(date);
  }

  @NonNull public static String date2JDBCDateString(java.util.Date date)
  {
    return jdbcDateFormat.format(date);
  }

  public static java.util.@NonNull Date jdbcDateTimeString2Date(@NonNull String content)
  {
    try {
      return jdbcDateTimeFormat.parse(content);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Invalid JDBC datetime " + content);
    }
  }

  public static java.util.@NonNull Date jdbcDateString2Date(@NonNull String content)
  {
    try {
      return jdbcDateFormat.parse(content);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Invalid JDBC date " + content);
    }
  }

  public static boolean isValidXSDDateTime(@NonNull String content)
  {
    try {
      xsdDateTimeFormat.parse(content);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  public static boolean isValidXSDDate(@NonNull String content)
  {
    try {
      xsdDateFormat.parse(content);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  public static boolean isValidXSDTime(@NonNull String content)
  {
    try {
      new org.apache.axis.types.Time(content);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static boolean isValidXSDDuration(@NonNull String content)
  {
    try {
      new org.apache.axis.types.Duration(content);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  public static boolean isValidJDBCDateTime(@NonNull String content)
  {
    try {
      jdbcDateTimeFormat.parse(content);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  public static int addDurationYears(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getYears(duration1) + getYears(duration2);
  }

  public static int addDurationMonths(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getMonths(duration1) + getMonths(duration2);
  }

  public static int addDurationDays(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getDays(duration1) + getDays(duration2);
  }

  public static int addDurationHours(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getHours(duration1) + getHours(duration2);
  }

  public static int addDurationMinutes(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getMinutes(duration1) + getMinutes(duration2);
  }

  public static double addDurationSeconds(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getSeconds(duration1) + getSeconds(duration2);
  }

  public static int subtractDurationYears(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getYears(duration1) - getYears(duration2);
  }

  public static int subtractDurationMonths(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getMonths(duration1) - getMonths(duration2);
  }

  public static int subtractDurationDays(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getDays(duration1) - getDays(duration2);
  }

  public static int subtractDurationHours(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getHours(duration1) - getHours(duration2);
  }

  public static int subtractDurationMinutes(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getMinutes(duration1) - getMinutes(duration2);
  }

  public static double subtractDurationSeconds(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getSeconds(duration1) - getSeconds(duration2);
  }

  private static int getYears(org.apache.axis.types.Duration duration)
  {
    if (duration.isNegative())
      return -duration.getYears();
    else
      return duration.getYears();
  }

  private static int getMonths(org.apache.axis.types.@NonNull Duration duration)
  {
    if (duration.isNegative())
      return -duration.getMonths();
    else
      return duration.getMonths();
  }

  private static int getDays(org.apache.axis.types.@NonNull Duration duration)
  {
    if (duration.isNegative())
      return -duration.getDays();
    else
      return duration.getDays();
  }

  private static int getHours(org.apache.axis.types.@NonNull Duration duration)
  {
    if (duration.isNegative())
      return -duration.getHours();
    else
      return duration.getHours();
  }

  private static int getMinutes(org.apache.axis.types.@NonNull Duration duration)
  {
    if (duration.isNegative())
      return -duration.getMinutes();
    else
      return duration.getMinutes();
  }

  private static double getSeconds(org.apache.axis.types.@NonNull Duration duration)
  {
    if (duration.isNegative())
      return -duration.getSeconds();
    else
      return duration.getSeconds();
  }
}
