package org.swrlapi.sqwrl;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.swrlapi.ext.SWRLAPIBuiltInAtom;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

public interface SQWRLQuery
{
	String getName();

	List<SWRLAtom> getHeadAtoms();

	List<SWRLAtom> getBodyAtoms();

	String getQueryText();

	SQWRLResult getResult() throws SQWRLException;

	SQWRLResultGenerator getResultGenerator();

	List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(Set<String> builtInNames);

	boolean hasCollections();

	List<SWRLAtom> getSQWRLPhase1BodyAtoms();

	List<SWRLAtom> getSQWRLPhase2BodyAtoms();

	void setActive(boolean isActive);

	boolean isActive();
}
