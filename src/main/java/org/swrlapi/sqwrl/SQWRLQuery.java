package org.swrlapi.sqwrl;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.core.SWRLAPIOntologyProcessor;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 * Represents a SQWRL query in the SWRLAPI. A {@link org.swrlapi.core.SWRLAPIOntologyProcessor} can be used to
 * extract SQWRL queries - which are stored as SWRL rules - from an OWL ontology.
 * 
 * @see org.swrlapi.core.SWRLAPIOntologyProcessor
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLResult
 * @see org.swrlapi.sqwrl.SQWRLResultGenerator
 */
public interface SQWRLQuery
{
	String getQueryName();

	boolean isActive();

	String getComment();

	List<SWRLAtom> getHeadAtoms();

	List<SWRLAtom> getBodyAtoms();

	String getQueryText();

	SQWRLResult getSQWRLResult() throws SQWRLException;

	List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(Set<String> builtInNames);

	boolean hasSQWRLCollections();

	List<SWRLAtom> getSQWRLPhase1BodyAtoms();

	List<SWRLAtom> getSQWRLPhase2BodyAtoms();

	void setActive(boolean isActive);

	SQWRLResultGenerator getSQWRLResultGenerator();
}
