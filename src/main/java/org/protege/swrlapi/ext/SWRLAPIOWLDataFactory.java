package org.protege.swrlapi.ext;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.protege.owl.portability.axioms.OWLAnnotationPropertyDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLAsymmetricObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLClassAssertionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLClassDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDataPropertyAssertionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDataPropertyDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDataPropertyDomainAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDataPropertyRangeAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDatatypeDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDifferentIndividualsAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDisjointClassesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDisjointDataPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDisjointObjectPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLEquivalentClassesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLEquivalentDataPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLEquivalentObjectPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLFunctionalDataPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLFunctionalObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLIndividualDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLInverseFunctionalObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLInverseObjectPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLIrreflexiveObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLObjectPropertyAssertionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLObjectPropertyDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLObjectPropertyDomainAxiomAdapter;
import org.protege.owl.portability.axioms.OWLObjectPropertyRangeAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSameIndividualAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSubClassOfAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSubDataPropertyOfAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSubObjectPropertyOfAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSymmetricObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLTransitiveObjectPropertyAxiomAdapter;
import org.protege.owl.portability.model.OWLAnnotationPropertyAdapter;
import org.protege.owl.portability.model.OWLAnonymousIndividualAdapter;
import org.protege.owl.portability.model.OWLClassAdapter;
import org.protege.owl.portability.model.OWLClassExpressionAdapter;
import org.protege.owl.portability.model.OWLDataComplementOfAdapter;
import org.protege.owl.portability.model.OWLDataIntersectionOfAdapter;
import org.protege.owl.portability.model.OWLDataOneOfAdapter;
import org.protege.owl.portability.model.OWLDataPropertyAdapter;
import org.protege.owl.portability.model.OWLDataPropertyExpressionAdapter;
import org.protege.owl.portability.model.OWLDataRangeAdapter;
import org.protege.owl.portability.model.OWLDataUnionOfAdapter;
import org.protege.owl.portability.model.OWLDatatypeAdapter;
import org.protege.owl.portability.model.OWLIndividualAdapter;
import org.protege.owl.portability.model.OWLLiteralAdapter;
import org.protege.owl.portability.model.OWLNamedIndividualAdapter;
import org.protege.owl.portability.model.OWLObjectComplementOfAdapter;
import org.protege.owl.portability.model.OWLObjectIntersectionOfAdapter;
import org.protege.owl.portability.model.OWLObjectInverseOfAdapter;
import org.protege.owl.portability.model.OWLObjectOneOfAdapter;
import org.protege.owl.portability.model.OWLObjectPropertyAdapter;
import org.protege.owl.portability.model.OWLObjectPropertyExpressionAdapter;
import org.protege.owl.portability.model.OWLObjectUnionOfAdapter;
import org.protege.owl.portability.restrictions.OWLDataAllValuesFromAdapter;
import org.protege.owl.portability.restrictions.OWLDataExactCardinalityAdapter;
import org.protege.owl.portability.restrictions.OWLDataHasValueAdapter;
import org.protege.owl.portability.restrictions.OWLDataMaxCardinalityAdapter;
import org.protege.owl.portability.restrictions.OWLDataMinCardinalityAdapter;
import org.protege.owl.portability.restrictions.OWLDataSomeValuesFromAdapter;
import org.protege.owl.portability.restrictions.OWLObjectAllValuesFromAdapter;
import org.protege.owl.portability.restrictions.OWLObjectExactCardinalityAdapter;
import org.protege.owl.portability.restrictions.OWLObjectHasValueAdapter;
import org.protege.owl.portability.restrictions.OWLObjectMaxCardinalityAdapter;
import org.protege.owl.portability.restrictions.OWLObjectMinCardinalityAdapter;
import org.protege.owl.portability.restrictions.OWLObjectSomeValuesFromAdapter;
import org.protege.owl.portability.swrl.SWRLBuiltInPredicateAdapter;
import org.protege.owl.portability.swrl.SWRLDifferentFromPredicateAdapter;
import org.protege.owl.portability.swrl.SWRLSameAsPredicateAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLClassAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLDataPropertyAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLDifferentIndividualsAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLObjectPropertyAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLSameIndividualAtomAdapter;
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

/**
 * Factory to create Portability API OWL objects. Equivalent to the OWLDataFactory class in the OWLAPI.
 */
public interface SWRLAPIOWLDataFactory
{
	OWLClassAdapter getOWLClass(); // Auto-generate a class with a unique URI in the default namespace

	OWLNamedIndividualAdapter getOWLNamedIndividual(); // Auto-generate an individual with a unique URI in the default
																											// namespace

	OWLClassAdapter getOWLClass(URI classURI);

	OWLNamedIndividualAdapter getOWLNamedIndividual(URI individualURI);

	OWLAnonymousIndividualAdapter getOWLAnonymousIndividual(String nodeID);

	OWLObjectPropertyAdapter getOWLObjectProperty(URI propertyURI);

	OWLDataPropertyAdapter getOWLDataProperty(URI propertyURI);

	OWLAnnotationPropertyAdapter getOWLAnnotationProperty(URI propertyURI);

	OWLDatatypeAdapter getOWLDatatype(URI datatypeURI);

	OWLObjectInverseOfAdapter getOWLObjectInverseOf(OWLObjectPropertyExpressionAdapter property);

	OWLObjectComplementOfAdapter getOWLObjectComplementOf(OWLClassExpressionAdapter cls);

	OWLObjectUnionOfAdapter getOWLObjectUnionOf(Set<OWLClassExpressionAdapter> classes);

	OWLObjectIntersectionOfAdapter getOWLObjectIntersectionOf(Set<OWLClassExpressionAdapter> classes);

	OWLObjectOneOfAdapter getOWLObjectOneOf(Set<OWLIndividualAdapter> individuals);

	OWLDataExactCardinalityAdapter getOWLDataExactCardinality(OWLDataPropertyExpressionAdapter property, int cardinality);

	OWLObjectExactCardinalityAdapter getOWLObjectExactCardinality(OWLObjectPropertyExpressionAdapter property,
			int cardinality);

	OWLDataMinCardinalityAdapter getOWLDataMinCardinality(OWLDataPropertyExpressionAdapter property, int cardinality);

	OWLObjectMinCardinalityAdapter getOWLObjectMinCardinality(OWLObjectPropertyExpressionAdapter property, int cardinality);

	OWLDataMaxCardinalityAdapter getOWLDataMaxCardinality(OWLDataPropertyExpressionAdapter property, int cardinality);

	OWLObjectMaxCardinalityAdapter getOWLObjectMaxCardinality(OWLObjectPropertyExpressionAdapter property, int cardinality);

	OWLDataHasValueAdapter getOWLDataHasValue(OWLDataPropertyExpressionAdapter property, OWLLiteralAdapter value);

	OWLObjectHasValueAdapter getOWLObjectHasValue(OWLObjectPropertyExpressionAdapter property,
			OWLIndividualAdapter individual);

	OWLObjectAllValuesFromAdapter getOWLObjectAllValuesFrom(OWLObjectPropertyExpressionAdapter property,
			OWLClassExpressionAdapter cls);

	OWLDataAllValuesFromAdapter getOWLDataAllValuesFrom(OWLDataPropertyExpressionAdapter property,
			OWLDataRangeAdapter filler);

	OWLObjectSomeValuesFromAdapter getOWLObjectSomeValuesFrom(OWLObjectPropertyExpressionAdapter property,
			OWLClassExpressionAdapter cls);

	OWLDataSomeValuesFromAdapter getOWLDataSomeValuesFrom(OWLDataPropertyExpressionAdapter property,
			OWLDataRangeAdapter filler);

	OWLDataPropertyAssertionAxiomAdapter getOWLDataPropertyAssertionAxiom(OWLIndividualAdapter subject,
			OWLDataPropertyExpressionAdapter property, OWLLiteralAdapter object);

	OWLObjectPropertyAssertionAxiomAdapter getOWLObjectPropertyAssertionAxiom(OWLIndividualAdapter subject,
			OWLObjectPropertyExpressionAdapter property, OWLIndividualAdapter object);

	OWLDifferentIndividualsAxiomAdapter getOWLDifferentIndividualsAxiom(Set<OWLIndividualAdapter> individuals);

	OWLSameIndividualAxiomAdapter getOWLSameIndividualAxiom(Set<OWLIndividualAdapter> individuals);

	OWLClassAssertionAxiomAdapter getOWLClassAssertionAxiom(OWLIndividualAdapter individual, OWLClassExpressionAdapter cls);

	OWLSubClassOfAxiomAdapter getOWLSubClassOfAxiom(OWLClassExpressionAdapter subClass,
			OWLClassExpressionAdapter superClass);

	OWLEquivalentClassesAxiomAdapter getOWLEquivalentClassesAxiom(Set<OWLClassExpressionAdapter> classes);

	OWLDisjointClassesAxiomAdapter getOWLDisjointClassesAxiom(Set<OWLClassExpressionAdapter> classes);

	OWLClassDeclarationAxiomAdapter getOWLClassDeclarationAxiom(OWLClassAdapter cls);

	OWLIndividualDeclarationAxiomAdapter getOWLIndividualDeclarationAxiom(OWLNamedIndividualAdapter individual);

	OWLObjectPropertyDeclarationAxiomAdapter getOWLObjectPropertyDeclarationAxiom(OWLObjectPropertyAdapter property);

	OWLDataPropertyDeclarationAxiomAdapter getOWLDataPropertyDeclarationAxiom(OWLDataPropertyAdapter property);

	OWLAnnotationPropertyDeclarationAxiomAdapter getOWLAnnotationPropertyDeclarationAxiom(
			OWLAnnotationPropertyAdapter property);

	OWLDatatypeDeclarationAxiomAdapter getOWLDatatypeDeclarationAxiom(OWLDatatypeAdapter datatype);

	OWLSubObjectPropertyOfAxiomAdapter getOWLSubObjectPropertyOfAxiom(OWLObjectPropertyExpressionAdapter subProperty,
			OWLObjectPropertyExpressionAdapter superProperty);

	OWLSubDataPropertyOfAxiomAdapter getOWLSubDataPropertyOfAxiom(OWLDataPropertyExpressionAdapter subProperty,
			OWLDataPropertyExpressionAdapter superProperty);

	OWLEquivalentObjectPropertiesAxiomAdapter getOWLEquivalentObjectPropertiesAxiom(
			Set<OWLObjectPropertyExpressionAdapter> properties);

	OWLEquivalentDataPropertiesAxiomAdapter getOWLEquivalentDataPropertiesAxiom(
			Set<OWLDataPropertyExpressionAdapter> properties);

	OWLDisjointObjectPropertiesAxiomAdapter getOWLDisjointObjectPropertiesAxiom(
			Set<OWLObjectPropertyExpressionAdapter> properties);

	OWLDisjointDataPropertiesAxiomAdapter getOWLDisjointDataPropertiesAxiom(
			Set<OWLDataPropertyExpressionAdapter> properties);

	OWLObjectPropertyDomainAxiomAdapter getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpressionAdapter property,
			OWLClassExpressionAdapter domain);

	OWLDataPropertyDomainAxiomAdapter getOWLDataPropertyDomainAxiom(OWLDataPropertyExpressionAdapter property,
			OWLClassExpressionAdapter domain);

	OWLObjectPropertyRangeAxiomAdapter getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpressionAdapter property,
			OWLClassExpressionAdapter domain);

	OWLDataPropertyRangeAxiomAdapter getOWLDataPropertyRangeAxiom(OWLDataPropertyExpressionAdapter property,
			OWLDataRangeAdapter range);

	OWLTransitiveObjectPropertyAxiomAdapter getOWLTransitiveObjectPropertyAxiom(
			OWLObjectPropertyExpressionAdapter property);

	OWLSymmetricObjectPropertyAxiomAdapter getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpressionAdapter property);

	OWLFunctionalObjectPropertyAxiomAdapter getOWLFunctionalObjectPropertyAxiom(
			OWLObjectPropertyExpressionAdapter property);

	OWLInverseFunctionalObjectPropertyAxiomAdapter getOWLInverseFunctionalObjectPropertyAxiom(
			OWLObjectPropertyExpressionAdapter property);

	OWLIrreflexiveObjectPropertyAxiomAdapter getOWLIrreflexiveObjectPropertyAxiom(
			OWLObjectPropertyExpressionAdapter property);

	OWLAsymmetricObjectPropertyAxiomAdapter getOWLAsymmetricObjectPropertyAxiom(
			OWLObjectPropertyExpressionAdapter property);

	OWLInverseObjectPropertiesAxiomAdapter getOWLInverseObjectPropertiesAxiom(
			OWLObjectPropertyExpressionAdapter firstProperty, OWLObjectPropertyExpressionAdapter secondProperty);

	OWLFunctionalDataPropertyAxiomAdapter getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpressionAdapter property);

	OWLDataOneOfAdapter getOWLDataOneOf(Set<? extends OWLLiteralAdapter> values);

	OWLDataComplementOfAdapter getOWLDataComplementOf(OWLDataRangeAdapter dataRange);

	OWLDataIntersectionOfAdapter getOWLDataIntersectionOf(Set<? extends OWLDataRangeAdapter> dataRanges);

	OWLDataUnionOfAdapter getOWLDataUnionOf(Set<? extends OWLDataRangeAdapter> dataRanges);

	SWRLAPIRule getSWRLRule(String ruleName, List<? extends SWRLAtomAdapter> body, List<? extends SWRLAtomAdapter> head);

	SWRLClassAtomAdapter getSWRLClassAtom(OWLClassExpressionAdapter predicate, SWRLIAtomArgument arg);

	SWRLObjectPropertyAtomAdapter getSWRLObjectPropertyAtom(OWLObjectPropertyExpressionAdapter property,
			SWRLIAtomArgument arg0, SWRLIAtomArgument arg1);

	SWRLDataPropertyAtomAdapter getSWRLDataPropertyAtom(OWLDataPropertyExpressionAdapter property,
			SWRLIAtomArgument arg0, SWRLDAtomArgument arg1);

	SWRLSameIndividualAtomAdapter getSWRLSameIndividualAtom(SWRLIAtomArgument arg0, SWRLIAtomArgument arg1);

	SWRLDifferentIndividualsAtomAdapter getSWRLDifferentIndividualsAtom(SWRLIAtomArgument arg0, SWRLIAtomArgument arg1);

	SWRLAPIBuiltInAtom getSWRLBuiltInAtom(String ruleName, URI builtInURI, String builtInPrefixedName,
			List<SWRLBuiltInArgument> arguments);

	SWRLVariableAtomArgument getSWRLVariableAtomArgument(String variableName);

	SWRLVariableBuiltInArgument getSWRLVariableBuiltInArgument(String variableName);

	SWRLClassAtomArgument getSWRLClassAtomArgument(OWLClassAdapter cls);

	SWRLClassBuiltInArgument getSWRLClassBuiltInArgument(OWLClassAdapter cls);

	SWRLIndividualAtomArgument getSWRLIndividualAtomArgument(OWLIndividualAdapter individual);

	SWRLIndividualBuiltInArgument getSWRLIndividualBuiltInArgument(OWLIndividualAdapter individual);

	SWRLObjectPropertyAtomArgument getSWRLObjectPropertyAtomArgument(OWLObjectPropertyAdapter property);

	SWRLObjectPropertyBuiltInArgument getSWRLObjectPropertyBuiltInArgument(OWLObjectPropertyAdapter property);

	SWRLDataPropertyAtomArgument getSWRLDataPropertyAtomArgument(OWLDataPropertyAdapter property);

	SWRLDataPropertyBuiltInArgument getSWRLDataPropertyBuiltInArgument(OWLDataPropertyAdapter property);

	SWRLAnnotationPropertyBuiltInArgument getSWRLAnnotationPropertyBuiltInArgument(OWLAnnotationPropertyAdapter property);

	SWRLLiteralBuiltInArgument getSWRLLiteralBuiltInArgument(OWLLiteralAdapter literal);

	SWRLLiteralAtomArgument getSWRLLiteralAtomArgument(OWLLiteralAdapter literal);

	SWRLLiteralAtomArgument getSWRLLiteralAtomArgument(String lexicalValue, OWLDatatypeAdapter datatype);

	SWRLSameAsPredicateAdapter getSWRLSameAsPredicate();

	SWRLDifferentFromPredicateAdapter getSWRLDifferentFromPredicate();

	SWRLBuiltInPredicateAdapter getSWRLBuiltInPredicate(String builtInPrefixedName);

	SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory();

	SWRLAtomArgumentFactory getSWRLAtomArgumentFactory();

	SQWRLResultValueFactory getSQWRLResultValueFactory();

	OWLDatatypeFactory getOWLDatatypeFactory();

	OWLLiteralFactory getOWLLiteralFactory();

	SWRLAPILiteralFactory getSWRLAPILiteralFactory();
}
