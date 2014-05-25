package org.swrlapi.core;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.builtins.arguments.impl.DefaultSWRLBuiltInArgumentFactory;
import org.swrlapi.core.impl.DefaultOWLLiteralFactory;
import org.swrlapi.core.impl.DefaultSWRLAPILiteralFactory;
import org.swrlapi.core.impl.DefaultSWRLAPIOWLDataFactory;
import org.swrlapi.core.impl.DefaultSWRLAPIOWLDatatypeFactory;
import org.swrlapi.core.impl.DefaultSWRLAPIOWLOntology;
import org.swrlapi.core.impl.DefaultSWRLAPIOntologyProcessor;
import org.swrlapi.core.impl.DefaultSWRLRuleEngineFactory;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import org.swrlapi.sqwrl.values.impl.DefaultSQWRLResultValueFactory;

public class SWRLAPIFactory
{
	public static SWRLAPIOWLOntology createSWRLAPIOWLOntology(OWLOntologyManager ontologyManager,
			OWLOntology owlOntology, DefaultPrefixManager prefixManager)
	{
		return new DefaultSWRLAPIOWLOntology(ontologyManager, owlOntology, prefixManager);
	}

	public static SWRLAPIOntologyProcessor createSWRLAPIOntologyProcessor(SWRLAPIOWLOntology swrlapiOWLOntology)
	{
		return new DefaultSWRLAPIOntologyProcessor(swrlapiOWLOntology);
	}

	public static SWRLRuleEngineFactory createSWRLRuleEngineFactory()
	{
		return new DefaultSWRLRuleEngineFactory();
	}

	public static SWRLAPIOWLDataFactory createSWRLAPIOWLDataFactory(OWLIRIResolver owlIRIResolver)
	{
		return new DefaultSWRLAPIOWLDataFactory(owlIRIResolver);
	}

	public static SWRLAPIOWLDatatypeFactory createSWRLAPIOWLDatatypeFactory()
	{
		return new DefaultSWRLAPIOWLDatatypeFactory();
	}

	public static OWLLiteralFactory createOWLLiteralFactory(SWRLAPIOWLDatatypeFactory owlDatatypeFactory)
	{
		return new DefaultOWLLiteralFactory(owlDatatypeFactory);
	}

	public static SWRLAPILiteralFactory createSWRLAPILiteralFactory(OWLLiteralFactory owlLiteralFactory)
	{
		return new DefaultSWRLAPILiteralFactory(owlLiteralFactory);
	}

	public static SWRLBuiltInArgumentFactory createSWRLBuiltInArgumentFactory(OWLIRIResolver owlIRIResolver,
			OWLLiteralFactory owlLiteralFactory)
	{
		return new DefaultSWRLBuiltInArgumentFactory(owlIRIResolver, owlLiteralFactory);
	}

	public static SQWRLResultValueFactory createSQWRLResultValueFactory(OWLIRIResolver owlIRIResolver,
			OWLLiteralFactory owlLiteralFactory)
	{
		return new DefaultSQWRLResultValueFactory(owlIRIResolver, owlLiteralFactory);
	}
}
