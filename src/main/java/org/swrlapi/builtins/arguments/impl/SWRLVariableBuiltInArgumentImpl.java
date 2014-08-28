package org.swrlapi.builtins.arguments.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.swrlapi.builtins.arguments.*;
import org.swrlapi.exceptions.SWRLBuiltInException;

class SWRLVariableBuiltInArgumentImpl extends SWRLBuiltInArgumentImpl implements SWRLVariableBuiltInArgument
{
	// There is an equals methods defined for this class.
	private static final long serialVersionUID = 1L;

	private final IRI iri;
	private final String variablePrefixedName;

	private SWRLBuiltInArgument builtInResult; // Used to store result of binding for unbound arguments
	boolean isBound;

	public SWRLVariableBuiltInArgumentImpl(IRI iri, String variablePrefixedName)
	{
		this.iri = iri;
		this.variablePrefixedName = variablePrefixedName;
		this.builtInResult = null;
		this.isBound = true;
	}

	@Override
	public IRI getIRI()
	{
		return this.iri;
	}

	@Override
	public boolean isVariable()
	{
		return true;
	}

	@Override
	public boolean isMultiValueVariable()
	{
		return false;
	}

	@Override
	public SWRLVariableBuiltInArgument asVariable()
	{
		return this;
	}

	@Override
	public SWRLMultiValueVariableBuiltInArgument asMultiValueVariable()
	{
		throw new SWRLBuiltInException("not a SWRLMultiVariableBuiltInArgument");
	}

	@Override
	public String getVariablePrefixedName()
	{
		return this.variablePrefixedName;
	}

	@Override
	public void setBuiltInResult(SWRLBuiltInArgument builtInResult)
	{
		if (!isUnbound())
			throw new SWRLBuiltInException("attempt to bind value to bound argument " + this.toString());

		setBound();

		this.builtInResult = builtInResult;
	}

	@Override
	public SWRLBuiltInArgument getBuiltInResult()
	{
		return this.builtInResult;
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
	public <T> T accept(SWRLBuiltInArgumentVisitorEx<T> visitor)
	{
		return visitor.visit(this);
	}

	@Override
	public void accept(SWRLBuiltInArgumentVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public void accept(SWRLObjectVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public <O> O accept(SWRLObjectVisitorEx<O> visitor)
	{
		return visitor.visit(this);
	}

	@Override
	public void accept(OWLObjectVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public <O> O accept(OWLObjectVisitorEx<O> visitor)
	{
		return visitor.visit(this);
	}

	private int compareTo(SWRLVariableBuiltInArgument o)
	{
		return this.getIRI().compareTo(o.getIRI());
	}

	@Override
	public int compareTo(OWLObject o)
	{
		if (!(o instanceof SWRLVariableBuiltInArgument))
			return -1;

		SWRLVariableBuiltInArgument other = (SWRLVariableBuiltInArgument)o;

		return compareTo(other);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		SWRLVariableBuiltInArgumentImpl impl = (SWRLVariableBuiltInArgumentImpl)obj;
		return super.equals(impl)
				&& ((this.builtInResult == impl.builtInResult) || (this.builtInResult != null && this.builtInResult
				.equals(impl.builtInResult)) && this.isBound == impl.isBound);
	}

	@Override
	public int hashCode()
	{
		int hash = 78;
		hash = hash + (null == this.builtInResult ? 0 : this.builtInResult.hashCode());
		hash = hash + (this.isBound ? 1 : 0);
		return hash;
	}
}
