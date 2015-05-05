package org.swrlapi.factory;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.core.LiteralFactory;
import org.swrlapi.core.OWLDatatypeFactory;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

import java.util.List;

public class DefaultSWRLAPIOWLDataFactory extends OWLDataFactoryImpl implements SWRLAPIOWLDataFactory
{
  private static final long serialVersionUID = 1L;

  private final IRIResolver iriResolver;
  private final OWLLiteralFactory owlLiteralFactory;
  private final OWLDatatypeFactory owlDatatypeFactory;
  private final LiteralFactory literalFactory;
  private final SWRLBuiltInArgumentFactory swrlBuiltInArgumentFactory;
  private final SQWRLResultValueFactory sqwrlResultValueFactory;

  public DefaultSWRLAPIOWLDataFactory(SWRLAPIOWLOntology swrlapiOWLOntology)
  {
    this.iriResolver = swrlapiOWLOntology.getIRIResolver();
    this.owlDatatypeFactory = SWRLAPIFactory.createOWLDatatypeFactory();
    this.owlLiteralFactory = SWRLAPIFactory.createOWLLiteralFactory();
    this.literalFactory = SWRLAPIFactory.createLiteralFactory();
    this.swrlBuiltInArgumentFactory = SWRLAPIFactory.createSWRLBuiltInArgumentFactory(swrlapiOWLOntology);
    this.sqwrlResultValueFactory = SWRLAPIFactory.createSQWRLResultValueFactory(swrlapiOWLOntology);
  }

  @Override
  public SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
  {
    return this.swrlBuiltInArgumentFactory;
  }

  @Override
  public SWRLAPIBuiltInAtom getSWRLAPIBuiltInAtom(String ruleName, IRI builtInIRI, String builtInPrefixedName,
      List<SWRLBuiltInArgument> arguments)
  {
    return SWRLAPIFactory.getSWRLAPIBuiltInAtom(ruleName, builtInIRI, builtInPrefixedName, arguments);
  }

  @Override
  public SQWRLResultValueFactory getSQWRLResultValueFactory()
  {
    return this.sqwrlResultValueFactory;
  }

  @Override
  public OWLDeclarationAxiom getOWLClassDeclarationAxiom(OWLClass cls)
  {
    return getOWLDeclarationAxiom(cls);
  }

  @Override
  public OWLDeclarationAxiom getOWLIndividualDeclarationAxiom(OWLNamedIndividual individual)
  {
    return getOWLDeclarationAxiom(individual);
  }

  @Override
  public OWLDeclarationAxiom getOWLObjectPropertyDeclarationAxiom(OWLObjectProperty property)
  {
    return getOWLDeclarationAxiom(property);
  }

  @Override
  public OWLDeclarationAxiom getOWLDataPropertyDeclarationAxiom(OWLDataProperty property)
  {
    return getOWLDeclarationAxiom(property);
  }

  @Override
  public OWLDeclarationAxiom getOWLAnnotationPropertyDeclarationAxiom(OWLAnnotationProperty property)
  {
    return getOWLDeclarationAxiom(property);
  }

  @Override
  public OWLDeclarationAxiom getOWLDatatypeDeclarationAxiom(OWLDatatype datatype)
  {
    return getOWLDeclarationAxiom(datatype);
  }

  @Override
  public OWLDatatypeFactory getOWLDatatypeFactory()
  {
    return this.owlDatatypeFactory;
  }

  @Override
  public OWLLiteralFactory getOWLLiteralFactory()
  {
    return this.owlLiteralFactory;
  }

  @Override
  public LiteralFactory getLiteralFactory()
  {
    return this.literalFactory;
  }

  @Override
  public IRIResolver getIRIResolver()
  {
    return this.iriResolver;
  }

  @Override
  public OWLClass getInjectedOWLClass()
  {
    // TODO Implement getInjectedOWLClass
    IRI iri = IRI
    // .create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + UUID.randomUUID().toString());
        .create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + "fred");

    return getOWLClass(iri);
  }

  @Override
  public OWLNamedIndividual getInjectedOWLNamedIndividual()
  {
    // TODO Implement getInjectedOWLNamedIndividual
    IRI iri = IRI
    // .create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + UUID.randomUUID().toString());
        .create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + "fred");

    return getOWLNamedIndividual(iri);
  }
}
