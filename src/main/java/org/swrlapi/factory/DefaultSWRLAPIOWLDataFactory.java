package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

import java.util.List;

public class DefaultSWRLAPIOWLDataFactory extends OWLDataFactoryImpl implements SWRLAPIOWLDataFactory
{
  private static final long serialVersionUID = 1L;

  @NonNull private final IRIResolver iriResolver;
  @NonNull private final OWLObjectRenderer owlObjectRenderer;
  @NonNull private final OWLLiteralFactory owlLiteralFactory;
  @NonNull private final OWLDatatypeFactory owlDatatypeFactory;
  @NonNull private final LiteralFactory literalFactory;
  @NonNull private final SWRLBuiltInArgumentFactory swrlBuiltInArgumentFactory;
  @NonNull private final SQWRLResultValueFactory sqwrlResultValueFactory;

  public DefaultSWRLAPIOWLDataFactory(@NonNull IRIResolver iriResolver, @NonNull OWLObjectRenderer owlObjectRenderer)
  {
    this.iriResolver = iriResolver;
    this.owlObjectRenderer = owlObjectRenderer;
    this.owlDatatypeFactory = SWRLAPIInternalFactory.createOWLDatatypeFactory();
    this.owlLiteralFactory = SWRLAPIInternalFactory.createOWLLiteralFactory();
    this.literalFactory = SWRLAPIInternalFactory.createLiteralFactory();
    this.swrlBuiltInArgumentFactory = SWRLAPIInternalFactory.createSWRLBuiltInArgumentFactory(this.iriResolver);
    this.sqwrlResultValueFactory = SWRLAPIInternalFactory
      .createSQWRLResultValueFactory(this.iriResolver, this.owlObjectRenderer);
  }

  @NonNull @Override public SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
  {
    return this.swrlBuiltInArgumentFactory;
  }

  @NonNull @Override public SWRLAPIBuiltInAtom getSWRLAPIBuiltInAtom(@NonNull String ruleName, @NonNull IRI builtInIRI,
    @NonNull String builtInPrefixedName, @NonNull List<@NonNull SWRLBuiltInArgument> arguments)
  {
    return SWRLAPIInternalFactory.createSWRLAPIBuiltInAtom(ruleName, builtInIRI, builtInPrefixedName, arguments);
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
    IRI iri = this.iriResolver.generateIRI();
    return getOWLClass(iri);
  }

  @NonNull @Override public OWLNamedIndividual getInjectedOWLNamedIndividual()
  {
    IRI iri = this.iriResolver.generateIRI();
    return getOWLNamedIndividual(iri);
  }
}
