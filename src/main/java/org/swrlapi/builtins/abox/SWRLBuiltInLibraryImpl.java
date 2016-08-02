package org.swrlapi.builtins.abox;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

import java.util.HashMap;
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

    OWLOntology ontology = getBuiltInBridge().getOWLOntology();
    Set<OWLClassAssertionAxiom> axioms = ontology.getAxioms(AxiomType.CLASS_ASSERTION, Imports.INCLUDED);
    boolean atLeastOneBoundArgumentUnequal = false;

    if (!axioms.isEmpty()) {
      Map<Integer, OWLObject> boundArgumentValues = new HashMap<>();
      Map<Integer, SWRLMultiValueVariableBuiltInArgument> unboundResultArguments = new HashMap<>();

      for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++) {
        if (arguments.get(argumentNumber).isVariable()) {
          IRI variableIRI = arguments.get(argumentNumber).asVariable().getIRI();
          unboundResultArguments.put(argumentNumber, createSWRLMultiValueVariableBuiltInArgument(variableIRI));
        } else {
          if (argumentNumber == 0) {
            checkThatArgumentIsAClassExpression(argumentNumber, arguments);
            boundArgumentValues.put(argumentNumber,
              arguments.get(argumentNumber).asSWRLClassExpressionBuiltInArgument().getOWLClassExpression());
          } else if (argumentNumber == 1) {
            checkThatArgumentIsANamedIndividual(argumentNumber, arguments);
            boundArgumentValues.put(argumentNumber,
              arguments.get(argumentNumber).asSWRLNamedIndividualBuiltInArgument().getOWLNamedIndividual());
          }
        }
      }

      for (OWLClassAssertionAxiom axiom : axioms) {
        OWLClassExpression crav1 = axiom.getClassExpression();
        OWLNamedIndividual crav2 = axiom.getIndividual().asOWLNamedIndividual(); // We do not handle anonymous

        atLeastOneBoundArgumentUnequal =
          (boundArgumentValues.containsKey(0) && !boundArgumentValues.get(0).equals(crav1)) || (
            boundArgumentValues.containsKey(1) && !boundArgumentValues.get(1).equals(crav2));

        if (atLeastOneBoundArgumentUnequal)
          break;

        if (unboundResultArguments.containsKey(0)) {
          if (crav1.isAnonymous())
            unboundResultArguments.get(0).addArgument(createClassExpressionBuiltInArgument(crav1));
          else
            unboundResultArguments.get(0).addArgument(createClassBuiltInArgument(crav1.asOWLClass()));
        }

        if (unboundResultArguments.containsKey(1))
          unboundResultArguments.get(1).addArgument(createNamedIndividualBuiltInArgument(crav2));
      }

      if (atLeastOneBoundArgumentUnequal)
        return false;
      else {
        for (Integer argumentNumber : unboundResultArguments.keySet())
          arguments.get(argumentNumber).asVariable().setBuiltInResult(unboundResultArguments.get(argumentNumber));
        return true;
      }
    } else
      return false;
  }

  // SAME_INDIVIDUAL, DIFFERENT_INDIVIDUALS, OBJECT_PROPERTY_ASSERTION, NEGATIVE_OBJECT_PROPERTY_ASSERTION,
  // DATA_PROPERTY_ASSERTION, NEGATIVE_DATA_PROPERTY_ASSERTION
}
