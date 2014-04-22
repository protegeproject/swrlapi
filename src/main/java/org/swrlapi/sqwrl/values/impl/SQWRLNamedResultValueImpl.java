package org.swrlapi.sqwrl.values.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.sqwrl.values.SQWRLNamedResultValue;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

abstract class SQWRLNamedResultValueImpl implements SQWRLNamedResultValue
{
	private final IRI iri;
	private final String prefixedName;

	protected SQWRLNamedResultValueImpl(IRI iri, String prefixedName)
	{
		this.iri = iri;
		this.prefixedName = prefixedName;
	}

	@Override
	public IRI getIRI()
	{
		return iri;
	}

	@Override
	public String getPrefixedName()
	{
		return this.prefixedName;
	}

	// TODO Look at
	@Override
	public int compareTo(SQWRLResultValue o)
	{
		if (!(o instanceof SQWRLNamedResultValue))
			return -1;
		else
			return iri.compareTo(((SQWRLNamedResultValue)o).getIRI());
	}

	@Override
	public String toString()
	{
		return prefixedName;
	}
}
