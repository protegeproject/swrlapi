package org.swrlapi.bridge;

import org.swrlapi.builtins.SWRLBuiltInBridge;
import org.swrlapi.builtins.SWRLBuiltInBridgeController;

public interface SWRLBridge extends SWRLRuleEngineBridge, SWRLBuiltInBridge, SWRLBuiltInBridgeController,
  SWRLRuleEngineBridgeController
{
}
