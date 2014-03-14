package org.protege.swrlapi.core.arguments;

import org.protege.swrlapi.core.SWRLOntologyProcessor;
import org.protege.swrlapi.ext.SWRLAPIRule;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * The SWRLAPI's base interface representing arguments to atoms. It extends the OWLAPI's {@link SWRLArgument} interface
 * and specializes it. The SWRLAPI has a range of atom argument that specialize this interface.
 * <p>
 * Since and OWLAPI ontology (represented by the OWLAPI class {@link OWLOntology}) or an OWL data factory (represented
 * by the class {@link OWLDataFactory), will not be aware of these types a {@link SWRLOntologyProcessor} must be used
 * to construct SWRLAPI SWRL rules (represented by the class {@link SWRLAPIRule}, which extends the OWLAPI
 * {@link SWRLRule} class).
 */
public interface SWRLAtomArgument extends SWRLArgument
{
	String toDisplayText();
}
