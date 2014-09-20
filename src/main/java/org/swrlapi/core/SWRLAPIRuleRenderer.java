package org.swrlapi.core;

import org.semanticweb.owlapi.model.SWRLRule;
import org.swrlapi.core.visitors.SWRLAPIEntityVisitorEx;

/**
 * A renderer for {@link org.swrlapi.core.SWRLAPIRule} and {@link org.swrlapi.sqwrl.SQWRLQuery} objects.
 *
 * Can create using the {@link org.swrlapi.core.SWRLAPIFactory}.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see org.swrlapi.core.SWRLAPIFactory
 */
public interface SWRLAPIRuleRenderer extends SWRLAPIEntityVisitorEx<String>
{
	String render(SWRLRule swrlapiRule);
}
