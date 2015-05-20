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
  SWRLClassBuiltInArgument createClassBuiltInArgument(OWLClass cls);

  /**
   * @param individual An OWL individual
   * @return An individual built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLNamedIndividualBuiltInArgument createIndividualBuiltInArgument(OWLNamedIndividual individual);

  /**
   * @param property An OWL object property
   * @return An object property built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLObjectPropertyBuiltInArgument createObjectPropertyBuiltInArgument(OWLObjectProperty property);

  /**
   * @param property An OWL data property
   * @return A data property built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLDataPropertyBuiltInArgument createDataPropertyBuiltInArgument(OWLDataProperty property);

  /**
   * @param property An OWL annotation property
   * @return An annotation property built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLAnnotationPropertyBuiltInArgument createAnnotationPropertyBuiltInArgument(OWLAnnotationProperty property);

  /**
   * @param datatype An OWL datatype
   * @return An datatype built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLDatatypeBuiltInArgument createDatatypeBuiltInArgument(OWLDatatype datatype);

  /**
   * @param s A string
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(String s);

  /**
   * @param b A Boolean
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(boolean b);

  /**
   * @param b A byte
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(byte b);

  /**
   * @param s A short
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(short s);

  /**
   * @param i An int
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(int i);

  /**
   * @param l A long
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(long l);

  /**
   * @param f A float
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(float f);

  /**
   * @param d A double
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(double d);

  /**
   * @param uri A URI
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(URI uri);

  /**
   * @param date A date
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDate date);

  /**
   * @param time A time
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDTime time);

  /**
   * @param datetime A datetime
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDateTime datetime);

  /**
   * @param duration A duration
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(XSDDuration duration);

  /**
   * @param literal An OWL literal
   * @return A literal built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLLiteralBuiltInArgument createLiteralBuiltInArgument(OWLLiteral literal);

  /**
   * @param variableIRI The IRI of a variable
   * @return A multi-variable built-in argument
   * @throws SWRLBuiltInException If an error occurs during generation
   */
  SWRLMultiValueVariableBuiltInArgument createSWRLMultiValueVariableBuiltInArgument(IRI variableIRI);

  /**
   * @param variableIRI The IRI of a variable
   * @param arguments A list of built-in arguments
   * @return A multi-variable built-in argument
   */
  SWRLMultiValueVariableBuiltInArgument createSWRLMultiValueVariableBuiltInArgument(IRI variableIRI,
      List<SWRLBuiltInArgument> arguments);

  /**
   * @param variableIRI The IRI of a variable
   * @param queryName A SQWRL query name
   * @param collectionName The name of a collection
   * @param collectionGroupID The group ID of the collection
   * @return A collection variable built-in argument
   */
  SQWRLCollectionVariableBuiltInArgument createSQWRLCollectionVariableBuiltInArgument(IRI variableIRI, String queryName,
      String collectionName, String collectionGroupID);
}
