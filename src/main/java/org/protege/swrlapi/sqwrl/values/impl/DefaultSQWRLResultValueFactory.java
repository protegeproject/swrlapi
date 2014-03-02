package org.protege.swrlapi.sqwrl.values.impl;

import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.protege.swrlapi.ext.OWLDatatypeFactory;
import org.protege.swrlapi.ext.OWLLiteralFactory;
import org.protege.swrlapi.ext.impl.DefaultOWLDatatypeFactory;
import org.protege.swrlapi.ext.impl.DefaultOWLLiteralFactory;
import org.protege.swrlapi.sqwrl.values.SQWRLAnnotationPropertyValue;
import org.protege.swrlapi.sqwrl.values.SQWRLClassValue;
import org.protege.swrlapi.sqwrl.values.SQWRLDataPropertyValue;
import org.protege.swrlapi.sqwrl.values.SQWRLIndividualValue;
import org.protege.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.protege.swrlapi.sqwrl.values.SQWRLObjectPropertyValue;
import org.protege.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import org.protege.swrlapi.xsd.XSDDate;
import org.protege.swrlapi.xsd.XSDDateTime;
import org.protege.swrlapi.xsd.XSDDuration;
import org.protege.swrlapi.xsd.XSDTime;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;

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
