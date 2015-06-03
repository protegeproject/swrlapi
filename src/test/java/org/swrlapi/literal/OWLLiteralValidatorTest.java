package org.swrlapi.literal;

import org.junit.Test;
import org.swrlapi.literal.OWLLiteralValidator;
import org.swrlapi.factory.OWLDatatypeFactory;
import org.swrlapi.factory.SWRLAPIFactory;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * @see OWLLiteralValidator
 */
public class OWLLiteralValidatorTest
{
	private OWLDatatypeFactory datatypeFactory = SWRLAPIFactory.createOWLDatatypeFactory();

	@Test
	public void testIsValidByte() throws Exception
	{
		assertTrue(OWLLiteralValidator.isValid("34", this.datatypeFactory.getByteDatatype()));
	}

	@Test
	public void testIsInvalidByte() throws Exception
	{
		assertFalse(OWLLiteralValidator.isValid("g", this.datatypeFactory.getByteDatatype()));
	}
}