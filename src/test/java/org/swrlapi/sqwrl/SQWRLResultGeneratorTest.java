package org.swrlapi.sqwrl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.factory.SQWRLResultValueFactory;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.resolvers.IRIResolver;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @see SQWRLResultGenerator
 * @see SQWRLResultManager
 */
public class SQWRLResultGeneratorTest
{
	private OWLOntologyManager ontologyManager;
	private OWLOntology ontology;
	private DefaultPrefixManager prefixManager;
	private IRIResolver iriResolver;
	private SQWRLResultValueFactory valueFactory;
	private SQWRLResultManager resultManager;

	private static final String TestPrefix = "test:";
	private static final String TestNamespace = "http://example.org#";

	private static final String columnName = "c";

	@Rule public ExpectedException thrown = ExpectedException.none();

	@Before public void setUp() throws OWLOntologyCreationException
	{
		ontologyManager = OWLManager.createOWLOntologyManager();
		ontology = ontologyManager.createOntology();
		prefixManager = new DefaultPrefixManager();
		iriResolver = SWRLAPIFactory.createIRIResolver(prefixManager);
		valueFactory = SWRLAPIFactory.createSQWRLResultValueFactory(iriResolver);
		resultManager = SWRLAPIFactory.createSQWRLResultManager(iriResolver);

		prefixManager.setPrefix(TestPrefix, TestNamespace);
	}

	@Test public void testAddColumns() throws Exception
	{
		List<String> columnNames = Stream.of("a", "b").collect(Collectors.toCollection(ArrayList::new));
		resultManager.addColumns(columnNames);
		resultManager.configured();

		assertEquals(columnNames, resultManager.getColumnNames());
	}

	@Test public void testAddColumn() throws Exception
	{
		resultManager.addColumn(columnName);
		resultManager.configured();

		assertEquals(columnName, resultManager.getColumnName(0));
	}

	@Test public void testGetColumnNamesPreConfiguration() throws Exception
	{
		thrown.expect(SQWRLException.class);
		thrown.expectMessage("attempt to do post-configuration operations before configuration");

		resultManager.getColumnNames();
	}

	@Test public void testGetColumnNamePreConfiguration() throws Exception
	{
		thrown.expect(SQWRLException.class);
		thrown.expectMessage("attempt to do post-configuration operations before configuration");

		resultManager.getColumnName(0);
	}

	@Test public void testEmptyResult() throws Exception
	{
		resultManager.configured();
		resultManager.prepared();

		assertTrue(resultManager.isConfigured());
		assertTrue(resultManager.isPrepared());
		assertEquals(resultManager.getNumberOfColumns(), 0);
		assertEquals(resultManager.getNumberOfRows(), 0);
	}

	@Test public void testPreparedWithoutConfigured() throws Exception
	{
		thrown.expect(SQWRLException.class);
		thrown.expectMessage("attempt to do post-configuration operations before configuration");

		resultManager.prepared();
	}

	@Test public void testDoubleConfigured() throws Exception
	{
		thrown.expect(SQWRLException.class);
		thrown.expectMessage("attempt to configure already configured result");

		resultManager.configured();
		resultManager.configured();
	}

	@Test public void testDoublePrepared() throws Exception
	{
		thrown.expect(SQWRLException.class);
		thrown.expectMessage("attempt to modify prepared result");

		resultManager.configured();
		resultManager.prepared();
		resultManager.prepared();
	}

	@Test public void testInvalidOpenRow() throws Exception
	{
		thrown.expect(SQWRLException.class);
		thrown.expectMessage("attempt to do post-configuration operations before configuration");

		resultManager.openRow();
	}

	@Test public void testIsRowOpen() throws Exception
	{
		resultManager.configured();
		resultManager.prepared();

		assertFalse(resultManager.isRowOpen());
	}
}