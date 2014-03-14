package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.core.arguments.SWRLDatatypeAtomArgument;

class SWRLDatatypeAtomArgumentImpl extends SWRLNamedAtomArgumentImpl implements SWRLDatatypeAtomArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLDatatypeAtomArgumentImpl(IRI uri)
	{
		super(uri);
	}
}
