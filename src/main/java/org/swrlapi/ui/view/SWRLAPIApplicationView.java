package org.swrlapi.ui.view;

/**
 * Provides a base application view that can be used to build a MVC-based GUI that uses the SWRLAPI. Used in conjunction
 * with a {@link org.swrlapi.ui.view.SWRLAPIApplicationView} and a
 * {@link org.swrlapi.ui.controller.SWRLAPIApplicationController}.
 *
 * @see org.swrlapi.ui.model.SWRLRuleEngineModel
 * @see org.swrlapi.ui.controller.SWRLAPIApplicationController
 */
public interface SWRLAPIApplicationView extends SWRLAPIView
{
	String getApplicationName();
}
