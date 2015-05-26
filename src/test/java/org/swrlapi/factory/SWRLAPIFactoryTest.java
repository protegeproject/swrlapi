package org.swrlapi.factory;

import junit.framework.TestCase;

import javax.swing.*;

/**
 * @see SWRLAPIFactory
 */
public class SWRLAPIFactoryTest extends TestCase
{
  public void testGetSQWRLIcon() throws Exception
  {
    Icon icon = SWRLAPIFactory.getSQWRLIcon();
    assertNotNull(icon);
  }

  public void testGetOWL2RLReasonerIcon() throws Exception
  {
    Icon icon= SWRLAPIFactory.getOWL2RLReasonerIcon();
    assertNotNull(icon);
  }
}