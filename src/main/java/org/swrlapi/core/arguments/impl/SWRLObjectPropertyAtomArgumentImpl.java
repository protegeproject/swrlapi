package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.core.arguments.SWRLObjectPropertyAtomArgument;

class SWRLObjectPropertyAtomArgumentImpl extends SWRLPropertyAtomArgumentImpl implements SWRLObjectPropertyAtomArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLObjectPropertyAtomArgumentImpl(IRI propertyIRI)
	{
		super(propertyIRI);
	}

	public SWRLObjectPropertyAtomArgumentImpl(OWLObjectProperty property)
	{
		super(property);
	}
}