package org.swrlapi.ext.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.core.OWLIRIResolver;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.ext.SWRLAPIBuiltInAtom;
import org.swrlapi.ext.SWRLAPIRule;

import uk.ac.manchester.cs.owl.owlapi.SWRLRuleImpl;

class DefaultSWRLAPIRule extends SWRLRuleImpl implements SWRLAPIRule
{
	private static final long serialVersionUID = 1L;

	private final String ruleName;
	private final boolean active;
	private final String comment;

	private List<SWRLAtom> bodyAtoms; // Body atoms can be reorganized during processing
	private final List<SWRLAtom> headAtoms;

	private final OWLIRIResolver iriResolver;

	public DefaultSWRLAPIRule(String ruleName, List<? extends SWRLAtom> bodyAtoms, List<? extends SWRLAtom> headAtoms,
			OWLIRIResolver iriResolver, boolean active, String comment)
	{
		// TODO Rule name
		super(new HashSet<SWRLAtom>(bodyAtoms), new HashSet<SWRLAtom>(headAtoms), new HashSet<OWLAnnotation>());
		this.ruleName = ruleName;
		this.active = active;
		this.comment = comment;
		this.bodyAtoms = new ArrayList<SWRLAtom>(bodyAtoms);
		this.headAtoms = new ArrayList<SWRLAtom>(headAtoms);

		this.iriResolver = iriResolver;

		processUnboundBuiltInArguments();
	}

	@Override
	public String getRuleName()
	{
		return this.ruleName;
	}

	@Override
	public boolean isActive()
	{
		return active;
	}

	@Override
	public String comment()
	{
		return comment;
	}

	@Override
	public List<SWRLAtom> getHeadAtoms()
	{
		return this.headAtoms;
	}

	@Override
	public List<SWRLAtom> getBodyAtoms()
	{
		return this.bodyAtoms;
	}

	@Override
	public List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromHead(Set<String> builtInNames)
	{
		return getBuiltInAtoms(getHeadAtoms(), builtInNames);
	}

	@Override
	public List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(Set<String> builtInNames)
	{
		return getBuiltInAtoms(getBodyAtoms(), builtInNames);
	}

	/**
	 * Find all built-in atoms with unbound arguments and tell them which of their arguments are unbound. See <a
	 * href="http://protege.cim3.net/cgi-bin/wiki.pl?SWRLBuiltInBridge#nid88T">here</a> for a discussion of the role of
	 * this method.
	 */
	private void processUnboundBuiltInArguments()
	{
		List<SWRLAPIBuiltInAtom> bodyBuiltInAtoms = new ArrayList<SWRLAPIBuiltInAtom>();
		List<SWRLAtom> bodyNonBuiltInAtoms = new ArrayList<SWRLAtom>();
		List<SWRLAtom> finalBodyAtoms = new ArrayList<SWRLAtom>();
		Set<String> variableShortNamesUsedByNonBuiltInBodyAtoms = new HashSet<String>(); // By definition, these will always
																																											// be
		// bound.
		Set<String> variableShortNamesBoundByBuiltIns = new HashSet<String>(); // Names of variables bound by built-ins in
																																						// this
		// rule

		// Process body atoms to build list of (1) built-in body atoms, and (2) the variables used by non-built-in atoms.
		for (SWRLAtom atom : getBodyAtoms()) {
			if (atom instanceof SWRLAPIBuiltInAtom)
				bodyBuiltInAtoms.add((SWRLAPIBuiltInAtom)atom);
			else {
				bodyNonBuiltInAtoms.add(atom);
				variableShortNamesUsedByNonBuiltInBodyAtoms.addAll(getReferencedVariableShortNames(atom));
			}
		}

		// Process the body built-in atoms and determine if they bind any of their arguments.
		for (SWRLAPIBuiltInAtom builtInAtom : bodyBuiltInAtoms) { // Read through built-in arguments and determine which
																															// are unbound.
			for (SWRLBuiltInArgument argument : builtInAtom.getBuiltInArguments()) {
				if (argument.isVariable()) {
					String argumentVariableShortName = argument.asVariable().getVariableShortName();

					// If a variable argument is not used by any non built-in body atom or is not bound by another body built-in
					// atom it will therefore be unbound when this built-in is called. We thus set this built-in argument to
					// unbound. If a built-in binds an argument, all later built-ins (proceeding from left to right) will be
					// passed the bound value of this variable during rule execution.
					if (!variableShortNamesUsedByNonBuiltInBodyAtoms.contains(argumentVariableShortName)
							&& !variableShortNamesBoundByBuiltIns.contains(argumentVariableShortName)) {
						argument.asVariable().setUnbound(); // Tell the built-in that it is expected to bind this argument
						variableShortNamesBoundByBuiltIns.add(argumentVariableShortName); // Flag as a bound variable for later
																																							// built-ins
					}
				}
			}
		}
		// If we have built-in atoms, construct a new body with built-in atoms moved to the end of the list. Some rule
		// engines (e.g., Jess) expect variables used as parameters to functions to have been defined before their use in
		// a left to right fashion.
		finalBodyAtoms = reorganizeBodyNonBuiltInAtoms(bodyNonBuiltInAtoms);
		this.bodyAtoms = finalBodyAtoms;
		finalBodyAtoms.addAll(bodyBuiltInAtoms);
	}

	/**
	 * Reorganize body non-built atoms so that class atoms appear firsts, followed by other atom types.
	 */
	private List<SWRLAtom> reorganizeBodyNonBuiltInAtoms(List<SWRLAtom> bodyNonBuiltInAtoms)
	{
		List<SWRLAtom> bodyClassAtoms = new ArrayList<SWRLAtom>();
		List<SWRLAtom> bodyNonClassNonBuiltInAtoms = new ArrayList<SWRLAtom>();
		List<SWRLAtom> result = new ArrayList<SWRLAtom>();

		for (SWRLAtom atom : bodyNonBuiltInAtoms) {
			if (atom instanceof SWRLClassAtom)
				bodyClassAtoms.add(atom);
			else
				bodyNonClassNonBuiltInAtoms.add(atom);
		}

		result.addAll(bodyClassAtoms); // We arrange the class atoms first
		result.addAll(bodyNonClassNonBuiltInAtoms); // Followed by other non built-in atoms

		return result;
	}

	private List<SWRLAPIBuiltInAtom> getBuiltInAtoms(List<SWRLAtom> atoms, Set<String> builtInNames)
	{
		List<SWRLAPIBuiltInAtom> result = new ArrayList<SWRLAPIBuiltInAtom>();

		for (SWRLAtom atom : atoms) {
			if (atom instanceof SWRLAPIBuiltInAtom) {
				SWRLAPIBuiltInAtom builtInAtom = (SWRLAPIBuiltInAtom)atom;
				if (builtInNames.contains(builtInAtom.getBuiltInShortName()))
					result.add(builtInAtom);
			}
		}
		return result;
	}

	private Set<String> getReferencedVariableShortNames(SWRLAtom atom)
	{
		Set<String> referencedVariableShortNames = new HashSet<String>();

		for (SWRLArgument argument : atom.getAllArguments()) {
			if (argument instanceof SWRLVariable) {
				SWRLVariable variable = (SWRLVariable)argument;
				IRI iri = variable.getIRI();
				String variableShortName = getOWLIRIResolver().iri2ShortName(iri);
				referencedVariableShortNames.add(variableShortName);
			} else if (argument instanceof SWRLVariableBuiltInArgument) {
				SWRLVariableBuiltInArgument variableBuiltInArgument = (SWRLVariableBuiltInArgument)argument;
				referencedVariableShortNames.add(variableBuiltInArgument.getVariableShortName());
			}
		}
		return referencedVariableShortNames;
	}

	private OWLIRIResolver getOWLIRIResolver()
	{
		return this.iriResolver;
	}
}
