package org.swrlapi.builtins.arguments;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRule;

/**
 * Interface representing an argument to a SWRL built-in. It extends the {@link SWRLDArgument} interface to define an
 * interface representing arguments to SWRL built-ins. In addition to the {@link SWRLAPIRule} and
 * {@link SWRLAPIBuiltInAtom} interfaces, this interface is the SWRLAPI's primary OWLAPI extension point. The
 * {@link SWRLBuiltInArgument} interface represents SWRL built-in atom arguments in the SWRLAPI, which has a wider range
 * of built-in argument types than the OWLAPI. The OWLAPI envisions built-in arguments as simple literals or variables
 * only. In addition to literals and variables, the SWRLAPI allows OWL named objects (classes, individuals, properties,
 * and datatypes) as well as SQWRL collection arguments. The {@link SWRLAPIRule} class represents SWRL rules in the
 * SWRLAPI.
 * <p/>
 * Since an OWLAPI ontology (represented by the OWLAPI class {@link OWLOntology}) or an OWL data factory (represented by
 * the OWLAPI class {@link org.semanticweb.owlapi.model.OWLDataFactory), will not be aware of these types a
 * {@link org.swrlapi.core.SWRLAPIOWLOntology} (in
 * conjunction with an {@link org.swrlapi.core.SWRLAPIOWLDataFactory}) must be used to extract SWRLAPI SWRL rules.
 * <p/>
 * Similarly, a SWRLAPI-aware parser is required to generate SWRLAPI rules from rule text.
 *
 * @see SWRLAPIRule, SWRLAPIBuiltInAtom, SWRLAPIOWLOntology, SWRLAPIOntologyProcessor
 * @see SWRLLiteralBuiltInArgument, SWRLVariableBuiltInArgument, SWRLClassBuiltInArgument,
 * SWRLNamedIndividualBuiltInArgument, SWRLObjectPropertyBuiltInArgument, SWRLDataPropertyBuiltInArgument,
 * SWRLDataPropertyBuiltInArgument, SWRLAnnotationPropertyBuiltInArgument, SWRLDatatypeBuiltInArgument,
 * SWRLMultiValueBuiltInArgument, SQWRLCollectionBuiltInArgument
 */
public interface SWRLBuiltInArgument extends SWRLDArgument
{
	boolean isVariable();

	SWRLVariableBuiltInArgument asVariable();

	boolean isMultiValueVariable();

	SWRLMultiValueVariableBuiltInArgument asMultiValueVariable();

	boolean wasBoundVariable();

	String getBoundVariableName();

	void setBoundVariableName(String boundVariableName);

	<T> T accept(SWRLBuiltInArgumentVisitorEx<T> visitor);
}
