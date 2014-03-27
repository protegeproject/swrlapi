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
import org.swrlapi.core.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;

public abstract class SWRLBuiltInArgumentImpl implements SWRLBuiltInArgument
{
	private static final long serialVersionUID = 1L;

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
	public void accept(SWRLObjectVisitor visitor)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <O> O accept(SWRLObjectVisitorEx<O> visitor)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<OWLEntity> getSignature()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<OWLAnonymousIndividual> getAnonymousIndividuals()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<OWLClass> getClassesInSignature()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<OWLDataProperty> getDataPropertiesInSignature()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<OWLObjectProperty> getObjectPropertiesInSignature()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<OWLNamedIndividual> getIndividualsInSignature()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<OWLDatatype> getDatatypesInSignature()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<OWLClassExpression> getNestedClassExpressions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(OWLObjectVisitor visitor)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <O> O accept(OWLObjectVisitorEx<O> visitor)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTopEntity()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBottomEntity()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int compareTo(OWLObject o)
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
