package org.swrlapi.core.xsd;

import org.semanticweb.owlapi.vocab.XSDVocabulary;

import java.util.Date;

public class XSDDateTime extends XSDType<XSDDateTime>
{
  private final Date datetime;

  public XSDDateTime(String content)
  {
    super(content, XSDVocabulary.DATE_TIME.getIRI());

    this.datetime = XSDTimeUtil.xsdDateTimeString2Date(getContent());
  }

  public XSDDateTime(java.util.Date datetime)
  {
    super(XSDTimeUtil.utilDate2XSDDateTimeString(datetime), XSDVocabulary.DATE_TIME.getIRI());

    this.datetime = datetime;
  }

  @Override
  protected void validate()
  {
    if (getContent() == null)
      throw new IllegalArgumentException("null content for xsd:DateTime");

    if (!XSDTimeUtil.isValidXSDDateTime(getContent()))
      throw new IllegalArgumentException("invalid xsd:DateTime " + getContent());
  }

  @Override
  public String getContent()
  {
    return this.content;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
      return true;

    if (!(o instanceof XSDDateTime))
      return false;

    XSDDateTime otherDateTime = (XSDDateTime)o;

    return this.datetime != null && otherDateTime.datetime != null && this.datetime.equals(otherDateTime.datetime);
  }

  @Override
  public int hashCode()
  {
    int code = 136;
    code += this.datetime.hashCode();
    return code;
  }

  @Override
  public int compareTo(XSDDateTime o)
  {
    if (o == null)
      throw new NullPointerException();

    if (this == o)
      return 0;

    return XSDTimeUtil.compareDateTimes(this.datetime, o.datetime);
  }
}
