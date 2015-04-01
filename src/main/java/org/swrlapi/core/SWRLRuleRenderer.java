package org.swrlapi.core;

import org.semanticweb.owlapi.model.SWRLRule;
import org.swrlapi.core.visitors.SWRLAPIEntityVisitorEx;
import org.swrlapi.sqwrl.SQWRLQuery;

/**
 * A renderer for {@link org.swrlapi.core.SWRLAPIRule} and {@link org.swrlapi.sqwrl.SQWRLQuery} objects.
 *
 * Can create using the {@link org.swrlapi.core.SWRLAPIFactory}.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see org.swrlapi.core.SWRLAPIFactory
 */
public interface SWRLRuleRenderer extends SWRLAPIEntityVisitorEx<String>
{
	String renderSWRLRule(SWRLRule rule);

	String renderSQWRLQuery(SQWRLQuery query);
}
