package org.protege.swrlapi.ext;

import java.net.URI;
import java.util.List;
import java.util.Set;

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
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;

/**
 * Factory to create Portability API OWL objects. Equivalent to the OWLDataFactory class in the OWLAPI.
 */
public interface SWRLAPIOWLDataFactory
{
	OWLClass getOWLClass(); // Auto-generate a class with a unique URI in the default namespace

	OWLNamedIndividual getOWLNamedIndividual(); // Auto-generate an individual with a unique URI in the default
																							// namespace

	OWLClass getOWLClass(URI classURI);

	OWLNamedIndividual getOWLNamedIndividual(URI individualURI);

	OWLAnonymousIndividual getOWLAnonymousIndividual(String nodeID);

	OWLObjectProperty getOWLObjectProperty(URI propertyURI);

	OWLDataProperty getOWLDataProperty(URI propertyURI);

	OWLAnnotationProperty getOWLAnnotationProperty(URI propertyURI);

	OWLDatatype getOWLDatatype(URI datatypeURI);

	OWLObjectInverseOf getOWLObjectInverseOf(OWLObjectPropertyExpression property);

	OWLObjectComplementOf getOWLObjectComplementOf(OWLClassExpression cls);

	OWLObjectUnionOf getOWLObjectUnionOf(Set<OWLClassExpression> classes);

	OWLObjectIntersectionOf getOWLObjectIntersectionOf(Set<OWLClassExpression> classes);

	OWLObjectOneOf getOWLObjectOneOf(Set<OWLIndividual> individuals);

	OWLDataExactCardinality getOWLDataExactCardinality(OWLDataPropertyExpression property, int cardinality);

	OWLObjectExactCardinality getOWLObjectExactCardinality(OWLObjectPropertyExpression property, int cardinality);

	OWLDataMinCardinality getOWLDataMinCardinality(OWLDataPropertyExpression property, int cardinality);

	OWLObjectMinCardinality getOWLObjectMinCardinality(OWLObjectPropertyExpression property, int cardinality);

	OWLDataMaxCardinality getOWLDataMaxCardinality(OWLDataPropertyExpression property, int cardinality);

	OWLObjectMaxCardinality getOWLObjectMaxCardinality(OWLObjectPropertyExpression property, int cardinality);

	OWLDataHasValue getOWLDataHasValue(OWLDataPropertyExpression property, OWLLiteral value);

	OWLObjectHasValue getOWLObjectHasValue(OWLObjectPropertyExpression property, OWLIndividual individual);

	OWLObjectAllValuesFrom getOWLObjectAllValuesFrom(OWLObjectPropertyExpression property, OWLClassExpression cls);

	OWLDataAllValuesFrom getOWLDataAllValuesFrom(OWLDataPropertyExpression property, OWLDataRange filler);

	OWLObjectSomeValuesFrom getOWLObjectSomeValuesFrom(OWLObjectPropertyExpression property, OWLClassExpression cls);

	OWLDataSomeValuesFrom getOWLDataSomeValuesFrom(OWLDataPropertyExpression property, OWLDataRange filler);

	OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
			OWLDataPropertyExpression property, OWLLiteral object);

	OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLIndividual subject,
			OWLObjectPropertyExpression property, OWLIndividual object);

	OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(Set<OWLIndividual> individuals);

	OWLSameIndividualAxiom getOWLSameIndividualAxiom(Set<OWLIndividual> individuals);

	OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLIndividual individual, OWLClassExpression cls);

	OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass, OWLClassExpression superClass);

	OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<OWLClassExpression> classes);

	OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(Set<OWLClassExpression> classes);

	OWLClassDeclarationAxiom getOWLClassDeclarationAxiom(OWLClass cls);

	OWLIndividualDeclarationAxiom getOWLIndividualDeclarationAxiom(OWLNamedIndividual individual);

	OWLObjectPropertyDeclarationAxiom getOWLObjectPropertyDeclarationAxiom(OWLObjectProperty property);

	OWLDataPropertyDeclarationAxiom getOWLDataPropertyDeclarationAxiom(OWLDataProperty property);

	OWLAnnotationPropertyDeclarationAxiom getOWLAnnotationPropertyDeclarationAxiom(OWLAnnotationProperty property);

	OWLDatatypeDeclarationAxiom getOWLDatatypeDeclarationAxiom(OWLDatatype datatype);

	OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(OWLObjectPropertyExpression subProperty,
			OWLObjectPropertyExpression superProperty);

	OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(OWLDataPropertyExpression subProperty,
			OWLDataPropertyExpression superProperty);

	OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(Set<OWLObjectPropertyExpression> properties);

	OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(Set<OWLDataPropertyExpression> properties);

	OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(Set<OWLObjectPropertyExpression> properties);

	OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(Set<OWLDataPropertyExpression> properties);

	OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpression property,
			OWLClassExpression domain);

	OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property, OWLClassExpression domain);

	OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpression property,
			OWLClassExpression domain);

	OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(OWLDataPropertyExpression property, OWLDataRange range);

	OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property);

	OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property);

	OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property);

	OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(
			OWLObjectPropertyExpression property);

	OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property);

	OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property);

	OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(OWLObjectPropertyExpression firstProperty,
			OWLObjectPropertyExpression secondProperty);

	OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property);

	OWLDataOneOf getOWLDataOneOf(Set<? extends OWLLiteral> values);

	OWLDataComplementOf getOWLDataComplementOf(OWLDataRange dataRange);

	OWLDataIntersectionOf getOWLDataIntersectionOf(Set<? extends OWLDataRange> dataRanges);

	OWLDataUnionOf getOWLDataUnionOf(Set<? extends OWLDataRange> dataRanges);

	SWRLAPIRule getSWRLRule(String ruleName, List<? extends SWRLAtom> body, List<? extends SWRLAtom> head);

	SWRLClassAtom getSWRLClassAtom(OWLClassExpression predicate, SWRLIAtomArgument arg);

	SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(OWLObjectPropertyExpression property, SWRLIAtomArgument arg0,
			SWRLIAtomArgument arg1);

	SWRLDataPropertyAtom getSWRLDataPropertyAtom(OWLDataPropertyExpression property, SWRLIAtomArgument arg0,
			SWRLDAtomArgument arg1);

	SWRLSameIndividualAtom getSWRLSameIndividualAtom(SWRLIAtomArgument arg0, SWRLIAtomArgument arg1);

	SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(SWRLIAtomArgument arg0, SWRLIAtomArgument arg1);

	SWRLAPIBuiltInAtom getSWRLBuiltInAtom(String ruleName, URI builtInURI, String builtInPrefixedName,
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
