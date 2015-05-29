package org.swrlapi.factory;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.resolvers.IRIResolver;

import static org.junit.Assert.assertEquals;

/**
 * @see SWRLBuiltInArgumentFactory
 */
public class SWRLBuiltInArgumentFactoryTest
{
  private static final String TestPrefix = "test";
  private static final String TestNamespace = "http://example.org#";

  private OWLOntologyManager ontologyManager;
  private OWLOntology ontology;
  private DefaultPrefixManager prefixManager;
  private IRIResolver iriResolver;
  private SWRLBuiltInArgumentFactory builtInArgumentFactory;
  private OWLDataFactory dataFactory;

  @Before public void setUp() throws OWLOntologyCreationException
  {
    ontologyManager = OWLManager.createOWLOntologyManager();
    dataFactory = ontologyManager.getOWLDataFactory();
    ontology = ontologyManager.createOntology();
    prefixManager = new DefaultPrefixManager();

    iriResolver = SWRLAPIFactory.createIRIResolver(prefixManager);
    builtInArgumentFactory = SWRLAPIFactory.createSWRLBuiltInArgumentFactory(iriResolver);

    prefixManager.setPrefix(TestPrefix, TestNamespace);
  }

  @Test public void testGetClassValue() throws Exception
  {
    IRI classIRI = IRI.create(TestNamespace + "AClass");
    OWLClass cls = this.dataFactory.getOWLClass(classIRI);
    SWRLClassBuiltInArgument classBuiltInArgument = this.builtInArgumentFactory.getClassBuiltInArgument(cls);

    assertEquals(classBuiltInArgument.getIRI(), classIRI);
  }
}
