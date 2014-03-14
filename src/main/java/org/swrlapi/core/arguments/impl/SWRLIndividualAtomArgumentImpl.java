package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.core.arguments.SWRLIndividualAtomArgument;

class SWRLIndividualAtomArgumentImpl extends SWRLNamedAtomArgumentImpl implements SWRLIndividualAtomArgument
{
	private static final long serialVersionUID = -2877896006624040094L;

	public SWRLIndividualAtomArgumentImpl(IRI individualIRI)
	{
		super(individualIRI);
	}
}
