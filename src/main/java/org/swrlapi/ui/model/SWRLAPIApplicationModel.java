package org.swrlapi.ui.model;

import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLQueryEngine;

/**
 * Provides an application model that can be used to build a MVC-based GUI that uses the SWRLAPI. Used in conjunction
 * with a {@link org.swrlapi.ui.model.SWRLAPIApplicationModel} and a
 * {@link org.swrlapi.ui.controller.SWRLAPIApplicationController}.
 *
 * @see org.swrlapi.ui.view.SWRLAPIApplicationView
 * @see org.swrlapi.ui.controller.SWRLAPIApplicationController
 */
public interface SWRLAPIApplicationModel extends SWRLAPIModel
{
	/**
	 * @return A SWRL rule engine
	 */
	SWRLRuleEngine getSWRLRuleEngine();

	/**
	 * @return A SQWRL query engine
	 */
	SQWRLQueryEngine getSQWRLQueryEngine();

	/**
	 * @return A SWRL parser
	 */
	SWRLParser getSWRLParser();

	/**
	 * @return A SWRL auto-completer
	 */
	SWRLAutoCompleter getSWRLAutoCompleter();

	/**
	 * @return A SWRL rules table model
	 */
	SWRLRulesTableModel getSWRLRulesTableModel();

	/**
	 * @return True if the rules in the underlying ontology have been modified since the last call to
	 *         {@link SWRLAPIApplicationModel#clearSWRLRulesModified()}.
	 */
	boolean areSWRLRulesModified();

	/**
	 * Clear the modified status of SWRL rules. Used in conjunction with {@link #areSWRLRulesModified()}.
	 */
	void clearSWRLRulesModified();
}
