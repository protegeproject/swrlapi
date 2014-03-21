package org.swrlapi.core.arguments.impl;

import java.net.URI;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyAtomArgument;
import org.swrlapi.core.arguments.SWRLAtomArgumentFactory;
import org.swrlapi.core.arguments.SWRLClassAtomArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyAtomArgument;
import org.swrlapi.core.arguments.SWRLIndividualAtomArgument;
import org.swrlapi.core.arguments.SWRLLiteralAtomArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyAtomArgument;
import org.swrlapi.core.arguments.SWRLVariableAtomArgument;
import org.swrlapi.ext.OWLLiteralFactory;
import org.swrlapi.xsd.XSDDate;
import org.swrlapi.xsd.XSDDateTime;
import org.swrlapi.xsd.XSDDuration;
import org.swrlapi.xsd.XSDTime;

public class DefaultSWRLAtomArgumentFactory implements SWRLAtomArgumentFactory
{
	private final OWLLiteralFactory owlLiteralFactory;

	public DefaultSWRLAtomArgumentFactory(OWLLiteralFactory owlLiteralFactory)
	{
		this.owlLiteralFactory = owlLiteralFactory;
	}

	@Override
	public SWRLVariableAtomArgument getVariableAtomArgument(String variableName)
	{
		return new SWRLVariableAtomArgumentImpl(variableName);
	}

	@Override
	public SWRLClassAtomArgument getClassAtomArgument(IRI classIRI)
	{
		return new SWRLClassAtomArgumentImpl(classIRI);
	}

	@Override
	public SWRLClassAtomArgument getClassAtomArgument(OWLClass cls)
	{
		return new SWRLClassAtomArgumentImpl(cls);
	}

	@Override
	public SWRLObjectPropertyAtomArgument getObjectPropertyAtomArgument(IRI propertyIRI)
	{
		return new SWRLObjectPropertyAtomArgumentImpl(propertyIRI);
	}

	@Override
	public SWRLObjectPropertyAtomArgument getObjectPropertyAtomArgument(OWLObjectProperty property)
	{
		return new SWRLObjectPropertyAtomArgumentImpl(property);
	}

	@Override
	public SWRLDataPropertyAtomArgument getDataPropertyAtomArgument(IRI propertyIRI)
	{
		return new SWRLDataPropertyAtomArgumentImpl(propertyIRI);
	}

	@Override
	public SWRLDataPropertyAtomArgument getDataPropertyAtomArgument(OWLDataProperty property)
	{
		return new SWRLDataPropertyAtomArgumentImpl(property);
	}

	@Override
	public SWRLAnnotationPropertyAtomArgument getAnnotationPropertyAtomArgument(IRI propertyIRI)
	{
		return new SWRLAnnotationPropertyAtomArgumentImpl(propertyIRI);
	}

	@Override
	public SWRLAnnotationPropertyAtomArgument getAnnotationPropertyAtomArgument(OWLAnnotationProperty property)
	{
		return new SWRLAnnotationPropertyAtomArgumentImpl(property);
	}

	@Override
	public SWRLIndividualAtomArgument getIndividualAtomArgument(IRI individualIRI)
	{
		return new SWRLIndividualAtomArgumentImpl(individualIRI);
	}

	@Override
	public SWRLIndividualAtomArgument getIndividualAtomArgument(OWLIndividual individual)
	{
		if (individual.isNamed()) {
			OWLNamedIndividual namedIndividual = individual.asOWLNamedIndividual();
			return new SWRLIndividualAtomArgumentImpl(namedIndividual.getIRI());
		} else
			throw new RuntimeException("OWL anonymous individual atom arguments not supported by Portability API");
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralAtomArgument(OWLLiteral literal)
	{
		return new SWRLLiteralAtomArgumentImpl(literal);
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralAtomArgument(String s)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(s));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralAtomArgument(boolean b)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(b));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralAtomArgument(byte b)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(b));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralAtomArgument(short s)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(s));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralAtomArgument(URI uri)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(uri));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralAtomArgument(int i)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(i));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralAtomArgument(long l)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(l));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralAtomArgument(float f)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(f));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralAtomArgument(double d)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(d));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralAtomArgument(XSDDate date)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(date));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralAtomArgument(XSDTime time)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(time));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralAtomArgument(XSDDateTime datetime)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(datetime));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralAtomArgument(XSDDuration duration)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(duration));
	}

	private OWLLiteralFactory getOWLLiteralFactory()
	{
		return this.owlLiteralFactory;
	}
}
