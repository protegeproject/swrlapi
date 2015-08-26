package org.swrlapi.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.Namespaces;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Datatype;

public class IntegrationTestBase
{
  final protected static String NS = "http://org.swrlapi.test";
  final protected static double DELTA = 1e-6;

  protected static final OWLDatatype RDFS_LITERAL = Datatype(iri(Namespaces.RDFS + "Literal"));
  protected static final OWLDatatype XSD_STRING = Datatype(iri(Namespaces.XSD + "string"));
  protected static final OWLDatatype XSD_BOOLEAN = Datatype(iri(Namespaces.XSD + "boolean"));
  protected static final OWLDatatype XSD_BYTE = Datatype(iri(Namespaces.XSD + "byte"));
  protected static final OWLDatatype XSD_SHORT = Datatype(iri(Namespaces.XSD + "short"));
  protected static final OWLDatatype XSD_INT = Datatype(iri(Namespaces.XSD + "int"));
  protected static final OWLDatatype XSD_LONG = Datatype(iri(Namespaces.XSD + "long"));
  protected static final OWLDatatype XSD_FLOAT = Datatype(iri(Namespaces.XSD + "float"));
  protected static final OWLDatatype XSD_DOUBLE = Datatype(iri(Namespaces.XSD + "double"));
  protected static final OWLDatatype XSD_DATE = Datatype(iri(Namespaces.XSD + "date"));
  protected static final OWLDatatype XSD_DATETIME = Datatype(iri(Namespaces.XSD + "dateTime"));
  protected static final OWLDatatype XSD_TIME = Datatype(iri(Namespaces.XSD + "time"));
  protected static final OWLDatatype XSD_DURATION = Datatype(iri(Namespaces.XSD + "duration"));
  protected static final OWLDatatype XSD_ANY_URI = Datatype(iri(Namespaces.XSD + "anyURI"));

  protected void addOWLAxioms(OWLOntology ontology, OWLAxiom... axioms)
  {
    OWLOntologyManager ontologyManager = ontology.getOWLOntologyManager();

    for (OWLAxiom axiom : axioms)
      ontologyManager.addAxiom(ontology, axiom);
  }

  protected DefaultPrefixManager createPrefixManager(OWLOntology ontology)
  {
    DefaultPrefixManager prefixManager = new DefaultPrefixManager();
    OWLDocumentFormat format = ontology.getOWLOntologyManager().getOntologyFormat(ontology);

    if (format.isPrefixOWLOntologyFormat())
      prefixManager.copyPrefixesFrom(format.asPrefixOWLOntologyFormat().getPrefixName2PrefixMap());

    prefixManager.setPrefix(":", NS);

    return prefixManager;
  }

  protected static org.semanticweb.owlapi.model.IRI iri(String fragment)
  {
    return org.semanticweb.owlapi.model.IRI.create(NS + "#" + fragment);
  }
}
