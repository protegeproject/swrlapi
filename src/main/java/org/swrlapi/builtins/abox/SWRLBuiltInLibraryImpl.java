package org.swrlapi.builtins.abox;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
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

  public boolean opaa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    Set<OWLObjectPropertyAssertionAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.NAMED_INDIVIDUAL, SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION,
        SWRLBuiltInArgumentType.NAMED_INDIVIDUAL);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLObjectPropertyAssertionAxiom axiom : axioms) {
        OWLNamedIndividual candidateValue1 = axiom.getSubject().asOWLNamedIndividual();
        OWLObjectPropertyExpression candidateValue2 = axiom.getProperty();
        OWLNamedIndividual candidateValue3 = axiom.getObject().asOWLNamedIndividual();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2, candidateValue3)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0).addArgument(createNamedIndividualBuiltInArgument(candidateValue1));

            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue2));

            if (outputMultiValueArguments.containsKey(2))
              outputMultiValueArguments.get(2).addArgument(createNamedIndividualBuiltInArgument(candidateValue3));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean nopaa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    Set<OWLNegativeObjectPropertyAssertionAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.NAMED_INDIVIDUAL, SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION,
        SWRLBuiltInArgumentType.NAMED_INDIVIDUAL);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLNegativeObjectPropertyAssertionAxiom axiom : axioms) {
        OWLNamedIndividual candidateValue1 = axiom.getSubject().asOWLNamedIndividual();
        OWLObjectPropertyExpression candidateValue2 = axiom.getProperty();
        OWLNamedIndividual candidateValue3 = axiom.getObject().asOWLNamedIndividual();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2, candidateValue3)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0).addArgument(createNamedIndividualBuiltInArgument(candidateValue1));

            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue2));

            if (outputMultiValueArguments.containsKey(2))
              outputMultiValueArguments.get(2).addArgument(createNamedIndividualBuiltInArgument(candidateValue3));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean dpaa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    Set<OWLDataPropertyAssertionAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.DATA_PROPERTY_ASSERTION, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.NAMED_INDIVIDUAL, SWRLBuiltInArgumentType.DATA_PROPERTY_EXPRESSION,
        SWRLBuiltInArgumentType.LITERAL);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLDataPropertyAssertionAxiom axiom : axioms) {
        OWLNamedIndividual candidateValue1 = axiom.getSubject().asOWLNamedIndividual();
        OWLDataPropertyExpression candidateValue2 = axiom.getProperty();
        OWLLiteral candidateValue3 = axiom.getObject();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2, candidateValue3)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0).addArgument(createNamedIndividualBuiltInArgument(candidateValue1));

            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1)
                .addArgument(createDataPropertyExpressionBuiltInArgument(candidateValue2));

            if (outputMultiValueArguments.containsKey(2))
              outputMultiValueArguments.get(2).addArgument(createLiteralBuiltInArgument(candidateValue3));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean ndpaa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    Set<OWLNegativeDataPropertyAssertionAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.NEGATIVE_DATA_PROPERTY_ASSERTION, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.NAMED_INDIVIDUAL, SWRLBuiltInArgumentType.DATA_PROPERTY_EXPRESSION,
        SWRLBuiltInArgumentType.LITERAL);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLNegativeDataPropertyAssertionAxiom axiom : axioms) {
        OWLNamedIndividual candidateValue1 = axiom.getSubject().asOWLNamedIndividual();
        OWLDataPropertyExpression candidateValue2 = axiom.getProperty();
        OWLLiteral candidateValue3 = axiom.getObject();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2, candidateValue3)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0).addArgument(createNamedIndividualBuiltInArgument(candidateValue1));

            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1)
                .addArgument(createDataPropertyExpressionBuiltInArgument(candidateValue2));

            if (outputMultiValueArguments.containsKey(2))
              outputMultiValueArguments.get(2).addArgument(createLiteralBuiltInArgument(candidateValue3));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }
}
