package org.swrlapi.ext;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLRule;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;

/**
 * SWRLAPI representation of a SWRL rule. It specializes the OWLAPI's {@link SWRLRule} to provide additional
 * functionality. In particular, the SWRLAPI defines a new atom type for built-ins called @{link SWRLAPIBuiltInAtom}
 * (which specializes the OWLAPI {@link SWRLBuiltInAtom} type). This atom takes additional built-in argument types
 * represented by {@link SWRLBuiltInArgument}, which extends the OWLAPI {@link SWRLDArgument} class.
 * <p>
 * SWRLAP SWRL rules also have rule names, which are stored in an annotation property associated with a rule.
 * <p>
 * These SWRLAPI rules are extracted from an OWL ontology by the {@link SWRLAPIOWLOntology#getSWRLAPIRules()} method.
 * 
 * @see SWRLAPIBuiltInAtom, SWRLBuiltInArgument, SWRLAPIOWLOntology
 */
public interface SWRLAPIRule extends SWRLRule
{
	String getRuleName();

	List<SWRLAtom> getHeadAtoms();

	List<SWRLAtom> getBodyAtoms();

	List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromHead(Set<String> builtInNames);

	List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(Set<String> builtInNames);

	boolean isActive();

	String comment();
}
