package org.protege.swrlapi.core.arguments.impl;

import java.net.URI;

import org.protege.swrlapi.core.arguments.SWRLDatatypeAtomArgument;

class SWRLDatatypeAtomArgumentImpl extends SWRLNamedAtomArgumentImpl implements SWRLDatatypeAtomArgument
{
	public SWRLDatatypeAtomArgumentImpl(URI uri, String prefixedName)
	{
		super(uri, prefixedName);
	}
}
