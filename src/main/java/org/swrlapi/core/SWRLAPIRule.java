package org.swrlapi.core;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLRule;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;

/**
 * SWRLAPI representation of a SWRL rule. It specializes the OWLAPI's {@link org.semanticweb.owlapi.model.SWRLRule}
 * to provide additional functionality. In particular, the SWRLAPI defines a new atom type for built-ins called
 * {@link org.swrlapi.core.SWRLAPIBuiltInAtom} (which specializes the OWLAPI
 * {@link org.semanticweb.owlapi.model.SWRLBuiltInAtom} type). This atom takes additional built-in argument types
 * represented by {@link org.swrlapi.builtins.arguments.SWRLBuiltInArgument}, which extends the OWLAPI
 * {@link org.semanticweb.owlapi.model.SWRLDArgument} class.
 * <p>
 * SWRLAPI SWRL rules also have rule names, which are stored in an annotation property associated with a rule.
 * <p>
 * These SWRLAPI rules are extracted from an OWL ontology by the
 * {@link org.swrlapi.core.SWRLAPIOWLOntology#getSWRLAPIRules()} method.
 * 
 * @see org.swrlapi.core.SWRLAPIBuiltInAtom
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 * @see org.swrlapi.core.SWRLAPIOWLOntology
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
