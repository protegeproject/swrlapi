package org.swrlapi.literal;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

import java.util.Date;

public class XSDDateTime extends XSDType<XSDDateTime>
{
  @NonNull private final Date datetime;

  public XSDDateTime(@NonNull String content)
  {
    super(content, XSDVocabulary.DATE_TIME.getIRI());

    this.datetime = XSDTimeUtil.xsdDateTimeString2Date(content);
  }

  public XSDDateTime(@NonNull Date datetime)
  {
    super(XSDTimeUtil.utilDate2XSDDateTimeString(datetime), XSDVocabulary.DATE_TIME.getIRI());

    this.datetime = datetime;
  }

  @Override protected void validate()
  {
    if (getContent() == null)
      throw new IllegalArgumentException("null content for xsd:DateTime");

    if (!XSDTimeUtil.isValidXSDDateTime(getContent()))
      throw new IllegalArgumentException("invalid xsd:DateTime " + getContent());
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;

    if (!(o instanceof XSDDateTime))
      return false;

    XSDDateTime otherDateTime = (XSDDateTime)o;

    return this.datetime != null && otherDateTime.datetime != null && this.datetime.equals(otherDateTime.datetime);
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int code = 136;
    code += this.datetime.hashCode();
    return code;
  }

  @SideEffectFree @Deterministic @Override public int compareTo(@NonNull XSDDateTime o)
  {
    if (o == null)
      throw new NullPointerException();

    if (this == o)
      return 0;

    return XSDTimeUtil.compareDateTimes(this.datetime, o.datetime);
  }
}
