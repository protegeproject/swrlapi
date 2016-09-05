package org.swrlapi.literal;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.factory.OWLDatatypeFactory;
import org.swrlapi.factory.OWLLiteralFactory;
import org.swrlapi.factory.SWRLAPIInternalFactory;

public class OWLLiteralComparatorTest
{
  private static final OWLLiteralFactory literalFactory = SWRLAPIInternalFactory.createOWLLiteralFactory();
  private static final OWLDatatypeFactory datatypeFactory = SWRLAPIInternalFactory.createOWLDatatypeFactory();

  @Test public void testXSDIntegerCompare() throws Exception
  {
    OWLLiteral l1 = literalFactory.getOWLLiteral("3", datatypeFactory.getIntegerDatatype());
    OWLLiteral l2 = literalFactory.getOWLLiteral("4", datatypeFactory.getIntegerDatatype());

    int result = OWLLiteralComparator.COMPARATOR.compare(l1, l2);

    Assert.assertTrue(result < 0);
  }

  @Test public void testIsByteNumeric() throws Exception
  {
    OWLLiteral l = literalFactory.getOWLLiteral("3", datatypeFactory.getByteDatatype());

    Assert.assertTrue(OWLLiteralComparator.isNumeric(l));
  }

  @Test public void testIsShortNumeric() throws Exception
  {
    OWLLiteral l = literalFactory.getOWLLiteral("3", datatypeFactory.getShortDatatype());

    Assert.assertTrue(OWLLiteralComparator.isNumeric(l));
  }

  @Test public void testIsIntNumeric() throws Exception
  {
    OWLLiteral l = literalFactory.getOWLLiteral("3", datatypeFactory.getIntDatatype());

    Assert.assertTrue(OWLLiteralComparator.isNumeric(l));
  }

  @Test public void testIsLongNumeric() throws Exception
  {
    OWLLiteral l = literalFactory.getOWLLiteral("3", datatypeFactory.getLongDatatype());

    Assert.assertTrue(OWLLiteralComparator.isNumeric(l));
  }

  @Test public void testIsFloatNumeric() throws Exception
  {
    OWLLiteral l = literalFactory.getOWLLiteral("3.0", datatypeFactory.getFloatDatatype());

    Assert.assertTrue(OWLLiteralComparator.isNumeric(l));
  }

  @Test public void testIsDoubleNumeric() throws Exception
  {
    OWLLiteral l = literalFactory.getOWLLiteral("3.0", datatypeFactory.getDoubleDatatype());

    Assert.assertTrue(OWLLiteralComparator.isNumeric(l));
  }

  @Test public void testIsDecimalNumeric() throws Exception
  {
    OWLLiteral l = literalFactory.getOWLLiteral("3", datatypeFactory.getDecimalDatatype());

    Assert.assertTrue(OWLLiteralComparator.isNumeric(l));
  }

  @Test public void testIsStringNotNumeric() throws Exception
  {
    OWLLiteral l = literalFactory.getOWLLiteral("a string");

    Assert.assertFalse(OWLLiteralComparator.isNumeric(l));
  }

}