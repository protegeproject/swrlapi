package org.swrlapi.core;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;

import java.util.List;

/**
 * Factory that extends the OWLAPI's {@link org.semanticweb.owlapi.model.OWLDataFactory} class with additional methods
 * to create entities used by the SWRLAPI.
 * <p>
 * It provides a method to create {@link org.swrlapi.core.SWRLAPIRule} objects, which extend an OWLAPI
 * {@link org.semanticweb.owlapi.model.SWRLRule}, and provides access to factories to create other entity types used by
 * the SWRLAPI that have no direct equivalent in the OWLAPI.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.core.SWRLAPIOntologyProcessor
 * @see org.swrlapi.core.SWRLAPIOWLOntology
 */
public interface SWRLAPIOWLDataFactory extends OWLDataFactory
{
  @NonNull SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory();

  @NonNull SWRLAPIBuiltInAtom getSWRLAPIBuiltInAtom(String ruleName, IRI builtInIRI, String builtInPrefixedName,
      List<SWRLBuiltInArgument> arguments);

  @NonNull OWLDatatypeFactory getOWLDatatypeFactory();

  @NonNull SQWRLResultValueFactory getSQWRLResultValueFactory();

  @NonNull OWLLiteralFactory getOWLLiteralFactory();

  @NonNull LiteralFactory getLiteralFactory();

  @NonNull IRIResolver getIRIResolver();

  // We provide convenience methods for defining these declaration axioms, though we do not specialize the
  // OWLDeclarationAxiom itself.

  @NonNull OWLDeclarationAxiom getOWLClassDeclarationAxiom(OWLClass individual);

  @NonNull OWLDeclarationAxiom getOWLIndividualDeclarationAxiom(OWLNamedIndividual individual);

  @NonNull OWLDeclarationAxiom getOWLObjectPropertyDeclarationAxiom(OWLObjectProperty property);

  @NonNull OWLDeclarationAxiom getOWLDataPropertyDeclarationAxiom(OWLDataProperty property);

  @NonNull OWLDeclarationAxiom getOWLAnnotationPropertyDeclarationAxiom(OWLAnnotationProperty property);

  @NonNull OWLDeclarationAxiom getOWLDatatypeDeclarationAxiom(OWLDatatype datatype);

  @NonNull OWLClass getInjectedOWLClass(); // Generate an OWL class with a unique IRI

  @NonNull OWLNamedIndividual getInjectedOWLNamedIndividual(); // Generate an OWL individual with a unique IRI
}
