package org.swrlapi.ext.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLMultiArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
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
		List<SWRLDArgument> swrlBuiltInArguments = new ArrayList<SWRLDArgument>();

		for (SWRLDArgument swrlDArgument : owlapiSWRLBuiltInAtom.getArguments()) {
			SWRLBuiltInArgument swrlBuiltInArgument = transformSWRLDArgument2SWRLBuiltInArgument(swrlDArgument);
			swrlBuiltInArguments.add(swrlBuiltInArgument);
		}

		return getSWRLAPIOWLDataFactory().getSWRLBuiltInAtom(owlapiSWRLBuiltInAtom.getPredicate(), swrlBuiltInArguments);
	}

	/**
	 * The {@link SWRLBuiltInArgument} interface represents the primary SWRLAPI extension point to the OWLAPI classes
	 * representing SWRL concepts.
	 * 
	 * @see {@link SWRLLiteralBuiltInArgument}, {@link SWRLVariableBuiltInArgument},{@link SWRLClassBuiltInArgument},
	 *      {@link SWRLIndividualBuiltInArgument}, {@link SWRLObjectPropertyBuiltInArgument},
	 *      {@link SWRLDataPropertyBuiltInArgument}, {@link SWRLDataPropertyBuiltInArgument},
	 *      {@link SWRLAnnotationPropertyBuiltInArgument}, {@link SWRLDatatypeBuiltInArgument},
	 *      {@link SQWRLCollectionBuiltInArgument}, {@link SWRLMultiArgument}
	 */
	private SWRLBuiltInArgument transformSWRLDArgument2SWRLBuiltInArgument(SWRLDArgument swrlDArgument)
	{ // SWRLLiteralArgument, SWRLVariable
		if (swrlDArgument instanceof SWRLVariable) {
			// SWRLVariable swrlVariable = (SWRLVariable)swrlDArgument;
			String variableName = ""; // TODO
			SWRLVariableBuiltInArgument argument = getSWRLBuiltInArgumentFactory().getVariableArgument(variableName);
			return argument;
		} else if (swrlDArgument instanceof SWRLLiteralArgument) {
			SWRLLiteralArgument swrlLiteralArgument = (SWRLLiteralArgument)swrlDArgument;
			OWLLiteral literal = swrlLiteralArgument.getLiteral();
			OWLDatatype datatype = literal.getDatatype();
			if (isURI(datatype)) {
				IRI iri = IRI.create(literal.getLiteral());
				SWRLBuiltInArgument argument;
				if (containsClassInSignature(iri)) {
					argument = getSWRLBuiltInArgumentFactory().getClassArgument(iri);
				} else {
					argument = getSWRLBuiltInArgumentFactory().getLiteralArgument(literal);
				}
				throw new RuntimeException("Not implemented");
			} else {
				SWRLLiteralBuiltInArgument argument = getSWRLBuiltInArgumentFactory().getLiteralArgument(literal);
				return argument;
			}
		} else
			throw new RuntimeException("Unknown " + SWRLDArgument.class.getName() + " class "
					+ swrlDArgument.getClass().getName());
	}

	private boolean isURI(OWLDatatype datatype)
	{
		throw new RuntimeException("Not implemented");
	}

	private SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
	{
		return this.swrlapiOWLDataFactory;
	}

	private SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
	{
		return getSWRLAPIOWLDataFactory().getSWRLBuiltInArgumentFactory();
	}
}
