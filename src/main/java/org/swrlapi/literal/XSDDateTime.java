package org.swrlapi.literal;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

import java.util.Date;
import java.util.Objects;

public class XSDDateTime extends XSDType<XSDDateTime>
{
  private final ThreadLocal<@NonNull Date> datetime = new ThreadLocal<Date>();

  public XSDDateTime(@NonNull String content)
  {
    super(content, XSDVocabulary.DATE_TIME.getIRI());

    this.datetime.set(XSDTimeUtil.xsdDateTimeString2UtilDate(content));
  }

  public XSDDateTime(@NonNull Date datetime)
  {
    super(XSDTimeUtil.utilDate2XSDDateTimeString(datetime), XSDVocabulary.DATE_TIME.getIRI());

    this.datetime.set(new Date(datetime.getTime()));
  }

  @Override protected void validate()
  {
    if (getContent() == null)
      throw new IllegalArgumentException("null content for xsd:DateTime");

    if (!XSDTimeUtil.isValidXSDDateTimeString(getContent()))
      throw new IllegalArgumentException("invalid xsd:DateTime " + getContent());
  }


  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    XSDDateTime that = (XSDDateTime)o;
    return datetime.get().getTime() == that.datetime.get().getTime();
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    return Objects.hash(datetime.get());
  }

  @SideEffectFree @Deterministic @Override public int compareTo(@NonNull XSDDateTime o)
  {
    if (o == null)
      throw new NullPointerException();

    if (this == o)
      return 0;

    return XSDTimeUtil.compareDateTimes(this.datetime.get(), o.datetime.get());
  }
}
