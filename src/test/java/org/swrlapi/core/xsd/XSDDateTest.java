package org.swrlapi.core.xsd;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * @see XSDDate
 */
public class XSDDateTest
{
	@Test (expected = IllegalArgumentException.class)
	public void testConstruction()
	{
		XSDDate date = new XSDDate("X");
	}

	@Test
	public void testValidate() throws Exception
	{
		XSDDate date = new XSDDate("1999-01-02");
		date.validate();
	}
}