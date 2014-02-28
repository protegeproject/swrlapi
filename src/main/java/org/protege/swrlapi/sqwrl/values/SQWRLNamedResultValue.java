package org.protege.swrlapi.sqwrl.values;

import java.net.URI;

public interface SQWRLNamedResultValue extends SQWRLResultValue
{
	URI getURI();

	String getPrefixedName();
}