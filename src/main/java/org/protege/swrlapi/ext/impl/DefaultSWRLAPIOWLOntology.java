package org.protege.swrlapi.ext.impl;

import java.util.Set;

import org.protege.swrlapi.ext.SWRLAPIOWLOntology;
import org.protege.swrlapi.ext.SWRLAPIRule;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;

public class DefaultSWRLAPIOWLOntology extends OWLOntologyImpl implements SWRLAPIOWLOntology
{
	private static final long serialVersionUID = 1L;

	public DefaultSWRLAPIOWLOntology(OWLOntologyManager manager, OWLOntologyID ontologyID)
	{
		super(manager, ontologyID);
	}

	@Override
	public void startBulkConversion()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void completeBulkConversion()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean hasOntologyChanged()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void resetOntologyChanged()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<SWRLAPIRule> getSWRLRules()
	{
		throw new RuntimeException("Not implemented");
	}

	// TODO We really do not want the following three methods here. They are convenience methods only and are used only by
	// a few built-in libraries.
	@Override
	public boolean isOWLIndividualOfType(IRI individualIRI, IRI classIRI)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI)
	{
		throw new RuntimeException("Not implemented");
	}

}
