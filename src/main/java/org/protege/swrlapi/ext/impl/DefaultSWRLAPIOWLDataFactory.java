package org.protege.swrlapi.ext.impl;

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
import org.protege.swrlapi.core.arguments.impl.SWRLAtomArgumentFactoryImpl;
import org.protege.swrlapi.core.arguments.impl.SWRLBuiltInArgumentFactoryImpl;
import org.protege.swrlapi.ext.OWLDatatypeFactory;
import org.protege.swrlapi.ext.OWLLiteralFactory;
import org.protege.swrlapi.ext.SWRLAPILiteralFactory;
import org.protege.swrlapi.ext.SWRLAPIOWLDataFactory;
import org.protege.swrlapi.ext.SWRLAPIOWLOntology;
import org.protege.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import org.protege.swrlapi.sqwrl.values.impl.DefaultSQWRLResultValueFactory;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class DefaultSWRLAPIOWLDataFactory extends OWLDataFactoryImpl implements SWRLAPIOWLDataFactory
{
	private static final long serialVersionUID = -7164746700364588906L;

	private final SWRLAPIOWLOntology owlOntology;

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
		IRI classIRI = getActiveOWLOntology().generateOWLEntityIRI("INJECTED_CLASS");

		return getOWLClass(classIRI);
	}

	@Override
	public OWLNamedIndividual getOWLNamedIndividual()
	{
		IRI classIRI = getActiveOWLOntology().generateOWLEntityIRI("INJECTED_INDIVIDUAL");

		return getOWLNamedIndividual(classIRI);
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
		OWLLiteral literal = getOWLLiteralFactory().getOWLLiteral(lexicalValue, datatype);

		return getSWRLAtomArgumentFactory().createLiteralArgument(literal);
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
