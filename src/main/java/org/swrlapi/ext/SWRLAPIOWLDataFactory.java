package org.swrlapi.ext;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.SWRLRule;
import org.swrlapi.core.arguments.SWRLAtomArgumentFactory;
import org.swrlapi.core.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;

/**
 * Factory that extends the OWLAPI's OWLDataFactory class with additional methods to create entitied used by the
 * SWRLAPI.
 * <p>
 * It provides a method to create a {@link SWRLAPIRule}, which extends an OWLAPI {@link SWRLRule}, and access to
 * factories to create other entity types used by the SWRLAPI that have no direct equivalent in the OWLAPI.
 */
public interface SWRLAPIOWLDataFactory extends OWLDataFactory
{
	SWRLAPIRule getSWRLRule(String ruleName, String ruleText); // Also SQWRL query

	SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory();

	SWRLAtomArgumentFactory getSWRLAtomArgumentFactory();

	OWLDatatypeFactory getOWLDatatypeFactory();

	SQWRLResultValueFactory getSQWRLResultValueFactory();

	OWLLiteralFactory getOWLLiteralFactory();

	SWRLAPILiteralFactory getSWRLAPILiteralFactory();

	OWLClass getOWLClass(); // Auto-generate an OWL class with a unique IRI

	OWLNamedIndividual getOWLNamedIndividual(); // Auto-generate an OWL individual with a unique IRI

	// We provide convenience methods for these declarations, though we do not specialize the OWLDeclarationAxiom class
	// itself.

	OWLDeclarationAxiom getOWLClassDeclarationAxiom(OWLClass individual);

	OWLDeclarationAxiom getOWLIndividualDeclarationAxiom(OWLNamedIndividual individual);

	OWLDeclarationAxiom getOWLObjectPropertyDeclarationAxiom(OWLObjectProperty property);

	OWLDeclarationAxiom getOWLDataPropertyDeclarationAxiom(OWLDataProperty property);

	OWLDeclarationAxiom getOWLAnnotationPropertyDeclarationAxiom(OWLAnnotationProperty property);

	OWLDeclarationAxiom getOWLDatatypeDeclarationAxiom(OWLDatatype datatype);
}
