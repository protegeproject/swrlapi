package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.swrlapi.core.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;

class SWRLDatatypeBuiltInArgumentImpl extends SWRLNamedBuiltInArgumentImpl implements SWRLDatatypeBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLDatatypeBuiltInArgumentImpl(OWLDatatype datatype)
	{
		super(datatype);
	}

	@Override
	public OWLDatatype getOWLDatatype()
	{
		return getOWLEntity().asOWLDatatype();
	}

	@Override
	public <T> T accept(SWRLBuiltInArgumentVisitorEx<T> visitor)
	{
		return visitor.visit(this);
	}

}
