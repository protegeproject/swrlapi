package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;
import java.util.List;

import org.protege.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgumentFactory;
import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLMultiArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.protege.swrlapi.ext.OWLDatatypeFactory;
import org.protege.swrlapi.ext.OWLLiteralFactory;
import org.protege.swrlapi.ext.impl.DefaultOWLDatatypeFactory;
import org.protege.swrlapi.ext.impl.DefaultOWLLiteralFactory;
import org.protege.swrlapi.xsd.XSDDate;
import org.protege.swrlapi.xsd.XSDDateTime;
import org.protege.swrlapi.xsd.XSDDuration;
import org.protege.swrlapi.xsd.XSDTime;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

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
	public SWRLVariableBuiltInArgument createUnboundVariableArgument(String variableName)
	{
		SWRLVariableBuiltInArgument argument = new SWRLVariableBuiltInArgumentImpl(variableName);
		argument.setUnbound();
		return argument;
	}

	@Override
	public SWRLVariableBuiltInArgument createVariableArgument(String variableName)
	{
		return new SWRLVariableBuiltInArgumentImpl(variableName);
	}

	@Override
	public SWRLClassBuiltInArgument createClassArgument(URI classURI, String prefixedName)
	{
		return new SWRLClassBuiltInArgumentImpl(classURI, prefixedName);
	}

	@Override
	public SWRLClassBuiltInArgument createClassArgument(OWLClass cls)
	{
		return new SWRLClassBuiltInArgumentImpl(cls);
	}

	@Override
	public SWRLObjectPropertyBuiltInArgument createObjectPropertyArgument(URI propertyURI, String prefixedName)
	{
		return new SWRLObjectPropertyBuiltInArgumentImpl(propertyURI, prefixedName);
	}

	@Override
	public SWRLObjectPropertyBuiltInArgument createObjectPropertyArgument(OWLObjectProperty property)
	{
		return new SWRLObjectPropertyBuiltInArgumentImpl(property);
	}

	@Override
	public SWRLDataPropertyBuiltInArgument createDataPropertyArgument(URI propertyURI, String prefixedName)
	{
		return new SWRLDataPropertyBuiltInArgumentImpl(propertyURI, prefixedName);
	}

	@Override
	public SWRLDataPropertyBuiltInArgument createDataPropertyArgument(OWLDataProperty property)
	{
		return new SWRLDataPropertyBuiltInArgumentImpl(property);
	}

	@Override
	public SWRLAnnotationPropertyBuiltInArgument createAnnotationPropertyArgument(URI propertyURI, String prefixedName)
	{
		return new SWRLAnnotationPropertyBuiltInArgumentImpl(propertyURI, prefixedName);
	}

	@Override
	public SWRLAnnotationPropertyBuiltInArgument createAnnotationPropertyArgument(OWLAnnotationProperty property)
	{
		return new SWRLAnnotationPropertyBuiltInArgumentImpl(property);
	}

	@Override
	public SWRLDatatypeBuiltInArgument createDatatypeArgument(URI uri, String prefixedName)
	{
		return new SWRLDatatypeBuiltInArgumentImpl(uri, prefixedName);
	}

	@Override
	public SWRLDatatypeBuiltInArgument createDatatypeArgument(OWLDatatype datatype)
	{
		return new SWRLDatatypeBuiltInArgumentImpl(datatype);
	}

	@Override
	public SWRLIndividualBuiltInArgument createIndividualArgument(URI individualURI, String prefixedName)
	{
		return new SWRLIndividualBuiltInArgumentImpl(individualURI, prefixedName);
	}

	@Override
	public SWRLIndividualBuiltInArgument createIndividualArgument(OWLIndividual individual)
	{
		if (individual.isNamed()) {
			OWLNamedIndividual namedIndividual = individual.asNamedIndividual();
			return new SWRLIndividualBuiltInArgumentImpl(namedIndividual.getURI(), namedIndividual.getPrefixedName());
		} else
			throw new RuntimeException("OWL anonymous individual built-in arguments not supported by Portability API");
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralArgument(OWLLiteral literal)
	{
		return new SWRLLiteralBuiltInArgumentImpl(literal);
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralArgument(String s)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(s));
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralArgument(boolean b)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(b));
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralArgument(int i)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(i));
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralArgument(long l)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(l));
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralArgument(float f)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(f));
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralArgument(double d)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(d));
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralArgument(byte b)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(b));
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralArgument(URI uri)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(uri));
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralArgument(XSDDate date)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(date));
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralArgument(XSDTime time)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(time));
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralArgument(XSDDateTime datetime)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(datetime));
	}

	@Override
	public SWRLLiteralBuiltInArgument createLiteralArgument(XSDDuration duration)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(duration));
	}

	@Override
	public SWRLMultiArgument createMultiArgument()
	{
		return new SWRLMultiArgumentImpl();
	}

	@Override
	public SWRLMultiArgument createMultiArgument(List<SWRLBuiltInArgument> arguments)
	{
		return new SWRLMultiArgumentImpl(arguments);
	}

	@Override
	public SQWRLCollectionBuiltInArgument createSQWRLCollectionArgument(String queryName, String collectionName,
			String collectionGroupID)
	{
		return new SQWRLCollectionBuiltInArgumentImpl(queryName, collectionName, collectionGroupID);
	}

	private OWLLiteralFactory getOWLLiteralFactory()
	{
		return this.owlLiteralFactory;
	}
}
