package org.protege.swrlapi.ext;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * SWRLAPI representation of a SWRL rule. Extends the OWLAPI's {@link SWRLRule} with additional functionality. In
 * particular, the SWRLAPI defines a range of additional atom argument classes that extend the OWLAPI {@link 
 */
public interface SWRLAPIRule extends SWRLRule
{
	String getName();

	List<SWRLAtom> getHeadAtoms();

	List<SWRLAtom> getBodyAtoms();

	List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromHead(Set<String> builtInNames);

	List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(Set<String> builtInNames);

	String toDisplayText();
}
