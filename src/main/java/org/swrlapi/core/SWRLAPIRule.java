package org.swrlapi.core;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * SWRLAPI representation of a SWRL rule. It specializes the OWLAPI's {@link org.semanticweb.owlapi.model.SWRLRule} to
 * provide additional functionality. In particular, the SWRLAPI defines a new atom type for built-ins called
 * {@link org.swrlapi.core.SWRLAPIBuiltInAtom} (which specializes the OWLAPI
 * {@link org.semanticweb.owlapi.model.SWRLBuiltInAtom} type). This atom takes additional built-in argument types
 * represented by {@link org.swrlapi.builtins.arguments.SWRLBuiltInArgument}, which extends the OWLAPI
 * {@link org.semanticweb.owlapi.model.SWRLDArgument} class.
 * <p>
 * A SWRLAPI SWRL rules also have an optional rule name, which is stored in a label annotation property.
 * <p>
 * These SWRLAPI rules are extracted from an OWL ontology by the
 * {@link org.swrlapi.core.SWRLAPIOWLOntology#getSWRLAPIRules()} method.
 *
 * A {@link org.swrlapi.core.SWRLAPIRenderer} can be used to print a rule.
 * 
 * @see org.swrlapi.core.SWRLAPIBuiltInAtom
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 * @see org.swrlapi.core.SWRLAPIOWLOntology
 * @see org.swrlapi.core.SWRLAPIRenderer
 */
public interface SWRLAPIRule extends SWRLRule
{
	String getRuleName();

	String getComment();

	boolean isActive();

	boolean isSQWRLQuery();

	List<SWRLAtom> getHeadAtoms();

	List<SWRLAtom> getBodyAtoms();

	List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromHead(Set<String> builtInNames);

	List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(Set<String> builtInNames);
}
