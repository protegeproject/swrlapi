package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.core.arguments.SWRLAtomArgumentFactory;
import org.swrlapi.core.arguments.SWRLClassAtomArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyAtomArgument;
import org.swrlapi.core.arguments.SWRLIndividualAtomArgument;
import org.swrlapi.core.arguments.SWRLLiteralAtomArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyAtomArgument;
import org.swrlapi.core.arguments.SWRLVariableAtomArgument;
import org.swrlapi.ext.OWLDatatypeFactory;
import org.swrlapi.ext.OWLLiteralFactory;
import org.swrlapi.ext.impl.DefaultOWLDatatypeFactory;
import org.swrlapi.ext.impl.DefaultOWLLiteralFactory;

public class SWRLAtomArgumentFactoryImpl implements SWRLAtomArgumentFactory
{
	private final OWLDatatypeFactory owlDatatypeFactory;
	private final OWLLiteralFactory owlLiteralFactory;

	public SWRLAtomArgumentFactoryImpl()
	{
		this.owlDatatypeFactory = new DefaultOWLDatatypeFactory();
		this.owlLiteralFactory = new DefaultOWLLiteralFactory(this.owlDatatypeFactory);
	}

	@Override
	public SWRLVariableAtomArgument getVariableArgument(String variableName)
	{
		return new SWRLVariableAtomArgumentImpl(variableName);
	}

	@Override
	public SWRLClassAtomArgument getClassArgument(IRI classIRI)
	{
		return new SWRLClassAtomArgumentImpl(classIRI);
	}

	@Override
	public SWRLClassAtomArgument getClassArgument(OWLClass cls)
	{
		return new SWRLClassAtomArgumentImpl(cls);
	}

	@Override
	public SWRLObjectPropertyAtomArgument getObjectPropertyArgument(IRI propertyIRI)
	{
		return new SWRLObjectPropertyAtomArgumentImpl(propertyIRI);
	}

	@Override
	public SWRLObjectPropertyAtomArgument getObjectPropertyArgument(OWLObjectProperty property)
	{
		return new SWRLObjectPropertyAtomArgumentImpl(property);
	}

	@Override
	public SWRLDataPropertyAtomArgument getDataPropertyArgument(IRI propertyIRI)
	{
		return new SWRLDataPropertyAtomArgumentImpl(propertyIRI);
	}

	@Override
	public SWRLDataPropertyAtomArgument getDataPropertyArgument(OWLDataProperty property)
	{
		return new SWRLDataPropertyAtomArgumentImpl(property);
	}

	@Override
	public SWRLIndividualAtomArgument getIndividualArgument(IRI individualIRI)
	{
		return new SWRLIndividualAtomArgumentImpl(individualIRI);
	}

	@Override
	public SWRLIndividualAtomArgument getIndividualArgument(OWLIndividual individual)
	{
		if (individual.isNamed()) {
			OWLNamedIndividual namedIndividual = individual.asOWLNamedIndividual();
			return new SWRLIndividualAtomArgumentImpl(namedIndividual.getIRI());
		} else
			throw new RuntimeException("OWL anonymous individual atom arguments not supported by Portability API");
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralArgument(OWLLiteral literal)
	{
		return new SWRLLiteralAtomArgumentImpl(literal);
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralArgument(String s)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(s));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralArgument(boolean b)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(b));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralArgument(int i)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(i));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralArgument(long l)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(l));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralArgument(float f)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(f));
	}

	@Override
	public SWRLLiteralAtomArgument getLiteralArgument(double d)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(d));
	}

	private OWLLiteralFactory getOWLLiteralFactory()
	{
		return this.owlLiteralFactory;
	}
}
