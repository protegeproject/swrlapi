package org.protege.swrlapi.ext.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import uk.ac.manchester.cs.owl.owlapi.OWLAnnotationPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLAnonymousIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLAsymmetricObjectPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLClassAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataComplementOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataExactCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataHasValueImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataIntersectionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataMaxCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataMinCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataOneOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyDomainAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyRangeAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataSomeValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataUnionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDifferentIndividualsAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDisjointClassesAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDisjointDataPropertiesAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDisjointObjectPropertiesAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLEquivalentClassesAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLEquivalentDataPropertiesAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLEquivalentObjectPropertiesAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLFunctionalDataPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLFunctionalObjectPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLInverseFunctionalObjectPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLInverseObjectPropertiesAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLIrreflexiveObjectPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectComplementOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectExactCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectHasValueImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectIntersectionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectInverseOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectMaxCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectMinCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectOneOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyDomainAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyRangeAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectSomeValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectUnionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSameIndividualAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubDataPropertyOfAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubObjectPropertyOfAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSymmetricObjectPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLTransitiveObjectPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLClassAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLDataPropertyAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLDifferentIndividualsAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLObjectPropertyAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLSameIndividualAtomImpl;

public class DefaultSWRLAPIOWLDataFactory implements SWRLAPIOWLDataFactory
{
	private final Map<URI, OWLClass> classes = new HashMap<URI, OWLClass>();
	private final Map<URI, OWLNamedIndividual> namedIndividuals = new HashMap<URI, OWLNamedIndividual>();
	private final Map<String, OWLAnonymousIndividual> anonymousIndividuals = new HashMap<String, OWLAnonymousIndividual>();
	private final Map<URI, OWLObjectProperty> objectProperties = new HashMap<URI, OWLObjectProperty>();
	private final Map<URI, OWLDataProperty> dataProperties = new HashMap<URI, OWLDataProperty>();
	private final Map<OWLObjectPropertyExpression, OWLObjectInverseOf> inverseProperties = new HashMap<OWLObjectPropertyExpression, OWLObjectInverseOf>();
	private final Map<URI, OWLAnnotationProperty> annotationProperties = new HashMap<URI, OWLAnnotationProperty>();
	private final Map<URI, OWLDatatype> datatypes = new HashMap<URI, OWLDatatype>();

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
	public OWLClass getOWLClass()
	{
		URI classURI = getActiveOWLOntology().generateOWLEntityURI("INJECTED_CLASS");

		return getOWLClass(classURI);
	}

	@Override
	public OWLNamedIndividual getOWLNamedIndividual()
	{
		URI classURI = getActiveOWLOntology().generateOWLEntityURI("INJECTED_INDIVIDUAL");

		return getOWLNamedIndividual(classURI);
	}

	@Override
	public OWLClass getOWLClass(URI classURI)
	{
		if (this.classes.containsKey(classURI))
			return this.classes.get(classURI);

		OWLClass cls = new OWLClassImpl(classURI, getActiveOWLOntology().uri2PrefixedName(classURI));
		this.classes.put(classURI, cls);
		return cls;

	}

	@Override
	public OWLNamedIndividual getOWLNamedIndividual(URI individualURI)
	{
		if (this.namedIndividuals.containsKey(individualURI))
			return this.namedIndividuals.get(individualURI);

		OWLNamedIndividual individual = new OWLNamedIndividualImpl(individualURI, getActiveOWLOntology().uri2PrefixedName(
				individualURI));
		this.namedIndividuals.put(individualURI, individual);
		return individual;
	}

	@Override
	public OWLAnonymousIndividual getOWLAnonymousIndividual(String nodeID)
	{
		if (this.anonymousIndividuals.containsKey(nodeID))
			return this.anonymousIndividuals.get(nodeID);

		OWLAnonymousIndividual individual = new OWLAnonymousIndividualImpl(nodeID);
		this.anonymousIndividuals.put(nodeID, individual);
		return individual;
	}

	@Override
	public OWLObjectProperty getOWLObjectProperty(URI propertyURI)
	{
		if (this.objectProperties.containsKey(propertyURI))
			return this.objectProperties.get(propertyURI);

		OWLObjectProperty property = new OWLObjectPropertyImpl(propertyURI, getActiveOWLOntology().uri2PrefixedName(
				propertyURI));
		this.objectProperties.put(propertyURI, property);
		return property;
	}

	@Override
	public OWLDataProperty getOWLDataProperty(URI propertyURI)
	{
		if (this.dataProperties.containsKey(propertyURI))
			return this.dataProperties.get(propertyURI);

		OWLDataProperty property = new OWLDataPropertyImpl(propertyURI, getActiveOWLOntology()
				.uri2PrefixedName(propertyURI));
		this.dataProperties.put(propertyURI, property);
		return property;
	}

	@Override
	public OWLAnnotationProperty getOWLAnnotationProperty(URI propertyURI)
	{
		if (this.annotationProperties.containsKey(propertyURI))
			return this.annotationProperties.get(propertyURI);

		OWLAnnotationProperty property = new OWLAnnotationPropertyImpl(propertyURI, getActiveOWLOntology()
				.uri2PrefixedName(propertyURI));
		this.annotationProperties.put(propertyURI, property);
		return property;
	}

	@Override
	public OWLDatatype getOWLDatatype(URI datatypeURI)
	{
		if (this.datatypes.containsKey(datatypeURI))
			return this.datatypes.get(datatypeURI);

		OWLDatatype datatype = new OWLDatatypeImpl(datatypeURI, getActiveOWLOntology().uri2PrefixedName(datatypeURI));
		this.datatypes.put(datatypeURI, datatype);
		return datatype;

	}

	@Override
	public OWLObjectInverseOf getOWLObjectInverseOf(OWLObjectPropertyExpression propertyExpression)
	{
		if (this.inverseProperties.containsKey(propertyExpression))
			return this.inverseProperties.get(propertyExpression);
		else {
			OWLObjectInverseOf inversePropertyOf = new OWLObjectInverseOfImpl(propertyExpression);
			this.inverseProperties.put(propertyExpression, inversePropertyOf);
			return inversePropertyOf;
		}
	}

	public OWLClass getOWLThingClass()
	{
		try {
			URI owlThingURI = new URI(OWL_THING);
			return new OWLClassImpl(owlThingURI, "owl:Thing"); // TODO look at
		} catch (URISyntaxException e) { // Should not fail
			throw new RuntimeException("error getting owl:Thing class"); // TODO look at
		}
	}

	@Override
	public OWLObjectComplementOf getOWLObjectComplementOf(OWLClassExpression cls)
	{
		return new OWLObjectComplementOfImpl(cls);
	}

	@Override
	public OWLObjectSomeValuesFrom getOWLObjectSomeValuesFrom(OWLObjectPropertyExpression property, OWLClassExpression cls)
	{
		return new OWLObjectSomeValuesFromImpl(property, cls);
	}

	@Override
	public OWLDataSomeValuesFrom getOWLDataSomeValuesFrom(OWLDataPropertyExpression property, OWLDataRange filler)
	{
		return new OWLDataSomeValuesFromImpl(property, filler);
	}

	@Override
	public OWLObjectUnionOf getOWLObjectUnionOf(Set<OWLClassExpression> unionClasses)
	{
		return new OWLObjectUnionOfImpl(unionClasses);
	}

	@Override
	public OWLObjectIntersectionOf getOWLObjectIntersectionOf(Set<OWLClassExpression> intersectionClasses)
	{
		return new OWLObjectIntersectionOfImpl(intersectionClasses);
	}

	@Override
	public OWLObjectOneOf getOWLObjectOneOf(Set<OWLIndividual> oneOfIndividuals)
	{
		return new OWLObjectOneOfImpl(oneOfIndividuals);
	}

	@Override
	public OWLDataExactCardinality getOWLDataExactCardinality(OWLDataPropertyExpression property, int cardinality)
	{
		return new OWLDataExactCardinalityImpl(property, cardinality);
	}

	@Override
	public OWLObjectExactCardinality getOWLObjectExactCardinality(OWLObjectPropertyExpression property, int cardinality)
	{
		return new OWLObjectExactCardinalityImpl(property, cardinality);
	}

	@Override
	public OWLDataMinCardinality getOWLDataMinCardinality(OWLDataPropertyExpression property, int cardinality)
	{
		return new OWLDataMinCardinalityImpl(property, cardinality);
	}

	@Override
	public OWLObjectMinCardinality getOWLObjectMinCardinality(OWLObjectPropertyExpression property, int cardinality)
	{
		return new OWLObjectMinCardinalityImpl(property, cardinality);
	}

	@Override
	public OWLDataMaxCardinality getOWLDataMaxCardinality(OWLDataPropertyExpression property, int cardinality)
	{
		return new OWLDataMaxCardinalityImpl(property, cardinality);
	}

	@Override
	public OWLObjectMaxCardinality getOWLObjectMaxCardinality(OWLObjectPropertyExpression property, int cardinality)
	{
		return new OWLObjectMaxCardinalityImpl(property, cardinality);
	}

	@Override
	public OWLDataHasValue getOWLDataHasValue(OWLDataPropertyExpression property, OWLLiteral value)
	{
		return new OWLDataHasValueImpl(property, value);
	}

	@Override
	public OWLObjectHasValue getOWLObjectHasValue(OWLObjectPropertyExpression property, OWLIndividual value)
	{
		return new OWLObjectHasValueImpl(property, value);
	}

	@Override
	public OWLObjectAllValuesFrom getOWLObjectAllValuesFrom(OWLObjectPropertyExpression property, OWLClassExpression cls)
	{
		return new OWLObjectAllValuesFromImpl(property, cls);
	}

	@Override
	public OWLDataAllValuesFrom getOWLDataAllValuesFrom(OWLDataPropertyExpression property, OWLDataRange filler)
	{
		return new OWLDataAllValuesFromImpl(property, filler);
	}

	// OWL axioms

	@Override
	public OWLDataPropertyAssertionAxiom getOWLDataPropertyAssertionAxiom(OWLIndividual subject,
			OWLDataPropertyExpression property, OWLLiteral literal)
	{
		return new OWLDataPropertyAssertionAxiomImpl(subject, property, literal);
	}

	@Override
	public OWLObjectPropertyAssertionAxiom getOWLObjectPropertyAssertionAxiom(OWLIndividual subject,
			OWLObjectPropertyExpression property, OWLIndividual object)
	{
		return new OWLObjectPropertyAssertionAxiomImpl(subject, property, object);
	}

	@Override
	public OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(Set<OWLIndividual> differentIndividuals)
	{
		return new OWLDifferentIndividualsAxiomImpl(differentIndividuals);
	}

	@Override
	public OWLSameIndividualAxiom getOWLSameIndividualAxiom(Set<OWLIndividual> sameIndividuals)
	{
		return new OWLSameIndividualAxiomImpl(sameIndividuals);
	}

	@Override
	public OWLSubObjectPropertyOfAxiom getOWLSubObjectPropertyOfAxiom(OWLObjectPropertyExpression subProperty,
			OWLObjectPropertyExpression superProperty)
	{
		return new OWLSubObjectPropertyOfAxiomImpl(subProperty, superProperty);
	}

	@Override
	public OWLSubDataPropertyOfAxiom getOWLSubDataPropertyOfAxiom(OWLDataPropertyExpression subProperty,
			OWLDataPropertyExpression superProperty)
	{
		return new OWLSubDataPropertyOfAxiomImpl(subProperty, superProperty);
	}

	@Override
	public OWLEquivalentObjectPropertiesAxiom getOWLEquivalentObjectPropertiesAxiom(
			Set<OWLObjectPropertyExpression> properties)
	{
		return new OWLEquivalentObjectPropertiesAxiomImpl(properties);
	}

	@Override
	public OWLEquivalentDataPropertiesAxiom getOWLEquivalentDataPropertiesAxiom(Set<OWLDataPropertyExpression> properties)
	{
		return new OWLEquivalentDataPropertiesAxiomImpl(properties);
	}

	@Override
	public OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(
			Set<OWLObjectPropertyExpression> properties)
	{
		return new OWLDisjointObjectPropertiesAxiomImpl(properties);
	}

	@Override
	public OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(Set<OWLDataPropertyExpression> properties)
	{
		return new OWLDisjointDataPropertiesAxiomImpl(properties);
	}

	@Override
	public OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(OWLObjectPropertyExpression property,
			OWLClassExpression domain)
	{
		return new OWLObjectPropertyDomainAxiomImpl(property, domain);
	}

	@Override
	public OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property,
			OWLClassExpression domain)
	{
		return new OWLDataPropertyDomainAxiomImpl(property, domain);
	}

	@Override
	public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom(OWLObjectPropertyExpression property,
			OWLClassExpression domain)
	{
		return new OWLObjectPropertyRangeAxiomImpl(property, domain);
	}

	@Override
	public OWLDataPropertyRangeAxiom getOWLDataPropertyRangeAxiom(OWLDataPropertyExpression property, OWLDataRange range)
	{
		return new OWLDataPropertyRangeAxiomImpl(property, range);
	}

	@Override
	public OWLTransitiveObjectPropertyAxiom getOWLTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property)
	{
		return new OWLTransitiveObjectPropertyAxiomImpl(property);
	}

	@Override
	public OWLSymmetricObjectPropertyAxiom getOWLSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property)
	{
		return new OWLSymmetricObjectPropertyAxiomImpl(property);
	}

	@Override
	public OWLFunctionalObjectPropertyAxiom getOWLFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property)
	{
		return new OWLFunctionalObjectPropertyAxiomImpl(property);
	}

	@Override
	public OWLInverseFunctionalObjectPropertyAxiom getOWLInverseFunctionalObjectPropertyAxiom(
			OWLObjectPropertyExpression property)
	{
		return new OWLInverseFunctionalObjectPropertyAxiomImpl(property);
	}

	@Override
	public OWLInverseObjectPropertiesAxiom getOWLInverseObjectPropertiesAxiom(OWLObjectPropertyExpression firstProperty,
			OWLObjectPropertyExpression secondProperty)
	{
		return new OWLInverseObjectPropertiesAxiomImpl(firstProperty, secondProperty);
	}

	@Override
	public OWLIrreflexiveObjectPropertyAxiom getOWLIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property)
	{
		return new OWLIrreflexiveObjectPropertyAxiomImpl(property);
	}

	@Override
	public OWLAsymmetricObjectPropertyAxiom getOWLAsymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property)
	{
		return new OWLAsymmetricObjectPropertyAxiomImpl(property);
	}

	@Override
	public OWLFunctionalDataPropertyAxiom getOWLFunctionalDataPropertyAxiom(OWLDataPropertyExpression property)
	{
		return new OWLFunctionalDataPropertyAxiomImpl(property);
	}

	@Override
	public OWLClassAssertionAxiom getOWLClassAssertionAxiom(OWLIndividual individual, OWLClassExpression description)
	{
		return new OWLClassAssertionAxiomImpl(individual, description);
	}

	@Override
	public OWLSubClassOfAxiom getOWLSubClassOfAxiom(OWLClassExpression subClass, OWLClassExpression superClass)
	{
		return new OWLSubClassOfAxiomImpl(subClass, superClass);
	}

	@Override
	public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom(Set<OWLClassExpression> equivalentClasses)
	{
		return new OWLEquivalentClassesAxiomImpl(equivalentClasses);
	}

	@Override
	public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(Set<OWLClassExpression> disjointClasses)
	{
		return new OWLDisjointClassesAxiomImpl(disjointClasses);
	}

	@Override
	public OWLClassDeclarationAxiom getOWLClassDeclarationAxiom(OWLClass cls)
	{
		return new OWLClassDeclarationAxiomImpl(cls);
	}

	@Override
	public OWLIndividualDeclarationAxiom getOWLIndividualDeclarationAxiom(OWLNamedIndividual individual)
	{
		return new OWLIndividualDeclarationAxiomImpl(individual);
	}

	@Override
	public OWLObjectPropertyDeclarationAxiom getOWLObjectPropertyDeclarationAxiom(OWLObjectProperty property)
	{
		return new OWLObjectPropertyDeclarationAxiomImpl(property);
	}

	@Override
	public OWLDataPropertyDeclarationAxiom getOWLDataPropertyDeclarationAxiom(OWLDataProperty property)
	{
		return new OWLDataPropertyDeclarationAxiomImpl(property);
	}

	@Override
	public OWLAnnotationPropertyDeclarationAxiom getOWLAnnotationPropertyDeclarationAxiom(OWLAnnotationProperty property)
	{
		return new OWLAnnotationPropertyDeclarationAxiomImpl(property);
	}

	@Override
	public OWLDatatypeDeclarationAxiom getOWLDatatypeDeclarationAxiom(OWLDatatype datatype)
	{
		return new OWLDatatypeDeclarationAxiomImpl(datatype);
	}

	public OWLObjectSomeValuesFrom getOWLSomeValuesFrom(OWLObjectPropertyExpression onProperty,
			OWLClassExpression someValuesFrom)
	{
		return new OWLObjectSomeValuesFromImpl(onProperty, someValuesFrom);
	}

	@Override
	public OWLDataOneOf getOWLDataOneOf(Set<? extends OWLLiteral> values)
	{
		return new OWLDataOneOfImpl(values);
	}

	@Override
	public OWLDataComplementOf getOWLDataComplementOf(OWLDataRange dataRange)
	{
		return new OWLDataComplementOfImpl(dataRange);
	}

	@Override
	public OWLDataIntersectionOf getOWLDataIntersectionOf(Set<? extends OWLDataRange> dataRanges)
	{
		return new OWLDataIntersectionOfImpl(dataRanges);
	}

	@Override
	public OWLDataUnionOf getOWLDataUnionOf(Set<? extends OWLDataRange> dataRanges)
	{
		return new OWLDataUnionOfImpl(dataRanges);
	}

	@Override
	public SWRLAPIRule getSWRLRule(String ruleName, List<? extends SWRLAtom> bodyAtoms, List<? extends SWRLAtom> headAtoms)
	{
		return new DefaultSWRLAPIRule(ruleName, bodyAtoms, headAtoms);
	}

	@Override
	public SWRLClassAtom getSWRLClassAtom(OWLClassExpression predicate, SWRLIAtomArgument arg)
	{
		return new SWRLClassAtomImpl(predicate, arg);
	}

	@Override
	public SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(OWLObjectPropertyExpression property, SWRLIAtomArgument arg0,
			SWRLIAtomArgument arg1)
	{
		return new SWRLObjectPropertyAtomImpl(property, arg0, arg1);
	}

	@Override
	public SWRLDataPropertyAtom getSWRLDataPropertyAtom(OWLDataPropertyExpression property, SWRLIAtomArgument arg0,
			SWRLDAtomArgument arg1)
	{
		return new SWRLDataPropertyAtomImpl(property, arg0, arg1);
	}

	@Override
	public SWRLSameIndividualAtom getSWRLSameIndividualAtom(SWRLIAtomArgument arg0, SWRLIAtomArgument arg1)
	{
		return new SWRLSameIndividualAtomImpl(arg0, arg1);
	}

	@Override
	public SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(SWRLIAtomArgument arg0, SWRLIAtomArgument arg1)
	{
		return new SWRLDifferentIndividualsAtomImpl(arg0, arg1);
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
	public SWRLClassAtomArgument getSWRLClassAtomArgument(OWLClass cls)
	{
		return getSWRLAtomArgumentFactory().createClassArgument(cls);
	}

	@Override
	public SWRLClassBuiltInArgument getSWRLClassBuiltInArgument(OWLClass cls)
	{
		return getSWRLBuiltInArgumentFactory().createClassArgument(cls);
	}

	@Override
	public SWRLIndividualAtomArgument getSWRLIndividualAtomArgument(OWLIndividual individual)
	{
		return getSWRLAtomArgumentFactory().createIndividualArgument(individual);
	}

	@Override
	public SWRLIndividualBuiltInArgument getSWRLIndividualBuiltInArgument(OWLIndividual individual)
	{
		return getSWRLBuiltInArgumentFactory().createIndividualArgument(individual);
	}

	@Override
	public SWRLObjectPropertyAtomArgument getSWRLObjectPropertyAtomArgument(OWLObjectProperty property)
	{
		return getSWRLAtomArgumentFactory().createObjectPropertyArgument(property);
	}

	@Override
	public SWRLObjectPropertyBuiltInArgument getSWRLObjectPropertyBuiltInArgument(OWLObjectProperty property)
	{
		return getSWRLBuiltInArgumentFactory().createObjectPropertyArgument(property);
	}

	@Override
	public SWRLDataPropertyAtomArgument getSWRLDataPropertyAtomArgument(OWLDataProperty property)
	{
		return getSWRLAtomArgumentFactory().createDataPropertyArgument(property);
	}

	@Override
	public SWRLDataPropertyBuiltInArgument getSWRLDataPropertyBuiltInArgument(OWLDataProperty property)
	{
		return getSWRLBuiltInArgumentFactory().createDataPropertyArgument(property);
	}

	@Override
	public SWRLAnnotationPropertyBuiltInArgument getSWRLAnnotationPropertyBuiltInArgument(OWLAnnotationProperty property)
	{
		return getSWRLBuiltInArgumentFactory().createAnnotationPropertyArgument(property);
	}

	@Override
	public SWRLLiteralBuiltInArgument getSWRLLiteralBuiltInArgument(OWLLiteral literal)
	{
		return getSWRLBuiltInArgumentFactory().createLiteralArgument(literal);
	}

	@Override
	public SWRLLiteralAtomArgument getSWRLLiteralAtomArgument(OWLLiteral literal)
	{
		return getSWRLAtomArgumentFactory().createLiteralArgument(literal);
	}

	@Override
	public SWRLLiteralAtomArgument getSWRLLiteralAtomArgument(String lexicalValue, OWLDatatype datatype)
	{
		OWLLiteral literal = new OWLLiteralImpl(lexicalValue, datatype);

		return getSWRLAtomArgumentFactory().createLiteralArgument(literal);
	}

	@Override
	public SWRLSameAsPredicate getSWRLSameAsPredicate()
	{
		return new SWRLSameAsPredicateImpl();
	}

	@Override
	public SWRLDifferentFromPredicate getSWRLDifferentFromPredicate()
	{
		return new SWRLDifferentFromPredicateImpl();
	}

	@Override
	public SWRLBuiltInPredicate getSWRLBuiltInPredicate(String builtInPrefixedName)
	{
		return new SWRLBuiltInPredicateImpl(builtInPrefixedName);
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
