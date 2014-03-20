package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLRule;
import org.swrlapi.core.SWRLOntologyProcessor;
import org.swrlapi.ext.SWRLAPIOWLDataFactory;
import org.swrlapi.ext.SWRLAPIRule;

/**
 * The SWRLAPI's base interface representing arguments to atoms. This interface represents the primary SWRLAPI extension
 * point of the OWLAPI. It extends the OWLAPI's {@link SWRLArgument} interface and specializes it. The primary
 * specialization is the {@link SWRLBuiltInArgument} interface, which represents the extended range of argument types
 * that can be passed to SWRLAPI built-ins.
 * <p>
 * Since an OWLAPI ontology (represented by the OWLAPI class {@link OWLOntology}) or an OWL data factory (represented by
 * the OWLAPI class {@link OWLDataFactory), will not be aware of these types a {@link SWRLOntologyProcessor} (in
 * conjunction with an {@link SWRLAPIOWLDataFactory}) must be used to extract SWRLAPI SWRL rules (represented by the
 * class {@link SWRLAPIRule}) from an OWL ontology. This class extends the OWLAPI SWRL rule class (represented by the
 * {@link SWRLRule} class).
 * <p>
 * Similarly, a SWRLAPI-aware parse is required to generate SWRLAPI rules from rule text.
 * 
 * @see SWRLVariableAtomArgument, SWRLLiteralAtomArgument, SWRLIndividualAtomArgument, SWRLClassAtomArgument,
 *      SWRLObjectPropertyAtomArgument, SWRLDataPropertyAtomArgument, SWRLAnnotationPropertyAtomArgument,
 *      SWRLDatatypeAtomArgument
 * @see SWRLAPIRule
 * @see SWRLVariableBuiltInArgument, SWRLLiteralBuiltInArgument, SWRLIndividualBuiltInArgument,
 *      SWRLClassBuiltInArgument, SWRLObjectPropertyBuiltInArgument, SWRLDataPropertyBuiltInArgument,
 *      SWRLAnnotationPropertyBuiltInArgument, SWRLDatatypeBuiltInArgument, SQWRLCollectionBuiltInArgument,
 *      SWRLMultiArgument
 */
public interface SWRLAtomArgument extends SWRLArgument
{
	String toDisplayText();
}
