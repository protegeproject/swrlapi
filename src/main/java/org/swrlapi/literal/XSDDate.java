package org.swrlapi.literal;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import dataflow.quals.Deterministic;
import dataflow.quals.SideEffectFree;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

import java.util.Date;

public class XSDDate extends XSDType<XSDDate>
{
  @NonNull private final Date date;

  public XSDDate(@NonNull String content)
  {
    super(content, XSDVocabulary.DATE.getIRI());

    this.date = XSDTimeUtil.xsdDateString2Date(content);
  }

  public XSDDate(@NonNull Date date)
  {
    super(XSDTimeUtil.utilDate2XSDDateString(date), XSDVocabulary.DATE.getIRI());

    this.date = date;
  }

  @Override protected void validate()
  {
    if (getContent() == null)
      throw new IllegalArgumentException("null content for xsd:Date");

    if (!XSDTimeUtil.isValidXSDDate(getContent()))
      throw new IllegalArgumentException("invalid xsd:Date '" + getContent() + "'");
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    XSDDate xsdDate = (XSDDate)o;

    return !(date != null ? !date.equals(xsdDate.date) : xsdDate.date != null);
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
