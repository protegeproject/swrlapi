package org.swrlapi.sqwrl;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.util.List;
import java.util.Set;

/**
 * Represents a SQWRL query in the SWRLAPI. A {@link org.swrlapi.core.SWRLAPIOntologyProcessor} can be used to
 * extract SQWRL queries - which are stored as {@link org.swrlapi.core.SWRLAPIRule}s - from an OWL ontology.
 * <p/>
 * A {@link org.swrlapi.core.SWRLAPIRenderer} can be used to display a query.
 *
 * @see org.swrlapi.core.SWRLAPIOntologyProcessor
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLResult
 * @see org.swrlapi.sqwrl.SQWRLResultGenerator
 * @see org.swrlapi.core.SWRLAPIRenderer
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

	boolean hasSQWRLCollections();

	List<SWRLAtom> getSQWRLPhase1BodyAtoms();

	List<SWRLAtom> getSQWRLPhase2BodyAtoms();

	List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(Set<String> builtInNames);
}
