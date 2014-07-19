package org.swrlapi.sqwrl;

import java.util.Set;

import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

/**
 * This interface defines the methods that must be provided by a SQWRL query engine.
 * 
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see org.swrlapi.sqwrl.SQWRLResult
 */
public interface SQWRLQueryEngine
{
	/**
	 * Run a named SQWRL query. SWRL rules will also be executed and any inferences produced by them will be available in
	 * the query.
	 */
	SQWRLResult runSQWRLQuery(String queryName) throws SQWRLException;

	/**
	 * Run all enabled SQWRL queries.
	 */
	void runSQWRLQueries() throws SQWRLException;

	/**
	 * Create and run a SQWRL query. The query will be created and added to the associated ontology.
	 */
	SQWRLResult runSQWRLQuery(String queryName, String queryText) throws SQWRLException;

	/**
	 * Create a SQWRL query.
	 */
	void createSQWRLQuery(String queryName, String queryText) throws SQWRLException;

	/**
	 * Get the results from a previously executed SQWRL query. Null is returned if there is no result.
	 */
	SQWRLResult getSQWRLResult(String queryName) throws SQWRLException;

	/**
	 * Get all the enabled SQWRL queries in the ontology.
	 */
	Set<SQWRLQuery> getSQWRLQueries() throws SQWRLException;

	/**
	 * Get the names of the enabled SQWRL queries in the ontology.
	 */
	Set<String> getSQWRLQueryNames();

	/**
	 * Reset the query engine.
	 */
	void reset() throws SWRLRuleEngineException;

	/**
	 * Returns the name of the underlying target rule engine.
	 */
	String getTargetRuleEngineName();

	/**
	 * Returns the version number of the underlying target rule engine.
	 */
	String getTargetRuleEngineVersion();

	/**
	 * Get the underlying controller for the OWL 2 RL reasoner used by the rule and query engine.
	 */
	OWL2RLEngine getOWL2RLEngine();
}
