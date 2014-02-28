package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.owl.portability.model.OWLDatatypeAdapter;
import org.protege.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;

class SWRLDatatypeBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLDatatypeBuiltInArgument
{
	public SWRLDatatypeBuiltInArgumentImpl(URI uri, String prefixedName)
	{
		super(uri, prefixedName);
	}

	public SWRLDatatypeBuiltInArgumentImpl(OWLDatatypeAdapter datatype)
	{
		super(datatype);
	}
}
