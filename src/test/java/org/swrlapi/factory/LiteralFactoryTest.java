package org.swrlapi.factory;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.literal.Literal;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * @see LiteralFactory
 */
public class LiteralFactoryTest
{
  private LiteralFactory literalFactory;

  @Before public void setUp() throws OWLOntologyCreationException
  {
    this.literalFactory = SWRLAPIInternalFactory.createLiteralFactory();
  }

  @Test public void testGetOWLLiteralBoolean() throws Exception
  {
    boolean b = true;

    Literal literal = this.literalFactory.getLiteral(b);

    assertTrue(literal.isBoolean());
    assertEquals(true, literal.getBoolean());
  }
}
