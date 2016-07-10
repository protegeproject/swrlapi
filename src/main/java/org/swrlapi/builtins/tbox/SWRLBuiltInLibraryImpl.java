package org.swrlapi.builtins.tbox;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

import java.util.List;
import java.util.Set;

/**
 * Implementations library for SWRL TBox built-ins
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

  // TODO Initial implementation - only supports case where both arguments are unbound
  public boolean sca(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    IRI subClassVariableIRI = arguments.get(0).asVariable().getIRI();
    IRI superClassVariableIRI = arguments.get(1).asVariable().getIRI();

    OWLOntology ontology = getBuiltInBridge().getOWLOntology();

    Set<OWLSubClassOfAxiom> axioms = ontology.getAxioms(AxiomType.SUBCLASS_OF, Imports.INCLUDED);

    if (!axioms.isEmpty()) {
      SWRLMultiValueVariableBuiltInArgument subClassesResultArgument = createSWRLMultiValueVariableBuiltInArgument(
        subClassVariableIRI);
      SWRLMultiValueVariableBuiltInArgument superClassesResultArgument = createSWRLMultiValueVariableBuiltInArgument(
        superClassVariableIRI);

      for (OWLSubClassOfAxiom axiom : axioms) {
        OWLClassExpression subCE = axiom.getSubClass();
        OWLClassExpression superCE = axiom.getSuperClass();

        if (subCE.isAnonymous()) {
          SWRLClassExpressionBuiltInArgument classExpressionBuiltInArgument = createClassExpressionBuiltInArgument(
            subCE);
          subClassesResultArgument.addArgument(classExpressionBuiltInArgument);
        } else {
          SWRLClassBuiltInArgument classBuiltInArgument = createClassBuiltInArgument(subCE.asOWLClass());
          subClassesResultArgument.addArgument(classBuiltInArgument);
        }

        if (superCE.isAnonymous()) {
          SWRLClassExpressionBuiltInArgument classExpressionBuiltInArgument = createClassExpressionBuiltInArgument(
            superCE);
          superClassesResultArgument.addArgument(classExpressionBuiltInArgument);
        } else {
          SWRLClassBuiltInArgument classBuiltInArgument = createClassBuiltInArgument(superCE.asOWLClass());
          superClassesResultArgument.addArgument(classBuiltInArgument);
        }
      }

      arguments.get(0).asVariable().setBuiltInResult(subClassesResultArgument);
      arguments.get(1).asVariable().setBuiltInResult(superClassesResultArgument);
      return true;
    } else
      return false;
  }
}
