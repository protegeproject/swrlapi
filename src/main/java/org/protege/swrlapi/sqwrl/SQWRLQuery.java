package org.protege.swrlapi.sqwrl;

import java.util.List;
import java.util.Set;

import org.protege.owl.portability.swrl.atoms.SWRLAtomAdapter;
import org.protege.swrlapi.ext.SWRLAPIBuiltInAtom;
import org.protege.swrlapi.sqwrl.exceptions.SQWRLException;

public interface SQWRLQuery
{
	String getName();

	List<SWRLAtomAdapter> getHeadAtoms();

	List<SWRLAtomAdapter> getBodyAtoms();

	String getQueryText();

	SQWRLResult getResult() throws SQWRLException;
	
	SQWRLResultGenerator getResultGenerator();
	
	List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(Set<String> builtInNames);
	
	boolean hasCollections();
	
	List<SWRLAtomAdapter> getSQWRLPhase1BodyAtoms();
	
	List<SWRLAtomAdapter> getSQWRLPhase2BodyAtoms();
	
	void setActive(boolean isActive);
	
	boolean isActive();
}
