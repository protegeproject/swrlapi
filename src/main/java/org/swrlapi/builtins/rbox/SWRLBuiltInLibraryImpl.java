package org.swrlapi.builtins.rbox;

import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;

/**
 * Implementation library for SWRL RBox built-ins
 */
public class SWRLBuiltInLibraryImpl extends AbstractSWRLBuiltInLibrary
{
  private static final String SWRLRBoxLibraryName = "SWRLRBoxBuiltIns";

  public SWRLBuiltInLibraryImpl()
  {
    super(SWRLRBoxLibraryName);
  }

  // TRANSITIVE_OBJECT_PROPERTY, DISJOINT_DATA_PROPERTIES, SUB_DATA_PROPERTY, EQUIVALENT_DATA_PROPERTIES,
  // DISJOINT_OBJECT_PROPERTIES, SUB_OBJECT_PROPERTY, EQUIVALENT_OBJECT_PROPERTIES, SUB_PROPERTY_CHAIN_OF,
  // INVERSE_OBJECT_PROPERTIES, SYMMETRIC_OBJECT_PROPERTY, ASYMMETRIC_OBJECT_PROPERTY, REFLEXIVE_OBJECT_PROPERTY,
  // IRREFLEXIVE_OBJECT_PROPERTY

  @Override public void reset()
  {
  }
}
