package org.swrlapi.builtins.arguments;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.factory.SWRLAPIOWLDataFactory;

import java.util.Optional;

/**
 * Interface representing an argument to a SWRL built-in. It extends the OWLAPI's
 * {@link org.semanticweb.owlapi.model.SWRLDArgument} interface to define an interface representing arguments to SWRL
 * built-ins. In addition to the {@link org.swrlapi.core.SWRLAPIRule} and {@link org.swrlapi.core.SWRLAPIBuiltInAtom}
 * interfaces, this interface is the SWRLAPI's primary OWLAPI extension point.
 * <p/>
 * The {@link org.swrlapi.builtins.arguments.SWRLBuiltInArgument} interface represents SWRL built-in atom arguments in
 * the SWRLAPI, which has a wider range of built-in argument types than the OWLAPI. The OWLAPI envisions built-in
 * arguments as simple literals or variables only. In addition to literals and variables, the SWRLAPI allows OWL named
 * objects (classes, individuals, properties, and datatypes) as well as SQWRL collection arguments. The
 * {@link org.swrlapi.core.SWRLAPIRule} class represents SWRL rules in the SWRLAPI.
 * <p/>
 * Since an OWLAPI ontology (represented by the OWLAPI class {@link org.semanticweb.owlapi.model.OWLOntology}) or an OWL
 * data factory (represented by the OWLAPI class {@link org.semanticweb.owlapi.model.OWLDataFactory}), will not be aware
 * of these types a {@link org.swrlapi.core.SWRLAPIOWLOntology} (in conjunction with an
 * {@link SWRLAPIOWLDataFactory}) must be used to extract SWRLAPI SWRL rules.
 * <p>
 * Similarly, a SWRLAPI-aware parser is required to generate SWRLAPI rules from rule text.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.core.SWRLAPIBuiltInAtom
 * @see org.swrlapi.core.SWRLAPIOWLOntology
 * @see org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLObjectPropertyExpressionBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLDataPropertyExpressionBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument
 */
public interface SWRLBuiltInArgument extends SWRLDArgument
{
  /**
   * @return The built-in argument type
   */
  @NonNull SWRLBuiltInArgumentType<?> getSWRLBuiltInArgumentType();

  /**
   * @return True if the argument is a variable (plain variable, collection, or multi-value)
   */
  boolean isVariable();

  /**
   * @return The argument as a SWRL variable built-in argument
   */
  @NonNull SWRLVariableBuiltInArgument asVariable() throws SWRLBuiltInException;

  /**
   * @return The argument as a SQWRL collection variable built-in argument
   */
  @NonNull SQWRLCollectionVariableBuiltInArgument asCollectionVariable() throws SWRLBuiltInException;

  /**
   * @return The argument as a SWRL multi-value variable built-in argument
   */
  @NonNull SWRLMultiValueVariableBuiltInArgument asMultiValueVariable() throws SWRLBuiltInException;

  /**
   * @return The argument as a SWRL literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument asSWRLLiteralBuiltInArgument() throws SWRLBuiltInException;

  /**
   * @return The argument as a SWRL named built-in argument
   */
  @NonNull SWRLNamedBuiltInArgument asSWRLNamedBuiltInArgument() throws SWRLBuiltInException;

  /**
   * @return The argument as a SWRL class built-in argument
   */
  @NonNull SWRLClassBuiltInArgument asSWRLClassBuiltInArgument() throws SWRLBuiltInException;

  /**
   * @return The argument as a SWRL class expression built-in argument
   */
  @NonNull SWRLClassExpressionBuiltInArgument asSWRLClassExpressionBuiltInArgument() throws SWRLBuiltInException;

  /**
   * @return The argument as a SWRL named individual built-in argument
   */
  @NonNull SWRLNamedIndividualBuiltInArgument asSWRLNamedIndividualBuiltInArgument() throws SWRLBuiltInException;

  /**
   * @return The argument as a SWRL object property built-in argument
   */
  @NonNull SWRLObjectPropertyBuiltInArgument asSWRLObjectPropertyBuiltInArgument() throws SWRLBuiltInException;

  /**
   * @return The argument as a SWRL object property expression built-in argument
   */
  @NonNull SWRLObjectPropertyExpressionBuiltInArgument asSWRLObjectPropertyExpressionBuiltInArgument()
    throws SWRLBuiltInException;

  /**
   * @return The argument as a SWRL data property built-in argument
   */
  @NonNull SWRLDataPropertyBuiltInArgument asSWRLDataPropertyBuiltInArgument() throws SWRLBuiltInException;

  /**
   * @return The argument as a SWRL data property expression built-in argument
   */
  @NonNull SWRLDataPropertyExpressionBuiltInArgument asSWRLDataPropertyExpressionBuiltInArgument()
    throws SWRLBuiltInException;

  /**
   * @return The argument as a SWRL annotation property built-in argument
   */
  @NonNull SWRLAnnotationPropertyBuiltInArgument asSWRLAnnotationPropertyBuiltInArgument() throws SWRLBuiltInException;

  /**
   * @return The argument as a SWRL datatype built-in argument
   */
  @NonNull SWRLDatatypeBuiltInArgument asSWRLDatatypeBuiltInArgument() throws SWRLBuiltInException;

  /**
   * @return True if the argument is a variable and was bound
   */
  boolean wasBoundVariable();

  /**
   * @return The bound variable name
   */
  @NonNull Optional<@NonNull String> getBoundVariableName();

  /**
   * @param boundVariableName The variable name that the built-in argument is bound to
   */
  void setBoundVariableName(@NonNull String boundVariableName);

  /**
   * @param visitor A visitor
   */
  void accept(@NonNull SWRLBuiltInArgumentVisitor visitor);

  /**
   * @param <T>     Type returned by the visitor
   * @param visitor A visitor
   * @return A result generate by the visitor
   */
  @NonNull <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor);
}
