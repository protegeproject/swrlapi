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
 * functionality. In particular, the SWRLAPI definesa new atom type for built-ins called @{link SWRLAPIBuiltInAtom}
 * (which specializes the OWLAPI {@link SWRLBuiltInAtom} type), and additional built-in argument types represented by
 * the base interface {@link SWRLBuiltInArgument}, which extends the OWLAPI {@link SWRLDArgument} class.
 * <p>
 * SWRLAP SWRL rules also have rule names, which are stored in an annotation property associated witha rule.
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

	String toDisplayText();
}
