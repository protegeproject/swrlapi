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

  public boolean caa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLClassAssertionAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.CLASS_ASSERTION, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<Integer, OWLObject> boundInputArgumentValues = getBoundInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.CLASS, SWRLBuiltInArgumentType.NAMED_INDIVIDUAL);
      Map<Integer, SWRLMultiValueVariableBuiltInArgument> unboundOutputArguments = getUnboundOutputArguments(arguments);
      boolean atLeastOneBoundArgumentUnequal = false;

      for (OWLClassAssertionAxiom axiom : axioms) {
        OWLClassExpression candidateResultArgumentValue1 = axiom.getClassExpression();
        OWLNamedIndividual candidateResultArgumentValue2 = axiom.getIndividual().asOWLNamedIndividual();

        atLeastOneBoundArgumentUnequal = (boundInputArgumentValues.containsKey(0) && !boundInputArgumentValues.get(0)
          .equals(candidateResultArgumentValue1)) || (boundInputArgumentValues.containsKey(1)
          && !boundInputArgumentValues.get(1).equals(candidateResultArgumentValue2));

        if (atLeastOneBoundArgumentUnequal)
          break;

        if (unboundOutputArguments.containsKey(0)) {
          if (candidateResultArgumentValue1.isAnonymous())
            unboundOutputArguments.get(0)
              .addArgument(createClassExpressionBuiltInArgument(candidateResultArgumentValue1));
          else
            unboundOutputArguments.get(0)
              .addArgument(createClassBuiltInArgument(candidateResultArgumentValue1.asOWLClass()));
        }

        if (unboundOutputArguments.containsKey(1))
          unboundOutputArguments.get(1)
            .addArgument(createNamedIndividualBuiltInArgument(candidateResultArgumentValue2));
      }

      if (atLeastOneBoundArgumentUnequal)
        return false;
      else {
        for (Integer argumentNumber : unboundOutputArguments.keySet())
          arguments.get(argumentNumber).asVariable().setBuiltInResult(unboundOutputArguments.get(argumentNumber));
        return true;
      }
    }
  }

  // SAME_INDIVIDUAL, DIFFERENT_INDIVIDUALS, OBJECT_PROPERTY_ASSERTION, NEGATIVE_OBJECT_PROPERTY_ASSERTION,
  // DATA_PROPERTY_ASSERTION, NEGATIVE_DATA_PROPERTY_ASSERTION
}
