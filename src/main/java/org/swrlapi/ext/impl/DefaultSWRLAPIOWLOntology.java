package org.swrlapi.ext.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLRule;
import org.swrlapi.ext.SWRLAPIOWLDataFactory;
import org.swrlapi.ext.SWRLAPIOWLOntology;
import org.swrlapi.ext.SWRLAPIRule;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;

public class DefaultSWRLAPIOWLOntology extends OWLOntologyImpl implements SWRLAPIOWLOntology
{
	private static final long serialVersionUID = 1L;

	private final SWRLAPIOWLDataFactory swrlapiOWLDataFactory;

	public DefaultSWRLAPIOWLOntology(OWLOntologyManager manager, OWLOntologyID ontologyID,
			SWRLAPIOWLDataFactory swrlapiOWLDataFactory)
	{
		super(manager, ontologyID);

		this.swrlapiOWLDataFactory = swrlapiOWLDataFactory;
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
		Set<SWRLAPIRule> swrlapiRules = new HashSet<SWRLAPIRule>();

		for (SWRLRule owlapiRule : getAxioms(AxiomType.SWRL_RULE)) {
			SWRLAPIRule swrlapiRule = convertOWLAPIRule2SWRLAPIRule(owlapiRule);
			swrlapiRules.add(swrlapiRule);
		}

		return swrlapiRules;
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

	private SWRLAPIRule convertOWLAPIRule2SWRLAPIRule(SWRLRule owlapiRule)
	{
		String ruleName = "";
		List<SWRLAtom> swrlapiBodyAtoms = new ArrayList<SWRLAtom>();
		List<SWRLAtom> swrlapiHeadAtoms = new ArrayList<SWRLAtom>();

		for (SWRLAtom atom : owlapiRule.getBody()) {
			if (atom instanceof SWRLBuiltInAtom) {
				SWRLBuiltInAtom owlapiBuiltInAtom = (SWRLBuiltInAtom)atom;
				SWRLBuiltInAtom swrlapiBuiltInAtom = transformSWRLBuiltInAtom(owlapiBuiltInAtom);
				swrlapiBodyAtoms.add(swrlapiBuiltInAtom);
			} else
				swrlapiBodyAtoms.add(atom);
		}

		return new DefaultSWRLAPIRule(ruleName, swrlapiBodyAtoms, swrlapiHeadAtoms);
	}

	private SWRLBuiltInAtom transformSWRLBuiltInAtom(SWRLBuiltInAtom owlapiSWRLBuiltInAtom)
	{
		List<SWRLDArgument> swrlapiDArguments = new ArrayList<SWRLDArgument>();

		for (SWRLDArgument owlapiSWRLDArgument : owlapiSWRLBuiltInAtom.getArguments()) {
			SWRLDArgument swrlapiSWRLDArgument = transformSWRLDArgument(owlapiSWRLDArgument);
			swrlapiDArguments.add(swrlapiSWRLDArgument);
		}

		return getSWRLAPIOWLDataFactory().getSWRLBuiltInAtom(owlapiSWRLBuiltInAtom.getPredicate(), swrlapiDArguments);
	}

	private SWRLDArgument transformSWRLDArgument(SWRLDArgument owlapiSWRLDArgument)
	{ // SWRLVariable,
		throw new RuntimeException("Not implemented");
	}

	private SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
	{
		return this.swrlapiOWLDataFactory;
	}

	// SWRLRule getSWRLRule(Set<? extends SWRLAtom> body, Set<? extends SWRLAtom> head);
	// SWRLRule getSWRLRule(Set<? extends SWRLAtom> body, Set<? extends SWRLAtom> head, Set<OWLAnnotation> annotations);
	// SWRLClassAtom getSWRLClassAtom(OWLClassExpression predicate, SWRLIArgument arg);
	// SWRLDataRangeAtom getSWRLDataRangeAtom(OWLDataRange predicate, SWRLDArgument arg);
	// SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(OWLObjectPropertyExpression property, SWRLIArgument arg0,
	// SWRLIArgument arg1);
	// SWRLDataPropertyAtom getSWRLDataPropertyAtom(OWLDataPropertyExpression property, SWRLIArgument arg0, SWRLDArgument
	// arg1);
	// SWRLBuiltInAtom getSWRLBuiltInAtom(IRI builtInIRI, List<SWRLDArgument> args);
	// SWRLVariable getSWRLVariable(IRI var);
	// SWRLIndividualArgument getSWRLIndividualArgument(OWLIndividual individual);
	// SWRLLiteralArgument getSWRLLiteralArgument(OWLLiteral literal);
	// SWRLSameIndividualAtom getSWRLSameIndividualAtom(SWRLIArgument arg0, SWRLIArgument arg1);
	// SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(SWRLIArgument arg0, SWRLIArgument arg1);

}
