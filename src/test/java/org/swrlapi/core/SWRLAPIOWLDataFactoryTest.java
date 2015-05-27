package org.swrlapi.core;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.factory.SWRLAPIFactory;

/**
 * @see SWRLAPIOWLDataFactory
 */
public class SWRLAPIOWLDataFactoryTest extends TestCase
{
	private SWRLAPIOWLDataFactory factory;

	@Before public void setUp() throws OWLOntologyCreationException
	{
		this.factory = SWRLAPIFactory.createSWRLAPIOWLDataFactory();
	}

	@Test public void testGetSWRLBuiltInArgumentFactory() throws Exception
	{
		SWRLBuiltInArgumentFactory builtInArgumentFactory = factory.getSWRLBuiltInArgumentFactory();
		assertNotNull(builtInArgumentFactory);;
	}
}