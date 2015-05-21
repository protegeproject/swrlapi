package org.swrlapi.core;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.factory.NaturalOrderComparator;

import java.net.URI;
import java.util.Comparator;

/**
 * A very basic literal comparator for core datatypes. Now OWL specification conformant.
 * 
 * @see org.semanticweb.owlapi.model.OWLLiteral
 */
public final class OWLLiteralComparator implements Comparator<OWLLiteral>
{
  @NonNull private static final Comparator<String> naturalOrderComparator = NaturalOrderComparator.NUMERICAL_ORDER;

  @NonNull public static final Comparator<OWLLiteral> COMPARATOR = new OWLLiteralComparator();

  @Override
  public int compare(@NonNull OWLLiteral l1, @NonNull OWLLiteral l2)
  {
    int diff = l1.getDatatype().compareTo(l2.getDatatype());
    if (diff != 0)
      return diff;

    diff = l1.getLang().compareTo(l2.getLang());
    if (diff != 0)
      return diff;

    diff = compareOWLLiterals(l1, l2);

    return diff;
  }

  private int compareOWLLiterals(@NonNull OWLLiteral l1, @NonNull OWLLiteral l2)
  {
    try {
      if (allNumeric(l1, l2)) {
        if (identicalTypes(l1, l2)) {
          if (l1.getDatatype().getIRI().equals(XSDVocabulary.BYTE.getIRI())) {
            Byte b1 = Byte.parseByte(l1.getLiteral());
            Byte b2 = Byte.parseByte(l2.getLiteral());
            return b1.compareTo(b2);
          } else if (l1.getDatatype().getIRI().equals(XSDVocabulary.SHORT.getIRI())) {
            Short s1 = Short.parseShort(l1.getLiteral());
            Short s2 = Short.parseShort(l2.getLiteral());
            return s1.compareTo(s2);
          } else if (l1.getDatatype().getIRI().equals(XSDVocabulary.INT.getIRI())) {
            Integer i1 = Integer.parseInt(l1.getLiteral());
            Integer i2 = Integer.parseInt(l2.getLiteral());
            return i1.compareTo(i2);
          } else if (l1.getDatatype().getIRI().equals(XSDVocabulary.LONG.getIRI())) {
            Long long1 = Long.parseLong(l1.getLiteral());
            Long long2 = Long.parseLong(l2.getLiteral());
            return long1.compareTo(long2);
          } else if (l1.getDatatype().isFloat()) {
            Float f1 = Float.parseFloat(l1.getLiteral());
            Float f2 = Float.parseFloat(l2.getLiteral());
            return f1.compareTo(f2);
          } else { // Double
            Double d1 = Double.parseDouble(l1.getLiteral());
            Double d2 = Double.parseDouble(l2.getLiteral());
            return d1.compareTo(d2);
          }
        } else { // Types differ - use double for comparison
          Double d1 = Double.parseDouble(l1.getLiteral());
          Double d2 = Double.parseDouble(l2.getLiteral());
          return d1.compareTo(d2);
        }
      } else { // Non numeric type; types will be the same
        if (!identicalTypes(l1, l2))
          return -1;
        else {
          if (l1.getDatatype().isBoolean()) {
            Boolean b1 = Boolean.parseBoolean(l1.getLiteral());
            Boolean b2 = Boolean.parseBoolean(l2.getLiteral());
            return b1.compareTo(b2);
          } else if (l1.getDatatype().getIRI().equals(XSDVocabulary.ANY_URI.getIRI())) {
            URI u1 = URI.create(l1.getLiteral());
            URI u2 = URI.create(l2.getLiteral());
            return u1.compareTo(u2);
          } else if (l1.getDatatype().getIRI().equals(XSDVocabulary.TIME.getIRI())) {
            XSDTime t1 = new XSDTime(l1.getLiteral());
            XSDTime t2 = new XSDTime(l2.getLiteral());
            return t1.compareTo(t2);
          } else if (l1.getDatatype().getIRI().equals(XSDVocabulary.DATE.getIRI())) {
            XSDDate d1 = new XSDDate(l1.getLiteral());
            XSDDate d2 = new XSDDate(l2.getLiteral());
            return d1.compareTo(d2);
          } else if (l1.getDatatype().getIRI().equals(XSDVocabulary.DATE_TIME.getIRI())) {
            XSDDateTime dt1 = new XSDDateTime(l1.getLiteral());
            XSDDateTime dt2 = new XSDDateTime(l2.getLiteral());
            return dt1.compareTo(dt2);
          } else if (l1.getDatatype().getIRI().equals(XSDVocabulary.DURATION.getIRI())) {
            XSDDuration d1 = new XSDDuration(l1.getLiteral());
            XSDDuration d2 = new XSDDuration(l2.getLiteral());
            return d1.compareTo(d2);
          } else
            // The OWLAPI seems to do a rather odd comparison so we use a natural order comparison
            return naturalOrderComparator.compare(l1.getLiteral(), l2.getLiteral());
        }
      }
    } catch (IllegalArgumentException e) {
      throw new SWRLAPIException("Literal " + l1.getLiteral() + " or " + l2.getLiteral() + " not valid "
          + l1.getDatatype().getIRI());
    }
  }

  private boolean identicalTypes(@NonNull OWLLiteral literal1, @NonNull OWLLiteral literal2)
  {
    return literal1.getDatatype().getIRI().equals(literal2.getDatatype().getIRI());
  }

  private boolean allNumeric(@NonNull OWLLiteral... literals)
  {
    for (OWLLiteral literal : literals)
      if (!isNumeric(literal))
        return false;
    return true;
  }

  private boolean isNumeric(@NonNull OWLLiteral literal)
  {
    return literal.getDatatype().getIRI().equals(XSDVocabulary.BYTE.getIRI())
        || literal.getDatatype().getIRI().equals(XSDVocabulary.SHORT.getIRI())
        || literal.getDatatype().getIRI().equals(XSDVocabulary.INT.getIRI())
        || literal.getDatatype().getIRI().equals(XSDVocabulary.LONG.getIRI())
        || literal.getDatatype().getIRI().equals(XSDVocabulary.FLOAT.getIRI())
        || literal.getDatatype().getIRI().equals(XSDVocabulary.DOUBLE.getIRI());
  }
}
