package org.swrlapi.visitors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;

public interface SWRLAPIEntityVisitorEx<@NonNull T>
  extends SWRLObjectVisitorEx<T>, SWRLBuiltInArgumentVisitorEx<@NonNull T>, SWRLAPIBuiltInAtomVisitorEx<@NonNull T>
{}
