package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLPropertyAtomArgument;
import org.semanticweb.owlapi.model.OWLNamedObject;

abstract class SWRLPropertyAtomArgumentImpl extends SWRLNamedAtomArgumentImpl implements SWRLPropertyAtomArgument
{
	private static final long serialVersionUID = 4770063557570548669L;

	protected SWRLPropertyAtomArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	protected SWRLPropertyAtomArgumentImpl(OWLNamedObject property)
	{
		super(property);
	}
}