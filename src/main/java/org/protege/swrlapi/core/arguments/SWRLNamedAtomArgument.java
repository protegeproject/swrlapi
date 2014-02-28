
package org.protege.swrlapi.core.arguments;

import java.net.URI;

public interface SWRLNamedAtomArgument extends SWRLAtomArgument
{
	URI getURI();

	String getPrefixedName();
}
