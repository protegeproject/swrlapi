package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyAtomArgument;

class SWRLAnnotationPropertyAtomArgumentImpl extends SWRLPropertyAtomArgumentImpl implements
		SWRLAnnotationPropertyAtomArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLAnnotationPropertyAtomArgumentImpl(IRI propertyIRI)
	{
		super(propertyIRI);
	}
}