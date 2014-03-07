package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLDataPropertyAtomArgument;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataProperty;

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