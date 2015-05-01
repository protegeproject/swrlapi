package org.swrlapi.core.visitors;

import org.swrlapi.core.SWRLAPIBuiltInAtom;

public interface SWRLAPIBuiltInAtomVisitorEx<T>
{
  T visit(SWRLAPIBuiltInAtom swrlapiBuiltInAtom);
}
