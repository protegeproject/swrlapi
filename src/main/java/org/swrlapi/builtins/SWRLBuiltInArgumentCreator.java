package org.swrlapi.builtins;

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
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.List;

/**
 * Utility methods that can be used by built-ins to create SWRL built-in result arguments.
 *
 * @see org.swrlapi.builtins.AbstractSWRLBuiltInLibrary
 */
public interface SWRLBuiltInArgumentCreator
{
  /**
   * @param cls An OWL class
   * @return A class built-in argument
   */
  @NonNull SWRLClassBuiltInArgument createClassBuiltInArgument(@NonNull OWLClass cls);

  /**
   * @param individual An OWL individual
   * @return An individual built-in argument
   */
  @NonNull SWRLNamedIndividualBuiltInArgument createIndividualBuiltInArgument(@NonNull OWLNamedIndividual individual);

  /**
   * @param property An OWL object property
   * @return An object property built-in argument
   */
  @NonNull SWRLObjectPropertyBuiltInArgument createObjectPropertyBuiltInArgument(@NonNull OWLObjectProperty property);

  /**
   * @param property An OWL data property
   * @return A data property built-in argument
   */
  @NonNull SWRLDataPropertyBuiltInArgument createDataPropertyBuiltInArgument(@NonNull OWLDataProperty property);

  /**
   * @param property An OWL annotation property
   * @return An annotation property built-in argument
   */
  @NonNull SWRLAnnotationPropertyBuiltInArgument createAnnotationPropertyBuiltInArgument(
    @NonNull OWLAnnotationProperty property);

  /**
   * @param datatype An OWL datatype
   * @return An datatype built-in argument
   */
  @NonNull SWRLDatatypeBuiltInArgument createDatatypeBuiltInArgument(@NonNull OWLDatatype datatype);

  /**
   * @param s A string
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(@NonNull String s);

  /**
   * @param b A Boolean
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(boolean b);

  /**
   * @param b A byte
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(byte b);

  /**
   * @param s A short
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(short s);

  /**
   * @param i An int
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(int i);

  /**
   * @param l A long
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(long l);

  /**
   * @param f A float
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(float f);

  /**
   * @param d A double
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(double d);

  /**
   * @param d A big decimal
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(@NonNull BigDecimal d);

  /**
   * @param i A big integer
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(@NonNull BigInteger i);

  /**
   * @param uri A URI
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(@NonNull URI uri);

  /**
   * @param date A date
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(@NonNull XSDDate date);

  /**
   * @param time A time
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(@NonNull XSDTime time);

  /**
   * @param datetime A datetime
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(@NonNull XSDDateTime datetime);

  /**
   * @param duration A duration
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(@NonNull XSDDuration duration);

  /**
   * @param literal An OWL literal
   * @return A literal built-in argument
   */
  @NonNull SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(@NonNull OWLLiteral literal);

  /**
   * @param variableIRI The IRI of a variable
   * @return A multi-variable built-in argument
   */
  @NonNull SWRLMultiValueVariableBuiltInArgument createSWRLMultiValueVariableBuiltInArgument(@NonNull IRI variableIRI);

  /**
   * @param variableIRI The IRI of a SWRL variable
   * @param arguments   A list of built-in arguments
   * @return A multi-variable built-in argument
   */
  @NonNull SWRLMultiValueVariableBuiltInArgument createSWRLMultiValueVariableBuiltInArgument(@NonNull IRI variableIRI,
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments);

  /**
   * @param variableIRI       The IRI of a SWRL variable
   * @param queryName         A SQWRL query name
   * @param collectionName    The name of a collection
   * @param collectionGroupID The group ID of the collection
   * @return A collection variable built-in argument
   */
  @NonNull SQWRLCollectionVariableBuiltInArgument createSQWRLCollectionVariableBuiltInArgument(@NonNull IRI variableIRI,
    @NonNull String queryName, @NonNull String collectionName, @NonNull String collectionGroupID);
}
