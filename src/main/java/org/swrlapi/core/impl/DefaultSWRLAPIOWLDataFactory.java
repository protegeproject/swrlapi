package org.swrlapi.core.impl;

import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLAPILiteralFactory;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLDatatypeFactory;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class DefaultSWRLAPIOWLDataFactory extends OWLDataFactoryImpl implements SWRLAPIOWLDataFactory
{
	private static final long serialVersionUID = 1L;

	private final IRIResolver iriResolver;
	private final OWLLiteralFactory owlLiteralFactory;
	private final SWRLAPIOWLDatatypeFactory swrlapiOWLDatatypeFactory;
	private final SWRLAPILiteralFactory swrlapiLiteralFactory;
	private final SWRLBuiltInArgumentFactory swrlBuiltInArgumentFactory;
	private final SQWRLResultValueFactory sqwrlResultValueFactory;

	public DefaultSWRLAPIOWLDataFactory(IRIResolver iriResolver)
	{
		this.iriResolver = iriResolver;
		this.swrlapiOWLDatatypeFactory = SWRLAPIFactory.createSWRLAPIOWLDatatypeFactory();
		this.owlLiteralFactory = SWRLAPIFactory.createOWLLiteralFactory();
		this.swrlapiLiteralFactory = SWRLAPIFactory.createSWRLAPILiteralFactory();
		this.swrlBuiltInArgumentFactory = SWRLAPIFactory.createSWRLBuiltInArgumentFactory(iriResolver);
		this.sqwrlResultValueFactory = SWRLAPIFactory.createSQWRLResultValueFactory(iriResolver);
	}

	@Override
	public SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
	{
		return this.swrlBuiltInArgumentFactory;
	}

	@Override
	public SWRLAPIBuiltInAtom getSWRLAPIBuiltInAtom(String ruleName, IRI builtInIRI, String builtInPrefixedName,
			List<SWRLBuiltInArgument> arguments)
	{
		return new DefaultSWRLAPIBuiltInAtom(ruleName, builtInIRI, builtInPrefixedName, arguments);
	}

	@Override
	public SWRLAPIRule getSWRLRule(String ruleName, String ruleText)
	{
		throw new SWRLAPIException("SWRL parser not implemented"); // TODO - yes it is!
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
	public SWRLAPIOWLDatatypeFactory getSWRLAPIOWLDatatypeFactory()
	{
		return this.swrlapiOWLDatatypeFactory;
	}

	@Override
	public OWLLiteralFactory getOWLLiteralFactory()
	{
		return this.owlLiteralFactory;
	}

	@Override
	public SWRLAPILiteralFactory getSWRLAPILiteralFactory()
	{
		return this.swrlapiLiteralFactory;
	}

	@Override
	public IRIResolver getIRIResolver()
	{
		return this.iriResolver;
	}

	@Override
	public OWLClass getInjectedOWLClass()
	{
		// TODO This is incorrect!!
		IRI iri = IRI
		// .create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + UUID.randomUUID().toString());
				.create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + "fred");

		return getOWLClass(iri);
	}

	@Override
	public OWLNamedIndividual getInjectedOWLNamedIndividual()
	{
		// TODO This is incorrect!
		IRI iri = IRI
		// .create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + UUID.randomUUID().toString());
				.create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + "fred");

		return getOWLNamedIndividual(iri);
	}
}
