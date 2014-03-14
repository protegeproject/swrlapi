package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.ext.OWLDatatypeFactory;
import org.swrlapi.ext.OWLLiteralFactory;
import org.swrlapi.ext.impl.DefaultOWLDatatypeFactory;
import org.swrlapi.ext.impl.DefaultOWLLiteralFactory;
import org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyValue;
import org.swrlapi.sqwrl.values.SQWRLClassValue;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyValue;
import org.swrlapi.sqwrl.values.SQWRLIndividualValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLObjectPropertyValue;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import org.swrlapi.xsd.XSDDate;
import org.swrlapi.xsd.XSDDateTime;
import org.swrlapi.xsd.XSDDuration;
import org.swrlapi.xsd.XSDTime;

public class DefaultSQWRLResultValueFactory implements SQWRLResultValueFactory
{
	private final OWLDatatypeFactory owlDatatypeFactory;
	private final OWLLiteralFactory owlLiteralFactory;

	public DefaultSQWRLResultValueFactory()
	{
		this.owlDatatypeFactory = new DefaultOWLDatatypeFactory();
		this.owlLiteralFactory = new DefaultOWLLiteralFactory(this.owlDatatypeFactory);
	}

	@Override
	public SQWRLClassValue createClassValue(SWRLClassBuiltInArgument classArgument)
	{
		return createClassValue(classArgument.getIRI(), classArgument.getPrefixedName());
	}

	@Override
	public SQWRLClassValue createClassValue(IRI classIRI, String prefixedName)
	{
		return new SQWRLClassValueImpl(classIRI, prefixedName);
	}

	@Override
	public SQWRLIndividualValue createIndividualValue(SWRLIndividualBuiltInArgument individualArgument)
	{
		return createIndividualValue(individualArgument.getIRI(), individualArgument.getPrefixedName());
	}

	@Override
	public SQWRLIndividualValue createIndividualValue(IRI individualIRI, String prefixedName)
	{
		return new SQWRLIndividualValueImpl(individualIRI, prefixedName);
	}

	@Override
	public SQWRLObjectPropertyValue createObjectPropertyValue(SWRLObjectPropertyBuiltInArgument objectPropertyArgument)
	{
		return createObjectPropertyValue(objectPropertyArgument.getIRI(), objectPropertyArgument.getPrefixedName());
	}

	@Override
	public SQWRLObjectPropertyValue createObjectPropertyValue(IRI propertyIRI, String prefixedName)
	{
		return new SQWRLObjectPropertyValueImpl(propertyIRI, prefixedName);
	}

	@Override
	public SQWRLDataPropertyValue createDataPropertyValue(SWRLDataPropertyBuiltInArgument dataPropertyArgument)
	{
		return createDataPropertyValue(dataPropertyArgument.getIRI(), dataPropertyArgument.getPrefixedName());
	}

	@Override
	public SQWRLDataPropertyValue createDataPropertyValue(IRI propertyIRI, String prefixedName)
	{
		return new SQWRLDataPropertyValueImpl(propertyIRI, prefixedName);
	}

	@Override
	public SQWRLAnnotationPropertyValue createAnnotationPropertyValue(
			SWRLAnnotationPropertyBuiltInArgument annotationPropertyArgument)
	{
		return createAnnotationPropertyValue(annotationPropertyArgument.getIRI(),
				annotationPropertyArgument.getPrefixedName());
	}

	@Override
	public SQWRLAnnotationPropertyValue createAnnotationPropertyValue(IRI propertyIRI, String prefixedName)
	{
		return new SQWRLAnnotationPropertyValueImpl(propertyIRI, prefixedName);
	}

	@Override
	public SQWRLLiteralResultValue getLiteral(OWLLiteral literal)
	{
		return new SQWRLLiteralResultValueImpl(literal);
	}

	@Override
	public SQWRLLiteralResultValue getLiteral(String s)
	{
		return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(s));
	}

	@Override
	public SQWRLLiteralResultValue getLiteral(boolean b)
	{
		return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(b));
	}

	@Override
	public SQWRLLiteralResultValue getLiteral(int i)
	{
		return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(i));
	}

	@Override
	public SQWRLLiteralResultValue getLiteral(long l)
	{
		return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(l));
	}

	@Override
	public SQWRLLiteralResultValue getLiteral(float f)
	{
		return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(f));
	}

	@Override
	public SQWRLLiteralResultValue getLiteral(double d)
	{
		return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(d));
	}

	@Override
	public SQWRLLiteralResultValue getLiteral(short s)
	{
		return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(s));
	}

	@Override
	public SQWRLLiteralResultValue getLiteral(XSDTime time)
	{
		return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(time));
	}

	@Override
	public SQWRLLiteralResultValue getLiteral(XSDDate date)
	{
		return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(date));
	}

	@Override
	public SQWRLLiteralResultValue getLiteral(XSDDateTime dateTime)
	{
		return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(dateTime));
	}

	@Override
	public SQWRLLiteralResultValue createLiteralValue(XSDDuration duration)
	{
		return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(duration));
	}

	private OWLLiteralFactory getOWLLiteralFactory()
	{
		return this.owlLiteralFactory;
	}
}
