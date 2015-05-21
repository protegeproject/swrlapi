package org.swrlapi.builtins.arguments;

import checkers.nullness.quals.NonNull;

/**
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor
 */
public interface SWRLBuiltInArgumentVisitorEx<T>
{
  @NonNull T visit(@NonNull SWRLClassBuiltInArgument argument);

  @NonNull T visit(@NonNull SWRLNamedIndividualBuiltInArgument argument);

  @NonNull T visit(@NonNull SWRLObjectPropertyBuiltInArgument argument);

  @NonNull T visit(@NonNull SWRLDataPropertyBuiltInArgument argument);

  @NonNull T visit(@NonNull SWRLAnnotationPropertyBuiltInArgument argument);

  @NonNull T visit(@NonNull SWRLDatatypeBuiltInArgument argument);

  @NonNull T visit(@NonNull SWRLLiteralBuiltInArgument argument);

  @NonNull T visit(@NonNull SWRLVariableBuiltInArgument argument);

  @NonNull T visit(@NonNull SWRLMultiValueVariableBuiltInArgument argument);

  @NonNull T visit(@NonNull SQWRLCollectionVariableBuiltInArgument argument);
}
