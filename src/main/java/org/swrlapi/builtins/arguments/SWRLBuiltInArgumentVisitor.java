package org.swrlapi.builtins.arguments;

import android.annotation.NonNull;

/**
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx
 */
public interface SWRLBuiltInArgumentVisitor
{
  void visit(@NonNull SWRLClassBuiltInArgument argument);

  void visit(@NonNull SWRLClassExpressionBuiltInArgument argument);

  void visit(@NonNull SWRLNamedIndividualBuiltInArgument argument);

  void visit(@NonNull SWRLObjectPropertyBuiltInArgument argument);

  void visit(@NonNull SWRLObjectPropertyExpressionBuiltInArgument argument);

  void visit(@NonNull SWRLDataPropertyBuiltInArgument argument);

  void visit(@NonNull SWRLDataPropertyExpressionBuiltInArgument argument);

  void visit(@NonNull SWRLAnnotationPropertyBuiltInArgument argument);

  void visit(@NonNull SWRLLiteralBuiltInArgument argument);

  void visit(@NonNull SWRLDatatypeBuiltInArgument argument);

  void visit(@NonNull SWRLVariableBuiltInArgument argument);

  void visit(@NonNull SQWRLCollectionVariableBuiltInArgument argument);

  void visit(@NonNull SWRLMultiValueVariableBuiltInArgument argument);
}
