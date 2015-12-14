package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;

import java.net.URI;
import java.util.List;

/**
 * Factory for creating {@link SWRLBuiltInArgument} objects.
 *
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 */
public interface SWRLBuiltInArgumentFactory
{
  /**
   * @param variableIRI The IRI of a SWRL variable
   * @return A built-in argument
   */
  @NonNull SWRLVariableBuiltInArgument getVariableBuiltInArgument(@NonNull IRI variableIRI);

  /**
   * @param variableIRI The IRI of a SWRL variable
   * @return A variable built-in argument
   */
  @NonNull SWRLVariableBuiltInArgument getUnboundVariableBuiltInArgument(@NonNull IRI variableIRI);

  /**
   * @param cls An OWL class
   * @return A built-in argument
   */
  @NonNull SWRLClassBuiltInArgument getClassBuiltInArgument(@NonNull OWLClass cls);

  /**
   * @param individual An OWL individual
   * @return A built-in argument
   */
  @NonNull SWRLNamedIndividualBuiltInArgument getNamedIndividualBuiltInArgument(@NonNull OWLNamedIndividual individual);

  /**
   * @param property An OWL object property
   * @return A built-in argument
   */
  @NonNull SWRLObjectPropertyBuiltInArgument getObjectPropertyBuiltInArgument(@NonNull OWLObjectProperty property);

  /**
   * @param property An OWL data property
   * @return A built-in argument
   */
  @NonNull SWRLDataPropertyBuiltInArgument getDataPropertyBuiltInArgument(@NonNull OWLDataProperty property);

  /**
   * @param property An OWL annotation property
   * @return A built-in argument
   */
  @NonNull SWRLAnnotationPropertyBuiltInArgument getAnnotationPropertyBuiltInArgument(
    @NonNull OWLAnnotationProperty property);

  /**
   * @param datatype An OWL datatype
   * @return A built-in argument
   */
  @NonNull SWRLDatatypeBuiltInArgument getDatatypeBuiltInArgument(@NonNull OWLDatatype datatype);

  /**
   * @param literal An OWL literal
   * @return A built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(@NonNull OWLLiteral literal);

  /**
   * @param s A string
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(@NonNull String s);

  /**
   * @param b A boolean
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(boolean b);

  /**
   * @param s A String
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(short s);

  /**
   * @param i An int
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(int i);

  /**
   * @param l A long
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(long l);

  /**
   * @param f A float
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(float f);

  /**
   * @param d A double
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(double d);

  /**
   * @param b A byte
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(byte b);

  /**
   * @param iri An IRI
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(@NonNull URI iri);

  /**
   * @param date A date
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(@NonNull XSDDate date);

  /**
   * @param time A time
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(@NonNull XSDTime time);

  /**
   * @param datetime A datetime
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(@NonNull XSDDateTime datetime);

  /**
   * @param duration A duration
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(@NonNull XSDDuration duration);

  /**
   * @param variableIRI The IRI of a SWRL variable
   * @return A built-in argument
   */
  @NonNull SWRLMultiValueVariableBuiltInArgument getMultiValueVariableBuiltInArgument(@NonNull IRI variableIRI);

  /**
   * @param variableIRI The IRI of a SWRL variable
   * @param arguments   Built-in arguments
   * @return A built-in argument
   */
  @NonNull SWRLMultiValueVariableBuiltInArgument getMultiValueVariableBuiltInArgument(@NonNull IRI variableIRI,
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments);

  /**
   * @param variableIRI    The IRI of a SWRL variable
   * @param queryName      The name of the query containing the collection
   * @param collectionName The name of the collection
   * @param collectionID   The collection ID
   * @return A built-in argument
   */
  @NonNull SQWRLCollectionVariableBuiltInArgument getSQWRLCollectionVariableBuiltInArgument(@NonNull IRI variableIRI,
    @NonNull String queryName, @NonNull String collectionName, @NonNull String collectionID);
}
