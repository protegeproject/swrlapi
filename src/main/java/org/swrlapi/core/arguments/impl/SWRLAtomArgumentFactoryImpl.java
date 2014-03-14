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
	public SWRLVariableAtomArgument createVariableArgument(String variableName)
	{
		return new SWRLVariableAtomArgumentImpl(variableName);
	}

	@Override
	public SWRLClassAtomArgument createClassArgument(IRI classIRI)
	{
		return new SWRLClassAtomArgumentImpl(classIRI);
	}

	@Override
	public SWRLClassAtomArgument createClassArgument(OWLClass cls)
	{
		return new SWRLClassAtomArgumentImpl(cls);
	}

	@Override
	public SWRLObjectPropertyAtomArgument createObjectPropertyArgument(IRI propertyIRI)
	{
		return new SWRLObjectPropertyAtomArgumentImpl(propertyIRI);
	}

	@Override
	public SWRLObjectPropertyAtomArgument createObjectPropertyArgument(OWLObjectProperty property)
	{
		return new SWRLObjectPropertyAtomArgumentImpl(property);
	}

	@Override
	public SWRLDataPropertyAtomArgument createDataPropertyArgument(IRI propertyIRI)
	{
		return new SWRLDataPropertyAtomArgumentImpl(propertyIRI);
	}

	@Override
	public SWRLDataPropertyAtomArgument createDataPropertyArgument(OWLDataProperty property)
	{
		return new SWRLDataPropertyAtomArgumentImpl(property);
	}

	@Override
	public SWRLIndividualAtomArgument createIndividualArgument(IRI individualIRI)
	{
		return new SWRLIndividualAtomArgumentImpl(individualIRI);
	}

	@Override
	public SWRLIndividualAtomArgument createIndividualArgument(OWLIndividual individual)
	{
		if (individual.isNamed()) {
			OWLNamedIndividual namedIndividual = individual.asOWLNamedIndividual();
			return new SWRLIndividualAtomArgumentImpl(namedIndividual.getIRI());
		} else
			throw new RuntimeException("OWL anonymous individual atom arguments not supported by Portability API");
	}

	@Override
	public SWRLLiteralAtomArgument createLiteralArgument(OWLLiteral literal)
	{
		return new SWRLLiteralAtomArgumentImpl(literal);
	}

	@Override
	public SWRLLiteralAtomArgument createLiteralArgument(String s)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(s));
	}

	@Override
	public SWRLLiteralAtomArgument createLiteralArgument(boolean b)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(b));
	}

	@Override
	public SWRLLiteralAtomArgument createLiteralArgument(int i)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(i));
	}

	@Override
	public SWRLLiteralAtomArgument createLiteralArgument(long l)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(l));
	}

	@Override
	public SWRLLiteralAtomArgument createLiteralArgument(float f)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(f));
	}

	@Override
	public SWRLLiteralAtomArgument createLiteralArgument(double d)
	{
		return new SWRLLiteralAtomArgumentImpl(getOWLLiteralFactory().getOWLLiteral(d));
	}

	private OWLLiteralFactory getOWLLiteralFactory()
	{
		return this.owlLiteralFactory;
	}
}
