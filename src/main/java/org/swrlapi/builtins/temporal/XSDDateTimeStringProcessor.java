package org.swrlapi.builtins.temporal;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * A class supporting processing of datetime strings represented in the standard XML Schema date format
 * 'yyyy-MM-ddTHH:mm:ss.S'.
 * <p>
 * TODO Time zone offset specifications are not yet supported.
 */
class XSDDateTimeStringProcessor extends DatetimeStringProcessor
{
  @NonNull private static final SimpleDateFormat _dateFormat = new SimpleDateFormat("y-M-d'T'h:m:s.S");
  @NonNull private static final String _delimiters = "-:.TZ";

  // The number of tokens (including delimeters) necessary to strip a datetime to a specified granularity.
  @NonNull private static final int[] _gTokenIndex = { 1, 3, 5, 7, 9, 11, 13 }; // 1=YEARS, 3=MONTHS etc.

  // Strings to pad a partially specified datetime.
  @NonNull private static final String _datetimeRoundDownPadding[] = { "-01-01T00:00:00.000", "-01T00:00:00.000",
    "T00:00:00.000", ":00:00.000", ":00.000", ".000", "" };

  // Day-in-month must be dealt with separately.
  @NonNull private static final String _datetimeRoundUpPadding[] = { "-12-31T23:59:59.999", "", "T23:59:59.999",
    ":59:59.999", ":59.999", ".999", "" };

  public XSDDateTimeStringProcessor()
  {
    super(_dateFormat, _delimiters, _gTokenIndex, _datetimeRoundDownPadding, _datetimeRoundUpPadding);
  }

  @NonNull @Override protected String constructDatetimeStringFromMillisecondsFrom1970Count(long millisecondsFrom1970)
  {
    Timestamp ts = new Timestamp(millisecondsFrom1970);

    return ts.toString().replace(' ', 'T'); // Timestamp.toString returns in JDBC format so replace space with 'T'.
  }
}
