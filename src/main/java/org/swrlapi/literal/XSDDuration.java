package org.swrlapi.literal;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.apache.axis.types.Duration;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

public class XSDDuration extends XSDType<XSDDuration>
{
  @NonNull private final Duration duration;

  public XSDDuration(@NonNull String content)
  {
    super(content, XSDVocabulary.DURATION.getIRI());

    this.duration = XSDTimeUtil.xsdDurationString2AxisDuration(content);
  }

  @Override protected void validate()
  {
    if (getContent() == null)
      throw new IllegalArgumentException("null content for XSD:duration literal");

    if (!XSDTimeUtil.isValidXSDDurationString(getContent()))
      throw new IllegalArgumentException("invalid xsd:Duration: " + getContent());
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;

    if (!(o instanceof XSDDuration))
      return false;

    XSDDuration otherDuration = (XSDDuration)o;

    return this.duration != null && otherDuration.duration != null && this.duration.equals(otherDuration.duration);
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int code = 34;
    code += this.duration.hashCode();
    return code;
  }

  @SideEffectFree @Deterministic @Override public int compareTo(@NonNull XSDDuration o)
  {
    if (o == null)
      throw new NullPointerException();

    if (this == o)
      return 0;

    return XSDTimeUtil.compareAxisDurations(this.duration, o.duration);
  }
}
