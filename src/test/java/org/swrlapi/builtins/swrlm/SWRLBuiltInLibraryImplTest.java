package org.swrlapi.builtins.swrlm;

import junit.framework.TestCase;

/**
 * @see org.swrlapi.builtins.swrlm.SWRLBuiltInLibraryImpl
 */
public class SWRLBuiltInLibraryImplTest extends TestCase
{
  private final SWRLBuiltInLibraryImpl libraryImpl = new SWRLBuiltInLibraryImpl();

  public void setUp() throws Exception
  {
    super.setUp();

    libraryImpl.reset();
  }

  public void testSqrt() throws Exception
  {
    // TODO Need a mock bridge for built-in library tests
    //    List<@NonNull SWRLBuiltInArgument> arguments = new ArrayList<>();
    //    SWRLBuiltInArgument argument1 = argumentFactory.getLiteralBuiltInArgument(2);
    //    SWRLBuiltInArgument argument2 = argumentFactory.getLiteralBuiltInArgument(4);
    //    arguments.add(argument1);
    //    arguments.add(argument2);
    //
    //    assertTrue(libraryImpl.sqrt(arguments));
  }
}