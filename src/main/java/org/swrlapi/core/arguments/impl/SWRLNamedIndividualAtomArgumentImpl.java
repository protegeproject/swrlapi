package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.core.arguments.SWRLNamedIndividualAtomArgument;

class SWRLNamedIndividualAtomArgumentImpl extends SWRLNamedAtomArgumentImpl implements SWRLNamedIndividualAtomArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLNamedIndividualAtomArgumentImpl(IRI individualIRI)
	{
		super(individualIRI);
	}
}
