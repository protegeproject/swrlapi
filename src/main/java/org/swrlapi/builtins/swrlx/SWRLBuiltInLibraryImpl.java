package org.swrlapi.builtins.swrlx;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Implementations library for SWRL Extensions built-ins.
 */
public class SWRLBuiltInLibraryImpl extends AbstractSWRLBuiltInLibrary
{
  private static final String Prefix = "swrlx";

  private static final String Namespace = "http://swrl.stanford.edu/ontologies/built-ins/3.3/swrlx.owl#";

  private static final String[] BuiltInNames = { "makeOWLClass", "makeOWLIndividual", "makeOWLThing", "createOWLThing",
    "invokeSWRLBuiltIn" };

  @NonNull private final Map<@NonNull String, @NonNull OWLClass> classInvocationMap;
  @NonNull private final Map<@NonNull String, @NonNull OWLNamedIndividual> individualInvocationMap;

  public SWRLBuiltInLibraryImpl()
  {
    super(Prefix, Namespace, new HashSet<>(Arrays.asList(BuiltInNames)));

    this.classInvocationMap = new HashMap<>();
    this.individualInvocationMap = new HashMap<>();
  }

  @Override public void reset()
  {
    this.classInvocationMap.clear();
    this.individualInvocationMap.clear();
  }

  /**
   * For every pattern of second and subsequent arguments, create an OWL anonymous class and bind it to the first
   * argument. If the first argument is already bound when the built-in is called, this method returns true.
   *
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean makeOWLClass(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsAtLeast(2, arguments.size());

    if (isUnboundArgument(0, arguments)) {
      String createInvocationPattern = createInvocationPattern(getBuiltInBridge(), getInvokingRuleName(),
        getInvokingBuiltInIndex(), getIsInConsequent(), arguments.subList(1, arguments.size()));
      OWLClass cls;

      if (this.classInvocationMap.containsKey(createInvocationPattern)) {
        cls = this.classInvocationMap.get(createInvocationPattern);
      } else {
        cls = getSWRLAPIOWLDataFactory().getInjectedOWLClass();
        OWLDeclarationAxiom declarationAxiom = getSWRLAPIOWLDataFactory().getOWLClassDeclarationAxiom(cls);
        getBuiltInBridge().injectOWLAxiom(declarationAxiom);
        this.classInvocationMap.put(createInvocationPattern, cls);
      }
      arguments.get(0).asVariable().setBuiltInResult(createClassBuiltInArgument(cls)); // Bind result to first parameter
    }

    return true;
  }

  /**
   * For every pattern of second and subsequent arguments, create an OWL individual of type OWL:Thing and bind it to the
   * first argument. If the first argument is already bound when the built-in is called, this method returns true.
   *
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  private boolean makeOWLIndividual(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsAtLeast(2, arguments.size());

    if (isUnboundArgument(0, arguments)) {
      OWLNamedIndividual individual;
      String createInvocationPattern = createInvocationPattern(getBuiltInBridge(), getInvokingRuleName(),
        getInvokingBuiltInIndex(), getIsInConsequent(), arguments.subList(1, arguments.size()));

      if (this.individualInvocationMap.containsKey(createInvocationPattern))
        individual = this.individualInvocationMap.get(createInvocationPattern);
      else {
        individual = getSWRLAPIOWLDataFactory().getInjectedOWLNamedIndividual();
        OWLDeclarationAxiom declarationAxiom = getSWRLAPIOWLDataFactory().getOWLIndividualDeclarationAxiom(individual);
        getBuiltInBridge().injectOWLAxiom(declarationAxiom);
        this.individualInvocationMap.put(createInvocationPattern, individual);
      }
      arguments.get(0).asVariable()
        .setBuiltInResult(createNamedIndividualBuiltInArgument(individual)); // Bind result to the first parameter
    }

    return true;
  }

  // For backwards compatability
  public boolean makeOWLThing(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return makeOWLIndividual(arguments);
  }

  // For backwards compatability
  public boolean createOWLThing(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return makeOWLIndividual(arguments);
  }

  // TODO: check for invocations to swrlx built-ins, which will cause blocking
  public boolean invokeSWRLBuiltIn(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsAtLeast(2, arguments.size());

    String builtInName = getArgumentAsAString(0, arguments);

    List<@NonNull List<@NonNull SWRLBuiltInArgument>> argumentPatterns = getBuiltInBridge()
      .invokeSWRLBuiltIn(getInvokingRuleName(), builtInName, getInvokingBuiltInIndex(), getIsInConsequent(),
        arguments.subList(1, arguments.size()));
    return !argumentPatterns.isEmpty();
  }
}
