package org.swrlapi.builtins.abox;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
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

  @NonNull Map<Integer, @NonNull SWRLMultiValueVariableBuiltInArgument> getUnboundOutputArguments(
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    Map<Integer, SWRLMultiValueVariableBuiltInArgument> unboundOutputArguments = new HashMap<>();

    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++) {
      if (arguments.get(argumentNumber).isVariable()) {
        IRI variableIRI = arguments.get(argumentNumber).asVariable().getIRI();
        unboundOutputArguments.put(argumentNumber, createSWRLMultiValueVariableBuiltInArgument(variableIRI));
      }
    }
    return unboundOutputArguments;
  }

  //  LITERAL, CLASS, CLASS_EXPRESSION, NAMED_INDIVIDUAL, OBJECT_PROPERTY,
  //  OBJECT_PROPERTY_EXPRESSION, DATA_PROPERTY, DATA_PROPERTY_EXPRESSION,
  //  ANNOTATION_PROPERTY, DATATYPE

  @NonNull Map<Integer, @NonNull OWLObject> getBoundInputArgumentValues(
    @NonNull List<@NonNull SWRLBuiltInArgument> arguments, @NonNull SWRLBuiltInArgumentType<?>... builtInArgumentTypes)
    throws SWRLBuiltInException
  {
    Map<Integer, @NonNull OWLObject> boundInputArgumentValues = new HashMap<>();

    if (arguments.size() != builtInArgumentTypes.length)
      throw new SWRLBuiltInException(
        "internal error: expecting " + arguments.size() + " entries for bound argument types, got"
          + builtInArgumentTypes.length);

    for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++) {
      if (!arguments.get(argumentNumber).isVariable()) {
        SWRLBuiltInArgumentType<?> builtInArgumentType = builtInArgumentTypes[argumentNumber];

        if (builtInArgumentType == SWRLBuiltInArgumentType.LITERAL) {
          checkThatArgumentIsALiteral(argumentNumber, arguments);
          boundInputArgumentValues
            .put(argumentNumber, arguments.get(argumentNumber).asSWRLLiteralBuiltInArgument().getLiteral());
        } else if (builtInArgumentType == SWRLBuiltInArgumentType.CLASS) {
          checkThatArgumentIsAClass(argumentNumber, arguments);
          boundInputArgumentValues
            .put(argumentNumber, arguments.get(argumentNumber).asSWRLClassBuiltInArgument().getOWLClass());
        } else if (builtInArgumentType == SWRLBuiltInArgumentType.CLASS_EXPRESSION) {
          checkThatArgumentIsAClassExpression(argumentNumber, arguments);
          boundInputArgumentValues.put(argumentNumber,
            arguments.get(argumentNumber).asSWRLClassExpressionBuiltInArgument().getOWLClassExpression());
        } else if (builtInArgumentType == SWRLBuiltInArgumentType.NAMED_INDIVIDUAL) {
          checkThatArgumentIsANamedIndividual(argumentNumber, arguments);
          boundInputArgumentValues.put(argumentNumber,
            arguments.get(argumentNumber).asSWRLNamedIndividualBuiltInArgument().getOWLNamedIndividual());
        } else if (builtInArgumentType == SWRLBuiltInArgumentType.OBJECT_PROPERTY) {
          checkThatArgumentIsAnObjectProperty(argumentNumber, arguments);
          boundInputArgumentValues.put(argumentNumber,
            arguments.get(argumentNumber).asSWRLObjectPropertyBuiltInArgument().getOWLObjectProperty());
        } else if (builtInArgumentType == SWRLBuiltInArgumentType.OBJECT_PROPERTY_EXPRESSION) {
          checkThatArgumentIsAnObjectPropertyExpression(argumentNumber, arguments);
          boundInputArgumentValues.put(argumentNumber,
            arguments.get(argumentNumber).asSWRLObjectPropertyExpressionBuiltInArgument()
              .getOWLObjectPropertyExpression());
        } else if (builtInArgumentType == SWRLBuiltInArgumentType.DATA_PROPERTY) {
          checkThatArgumentIsADataProperty(argumentNumber, arguments);
          boundInputArgumentValues.put(argumentNumber,
            arguments.get(argumentNumber).asSWRLDataPropertyBuiltInArgument().getOWLDataProperty());
        } else if (builtInArgumentType == SWRLBuiltInArgumentType.DATA_PROPERTY_EXPRESSION) {
          checkThatArgumentIsADataPropertyExpression(argumentNumber, arguments);
          boundInputArgumentValues.put(argumentNumber,
            arguments.get(argumentNumber).asSWRLDataPropertyExpressionBuiltInArgument().getOWLDataPropertyExpression());
        } else if (builtInArgumentType == SWRLBuiltInArgumentType.ANNOTATION_PROPERTY) {
          checkThatArgumentIsAnAnnotationProperty(argumentNumber, arguments);
          boundInputArgumentValues.put(argumentNumber,
            arguments.get(argumentNumber).asSWRLAnnotationPropertyBuiltInArgument().getOWLAnnotationProperty());
        } else if (builtInArgumentType == SWRLBuiltInArgumentType.DATATYPE) {
          checkThatArgumentIsADatatype(argumentNumber, arguments);
          boundInputArgumentValues
            .put(argumentNumber, arguments.get(argumentNumber).asSWRLDatatypeBuiltInArgument().getOWLDatatype());
        } else
          throw new SWRLBuiltInException(
            "internal error: unexpected argument type " + builtInArgumentType + " for argument number "
              + argumentNumber);
      }
    }
    return boundInputArgumentValues;
  }

  public boolean caa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    Set<OWLClassAssertionAxiom> axioms = getBuiltInBridge().getOWLOntology()
      .getAxioms(AxiomType.CLASS_ASSERTION, Imports.INCLUDED);
    boolean atLeastOneBoundArgumentUnequal = false;

    if (!axioms.isEmpty()) {
      Map<Integer, OWLObject> boundInputArgumentValues = getBoundInputArgumentValues(arguments,
        SWRLBuiltInArgumentType.CLASS, SWRLBuiltInArgumentType.NAMED_INDIVIDUAL);
      Map<Integer, SWRLMultiValueVariableBuiltInArgument> unboundOutputArguments = getUnboundOutputArguments(arguments);

      for (OWLClassAssertionAxiom axiom : axioms) {
        OWLClassExpression crav1 = axiom.getClassExpression();
        OWLNamedIndividual crav2 = axiom.getIndividual().asOWLNamedIndividual(); // We do not handle anonymous

        atLeastOneBoundArgumentUnequal =
          (boundInputArgumentValues.containsKey(0) && !boundInputArgumentValues.get(0).equals(crav1)) || (
            boundInputArgumentValues.containsKey(1) && !boundInputArgumentValues.get(1).equals(crav2));

        if (atLeastOneBoundArgumentUnequal)
          break;

        if (unboundOutputArguments.containsKey(0)) {
          if (crav1.isAnonymous())
            unboundOutputArguments.get(0).addArgument(createClassExpressionBuiltInArgument(crav1));
          else
            unboundOutputArguments.get(0).addArgument(createClassBuiltInArgument(crav1.asOWLClass()));
        }

        if (unboundOutputArguments.containsKey(1))
          unboundOutputArguments.get(1).addArgument(createNamedIndividualBuiltInArgument(crav2));
      }

      if (atLeastOneBoundArgumentUnequal)
        return false;
      else {
        for (Integer argumentNumber : unboundOutputArguments.keySet())
          arguments.get(argumentNumber).asVariable().setBuiltInResult(unboundOutputArguments.get(argumentNumber));
        return true;
      }
    } else
      return false;
  }

  // SAME_INDIVIDUAL, DIFFERENT_INDIVIDUALS, OBJECT_PROPERTY_ASSERTION, NEGATIVE_OBJECT_PROPERTY_ASSERTION,
  // DATA_PROPERTY_ASSERTION, NEGATIVE_DATA_PROPERTY_ASSERTION
}
