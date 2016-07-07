package org.swrlapi.factory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * @see OWLLiteralFactory
 */
public class OWLLiteralFactoryTest
{
  private OWLLiteralFactory literalFactory;
  private OWLDatatypeFactory datatypeFactory;

  @Before public void setUp() throws OWLOntologyCreationException
  {
    this.literalFactory = SWRLAPIInternalFactory.createOWLLiteralFactory();
    this.datatypeFactory = SWRLAPIInternalFactory.createOWLDatatypeFactory();
  }

  @Test public void testGetOWLLiteralBoolean() throws Exception
  {
    boolean b = true;

    OWLLiteral literal = this.literalFactory.getOWLLiteral(b);

    Assert.assertEquals(literal.getDatatype(), this.datatypeFactory.getBooleanDatatype());
    Assert.assertTrue(literal.isBoolean());
    Assert.assertEquals(literal.getLiteral(), "true");
  }
}
