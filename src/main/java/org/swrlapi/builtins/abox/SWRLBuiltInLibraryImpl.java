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
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementations library for SWRL ABox built-ins
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

  // TODO Initial implementation - only supports case where both arguments are unbound
  public boolean caa(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    OWLOntology ontology = getBuiltInBridge().getOWLOntology();
    Set<OWLClassAssertionAxiom> axioms = ontology.getAxioms(AxiomType.CLASS_ASSERTION, Imports.INCLUDED);

    if (!axioms.isEmpty()) {
      Map<Integer, OWLObject> argumentValues = new HashMap<>();
      Map<Integer, SWRLMultiValueVariableBuiltInArgument> resultMultiArguments = new HashMap<>();

      for (int argumentNumber = 0; argumentNumber < arguments.size(); argumentNumber++) {
        if (arguments.get(argumentNumber).isVariable()) {
          IRI variableIRI = arguments.get(0).asVariable().getIRI();
          resultMultiArguments.put(argumentNumber, createSWRLMultiValueVariableBuiltInArgument(variableIRI));
        } else {
          if (argumentNumber == 0) {
            checkThatArgumentIsAClassExpression(argumentNumber, arguments);
            argumentValues.put(argumentNumber,
              arguments.get(argumentNumber).asSWRLClassExpressionBuiltInArgument().getOWLClassExpression());
          } else if (argumentNumber == 1) {
            checkThatArgumentIsANamedIndividual(argumentNumber, arguments);
            argumentValues.put(argumentNumber,
              arguments.get(argumentNumber).asSWRLNamedIndividualBuiltInArgument().getOWLNamedIndividual());
          }
        }
      }

      for (OWLClassAssertionAxiom axiom : axioms) {
        OWLClassExpression crav1 = axiom.getClassExpression();
        OWLNamedIndividual crav2 = axiom.getIndividual().asOWLNamedIndividual(); // We do not handle anonymous

        if (argumentValues.containsKey(0) && !arguments.get(0).equals(crav1))
          continue;
        if (argumentValues.containsKey(1) && !arguments.get(1).equals(crav2))
          continue;

        if (crav1.isAnonymous())
          resultMultiArguments.get(0).addArgument(createClassExpressionBuiltInArgument(crav1));
        else
          resultMultiArguments.get(0).addArgument(createClassBuiltInArgument(crav1.asOWLClass()));

        SWRLNamedIndividualBuiltInArgument ra2 = createNamedIndividualBuiltInArgument(crav2);
        resultMultiArguments.get(1).addArgument(ra2);
      }

      if (!resultMultiArguments.isEmpty()) {
        for (Integer argumentNumber : resultMultiArguments.keySet()) {
          if (resultMultiArguments.containsKey(argumentNumber))
            arguments.get(argumentNumber).asVariable().setBuiltInResult(resultMultiArguments.get(argumentNumber));
        }
        return true;
      } else
        return false;
    } else
      return false;
  }
}
