package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLPropertyAtomArgument;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedObject;

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