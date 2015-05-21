package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.sqwrl.SQWRLNames;
import uk.ac.manchester.cs.owl.owlapi.SWRLRuleImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DefaultSWRLAPIRule extends SWRLRuleImpl implements SWRLAPIRule
{
  private static final long serialVersionUID = 1L;

  @NonNull private final String ruleName;
  @NonNull private final boolean active;
  @NonNull private final String comment;

  @NonNull private List<SWRLAtom> bodyAtoms; // Body atoms can be reorganized during processing
  @NonNull private final List<SWRLAtom> headAtoms;

  public DefaultSWRLAPIRule(@NonNull String ruleName, @NonNull List<? extends SWRLAtom> bodyAtoms,
    @NonNull List<? extends SWRLAtom> headAtoms, @NonNull String comment, boolean isActive)
  {
    super(new LinkedHashSet<>(bodyAtoms), new LinkedHashSet<>(headAtoms), new HashSet<>());
    this.ruleName = ruleName;
    this.active = isActive;
    this.comment = comment;
    this.bodyAtoms = new ArrayList<>(bodyAtoms);
    this.headAtoms = new ArrayList<>(headAtoms);

    processBuiltInArguments();
  }

  @NonNull @Override public String getRuleName()
  {
    return this.ruleName;
  }

  @Override public boolean isActive()
  {
    return this.active;
  }

  @Override public boolean isSQWRLQuery()
  {
    return !getBuiltInAtomsFromHead(SQWRLNames.getSQWRLBuiltInNames()).isEmpty() || !getBuiltInAtomsFromBody(
      SQWRLNames.getSQWRLBuiltInNames()).isEmpty();
  }

  @NonNull @Override public String getComment()
  {
    return this.comment;
  }

  @NonNull @Override public List<SWRLAtom> getHeadAtoms()
  {
    return this.headAtoms;
  }

  @NonNull @Override public List<SWRLAtom> getBodyAtoms()
  {
    return this.bodyAtoms;
  }

  @NonNull @Override public List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromHead(@NonNull Set<String> builtInNames)
  {
    return getBuiltInAtoms(getHeadAtoms(), builtInNames);
  }

  @NonNull @Override public List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(@NonNull Set<String> builtInNames)
  {
    return getBuiltInAtoms(getBodyAtoms(), builtInNames);
  }

  /**
   * Find all built-in atoms with unbound arguments and tell them which of their arguments are unbound.
   */
  private void processBuiltInArguments()
  {
    List<SWRLAPIBuiltInAtom> bodyBuiltInAtoms = new ArrayList<>();
    List<SWRLAtom> bodyNonBuiltInAtoms = new ArrayList<>();
    Set<IRI> variablesUsedByNonBuiltInBodyAtoms = new HashSet<>(); // By definition, always bound
    Set<IRI> variablesBoundByBuiltIns = new HashSet<>(); // Variables bound by built-ins in rule
    List<SWRLAtom> finalBodyAtoms;

    // Process body atoms to build list of (1) built-in body atoms, and (2) the variables used by non-built-in atoms.
    for (SWRLAtom atom : getBodyAtoms()) {
      if (atom instanceof SWRLAPIBuiltInAtom)
        bodyBuiltInAtoms.add((SWRLAPIBuiltInAtom)atom);
      else {
        bodyNonBuiltInAtoms.add(atom);
        variablesUsedByNonBuiltInBodyAtoms.addAll(getReferencedVariableIRIs(atom));
      }
    }

    // Process the body built-in atoms and determine if they bind any of their arguments.
    for (SWRLAPIBuiltInAtom builtInAtom : bodyBuiltInAtoms) { // Read through built-in arguments and determine which
      // are unbound.
      // If a variable argument is not used by any non built-in body atom or is not bound by another body built-in
      // atom it will therefore be unbound when this built-in is called. We thus set this built-in argument to
      // unbound. If a built-in binds an argument, all later built-ins (proceeding from left to right) will be
      // passed the bound value of this variable during rule execution.
      // Tell the built-in that it is expected to bind this argument
      // Flag as a bound variable for later built-ins
      builtInAtom.getBuiltInArguments().stream().filter(SWRLBuiltInArgument::isVariable).forEach(argument -> {
        IRI argumentVariableIRI = argument.asVariable().getIRI();

        // If a variable argument is not used by any non built-in body atom or is not bound by another body built-in
        // atom it will therefore be unbound when this built-in is called. We thus set this built-in argument to
        // unbound. If a built-in binds an argument, all later built-ins (proceeding from left to right) will be
        // passed the bound value of this variable during rule execution.
        if (!variablesUsedByNonBuiltInBodyAtoms.contains(argumentVariableIRI) && !variablesBoundByBuiltIns
          .contains(argumentVariableIRI)) {
          argument.asVariable().setUnbound(); // Tell the built-in that it is expected to bind this argument
          variablesBoundByBuiltIns.add(argumentVariableIRI); // Flag as a bound variable for later built-ins
        }
      });
    }
    // If we have built-in atoms, construct a new body with built-in atoms moved to the end of the list. Some rule
    // engines (e.g., Jess) expect variables used as parameters to functions to have been defined before their use in
    // a left to right fashion.
    finalBodyAtoms = reorganizeBodyNonBuiltInAtoms(bodyNonBuiltInAtoms);
    this.bodyAtoms = finalBodyAtoms;
    finalBodyAtoms.addAll(bodyBuiltInAtoms);
  }

  /**
   * Reorganize body non-built atoms so that class atoms appear firsts, followed by other atom types.
   */
  @NonNull private List<SWRLAtom> reorganizeBodyNonBuiltInAtoms(@NonNull List<SWRLAtom> bodyNonBuiltInAtoms)
  {
    List<SWRLAtom> bodyClassAtoms = new ArrayList<>();
    List<SWRLAtom> bodyNonClassNonBuiltInAtoms = new ArrayList<>();
    List<SWRLAtom> result = new ArrayList<>();

    for (SWRLAtom atom : bodyNonBuiltInAtoms) {
      if (atom instanceof SWRLClassAtom)
        bodyClassAtoms.add(atom);
      else
        bodyNonClassNonBuiltInAtoms.add(atom);
    }

    result.addAll(bodyClassAtoms); // We arrange the class atoms first
    result.addAll(bodyNonClassNonBuiltInAtoms); // Followed by other non built-in atoms

    return result;
  }

  @NonNull private List<SWRLAPIBuiltInAtom> getBuiltInAtoms(@NonNull List<SWRLAtom> atoms,
    @NonNull Set<String> builtInNames)
  {
    List<SWRLAPIBuiltInAtom> result = new ArrayList<>();

    atoms.stream().filter(atom -> atom instanceof SWRLAPIBuiltInAtom).forEach(atom -> {
      SWRLAPIBuiltInAtom builtInAtom = (SWRLAPIBuiltInAtom)atom;
      if (builtInNames.contains(builtInAtom.getBuiltInPrefixedName()))
        result.add(builtInAtom);
    });
    return result;
  }

  @NonNull private Set<IRI> getReferencedVariableIRIs(@NonNull SWRLAtom atom)
  {
    Set<IRI> referencedVariableIRIs = new HashSet<>();

    atom.getAllArguments().stream().filter(argument -> argument instanceof SWRLVariable).forEach(argument -> {
      SWRLVariable variable = (SWRLVariable)argument;
      referencedVariableIRIs.add(variable.getIRI());
    });
    return referencedVariableIRIs;
  }
}
