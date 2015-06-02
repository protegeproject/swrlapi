package org.swrlapi.sqwrl;

import junit.framework.Assert;
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
import org.swrlapi.sqwrl.values.SQWRLIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @see SQWRLResultManager
 */
public class SQWRLResultManagerTest
{
	private static final String TestPrefix = "test:";
	private static final String TestNamespace = "http://example.org#";

	private OWLOntologyManager ontologyManager;
	private OWLOntology ontology;
	private DefaultPrefixManager prefixManager;
	private IRIResolver iriResolver;
	private SQWRLResultValueFactory valueFactory;
	private SQWRLResultManager resultManager;

	private IRI i1IRI = IRI.create(TestNamespace + "i1");
	private String columnName = "c";
	private String column1Name = "c1";
	private String column2Name = "c2";

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

	@Test public void testGetNumberOfColumns() throws Exception
	{
		String columnName = "c1";

		resultManager.addColumn(columnName);

		resultManager.configured();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 1);

	}

	@Test public void testGetNumberOfRows() throws Exception
	{
		resultManager.addColumn(columnName);

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(27));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 1);
		assertEquals(resultManager.getNumberOfRows(), 1);
	}

	@Test public void testGetLiteral() throws Exception
	{
		resultManager.addColumn(columnName);

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(27));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 1);
		assertEquals(resultManager.getNumberOfRows(), 1);

		resultManager.next();

		SQWRLLiteralResultValue cellValue = resultManager.getLiteral(columnName);

		assertTrue(cellValue.isInt());
		assertEquals(cellValue.getInt(), 27);
	}

	@Test public void testAvgAggregateFunction() throws Exception
	{
		resultManager.addColumn(column1Name);
		resultManager.addAggregateColumn(column2Name, SQWRLResultNames.AvgAggregateFunction);

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getIndividualValue(i1IRI));
		resultManager.addCell(valueFactory.getLiteralValue(20));
		resultManager.closeRow();
		resultManager.openRow();
		resultManager.addCell(valueFactory.getIndividualValue(i1IRI));
		resultManager.addCell(valueFactory.getLiteralValue(30));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 2);
		assertEquals(resultManager.getNumberOfRows(), 1);

		resultManager.next();

		SQWRLLiteralResultValue literalValue = resultManager.getLiteral(column2Name);
		assertTrue(literalValue.isInt());
		assertEquals(literalValue.getInt(), 25);
	}

	@Test public void testMinAggregateFunction() throws Exception
	{
		resultManager.addColumn(column1Name);
		resultManager.addAggregateColumn(column2Name, SQWRLResultNames.MinAggregateFunction);

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getIndividualValue(i1IRI));
		resultManager.addCell(valueFactory.getLiteralValue(20));
		resultManager.closeRow();
		resultManager.openRow();
		resultManager.addCell(valueFactory.getIndividualValue(i1IRI));
		resultManager.addCell(valueFactory.getLiteralValue(30));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 2);
		assertEquals(resultManager.getNumberOfRows(), 1);

		resultManager.next();

		SQWRLLiteralResultValue literalValue = resultManager.getLiteral(column2Name);
		assertTrue(literalValue.isInt());
		assertEquals(literalValue.getInt(), 20);
	}

	@Test public void testMaxAggregateFunction() throws Exception
	{
		resultManager.addColumn(column1Name);
		resultManager.addAggregateColumn(column2Name, SQWRLResultNames.MaxAggregateFunction);

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getIndividualValue(i1IRI));
		resultManager.addCell(valueFactory.getLiteralValue(20));
		resultManager.closeRow();
		resultManager.openRow();
		resultManager.addCell(valueFactory.getIndividualValue(i1IRI));
		resultManager.addCell(valueFactory.getLiteralValue(30));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 2);
		assertEquals(resultManager.getNumberOfRows(), 1);

		resultManager.next();

		SQWRLLiteralResultValue literalValue = resultManager.getLiteral(column2Name);
		assertTrue(literalValue.isInt());
		assertEquals(literalValue.getInt(), 30);
	}
}