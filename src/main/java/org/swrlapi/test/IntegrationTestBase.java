package org.swrlapi.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.Namespaces;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

public class IntegrationTestBase
{
  final protected static String NS = "http://org.swrlapi.test#";
  final protected static double DELTA = 1e-6;

  protected static final OWLDatatype RDFS_LITERAL = Datatype(IRI(Namespaces.RDFS + "Literal"));
  protected static final OWLDatatype XSD_STRING = Datatype(IRI(Namespaces.XSD + "string"));
  protected static final OWLDatatype XSD_BOOLEAN = Datatype(IRI(Namespaces.XSD + "boolean"));
  protected static final OWLDatatype XSD_BYTE = Datatype(IRI(Namespaces.XSD + "byte"));
  protected static final OWLDatatype XSD_SHORT = Datatype(IRI(Namespaces.XSD + "short"));
  protected static final OWLDatatype XSD_INT = Datatype(IRI(Namespaces.XSD + "int"));
  protected static final OWLDatatype XSD_LONG = Datatype(IRI(Namespaces.XSD + "long"));
  protected static final OWLDatatype XSD_FLOAT = Datatype(IRI(Namespaces.XSD + "float"));
  protected static final OWLDatatype XSD_DOUBLE = Datatype(IRI(Namespaces.XSD + "double"));
  protected static final OWLDatatype XSD_DATE = Datatype(IRI(Namespaces.XSD + "date"));
  protected static final OWLDatatype XSD_DATETIME = Datatype(IRI(Namespaces.XSD + "dateTime"));
  protected static final OWLDatatype XSD_TIME = Datatype(IRI(Namespaces.XSD + "time"));
  protected static final OWLDatatype XSD_DURATION = Datatype(IRI(Namespaces.XSD + "duration"));
  protected static final OWLDatatype XSD_ANY_URI = Datatype(IRI(Namespaces.XSD + "anyURI"));

  protected void addOWLAxioms(OWLOntology ontology, OWLAxiom... axioms)
  {
    OWLOntologyManager ontologyManager = ontology.getOWLOntologyManager();

    for (OWLAxiom axiom : axioms)
      ontologyManager.addAxiom(ontology, axiom);
  }

  protected static org.semanticweb.owlapi.model.IRI iri(String iri)
  {
    return org.semanticweb.owlapi.model.IRI.create(iri);
  }
}
