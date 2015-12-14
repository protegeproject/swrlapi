package org.swrlapi.builtins.temporal;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

/**
 * A class supporting processing of datetime strings. This class will be specialized by subclasses to deal with
 * different datetime formats, e.g., XSD and JDBC datetimes.
 */
abstract class DatetimeStringProcessor
{
  @NonNull private final SimpleDateFormat dateFormat;
  @NonNull private final String delimiters;

  // The number of tokens (including delimeters) necessary to strip a datetime to the specified granularity
  @NonNull private final int[] gTokenIndex;

  @NonNull private final String datetimeRoundDownPadding[]; // Padding for a partially specified datetime
  @NonNull private final String datetimeRoundUpPadding[]; // Padding for a partially specified datetime

  /**
   * @param dateFormat               A date format
   * @param delimiters               Delimiters
   * @param gTokenIndex              Indices of granularity tokens
   * @param datetimeRoundDownPadding Padding for rounding down
   * @param datetimeRoundUpPadding   Padding for rounding up
   */
  DatetimeStringProcessor(@NonNull SimpleDateFormat dateFormat, @NonNull String delimiters, @NonNull int gTokenIndex[],
    @NonNull String datetimeRoundDownPadding[], @NonNull String datetimeRoundUpPadding[])
  {
    this.dateFormat = dateFormat;
    this.delimiters = delimiters;
    this.gTokenIndex = gTokenIndex.clone();
    this.datetimeRoundUpPadding = datetimeRoundUpPadding.clone();
    this.datetimeRoundDownPadding = datetimeRoundDownPadding.clone();
  }

  @NonNull protected abstract String constructDatetimeStringFromMillisecondsFrom1970Count(long milliseconds);

  /**
   * Take a granule count (from the beginning of calendar time, i.e., January 1st 1 C.E) at any granularity and convert
   * it to a datetime string.
   */
  @NonNull public String granuleCount2DatetimeString(long granuleCount, int granularity) throws TemporalException
  {
    Temporal.checkGranularity(granularity);

    long granuleCountInMilliSeconds = Temporal.convertGranuleCount(granuleCount, granularity, Temporal.MILLISECONDS);

    // The java.sql.Timestamp constructor will correctly deal with negative milliseconds.

    return constructDatetimeStringFromMillisecondsFrom1970Count(
      granuleCountInMilliSeconds - Temporal.MillisecondsTo1970); // Call subclass.
  }

  /**
   * Take a full-specification datetime string (which will have the granularity of milliseconds), discard any
   * information that is finer than the supplied granularity, and return a full-specification datetime string, e.g.,
   * Converting the JDBC datetime '1988-02-03 10:10:11.433' to a granularity of MONTHS will produce '1988-02-01
   * 00:00:00.000'.
   */
  @NonNull public String expressDatetimeStringAtGranularity(@NonNull String datetimeString, int granularity,
    boolean roundUp) throws TemporalException
  {
    String localDatetimeString = stripDatetimeString(datetimeString, granularity); // Also checks granularity for
    // sanity.

    return padDatetimeString(localDatetimeString, roundUp);
  }

  @NonNull public String expressDatetimeStringAtGranularity(@NonNull String datetimeString, int granularity)
    throws TemporalException
  {
    return expressDatetimeStringAtGranularity(datetimeString, granularity, false);
  }

  private void checkDatetimeString(@NonNull String datetimeString) throws TemporalException
  {
    String localDatetimeString = datetimeString.trim();
    java.util.Date date = this.dateFormat.parse(localDatetimeString, new ParsePosition(0));

    if (date == null)
      Temporal.throwInvalidDatetimeStringException(datetimeString);
  }

  /**
   * Take a possibly incomplete datetime string and make it to a valid datetime string, discarding any information finer
   * than the supplied granularity, and round up or down, e.g., '1988-02' is converted to the XSD datetime
   * '1988-02-01T00:00:00.000' when rounded down at any granularity finer than 'days'; the JDBC datetime '1988-1-1
   * 12:10' is converted to '1988-1-1 12:59:59.999' when rounded up at a granularity of hours.
   */
  @NonNull public String normalizeDatetimeString(@NonNull String datetimeString, int granularity, boolean roundUp)
    throws TemporalException
  {
    String localDatetimeString = stripDatetimeString(datetimeString.trim(), granularity);
    String normalizedDatetimeString = padDatetimeString(localDatetimeString, roundUp);

    checkDatetimeString(normalizedDatetimeString);

    return normalizedDatetimeString;
  }

  @NonNull public String normalizeDatetimeString(@NonNull String datetime, int granularity) throws TemporalException
  {
    return normalizeDatetimeString(datetime, granularity, false);
  }

  @NonNull public String normalizeDatetimeString(@NonNull String datetime) throws TemporalException
  {
    return normalizeDatetimeString(datetime, Temporal.FINEST, false);
  }

  /**
   * Take a possibly incomplete datetime string and pad it to a full specification datetime string rounding up or down,
   * e.g., the JDBC datetime '1988-10-10 12' becomes '1988-10-10 12:59:59:999' when rounded up and '1988-10-10
   * 12:00:00.000' when rounded down.
   */
  @NonNull private String padDatetimeString(@NonNull String datetimeString, boolean roundUp) throws TemporalException
  {
    StringTokenizer tokenizer = new StringTokenizer(datetimeString.trim(),
      this.delimiters); // Do not count delimiters as tokens.
    int numberOfTokens = tokenizer.countTokens();

    if (numberOfTokens == 0)
      Temporal.throwInvalidDatetimeStringException(datetimeString);

    int granularity = numberOfTokens - 1; // YEARS will have one token, MONTHS 2, etc., so subtract one for granularity
    String token = tokenizer.nextToken().trim();
    long yearCount = Long.parseLong(token);

    if (roundUp) {
      if (granularity != Temporal.MONTHS)
        return datetimeString.trim() + this.datetimeRoundUpPadding[granularity];
      else { // If only a month is specified, deal with the days-in-month issue.
        token = tokenizer.nextToken().trim();
        long monthCount = Long.parseLong(token);
        Temporal.checkMonthCount(monthCount);
        long daysInMonth = Temporal.getDaysInMonth(monthCount);
        if (Temporal.isLeapYear(yearCount) && (monthCount == 2))
          daysInMonth++;
        return datetimeString.trim() + "-" + daysInMonth + this.datetimeRoundUpPadding[Temporal.DAYS];
      }
    } else
      return datetimeString.trim() + this.datetimeRoundDownPadding[granularity];
  }

  /*
   * Strip off any information supplied that is finer than the specified granularity. e.g., the JDBC datetime string
   * '1988-02-01 12:01:22.000' expressed with a granularity of MONTHS is '1988-02'. If a datetime string is supplied
   * that is coarser than the requested granularity, then we just return the original datetime string, e.g, '1999-02-01'
   * expressed with a granularity of MINUTES is '1999-02-01'.
   */
  @NonNull public String stripDatetimeString(@NonNull String datetimeString, int granularity) throws TemporalException
  {
    String strippedDatetimeString = "";

    Temporal.checkGranularity(granularity);

    StringTokenizer tokenizer = new StringTokenizer(datetimeString, this.delimiters,
      true); // Return all tokens including delimiters.
    int numberOfTokens = tokenizer.countTokens();

    if (numberOfTokens == 0)
      Temporal.throwInvalidDatetimeStringException(datetimeString);

    if (numberOfTokens <= this.gTokenIndex[granularity])
      strippedDatetimeString = datetimeString;
    else {
      try {
        int i = 0;
        while (i < this.gTokenIndex[granularity] && tokenizer.hasMoreTokens()) {
          strippedDatetimeString = strippedDatetimeString + tokenizer.nextToken();
          i++;
        }
      } catch (Exception e) {
        Temporal.throwInvalidDatetimeStringException(datetimeString);
      }
    }
    return strippedDatetimeString;
  }

  /**
   * Get the finest specified granularity of a datetime. For example, the finest granularity specified in the string
   * '1999-02' is months.
   */
  public int getFinestSpecifiedGranularity(@NonNull String datetimeString) throws TemporalException
  {
    StringTokenizer tokenizer = new StringTokenizer(datetimeString, this.delimiters, true); // Return all tokens
    // including delimiters.
    int numberOfTokens = tokenizer.countTokens();
    int granularity = -1;

    for (int i = 0; i < this.gTokenIndex.length; i++) {
      if (numberOfTokens == this.gTokenIndex[i]) {
        granularity = i;
        break;
      }
    }

    if (granularity == -1)
      Temporal.throwInvalidDatetimeStringException(datetimeString);

    return granularity;
  }

  public int getYears(@NonNull String datetimeString) throws TemporalException
  {
    return getTimeComponent(datetimeString, Temporal.YEARS);
  }

  public int getMonths(@NonNull String datetimeString) throws TemporalException
  {
    return getTimeComponent(datetimeString, Temporal.MONTHS);
  }

  public int getDays(@NonNull String datetimeString) throws TemporalException
  {
    return getTimeComponent(datetimeString, Temporal.DAYS);
  }

  public int getHours(@NonNull String datetimeString) throws TemporalException
  {
    return getTimeComponent(datetimeString, Temporal.HOURS);
  }

  public int getMinutes(@NonNull String datetimeString) throws TemporalException
  {
    return getTimeComponent(datetimeString, Temporal.MINUTES);
  }

  public int getSeconds(@NonNull String datetimeString) throws TemporalException
  {
    return getTimeComponent(datetimeString, Temporal.SECONDS);
  }

  public int getMilliseconds(@NonNull String datetimeString) throws TemporalException
  {
    return getTimeComponent(datetimeString, Temporal.MILLISECONDS);
  }

  private int getTimeComponent(@NonNull String datetimeString, int granularity) throws TemporalException
  {
    StringTokenizer tokenizer;
    int result = -1;
    int numberOfTokens;

    Temporal.checkGranularity(granularity);

    tokenizer = new StringTokenizer(datetimeString, this.delimiters, true); // Return all tokens including delimiters.
    numberOfTokens = tokenizer.countTokens();

    if (numberOfTokens == 0)
      Temporal.throwInvalidDatetimeStringException(datetimeString);

    if (numberOfTokens < this.gTokenIndex[granularity])
      throw new TemporalException(
        "cannot extract " + Temporal.getStringGranularityRepresentation(granularity) + " from incomplete datetime "
          + datetimeString);

    try {
      int i = 1;
      while (i++ < this.gTokenIndex[granularity])
        tokenizer.nextToken();
      return new Integer(tokenizer.nextToken());
    } catch (Exception e) {
      Temporal.throwInvalidDatetimeStringException(datetimeString);
    }
    return result;
  }
}
