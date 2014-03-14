package org.swrlapi.core.arguments.impl;

import java.net.URI;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLMultiArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.ext.OWLDatatypeFactory;
import org.swrlapi.ext.OWLLiteralFactory;
import org.swrlapi.ext.impl.DefaultOWLDatatypeFactory;
import org.swrlapi.ext.impl.DefaultOWLLiteralFactory;
import org.swrlapi.xsd.XSDDate;
import org.swrlapi.xsd.XSDDateTime;
import org.swrlapi.xsd.XSDDuration;
import org.swrlapi.xsd.XSDTime;

public class SWRLBuiltInArgumentFactoryImpl implements SWRLBuiltInArgumentFactory
{
	private final OWLDatatypeFactory owlDatatypeFactory;
	private final OWLLiteralFactory owlLiteralFactory;

	public SWRLBuiltInArgumentFactoryImpl()
	{
		this.owlDatatypeFactory = new DefaultOWLDatatypeFactory();
		this.owlLiteralFactory = new DefaultOWLLiteralFactory(this.owlDatatypeFactory);
	}

	@Override
	public SWRLVariableBuiltInArgument getUnboundVariableArgument(String variableName)
	{
		SWRLVariableBuiltInArgument argument = new SWRLVariableBuiltInArgumentImpl(variableName);
		argument.setUnbound();
		return argument;
	}

	@Override
	public SWRLVariableBuiltInArgument getVariableArgument(String variableName)
	{
		return new SWRLVariableBuiltInArgumentImpl(variableName);
	}

	@Override
	public SWRLClassBuiltInArgument getClassArgument(IRI classIRI)
	{
		return new SWRLClassBuiltInArgumentImpl(classIRI);
	}

	@Override
	public SWRLClassBuiltInArgument getClassArgument(OWLClass cls)
	{
		return new SWRLClassBuiltInArgumentImpl(cls);
	}

	@Override
	public SWRLObjectPropertyBuiltInArgument getObjectPropertyArgument(IRI propertyIRI)
	{
		return new SWRLObjectPropertyBuiltInArgumentImpl(propertyIRI);
	}

	@Override
	public SWRLObjectPropertyBuiltInArgument getObjectPropertyArgument(OWLObjectProperty property)
	{
		return new SWRLObjectPropertyBuiltInArgumentImpl(property);
	}

	@Override
	public SWRLDataPropertyBuiltInArgument getDataPropertyArgument(IRI propertyIRI)
	{
		return new SWRLDataPropertyBuiltInArgumentImpl(propertyIRI);
	}

	@Override
	public SWRLDataPropertyBuiltInArgument getDataPropertyArgument(OWLDataProperty property)
	{
		return new SWRLDataPropertyBuiltInArgumentImpl(property);
	}

	@Override
	public SWRLAnnotationPropertyBuiltInArgument getAnnotationPropertyArgument(IRI propertyIRI)
	{
		return new SWRLAnnotationPropertyBuiltInArgumentImpl(propertyIRI);
	}

	@Override
	public SWRLAnnotationPropertyBuiltInArgument getAnnotationPropertyArgument(OWLAnnotationProperty property)
	{
		return new SWRLAnnotationPropertyBuiltInArgumentImpl(property);
	}

	@Override
	public SWRLDatatypeBuiltInArgument getDatatypeArgument(IRI iri)
	{
		return new SWRLDatatypeBuiltInArgumentImpl(iri);
	}

	@Override
	public SWRLDatatypeBuiltInArgument getDatatypeArgument(OWLDatatype datatype)
	{
		return new SWRLDatatypeBuiltInArgumentImpl(datatype);
	}

	@Override
	public SWRLIndividualBuiltInArgument getIndividualArgument(IRI individualIRI)
	{
		return new SWRLIndividualBuiltInArgumentImpl(individualIRI);
	}

	@Override
	public SWRLIndividualBuiltInArgument getIndividualArgument(OWLIndividual individual)
	{
		if (individual.isNamed()) {
			OWLNamedIndividual namedIndividual = individual.asOWLNamedIndividual();
			return new SWRLIndividualBuiltInArgumentImpl(namedIndividual.getIRI());
		} else
			throw new RuntimeException("OWL anonymous individual built-in arguments not supported by Portability API");
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralArgument(OWLLiteral literal)
	{
		return new SWRLLiteralBuiltInArgumentImpl(literal);
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralArgument(String s)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(s));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralArgument(boolean b)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(b));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralArgument(int i)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(i));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralArgument(long l)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(l));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralArgument(float f)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(f));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralArgument(double d)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(d));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralArgument(byte b)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(b));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralArgument(URI uri)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(uri));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralArgument(XSDDate date)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(date));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralArgument(XSDTime time)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(time));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralArgument(XSDDateTime datetime)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(datetime));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralArgument(XSDDuration duration)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(duration));
	}

	@Override
	public SWRLMultiArgument getMultiArgument()
	{
		return new SWRLMultiArgumentImpl();
	}

	@Override
	public SWRLMultiArgument getMultiArgument(List<SWRLBuiltInArgument> arguments)
	{
		return new SWRLMultiArgumentImpl(arguments);
	}

	@Override
	public SQWRLCollectionBuiltInArgument getSQWRLCollectionArgument(String queryName, String collectionName,
			String collectionGroupID)
	{
		return new SQWRLCollectionBuiltInArgumentImpl(queryName, collectionName, collectionGroupID);
	}

	private OWLLiteralFactory getOWLLiteralFactory()
	{
		return this.owlLiteralFactory;
	}
}
