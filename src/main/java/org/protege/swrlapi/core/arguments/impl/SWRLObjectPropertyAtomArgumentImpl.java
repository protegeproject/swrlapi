package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLObjectPropertyAtomArgument;
import org.semanticweb.owlapi.model.OWLObjectProperty;

class SWRLObjectPropertyAtomArgumentImpl extends SWRLPropertyAtomArgumentImpl implements SWRLObjectPropertyAtomArgument
{
	public SWRLObjectPropertyAtomArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}

	public SWRLObjectPropertyAtomArgumentImpl(OWLObjectProperty property)
	{
		super(property);
	}
}