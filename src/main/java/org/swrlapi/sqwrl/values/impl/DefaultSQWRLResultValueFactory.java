package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.SWRLAPIIRIResolver;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import org.swrlapi.sqwrl.values.*;
import org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue;

public class DefaultSQWRLResultValueFactory implements SQWRLResultValueFactory
{
	private final OWLLiteralFactory owlLiteralFactory;
	private final SWRLAPIIRIResolver iriResolver;

	public DefaultSQWRLResultValueFactory(SWRLAPIIRIResolver iriResolver, OWLLiteralFactory owlLiteralFactory)
	{
		this.owlLiteralFactory = owlLiteralFactory;
		this.iriResolver = iriResolver;
	}

	@Override
	public SQWRLClassResultValue getClassValue(SWRLClassBuiltInArgument classArgument)
	{
		return getClassValue(classArgument.getIRI());
	}

	@Override
	public SQWRLClassResultValue getClassValue(IRI classIRI)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(classIRI);

		return new SQWRLClassResultValueImpl(classIRI, prefixedName);
	}

	@Override
	public SQWRLIndividualResultValue getIndividualValue(SWRLNamedIndividualBuiltInArgument individualArgument)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(individualArgument.getIRI());

		return new SQWRLIndividualResultValueImpl(individualArgument.getIRI(), prefixedName);
	}

	@Override
	public SQWRLIndividualResultValue getIndividualValue(IRI individualIRI)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(individualIRI);

		return new SQWRLIndividualResultValueImpl(individualIRI, prefixedName);
	}

	@Override
	public SQWRLObjectPropertyResultValue getObjectPropertyValue(SWRLObjectPropertyBuiltInArgument objectPropertyArgument)
	{
		return getObjectPropertyValue(objectPropertyArgument.getIRI());
	}

	@Override
	public SQWRLObjectPropertyResultValue getObjectPropertyValue(IRI propertyIRI)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);

		return new SQWRLObjectPropertyResultValueImpl(propertyIRI, prefixedName);
	}

	@Override
	public SQWRLDataPropertyResultValue getDataPropertyValue(SWRLDataPropertyBuiltInArgument dataPropertyArgument)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(dataPropertyArgument.getIRI());

		return new SQWRLDataPropertyResultValueImpl(dataPropertyArgument.getIRI(), prefixedName);
	}

	@Override
	public SQWRLDataPropertyResultValue getDataPropertyValue(IRI propertyIRI)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);

		return new SQWRLDataPropertyResultValueImpl(propertyIRI, prefixedName);
	}

	@Override
	public SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(
			SWRLAnnotationPropertyBuiltInArgument annotationPropertyArgument)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(annotationPropertyArgument.getIRI());

		return new SQWRLAnnotationPropertyResultValueImpl(annotationPropertyArgument.getIRI(), prefixedName);
	}

	@Override
	public SQWRLAnnotationPropertyResultValue getAnnotationPropertyValue(IRI propertyIRI)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);

		return new SQWRLAnnotationPropertyResultValueImpl(propertyIRI, prefixedName);
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

	private SWRLAPIIRIResolver getIRIResolver()
	{
		return this.iriResolver;
	}
}
