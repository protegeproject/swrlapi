package org.swrlapi.sqwrl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.factory.SWRLAPIInternalFactory;
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
  private SQWRLResultManager resultManager;

  private static final String TestPrefix = "test:";
  private static final String TestNamespace = "http://example.org#";

  private static final String columnName = "c";

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Before public void setUp() throws OWLOntologyCreationException
  {
    IRIResolver iriResolver = SWRLAPIFactory.createIRIResolver();

    iriResolver.setPrefix(TestPrefix, TestNamespace);

    resultManager = SWRLAPIInternalFactory.createSQWRLResultManager(iriResolver);
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
    assertEquals(0, resultManager.getNumberOfColumns());
    assertEquals(0, resultManager.getNumberOfRows());
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
