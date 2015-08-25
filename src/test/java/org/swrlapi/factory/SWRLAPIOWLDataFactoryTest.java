package org.swrlapi.factory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.IRIResolver;

/**
 * @see SWRLAPIOWLDataFactory
 */
public class SWRLAPIOWLDataFactoryTest
{
	private SWRLAPIOWLDataFactory factory;

	@Before public void setUp() throws OWLOntologyCreationException
	{
    DefaultPrefixManager prefixManager = new DefaultPrefixManager();
    IRIResolver iriResolver = SWRLAPIFactory.createIRIResolver(prefixManager);
		this.factory = SWRLAPIFactory.createSWRLAPIOWLDataFactory(iriResolver);
	}

	@Test public void testGetSWRLBuiltInArgumentFactory() throws Exception
	{
		SWRLBuiltInArgumentFactory builtInArgumentFactory = factory.getSWRLBuiltInArgumentFactory();
		Assert.assertNotNull(builtInArgumentFactory);
	}
}