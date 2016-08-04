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

  private boolean atLeastOneBoundArgumentUnequal(Map<Integer, @NonNull OWLObject> inputArgumentValues,
    OWLObject... candidateValues) throws SWRLBuiltInException
  {
    for (int argumentNumber : inputArgumentValues.keySet()) {
      OWLObject inputArgumentValue = inputArgumentValues.get(argumentNumber);
      if (!inputArgumentValue.equals(candidateValues[argumentNumber]))
        return true;
    }
    return false;
  }

  public boolean caa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLClassAssertionAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.CLASS_ASSERTION, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.CLASS, SWRLBuiltInArgumentType.NAMED_INDIVIDUAL);
      Map<Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLClassAssertionAxiom axiom : axioms) {
        OWLClassExpression candidateValue1 = axiom.getClassExpression();
        OWLNamedIndividual candidateValue2 = axiom.getIndividual().asOWLNamedIndividual();

        if (atLeastOneBoundArgumentUnequal(inputArgumentValues, candidateValue1, candidateValue2))
          return false;

        if (outputMultiValueArguments.containsKey(0))
          outputMultiValueArguments.get(0).addArgument(createClassExpressionBuiltInArgument(candidateValue1));

        if (outputMultiValueArguments.containsKey(1))
          outputMultiValueArguments.get(1).addArgument(createNamedIndividualBuiltInArgument(candidateValue2));
      }

      for (Integer argumentNumber : outputMultiValueArguments.keySet())
        arguments.get(argumentNumber).asVariable().setBuiltInResult(outputMultiValueArguments.get(argumentNumber));
      return true;
    }
  }

  // SAME_INDIVIDUAL, DIFFERENT_INDIVIDUALS, OBJECT_PROPERTY_ASSERTION, NEGATIVE_OBJECT_PROPERTY_ASSERTION,
  // DATA_PROPERTY_ASSERTION, NEGATIVE_DATA_PROPERTY_ASSERTION
}
