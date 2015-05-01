package org.swrlapi.sqwrl;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 * Represents a SQWRL query in the SWRLAPI. A {@link org.swrlapi.core.SWRLAPIOntologyProcessor} can be used to extract
 * SQWRL queries - which are stored as {@link org.swrlapi.core.SWRLAPIRule}s - from an OWL ontology.
 * <p>
 * A {@link org.swrlapi.core.SWRLRuleRenderer} can be used to display a query.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLResult
 * @see org.swrlapi.sqwrl.SQWRLResultGenerator
 * @see org.swrlapi.core.SWRLRuleRenderer
 */
public interface SQWRLQuery
{
  /**
   * @return The name of the query
   */
  String getQueryName();

  /**
   * @return True if the query is active
   */
  boolean isActive();

  /**
   * @param isActive The active status
   */
  void setActive(boolean isActive);

  /**
   * @return A comment associated with the query
   */
  String getComment();

  /**
   * @return A list of SWRL atoms
   */
  List<SWRLAtom> getHeadAtoms();

  /**
   * @return A list of SWRL atoms
   */
  List<SWRLAtom> getBodyAtoms();

  SQWRLResult getSQWRLResult() throws SQWRLException;

  /**
   * @return True if the query uses SQWRL collections
   */
  boolean hasSQWRLCollections();

  /**
   * @return A list of SWRL atoms
   */
  List<SWRLAtom> getSQWRLPhase1BodyAtoms();

  /**
   * @return A list of SWRL atoms
   */
  List<SWRLAtom> getSQWRLPhase2BodyAtoms();

  /**
   * @param builtInNames A set of built-in names
   * @return A list of built-in atoms
   */
  List<SWRLAPIBuiltInAtom> getBuiltInAtomsFromBody(Set<String> builtInNames);

  /**
   * @return A SQWRL result generator
   */
  SQWRLResultGenerator getSQWRLResultGenerator();
}
