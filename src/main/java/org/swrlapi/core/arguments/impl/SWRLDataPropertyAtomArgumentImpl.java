package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.swrlapi.core.arguments.SWRLDataPropertyAtomArgument;

class SWRLDataPropertyAtomArgumentImpl extends SWRLPropertyAtomArgumentImpl implements SWRLDataPropertyAtomArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLDataPropertyAtomArgumentImpl(IRI propertyIRI)
	{
		super(propertyIRI);
	}

	public SWRLDataPropertyAtomArgumentImpl(OWLDataProperty property)
	{
		super(property);
	}
}