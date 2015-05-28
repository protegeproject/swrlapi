package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
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
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

import java.util.List;

public class DefaultSWRLAPIOWLDataFactory extends OWLDataFactoryImpl implements SWRLAPIOWLDataFactory
{
  private static final long serialVersionUID = 1L;

  @NonNull private final IRIResolver iriResolver;
  @NonNull private final OWLLiteralFactory owlLiteralFactory;
  @NonNull private final OWLDatatypeFactory owlDatatypeFactory;
  @NonNull private final LiteralFactory literalFactory;
  @NonNull private final SWRLBuiltInArgumentFactory swrlBuiltInArgumentFactory;
  @NonNull private final SQWRLResultValueFactory sqwrlResultValueFactory;

  public DefaultSWRLAPIOWLDataFactory()
  {
    this(new IRIResolver());
  }

  public DefaultSWRLAPIOWLDataFactory(@NonNull IRIResolver iriResolver)
  {
    this.iriResolver = iriResolver;
    this.owlDatatypeFactory = SWRLAPIFactory.createOWLDatatypeFactory();
    this.owlLiteralFactory = SWRLAPIFactory.createOWLLiteralFactory();
    this.literalFactory = SWRLAPIFactory.createLiteralFactory();
    this.swrlBuiltInArgumentFactory = SWRLAPIFactory.createSWRLBuiltInArgumentFactory(this.iriResolver);
    this.sqwrlResultValueFactory = SWRLAPIFactory.createSQWRLResultValueFactory(this.iriResolver);
  }

  @NonNull @Override public SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
  {
    return this.swrlBuiltInArgumentFactory;
  }

  @NonNull @Override public SWRLAPIBuiltInAtom getSWRLAPIBuiltInAtom(@NonNull String ruleName, @NonNull IRI builtInIRI,
    @NonNull String builtInPrefixedName, @NonNull List<SWRLBuiltInArgument> arguments)
  {
    return SWRLAPIFactory.createSWRLAPIBuiltInAtom(ruleName, builtInIRI, builtInPrefixedName, arguments);
  }

  @NonNull @Override public SQWRLResultValueFactory getSQWRLResultValueFactory()
  {
    return this.sqwrlResultValueFactory;
  }

  @NonNull @Override public OWLDeclarationAxiom getOWLClassDeclarationAxiom(@NonNull OWLClass cls)
  {
    return getOWLDeclarationAxiom(cls);
  }

  @NonNull @Override public OWLDeclarationAxiom getOWLIndividualDeclarationAxiom(OWLNamedIndividual individual)
  {
    return getOWLDeclarationAxiom(individual);
  }

  @NonNull @Override public OWLDeclarationAxiom getOWLObjectPropertyDeclarationAxiom(
    @NonNull OWLObjectProperty property)
  {
    return getOWLDeclarationAxiom(property);
  }

  @NonNull @Override public OWLDeclarationAxiom getOWLDataPropertyDeclarationAxiom(@NonNull OWLDataProperty property)
  {
    return getOWLDeclarationAxiom(property);
  }

  @NonNull @Override public OWLDeclarationAxiom getOWLAnnotationPropertyDeclarationAxiom(
    @NonNull OWLAnnotationProperty property)
  {
    return getOWLDeclarationAxiom(property);
  }

  @NonNull @Override public OWLDeclarationAxiom getOWLDatatypeDeclarationAxiom(@NonNull OWLDatatype datatype)
  {
    return getOWLDeclarationAxiom(datatype);
  }

  @NonNull @Override public OWLDatatypeFactory getOWLDatatypeFactory()
  {
    return this.owlDatatypeFactory;
  }

  @NonNull @Override public OWLLiteralFactory getOWLLiteralFactory()
  {
    return this.owlLiteralFactory;
  }

  @NonNull @Override public LiteralFactory getLiteralFactory()
  {
    return this.literalFactory;
  }

  @NonNull @Override public OWLClass getInjectedOWLClass()
  {
    // TODO Implement getInjectedOWLClass
    IRI iri = IRI
      // .create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + UUID.randomUUID().toString());
      .create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + "fred");

    return getOWLClass(iri);
  }

  @NonNull @Override public OWLNamedIndividual getInjectedOWLNamedIndividual()
  {
    // TODO Implement getInjectedOWLNamedIndividual
    IRI iri = IRI
      // .create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + UUID.randomUUID().toString());
      .create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + "fred");

    return getOWLNamedIndividual(iri);
  }
}
