package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLDataPropertyAtomArgument;
import org.semanticweb.owlapi.model.OWLDataProperty;

class SWRLDataPropertyAtomArgumentImpl extends SWRLPropertyAtomArgumentImpl implements SWRLDataPropertyAtomArgument
{
	public SWRLDataPropertyAtomArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	public SWRLDataPropertyAtomArgumentImpl(OWLDataProperty property)
	{
		super(property);
	}
}