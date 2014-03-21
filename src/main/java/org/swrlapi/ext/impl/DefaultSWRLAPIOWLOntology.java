package org.swrlapi.ext.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLPredicate;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.core.arguments.SWRLClassAtomArgument;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLMultiValueBuiltInArgument;
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
	public Set<SWRLAPIRule> getSWRLAPIRules()
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
		String ruleName = ""; // TODO
		List<SWRLAtom> swrlapiBodyAtoms = new ArrayList<SWRLAtom>();
		List<SWRLAtom> swrlapiHeadAtoms = new ArrayList<SWRLAtom>();

		for (SWRLAtom owlapiAtom : owlapiRule.getBody()) {
			SWRLAtom swrlapiAtom = convertOWLAPISWRLAtom2SWRLAPIAtom(owlapiAtom);
			swrlapiBodyAtoms.add(swrlapiAtom);
		}

		for (SWRLAtom owlapiAtom : owlapiRule.getHead()) {
			SWRLAtom swrlapiAtom = convertOWLAPISWRLAtom2SWRLAPIAtom(owlapiAtom);
			swrlapiHeadAtoms.add(swrlapiAtom);
		}

		return new DefaultSWRLAPIRule(ruleName, swrlapiBodyAtoms, swrlapiHeadAtoms);
	}

	private SWRLAtom convertOWLAPISWRLAtom2SWRLAPIAtom(SWRLAtom atom)
	{ // TODO Use SWRLObjectVisitor
		if (isSWRLBuiltInAtom(atom)) {
			SWRLBuiltInAtom owlapiBuiltInAtom = (SWRLBuiltInAtom)atom;
			List<SWRLDArgument> swrlDArguments = owlapiBuiltInAtom.getArguments();
			List<SWRLDArgument> swrlBuiltInArguments = convertSWRLDArguments2SWRLBuiltInArguments(swrlDArguments);
			SWRLBuiltInAtom swrlapiAtom = getSWRLAPIOWLDataFactory().getSWRLBuiltInAtom(owlapiBuiltInAtom.getPredicate(),
					swrlBuiltInArguments);
			return swrlapiAtom;
		} else if (atom instanceof SWRLClassAtom) {
			SWRLClassAtom owlapiAtom = (SWRLClassAtom)atom;
			OWLClassExpression owlapiPredicate = owlapiAtom.getPredicate();
			SWRLIArgument owlapiArgument = owlapiAtom.getArgument();
			SWRLClassAtomArgument swrlapiArgument = null;
			SWRLAtom swrlapiAtom = null;
			return swrlapiAtom;
		} else if (atom instanceof SWRLDataPropertyAtom) {
			SWRLDataPropertyAtom owlapiAtom = (SWRLDataPropertyAtom)atom;
			SWRLAtom swrlapiAtom = null;
			return swrlapiAtom;
		} else if (atom instanceof SWRLObjectPropertyAtom) {
			SWRLObjectPropertyAtom owlapiAtom = (SWRLObjectPropertyAtom)atom;
			SWRLAtom swrlapiAtom = null;
			return swrlapiAtom;
		} else if (atom instanceof SWRLSameIndividualAtom) {
			SWRLSameIndividualAtom owlapiAtom = (SWRLSameIndividualAtom)atom;
			SWRLAtom swrlapiAtom = null;
			return swrlapiAtom;
		} else if (atom instanceof SWRLDifferentIndividualsAtom) {
			SWRLDifferentIndividualsAtom owlapiAtom = (SWRLDifferentIndividualsAtom)atom;
			SWRLAtom swrlapiAtom = null;
			return swrlapiAtom;
		} else if (atom instanceof SWRLDataRangeAtom) {
			throw new RuntimeException("SWRL data range atoms not implemented by the SWRLAPI");
		} else
			throw new RuntimeException("Unexpected" + SWRLAtom.class.getName() + " class " + atom.getClass().getName());
	}

	private boolean isSWRLBuiltInAtom(SWRLAtom atom)
	{
		if (atom instanceof SWRLBuiltInAtom)
			return true;
		else {
			SWRLPredicate predicate = atom.getPredicate();
			if (predicate instanceof IRI) {
				IRI iri = (IRI)predicate;
				return isSWRLBuiltIn(iri);
			}
			return false;
		}
	}

	private boolean isSWRLBuiltIn(IRI iri)
	{
		throw new RuntimeException("isSWRLBuiltIn not implemented");
	}

	/**
	 * Both the OWLAPI and the SWRLAPI use the {@link SWRLBuiltInAtom} class to represent built-in atoms. However, the
	 * SWRLAPI has a richer range of possible argument types. The OWLAPI allows {@link SWRLDArgument} built-in arguments
	 * only, whereas the SWRLAPI has a range of types. These types are represented buy the {@link SWRLBuiltInArgument}
	 * interface.
	 * 
	 * @see SWRLBuiltInArgument
	 */
	private List<SWRLDArgument> convertSWRLDArguments2SWRLBuiltInArguments(List<SWRLDArgument> swrlDArguments)
	{
		List<SWRLDArgument> swrlBuiltInArguments = new ArrayList<SWRLDArgument>();

		for (SWRLDArgument swrlDArgument : swrlDArguments) {
			SWRLBuiltInArgument swrlBuiltInArgument = convertSWRLDArgument2SWRLBuiltInArgument(swrlDArgument);
			swrlBuiltInArguments.add(swrlBuiltInArgument);
		}

		return swrlBuiltInArguments;
	}

	/**
	 * The {@link SWRLBuiltInArgument} interface represents the primary SWRLAPI extension point to the OWLAPI classes to
	 * represent arguments to SWRL built-in atoms.
	 * 
	 * @see SWRLBuiltInArgument, SWRLVariableBuiltInArgument, SWRLLiteralArgument, SWRLDArgument
	 */
	private SWRLBuiltInArgument convertSWRLDArgument2SWRLBuiltInArgument(SWRLDArgument swrlDArgument)
	{
		if (swrlDArgument instanceof SWRLLiteralArgument) {
			SWRLLiteralArgument swrlLiteralArgument = (SWRLLiteralArgument)swrlDArgument;
			SWRLBuiltInArgument argument = convertSWRLLiteralArgument2SWRLBuiltInArgument(swrlLiteralArgument);
			return argument;
		} else if (swrlDArgument instanceof SWRLVariable) {
			SWRLVariable swrlVariable = (SWRLVariable)swrlDArgument;
			SWRLVariableBuiltInArgument argument = transformSWRLVariable2SWRLVariableBuiltInArgument(swrlVariable);
			return argument;
		} else
			throw new RuntimeException("Unknown " + SWRLDArgument.class.getName() + " class "
					+ swrlDArgument.getClass().getName());
	}

	/**
	 * The OWLAPI permits only variable and literal arguments to built-ins, which conforms with the SWRL Specification.
	 * The SWRLAPI also permits OWL named objects (classes, named individuals, properties, and datatypes) as arguments. In
	 * order to support these additional argument types in a Specification-conformant way, the SWRLAPI treats URI literal
	 * arguments specially. It a URI literal argument is passed to a built-in we determine if it refers to an OWL named
	 * object in the active ontology and if so we create specific SWRLAPI built-in argument types for it.
	 * <p>
	 * The SWRLAPI allows SQWRL collection built-in arguments (represented by a {@link SQWRLCollectionBuiltInArgument})
	 * and multi-value variables (represented by a {@link SWRLMultiValueBuiltInArgument}). These two argument types are
	 * not instantiated directly as built-in argument types. They are created at runtime during rule execution and passed
	 * as result values in SWRL variable built-in arguments.
	 * 
	 * @see SWRLLiteralBuiltInArgument, SWRLClassBuiltInArgument, SWRLNamedIndividualBuiltInArgument,
	 *      SWRLObjectPropertyBuiltInArgument, SWRLDataPropertyBuiltInArgument, SWRLAnnotationPropertyBuiltInArgument,
	 *      SWRLDatatypeBuiltInArgument, SQWRLCollectionBuiltInArgument, SWRLMultiValueBuiltInArgument
	 */
	private SWRLBuiltInArgument convertSWRLLiteralArgument2SWRLBuiltInArgument(SWRLLiteralArgument swrlLiteralArgument)
	{
		OWLLiteral literal = swrlLiteralArgument.getLiteral();
		OWLDatatype datatype = literal.getDatatype();

		if (isURI(datatype)) {
			IRI iri = IRI.create(literal.getLiteral());
			if (containsClassInSignature(iri)) {
				return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(iri);
			} else if (containsIndividualInSignature(iri)) {
				return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(iri);
			} else if (containsObjectPropertyInSignature(iri)) {
				return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(iri);
			} else if (containsDataPropertyInSignature(iri)) {
				return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(iri);
			} else if (containsAnnotationPropertyInSignature(iri)) {
				return getSWRLBuiltInArgumentFactory().getAnnotationPropertyBuiltInArgument(iri);
			} else if (containsDatatypeInSignature(iri)) {
				return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(iri);
			} else {
				return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
			}
		} else {
			SWRLLiteralBuiltInArgument argument = getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
			return argument;
		}
	}

	private SWRLVariableBuiltInArgument transformSWRLVariable2SWRLVariableBuiltInArgument(SWRLVariable swrlVariable)
	{
		IRI iri = swrlVariable.getIRI();
		String variableName = iri2ShortName(iri);

		SWRLVariableBuiltInArgument argument = getSWRLBuiltInArgumentFactory().getVariableBuiltInArgument(variableName);

		return argument;
	}

	private String iri2ShortName(IRI iri)
	{
		throw new RuntimeException("iri2ShortName not implemented");
	}

	private boolean isURI(OWLDatatype datatype)
	{
		return datatype.getIRI().equals(OWL2Datatype.XSD_ANY_URI.getIRI());
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
