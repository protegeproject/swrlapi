package org.swrlapi.core;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;

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