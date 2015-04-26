package org.swrlapi.ui.controller;

import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.view.SWRLAPIApplicationView;

/**
 * Provides an application controller that can be used to build a MVC-based GUI that uses the SWRLAPI. Used in
 * conjunction with a {@link SWRLRuleEngineModel} and a {@link SWRLAPIApplicationView}.
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
