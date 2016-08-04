package org.swrlapi.builtins.abox;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentType;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation library for SWRL ABox built-ins
 */
public class SWRLBuiltInLibraryImpl extends AbstractSWRLBuiltInLibrary
{
  private static final String SWRLABoxLibraryName = "SWRLABoxBuiltIns";

  public SWRLBuiltInLibraryImpl()
  {
    super(SWRLABoxLibraryName);
  }

  @Override public void reset()
  {
  }

  private boolean atLeastOneBoundArgumentUnequal(Map<Integer, @NonNull OWLObject> boundInputArgumentValues,
    OWLObject... candidateValues) throws SWRLBuiltInException
  {
    if (boundInputArgumentValues.size() != candidateValues.length)
      throw new SWRLBuiltInException(
        "internal error: expecting " + boundInputArgumentValues.size() + " candidates for bound argument values, got"
          + candidateValues.length);

    for (int argumentNumber : boundInputArgumentValues.keySet()) {
      OWLObject owlObject = boundInputArgumentValues.get(argumentNumber);
      if (!owlObject.equals(candidateValues[argumentNumber]))
        return false;
    }
    return true;
  }

  public boolean caa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLClassAssertionAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.CLASS_ASSERTION, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<Integer, @NonNull OWLObject> boundInputArgumentValues = getBoundInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.CLASS, SWRLBuiltInArgumentType.NAMED_INDIVIDUAL);
      Map<Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> unboundOutputMultiValueArguments = createUnboundOutputMultiValueArguments(
        arguments);

      for (OWLClassAssertionAxiom axiom : axioms) {
        OWLClassExpression candidateValue1 = axiom.getClassExpression();
        OWLNamedIndividual candidateValue2 = axiom.getIndividual().asOWLNamedIndividual();

        if (atLeastOneBoundArgumentUnequal(boundInputArgumentValues, candidateValue1, candidateValue2))
          return false;

        if (unboundOutputMultiValueArguments.containsKey(0))
          unboundOutputMultiValueArguments.get(0).addArgument(createClassExpressionBuiltInArgument(candidateValue1));

        if (unboundOutputMultiValueArguments.containsKey(1))
          unboundOutputMultiValueArguments.get(1).addArgument(createNamedIndividualBuiltInArgument(candidateValue2));
      }

      for (Integer argumentNumber : unboundOutputMultiValueArguments.keySet())
        arguments.get(argumentNumber).asVariable()
          .setBuiltInResult(unboundOutputMultiValueArguments.get(argumentNumber));
      return true;
    }
  }

  // SAME_INDIVIDUAL, DIFFERENT_INDIVIDUALS, OBJECT_PROPERTY_ASSERTION, NEGATIVE_OBJECT_PROPERTY_ASSERTION,
  // DATA_PROPERTY_ASSERTION, NEGATIVE_DATA_PROPERTY_ASSERTION
}
