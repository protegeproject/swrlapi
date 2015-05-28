package org.swrlapi.core;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;

import java.net.URI;

/**
 * A basic OWL literal validator that deals with a set of core datatypes
 */
public class OWLLiteralValidator
{
  public static boolean isValid(@NonNull String literal, @NonNull OWLDatatype datatype)
  {
    try {
      if (datatype.getIRI().equals(XSDVocabulary.BYTE.getIRI())) {
        Byte.parseByte(literal);
        return true;
      } else if (datatype.getIRI().equals(XSDVocabulary.SHORT.getIRI())) {
        Short.parseShort(literal);
        return true;
      } else if (datatype.getIRI().equals(XSDVocabulary.INT.getIRI())) {
        Integer.parseInt(literal);
        return true;
      } else if (datatype.getIRI().equals(XSDVocabulary.LONG.getIRI())) {
        Long.parseLong(literal);
        return true;
      } else if (datatype.getIRI().equals(XSDVocabulary.FLOAT.getIRI())) {
        Float.parseFloat(literal);
        return true;
      } else if (datatype.getIRI().equals(XSDVocabulary.DOUBLE.getIRI())) {
        Double.parseDouble(literal);
        return true;
      } else if (datatype.getIRI().equals(XSDVocabulary.BOOLEAN.getIRI())) {
        Boolean.parseBoolean(literal);
        return true;
      } else if (datatype.getIRI().equals(XSDVocabulary.ANY_URI.getIRI())) {
        URI.create(literal);
        return true;
      } else if (datatype.getIRI().equals(XSDVocabulary.TIME.getIRI())) {
        new XSDTime(literal);
        return true;
      } else if (datatype.getIRI().equals(XSDVocabulary.DATE_TIME.getIRI())) {
        new XSDDateTime(literal);
        return true;
      } else if (datatype.getIRI().equals(XSDVocabulary.DATE.getIRI())) {
        new XSDDate(literal);
        return true;
      } else if (datatype.getIRI().equals(XSDVocabulary.DURATION.getIRI())) {
        new XSDDuration(literal);
        return true;
      } else
        // We do not validate types we do not know about
        return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
