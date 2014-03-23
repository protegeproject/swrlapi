package org.swrlapi.core.arguments.impl;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;

abstract class SWRLBuiltInArgumentImpl implements SWRLBuiltInArgument
{
	private static final long serialVersionUID = 1L;

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
	public String toString()
	{
		return toDisplayText();
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
	public void accept(SWRLObjectVisitor visitor)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public <O> O accept(SWRLObjectVisitorEx<O> visitor)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLEntity> getSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLAnonymousIndividual> getAnonymousIndividuals()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLClass> getClassesInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDataProperty> getDataPropertiesInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLObjectProperty> getObjectPropertiesInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLNamedIndividual> getIndividualsInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDatatype> getDatatypesInSignature()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLClassExpression> getNestedClassExpressions()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void accept(OWLObjectVisitor visitor)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public <O> O accept(OWLObjectVisitorEx<O> visitor)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean isTopEntity()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean isBottomEntity()
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public int compareTo(OWLObject o)
	{
		throw new RuntimeException("Not implemented");
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
