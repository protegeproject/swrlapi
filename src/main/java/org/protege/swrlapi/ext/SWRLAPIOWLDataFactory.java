package org.protege.swrlapi.ext;

import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLAtomArgumentFactory;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgumentFactory;
import org.protege.swrlapi.core.arguments.SWRLClassAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLVariableAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.protege.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Factory that extends the OWLAPI's OWLDataFactory class with additional methods used by the SWRLAPI.
 */
public interface SWRLAPIOWLDataFactory extends OWLDataFactory
{
	OWLClass getOWLClass(); // Auto-generate a class with a unique IRI in the default namespace

	OWLNamedIndividual getOWLNamedIndividual(); // Auto-generate an individual with a unique IRI in the default
																							// namespace

	SWRLVariableAtomArgument getSWRLVariableAtomArgument(String variableName);

	SWRLVariableBuiltInArgument getSWRLVariableBuiltInArgument(String variableName);

	SWRLClassAtomArgument getSWRLClassAtomArgument(OWLClass cls);

	SWRLClassBuiltInArgument getSWRLClassBuiltInArgument(OWLClass cls);

	SWRLIndividualAtomArgument getSWRLIndividualAtomArgument(OWLIndividual individual);

	SWRLIndividualBuiltInArgument getSWRLIndividualBuiltInArgument(OWLIndividual individual);

	SWRLObjectPropertyAtomArgument getSWRLObjectPropertyAtomArgument(OWLObjectProperty property);

	SWRLObjectPropertyBuiltInArgument getSWRLObjectPropertyBuiltInArgument(OWLObjectProperty property);

	SWRLDataPropertyAtomArgument getSWRLDataPropertyAtomArgument(OWLDataProperty property);

	SWRLDataPropertyBuiltInArgument getSWRLDataPropertyBuiltInArgument(OWLDataProperty property);

	SWRLAnnotationPropertyBuiltInArgument getSWRLAnnotationPropertyBuiltInArgument(OWLAnnotationProperty property);

	SWRLLiteralBuiltInArgument getSWRLLiteralBuiltInArgument(OWLLiteral literal);

	SWRLLiteralAtomArgument getSWRLLiteralAtomArgument(OWLLiteral literal);

	SWRLLiteralAtomArgument getSWRLLiteralAtomArgument(String lexicalValue, OWLDatatype datatype);

	OWLDeclarationAxiom getOWLClassDeclarationAxiom(OWLClass individual);

	OWLDeclarationAxiom getOWLIndividualDeclarationAxiom(OWLNamedIndividual individual);

	OWLDeclarationAxiom getOWLObjectPropertyDeclarationAxiom(OWLObjectProperty property);

	OWLDeclarationAxiom getOWLDataPropertyDeclarationAxiom(OWLDataProperty property);

	OWLDeclarationAxiom getOWLAnnotationPropertyDeclarationAxiom(OWLAnnotationProperty property);

	OWLDeclarationAxiom getOWLDatatypeDeclarationAxiom(OWLDatatype datatype);

	SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory();

	SWRLAtomArgumentFactory getSWRLAtomArgumentFactory();

	SQWRLResultValueFactory getSQWRLResultValueFactory();

	OWLDatatypeFactory getOWLDatatypeFactory();

	OWLLiteralFactory getOWLLiteralFactory();

	SWRLAPILiteralFactory getSWRLAPILiteralFactory();
}
