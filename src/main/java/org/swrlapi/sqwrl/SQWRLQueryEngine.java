package org.swrlapi.sqwrl;

import checkers.nullness.quals.NonNull;
import org.swrlapi.core.SQWRLQueryRenderer;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import javax.swing.*;
import java.util.Set;

/**
 * This interface defines the methods that must be provided by a SQWRL query engine.
 *
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see org.swrlapi.sqwrl.SQWRLResult
 */
public interface SQWRLQueryEngine extends SWRLRuleEngine
{
  /**
   * Create and run a SQWRL query. The query will be created and added to the associated ontology.
   * 
   * @param queryName The name of the query
   * @param queryText The query
   * @return The result of the query
   * @throws SWRLParseException If a parsing error occurs during processing
   * @throws SQWRLException If a SQWRL-specific error occurs during processing
   */
  SQWRLResult runSQWRLQuery(String queryName, String queryText) throws SWRLParseException, SQWRLException;

  /**
   * Create a SQWRL query.
   *
   * @param queryName The name of the query
   * @param queryText The query
   * @throws SWRLParseException If a parsing error occurs during processing
   * @throws SQWRLException If a SQWRL-specific error occurs during processing
   */
  void createSQWRLQuery(String queryName, String queryText) throws SWRLParseException, SQWRLException;

  /**
   * Run a named SQWRL query. SWRL rules will also be executed and any inferences produced by them will be available in
   * the query.
   * 
   * @param queryName The name of the query
   * @return The result of the query
   * @throws SQWRLException If an exception occurs during processing
   */
  SQWRLResult runSQWRLQuery(String queryName) throws SQWRLException;

  /**
   * Run all enabled SQWRL queries.
   *
   * @throws SQWRLException If an error occurs during processing
   */
  void runSQWRLQueries() throws SQWRLException;

  /**
   * Get the results from a previously executed SQWRL query. Null is returned if there is no result.
   *
   * @param queryName The name of the query
   * @return The result of the query
   * @throws SQWRLException If an error occurs during processing
   */
  SQWRLResult getSQWRLResult(String queryName) throws SQWRLException;

  /**
   * Get all the enabled SQWRL queries in the ontology.
   *
   * @return A set of queries
   */
  Set<SQWRLQuery> getSQWRLQueries();

  /**
   * Get the names of the enabled SQWRL queries in the ontology.
   *
   * @return The names of all SQWRL queries
   */
  Set<String> getSQWRLQueryNames();

  /**
   * @return A SQWRL query renderer
   */
  @NonNull SQWRLQueryRenderer createSQWRLQueryRenderer();

  /**
   * Load specific query, all enabled rules, and relevant knowledge from OWL. All existing bridge rules and knowledge
   * will first be cleared and the rule engine will be reset.
   *
   * @param queryName The name of the query
   * @throws SWRLRuleEngineException If an error occurs during inference
   */
  void importSQWRLQueryAndOWLAxioms(String queryName) throws SWRLRuleEngineException;

  /**
   * Returns the name of the underlying target query engine.
   *
   * @return The name of the target query engine
   */
  String getQueryEngineName();

  /**
   * Returns the version number of the underlying target query engine.
   *
   * @return The version of the target query engine
   */
  String getQueryEngineVersion();

  /**
   *
   * @return An icon representing the query engine
   */
  Icon getQueryEngineIcon();
}
