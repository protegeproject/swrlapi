package org.protege.swrlapi.ext.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.protege.owl.portability.axioms.impl.OWLAnnotationPropertyDeclarationAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLAsymmetricObjectPropertyAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLClassAssertionAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLClassDeclarationAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLDataPropertyAssertionAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLDataPropertyDeclarationAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLDataPropertyDomainAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLDataPropertyRangeAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLDatatypeDeclarationAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLDifferentIndividualsAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLDisjointClassesAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLDisjointDataPropertiesAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLDisjointObjectPropertiesAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLEquivalentClassesAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLEquivalentDataPropertiesAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLEquivalentObjectPropertiesAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLFunctionalDataPropertyAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLFunctionalObjectPropertyAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLIndividualDeclarationAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLInverseFunctionalObjectPropertyAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLInverseObjectPropertiesAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLIrreflexiveObjectPropertyAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLObjectPropertyAssertionAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLObjectPropertyDeclarationAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLObjectPropertyDomainAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLObjectPropertyRangeAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLSameIndividualAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLSubClassOfAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLSubDataPropertyOfAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLSubObjectPropertyOfAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLSymmetricObjectPropertyAxiomAdapterImpl;
import org.protege.owl.portability.axioms.impl.OWLTransitiveObjectPropertyAxiomAdapterImpl;
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
import org.protege.owl.portability.model.impl.OWLAnnotationPropertyAdapterImpl;
import org.protege.owl.portability.model.impl.OWLAnonymousIndividualAdapterImpl;
import org.protege.owl.portability.model.impl.OWLClassAdapterImpl;
import org.protege.owl.portability.model.impl.OWLDataComplementOfAdapterImpl;
import org.protege.owl.portability.model.impl.OWLDataIntersectionOfAdapterImpl;
import org.protege.owl.portability.model.impl.OWLDataOneOfAdapterImpl;
import org.protege.owl.portability.model.impl.OWLDataPropertyAdapterImpl;
import org.protege.owl.portability.model.impl.OWLDataUnionOfAdapterImpl;
import org.protege.owl.portability.model.impl.OWLDatatypeAdapterImpl;
import org.protege.owl.portability.model.impl.OWLLiteralAdapterImpl;
import org.protege.owl.portability.model.impl.OWLNamedIndividualAdapterImpl;
import org.protege.owl.portability.model.impl.OWLObjectComplementOfAdapterImpl;
import org.protege.owl.portability.model.impl.OWLObjectIntersectionOfAdapterImpl;
import org.protege.owl.portability.model.impl.OWLObjectInverseOfAdapterImpl;
import org.protege.owl.portability.model.impl.OWLObjectOneOfAdapterImpl;
import org.protege.owl.portability.model.impl.OWLObjectPropertyAdapterImpl;
import org.protege.owl.portability.model.impl.OWLObjectUnionOfAdapterImpl;
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
import org.protege.owl.portability.restrictions.impl.OWLDataAllValuesFromAdapterImpl;
import org.protege.owl.portability.restrictions.impl.OWLDataExactCardinalityAdapterImpl;
import org.protege.owl.portability.restrictions.impl.OWLDataHasValueAdapterImpl;
import org.protege.owl.portability.restrictions.impl.OWLDataMaxCardinalityAdapterImpl;
import org.protege.owl.portability.restrictions.impl.OWLDataMinCardinalityAdapterImpl;
import org.protege.owl.portability.restrictions.impl.OWLDataSomeValuesFromAdapterImpl;
import org.protege.owl.portability.restrictions.impl.OWLObjectAllValuesFromAdapterImpl;
import org.protege.owl.portability.restrictions.impl.OWLObjectExactCardinalityAdapterImpl;
import org.protege.owl.portability.restrictions.impl.OWLObjectHasValueAdapterImpl;
import org.protege.owl.portability.restrictions.impl.OWLObjectMaxCardinalityAdapterImpl;
import org.protege.owl.portability.restrictions.impl.OWLObjectMinCardinalityAdapterImpl;
import org.protege.owl.portability.restrictions.impl.OWLObjectSomeValuesFromAdapterImpl;
import org.protege.owl.portability.swrl.SWRLBuiltInPredicateAdapter;
import org.protege.owl.portability.swrl.SWRLDifferentFromPredicateAdapter;
import org.protege.owl.portability.swrl.SWRLSameAsPredicateAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLClassAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLDataPropertyAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLDifferentIndividualsAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLObjectPropertyAtomAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLSameIndividualAtomAdapter;
import org.protege.owl.portability.swrl.atoms.impl.SWRLClassAtomAdapterImpl;
import org.protege.owl.portability.swrl.atoms.impl.SWRLDataPropertyAtomAdapterImpl;
import org.protege.owl.portability.swrl.atoms.impl.SWRLDifferentIndividualsAtomAdapterImpl;
import org.protege.owl.portability.swrl.atoms.impl.SWRLObjectPropertyAtomAdapterImpl;
import org.protege.owl.portability.swrl.atoms.impl.SWRLSameIndividualAtomAdapterImpl;
import org.protege.owl.portability.swrl.impl.SWRLBuiltInPredicateAdapterImpl;
import org.protege.owl.portability.swrl.impl.SWRLDifferentFromPredicateAdapterImpl;
import org.protege.owl.portability.swrl.impl.SWRLSameAsPredicateAdapterImpl;
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
import org.protege.swrlapi.core.arguments.impl.SWRLAtomArgumentFactoryImpl;
import org.protege.swrlapi.core.arguments.impl.SWRLBuiltInArgumentFactoryImpl;
import org.protege.swrlapi.ext.OWLDatatypeFactory;
import org.protege.swrlapi.ext.OWLLiteralFactory;
import org.protege.swrlapi.ext.SWRLAPIBuiltInAtom;
import org.protege.swrlapi.ext.SWRLAPILiteralFactory;
import org.protege.swrlapi.ext.SWRLAPIOWLDataFactory;
import org.protege.swrlapi.ext.SWRLAPIOWLOntology;
import org.protege.swrlapi.ext.SWRLAPIRule;
import org.protege.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import org.protege.swrlapi.sqwrl.values.impl.DefaultSQWRLResultValueFactory;

public class DefaultSWRLAPIOWLDataFactory implements SWRLAPIOWLDataFactory
{
	private final Map<URI, OWLClassAdapter> classes = new HashMap<URI, OWLClassAdapter>();
	private final Map<URI, OWLNamedIndividualAdapter> namedIndividuals = new HashMap<URI, OWLNamedIndividualAdapter>();
	private final Map<String, OWLAnonymousIndividualAdapter> anonymousIndividuals = new HashMap<String, OWLAnonymousIndividualAdapter>();
	private final Map<URI, OWLObjectPropertyAdapter> objectProperties = new HashMap<URI, OWLObjectPropertyAdapter>();
	private final Map<URI, OWLDataPropertyAdapter> dataProperties = new HashMap<URI, OWLDataPropertyAdapter>();
	private final Map<OWLObjectPropertyExpressionAdapter, OWLObjectInverseOfAdapter> inverseProperties = new HashMap<OWLObjectPropertyExpressionAdapter, OWLObjectInverseOfAdapter>();
	private final Map<URI, OWLAnnotationPropertyAdapter> annotationProperties = new HashMap<URI, OWLAnnotationPropertyAdapter>();
	private final Map<URI, OWLDatatypeAdapter> datatypes = new HashMap<URI, OWLDatatypeAdapter>();

	private final SWRLAPIOWLOntology owlOntology;

	private static final String OWL_THING = "http://www.w3.org/2002/07/owl#Thing";

	private final SWRLAtomArgumentFactory swrlAtomArgumentFactory;
	private final SWRLBuiltInArgumentFactory swrlBuiltInArgumentFactory;
	private final SQWRLResultValueFactory sqwrlResultValueFactory;
	private final OWLDatatypeFactory owlDatatypeFactory;
	private final OWLLiteralFactory owlLiteralFactory;
	private final SWRLAPILiteralFactory swrlAPILiteralFactory;

	public DefaultSWRLAPIOWLDataFactory(SWRLAPIOWLOntology owlOntology)
	{
		this.owlOntology = owlOntology;
		this.swrlAtomArgumentFactory = new SWRLAtomArgumentFactoryImpl();
		this.swrlBuiltInArgumentFactory = new SWRLBuiltInArgumentFactoryImpl();
		this.sqwrlResultValueFactory = new DefaultSQWRLResultValueFactory();
		this.owlDatatypeFactory = new DefaultOWLDatatypeFactory();
		this.owlLiteralFactory = new DefaultOWLLiteralFactory(this.owlDatatypeFactory);
		this.swrlAPILiteralFactory = new DefaultSWRLAPILiteralFactory();
	}

	@Override
	public SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
	{
		return this.swrlBuiltInArgumentFactory;
	}

	@Override
	public OWLClassAdapter getOWLClass()
	{
		URI classURI = getActiveOWLOntology().generateOWLEntityURI("INJECTED_CLASS");

		return getOWLClass(classURI);
	}

	@Override
	public OWLNamedIndividualAdapter getOWLNamedIndividual()
	{
		URI classURI = getActiveOWLOntology().generateOWLEntityURI("INJECTED_INDIVIDUAL");

		return getOWLNamedIndividual(classURI);
	}

	@Override
	public OWLClassAdapter getOWLClass(URI classURI)
	{
		if (this.classes.containsKey(classURI))
			return this.classes.get(classURI);

		OWLClassAdapter cls = new OWLClassAdapterImpl(classURI, getActiveOWLOntology().uri2PrefixedName(classURI));
		this.classes.put(classURI, cls);
		return cls;

	}

	@Override
	public OWLNamedIndividualAdapter getOWLNamedIndividual(URI individualURI)
	{
		if (this.namedIndividuals.containsKey(individualURI))
			return this.namedIndividuals.get(individualURI);

		OWLNamedIndividualAdapter individual = new OWLNamedIndividualAdapterImpl(individualURI, getActiveOWLOntology()
				.uri2PrefixedName(individualURI));
		this.namedIndividuals.put(individualURI, individual);
		return individual;
	}

	@Override
	public OWLAnonymousIndividualAdapter getOWLAnonymousIndividual(String nodeID)
	{
		if (this.anonymousIndividuals.containsKey(nodeID))
			return this.anonymousIndividuals.get(nodeID);

		OWLAnonymousIndividualAdapter individual = new OWLAnonymousIndividualAdapterImpl(nodeID);
		this.anonymousIndividuals.put(nodeID, individual);
		return individual;
	}

	@Override
	public OWLObjectPropertyAdapter getOWLObjectProperty(URI propertyURI)
	{
		if (this.objectProperties.containsKey(propertyURI))
			return this.objectProperties.get(propertyURI);

		OWLObjectPropertyAdapter property = new OWLObjectPropertyAdapterImpl(propertyURI, getActiveOWLOntology()
				.uri2PrefixedName(propertyURI));
		this.objectProperties.put(propertyURI, property);
		return property;
	}

	@Override
	public OWLDataPropertyAdapter getOWLDataProperty(URI propertyURI)
	{
		if (this.dataProperties.containsKey(propertyURI))
			return this.dataProperties.get(propertyURI);

		OWLDataPropertyAdapter property = new OWLDataPropertyAdapterImpl(propertyURI, getActiveOWLOntology()
				.uri2PrefixedName(propertyURI));
		this.dataProperties.put(propertyURI, property);
		return property;
	}

	@Override
	public OWLAnnotationPropertyAdapter getOWLAnnotationProperty(URI propertyURI)
	{
		if (this.annotationProperties.containsKey(propertyURI))
			return this.annotationProperties.get(propertyURI);

		OWLAnnotationPropertyAdapter property = new OWLAnnotationPropertyAdapterImpl(propertyURI, getActiveOWLOntology()
				.uri2PrefixedName(propertyURI));
		this.annotationProperties.put(propertyURI, property);
		return property;
	}

	@Override
	public OWLDatatypeAdapter getOWLDatatype(URI datatypeURI)
	{
		if (this.datatypes.containsKey(datatypeURI))
			return this.datatypes.get(datatypeURI);

		OWLDatatypeAdapter datatype = new OWLDatatypeAdapterImpl(datatypeURI, getActiveOWLOntology().uri2PrefixedName(
				datatypeURI));
		this.datatypes.put(datatypeURI, datatype);
		return datatype;

	}

	@Override
	public OWLObjectInverseOfAdapter getOWLObjectInverseOf(OWLObjectPropertyExpressionAdapter propertyExpression)
	{
		if (this.inverseProperties.containsKey(propertyExpression))
			return this.inverseProperties.get(propertyExpression);
		else {
			OWLObjectInverseOfAdapter inversePropertyOf = new OWLObjectInverseOfAdapterImpl(propertyExpression);
			this.inverseProperties.put(propertyExpression, inversePropertyOf);
			return inversePropertyOf;
		}
	}

	public OWLClassAdapter getOWLThingClass()
	{
		try {
			URI owlThingURI = new URI(OWL_THING);
			return new OWLClassAdapterImpl(owlThingURI, "owl:Thing"); // TODO look at
		} catch (URISyntaxException e) { // Should not fail
			throw new RuntimeException("error getting owl:Thing class"); // TODO look at
		}
	}

	@Override
	public OWLObjectComplementOfAdapter getOWLObjectComplementOf(OWLClassExpressionAdapter cls)
	{
		return new OWLObjectComplementOfAdapterImpl(cls);
	}

	@Override
	public OWLObjectSomeValuesFromAdapter getOWLObjectSomeValuesFrom(OWLObjectPropertyExpressionAdapter property,
			OWLClassExpressionAdapter cls)
	{
		return new OWLObjectSomeValuesFromAdapterImpl(property, cls);
	}

	@Override
	public OWLDataSomeValuesFromAdapter getOWLDataSomeValuesFrom(OWLDataPropertyExpressionAdapter property,
			OWLDataRangeAdapter filler)
	{
		return new OWLDataSomeValuesFromAdapterImpl(property, filler);
	}

	@Override
	public OWLObjectUnionOfAdapter getOWLObjectUnionOf(Set<OWLClassExpressionAdapter> unionClasses)
	{
		return new OWLObjectUnionOfAdapterImpl(unionClasses);
	}

	@Override
	public OWLObjectIntersectionOfAdapter getOWLObjectIntersectionOf(Set<OWLClassExpressionAdapter> intersectionClasses)
	{
		return new OWLObjectIntersectionOfAdapterImpl(intersectionClasses);
	}

	@Override
	public OWLObjectOneOfAdapter getOWLObjectOneOf(Set<OWLIndividualAdapter> oneOfIndividuals)
	{
		return new OWLObjectOneOfAdapterImpl(oneOfIndividuals);
	}

	@Override
	public OWLDataExactCardinalityAdapter getOWLDataExactCardinality(OWLDataPropertyExpressionAdapter property,
			int cardinality)
	{
		return new OWLDataExactCardinalityAdapterImpl(property, cardinality);
	}

	@Override
	public OWLObjectExactCardinalityAdapter getOWLObjectExactCardinality(OWLObjectPropertyExpressionAdapter property,
			int cardinality)
	{
		return new OWLObjectExactCardinalityAdapterImpl(property, cardinality);
	}

	@Override
	public OWLDataMinCardinalityAdapter getOWLDataMinCardinality(OWLDataPropertyExpressionAdapter property,
			int cardinality)
	{
		return new OWLDataMinCardinalityAdapterImpl(property, cardinality);
	}

	@Override
	public OWLObjectMinCardinalityAdapter getOWLObjectMinCardinality(OWLObjectPropertyExpressionAdapter property,
			int cardinality)
	{
		return new OWLObjectMinCardinalityAdapterImpl(property, cardinality);
	}

	@Override
	public OWLDataMaxCardinalityAdapter getOWLDataMaxCardinality(OWLDataPropertyExpressionAdapter property,
			int cardinality)
	{
		return new OWLDataMaxCardinalityAdapterImpl(property, cardinality);
	}

	@Override
	public OWLObjectMaxCardinalityAdapter getOWLObjectMaxCardinality(OWLObjectPropertyExpressionAdapter property,
			int cardinality)
	{
		return new OWLObjectMaxCardinalityAdapterImpl(property, cardinality);
	}

	@Override
	public OWLDataHasValueAdapter getOWLDataHasValue(OWLDataPropertyExpressionAdapter property, OWLLiteralAdapter value)
	{
		return new OWLDataHasValueAdapterImpl(property, value);
	}

	@Override
	public OWLObjectHasValueAdapter getOWLObjectHasValue(OWLObjectPropertyExpressionAdapter property,
			OWLIndividualAdapter value)
	{
		return new OWLObjectHasValueAdapterImpl(property, value);
	}

	@Override
	public OWLObjectAllValuesFromAdapter getOWLObjectAllValuesFrom(OWLObjectPropertyExpressionAdapter property,
			OWLClassExpressionAdapter cls)
	{
		return new OWLObjectAllValuesFromAdapterImpl(property, cls);
	}

	@Override
	public OWLDataAllValuesFromAdapter getOWLDataAllValuesFrom(OWLDataPropertyExpressionAdapter property,
			OWLDataRangeAdapter filler)
	{
		return new OWLDataAllValuesFromAdapterImpl(property, filler);
	}

	// OWL axioms

	@Override
	public OWLDataPropertyAssertionAxiomAdapter getOWLDataPropertyAssertionAxiom(OWLIndividualAdapter subject,
			OWLDataPropertyExpressionAdapter property, OWLLiteralAdapter literal)
	{
		return new OWLDataPropertyAssertionAxiomAdapterImpl(subject, property, literal);
	}

	@Override
	public OWLObjectPropertyAssertionAxiomAdapter getOWLObjectPropertyAssertionAxiom(OWLIndividualAdapter subject,
			OWLObjectPropertyExpressionAdapter property, OWLIndividualAdapter object)
	{
		return new OWLObjectPropertyAssertionAxiomAdapterImpl(subject, property, object);
	}

	@Override
	public OWLDifferentIndividualsAxiomAdapter getOWLDifferentIndividualsAxiom(
			Set<OWLIndividualAdapter> differentIndividuals)
	{
		return new OWLDifferentIndividualsAxiomAdapterImpl(differentIndividuals);
	}

	@Override
	public OWLSameIndividualAxiomAdapter getOWLSameIndividualAxiom(Set<OWLIndividualAdapter> sameIndividuals)
	{
		return new OWLSameIndividualAxiomAdapterImpl(sameIndividuals);
	}

	@Override
	public OWLSubObjectPropertyOfAxiomAdapter getOWLSubObjectPropertyOfAxiom(
			OWLObjectPropertyExpressionAdapter subProperty, OWLObjectPropertyExpressionAdapter superProperty)
	{
		return new OWLSubObjectPropertyOfAxiomAdapterImpl(subProperty, superProperty);
	}

	@Override
	public OWLSubDataPropertyOfAxiomAdapter getOWLSubDataPropertyOfAxiom(OWLDataPropertyExpressionAdapter subProperty,
			OWLDataPropertyExpressionAdapter superProperty)
	{
		return new OWLSubDataPropertyOfAxiomAdapterImpl(subProperty, superProperty);
	}

	@Override
	public OWLEquivalentObjectPropertiesAxiomAdapter getOWLEquivalentObjectPropertiesAxiom(
			Set<OWLObjectPropertyExpressionAdapter> properties)
	{
		return new OWLEquivalentObjectPropertiesAxiomAdapterImpl(properties);
	}

	@Override
	public OWLEquivalentDataPropertiesAxiomAdapter getOWLEquivalentDataPropertiesAxiom(
			Set<OWLDataPropertyExpressionAdapter> properties)
	{
		return new OWLEquivalentDataPropertiesAxiomAdapterImpl(properties);
	}

	@Override
	public OWLDisjointObjectPropertiesAxiomAdapter getOWLDisjointObjectPropertiesAxiom(
			Set<OWLObjectPropertyExpressionAdapter> properties)
	{
		return new OWLDisjointObjectPropertiesAxiomAdapterImpl(properties);
	}

	@Override
	public OWLDisjointDataPropertiesAxiomAdapter getOWLDisjointDataPropertiesAxiom(
			Set<OWLDataPropertyExpressionAdapter> properties)
	{
		return new OWLDisjointDataPropertiesAxiomAdapterImpl(properties);
	}

	@Override
	public OWLObjectPropertyDomainAxiomAdapter getOWLObjectPropertyDomainAxiom(
			OWLObjectPropertyExpressionAdapter property, OWLClassExpressionAdapter domain)
	{
		return new OWLObjectPropertyDomainAxiomAdapterImpl(property, domain);
	}

	@Override
	public OWLDataPropertyDomainAxiomAdapter getOWLDataPropertyDomainAxiom(OWLDataPropertyExpressionAdapter property,
			OWLClassExpressionAdapter domain)
	{
		return new OWLDataPropertyDomainAxiomAdapterImpl(property, domain);
	}

	@Override
	public OWLObjectPropertyRangeAxiomAdapter getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpressionAdapter property,
			OWLClassExpressionAdapter domain)
	{
		return new OWLObjectPropertyRangeAxiomAdapterImpl(property, domain);
	}

	@Override
	public OWLDataPropertyRangeAxiomAdapter getOWLDataPropertyRangeAxiom(OWLDataPropertyExpressionAdapter property,
			OWLDataRangeAdapter range)
	{
		return new OWLDataPropertyRangeAxiomAdapterImpl(property, range);
	}

	@Override
	public OWLTransitiveObjectPropertyAxiomAdapter getOWLTransitiveObjectPropertyAxiom(
			OWLObjectPropertyExpressionAdapter property)
	{
		return new OWLTransitiveObjectPropertyAxiomAdapterImpl(property);
	}

	@Override
	public OWLSymmetricObjectPropertyAxiomAdapter getOWLSymmetricObjectPropertyAxiom(
			OWLObjectPropertyExpressionAdapter property)
	{
		return new OWLSymmetricObjectPropertyAxiomAdapterImpl(property);
	}

	@Override
	public OWLFunctionalObjectPropertyAxiomAdapter getOWLFunctionalObjectPropertyAxiom(
			OWLObjectPropertyExpressionAdapter property)
	{
		return new OWLFunctionalObjectPropertyAxiomAdapterImpl(property);
	}

	@Override
	public OWLInverseFunctionalObjectPropertyAxiomAdapter getOWLInverseFunctionalObjectPropertyAxiom(
			OWLObjectPropertyExpressionAdapter property)
	{
		return new OWLInverseFunctionalObjectPropertyAxiomAdapterImpl(property);
	}

	@Override
	public OWLInverseObjectPropertiesAxiomAdapter getOWLInverseObjectPropertiesAxiom(
			OWLObjectPropertyExpressionAdapter firstProperty, OWLObjectPropertyExpressionAdapter secondProperty)
	{
		return new OWLInverseObjectPropertiesAxiomAdapterImpl(firstProperty, secondProperty);
	}

	@Override
	public OWLIrreflexiveObjectPropertyAxiomAdapter getOWLIrreflexiveObjectPropertyAxiom(
			OWLObjectPropertyExpressionAdapter property)
	{
		return new OWLIrreflexiveObjectPropertyAxiomAdapterImpl(property);
	}

	@Override
	public OWLAsymmetricObjectPropertyAxiomAdapter getOWLAsymmetricObjectPropertyAxiom(
			OWLObjectPropertyExpressionAdapter property)
	{
		return new OWLAsymmetricObjectPropertyAxiomAdapterImpl(property);
	}

	@Override
	public OWLFunctionalDataPropertyAxiomAdapter getOWLFunctionalDataPropertyAxiom(
			OWLDataPropertyExpressionAdapter property)
	{
		return new OWLFunctionalDataPropertyAxiomAdapterImpl(property);
	}

	@Override
	public OWLClassAssertionAxiomAdapter getOWLClassAssertionAxiom(OWLIndividualAdapter individual,
			OWLClassExpressionAdapter description)
	{
		return new OWLClassAssertionAxiomAdapterImpl(individual, description);
	}

	@Override
	public OWLSubClassOfAxiomAdapter getOWLSubClassOfAxiom(OWLClassExpressionAdapter subClass,
			OWLClassExpressionAdapter superClass)
	{
		return new OWLSubClassOfAxiomAdapterImpl(subClass, superClass);
	}

	@Override
	public OWLEquivalentClassesAxiomAdapter getOWLEquivalentClassesAxiom(Set<OWLClassExpressionAdapter> equivalentClasses)
	{
		return new OWLEquivalentClassesAxiomAdapterImpl(equivalentClasses);
	}

	@Override
	public OWLDisjointClassesAxiomAdapter getOWLDisjointClassesAxiom(Set<OWLClassExpressionAdapter> disjointClasses)
	{
		return new OWLDisjointClassesAxiomAdapterImpl(disjointClasses);
	}

	@Override
	public OWLClassDeclarationAxiomAdapter getOWLClassDeclarationAxiom(OWLClassAdapter cls)
	{
		return new OWLClassDeclarationAxiomAdapterImpl(cls);
	}

	@Override
	public OWLIndividualDeclarationAxiomAdapter getOWLIndividualDeclarationAxiom(OWLNamedIndividualAdapter individual)
	{
		return new OWLIndividualDeclarationAxiomAdapterImpl(individual);
	}

	@Override
	public OWLObjectPropertyDeclarationAxiomAdapter getOWLObjectPropertyDeclarationAxiom(OWLObjectPropertyAdapter property)
	{
		return new OWLObjectPropertyDeclarationAxiomAdapterImpl(property);
	}

	@Override
	public OWLDataPropertyDeclarationAxiomAdapter getOWLDataPropertyDeclarationAxiom(OWLDataPropertyAdapter property)
	{
		return new OWLDataPropertyDeclarationAxiomAdapterImpl(property);
	}

	@Override
	public OWLAnnotationPropertyDeclarationAxiomAdapter getOWLAnnotationPropertyDeclarationAxiom(
			OWLAnnotationPropertyAdapter property)
	{
		return new OWLAnnotationPropertyDeclarationAxiomAdapterImpl(property);
	}

	@Override
	public OWLDatatypeDeclarationAxiomAdapter getOWLDatatypeDeclarationAxiom(OWLDatatypeAdapter datatype)
	{
		return new OWLDatatypeDeclarationAxiomAdapterImpl(datatype);
	}

	public OWLObjectSomeValuesFromAdapter getOWLSomeValuesFrom(OWLObjectPropertyExpressionAdapter onProperty,
			OWLClassExpressionAdapter someValuesFrom)
	{
		return new OWLObjectSomeValuesFromAdapterImpl(onProperty, someValuesFrom);
	}

	@Override
	public OWLDataOneOfAdapter getOWLDataOneOf(Set<? extends OWLLiteralAdapter> values)
	{
		return new OWLDataOneOfAdapterImpl(values);
	}

	@Override
	public OWLDataComplementOfAdapter getOWLDataComplementOf(OWLDataRangeAdapter dataRange)
	{
		return new OWLDataComplementOfAdapterImpl(dataRange);
	}

	@Override
	public OWLDataIntersectionOfAdapter getOWLDataIntersectionOf(Set<? extends OWLDataRangeAdapter> dataRanges)
	{
		return new OWLDataIntersectionOfAdapterImpl(dataRanges);
	}

	@Override
	public OWLDataUnionOfAdapter getOWLDataUnionOf(Set<? extends OWLDataRangeAdapter> dataRanges)
	{
		return new OWLDataUnionOfAdapterImpl(dataRanges);
	}

	@Override
	public SWRLAPIRule getSWRLRule(String ruleName, List<? extends SWRLAtomAdapter> bodyAtoms,
			List<? extends SWRLAtomAdapter> headAtoms)
	{
		return new DefaultSWRLAPIRule(ruleName, bodyAtoms, headAtoms);
	}

	@Override
	public SWRLClassAtomAdapter getSWRLClassAtom(OWLClassExpressionAdapter predicate, SWRLIAtomArgument arg)
	{
		return new SWRLClassAtomAdapterImpl(predicate, arg);
	}

	@Override
	public SWRLObjectPropertyAtomAdapter getSWRLObjectPropertyAtom(OWLObjectPropertyExpressionAdapter property,
			SWRLIAtomArgument arg0, SWRLIAtomArgument arg1)
	{
		return new SWRLObjectPropertyAtomAdapterImpl(property, arg0, arg1);
	}

	@Override
	public SWRLDataPropertyAtomAdapter getSWRLDataPropertyAtom(OWLDataPropertyExpressionAdapter property,
			SWRLIAtomArgument arg0, SWRLDAtomArgument arg1)
	{
		return new SWRLDataPropertyAtomAdapterImpl(property, arg0, arg1);
	}

	@Override
	public SWRLSameIndividualAtomAdapter getSWRLSameIndividualAtom(SWRLIAtomArgument arg0, SWRLIAtomArgument arg1)
	{
		return new SWRLSameIndividualAtomAdapterImpl(arg0, arg1);
	}

	@Override
	public SWRLDifferentIndividualsAtomAdapter getSWRLDifferentIndividualsAtom(SWRLIAtomArgument arg0,
			SWRLIAtomArgument arg1)
	{
		return new SWRLDifferentIndividualsAtomAdapterImpl(arg0, arg1);
	}

	@Override
	public SWRLAPIBuiltInAtom getSWRLBuiltInAtom(String ruleName, URI builtInURI, String builtInPrefixedName,
			List<SWRLBuiltInArgument> arguments)
	{
		return new DefaultSWRLAPIBuiltInAtom(ruleName, builtInURI, builtInPrefixedName, arguments);
	}

	@Override
	public SWRLVariableAtomArgument getSWRLVariableAtomArgument(String variableName)
	{
		return getSWRLAtomArgumentFactory().createVariableArgument(variableName);
	}

	@Override
	public SWRLVariableBuiltInArgument getSWRLVariableBuiltInArgument(String variableName)
	{
		return getSWRLBuiltInArgumentFactory().createVariableArgument(variableName);
	}

	@Override
	public SWRLClassAtomArgument getSWRLClassAtomArgument(OWLClassAdapter cls)
	{
		return getSWRLAtomArgumentFactory().createClassArgument(cls);
	}

	@Override
	public SWRLClassBuiltInArgument getSWRLClassBuiltInArgument(OWLClassAdapter cls)
	{
		return getSWRLBuiltInArgumentFactory().createClassArgument(cls);
	}

	@Override
	public SWRLIndividualAtomArgument getSWRLIndividualAtomArgument(OWLIndividualAdapter individual)
	{
		return getSWRLAtomArgumentFactory().createIndividualArgument(individual);
	}

	@Override
	public SWRLIndividualBuiltInArgument getSWRLIndividualBuiltInArgument(OWLIndividualAdapter individual)
	{
		return getSWRLBuiltInArgumentFactory().createIndividualArgument(individual);
	}

	@Override
	public SWRLObjectPropertyAtomArgument getSWRLObjectPropertyAtomArgument(OWLObjectPropertyAdapter property)
	{
		return getSWRLAtomArgumentFactory().createObjectPropertyArgument(property);
	}

	@Override
	public SWRLObjectPropertyBuiltInArgument getSWRLObjectPropertyBuiltInArgument(OWLObjectPropertyAdapter property)
	{
		return getSWRLBuiltInArgumentFactory().createObjectPropertyArgument(property);
	}

	@Override
	public SWRLDataPropertyAtomArgument getSWRLDataPropertyAtomArgument(OWLDataPropertyAdapter property)
	{
		return getSWRLAtomArgumentFactory().createDataPropertyArgument(property);
	}

	@Override
	public SWRLDataPropertyBuiltInArgument getSWRLDataPropertyBuiltInArgument(OWLDataPropertyAdapter property)
	{
		return getSWRLBuiltInArgumentFactory().createDataPropertyArgument(property);
	}

	@Override
	public SWRLAnnotationPropertyBuiltInArgument getSWRLAnnotationPropertyBuiltInArgument(
			OWLAnnotationPropertyAdapter property)
	{
		return getSWRLBuiltInArgumentFactory().createAnnotationPropertyArgument(property);
	}

	@Override
	public SWRLLiteralBuiltInArgument getSWRLLiteralBuiltInArgument(OWLLiteralAdapter literal)
	{
		return getSWRLBuiltInArgumentFactory().createLiteralArgument(literal);
	}

	@Override
	public SWRLLiteralAtomArgument getSWRLLiteralAtomArgument(OWLLiteralAdapter literal)
	{
		return getSWRLAtomArgumentFactory().createLiteralArgument(literal);
	}

	@Override
	public SWRLLiteralAtomArgument getSWRLLiteralAtomArgument(String lexicalValue, OWLDatatypeAdapter datatype)
	{
		OWLLiteralAdapter literal = new OWLLiteralAdapterImpl(lexicalValue, datatype);

		return getSWRLAtomArgumentFactory().createLiteralArgument(literal);
	}

	@Override
	public SWRLSameAsPredicateAdapter getSWRLSameAsPredicate()
	{
		return new SWRLSameAsPredicateAdapterImpl();
	}

	@Override
	public SWRLDifferentFromPredicateAdapter getSWRLDifferentFromPredicate()
	{
		return new SWRLDifferentFromPredicateAdapterImpl();
	}

	@Override
	public SWRLBuiltInPredicateAdapter getSWRLBuiltInPredicate(String builtInPrefixedName)
	{
		return new SWRLBuiltInPredicateAdapterImpl(builtInPrefixedName);
	}

	@Override
	public SQWRLResultValueFactory getSQWRLResultValueFactory()
	{
		return this.sqwrlResultValueFactory;
	}

	@Override
	public SWRLAtomArgumentFactory getSWRLAtomArgumentFactory()
	{
		return this.swrlAtomArgumentFactory;
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
	public SWRLAPILiteralFactory getSWRLAPILiteralFactory()
	{
		return this.swrlAPILiteralFactory;
	}

	private SWRLAPIOWLOntology getActiveOWLOntology()
	{
		return this.owlOntology;
	}
}
