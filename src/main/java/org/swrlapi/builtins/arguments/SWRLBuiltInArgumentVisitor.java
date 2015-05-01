package org.swrlapi.builtins.arguments;

/**
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx
 */
public interface SWRLBuiltInArgumentVisitor
{
  void visit(SWRLClassBuiltInArgument argument);

  void visit(SWRLNamedIndividualBuiltInArgument argument);

  void visit(SWRLObjectPropertyBuiltInArgument argument);

  void visit(SWRLDataPropertyBuiltInArgument argument);

  void visit(SWRLAnnotationPropertyBuiltInArgument argument);

  void visit(SWRLLiteralBuiltInArgument argument);

  void visit(SWRLDatatypeBuiltInArgument argument);

  void visit(SWRLVariableBuiltInArgument argument);

  void visit(SQWRLCollectionVariableBuiltInArgument argument);

  void visit(SWRLMultiValueVariableBuiltInArgument argument);
}
