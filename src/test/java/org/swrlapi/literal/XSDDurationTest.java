package org.swrlapi.literal;

import org.junit.Test;

/**
 * @see XSDDuration
 */
public class XSDDurationTest
{
  @Test(expected = IllegalArgumentException.class) public void testConstruction()
  {
    XSDDuration duration = new XSDDuration("X");
  }

  @Test public void testValidate() throws Exception
  {
    XSDDuration duration = new XSDDuration("P43Y");
    duration.validate();
  }

}