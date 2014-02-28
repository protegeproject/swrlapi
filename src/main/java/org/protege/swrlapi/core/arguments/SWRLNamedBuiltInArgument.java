
package org.protege.swrlapi.core.arguments;

import java.net.URI;

public interface SWRLNamedBuiltInArgument extends SWRLBuiltInArgument
{
	URI getURI();

	String getPrefixedName();
}
