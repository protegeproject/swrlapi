package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLIndividualAtomArgument;
import org.semanticweb.owlapi.model.IRI;

class SWRLIndividualAtomArgumentImpl extends SWRLNamedAtomArgumentImpl implements SWRLIndividualAtomArgument
{
	private static final long serialVersionUID = -2877896006624040094L;

	public SWRLIndividualAtomArgumentImpl(IRI individualIRI, String prefixedName)
	{
		super(individualIRI, prefixedName);
	}
}
