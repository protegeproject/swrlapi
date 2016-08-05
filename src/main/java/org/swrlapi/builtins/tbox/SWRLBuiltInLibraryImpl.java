package org.swrlapi.builtins.tbox;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
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
 * Implementation library for SWRL TBox built-ins
 */
public class SWRLBuiltInLibraryImpl extends AbstractSWRLBuiltInLibrary
{
  private static final String SWRLTBoxLibraryName = "SWRLTBoxBuiltIns";

  public SWRLBuiltInLibraryImpl()
  {
    super(SWRLTBoxLibraryName);
  }

  @Override public void reset()
  {
  }

  public boolean sca(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLSubClassOfAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.SUBCLASS_OF, Imports.INCLUDED);

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.CLASS_EXPRESSION, SWRLBuiltInArgumentType.CLASS_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLSubClassOfAxiom axiom : axioms) {
        OWLClassExpression candidateValue1 = axiom.getSubClass();
        OWLClassExpression candidateValue2 = axiom.getSuperClass();

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0).addArgument(createClassExpressionBuiltInArgument(candidateValue1));

            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1).addArgument(createClassExpressionBuiltInArgument(candidateValue2));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean eca(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLEquivalentClassesAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.EQUIVALENT_CLASSES, Imports.INCLUDED).stream().flatMap(a -> a.asPairwiseAxioms().stream())
      .collect(Collectors.toSet());

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.CLASS_EXPRESSION, SWRLBuiltInArgumentType.CLASS_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLEquivalentClassesAxiom axiom : axioms) {
        OWLClassExpression candidateValue1 = axiom.getClassExpressionsAsList().get(0);
        OWLClassExpression candidateValue2 = axiom.getClassExpressionsAsList().get(1);

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0).addArgument(createClassExpressionBuiltInArgument(candidateValue1));

            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1).addArgument(createClassExpressionBuiltInArgument(candidateValue2));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  public boolean dca(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLDisjointClassesAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.DISJOINT_CLASSES, Imports.INCLUDED).stream().flatMap(a -> a.asPairwiseAxioms().stream())
      .collect(Collectors.toSet());

    if (axioms.isEmpty())
      return false;
    else {
      Map<@NonNull Integer, @NonNull OWLObject> inputArgumentValues = getInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.CLASS_EXPRESSION, SWRLBuiltInArgumentType.CLASS_EXPRESSION);
      Map<@NonNull Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> outputMultiValueArguments = createOutputMultiValueArguments(
        arguments);

      for (OWLDisjointClassesAxiom axiom : axioms) {
        OWLClassExpression candidateValue1 = axiom.getClassExpressionsAsList().get(0);
        OWLClassExpression candidateValue2 = axiom.getClassExpressionsAsList().get(1);

        if (!noBoundArgumentsMismatch(inputArgumentValues, candidateValue1, candidateValue2)) {
          if (outputMultiValueArguments.isEmpty())
            return true; // We have a match and there are no unbound arguments - return immediately
          else { // We have a match so update any unbound arguments with the matched values
            if (outputMultiValueArguments.containsKey(0))
              outputMultiValueArguments.get(0).addArgument(createClassExpressionBuiltInArgument(candidateValue1));

            if (outputMultiValueArguments.containsKey(1))
              outputMultiValueArguments.get(1).addArgument(createClassExpressionBuiltInArgument(candidateValue2));
          }
        }
      }
      return processOutputMultiValueArguments(arguments, outputMultiValueArguments);
    }
  }

  // OBJECT_PROPERTY_DOMAIN, OBJECT_PROPERTY_RANGE, FUNCTIONAL_OBJECT_PROPERTY, INVERSE_FUNCTIONAL_OBJECT_PROPERTY,
  // DATA_PROPERTY_DOMAIN, DATA_PROPERTY_RANGE, FUNCTIONAL_DATA_PROPERTY,
  // DISJOINT_UNION,
  // DATATYPE_DEFINITION,
  // HAS_KEY

}
