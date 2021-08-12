package org.swrlapi.literal;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

import java.util.Date;
import java.util.Objects;

public class XSDDate extends XSDType<XSDDate>
{
  private final ThreadLocal<@NonNull Date> date = new ThreadLocal<>();

  public XSDDate(@NonNull String content)
  {
    super(content, XSDVocabulary.DATE.getIRI());

    this.date.set(XSDTimeUtil.xsdDateString2UtilDate(content));
  }

  public XSDDate(@NonNull Date date)
  {
    super(XSDTimeUtil.utilDate2XSDDateString(date), XSDVocabulary.DATE.getIRI());

    this.date.set(new Date(date.getTime()));
  }

  @Override protected void validate()
  {
    if (getContent() == null)
      throw new IllegalArgumentException("null content for xsd:Date");

    if (!XSDTimeUtil.isValidXSDDateString(getContent()))
      throw new IllegalArgumentException("invalid xsd:Date '" + getContent() + "'");
  }

  @SideEffectFree @Deterministic @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    XSDDate that = (XSDDate)o;
    return date.get().getTime() == that.date.get().getTime();
  }

  @Override public int hashCode()
  {
    return Objects.hash(date.get());
  }

  @SideEffectFree @Deterministic @Override public int compareTo(@NonNull XSDDate o)
  {
    if (o == null)
      throw new NullPointerException();

    if (this == o)
      return 0;

    return XSDTimeUtil.compareDates(this.date.get(), o.date.get());
  }
}
