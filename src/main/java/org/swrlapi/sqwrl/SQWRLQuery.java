package org.swrlapi.sqwrl;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.core.SWRLAPIOntologyProcessor;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 * Represents a SQWRL query in the SWRLAPI. A {@link org.swrlapi.core.SWRLAPIOntologyProcessor} can be used to
 * extract SQWRL queries - which are stored as {@link org.swrlapi.core.SWRLAPIRule}s - from an OWL ontology.
 *
 *  * A {@link org.swrlapi.core.SWRLAPIRuleRenderer} can be used to print a query.
 * 
 * @see org.swrlapi.core.SWRLAPIOntologyProcessor
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLResult
 * @see org.swrlapi.sqwrl.SQWRLResultGenerator
 * @see org.swrlapi.core.SWRLAPIRuleRenderer
 */
public interface SQWRLQuery
{
	String getQueryName();

	boolean isActive();

	void setActive(boolean isActive);

	String getComment();

	List<SWRLAtom> getHeadAtoms();

	List<SWRLAtom> getBodyAtoms();

	SQWRLResult getSQWRLResult() throws SQWRLException;

	SQWRLResultGenerator getSQWRLResultGenerator();

	List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(Set<String> builtInNames);

	boolean hasSQWRLCollections();

	List<SWRLAtom> getSQWRLPhase1BodyAtoms();

	List<SWRLAtom> getSQWRLPhase2BodyAtoms();
}
