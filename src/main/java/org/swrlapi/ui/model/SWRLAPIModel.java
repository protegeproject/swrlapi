package org.swrlapi.ui.model;

import org.swrlapi.ui.view.SWRLAPIView;

/**
 * Represents a base SWRLAPI model that can be used to construct MVC-based views in an application that uses the
 * SWRLAPI.
 *
 * @see org.swrlapi.ui.view.SWRLAPIView
 */
public interface SWRLAPIModel
{
	void updateView();
}
