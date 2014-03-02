package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;

class SQWRLCollectionBuiltInArgumentImpl extends SWRLBuiltInArgumentImpl implements SQWRLCollectionBuiltInArgument
{
	private static final long serialVersionUID = 1518745061145936538L;

	private final String queryName, collectionName, collectionGroupID;

	public SQWRLCollectionBuiltInArgumentImpl(String queryName, String collectionName, String collectionGroupID)
	{
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
	public String toString()
	{
		return toDisplayText();
	}

	// TODO look at this. Collection arguments are not really comparable.
	public int compareTo(SQWRLCollectionBuiltInArgument ca)
	{
		return toString().compareTo(ca.toString());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SQWRLCollectionBuiltInArgumentImpl impl = (SQWRLCollectionBuiltInArgumentImpl)obj;
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
