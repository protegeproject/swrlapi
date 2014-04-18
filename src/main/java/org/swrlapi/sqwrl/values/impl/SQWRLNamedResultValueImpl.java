package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLNamedResultValue;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

abstract class SQWRLNamedResultValueImpl implements SQWRLNamedResultValue
{
	private final IRI classIRI;
	private final String prefixedName;

	protected SQWRLNamedResultValueImpl(IRI classIRI, String prefixedName)
	{
		this.classIRI = classIRI;
		this.prefixedName = prefixedName;
	}

	@Override
	public IRI getIRI()
	{
		return classIRI;
	}

	@Override
	public String getPrefixedName()
	{
		return this.prefixedName;
	}

	// TODO: fix
	@Override
	public int compareTo(SQWRLResultValue o)
	{
		return classIRI.compareTo(((SQWRLNamedResultValueImpl)o).getIRI());
	}

	@Override
	public String toString()
	{
		return prefixedName;
	}
}
