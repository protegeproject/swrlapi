package org.swrlapi.visitors;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;

public interface SWRLAPIEntityVisitorEx<@NonNull T> extends SWRLObjectVisitorEx<T>, SWRLBuiltInArgumentVisitorEx<T>,
    SWRLAPIBuiltInAtomVisitorEx<T>
{
}
