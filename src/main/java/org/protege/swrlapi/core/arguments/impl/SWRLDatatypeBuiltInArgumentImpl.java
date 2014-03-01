package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.semanticweb.owlapi.model.OWLDatatype;

class SWRLDatatypeBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLDatatypeBuiltInArgument
{
	public SWRLDatatypeBuiltInArgumentImpl(URI uri, String prefixedName)
	{
		super(uri, prefixedName);
	}

	public SWRLDatatypeBuiltInArgumentImpl(OWLDatatype datatype)
	{
		super(datatype);
	}
}
