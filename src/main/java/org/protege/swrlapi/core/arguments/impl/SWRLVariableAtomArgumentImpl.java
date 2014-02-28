package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLVariableAtomArgument;

class SWRLVariableAtomArgumentImpl implements SWRLVariableAtomArgument
{
	private final String variableName;

	public SWRLVariableAtomArgumentImpl(String variableName)
	{
		this.variableName = variableName;
	}

	@Override
	public String getVariableName()
	{
		return this.variableName;
	}

	@Override
	public String toDisplayText()
	{
		return "?" + this.variableName;
	}

	@Override
	public String toString()
	{
		return toDisplayText();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SWRLVariableAtomArgumentImpl impl = (SWRLVariableAtomArgumentImpl)obj;
		return (((this.variableName == impl.variableName || this.variableName != null
				&& this.variableName.equals(impl.variableName))));
	}

	@Override
	public int hashCode()
	{
		int hash = 78;
		hash = hash + (null == this.variableName ? 0 : this.variableName.hashCode());
		return hash;
	}
}
