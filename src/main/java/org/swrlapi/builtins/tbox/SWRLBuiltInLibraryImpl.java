package org.swrlapi.builtins.tbox;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

import java.util.List;

/**
 * Implementation library for SWRL TBox built-ins
 */
public class SWRLBuiltInLibraryImpl extends AbstractSWRLBuiltInLibrary
{
  private static final String SWRLTBoxLibraryName = "SWRLTBoxBuiltIns";

  public SWRLBuiltInLibraryImpl()
  {
    super(SWRLTBoxLibraryName);
  }

  @Override public void reset()
  {
  }

  // SUBCLASS_OF, EQUIVALENT_CLASSES, DISJOINT_CLASSES, OBJECT_PROPERTY_DOMAIN, OBJECT_PROPERTY_RANGE, FUNCTIONAL_OBJECT_PROPERTY,
  // INVERSE_FUNCTIONAL_OBJECT_PROPERTY, DATA_PROPERTY_DOMAIN, DATA_PROPERTY_RANGE, FUNCTIONAL_DATA_PROPERTY,
  // DATATYPE_DEFINITION, DISJOINT_UNION, HAS_KEY

  public boolean sca(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return false;
  }
}
