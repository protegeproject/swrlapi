package org.protege.swrlapi.core.arguments.impl;

import java.util.Set;

import org.protege.swrlapi.core.arguments.SWRLLiteralAtomArgument;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;

class SWRLLiteralAtomArgumentImpl extends SWRLAtomArgumentImpl implements SWRLLiteralAtomArgument
{
	private static final long serialVersionUID = 2564969515366239679L;

	private final OWLLiteral literal;

	public SWRLLiteralAtomArgumentImpl(OWLLiteral literal)
	{
		this.literal = literal;
	}

	@Override
	public OWLLiteral getLiteral()
	{
		return this.literal;
	}

	@Override
	public String toDisplayText()
	{
		return this.literal.toString();
	}

	@Override
	public String toString()
	{
		return toDisplayText();
	}

	@Override
	public String toDisplayText()
	{
		throw new RuntimeException("Not implemented");
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
		SWRLLiteralAtomArgumentImpl impl = (SWRLLiteralAtomArgumentImpl)obj;
		return (getLiteral() == impl.getLiteral() || (getLiteral() != null && getLiteral().equals(impl.getLiteral())));
	}

	@Override
	public int hashCode()
	{
		int hash = 123;
		hash = hash + (null == getLiteral() ? 0 : getLiteral().hashCode());
		return hash;
	}
}
