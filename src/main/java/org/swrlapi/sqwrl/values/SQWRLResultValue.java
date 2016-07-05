package org.swrlapi.sqwrl.values;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 * Base result value provided by a {@link org.swrlapi.sqwrl.SQWRLResult}.
 *
 * @see org.swrlapi.sqwrl.values.SQWRLEntityResultValue
 * @see org.swrlapi.sqwrl.values.SQWRLClassResultValue
 * @see SQWRLNamedIndividualResultValue
 * @see org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue
 * @see org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue
 * @see org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue
 * @see org.swrlapi.sqwrl.values.SQWRLLiteralResultValue
 */
public interface SQWRLResultValue
{
  /**
   * @return True if the result value is an OWL entity
   */
  boolean isEntity();

  /**
   * @return True if the result value is an OWL expression (object or datatype)
   */
  boolean isExpression();

  /**
   * @return True if the result value is an OWL literal
   */
  boolean isLiteral();

  /**
   * @return True if the result value is an OWL class
   */
  boolean isClass();

  /**
   * @return True if the result value is an OWL class expression
   */
  boolean isClassExpression();

  /**
   * @return True if the result value is a named OWL individual
   */
  boolean isNamedIndividual();

  /**
   * @return True if the result value is an OWL object property
   */
  boolean isObjectProperty();

  /**
   * @return True if the result value is an OWL object property expression
   */
  boolean isObjectPropertyExpression();

  /**
   * @return True if the result value is an OWL data property
   */
  boolean isDataProperty();

  /**
   * @return True if the result value is an OWL data property expression
   */
  boolean isDataPropertyExpression();

  /**
   * @return True if the result value is an OWL annotation property
   */
  boolean isAnnotationProperty();

  /**
   * @return True if the result value is an OWL datatype
   */
  boolean isDatatype();

  /**
   * @return A SQWRL entity result value
   * @throws SQWRLException If the result value cannot be converted to an OWL entity result value
   */
  @NonNull SQWRLEntityResultValue asEntityResult() throws SQWRLException;

  /**
   * @return A SQWRL expression result value
   * @throws SQWRLException If the result value cannot be converted to an expression result value
   */
  @NonNull SQWRLExpressionResultValue asExpressionResult() throws SQWRLException;

  /**
   * @return A SQWRL class result value
   * @throws SQWRLException If the result value cannot be converted to a class result value
   */
  @NonNull SQWRLClassResultValue asClassResult() throws SQWRLException;

  /**
   * @return A SQWRL class expression result value
   * @throws SQWRLException If the result value cannot be converted to a class result value
   */
  @NonNull SQWRLClassExpressionResultValue asClassExpressionResult() throws SQWRLException;

  /**
   * @return A SQWRL entity result value
   * @throws SQWRLException If the result value cannot be converted to an individual result value
   */
  @NonNull SQWRLNamedIndividualResultValue asNamedIndividualResult() throws SQWRLException;

  /**
   * @return A SQWRL entity result value
   * @throws SQWRLException If the result value cannot be converted to an object property result value
   */
  @NonNull SQWRLObjectPropertyResultValue asObjectPropertyResult() throws SQWRLException;

  /**
   * @return A SQWRL object property expression result value
   * @throws SQWRLException If the result value cannot be converted to an object property expression result value
   */
  @NonNull SQWRLObjectPropertyExpressionResultValue asObjectPropertyExpressionResult() throws SQWRLException;

  /**
   * @return A SQWRL entity result value
   * @throws SQWRLException If the result value cannot be converted to a data property result value
   */
  @NonNull SQWRLDataPropertyResultValue asDataPropertyResult() throws SQWRLException;

  /**
   * @return A SQWRL data property expression result value
   * @throws SQWRLException If the result value cannot be converted to a data property expression result value
   */
  @NonNull SQWRLDataPropertyExpressionResultValue asDataPropertyExpressionResult() throws SQWRLException;

  /**
   * @return A SQWRL annotation property result value
   * @throws SQWRLException If the result value cannot be converted to an annotation property result value
   */
  @NonNull SQWRLAnnotationPropertyResultValue asAnnotationPropertyResult() throws SQWRLException;

  /**
   * @return A SQWRL datatype result value
   * @throws SQWRLException If the result value cannot be converted to a datatype result value
   */
  @NonNull SQWRLDatatypeResultValue asDatatypeResult() throws SQWRLException;

  /**
   * @return A SQWRL entity result value
   * @throws SQWRLException If the result value cannot be converted to a literal result value
   */
  @NonNull SQWRLLiteralResultValue asLiteralResult() throws SQWRLException;
}