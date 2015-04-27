package org.swrlapi.ui.controller;

import org.swrlapi.ui.model.SWRLRuleEngineModel;

/**
 * Provides an MVC-based rule engine controller. Used in conjunction with a {@link SWRLRuleEngineModel} and a
 * {@link SWRLAPIApplicationView}.
 *
 * @see org.swrlapi.ui.model.SWRLRuleEngineModel
 * @see org.swrlapi.ui.view.SWRLAPIApplicationView
 */
public interface SWRLRuleEngineController
{
	/**
	 * @return The rule engine model associated with the controller
	 */
	SWRLRuleEngineModel getSWRLRuleEngineModel();
}
