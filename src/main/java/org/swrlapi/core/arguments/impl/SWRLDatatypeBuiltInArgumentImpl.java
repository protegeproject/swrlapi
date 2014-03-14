package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;

class SWRLDatatypeBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLDatatypeBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLDatatypeBuiltInArgumentImpl(IRI iri)
	{
		super(iri);
	}

	public SWRLDatatypeBuiltInArgumentImpl(OWLDatatype datatype)
	{
		super(datatype);
	}
}
