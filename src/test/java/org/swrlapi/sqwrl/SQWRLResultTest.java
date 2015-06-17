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
import org.swrlapi.core.IRIResolver;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;
import org.swrlapi.sqwrl.values.SQWRLIndividualResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @see SQWRLResult
 * @see SQWRLResultManager
 */
public class SQWRLResultTest
{
  private OWLOntologyManager ontologyManager;
	private OWLOntology ontology;
	private DefaultPrefixManager prefixManager;
	private IRIResolver iriResolver;
	private SQWRLResultManager resultManager;
  private SQWRLResultValueFactory valueFactory;

  private static final String TestPrefix = "test:";
  private static final String TestNamespace = "http://example.org#";

  private static final IRI c1IRI = IRI.create(TestNamespace + "c1");
  private static final IRI i1IRI = IRI.create(TestNamespace + "i1");
  private static final IRI p1IRI = IRI.create(TestNamespace + "p1");
  private static final String c1PrefixedName = TestPrefix + "c1";
  private static final String i1PrefixedName = TestPrefix + "i1";
  private static final String p1PrefixedName = TestPrefix + "p1";
  private static final String columnName = "c";
	private static final String column1Name = "c1";

	@Rule public ExpectedException thrown = ExpectedException.none();

	@Before public void setUp() throws OWLOntologyCreationException
	{
		ontologyManager = OWLManager.createOWLOntologyManager();
		ontology = ontologyManager.createOntology();
		prefixManager = new DefaultPrefixManager();
		iriResolver = SWRLAPIFactory.createIRIResolver(prefixManager);
		resultManager = SWRLAPIFactory.createSQWRLResultManager(prefixManager);

    valueFactory = SWRLAPIFactory.createSQWRLResultValueFactory(iriResolver);

    prefixManager.setPrefix(TestPrefix, TestNamespace);
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

	@Test public void testGetNumberOfColumns() throws Exception
	{
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

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(2));
    resultManager.closeRow();

		resultManager.prepared();

    assertEquals(resultManager.getNumberOfColumns(), 1);
		assertEquals(resultManager.getNumberOfRows(), 2);
	}

  @Test public void testGetClass() throws Exception
  {
    resultManager.addColumn(columnName);

    resultManager.configured();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getClassValue(c1IRI));
    resultManager.closeRow();

    resultManager.prepared();

    assertEquals(resultManager.getNumberOfColumns(), 1);
    assertEquals(resultManager.getNumberOfRows(), 1);

    resultManager.next();

    assertTrue(resultManager.hasClassValue(0));
    assertTrue(resultManager.hasClassValue(columnName));

    SQWRLClassResultValue value = resultManager.getClass(columnName);
    assertEquals(value.getPrefixedName(), c1PrefixedName);
  }

  @Test public void testGetIndividual() throws Exception
  {
    resultManager.addColumn(columnName);

    resultManager.configured();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getIndividualValue(i1IRI));
    resultManager.closeRow();

    resultManager.prepared();

    assertEquals(resultManager.getNumberOfColumns(), 1);
    assertEquals(resultManager.getNumberOfRows(), 1);

    resultManager.next();

    assertTrue(resultManager.hasIndividualValue(0));
    assertTrue(resultManager.hasIndividualValue(columnName));

    SQWRLIndividualResultValue value = resultManager.getIndividual(columnName);
    assertEquals(value.getPrefixedName(), i1PrefixedName);
  }

  @Test public void testGetObjectProperty() throws Exception
  {
    resultManager.addColumn(columnName);

    resultManager.configured();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getObjectPropertyValue(p1IRI));
    resultManager.closeRow();

    resultManager.prepared();

    assertEquals(resultManager.getNumberOfColumns(), 1);
    assertEquals(resultManager.getNumberOfRows(), 1);

    resultManager.next();

    assertTrue(resultManager.hasObjectPropertyValue(0));
    assertTrue(resultManager.hasObjectPropertyValue(columnName));

    SQWRLObjectPropertyResultValue value = resultManager.getObjectProperty(columnName);
    assertEquals(value.getPrefixedName(), p1PrefixedName);
  }

  @Test public void testGetDataProperty() throws Exception
  {
    resultManager.addColumn(columnName);

    resultManager.configured();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getDataPropertyValue(p1IRI));
    resultManager.closeRow();

    resultManager.prepared();

    assertEquals(resultManager.getNumberOfColumns(), 1);
    assertEquals(resultManager.getNumberOfRows(), 1);

    resultManager.next();

    assertTrue(resultManager.hasDataPropertyValue(0));
    assertTrue(resultManager.hasDataPropertyValue(columnName));

    SQWRLDataPropertyResultValue value = resultManager.getDataProperty(columnName);
    assertEquals(value.getPrefixedName(), p1PrefixedName);
  }

	@Test public void testGetAnnotationProperty() throws Exception
	{
		resultManager.addColumn(columnName);

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getAnnotationPropertyValue(p1IRI));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 1);
		assertEquals(resultManager.getNumberOfRows(), 1);

		resultManager.next();

		assertTrue(resultManager.hasAnnotationPropertyValue(0));
		assertTrue(resultManager.hasAnnotationPropertyValue(columnName));

		SQWRLAnnotationPropertyResultValue value = resultManager.getAnnotationProperty(columnName);
		assertEquals(value.getPrefixedName(), p1PrefixedName);
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

    assertTrue(resultManager.hasLiteralValue(0));
    assertTrue(resultManager.hasLiteralValue(columnName));

    SQWRLLiteralResultValue value = resultManager.getLiteral(columnName);

		assertTrue(value.isInt());
		assertEquals(value.getInt(), 27);
	}

	@Test public void testAvgAggregateFunction() throws Exception
	{
		resultManager.addAggregateColumn(column1Name, SQWRLResultNames.AvgAggregateFunction);

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(20));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(30));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 1);
		assertEquals(resultManager.getNumberOfRows(), 1);

		resultManager.next();

		SQWRLLiteralResultValue value = resultManager.getLiteral(column1Name);
		assertTrue(value.isInt());
		assertEquals(value.getInt(), 25);
	}

	@Test public void testMinAggregateFunction() throws Exception
	{
		resultManager.addAggregateColumn(column1Name, SQWRLResultNames.MinAggregateFunction);

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(20));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(30));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 1);
		assertEquals(resultManager.getNumberOfRows(), 1);

		resultManager.next();

		SQWRLLiteralResultValue value = resultManager.getLiteral(column1Name);
		assertTrue(value.isInt());
		assertEquals(value.getInt(), 20);
	}

	@Test public void testMaxAggregateFunction() throws Exception
	{
		resultManager.addAggregateColumn(column1Name, SQWRLResultNames.MaxAggregateFunction);

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(20));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(30));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 1);
		assertEquals(resultManager.getNumberOfRows(), 1);

		resultManager.next();

		SQWRLLiteralResultValue value = resultManager.getLiteral(column1Name);
		assertTrue(value.isInt());
		assertEquals(value.getInt(), 30);
	}

  @Test public void testSumAggregateFunction() throws Exception
  {
    resultManager.addAggregateColumn(column1Name, SQWRLResultNames.SumAggregateFunction);

    resultManager.configured();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(20));
    resultManager.closeRow();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(30));
    resultManager.closeRow();

    resultManager.prepared();

    assertEquals(resultManager.getNumberOfColumns(), 1);
    assertEquals(resultManager.getNumberOfRows(), 1);

    resultManager.next();

    SQWRLLiteralResultValue value = resultManager.getLiteral(column1Name);
    assertTrue(value.isInt());
    assertEquals(value.getInt(), 50);
  }

  @Test public void testMedianAggregateFunction() throws Exception
  {
    resultManager.addAggregateColumn(column1Name, SQWRLResultNames.MedianAggregateFunction);

    resultManager.configured();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(20));
    resultManager.closeRow();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(40));
    resultManager.closeRow();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(30));
    resultManager.closeRow();

    resultManager.prepared();

    assertEquals(resultManager.getNumberOfColumns(), 1);
    assertEquals(resultManager.getNumberOfRows(), 1);

    resultManager.next();

    SQWRLLiteralResultValue value = resultManager.getLiteral(column1Name);
    assertTrue(value.isInt());
    assertEquals(value.getInt(), 30);
  }

  @Test public void testCountAggregateFunction() throws Exception
  {
    resultManager.addAggregateColumn(column1Name, SQWRLResultNames.CountAggregateFunction);

    resultManager.configured();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(20));
    resultManager.closeRow();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(30));
    resultManager.closeRow();

    resultManager.prepared();

    assertEquals(resultManager.getNumberOfColumns(), 1);
    assertEquals(resultManager.getNumberOfRows(), 1);

    resultManager.next();

    SQWRLLiteralResultValue value = resultManager.getLiteral(column1Name);
    assertTrue(value.isInt());
    assertEquals(value.getInt(), 2);
  }

  @Test public void testCountDistinctAggregateFunction() throws Exception
  {
    resultManager.addAggregateColumn(column1Name, SQWRLResultNames.CountDistinctAggregateFunction);

    resultManager.configured();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(20));
    resultManager.closeRow();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(20));
    resultManager.closeRow();

    resultManager.prepared();

    assertEquals(resultManager.getNumberOfColumns(), 1);
    assertEquals(resultManager.getNumberOfRows(), 1);

    resultManager.next();

    SQWRLLiteralResultValue value = resultManager.getLiteral(column1Name);
    assertTrue(value.isInt());
    assertEquals(value.getInt(), 1);
  }

  @Test public void testSetIsDistinct() throws Exception
  {
    resultManager.addColumn(columnName);
    resultManager.setIsDistinct();
    resultManager.configured();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(20));
    resultManager.closeRow();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(20));
    resultManager.closeRow();

    resultManager.prepared();

    assertEquals(resultManager.getNumberOfColumns(), 1);
    assertEquals(resultManager.getNumberOfRows(), 1);
  }

  @Test public void testSetOrderByColumnAscending() throws Exception
  {
    resultManager.addColumn(columnName);
    resultManager.setOrderByColumn(0, true);

    resultManager.configured();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(30));
    resultManager.closeRow();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(20));
    resultManager.closeRow();

    resultManager.prepared();

    assertEquals(resultManager.getNumberOfColumns(), 1);
    assertEquals(resultManager.getNumberOfRows(), 2);

    assertTrue(resultManager.isOrdered());
    assertTrue(resultManager.isOrderedAscending());

    resultManager.next();

    SQWRLLiteralResultValue literal1Value = resultManager.getLiteral(0);
    assertTrue(literal1Value.isInt());
    assertEquals(literal1Value.getInt(), 20);

    resultManager.next();

    SQWRLLiteralResultValue literal2Value = resultManager.getLiteral(0);
    assertTrue(literal2Value.isInt());
    assertEquals(literal2Value.getInt(), 30);
  }

  @Test public void testSetOrderByColumnDescending() throws Exception
  {
    resultManager.addColumn(columnName);
    resultManager.setOrderByColumn(0, false);

    resultManager.configured();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(30));
    resultManager.closeRow();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(20));
    resultManager.closeRow();

    resultManager.prepared();

    assertEquals(resultManager.getNumberOfColumns(), 1);
    assertEquals(resultManager.getNumberOfRows(), 2);

    assertTrue(resultManager.isOrdered());
    assertFalse(resultManager.isOrderedAscending());

    resultManager.next();

    SQWRLLiteralResultValue literal1Value = resultManager.getLiteral(0);
    assertTrue(literal1Value.isInt());
    assertEquals(literal1Value.getInt(), 30);

    resultManager.next();

    SQWRLLiteralResultValue literal2Value = resultManager.getLiteral(0);
    assertTrue(literal2Value.isInt());
    assertEquals(literal2Value.getInt(), 20);
  }

  @Test public void testSetLimit() throws Exception
  {
    resultManager.addColumn(columnName);

    resultManager.setLimit(2);

    resultManager.configured();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(20));
    resultManager.closeRow();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(40));
    resultManager.closeRow();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(30));
    resultManager.closeRow();

    resultManager.prepared();

    assertEquals(resultManager.getNumberOfColumns(), 1);
    assertEquals(resultManager.getNumberOfRows(), 2);
  }

	@Test public void testSetNth() throws Exception
  {
    resultManager.addColumn(columnName);

    resultManager.setNth(2);

    resultManager.configured();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(20));
    resultManager.closeRow();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(40));
    resultManager.closeRow();

    resultManager.openRow();
    resultManager.addCell(valueFactory.getLiteralValue(30));
    resultManager.closeRow();

    resultManager.prepared();

    assertEquals(resultManager.getNumberOfColumns(), 1);
    assertEquals(resultManager.getNumberOfRows(), 1);

    resultManager.next();

    SQWRLLiteralResultValue value = resultManager.getLiteral(0);
    assertTrue(value.isInt());
    assertEquals(value.getInt(), 40);
  }

	@Test public void testSetNotNth() throws Exception
	{
		resultManager.addColumn(columnName);

		resultManager.setNotNth(2);

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(20));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(40));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(30));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 1);
		assertEquals(resultManager.getNumberOfRows(), 2);

		resultManager.next();

		assertTrue(resultManager.getLiteral(0).isInt());
		assertEquals(resultManager.getLiteral(0).getInt(), 20);

		resultManager.next();

		assertTrue(resultManager.getLiteral(0).isInt());
		assertEquals(resultManager.getLiteral(0).getInt(), 30);
	}

	@Test public void testSetFirst() throws Exception
	{
		resultManager.addColumn(columnName);

		resultManager.setFirst();

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(20));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(40));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(30));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 1);
		assertEquals(resultManager.getNumberOfRows(), 1);

		resultManager.next();

		assertTrue(resultManager.getLiteral(0).isInt());
		assertEquals(resultManager.getLiteral(0).getInt(), 20);
	}

	@Test public void testSetFirstN() throws Exception
	{
		resultManager.addColumn(columnName);

		resultManager.setFirst(2);

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(20));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(40));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(30));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 1);
		assertEquals(resultManager.getNumberOfRows(), 2);

		resultManager.next();

		assertTrue(resultManager.getLiteral(0).isInt());
		assertEquals(resultManager.getLiteral(0).getInt(), 20);

		resultManager.next();

		assertTrue(resultManager.getLiteral(0).isInt());
		assertEquals(resultManager.getLiteral(0).getInt(), 40);
	}

	@Test public void testSetLastN() throws Exception
	{
		resultManager.addColumn(columnName);

		resultManager.setLast(2);

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(20));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(40));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(30));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 1);
		assertEquals(resultManager.getNumberOfRows(), 2);

		resultManager.next();

		assertTrue(resultManager.getLiteral(0).isInt());
		assertEquals(resultManager.getLiteral(0).getInt(), 40);

		resultManager.next();

		assertTrue(resultManager.getLiteral(0).isInt());
		assertEquals(resultManager.getLiteral(0).getInt(), 30);
	}

	@Test public void testSetLast() throws Exception
	{
		resultManager.addColumn(columnName);

		resultManager.setLast();

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(20));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(40));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(30));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 1);
		assertEquals(resultManager.getNumberOfRows(), 1);

		resultManager.next();

		assertTrue(resultManager.getLiteral(0).isInt());
		assertEquals(resultManager.getLiteral(0).getInt(), 30);
	}
//	void setNotFirst();
//	void setNotFirst(int n);
//	void setNotLast();
//	void setNotLast(int n);

	@Test public void testNthSlice() throws Exception
	{
		resultManager.addColumn(columnName);

		resultManager.setNthSlice(2, 2);

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(20));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(40));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(30));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 1);
		assertEquals(resultManager.getNumberOfRows(), 2);

		resultManager.next();

		assertTrue(resultManager.getLiteral(0).isInt());
		assertEquals(resultManager.getLiteral(0).getInt(), 40);

		resultManager.next();

		assertTrue(resultManager.getLiteral(0).isInt());
		assertEquals(resultManager.getLiteral(0).getInt(), 30);
	}

	@Test public void testNotNthSlice() throws Exception
	{
		resultManager.addColumn(columnName);

		resultManager.setNotNthSlice(2, 2);

		resultManager.configured();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(20));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(40));
		resultManager.closeRow();

		resultManager.openRow();
		resultManager.addCell(valueFactory.getLiteralValue(30));
		resultManager.closeRow();

		resultManager.prepared();

		assertEquals(resultManager.getNumberOfColumns(), 1);
		assertEquals(resultManager.getNumberOfRows(), 1);

		resultManager.next();

		assertTrue(resultManager.getLiteral(0).isInt());
		assertEquals(resultManager.getLiteral(0).getInt(), 20);
	}

//	void setNthLastSlice(int nth, int sliceSize);
//	void setNotNthLastSlice(int nth, int sliceSize);
//
}