package org.protege.swrlapi.sqwrl.values.impl;

import org.protege.swrlapi.sqwrl.values.SQWRLNamedResultValue;
import org.protege.swrlapi.sqwrl.values.SQWRLResultValue;
import org.semanticweb.owlapi.model.IRI;

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
		return prefixedName;
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
		return getPrefixedName();
	}
}
