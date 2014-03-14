package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.swrlapi.core.arguments.SWRLPropertyAtomArgument;

abstract class SWRLPropertyAtomArgumentImpl extends SWRLNamedAtomArgumentImpl implements SWRLPropertyAtomArgument
{
	private static final long serialVersionUID = 1L;

	protected SWRLPropertyAtomArgumentImpl(IRI propertyIRI)
	{
		super(propertyIRI);
	}

	protected SWRLPropertyAtomArgumentImpl(OWLNamedObject property)
	{
		super(property);
	}
}