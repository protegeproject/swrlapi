package org.swrlapi.core;

import org.swrlapi.core.visitors.SWRLAPIEntityVisitorEx;

/**
 * @see org.swrlapi.core.SWRLAPIRule
 */
public interface SWRLAPIRuleRenderer extends SWRLAPIEntityVisitorEx<String>
{
	String render(SWRLAPIRule swrlapiRule);
}
