package org.swrlapi.ui.controller;

import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.view.SWRLAPIApplicationView;

/**
 * Provides an application controller that can be used to build a MVC-based GUI that uses the SWRLAPI. Used in
 * conjunction with a {@link SWRLAPIApplicationModel} and a {@link SWRLAPIApplicationView}.
 *
 * @see org.swrlapi.ui.model.SWRLAPIApplicationModel
 * @see org.swrlapi.ui.view.SWRLAPIApplicationView
 */
public interface SWRLAPIApplicationController
{
	/**
	 * @return The application model associated with the controller
	 */
	SWRLAPIApplicationModel getApplicationModel();
}
