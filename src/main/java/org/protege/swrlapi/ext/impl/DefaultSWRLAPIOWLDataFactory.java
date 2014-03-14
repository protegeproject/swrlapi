package org.protege.swrlapi.ext.impl;

import org.protege.swrlapi.core.arguments.SWRLAtomArgumentFactory;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgumentFactory;
import org.protege.swrlapi.core.arguments.impl.SWRLAtomArgumentFactoryImpl;
import org.protege.swrlapi.core.arguments.impl.SWRLBuiltInArgumentFactoryImpl;
import org.protege.swrlapi.ext.OWLDatatypeFactory;
import org.protege.swrlapi.ext.OWLLiteralFactory;
import org.protege.swrlapi.ext.SWRLAPILiteralFactory;
import org.protege.swrlapi.ext.SWRLAPIOWLDataFactory;
import org.protege.swrlapi.ext.SWRLAPIRule;
import org.protege.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import org.protege.swrlapi.sqwrl.values.impl.DefaultSQWRLResultValueFactory;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class DefaultSWRLAPIOWLDataFactory extends OWLDataFactoryImpl implements SWRLAPIOWLDataFactory
{
	private static final long serialVersionUID = -7164746700364588906L;

	private final SWRLAtomArgumentFactory swrlAtomArgumentFactory;
	private final SWRLBuiltInArgumentFactory swrlBuiltInArgumentFactory;
	private final SQWRLResultValueFactory sqwrlResultValueFactory;
	private final OWLDatatypeFactory owlDatatypeFactory;
	private final OWLLiteralFactory owlLiteralFactory;
	private final SWRLAPILiteralFactory swrlAPILiteralFactory;

	public DefaultSWRLAPIOWLDataFactory()
	{
		this.swrlAtomArgumentFactory = new SWRLAtomArgumentFactoryImpl();
		this.swrlBuiltInArgumentFactory = new SWRLBuiltInArgumentFactoryImpl();
		this.sqwrlResultValueFactory = new DefaultSQWRLResultValueFactory();
		this.owlDatatypeFactory = new DefaultOWLDatatypeFactory();
		this.owlLiteralFactory = new DefaultOWLLiteralFactory(this.owlDatatypeFactory);
		this.swrlAPILiteralFactory = new DefaultSWRLAPILiteralFactory();
	}

	@Override
	public SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
	{
		return this.swrlBuiltInArgumentFactory;
	}

	@Override
	public OWLClass getOWLClass()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public OWLNamedIndividual getOWLNamedIndividual()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public SWRLAPIRule getSWRLRule(String ruleName, String ruleText)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public SQWRLResultValueFactory getSQWRLResultValueFactory()
	{
		return this.sqwrlResultValueFactory;
	}

	@Override
	public OWLDeclarationAxiom getOWLClassDeclarationAxiom(OWLClass cls)
	{
		return getOWLDeclarationAxiom(cls);
	}

	@Override
	public OWLDeclarationAxiom getOWLIndividualDeclarationAxiom(OWLNamedIndividual individual)
	{
		return getOWLDeclarationAxiom(individual);
	}

	@Override
	public OWLDeclarationAxiom getOWLObjectPropertyDeclarationAxiom(OWLObjectProperty property)
	{
		return getOWLDeclarationAxiom(property);
	}

	@Override
	public OWLDeclarationAxiom getOWLDataPropertyDeclarationAxiom(OWLDataProperty property)
	{
		return getOWLDeclarationAxiom(property);
	}

	@Override
	public OWLDeclarationAxiom getOWLAnnotationPropertyDeclarationAxiom(OWLAnnotationProperty property)
	{
		return getOWLDeclarationAxiom(property);
	}

	@Override
	public OWLDeclarationAxiom getOWLDatatypeDeclarationAxiom(OWLDatatype datatype)
	{
		return getOWLDeclarationAxiom(datatype);
	}

	@Override
	public SWRLAtomArgumentFactory getSWRLAtomArgumentFactory()
	{
		return this.swrlAtomArgumentFactory;
	}

	@Override
	public OWLDatatypeFactory getOWLDatatypeFactory()
	{
		return this.owlDatatypeFactory;
	}

	@Override
	public OWLLiteralFactory getOWLLiteralFactory()
	{
		return this.owlLiteralFactory;
	}

	@Override
	public SWRLAPILiteralFactory getSWRLAPILiteralFactory()
	{
		return this.swrlAPILiteralFactory;
	}
}
