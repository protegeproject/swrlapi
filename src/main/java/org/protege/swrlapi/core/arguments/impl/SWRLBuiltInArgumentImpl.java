package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;

abstract class SWRLBuiltInArgumentImpl implements SWRLBuiltInArgument
{
	// There is an equals methods defined for this class.
	private String variableName;
	private SWRLBuiltInArgument builtInResult; // Used to store result of binding for unbound arguments
	boolean isBound;

	public SWRLBuiltInArgumentImpl(String variableName)
	{
		this.variableName = variableName;
		this.builtInResult = null;
		this.isBound = true;
	}

	public SWRLBuiltInArgumentImpl()
	{
		this.variableName = null;
		this.builtInResult = null;
		this.isBound = true;
	}

	@Override
	public boolean isVariable()
	{
		return this.variableName != null;
	}

	@Override
	public void setVariableName(String variableName)
	{
		this.variableName = variableName;
	}

	@Override
	public void setBuiltInResult(SWRLBuiltInArgument builtInResult)
	{
		if (!isUnbound())
			throw new RuntimeException("attempt to bind value to bound argument " + this.toString());

		setBound();

		this.builtInResult = builtInResult;
		this.builtInResult.setVariableName(getVariableName());
	}

	@Override
	public SWRLBuiltInArgument getBuiltInResult()
	{
		// TODO this should probably throw TargetRuleEngineExceptiion
		// if (!isUnbound()) throw new BuiltInException("attempt to retrieve binding from a non bound argument " +
		// this.toString());

		return this.builtInResult;
	}

	@Override
	public String getVariableName()
	{
		return this.variableName;
	}

	@Override
	public boolean hasBuiltInResult()
	{
		return this.builtInResult != null;
	}

	@Override
	public boolean isUnbound()
	{
		return !this.isBound;
	}

	@Override
	public boolean isBound()
	{
		return this.isBound;
	}

	@Override
	public void setUnbound()
	{
		this.isBound = false;
	}

	@Override
	public void setBound()
	{
		this.isBound = true;
	}

	@Override
	public String toDisplayText()
	{
		if (this.builtInResult != null)
			return this.builtInResult.toString();
		else
			return "?" + getVariableName();
	}

	@Override
	public String toString()
	{
		return toDisplayText();
	}

	@Override
	public int compareTo(SWRLBuiltInArgument o)
	{
		return getVariableName().compareTo(o.getVariableName());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SWRLBuiltInArgumentImpl impl = (SWRLBuiltInArgumentImpl)obj;
		return super.equals(impl)
				&& ((this.builtInResult == impl.builtInResult) || (this.builtInResult != null && this.builtInResult
						.equals(impl.builtInResult))
						&& this.isBound == impl.isBound
						&& (this.variableName != null && this.variableName.equals(impl.getVariableName())));
	}

	@Override
	public int hashCode()
	{
		int hash = 78;
		hash = hash + (null == this.builtInResult ? 0 : this.builtInResult.hashCode());
		hash = hash + (null == this.variableName ? 0 : this.variableName.hashCode());
		hash = hash + (this.isBound ? 1 : 0);
		return hash;
	}
}
