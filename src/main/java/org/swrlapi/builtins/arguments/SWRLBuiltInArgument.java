package org.swrlapi.builtins.arguments;

import org.semanticweb.owlapi.model.SWRLDArgument;

/**
 * Interface representing an argument to a SWRL built-in. It extends the OWLAPI's
 * {@link org.semanticweb.owlapi.model.SWRLDArgument} interface to define an interface representing arguments to SWRL
 * built-ins. In addition to the {@link org.swrlapi.core.SWRLAPIRule} and {@link org.swrlapi.core.SWRLAPIBuiltInAtom}
 * interfaces, this interface is the SWRLAPI's primary OWLAPI extension point.
 * <p>
 * The {@link org.swrlapi.builtins.arguments.SWRLBuiltInArgument} interface represents SWRL built-in atom arguments in
 * the SWRLAPI, which has a wider range of built-in argument types than the OWLAPI. The OWLAPI envisions built-in
 * arguments as simple literals or variables only. In addition to literals and variables, the SWRLAPI allows OWL named
 * objects (classes, individuals, properties, and datatypes) as well as SQWRL collection arguments. The
 * {@link org.swrlapi.core.SWRLAPIRule} class represents SWRL rules in the SWRLAPI.
 * <p/>
 * Since an OWLAPI ontology (represented by the OWLAPI class {@link org.semanticweb.owlapi.model.OWLOntology}) or an OWL
 * data factory (represented by the OWLAPI class {@link org.semanticweb.owlapi.model.OWLDataFactory), will not be aware
 * of these types a {@link org.swrlapi.core.SWRLAPIOWLOntology} (in conjunction with an
 * {@link org.swrlapi.core.SWRLAPIOWLDataFactory}) must be used to extract SWRLAPI SWRL rules.
 * <p/>
 * Similarly, a SWRLAPI-aware parser is required to generate SWRLAPI rules from rule text.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.core.SWRLAPIBuiltInAtom
 * @see org.swrlapi.core.SWRLAPIOWLOntology
 * @see org.swrlapi.core.SWRLAPIOntologyProcessor
 * @see org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument
 */
public interface SWRLBuiltInArgument extends SWRLDArgument
{
	boolean isVariable();

	boolean isMultiValueVariable();

	boolean isLiteral();

	boolean isNamed();

	SWRLVariableBuiltInArgument asVariable();

	SWRLMultiValueVariableBuiltInArgument asMultiValueVariable();

	SWRLLiteralBuiltInArgument asSWRLLiteralBuiltInArgument();

	SWRLNamedBuiltInArgument asSWRLNamedBuiltInArgument();

	boolean wasBoundVariable();

	String getBoundVariableName();

	void setBoundVariableName(String boundVariableName);

	void accept(SWRLBuiltInArgumentVisitor visitor);

	<T> T accept(SWRLBuiltInArgumentVisitorEx<T> visitor);
}
