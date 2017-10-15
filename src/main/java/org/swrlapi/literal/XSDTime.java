package org.swrlapi.literal;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.apache.axis.types.Time;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

import java.util.Date;

public class XSDTime extends XSDType<XSDTime>
{
  private final @NonNull Time time;

  public XSDTime(@NonNull String content)
  {
    super(content, XSDVocabulary.TIME.getIRI());

    this.time = XSDTimeUtil.xsdTimeString2AxisTime(content);
  }

  public XSDTime(@NonNull Date date)
  {
    super(XSDTimeUtil.utilDate2XSDTimeString(date), XSDVocabulary.TIME.getIRI());

    this.time = XSDTimeUtil.utilDate2XSDTime(date);
  }

  @Override protected void validate()
  {
    if (getContent() == null)
      throw new IllegalArgumentException("null content for xsd:Time");

    if (!XSDTimeUtil.isValidXSDTimeString(getContent()))
      throw new IllegalArgumentException("invalid xsd:Time '" + getContent() + "'");
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;

    if (!(o instanceof XSDTime))
      return false;

    XSDTime otherTime = (XSDTime)o;

    return this.time != null && otherTime.time != null && this.time.equals(otherTime.time);
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int code = 156;
    code += this.time.hashCode();
    return code;
  }

  @SideEffectFree @Deterministic @Override public int compareTo(@NonNull XSDTime o)
  {
    if (o == null)
      throw new NullPointerException();

    if (this == o)
      return 0;

    return XSDTimeUtil.compareAxisTimes(this.time, o.time);
  }
}
