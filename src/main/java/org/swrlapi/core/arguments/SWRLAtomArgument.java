package org.swrlapi.core.arguments;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLRule;
import org.swrlapi.ext.SWRLAPIOWLDataFactory;
import org.swrlapi.ext.SWRLAPIOWLOntology;
import org.swrlapi.ext.SWRLAPIRule;

/**
 * The SWRLAPI's base interface representing arguments to atoms. In addition to the {@link SWRLAPIRule} interface, this
 * interface represents the primary SWRLAPI extension point of the OWLAPI. It extends the OWLAPI's {@link SWRLArgument}
 * interface and specializes it. The primary specialization is the {@link SWRLBuiltInArgument} interface, which
 * represents the extended range of argument types that can be passed to SWRLAPI built-ins. The SWRLAPI also has a
 * specialized SWRL rule class, which is represented by the interface {@link SWRLAPIRule}). It extends the OWLAPI SWRL
 * rule class (represented by the {@link SWRLRule} class) and is aware of the additional atom argument types in SWRLAPI
 * rules.
 * <p>
 * Since an OWLAPI ontology (represented by the OWLAPI class {@link OWLOntology}) or an OWL data factory (represented by
 * the OWLAPI class {@link OWLDataFactory), will not be aware of these types a {@link SWRLAPIOWLOntology} (in
 * conjunction with an {@link SWRLAPIOWLDataFactory}) must be used to extract SWRLAPI SWRL rules
 * <p>
 * Similarly, a SWRLAPI-aware parser is required to generate SWRLAPI rules from rule text.
 * 
 * @see SWRLAPIRule, SWRLAPIOWLOntology, SWRLAPIOWLDataFactory, SWRLBuiltInArgumentFactory, SWRLAtomArgumentFactory
 * @see SWRLVariableAtomArgument, SWRLLiteralAtomArgument, SWRLIndividualAtomArgument, SWRLClassAtomArgument,
 *      SWRLObjectPropertyAtomArgument, SWRLDataPropertyAtomArgument, SWRLAnnotationPropertyAtomArgument,
 *      SWRLDatatypeAtomArgument
 * @see SWRLBuiltInArgument, SWRLVariableBuiltInArgument, SWRLLiteralBuiltInArgument, SWRLIndividualBuiltInArgument,
 *      SWRLClassBuiltInArgument, SWRLObjectPropertyBuiltInArgument, SWRLDataPropertyBuiltInArgument,
 *      SWRLAnnotationPropertyBuiltInArgument, SWRLDatatypeBuiltInArgument, SQWRLCollectionBuiltInArgument,
 *      SWRLMultiBuiltInArgument
 */
public interface SWRLAtomArgument extends SWRLArgument
{
	String toDisplayText();
}
