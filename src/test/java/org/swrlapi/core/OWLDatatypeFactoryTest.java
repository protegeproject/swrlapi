package org.swrlapi.core;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.factory.SWRLAPIFactory;

/**
 * @see OWLDatatypeFactory
 */
public class OWLDatatypeFactoryTest extends TestCase
{
  private OWLDatatypeFactory factory;

  @Before public void setUp() throws OWLOntologyCreationException
  {
    this.factory = SWRLAPIFactory.createOWLDatatypeFactory();
  }

  @Test
  public void testGetBooleanDatatype() throws Exception
  {
    OWLDatatype datatype = this.factory.getBooleanDatatype();

    assertEquals(datatype.getIRI(), XSDVocabulary.BOOLEAN.getIRI());
    assertTrue(datatype.isBoolean());
  }
}