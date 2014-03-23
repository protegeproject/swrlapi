package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.OWLIRIResolver;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.ext.OWLLiteralFactory;
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
	private final OWLLiteralFactory owlLiteralFactory;
	private final OWLIRIResolver iriResolver;

	public DefaultSQWRLResultValueFactory(OWLIRIResolver iriResolver, OWLLiteralFactory owlLiteralFactory)
	{
		this.owlLiteralFactory = owlLiteralFactory;
		this.iriResolver = iriResolver;
	}

	@Override
	public SQWRLClassValue getClassValue(SWRLClassBuiltInArgument classArgument)
	{
		return getClassValue(classArgument.getIRI());
	}

	@Override
	public SQWRLClassValue getClassValue(IRI classIRI)
	{
		String prefixedName = getOWLIRIResolver().iri2PrefixedName(classIRI);

		return new SQWRLClassValueImpl(classIRI, prefixedName);
	}

	@Override
	public SQWRLIndividualValue getIndividualValue(SWRLNamedIndividualBuiltInArgument individualArgument)
	{
		String prefixedName = getOWLIRIResolver().iri2PrefixedName(individualArgument.getIRI());

		return new SQWRLIndividualValueImpl(individualArgument.getIRI(), prefixedName);
	}

	@Override
	public SQWRLIndividualValue getIndividualValue(IRI individualIRI)
	{
		String prefixedName = getOWLIRIResolver().iri2PrefixedName(individualIRI);

		return new SQWRLIndividualValueImpl(individualIRI, prefixedName);
	}

	@Override
	public SQWRLObjectPropertyValue getObjectPropertyValue(SWRLObjectPropertyBuiltInArgument objectPropertyArgument)
	{
		return getObjectPropertyValue(objectPropertyArgument.getIRI());
	}

	@Override
	public SQWRLObjectPropertyValue getObjectPropertyValue(IRI propertyIRI)
	{
		String prefixedName = getOWLIRIResolver().iri2PrefixedName(propertyIRI);

		return new SQWRLObjectPropertyValueImpl(propertyIRI, prefixedName);
	}

	@Override
	public SQWRLDataPropertyValue getDataPropertyValue(SWRLDataPropertyBuiltInArgument dataPropertyArgument)
	{
		String prefixedName = getOWLIRIResolver().iri2PrefixedName(dataPropertyArgument.getIRI());

		return new SQWRLDataPropertyValueImpl(dataPropertyArgument.getIRI(), prefixedName);
	}

	@Override
	public SQWRLDataPropertyValue getDataPropertyValue(IRI propertyIRI)
	{
		String prefixedName = getOWLIRIResolver().iri2PrefixedName(propertyIRI);

		return new SQWRLDataPropertyValueImpl(propertyIRI, prefixedName);
	}

	@Override
	public SQWRLAnnotationPropertyValue getAnnotationPropertyValue(
			SWRLAnnotationPropertyBuiltInArgument annotationPropertyArgument)
	{
		String prefixedName = getOWLIRIResolver().iri2PrefixedName(annotationPropertyArgument.getIRI());

		return new SQWRLAnnotationPropertyValueImpl(annotationPropertyArgument.getIRI(), prefixedName);
	}

	@Override
	public SQWRLAnnotationPropertyValue getAnnotationPropertyValue(IRI propertyIRI)
	{
		String prefixedName = getOWLIRIResolver().iri2PrefixedName(propertyIRI);

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
	public SQWRLLiteralResultValue getLiteralValue(XSDDuration duration)
	{
		return new SQWRLLiteralResultValueImpl(getOWLLiteralFactory().getOWLLiteral(duration));
	}

	private OWLLiteralFactory getOWLLiteralFactory()
	{
		return this.owlLiteralFactory;
	}

	private OWLIRIResolver getOWLIRIResolver()
	{
		return this.iriResolver;
	}
}
