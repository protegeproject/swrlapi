package org.swrlapi.sqwrl;

import java.util.Set;

import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

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
	 * @throws SQWRLException If an error occurs during processing
	 */
	SQWRLResult runSQWRLQuery(String queryName, String queryText) throws SQWRLException;

	/**
	 * Create a SQWRL query.
	 *
	 * @param queryName The name of the query
	 * @param queryText The query
	 * @throws SQWRLException If an error occurs during processing
	 */
	void createSQWRLQuery(String queryName, String queryText) throws SQWRLException;

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
	 * Reset the query engine.
	 */
	void reset();

	/**
	 * Returns the name of the underlying target rule engine.
	 *
	 * @return The name of the target rule engine
	 */
	String getTargetRuleEngineName();

	/**
	 * Returns the version number of the underlying target rule engine.
	 *
	 * @return The version of the target rule engine
	 */
	String getTargetRuleEngineVersion();

	/**
	 * Get the underlying controller for the OWL 2 RL reasoner used by the rule and query engine.
	 *
	 * @return The underlying OWL 2 RL engine
	 */
	OWL2RLEngine getOWL2RLEngine();
}
