package org.swrlapi.factory;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.sqwrl.values.SQWRLClassResultValue;

import static junit.framework.TestCase.assertEquals;

/**
 * @see SQWRLResultValueFactory
 */
public class SQWRLResultValueFactoryTest
{
  private static final String TestPrefix = "test";
  private static final String TestNamespace = "http://example.org#";

  private OWLOntologyManager ontologyManager;
  private IRIResolver iriResolver;
  private SQWRLResultValueFactory resultValueFactory;
  private SWRLBuiltInArgumentFactory builtInArgumentFactory;
  private OWLDataFactory dataFactory;

  @Before public void setUp() throws OWLOntologyCreationException
  {
    ontologyManager = OWLManager.createOWLOntologyManager();
    dataFactory = ontologyManager.getOWLDataFactory();
    iriResolver = SWRLAPIFactory.createIRIResolver();
    resultValueFactory = SWRLAPIInternalFactory.createSQWRLResultValueFactory(iriResolver);
    builtInArgumentFactory = SWRLAPIInternalFactory.createSWRLBuiltInArgumentFactory(iriResolver);

    iriResolver.setPrefix(TestPrefix, TestNamespace);
  }

  @Test public void testGetClassValueWithBuiltInArgument() throws Exception
  {
    IRI classIRI = IRI.create(TestNamespace + "AClass");
    OWLClass cls = this.dataFactory.getOWLClass(classIRI);
    SWRLClassBuiltInArgument classBuiltInArgument = this.builtInArgumentFactory.getClassBuiltInArgument(cls);
    SQWRLClassResultValue value = this.resultValueFactory.getClassValue(classBuiltInArgument);

    assertEquals(classIRI, value.getIRI());
  }

  @Test public void testGetClassValueWithIRI() throws Exception
  {
    IRI classIRI = IRI.create(TestNamespace + "AClass");
    SQWRLClassResultValue value = this.resultValueFactory.getClassValue(classIRI);

    assertEquals(classIRI, value.getIRI());
  }
}
