package org.swrlapi.factory;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * @see OWLLiteralFactory
 */
public class OWLLiteralFactoryTest extends TestCase
{
  private OWLLiteralFactory literalFactory;
  private OWLDatatypeFactory datatypeFactory;

  @Before public void setUp() throws OWLOntologyCreationException
  {
    this.literalFactory = SWRLAPIFactory.createOWLLiteralFactory();
    this.datatypeFactory = SWRLAPIFactory.createOWLDatatypeFactory();
  }

  @Test
  public void testGetOWLLiteralBoolean() throws Exception
  {
    boolean b = true;

    OWLLiteral literal = this.literalFactory.getOWLLiteral(b);

    assertEquals(literal.getDatatype(), this.datatypeFactory.getBooleanDatatype());
    assertTrue(literal.isBoolean());
    assertEquals(literal.getLiteral(), "true");
  }
}