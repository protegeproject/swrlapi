package org.swrlapi.literal;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

import java.util.Date;
import java.util.Objects;

public class XSDDate extends XSDType<XSDDate>
{
  @NonNull private final Date date;

  public XSDDate(@NonNull String content)
  {
    super(content, XSDVocabulary.DATE.getIRI());

    this.date = XSDTimeUtil.xsdDateString2UtilDate(content);
  }

  public XSDDate(@NonNull Date date)
  {
    super(XSDTimeUtil.utilDate2XSDDateString(date), XSDVocabulary.DATE.getIRI());

    this.date = new Date(date.getTime());
  }

  @Override protected void validate()
  {
    if (getContent() == null)
      throw new IllegalArgumentException("null content for xsd:Date");

    if (!XSDTimeUtil.isValidXSDDateString(getContent()))
      throw new IllegalArgumentException("invalid xsd:Date '" + getContent() + "'");
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    XSDDate xsdDate = (XSDDate)o;

    return Objects.equals(date, xsdDate.date);
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    return date != null ? date.hashCode() : 0;
  }

  @SideEffectFree @Deterministic @Override public int compareTo(@NonNull XSDDate o)
  {
    if (o == null)
      throw new NullPointerException();

    if (this == o)
      return 0;

    return XSDTimeUtil.compareDates(this.date, o.date);
  }
}
