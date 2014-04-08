package org.swrlapi.sqwrl;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.swrlapi.core.SWRLAPIOntologyProcessor;
import org.swrlapi.ext.SWRLAPIBuiltInAtom;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 * Represents a SQWRL query in the SWRLAPI. A {@link SWRLAPIOntologyProcessor} can be used to extract SQWRL queries -
 * which are stored as SWRL rules - from an OWL ontology.
 * 
 * @see SWRLAPIOntologyProcessor, SWRLAPIRule
 */
public interface SQWRLQuery
{
	String getName();

	boolean isActive();

	String getComment();

	List<SWRLAtom> getHeadAtoms();

	List<SWRLAtom> getBodyAtoms();

	String getQueryText();

	SQWRLResult getResult() throws SQWRLException;

	List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(Set<String> builtInNames);

	boolean hasCollections();

	List<SWRLAtom> getSQWRLPhase1BodyAtoms();

	List<SWRLAtom> getSQWRLPhase2BodyAtoms();

	void setActive(boolean isActive);

	SQWRLResultGenerator getResultGenerator();
}
