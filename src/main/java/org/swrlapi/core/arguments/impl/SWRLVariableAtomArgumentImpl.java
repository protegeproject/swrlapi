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
import org.swrlapi.core.arguments.SWRLVariableAtomArgument;

class SWRLVariableAtomArgumentImpl extends SWRLAtomArgumentImpl implements SWRLVariableAtomArgument
{
	private static final long serialVersionUID = 7541492844317921693L;

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
	public String toString()
	{
		return toDisplayText();
	}

	@Override
	public String toDisplayText()
	{
		return "?" + this.variableName;
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
