package org.swrlapi.core.visitors;

import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;

public interface SWRLAPIEntityVisitorEx<T> extends SWRLObjectVisitorEx<T>, SWRLBuiltInArgumentVisitorEx<T>,
    SWRLAPIBuiltInAtomVisitorEx<T>
{
}
