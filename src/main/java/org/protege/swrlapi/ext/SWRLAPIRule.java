package org.protege.swrlapi.ext;

import java.util.List;
import java.util.Set;

import org.protege.owl.portability.axioms.SWRLRuleAdapter;
import org.protege.owl.portability.swrl.atoms.SWRLAtomAdapter;

public interface SWRLAPIRule extends SWRLRuleAdapter
{
	String getName();

	List<SWRLAtomAdapter> getHeadAtoms();

	List<SWRLAtomAdapter> getBodyAtoms();

	List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromHead(Set<String> builtInNames);

	List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(Set<String> builtInNames);
}
