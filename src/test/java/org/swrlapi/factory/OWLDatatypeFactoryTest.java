package org.swrlapi.factory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

/**
 * @see OWLDatatypeFactory
 */
public class OWLDatatypeFactoryTest
{
  private OWLDatatypeFactory factory;

  @Before public void setUp() throws OWLOntologyCreationException
  {
    this.factory = SWRLAPIFactory.createOWLDatatypeFactory();
  }

  @Test public void testGetBooleanDatatype() throws Exception
  {
    OWLDatatype datatype = this.factory.getBooleanDatatype();

    Assert.assertEquals(XSDVocabulary.BOOLEAN.getIRI(), datatype.getIRI());
    Assert.assertTrue(datatype.isBoolean());
  }
}
