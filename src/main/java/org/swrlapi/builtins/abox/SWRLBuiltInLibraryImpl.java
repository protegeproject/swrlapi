package org.swrlapi.builtins.abox;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentType;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

  private boolean noBoundArgumentsMismatch(Map<Integer, @NonNull OWLObject> inputArgumentValues,
    OWLObject... candidateValues) throws SWRLBuiltInException
  {
    for (int argumentNumber : inputArgumentValues.keySet()) {
      OWLObject inputArgumentValue = inputArgumentValues.get(argumentNumber);
      if (!inputArgumentValue.equals(candidateValues[argumentNumber]))
        return true;
    }
    return false;
  }

  private boolean processOutputMultiValueArguments(@NonNull List<@NonNull SWRLBuiltInArgument> arguments,
    Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments)
    throws SWRLBuiltInException
  {
    if (outputMultiValueArguments.values().stream().filter(a -> a.hasArguments()).collect(Collectors.toSet())
      .isEmpty()) // No output multi-value arguments have content
      return false;
    else {
      for (Integer argumentNumber : outputMultiValueArguments.keySet())
        arguments.get(argumentNumber).asVariable().setBuiltInResult(outputMultiValueArguments.get(argumentNumber));
      return true;
    }
  }

  public boolean caa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLClassAssertionAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.CLASS_ASSERTION, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.CLASS, SWRLBuiltInArgumentType.NAMED_INDIVIDUAL);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLClassAssertionAxiom axiom : axioms) {
        OWLClassExpression candidateValue1 = axiom.getClassExpression();
        OWLNamedIndividual candidateValue2 = axiom.getIndividual().asOWLNamedIndividual();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0).addArgument(createClassExpressionBuiltInArgument(candidateValue1));

            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1).addArgument(createNamedIndividualBuiltInArgument(candidateValue2));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean sia(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLSameIndividualAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.SAME_INDIVIDUAL, Imports.INCLUDED).stream().flatMap(a -> a.asPairwiseAxioms().stream())
      .collect(Collectors.toSet());

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.NAMED_INDIVIDUAL, SWRLBuiltInArgumentType.NAMED_INDIVIDUAL);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLSameIndividualAxiom axiom : axioms) {
        OWLNamedIndividual candidateValue1 = axiom.getIndividualsAsList().get(0).asOWLNamedIndividual();
        OWLNamedIndividual candidateValue2 = axiom.getIndividualsAsList().get(1).asOWLNamedIndividual();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0).addArgument(createNamedIndividualBuiltInArgument(candidateValue1));

            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1).addArgument(createNamedIndividualBuiltInArgument(candidateValue2));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean dia(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLDifferentIndividualsAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.DIFFERENT_INDIVIDUALS, Imports.INCLUDED).stream().flatMap(a -> a.asPairwiseAxioms().stream())
      .collect(Collectors.toSet());

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.NAMED_INDIVIDUAL, SWRLBuiltInArgumentType.NAMED_INDIVIDUAL);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLDifferentIndividualsAxiom axiom : axioms) {
        OWLNamedIndividual candidateValue1 = axiom.getIndividualsAsList().get(0).asOWLNamedIndividual();
        OWLNamedIndividual candidateValue2 = axiom.getIndividualsAsList().get(1).asOWLNamedIndividual();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0).addArgument(createNamedIndividualBuiltInArgument(candidateValue1));

            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1).addArgument(createNamedIndividualBuiltInArgument(candidateValue2));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  // OBJECT_PROPERTY_ASSERTION, NEGATIVE_OBJECT_PROPERTY_ASSERTION,
  // DATA_PROPERTY_ASSERTION, NEGATIVE_DATA_PROPERTY_ASSERTION
}
