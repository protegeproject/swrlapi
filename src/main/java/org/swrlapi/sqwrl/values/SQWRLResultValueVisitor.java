package org.swrlapi.sqwrl.values;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface SQWRLResultValueVisitor<T>
{
  @NonNull T visit(SQWRLEntityResultValue value);

  @NonNull T visit(SQWRLClassResultValue value);

  @NonNull T visit(SQWRLClassExpressionResultValue value);

  @NonNull T visit(SQWRLIndividualResultValue value);

  @NonNull T visit(SQWRLNamedIndividualResultValue value);

  @NonNull T visit(SQWRLObjectPropertyResultValue value);

  @NonNull T visit(SQWRLObjectPropertyExpressionResultValue value);

  @NonNull T visit(SQWRLDataPropertyResultValue value);

  @NonNull T visit(SQWRLDataPropertyExpressionResultValue value);

  @NonNull T visit(SQWRLAnnotationPropertyResultValue value);

  @NonNull T visit(SQWRLDatatypeResultValue value);

  @NonNull T visit(SQWRLDataRangeResultValue value);

  @NonNull T visit(SQWRLLiteralResultValue value);
}
