package org.protege.swrlapi.ext;

import java.util.List;

import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLAtomArgumentFactory;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgumentFactory;
import org.protege.swrlapi.core.arguments.SWRLClassAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLIAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLVariableAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.protege.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;

/**
 * Factory to create Portability API OWL objects. Equivalent to the OWLDataFactory class in the OWLAPI.
 */
public interface SWRLAPIOWLDataFactory extends OWLDataFactory
{
	OWLClass getOWLClass(); // Auto-generate a class with a unique IRI in the default namespace

	OWLNamedIndividual getOWLNamedIndividual(); // Auto-generate an individual with a unique IRI in the default
																							// namespace

	OWLDataExactCardinality getOWLDataExactCardinality(OWLDataPropertyExpression property, int cardinality);

	OWLObjectExactCardinality getOWLObjectExactCardinality(OWLObjectPropertyExpression property, int cardinality);

	OWLDataMinCardinality getOWLDataMinCardinality(OWLDataPropertyExpression property, int cardinality);

	OWLObjectMinCardinality getOWLObjectMinCardinality(OWLObjectPropertyExpression property, int cardinality);

	OWLDataMaxCardinality getOWLDataMaxCardinality(OWLDataPropertyExpression property, int cardinality);

	OWLObjectMaxCardinality getOWLObjectMaxCardinality(OWLObjectPropertyExpression property, int cardinality);

	OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
			OWLDataPropertyExpression property, OWLLiteral object);

	OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLIndividual subject,
			OWLObjectPropertyExpression property, OWLIndividual object);

	OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLIndividual individual, OWLClassExpression cls);

	SWRLAPIRule getSWRLRule(String ruleName, List<? extends SWRLAtom> body, List<? extends SWRLAtom> head);

	SWRLClassAtom getSWRLClassAtom(OWLClassExpression predicate, SWRLIAtomArgument arg);

	SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(OWLObjectPropertyExpression property, SWRLIAtomArgument arg0,
			SWRLIAtomArgument arg1);

	SWRLDataPropertyAtom getSWRLDataPropertyAtom(OWLDataPropertyExpression property, SWRLIAtomArgument arg0,
			SWRLDAtomArgument arg1);

	SWRLSameIndividualAtom getSWRLSameIndividualAtom(SWRLIAtomArgument arg0, SWRLIAtomArgument arg1);

	SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(SWRLIAtomArgument arg0, SWRLIAtomArgument arg1);

	SWRLAPIBuiltInAtom getSWRLBuiltInAtom(String ruleName, IRI builtInIRI, String builtInPrefixedName,
			List<SWRLBuiltInArgument> arguments);

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

	SWRLSameAsPredicate getSWRLSameAsPredicate();

	SWRLDifferentFromPredicate getSWRLDifferentFromPredicate();

	SWRLBuiltInPredicate getSWRLBuiltInPredicate(String builtInPrefixedName);

	SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory();

	SWRLAtomArgumentFactory getSWRLAtomArgumentFactory();

	SQWRLResultValueFactory getSQWRLResultValueFactory();

	OWLDatatypeFactory getOWLDatatypeFactory();

	OWLLiteralFactory getOWLLiteralFactory();

	SWRLAPILiteralFactory getSWRLAPILiteralFactory();
}
