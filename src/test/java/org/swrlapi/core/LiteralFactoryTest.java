package org.swrlapi.core;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.factory.SWRLAPIFactory;

/**
 * @see LiteralFactory
 */
public class LiteralFactoryTest extends TestCase
{
  private LiteralFactory literalFactory;

  @Before public void setUp() throws OWLOntologyCreationException
  {
    this.literalFactory = SWRLAPIFactory.createLiteralFactory();
  }

  @Test
  public void testGetOWLLiteralBoolean() throws Exception
  {
    boolean b = true;

    Literal literal = this.literalFactory.getLiteral(b);

    assertTrue(literal.isBoolean());
    assertEquals(literal.getBoolean(), true);
  }
}