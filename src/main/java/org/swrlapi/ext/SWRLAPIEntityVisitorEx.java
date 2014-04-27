package org.swrlapi.ext;

import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.swrlapi.core.arguments.SWRLBuiltInArgumentVisitorEx;

public interface SWRLAPIEntityVisitorEx<T> extends SWRLObjectVisitorEx<T>, SWRLBuiltInArgumentVisitorEx<T>,
		SWRLAPIBuiltInAtomVisitorEx<T>
{
}
