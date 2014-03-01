package org.protege.swrlapi.sqwrl;

import java.util.List;
import java.util.Set;

import org.protege.swrlapi.ext.SWRLAPIBuiltInAtom;
import org.protege.swrlapi.sqwrl.exceptions.SQWRLException;
import org.semanticweb.owlapi.model.SWRLAtom;

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
