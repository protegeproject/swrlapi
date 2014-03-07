package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyAtomArgument;
import org.semanticweb.owlapi.model.IRI;

class SWRLAnnotationPropertyAtomArgumentImpl extends SWRLPropertyAtomArgumentImpl implements
		SWRLAnnotationPropertyAtomArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLAnnotationPropertyAtomArgumentImpl(IRI propertyIRI)
	{
		super(propertyIRI);
	}
}