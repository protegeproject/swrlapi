package org.swrlapi.visitors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.core.SWRLAPIBuiltInAtom;

public interface SWRLAPIBuiltInAtomVisitorEx<T>
{
  @NonNull T visit(SWRLAPIBuiltInAtom swrlapiBuiltInAtom);
}
