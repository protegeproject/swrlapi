package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.core.arguments.SQWRLCollectionVariableBuiltInArgument;

class SQWRLCollectionVariableBuiltInArgumentImpl extends SWRLVariableBuiltInArgumentImpl implements
		SQWRLCollectionVariableBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	private final String queryName, collectionName, collectionGroupID;

	public SQWRLCollectionVariableBuiltInArgumentImpl(IRI iri, String variableName, String queryName, String collectionName,
			String collectionGroupID)
	{
		super(iri, variableName);
		this.queryName = queryName;
		this.collectionName = collectionName;
		this.collectionGroupID = collectionGroupID;
	}

	@Override
	public String getGroupID()
	{
		return this.collectionGroupID;
	}

	@Override
	public String getQueryName()
	{
		return this.queryName;
	}

	@Override
	public String getCollectionName()
	{
		return this.collectionName;
	}

	@Override
	public String toDisplayText()
	{
		return getQueryName() + ":" + getCollectionName() + "@" + getGroupID();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SQWRLCollectionVariableBuiltInArgumentImpl impl = (SQWRLCollectionVariableBuiltInArgumentImpl)obj;
		return (getQueryName() == impl.getQueryName() || (getQueryName() != null && getQueryName().equals(
				impl.getQueryName())))
				&& (getCollectionName() == impl.getCollectionName() || (getCollectionName() != null && getCollectionName()
						.equals(impl.getCollectionName())))
				&& (getGroupID() == impl.getGroupID() || (getGroupID() != null && getGroupID().equals(impl.getGroupID())));
	}

	@Override
	public int hashCode()
	{
		int hash = 12;
		hash = hash + (null == getQueryName() ? 0 : getQueryName().hashCode());
		hash = hash + (null == getCollectionName() ? 0 : getCollectionName().hashCode());
		hash = hash + (null == getGroupID() ? 0 : getGroupID().hashCode());
		return hash;
	}
}
