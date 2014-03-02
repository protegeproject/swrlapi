package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLDatatypeAtomArgument;
import org.semanticweb.owlapi.model.IRI;

class SWRLDatatypeAtomArgumentImpl extends SWRLNamedAtomArgumentImpl implements SWRLDatatypeAtomArgument
{
	private static final long serialVersionUID = -1389527320122265684L;

	public SWRLDatatypeAtomArgumentImpl(IRI uri, String prefixedName)
	{
		super(uri, prefixedName);
	}
}
