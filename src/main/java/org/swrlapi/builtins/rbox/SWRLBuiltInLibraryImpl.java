package org.swrlapi.builtins.rbox;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentType;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInNotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation library for SWRL RBox built-ins
 */
public class SWRLBuiltInLibraryImpl extends AbstractSWRLBuiltInLibrary
{
  private static final String PREFIX = "rbox";

  private static final String NAMESPACE = "http://swrl.stanford.edu/ontologies/built-ins/5.0.0/rbox.owl#";

  private static final String[] BUILT_IN_NAMES = { "topa", "djopa", "eopa", "sopa", "spa", "aopa", "ropa", "iropa",
    "iopa", "djdpa", "sdpa", "dpda", "edpa", "spoca" };

  public SWRLBuiltInLibraryImpl()
  {
    super(PREFIX, NAMESPACE, new HashSet<>(Arrays.asList(BUILT_IN_NAMES)));
  }

  @Override public void reset()
  {
  }

  public boolean topa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(1, arguments.size());

    Set<OWLTransitiveObjectPropertyAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.TRANSITIVE_OBJECT_PROPERTY, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLTransitiveObjectPropertyAxiom axiom : axioms) {
        OWLObjectPropertyExpression candidateValue1 = axiom.getProperty();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue1));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean spa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(1, arguments.size());

    Set<OWLSymmetricObjectPropertyAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.SYMMETRIC_OBJECT_PROPERTY, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLSymmetricObjectPropertyAxiom axiom : axioms) {
        OWLObjectPropertyExpression candidateValue1 = axiom.getProperty();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue1));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean aopa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(1, arguments.size());

    Set<OWLAsymmetricObjectPropertyAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.ASYMMETRIC_OBJECT_PROPERTY, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLAsymmetricObjectPropertyAxiom axiom : axioms) {
        OWLObjectPropertyExpression candidateValue1 = axiom.getProperty();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue1));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean ropa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(1, arguments.size());

    Set<OWLReflexiveObjectPropertyAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.REFLEXIVE_OBJECT_PROPERTY, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLReflexiveObjectPropertyAxiom axiom : axioms) {
        OWLObjectPropertyExpression candidateValue1 = axiom.getProperty();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue1));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean iropa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(1, arguments.size());

    Set<OWLIrreflexiveObjectPropertyAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.IRREFLEXIVE_OBJECT_PROPERTY, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLIrreflexiveObjectPropertyAxiom axiom : axioms) {
        OWLObjectPropertyExpression candidateValue1 = axiom.getProperty();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue1));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean iopa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLInverseObjectPropertiesAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.INVERSE_OBJECT_PROPERTIES, Imports.INCLUDED).stream()
      .flatMap(a -> a.asPairwiseAxioms().stream()).collect(Collectors.toSet());

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION, SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLInverseObjectPropertiesAxiom axiom : axioms) {
        OWLObjectPropertyExpression candidateValue1 = axiom.getFirstProperty();
        OWLObjectPropertyExpression candidateValue2 = axiom.getSecondProperty();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue1));
            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue2));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean djopa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLDisjointObjectPropertiesAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.DISJOINT_OBJECT_PROPERTIES, Imports.INCLUDED).stream()
      .flatMap(a -> a.asPairwiseAxioms().stream()).collect(Collectors.toSet());

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION, SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLDisjointObjectPropertiesAxiom axiom : axioms) {
        List<OWLObjectPropertyExpression> properties = new ArrayList<>(axiom.getProperties());
        OWLObjectPropertyExpression candidateValue1 = properties.get(0);
        OWLObjectPropertyExpression candidateValue2 = properties.get(1);

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue1));
            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue2));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean djdpa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLDisjointDataPropertiesAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.DISJOINT_DATA_PROPERTIES, Imports.INCLUDED).stream()
      .flatMap(a -> a.asPairwiseAxioms().stream()).collect(Collectors.toSet());

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.DATA_PROPERTY_EXPRESSION, SWRLBuiltInArgumentType.DATA_PROPERTY_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLDisjointDataPropertiesAxiom axiom : axioms) {
        List<OWLDataPropertyExpression> properties = new ArrayList<>(axiom.getProperties());
        OWLDataPropertyExpression candidateValue1 = properties.get(0);
        OWLDataPropertyExpression candidateValue2 = properties.get(1);

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0)
                .addArgument(createDataPropertyExpressionBuiltInArgument(candidateValue1));
            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1)
                .addArgument(createDataPropertyExpressionBuiltInArgument(candidateValue2));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean sopa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLSubObjectPropertyOfAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.SUB_OBJECT_PROPERTY, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION, SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLSubObjectPropertyOfAxiom axiom : axioms) {
        OWLObjectPropertyExpression candidateValue1 = axiom.getSubProperty();
        OWLObjectPropertyExpression candidateValue2 = axiom.getSuperProperty();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue1));
            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue2));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean sdpa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLSubDataPropertyOfAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.SUB_DATA_PROPERTY, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.DATA_PROPERTY_EXPRESSION, SWRLBuiltInArgumentType.DATA_PROPERTY_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLSubDataPropertyOfAxiom axiom : axioms) {
        OWLDataPropertyExpression candidateValue1 = axiom.getSubProperty();
        OWLDataPropertyExpression candidateValue2 = axiom.getSuperProperty();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0)
                .addArgument(createDataPropertyExpressionBuiltInArgument(candidateValue1));
            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1)
                .addArgument(createDataPropertyExpressionBuiltInArgument(candidateValue2));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean eopa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLEquivalentObjectPropertiesAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES, Imports.INCLUDED).stream()
      .flatMap(a -> a.asPairwiseAxioms().stream()).collect(Collectors.toSet());

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION, SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLEquivalentObjectPropertiesAxiom axiom : axioms) {
        List<OWLObjectPropertyExpression> properties = new ArrayList<>(axiom.getProperties());
        OWLObjectPropertyExpression candidateValue1 = properties.get(0);
        OWLObjectPropertyExpression candidateValue2 = properties.get(1);

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue1));
            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1)
                .addArgument(createObjectPropertyExpressionBuiltInArgument(candidateValue2));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean edpa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLEquivalentDataPropertiesAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.EQUIVALENT_DATA_PROPERTIES, Imports.INCLUDED).stream()
      .flatMap(a -> a.asPairwiseAxioms().stream()).collect(Collectors.toSet());

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.DATA_PROPERTY_EXPRESSION, SWRLBuiltInArgumentType.DATA_PROPERTY_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLEquivalentDataPropertiesAxiom axiom : axioms) {
        List<OWLDataPropertyExpression> properties = new ArrayList<>(axiom.getProperties());
        OWLDataPropertyExpression candidateValue1 = properties.get(0);
        OWLDataPropertyExpression candidateValue2 = properties.get(1);

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0)
                .addArgument(createDataPropertyExpressionBuiltInArgument(candidateValue1));
            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1)
                .addArgument(createDataPropertyExpressionBuiltInArgument(candidateValue2));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean spcoa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    throw new SWRLBuiltInNotImplementedException("rbox:spcoa built-in not implemented");
  }
}
