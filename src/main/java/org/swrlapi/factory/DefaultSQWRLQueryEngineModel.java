package org.swrlapi.factory;

import org.semanticweb.owlapi.model.OWLOntology;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.ui.model.SQWRLQueryEngineModel;

import checkers.nullness.quals.NonNull;

public class DefaultSQWRLQueryEngineModel extends DefaultSWRLRuleEngineModel implements SQWRLQueryEngineModel
{
	@NonNull
	private final SQWRLQueryEngine queryEngine;

	public DefaultSQWRLQueryEngineModel(@NonNull OWLOntology ontology, @NonNull SQWRLQueryEngine sqwrlQueryEngine)
	{
		super(ontology, sqwrlQueryEngine);
		this.queryEngine = sqwrlQueryEngine;
	}

	@NonNull
	@Override
	public SQWRLQueryEngine getSQWRLQueryEngine()
	{
		return this.queryEngine;
	}
}
