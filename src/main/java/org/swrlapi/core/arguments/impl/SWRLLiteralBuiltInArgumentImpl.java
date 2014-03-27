package org.swrlapi.core.arguments.impl;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;

import uk.ac.manchester.cs.owl.owlapi.SWRLLiteralArgumentImpl;

class SWRLLiteralBuiltInArgumentImpl extends SWRLLiteralArgumentImpl implements SWRLLiteralBuiltInArgument
{
	private static final long serialVersionUID = 1L;

	public SWRLLiteralBuiltInArgumentImpl(OWLLiteral literal)
	{
		super(literal);
	}

	@Override
	public boolean isVariable()
	{
		return false;
	}

	@Override
	public boolean isMultiValueVariable()
	{
		return false;
	}

	@Override
	public SWRLVariableBuiltInArgument asVariable()
	{
		throw new RuntimeException("Not a SWRLVariableBuiltInArgument");
	}

	@Override
	public SWRLMultiValueVariableBuiltInArgument asMultiValueVariable()
	{
		throw new RuntimeException("Not a SWRLMultiVariableBuiltInArgument");
	}

	@Override
	public String toDisplayText()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public int hashCode()
	{
		int hash = 12;
		hash = hash + (null == getLiteral() ? 0 : getLiteral().hashCode());
		return hash;
	}
}
