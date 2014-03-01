package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLAtomArgumentFactory;
import org.protege.swrlapi.core.arguments.SWRLClassAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLVariableAtomArgument;
import org.protege.swrlapi.ext.OWLDatatypeFactory;
import org.protege.swrlapi.ext.OWLLiteralFactory;
import org.protege.swrlapi.ext.impl.DefaultOWLDatatypeFactory;
import org.protege.swrlapi.ext.impl.DefaultOWLLiteralFactory;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

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
	public SWRLClassAtomArgument createClassArgument(URI classURI, String prefixedName)
	{
		return new SWRLClassAtomArgumentImpl(classURI, prefixedName);
	}

	@Override
	public SWRLClassAtomArgument createClassArgument(OWLClass cls)
	{
		return new SWRLClassAtomArgumentImpl(cls);
	}

	@Override
	public SWRLObjectPropertyAtomArgument createObjectPropertyArgument(URI propertyURI, String prefixedName)
	{
		return new SWRLObjectPropertyAtomArgumentImpl(propertyURI, prefixedName);
	}

	@Override
	public SWRLObjectPropertyAtomArgument createObjectPropertyArgument(OWLObjectProperty property)
	{
		return new SWRLObjectPropertyAtomArgumentImpl(property);
	}

	@Override
	public SWRLDataPropertyAtomArgument createDataPropertyArgument(URI propertyURI, String prefixedName)
	{
		return new SWRLDataPropertyAtomArgumentImpl(propertyURI, prefixedName);
	}

	@Override
	public SWRLDataPropertyAtomArgument createDataPropertyArgument(OWLDataProperty property)
	{
		return new SWRLDataPropertyAtomArgumentImpl(property);
	}

	@Override
	public SWRLIndividualAtomArgument createIndividualArgument(URI individualURI, String prefixedName)
	{
		return new SWRLIndividualAtomArgumentImpl(individualURI, prefixedName);
	}

	@Override
	public SWRLIndividualAtomArgument createIndividualArgument(OWLIndividual individual)
	{
		if (individual.isNamed()) {
			OWLNamedIndividual namedIndividual = individual.asNamedIndividual();
			return new SWRLIndividualAtomArgumentImpl(namedIndividual.getURI(), namedIndividual.getPrefixedName());
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
