package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.sqwrl.SQWRLNames;
import uk.ac.manchester.cs.owl.owlapi.SWRLRuleImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DefaultSWRLAPIRule extends SWRLRuleImpl implements SWRLAPIRule
{
  private static final long serialVersionUID = 1L;

  @NonNull private final String ruleName;
  @NonNull private final String comment;
  @NonNull private final List<@NonNull SWRLAtom> bodyAtoms;
  @NonNull private final List<@NonNull SWRLAtom> headAtoms;
  @NonNull private boolean active;

  public DefaultSWRLAPIRule(@NonNull String ruleName, @NonNull List<? extends @NonNull SWRLAtom> bodyAtoms,
    @NonNull List<? extends @NonNull SWRLAtom> headAtoms, @NonNull String comment, boolean isActive)
    throws SWRLBuiltInException
  {
    super(new LinkedHashSet<>(bodyAtoms), new LinkedHashSet<>(headAtoms), new HashSet<>());
    this.ruleName = ruleName;
    this.active = isActive;
    this.comment = comment;
    this.bodyAtoms = new ArrayList<>(bodyAtoms);
    this.headAtoms = new ArrayList<>(headAtoms);
    processBodyAtoms(bodyAtoms);
  }

  @NonNull @Override public String getRuleName()
  {
    return this.ruleName;
  }

  @Override public boolean isActive()
  {
    return this.active;
  }

  @Override public void setActive(boolean active)
  {
    this.active = active;
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

  @NonNull @Override public List<@NonNull SWRLAtom> getHeadAtoms()
  {
    return this.headAtoms;
  }

  @NonNull @Override public List<@NonNull SWRLAtom> getBodyAtoms()
  {
    return this.bodyAtoms;
  }

  @NonNull @Override public List<@NonNull SWRLAPIBuiltInAtom> getBuiltInAtomsFromHead(
    @NonNull Set<@NonNull String> builtInNames)
  {
    return getBuiltInAtoms(getHeadAtoms(), builtInNames);
  }

  @NonNull @Override public List<@NonNull SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(
    @NonNull Set<@NonNull String> builtInNames)
  {
    return getBuiltInAtoms(getBodyAtoms(), builtInNames);
  }

  /**
   * Find all built-in atoms with unbound arguments and tell them which of their arguments are unbound.
   */
  private static void processBodyAtoms(List<? extends @NonNull SWRLAtom> bodyAtoms) throws SWRLBuiltInException
  {
    List<@NonNull SWRLAPIBuiltInAtom> bodyBuiltInAtoms = new ArrayList<>();
    List<@NonNull SWRLAtom> bodyNonBuiltInAtoms = new ArrayList<>();
    Set<@NonNull IRI> variablesUsedByNonBuiltInBodyAtoms = new HashSet<>(); // By definition, always bound
    Set<@NonNull IRI> variablesBoundByBuiltIns = new HashSet<>(); // Variables bound by built-ins in rule

    // Process body atoms to build list of (1) built-in body atoms, and (2) the variables used by non-built-in atoms.
    for (SWRLAtom atom : bodyAtoms) {
      if (atom instanceof SWRLAPIBuiltInAtom)
        bodyBuiltInAtoms.add((SWRLAPIBuiltInAtom)atom);
      else {
        bodyNonBuiltInAtoms.add(atom);
        variablesUsedByNonBuiltInBodyAtoms.addAll(getReferencedVariableIRIs(atom));
      }
    }

    // Process the body built-in atoms and determine if they bind any of their arguments.
    for (SWRLAPIBuiltInAtom builtInAtom : bodyBuiltInAtoms) {
      // Read through built-in arguments and determine which variables are unbound.
      // If a variable argument is not used by any non built-in body atom or is not bound by another body built-in
      // atom it will therefore be unbound when this built-in is called. We thus set this built-in argument to
      // unbound. If a built-in binds an argument, all later built-ins (proceeding from left to right) will be
      // passed the bound value of this variable during rule execution.
      // Tell the built-in that it is expected to bind this argument
      // Flag as a bound variable for later built-ins
      for (SWRLBuiltInArgument argument : builtInAtom.getBuiltInArguments()) {
        if (argument.isVariable()) {
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
        }
      }
    }
  }

  /**
   * Reorganize body non-built atoms so that class atoms appear firsts, followed by other atom types.
   */
  @NonNull private static List<@NonNull SWRLAtom> reorganizeBodyNonBuiltInAtoms(
    @NonNull List<@NonNull SWRLAtom> bodyNonBuiltInAtoms)
  {
    List<@NonNull SWRLAtom> bodyClassAtoms = new ArrayList<>();
    List<@NonNull SWRLAtom> bodyNonClassNonBuiltInAtoms = new ArrayList<>();
    List<@NonNull SWRLAtom> result = new ArrayList<>();

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

  @NonNull private List<@NonNull SWRLAPIBuiltInAtom> getBuiltInAtoms(@NonNull List<@NonNull SWRLAtom> atoms,
    @NonNull Set<@NonNull String> builtInNames)
  {
    List<@NonNull SWRLAPIBuiltInAtom> result = new ArrayList<>();

    for (SWRLAtom atom : atoms) {
      if (atom instanceof SWRLBuiltInAtom) {
        SWRLAPIBuiltInAtom builtInAtom = (SWRLAPIBuiltInAtom)atom;
        if (builtInNames.contains(builtInAtom.getBuiltInPrefixedName()))
          result.add(builtInAtom);
      }
    }
    return result;
  }

  @NonNull private static Set<@NonNull IRI> getReferencedVariableIRIs(@NonNull SWRLAtom atom)
  {
    Set<@NonNull IRI> referencedVariableIRIs = new HashSet<>();

    for (SWRLArgument argument : atom.getAllArguments()) {
      if (argument instanceof SWRLVariable) {
        SWRLVariable variable = (SWRLVariable)argument;
        referencedVariableIRIs.add(variable.getIRI());
      }
    }
    return referencedVariableIRIs;
  }

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    if (!super.equals(o))
      return false;

    DefaultSWRLAPIRule that = (DefaultSWRLAPIRule)o;

    if (active != that.active)
      return false;
    if (!Objects.equals(ruleName, that.ruleName))
      return false;
    if (!Objects.equals(comment, that.comment))
      return false;
    if (!Objects.equals(bodyAtoms, that.bodyAtoms))
      return false;
    return Objects.equals(headAtoms, that.headAtoms);

  }

  @Override public int hashCode()
  {
    int result = super.hashCode();
    result = 31 * result + (ruleName != null ? ruleName.hashCode() : 0);
    result = 31 * result + (active ? 1 : 0);
    result = 31 * result + (comment != null ? comment.hashCode() : 0);
    result = 31 * result + (bodyAtoms != null ? bodyAtoms.hashCode() : 0);
    result = 31 * result + (headAtoms != null ? headAtoms.hashCode() : 0);
    return result;
  }
}
