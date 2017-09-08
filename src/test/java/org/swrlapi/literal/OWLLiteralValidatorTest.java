package org.swrlapi.literal;

import org.junit.Test;
import org.swrlapi.factory.OWLDatatypeFactory;
import org.swrlapi.factory.SWRLAPIInternalFactory;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * @see OWLLiteralValidator
 */
public class OWLLiteralValidatorTest
{
  private final OWLDatatypeFactory datatypeFactory = SWRLAPIInternalFactory.createOWLDatatypeFactory();

  @Test public void testIsValidBoolean() throws Exception
  {
    assertTrue(OWLLiteralValidator.isValid("true", this.datatypeFactory.getBooleanDatatype()));
  }

  @Test public void testIsInvalidBoolean() throws Exception
  {
    assertFalse(OWLLiteralValidator.isValid("g", this.datatypeFactory.getBooleanDatatype()));
  }

  @Test public void testIsValidInteger() throws Exception
  {
    assertTrue(OWLLiteralValidator.isValid("34", this.datatypeFactory.getIntegerDatatype()));
  }

  @Test public void testIsInvalidInteger() throws Exception
  {
    assertFalse(OWLLiteralValidator.isValid("g", this.datatypeFactory.getIntegerDatatype()));
  }

  @Test public void testIsValidDecimal() throws Exception
  {
    assertTrue(OWLLiteralValidator.isValid("34.9", this.datatypeFactory.getDecimalDatatype()));
  }

  @Test public void testIsInvalidDecimal() throws Exception
  {
    assertFalse(OWLLiteralValidator.isValid("g", this.datatypeFactory.getDecimalDatatype()));
  }

  @Test public void testIsValidByte() throws Exception
  {
    assertTrue(OWLLiteralValidator.isValid("34", this.datatypeFactory.getByteDatatype()));
  }

  @Test public void testIsInvalidByte() throws Exception
  {
    assertFalse(OWLLiteralValidator.isValid("g", this.datatypeFactory.getByteDatatype()));
  }

  @Test public void testIsValidShort() throws Exception
  {
    assertTrue(OWLLiteralValidator.isValid("34", this.datatypeFactory.getShortDatatype()));
  }

  @Test public void testIsInvalidShort() throws Exception
  {
    assertFalse(OWLLiteralValidator.isValid("g", this.datatypeFactory.getShortDatatype()));
  }

  @Test public void testIsValidInt() throws Exception
  {
    assertTrue(OWLLiteralValidator.isValid("34", this.datatypeFactory.getIntDatatype()));
  }

  @Test public void testIsInvalidInt() throws Exception
  {
    assertFalse(OWLLiteralValidator.isValid("g", this.datatypeFactory.getIntDatatype()));
  }

  @Test public void testIsValidLong() throws Exception
  {
    assertTrue(OWLLiteralValidator.isValid("34", this.datatypeFactory.getLongDatatype()));
  }

  @Test public void testIsInvalidLong() throws Exception
  {
    assertFalse(OWLLiteralValidator.isValid("g", this.datatypeFactory.getLongDatatype()));
  }

  @Test public void testIsValidFloat() throws Exception
  {
    assertTrue(OWLLiteralValidator.isValid("34.3", this.datatypeFactory.getFloatDatatype()));
  }

  @Test public void testIsInvalidFloat() throws Exception
  {
    assertFalse(OWLLiteralValidator.isValid("g", this.datatypeFactory.getFloatDatatype()));
  }

  @Test public void testIsValidDouble() throws Exception
  {
    assertTrue(OWLLiteralValidator.isValid("34.3", this.datatypeFactory.getDoubleDatatype()));
  }

  @Test public void testIsInvalidDouble() throws Exception
  {
    assertFalse(OWLLiteralValidator.isValid("g", this.datatypeFactory.getDoubleDatatype()));
  }

  @Test public void testIsValidDate() throws Exception
  {
    assertTrue(OWLLiteralValidator.isValid("1999-04-03", this.datatypeFactory.getDateDatatype()));
  }

  @Test public void testIsInvalidDate() throws Exception
  {
    assertFalse(OWLLiteralValidator.isValid("g", this.datatypeFactory.getDateDatatype()));
  }

  @Test public void testIsValidDateTime() throws Exception
  {
    assertTrue(OWLLiteralValidator.isValid("1999-04-03T12:12:12.43", this.datatypeFactory.getDateTimeDatatype()));
  }

  @Test public void testIsInvalidDateTime() throws Exception
  {
    assertFalse(OWLLiteralValidator.isValid("g", this.datatypeFactory.getDateTimeDatatype()));
  }

  @Test public void testIsValidTime() throws Exception
  {
    assertTrue(OWLLiteralValidator.isValid("12:12:12.431", this.datatypeFactory.getTimeDatatype()));
  }

  @Test public void testIsInvalidTime() throws Exception
  {
    assertFalse(OWLLiteralValidator.isValid("12:0:10.332", this.datatypeFactory.getTimeDatatype()));
  }

  @Test public void testIsValidDuration() throws Exception
  {
    assertTrue(OWLLiteralValidator.isValid("P43Y", this.datatypeFactory.getDurationDatatype()));
  }

  @Test public void testIsInvalidDuration() throws Exception
  {
    assertFalse(OWLLiteralValidator.isValid("43Y", this.datatypeFactory.getDurationDatatype()));
  }

}