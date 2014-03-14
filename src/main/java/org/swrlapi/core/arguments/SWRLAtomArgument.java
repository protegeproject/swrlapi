package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLRule;
import org.swrlapi.core.SWRLOntologyProcessor;
import org.swrlapi.ext.SWRLAPIRule;

/**
 * The SWRLAPI's base interface representing arguments to atoms. This interface represents the primary SWRLAPI extension
 * point of the OWLAPI. It extends the OWLAPI's {@link SWRLArgument} interface and specializes it. The primary
 * specialization is the {@link SWRLBuiltInArgument} interface, which represents the more specialized range of argument
 * types that can be passed to SWRLAPI built-ins.
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
