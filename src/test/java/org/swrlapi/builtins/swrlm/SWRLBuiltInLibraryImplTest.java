package org.swrlapi.builtins.swrlm;

import junit.framework.TestCase;
import org.swrlapi.factory.SWRLBuiltInArgumentFactory;
import org.swrlapi.factory.DefaultIRIResolver;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.factory.SWRLAPIFactory;

/**
 * @see org.swrlapi.builtins.swrlm.SWRLBuiltInLibraryImpl
 */
public class SWRLBuiltInLibraryImplTest extends TestCase
{
  private final SWRLBuiltInLibraryImpl libraryImpl = new SWRLBuiltInLibraryImpl();
  private final IRIResolver iriResolver = new DefaultIRIResolver();
  private final SWRLBuiltInArgumentFactory argumentFactory = SWRLAPIFactory
    .createSWRLBuiltInArgumentFactory(iriResolver);

  public void setUp() throws Exception
  {
    super.setUp();

    libraryImpl.reset();
  }

  public void testSqrt() throws Exception
  {
    // TODO Need a mock bridge
    //    List<SWRLBuiltInArgument> arguments = new ArrayList<>();
    //    SWRLBuiltInArgument argument1 = argumentFactory.getLiteralBuiltInArgument(2);
    //    SWRLBuiltInArgument argument2 = argumentFactory.getLiteralBuiltInArgument(4);
    //    arguments.add(argument1);
    //    arguments.add(argument2);
    //
    //    assertTrue(libraryImpl.sqrt(arguments));
  }
}