package org.swrlapi.builtins.abox;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

import java.util.List;
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
    IRI classVariableIRI = arguments.get(0).asVariable().getIRI();
    IRI individualVariableIRI = arguments.get(1).asVariable().getIRI();

    OWLOntology ontology = getBuiltInBridge().getSWRLAPIOWLOntology().getOWLOntology();

    Set<OWLClassAssertionAxiom> axioms = ontology.getAxioms(AxiomType.CLASS_ASSERTION, Imports.INCLUDED);

    if (!axioms.isEmpty()) {
      SWRLMultiValueVariableBuiltInArgument classesResultArgument = createSWRLMultiValueVariableBuiltInArgument(
        classVariableIRI);
      SWRLMultiValueVariableBuiltInArgument individualsResultArgument = createSWRLMultiValueVariableBuiltInArgument(
        individualVariableIRI);

      for (OWLClassAssertionAxiom axiom : axioms) {
        OWLClassExpression ce = axiom.getClassExpression();
        OWLIndividual individual = axiom.getIndividual();

        if (individual.isAnonymous())
          continue;

        OWLNamedIndividual namedIndividual = individual.asOWLNamedIndividual();
        SWRLNamedIndividualBuiltInArgument namedIndividualBuiltInArgument = createNamedIndividualBuiltInArgument(
          namedIndividual);
        individualsResultArgument.addArgument(namedIndividualBuiltInArgument);

        if (ce.isAnonymous()) {
          SWRLClassExpressionBuiltInArgument classExpressionBuiltInArgument = createClassExpressionBuiltInArgument(ce);
          classesResultArgument.addArgument(classExpressionBuiltInArgument);
        } else {
          SWRLClassBuiltInArgument classBuiltInArgument = createClassBuiltInArgument(ce.asOWLClass());
          classesResultArgument.addArgument(classBuiltInArgument);
        }
      }

      if (!classesResultArgument.hasNoArguments()) {
        arguments.get(0).asVariable().setBuiltInResult(classesResultArgument);
        arguments.get(1).asVariable().setBuiltInResult(individualsResultArgument);
        return true;
      } else
        return false;
    } else
      return false;
  }

}
