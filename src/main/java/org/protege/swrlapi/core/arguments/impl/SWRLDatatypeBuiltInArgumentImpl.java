package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;

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
