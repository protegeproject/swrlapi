package org.swrlapi.core.xsd;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.apache.axis.types.Duration;

public class XSDDuration extends XSDType<XSDDuration>
{
  @NonNull private final Duration duration;

  public XSDDuration(@NonNull String content)
  {
    super(content, XSDVocabulary.DURATION.getIRI());

    this.duration = XSDTimeUtil.xsdDurationString2AxisDuration(getContent());
  }

  @Override protected void validate()
  {
    if (getContent() == null)
      throw new IllegalArgumentException("null content for XSD:duration literal");

    if (!XSDTimeUtil.isValidXSDDuration(getContent()))
      throw new IllegalArgumentException("invalid xsd:Duration: " + getContent());
  }

  @NonNull @Override public String getContent()
  {
    return this.content;
  }

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;

    if (!(o instanceof XSDDuration))
      return false;

    XSDDuration otherDuration = (XSDDuration)o;

    return this.duration != null && otherDuration.duration != null && this.duration.equals(otherDuration.duration);
  }

  @Override public int hashCode()
  {
    int code = 34;
    code += this.duration.hashCode();
    return code;
  }

  @Override public int compareTo(@NonNull XSDDuration o)
  {
    if (o == null)
      throw new NullPointerException();

    if (this == o)
      return 0;

    return XSDTimeUtil.compareDurations(this.duration, o.duration);
  }
}
