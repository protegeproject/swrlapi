package org.swrlapi.builtins.temporal;

import checkers.nullness.quals.NonNull;
import dataflow.quals.SideEffectFree;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that represents a single instant in time.
 */
class Instant
{
  @NonNull private final Temporal temporal;
  private long granuleCount; // Granule count since 1 C.E.
  private int granularity;

  private final long granuleCountArray[] = new long[Temporal.NUMBER_OF_GRANULARITIES];

  public Instant(@NonNull Temporal temporal, long granuleCount, int granularity)
  {
    this.temporal = temporal;
    this.granuleCount = granuleCount;
    this.granularity = granularity;

    for (int i = 0; i < Temporal.NUMBER_OF_GRANULARITIES; i++)
      this.granuleCountArray[i] = -1;
  }

  public Instant(@NonNull Temporal temporal, @NonNull Timestamp timestamp) throws TemporalException
  {
    this(temporal, timestamp, Temporal.FINEST);
  }

  public Instant(@NonNull Temporal temporal, @NonNull Timestamp timestamp, int granularity) throws TemporalException
  {
    this.temporal = temporal;
    this.granuleCount = temporal.sqlTimestamp2GranuleCount(timestamp, granularity);

    this.granularity = granularity;

    for (int i = 0; i < Temporal.NUMBER_OF_GRANULARITIES; i++)
      this.granuleCountArray[i] = -1;
  }

  public Instant(@NonNull Temporal temporal, java.util.@NonNull Date date) throws TemporalException
  {
    this(temporal, date, Temporal.FINEST);
  }

  public Instant(@NonNull Temporal temporal, java.util.@NonNull Date date, int granularity) throws TemporalException
  {
    this.temporal = temporal;
    this.granuleCount = Temporal.utilDate2GranuleCount(date, granularity);
    this.granularity = granularity;

    for (int i = 0; i < Temporal.NUMBER_OF_GRANULARITIES; i++)
      this.granuleCountArray[i] = -1;
  }

  public Instant(@NonNull Temporal temporal, java.sql.@NonNull Date date) throws TemporalException
  {
    this(temporal, date, Temporal.FINEST);
  }

  public Instant(@NonNull Temporal temporal, java.sql.@NonNull Date date, int granularity) throws TemporalException
  {
    this.temporal = temporal;
    this.granuleCount = Temporal.sqlDate2GranuleCount(date, granularity);
    this.granularity = granularity;

    for (int i = 0; i < Temporal.NUMBER_OF_GRANULARITIES; i++)
      this.granuleCountArray[i] = -1;
  }

  public Instant(@NonNull Temporal t, @NonNull String datetimeString, int g, boolean roundUp) throws TemporalException
  {
    String localDatetimeString;

    if (datetimeString.equals("now"))
      localDatetimeString = t.getNowDatetimeString();
    else
      localDatetimeString = datetimeString.trim();

    localDatetimeString = t.normalizeDatetimeString(localDatetimeString, g, roundUp);

    localDatetimeString = t.expressDatetimeStringAtGranularity(localDatetimeString, g);

    this.temporal = t;
    this.granularity = g;
    this.granuleCount = t.datetimeString2GranuleCount(localDatetimeString, g);

    for (int i = 0; i < Temporal.NUMBER_OF_GRANULARITIES; i++)
      this.granuleCountArray[i] = -1;
  }

  public Instant(@NonNull Temporal temporal, @NonNull String datetimeString, int granularity) throws TemporalException
  {
    this(temporal, datetimeString, granularity, false);
  }

  public Instant(@NonNull Temporal temporal, @NonNull String datetimeString) throws TemporalException
  {
    this(temporal, datetimeString, Temporal.FINEST, false);
  }

  public Instant(@NonNull Temporal temporal, @NonNull String datetimeString, boolean roundUp) throws TemporalException
  {
    this(temporal, datetimeString, Temporal.FINEST, roundUp);
  }

  public Instant(@NonNull Temporal temporal, @NonNull Instant instant) throws TemporalException
  {
    this(temporal, instant.getGranuleCount(instant.getGranularity()), instant.getGranularity());
  }

  public int getGranularity()
  {
    return this.granularity;
  }

  public void setGranularity(int granularity) throws TemporalException
  {
    if (this.granularity == granularity)
      return;

    this.granuleCount = Temporal.convertGranuleCount(this.granuleCount, this.granularity, granularity);

    this.granularity = granularity;

    for (int i = 0; i < Temporal.NUMBER_OF_GRANULARITIES; i++)
      this.granuleCountArray[i] = -1;
  }

  public void setGranuleCount(long granuleCount, int granularity)
  {
    this.granuleCount = granuleCount;
    this.granularity = granularity;

    for (int i = 0; i < Temporal.NUMBER_OF_GRANULARITIES; i++)
      this.granuleCountArray[i] = -1;
  }

  public long getGranuleCount()
  {
    return this.granuleCount;
  }

  // We use an array to cache the result of granule count conversions for each granularity.
  public long getGranuleCount(int g) throws TemporalException
  {
    long resultGranuleCount;

    if (getGranularity() != g) {

      if (this.granuleCountArray[g] == -1) { // No conversion yet for this granularity.
        resultGranuleCount = Temporal.convertGranuleCount(this.granuleCount, getGranularity(), g);
        this.granuleCountArray[g] = resultGranuleCount;
      } else
        resultGranuleCount = this.granuleCountArray[g];
    } else
      resultGranuleCount = getGranuleCount(); // Same granularity.

    return resultGranuleCount;
  }

  @NonNull public String getDatetimeString() throws TemporalException
  {
    return getDatetimeString(Temporal.FINEST);
  }

  @NonNull public String getDatetimeString(int g) throws TemporalException
  {
    long localGranuleCount = getGranuleCount(g);

    return this.temporal.stripDatetimeString(this.temporal.granuleCount2DatetimeString(localGranuleCount, g), g);
  }

  public java.util.Date getUtilDate() throws TemporalException
  {
    return getUtilDate(this.granularity);
  }

  public java.util.Date getUtilDate(int g) throws TemporalException
  {
    long localGranuleCount = this.granuleCount;

    return Temporal.granuleCount2UtilDate(localGranuleCount, g);
  }

  public java.sql.Date getSQLDate() throws TemporalException
  {
    return getSQLDate(this.granularity);
  }

  public java.sql.Date getSQLDate(int g) throws TemporalException
  {
    long localGranuleCount = this.granuleCount;

    return Temporal.granuleCount2SQLDate(localGranuleCount, g);
  }

  public boolean isStartOfTime()
  {
    return (this.granuleCount == 0);
  }

  @SideEffectFree @NonNull public String toString(int g) throws TemporalException
  {
    return getDatetimeString(g);
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    try {
      return toString(Temporal.FINEST);
    } catch (TemporalException e) {
      return "<INVALID_INSTANT: " + e.toString() + ">";
    }
  }

  public void addGranuleCount(long gc, int g) throws TemporalException
  {
    long plusGranuleCount;

    plusGranuleCount = Temporal.convertGranuleCount(gc, g, this.granularity);

    this.granuleCount = getGranuleCount() + plusGranuleCount;

    for (int i = 0; i < Temporal.NUMBER_OF_GRANULARITIES; i++)
      this.granuleCountArray[i] = -1;
  }

  public void subtractGranuleCount(long gc, int g) throws TemporalException
  {
    long subtractGranuleCount;

    subtractGranuleCount = Temporal.convertGranuleCount(gc, g, this.granularity);

    this.granuleCount -= subtractGranuleCount;

    for (int i = 0; i < Temporal.NUMBER_OF_GRANULARITIES; i++)
      this.granuleCountArray[i] = -1;
  }

  public long duration(@NonNull Instant i2, int g) throws TemporalException
  {
    return java.lang.Math.abs(getGranuleCount(g) - i2.getGranuleCount(g));
  }

  public boolean before(@NonNull Instant i2, int g) throws TemporalException
  {
    return getGranuleCount(g) < i2.getGranuleCount(g);
  }

  public boolean after(@NonNull Instant i2, int g) throws TemporalException
  {
    return getGranuleCount(g) > i2.getGranuleCount(g);
  }

  public boolean equals(@NonNull Instant i2, int g) throws TemporalException
  {
    return getGranuleCount(g) == i2.getGranuleCount(g);
  }

  public boolean meets(@NonNull Instant i2, int g) throws TemporalException
  {
    return (((getGranuleCount(g) + 1) == i2.getGranuleCount(g)) || (getGranuleCount(g) == i2.getGranuleCount(g)));
  }

  public boolean met_by(@NonNull Instant i2, int g) throws TemporalException
  {
    return i2.meets(this, g);
  }

  public boolean adjacent(@NonNull Instant i2, int g) throws TemporalException
  {
    return (meets(i2, g) || met_by(i2, g));
  }

  public boolean overlaps(Instant i2, int g) throws TemporalException
  {
    Temporal.checkGranularity(g);

    return false; // Instants cannot overlap.
  }

  public boolean overlapped_by(@NonNull Instant i2, int g) throws TemporalException
  {
    return i2.overlaps(this, g);
  }

  public boolean contains(Instant i2, int g) throws TemporalException
  {
    Temporal.checkGranularity(g);

    return false; // Instant cannot contain another instant.
  }

  public boolean during(Instant i2, int g) throws TemporalException
  {
    Temporal.checkGranularity(g);

    return false; // Instant cannot be during another instant.
  }

  public boolean starts(Instant i2, int g) throws TemporalException
  {
    Temporal.checkGranularity(g);

    return false; // One instant cannot start another
  }

  public boolean started_by(@NonNull Instant i2, int g) throws TemporalException
  {
    return i2.starts(this, g);
  }

  public boolean finishes(Instant i2, int g) throws TemporalException
  {
    Temporal.checkGranularity(g);

    return false; // One instant cannot finish another
  }

  public boolean finished_by(@NonNull Instant i2, int g) throws TemporalException
  {
    return i2.finishes(this, g);
  }

  // Take a list of instants and remove duplicate identical elements.
  @NonNull public List<@NonNull Instant> coalesce(@NonNull List<@NonNull Instant> instants, int g) throws TemporalException
  {
    Instant i1, i2;
    List<@NonNull Instant> resultList = new ArrayList<>();

    // Loop through each instant in the list trying to merge with other instants.
    while (!instants.isEmpty()) {
      i1 = instants.get(0);
      instants.remove(0); // Remove each instants as we deal with it.

      // See if we can merge this instant with the remaining instants in the list. If we merge this instant with an
      // existing instant later
      // in the list, remove the later element.
      Iterator<@NonNull Instant> iterator = instants.iterator();
      while (iterator.hasNext()) {
        i2 = iterator.next();
        // Merge contiguous or overlapping periods.
        if (i1.equals(i2, g)) {
          iterator.remove(); // We have merged with instant i2 - remove it.
        }
      }
      resultList.add(i1);
    }

    return resultList;
  }

}
