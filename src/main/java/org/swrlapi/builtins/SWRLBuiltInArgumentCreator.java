package org.swrlapi.builtins;

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
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import org.swrlapi.exceptions.SWRLBuiltInException;

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
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLClassBuiltInArgument createClassBuiltInArgument(OWLClass cls) throws SWRLBuiltInException;

  /**
   * @param individual An OWL individual
   * @return An individual built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLNamedIndividualBuiltInArgument createIndividualBuiltInArgument(OWLNamedIndividual individual)
      throws SWRLBuiltInException;

  /**
   * @param property An OWL object property
   * @return An object property built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLObjectPropertyBuiltInArgument createObjectPropertyBuiltInArgument(OWLObjectProperty property)
      throws SWRLBuiltInException;

  /**
   * @param property An OWL data property
   * @return A data property built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLDataPropertyBuiltInArgument createDataPropertyBuiltInArgument(OWLDataProperty property)
      throws SWRLBuiltInException;

  /**
   * @param property An OWL annotation property
   * @return An annotation property built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLAnnotationPropertyBuiltInArgument createAnnotationPropertyBuiltInArgument(OWLAnnotationProperty property)
      throws SWRLBuiltInException;

  /**
   * @param datatype An OWL datatype
   * @return An datatype built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLDatatypeBuiltInArgument createDatatypeBuiltInArgument(OWLDatatype datatype) throws SWRLBuiltInException;

  /**
   * @param s A string
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(String s) throws SWRLBuiltInException;

  /**
   * @param b A Boolean
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(boolean b) throws SWRLBuiltInException;

  /**
   * @param b A byte
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(byte b) throws SWRLBuiltInException;

  /**
   * @param s A short
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(short s) throws SWRLBuiltInException;

  /**
   * @param i An int
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(int i) throws SWRLBuiltInException;

  /**
   * @param l A long
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(long l) throws SWRLBuiltInException;

  /**
   * @param f A float
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(float f) throws SWRLBuiltInException;

  /**
   * @param d A double
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(double d) throws SWRLBuiltInException;

  /**
   * @param uri A URI
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(URI uri) throws SWRLBuiltInException;

  /**
   * @param date A date
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDate date) throws SWRLBuiltInException;

  /**
   * @param time A time
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDTime time) throws SWRLBuiltInException;

  /**
   * @param datetime A datetime
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDateTime datetime) throws SWRLBuiltInException;

  /**
   * @param duration A duration
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDuration duration) throws SWRLBuiltInException;

  /**
   * @param literal An OWL literal
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(OWLLiteral literal) throws SWRLBuiltInException;

  /**
   * @param variableIRI The IRI of a variable
   * @return A multi-variable built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLMultiValueVariableBuiltInArgument createSWRLMultiValueVariableBuiltInArgument(IRI variableIRI)
      throws SWRLBuiltInException;

  /**
   * @param variableIRI The IRI of a variable
   * @param arguments A list of built-in arguments
   * @return A multi-variable built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLMultiValueVariableBuiltInArgument createSWRLMultiValueVariableBuiltInArgument(IRI variableIRI,
      List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException;

  /**
   * @param variableIRI The IRI of a variable
   * @param queryName A SQWRL query name
   * @param collectionName The name of a collection
   * @param collectionGroupID The group ID of the collection
   * @return A collection variable built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SQWRLCollectionVariableBuiltInArgument createSQWRLCollectionVariableBuiltInArgument(IRI variableIRI, String queryName,
      String collectionName, String collectionGroupID) throws SWRLBuiltInException;
}
