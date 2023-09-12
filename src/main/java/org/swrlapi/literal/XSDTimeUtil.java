package org.swrlapi.literal;

import org.checkerframework.checker.nullness.qual.NonNull;

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

  private static final ThreadLocal<@NonNull SimpleDateFormat> xsdDateTimeFormat = ThreadLocal
    .withInitial(() -> new SimpleDateFormat(xsdDateTimeFormatString));

  private static final ThreadLocal<@NonNull SimpleDateFormat> xsdDateFormat = ThreadLocal
    .withInitial(() -> new SimpleDateFormat(xsdDateFormatString));

  private static final ThreadLocal<@NonNull SimpleDateFormat> jdbcDateTimeFormat = ThreadLocal
    .withInitial(() -> new SimpleDateFormat(jdbcDateTimeFormatString));

  private static final ThreadLocal<@NonNull SimpleDateFormat> jdbcDateFormat = ThreadLocal
    .withInitial(() -> new SimpleDateFormat(jdbcDateFormatString));

  public static org.apache.axis.types.Duration addAxisDurations(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setYears(getYearsFromAxisDuration(duration1) + getYearsFromAxisDuration(duration2));
    result.setMonths(getMonthsFromAxisDuration(duration1) + getMonthsFromAxisDuration(duration2));
    result.setDays(getDaysFromAxisDuration(duration1) + getDaysFromAxisDuration(duration2));
    result.setHours(getHoursFromAxisDuration(duration1) + getHoursFromAxisDuration(duration2));
    result.setMinutes(getMinutesFromAxisDuration(duration1) + getMinutesFromAxisDuration(duration2));
    result.setSeconds(getSecondsFromAxisDuration(duration1) + getSecondsFromAxisDuration(duration2));

    return result;
  }

  public static int compareAxisDurations(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    int diff = getYearsFromAxisDuration(duration1) - getYearsFromAxisDuration(duration2);
    if (diff != 0)
      return diff;

    diff = getMonthsFromAxisDuration(duration1) - getMonthsFromAxisDuration(duration2);
    if (diff != 0)
      return diff;

    diff = getDaysFromAxisDuration(duration1) - getDaysFromAxisDuration(duration2);
    if (diff != 0)
      return diff;

    diff = getHoursFromAxisDuration(duration1) - getHoursFromAxisDuration(duration2);
    if (diff != 0)
      return diff;

    diff = getMinutesFromAxisDuration(duration1) - getMinutesFromAxisDuration(duration2);
    if (diff != 0)
      return diff;

    // TODO Look at this
    diff = (int)getSecondsFromAxisDuration(duration1) - (int)getSecondsFromAxisDuration(duration2);

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
    if (diff != 0)
      return diff;

    diff = calendar1.get(Calendar.MILLISECOND) - calendar2.get(Calendar.MILLISECOND);
    if (diff != 0)
      return diff;

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

  public static int compareAxisTimes(org.apache.axis.types.@NonNull Time time1,
    org.apache.axis.types.@NonNull Time time2)
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

    diff = calendar1.get(Calendar.MILLISECOND) - calendar2.get(Calendar.MILLISECOND);
    if (diff != 0)
      return diff;

    return diff;
  }

  public static org.apache.axis.types.@NonNull Duration subtractAxisDurations(
    org.apache.axis.types.@NonNull Duration duration1, org.apache.axis.types.@NonNull Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setYears(getYearsFromAxisDuration(duration1) - getYearsFromAxisDuration(duration2));
    result.setMonths(getMonthsFromAxisDuration(duration1) - getMonthsFromAxisDuration(duration2));
    result.setDays(getDaysFromAxisDuration(duration1) - getDaysFromAxisDuration(duration2));
    result.setHours(getHoursFromAxisDuration(duration1) - getHoursFromAxisDuration(duration2));
    result.setMinutes(getMinutesFromAxisDuration(duration1) - getMinutesFromAxisDuration(duration2));
    result.setSeconds(getSecondsFromAxisDuration(duration1) - getSecondsFromAxisDuration(duration2));

    return result;
  }

  public static org.apache.axis.types.@NonNull Duration multiplyAxisDurations(
    org.apache.axis.types.@NonNull Duration duration1, org.apache.axis.types.@NonNull Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setYears(getYearsFromAxisDuration(duration1) * getYearsFromAxisDuration(duration2));
    result.setMonths(getMonthsFromAxisDuration(duration1) * getMonthsFromAxisDuration(duration2));
    result.setDays(getDaysFromAxisDuration(duration1) * getDaysFromAxisDuration(duration2));
    result.setHours(getHoursFromAxisDuration(duration1) * getHoursFromAxisDuration(duration2));
    result.setMinutes(getMinutesFromAxisDuration(duration1) * getMinutesFromAxisDuration(duration2));
    result.setSeconds(getSecondsFromAxisDuration(duration1) * getSecondsFromAxisDuration(duration2));

    return result;
  }

  public static org.apache.axis.types.@NonNull Duration divideAxisDurations(org.apache.axis.types.Duration duration1,
    org.apache.axis.types.Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setYears(getYearsFromAxisDuration(duration1) / getYearsFromAxisDuration(duration2));
    result.setMonths(getMonthsFromAxisDuration(duration1) / getMonthsFromAxisDuration(duration2));
    result.setDays(getDaysFromAxisDuration(duration1) / getDaysFromAxisDuration(duration2));
    result.setHours(getHoursFromAxisDuration(duration1) / getHoursFromAxisDuration(duration2));
    result.setMinutes(getMinutesFromAxisDuration(duration1) / getMinutesFromAxisDuration(duration2));
    result.setSeconds(getSecondsFromAxisDuration(duration1) / getSecondsFromAxisDuration(duration2));

    return result;
  }

  public static org.apache.axis.types.@NonNull Duration addDayTimeDurations(
    org.apache.axis.types.@NonNull Duration duration1, org.apache.axis.types.@NonNull Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getDaysFromAxisDuration(duration1) + getDaysFromAxisDuration(duration2));
    result.setHours(getHoursFromAxisDuration(duration1) + getHoursFromAxisDuration(duration2));
    result.setMinutes(getMinutesFromAxisDuration(duration1) + getMinutesFromAxisDuration(duration2));
    result.setSeconds(getSecondsFromAxisDuration(duration1) + getSecondsFromAxisDuration(duration2));

    return result;
  }

  public static org.apache.axis.types.@NonNull Duration subtractDayTimeDurations(
    org.apache.axis.types.@NonNull Duration duration1, org.apache.axis.types.@NonNull Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getDaysFromAxisDuration(duration1) - getDaysFromAxisDuration(duration2));
    result.setHours(getHoursFromAxisDuration(duration1) - getHoursFromAxisDuration(duration2));
    result.setMinutes(getMinutesFromAxisDuration(duration1) - getMinutesFromAxisDuration(duration2));
    result.setSeconds(getSecondsFromAxisDuration(duration1) - getSecondsFromAxisDuration(duration2));

    return result;
  }

  public static org.apache.axis.types.@NonNull Duration multiplyDayTimeDurations(
    org.apache.axis.types.@NonNull Duration duration1, org.apache.axis.types.@NonNull Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getDaysFromAxisDuration(duration1) * getDaysFromAxisDuration(duration2));
    result.setHours(getHoursFromAxisDuration(duration1) * getHoursFromAxisDuration(duration2));
    result.setMinutes(getMinutesFromAxisDuration(duration1) * getMinutesFromAxisDuration(duration2));
    result.setSeconds(getSecondsFromAxisDuration(duration1) * getSecondsFromAxisDuration(duration2));

    return result;
  }

  public static org.apache.axis.types.@NonNull Duration divideDayTimeDurations(org.apache.axis.types.Duration duration1,
    org.apache.axis.types.Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getDaysFromAxisDuration(duration1) / getDaysFromAxisDuration(duration2));
    result.setHours(getHoursFromAxisDuration(duration1) / getHoursFromAxisDuration(duration2));
    result.setMinutes(getMinutesFromAxisDuration(duration1) / getMinutesFromAxisDuration(duration2));
    result.setSeconds(getSecondsFromAxisDuration(duration1) / getSecondsFromAxisDuration(duration2));

    return result;
  }

  public static org.apache.axis.types.@NonNull Duration addYearMonthDurations(
    org.apache.axis.types.@NonNull Duration duration1, org.apache.axis.types.@NonNull Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getYearsFromAxisDuration(duration1) + getYearsFromAxisDuration(duration2));
    result.setHours(getMonthsFromAxisDuration(duration1) + getMonthsFromAxisDuration(duration2));

    return result;
  }

  public static org.apache.axis.types.Duration subtractYearMonthDurations(org.apache.axis.types.Duration duration1,
    org.apache.axis.types.Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getYearsFromAxisDuration(duration1) - getYearsFromAxisDuration(duration2));
    result.setHours(getMonthsFromAxisDuration(duration1) - getMonthsFromAxisDuration(duration2));

    return result;
  }

  public static org.apache.axis.types.Duration multiplyYearMonthDurations(org.apache.axis.types.Duration duration1,
    org.apache.axis.types.Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getYearsFromAxisDuration(duration1) * getYearsFromAxisDuration(duration2));
    result.setHours(getMonthsFromAxisDuration(duration1) * getMonthsFromAxisDuration(duration2));

    return result;
  }

  public static org.apache.axis.types.Duration divideYearMonthDurations(org.apache.axis.types.Duration duration1,
    org.apache.axis.types.Duration duration2)
  {
    org.apache.axis.types.Duration result = new org.apache.axis.types.Duration();

    result.setDays(getYearsFromAxisDuration(duration1) / getYearsFromAxisDuration(duration2));
    result.setHours(getMonthsFromAxisDuration(duration1) / getMonthsFromAxisDuration(duration2));

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

    result.set(calendar.get(Calendar.YEAR) + getYearsFromAxisDuration(duration),
      calendar.get(Calendar.MONTH) + getMonthsFromAxisDuration(duration), calendar.get(Calendar.DAY_OF_MONTH),
      calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));

    return result.getTime();
  }

  public static java.util.Date addYearMonthDurationToUtilDate(java.util.Date date,
    org.apache.axis.types.Duration duration)
  {
    Calendar calendar = new GregorianCalendar(), resultCalendar = new GregorianCalendar();

    calendar.setTime(date);

    resultCalendar.set(calendar.get(Calendar.YEAR) + getYearsFromAxisDuration(duration),
      calendar.get(Calendar.MONTH) + getMonthsFromAxisDuration(duration), calendar.get(Calendar.DAY_OF_MONTH));

    return resultCalendar.getTime();
  }

  public static java.util.Date subtractYearMonthDurationFromUtilDateTime(java.util.Date date,
    org.apache.axis.types.Duration duration)
  {
    Calendar calendar = new GregorianCalendar(), result = new GregorianCalendar();

    calendar.setTime(date);

    result.set(calendar.get(Calendar.YEAR) - getYearsFromAxisDuration(duration),
      calendar.get(Calendar.MONTH) - getMonthsFromAxisDuration(duration), calendar.get(Calendar.DAY_OF_MONTH),
      calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));

    return result.getTime();
  }

  public static java.util.@NonNull Date subtractYearMonthDurationFromUtilDate(java.util.@NonNull Date date,
    org.apache.axis.types.@NonNull Duration duration)
  {
    Calendar calendar = new GregorianCalendar(), resultCalendar = new GregorianCalendar();

    calendar.setTime(date);

    resultCalendar.set(calendar.get(Calendar.YEAR) - getYearsFromAxisDuration(duration),
      calendar.get(Calendar.MONTH) - getMonthsFromAxisDuration(duration), calendar.get(Calendar.DAY_OF_MONTH));

    return resultCalendar.getTime();
  }

  public static java.util.@NonNull Date addDayTimeDurationToUtilDate(java.util.@NonNull Date date,
    org.apache.axis.types.@NonNull Duration duration)
  {
    Calendar calendar = new GregorianCalendar(), resultCalendar = new GregorianCalendar();

    calendar.setTime(date);

    resultCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
      calendar.get(Calendar.DAY_OF_MONTH) + getDaysFromAxisDuration(duration),
      calendar.get(Calendar.HOUR) + getHoursFromAxisDuration(duration),
      calendar.get(Calendar.MINUTE) + getMinutesFromAxisDuration(duration),
      (int)(calendar.get(Calendar.SECOND) + getSecondsFromAxisDuration(duration)));

    return resultCalendar.getTime();
  }

  public static java.util.@NonNull Date subtractDayTimeDurationFromUtilDate(java.util.@NonNull Date date,
    org.apache.axis.types.@NonNull Duration duration)
  {
    Calendar calendar = new GregorianCalendar(), resultCalendar = new GregorianCalendar();

    calendar.setTime(date);

    resultCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
      calendar.get(Calendar.DAY_OF_MONTH) - getDaysFromAxisDuration(duration),
      calendar.get(Calendar.HOUR) - getHoursFromAxisDuration(duration),
      calendar.get(Calendar.MINUTE) - getMinutesFromAxisDuration(duration),
      (int)(calendar.get(Calendar.SECOND) - getSecondsFromAxisDuration(duration)));

    return resultCalendar.getTime();
  }

  public static org.apache.axis.types.@NonNull Time addDayTimeDurationToTime(org.apache.axis.types.@NonNull Time time,
    org.apache.axis.types.@NonNull Duration duration)
  {
    Calendar calendar = time.getAsCalendar(), resultCalendar = new GregorianCalendar();

    resultCalendar.set(0, calendar.get(Calendar.DAY_OF_MONTH) + getDaysFromAxisDuration(duration),
      calendar.get(Calendar.HOUR) + getHoursFromAxisDuration(duration),
      calendar.get(Calendar.MINUTE) + getMinutesFromAxisDuration(duration),
      (int)(calendar.get(Calendar.SECOND) + getSecondsFromAxisDuration(duration)));

    return new org.apache.axis.types.Time(resultCalendar);
  }

  public static org.apache.axis.types.Time subtractDayTimeDurationFromTime(org.apache.axis.types.@NonNull Time time,
    org.apache.axis.types.@NonNull Duration duration)
  {
    Calendar calendar = time.getAsCalendar(), resultCalendar = new GregorianCalendar();

    resultCalendar.set(0, calendar.get(Calendar.DAY_OF_MONTH) - getDaysFromAxisDuration(duration),
      calendar.get(Calendar.HOUR) - getHoursFromAxisDuration(duration),
      calendar.get(Calendar.MINUTE) - getMinutesFromAxisDuration(duration),
      (int)(calendar.get(Calendar.SECOND) - getSecondsFromAxisDuration(duration)));

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
    return xsdDateTimeFormat.get().format(date);
  }

  @NonNull public static String utilDate2XSDDateString(java.util.@NonNull Date date)
  {
    return xsdDateFormat.get().format(date);
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

  public static java.util.@NonNull Date xsdDateTime2UtilDate(@NonNull XSDDateTime dateTime)
  {
    return xsdDateTimeString2UtilDate(dateTime.getContent());
  }

  public static java.util.@NonNull Date xsdDateTimeString2UtilDate(@NonNull String content)
  {
    try {
      return xsdDateTimeFormat.get().parse(content);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Invalid xsd:DateTime " + content);
    }
  }

  public static java.util.@NonNull Date xsdDate2UtilDate(@NonNull XSDDate date)
  {
    return xsdDateString2UtilDate(date.getContent());
  }

  public static java.util.@NonNull Date xsdDateString2UtilDate(@NonNull String content)
  {
    try {
      return xsdDateFormat.get().parse(content);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Invalid xsd:Date " + content);
    }
  }

  public static java.util.@NonNull Date xsdTimeString2UtilDate(@NonNull String content)
  {
    org.apache.axis.types.Time time = new org.apache.axis.types.Time(content);

    return time.getAsCalendar().getTime();
  }

  public static org.apache.axis.types.@NonNull Time xsdTimeString2AxisTime(@NonNull String content)
  {
    return new org.apache.axis.types.Time(content);
  }

  public static org.apache.axis.types.@NonNull Duration xsdDuration2AxisDuration(@NonNull XSDDuration duration)
  {
    return new org.apache.axis.types.Duration(duration.getContent());
  }

  public static org.apache.axis.types.@NonNull Duration xsdDurationString2AxisDuration(@NonNull String content)
  {
    return new org.apache.axis.types.Duration(content);
  }

  @NonNull public static String date2JDBCDateTimeString(java.util.@NonNull Date date)
  {
    return jdbcDateTimeFormat.get().format(date);
  }

  @NonNull public static String date2JDBCDateString(java.util.Date date)
  {
    return jdbcDateFormat.get().format(date);
  }

  public static java.util.@NonNull Date jdbcDateTimeString2UtilDate(@NonNull String content)
  {
    try {
      return jdbcDateTimeFormat.get().parse(content);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Invalid JDBC datetime " + content);
    }
  }

  public static java.util.@NonNull Date jdbcDateString2UtilDate(@NonNull String content)
  {
    try {
      return jdbcDateFormat.get().parse(content);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Invalid JDBC date " + content);
    }
  }

  public static boolean isValidXSDDateTimeString(@NonNull String content)
  {
    try {
      xsdDateTimeFormat.get().parse(content);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  public static boolean isValidXSDDateString(@NonNull String content)
  {
    try {
      xsdDateFormat.get().parse(content);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  public static boolean isValidXSDTimeString(@NonNull String content)
  {
    try {
      new org.apache.axis.types.Time(content);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static boolean isValidXSDDurationString(@NonNull String content)
  {
    try {
      new org.apache.axis.types.Duration(content);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  public static boolean isValidJDBCDateTimeString(@NonNull String content)
  {
    try {
      jdbcDateTimeFormat.get().parse(content);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  public static int addAxisDurationYears(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getYearsFromAxisDuration(duration1) + getYearsFromAxisDuration(duration2);
  }

  public static int addAxisDurationMonths(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getMonthsFromAxisDuration(duration1) + getMonthsFromAxisDuration(duration2);
  }

  public static int addAxisDurationDays(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getDaysFromAxisDuration(duration1) + getDaysFromAxisDuration(duration2);
  }

  public static int addAxisDurationHours(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getHoursFromAxisDuration(duration1) + getHoursFromAxisDuration(duration2);
  }

  public static int addAxisDurationMinutes(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getMinutesFromAxisDuration(duration1) + getMinutesFromAxisDuration(duration2);
  }

  public static double addAxisDurationSeconds(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getSecondsFromAxisDuration(duration1) + getSecondsFromAxisDuration(duration2);
  }

  public static int subtractAxisDurationYears(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getYearsFromAxisDuration(duration1) - getYearsFromAxisDuration(duration2);
  }

  public static int subtractAxisDurationMonths(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getMonthsFromAxisDuration(duration1) - getMonthsFromAxisDuration(duration2);
  }

  public static int subtractAxisDurationDays(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getDaysFromAxisDuration(duration1) - getDaysFromAxisDuration(duration2);
  }

  public static int subtractAxisDurationHours(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getHoursFromAxisDuration(duration1) - getHoursFromAxisDuration(duration2);
  }

  public static int subtractAxisDurationMinutes(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getMinutesFromAxisDuration(duration1) - getMinutesFromAxisDuration(duration2);
  }

  public static double subtractAxisDurationSeconds(org.apache.axis.types.@NonNull Duration duration1,
    org.apache.axis.types.@NonNull Duration duration2)
  {
    return getSecondsFromAxisDuration(duration1) - getSecondsFromAxisDuration(duration2);
  }

  private static int getYearsFromAxisDuration(org.apache.axis.types.@NonNull Duration duration)
  {
    if (duration.isNegative())
      return -duration.getYears();
    else
      return duration.getYears();
  }

  private static int getMonthsFromAxisDuration(org.apache.axis.types.@NonNull Duration duration)
  {
    if (duration.isNegative())
      return -duration.getMonths();
    else
      return duration.getMonths();
  }

  private static int getDaysFromAxisDuration(org.apache.axis.types.@NonNull Duration duration)
  {
    if (duration.isNegative())
      return -duration.getDays();
    else
      return duration.getDays();
  }

  private static int getHoursFromAxisDuration(org.apache.axis.types.@NonNull Duration duration)
  {
    if (duration.isNegative())
      return -duration.getHours();
    else
      return duration.getHours();
  }

  private static int getMinutesFromAxisDuration(org.apache.axis.types.@NonNull Duration duration)
  {
    if (duration.isNegative())
      return -duration.getMinutes();
    else
      return duration.getMinutes();
  }

  private static double getSecondsFromAxisDuration(org.apache.axis.types.@NonNull Duration duration)
  {
    if (duration.isNegative())
      return -duration.getSeconds();
    else
      return duration.getSeconds();
  }
}
