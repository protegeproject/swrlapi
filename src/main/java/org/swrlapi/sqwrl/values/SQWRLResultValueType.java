package org.swrlapi.sqwrl.values;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class SQWRLResultValueType<E extends SQWRLResultValue>
{
  @NonNull public static final SQWRLResultValueType<SQWRLClassResultValue> CLASS;
  @NonNull public static final SQWRLResultValueType<SQWRLClassExpressionResultValue> CLASS_EXPRESSION;
  @NonNull public static final SQWRLResultValueType<SQWRLIndividualResultValue> INDIVIDUAL;
  @NonNull public static final SQWRLResultValueType<SQWRLNamedIndividualResultValue> NAMED_INDIVIDUAL;
  @NonNull public static final SQWRLResultValueType<SQWRLObjectPropertyResultValue> OBJECT_PROPERTY;
  @NonNull public static final SQWRLResultValueType<SQWRLObjectPropertyExpressionResultValue> OBJECT_PROPERTY_EXPRESSION;
  @NonNull public static final SQWRLResultValueType<SQWRLDataPropertyResultValue> DATA_PROPERTY;
  @NonNull public static final SQWRLResultValueType<SQWRLDataPropertyExpressionResultValue> DATA_PROPERTY_EXPRESSION;
  @NonNull public static final SQWRLResultValueType<SQWRLAnnotationPropertyResultValue> ANNOTATION_PROPERTY;
  @NonNull public static final SQWRLResultValueType<SQWRLDatatypeResultValue> DATATYPE;
  @NonNull public static final SQWRLResultValueType<SQWRLDataRangeResultValue> DATA_RANGE;
  @NonNull public static final SQWRLResultValueType<SQWRLLiteralResultValue> LITERAL;

  public static final @NonNull List<SQWRLResultValueType<?>> VALUES;

  private SQWRLResultValueType() {}

  static {
    CLASS = new SQWRLResultValueType<>();
    CLASS_EXPRESSION = new SQWRLResultValueType<>();
    INDIVIDUAL = new SQWRLResultValueType<>();
    NAMED_INDIVIDUAL = new SQWRLResultValueType<>();
    OBJECT_PROPERTY = new SQWRLResultValueType<>();
    OBJECT_PROPERTY_EXPRESSION = new SQWRLResultValueType<>();
    DATA_PROPERTY = new SQWRLResultValueType<>();
    DATA_PROPERTY_EXPRESSION = new SQWRLResultValueType<>();
    ANNOTATION_PROPERTY = new SQWRLResultValueType<>();
    DATATYPE = new SQWRLResultValueType<>();
    DATA_RANGE = new SQWRLResultValueType<>();
    LITERAL = new SQWRLResultValueType<>();
    VALUES = Collections.unmodifiableList(Arrays.asList(
      new SQWRLResultValueType<?>[] { CLASS, CLASS_EXPRESSION, NAMED_INDIVIDUAL, INDIVIDUAL, OBJECT_PROPERTY,
        OBJECT_PROPERTY_EXPRESSION, DATA_PROPERTY, DATA_PROPERTY_EXPRESSION, ANNOTATION_PROPERTY, DATATYPE, DATA_RANGE,
        LITERAL }));
  }
}
