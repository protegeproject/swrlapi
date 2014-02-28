package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyAtomArgument;

class SWRLAnnotationPropertyAtomArgumentImpl extends SWRLPropertyAtomArgumentImpl implements
		SWRLAnnotationPropertyAtomArgument
{
	public SWRLAnnotationPropertyAtomArgumentImpl(URI propertyURI, String prefixedName)
	{
		super(propertyURI, prefixedName);
	}
}