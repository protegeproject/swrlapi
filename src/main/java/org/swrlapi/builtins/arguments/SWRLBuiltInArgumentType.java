package org.swrlapi.builtins.arguments;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class SWRLBuiltInArgumentType<E extends SWRLBuiltInArgument>
{
  @NonNull public static final SWRLBuiltInArgumentType<SWRLVariableBuiltInArgument> VARIABLE;
  @NonNull public static final SWRLBuiltInArgumentType<SWRLMultiValueVariableBuiltInArgument> MULTI_VALUE_VARIABLE;
  @NonNull public static final SWRLBuiltInArgumentType<SQWRLCollectionVariableBuiltInArgument> COLLECTION_VARIABLE;
  @NonNull public static final SWRLBuiltInArgumentType<SWRLLiteralBuiltInArgument> LITERAL;
  @NonNull public static final SWRLBuiltInArgumentType<SWRLClassBuiltInArgument> CLASS;
  @NonNull public static final SWRLBuiltInArgumentType<SWRLClassExpressionBuiltInArgument> CLASS_EXPRESSION;
  @NonNull public static final SWRLBuiltInArgumentType<SWRLNamedIndividualBuiltInArgument> NAMED_INDIVIDUAL;
  @NonNull public static final SWRLBuiltInArgumentType<SWRLObjectPropertyBuiltInArgument> OBJECT_PROPERTY;
  @NonNull public static final SWRLBuiltInArgumentType<SWRLObjectPropertyExpressionBuiltInArgument> OBJECT_PROPERTY_EXPRESSION;
  @NonNull public static final SWRLBuiltInArgumentType<SWRLDataPropertyBuiltInArgument> DATA_PROPERTY;
  @NonNull public static final SWRLBuiltInArgumentType<SWRLDataPropertyExpressionBuiltInArgument> DATA_PROPERTY_EXPRESSION;
  @NonNull public static final SWRLBuiltInArgumentType<SWRLAnnotationPropertyBuiltInArgument> ANNOTATION_PROPERTY;
  @NonNull public static final SWRLBuiltInArgumentType<SWRLDatatypeBuiltInArgument> DATATYPE;

  @NonNull public static final List<SWRLBuiltInArgumentType<?>> VALUES;

  private SWRLBuiltInArgumentType() {}

  static {
    VARIABLE = new SWRLBuiltInArgumentType();
    MULTI_VALUE_VARIABLE = new SWRLBuiltInArgumentType();
    COLLECTION_VARIABLE = new SWRLBuiltInArgumentType();
    LITERAL = new SWRLBuiltInArgumentType();
    CLASS = new SWRLBuiltInArgumentType();
    CLASS_EXPRESSION = new SWRLBuiltInArgumentType();
    NAMED_INDIVIDUAL = new SWRLBuiltInArgumentType();
    OBJECT_PROPERTY = new SWRLBuiltInArgumentType();
    OBJECT_PROPERTY_EXPRESSION = new SWRLBuiltInArgumentType();
    DATA_PROPERTY = new SWRLBuiltInArgumentType();
    DATA_PROPERTY_EXPRESSION = new SWRLBuiltInArgumentType();
    ANNOTATION_PROPERTY = new SWRLBuiltInArgumentType();
    DATATYPE = new SWRLBuiltInArgumentType();
    VALUES = Collections.unmodifiableList(Arrays.asList(
      new SWRLBuiltInArgumentType<?>[] { VARIABLE, MULTI_VALUE_VARIABLE, COLLECTION_VARIABLE, LITERAL, CLASS,
        CLASS_EXPRESSION, NAMED_INDIVIDUAL, OBJECT_PROPERTY, OBJECT_PROPERTY_EXPRESSION, DATA_PROPERTY,
        DATA_PROPERTY_EXPRESSION, ANNOTATION_PROPERTY, DATATYPE }));
  }
}
