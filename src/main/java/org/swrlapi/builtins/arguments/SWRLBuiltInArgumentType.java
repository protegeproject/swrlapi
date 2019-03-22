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
    VARIABLE = new SWRLBuiltInArgumentType<>();
    MULTI_VALUE_VARIABLE = new SWRLBuiltInArgumentType<SWRLMultiValueVariableBuiltInArgument>();
    COLLECTION_VARIABLE = new SWRLBuiltInArgumentType<SQWRLCollectionVariableBuiltInArgument>();
    LITERAL = new SWRLBuiltInArgumentType<SWRLLiteralBuiltInArgument>();
    CLASS = new SWRLBuiltInArgumentType<SWRLClassBuiltInArgument>();
    CLASS_EXPRESSION = new SWRLBuiltInArgumentType<SWRLClassExpressionBuiltInArgument>();
    NAMED_INDIVIDUAL = new SWRLBuiltInArgumentType<SWRLNamedIndividualBuiltInArgument>();
    OBJECT_PROPERTY = new SWRLBuiltInArgumentType<SWRLObjectPropertyBuiltInArgument>();
    OBJECT_PROPERTY_EXPRESSION = new SWRLBuiltInArgumentType<SWRLObjectPropertyExpressionBuiltInArgument>();
    DATA_PROPERTY = new SWRLBuiltInArgumentType<SWRLDataPropertyBuiltInArgument>();
    DATA_PROPERTY_EXPRESSION = new SWRLBuiltInArgumentType<SWRLDataPropertyExpressionBuiltInArgument>();
    ANNOTATION_PROPERTY = new SWRLBuiltInArgumentType<SWRLAnnotationPropertyBuiltInArgument>();
    DATATYPE = new SWRLBuiltInArgumentType<SWRLDatatypeBuiltInArgument>();
    VALUES = Collections.unmodifiableList(Arrays.asList(
      new SWRLBuiltInArgumentType<?>[] { VARIABLE, MULTI_VALUE_VARIABLE, COLLECTION_VARIABLE, LITERAL, CLASS,
        CLASS_EXPRESSION, NAMED_INDIVIDUAL, OBJECT_PROPERTY, OBJECT_PROPERTY_EXPRESSION, DATA_PROPERTY,
        DATA_PROPERTY_EXPRESSION, ANNOTATION_PROPERTY, DATATYPE }));
  }
}
