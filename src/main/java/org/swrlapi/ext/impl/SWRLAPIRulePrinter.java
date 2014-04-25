package org.swrlapi.ext.impl;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;

public class SWRLAPIRulePrinter implements SWRLObjectVisitorEx<String>
{
	private final DefaultPrefixManager prefixManager;

	public SWRLAPIRulePrinter(DefaultPrefixManager prefixManager)
	{
		this.prefixManager = prefixManager;
	}

	@Override
	public String visit(SWRLRule node)
	{
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;

		for (SWRLAtom atom : node.getBody()) {
			if (!isFirst)
				sb.append(" ^ ");
			sb.append(atom.accept(this));
			isFirst = false;
		}

		sb.append(" -> ");

		isFirst = true;
		for (SWRLAtom atom : node.getHead()) {
			if (!isFirst)
				sb.append(" ^ ");
			sb.append(atom.accept(this));
			isFirst = false;
		}
		return sb.toString();
	}

	@Override
	public String visit(SWRLClassAtom classAtom)
	{
		OWLClassExpression classExpression = classAtom.getPredicate();
		SWRLIArgument argument = classAtom.getArgument();
		StringBuilder sb = new StringBuilder();

		sb.append(visit(classExpression));

		sb.append("(" + visit(argument) + ")");

		return sb.toString();
	}

	@Override
	public String visit(SWRLDataRangeAtom dataRangeAtom)
	{
		OWLDataRange dataRange = dataRangeAtom.getPredicate();
		SWRLDArgument argument = dataRangeAtom.getArgument();
		StringBuilder sb = new StringBuilder();

		sb.append(visit(dataRange));

		sb.append("(" + visit(argument) + ")");

		return sb.toString();
	}

	@Override
	public String visit(SWRLObjectPropertyAtom objectPropertyAtom)
	{
		OWLObjectPropertyExpression objectPropertyExpression = objectPropertyAtom.getPredicate();
		SWRLIArgument argument1 = objectPropertyAtom.getFirstArgument();
		SWRLIArgument argument2 = objectPropertyAtom.getSecondArgument();
		StringBuilder sb = new StringBuilder();

		sb.append(visit(objectPropertyExpression));

		sb.append("(" + visit(argument1) + ", " + visit(argument2) + ")");

		return sb.toString();
	}

	@Override
	public String visit(SWRLDataPropertyAtom dataPropertyAtom)
	{
		OWLDataPropertyExpression dataPropertyExpression = dataPropertyAtom.getPredicate();
		SWRLIArgument argument1 = dataPropertyAtom.getFirstArgument();
		SWRLDArgument argument2 = dataPropertyAtom.getSecondArgument();
		StringBuilder sb = new StringBuilder();

		sb.append(visit(dataPropertyExpression));

		sb.append("(" + visit(argument1) + ", " + visit(argument2) + ")");

		return sb.toString();
	}

	@Override
	public String visit(SWRLBuiltInAtom builtInAtom)
	{
		return builtInAtom.toString();
	}

	@Override
	public String visit(SWRLVariable variable)
	{
		String variableShortName = prefixManager.getShortForm(variable.getIRI());

		if (variableShortName.startsWith(":"))
			return "?" + variableShortName.substring(1);
		else
			return "?" + variableShortName;
	}

	@Override
	public String visit(SWRLIndividualArgument individualArgument)
	{
		return visit(individualArgument.getIndividual());
	}

	@Override
	public String visit(SWRLLiteralArgument literalArgument)
	{
		OWLLiteral literal = literalArgument.getLiteral();
		OWLDatatype datatype = literal.getDatatype();
		String value = literal.getLiteral();

		return "\"" + value + "\"^^" + visit(datatype);
	}

	@Override
	public String visit(SWRLSameIndividualAtom sameIndividualAtom)
	{
		SWRLIArgument argument1 = sameIndividualAtom.getFirstArgument();
		SWRLIArgument argument2 = sameIndividualAtom.getSecondArgument();
		StringBuilder sb = new StringBuilder();

		sb.append("sameAs");

		sb.append("(" + visit(argument1) + ", " + visit(argument2) + ")");

		return sb.toString();
	}

	@Override
	public String visit(SWRLDifferentIndividualsAtom differentIndividualsAtom)
	{
		SWRLIArgument argument1 = differentIndividualsAtom.getFirstArgument();
		SWRLIArgument argument2 = differentIndividualsAtom.getSecondArgument();
		StringBuilder sb = new StringBuilder();

		sb.append("differentFrom");

		sb.append("(" + visit(argument1) + ", " + visit(argument2) + ")");

		return sb.toString();
	}

	private String visit(SWRLIArgument argument)
	{
		StringBuilder sb = new StringBuilder();

		if (argument instanceof SWRLIndividualArgument) {
			SWRLIndividualArgument individualArgument = (SWRLIndividualArgument)argument;
			sb.append(individualArgument.accept(this));
		} else if (argument instanceof SWRLVariable) {
			SWRLVariable variableArgument = (SWRLVariable)argument;
			sb.append(variableArgument.accept(this));
		} else
			sb.append("<Unknown " + SWRLIArgument.class.getName() + " type " + argument.getClass().getName() + ">");

		return sb.toString();
	}

	private String visit(SWRLDArgument argument)
	{
		StringBuilder sb = new StringBuilder();

		if (argument instanceof SWRLBuiltInArgument) {
			SWRLBuiltInArgument builtInArgument = (SWRLBuiltInArgument)argument;
			sb.append(builtInArgument.toDisplayText());
		} else if (argument instanceof SWRLLiteralArgument) {
			SWRLLiteralArgument literalArgument = (SWRLLiteralArgument)argument;
			sb.append(literalArgument.accept(this));
		} else if (argument instanceof SWRLVariable) {
			SWRLVariable variableArgument = (SWRLVariable)argument;
			sb.append(variableArgument.accept(this));
		} else
			sb.append("<Unknown " + SWRLDArgument.class.getName() + " type " + argument.getClass().getName() + ">");

		return sb.toString();
	}

	private String visit(OWLClassExpression classExpression)
	{
		if (classExpression.isAnonymous())
			return classExpression.toString();
		else {
			OWLClass cls = classExpression.asOWLClass();
			return visit(cls);
		}
	}

	private String visit(OWLClass cls)
	{
		return prefixManager.getShortForm(cls.getIRI());
	}

	private String visit(OWLIndividual individual)
	{
		if (individual.isNamed())
			return prefixManager.getShortForm(individual.asOWLNamedIndividual().getIRI());
		else
			return individual.toString();
	}

	private String visit(OWLObjectPropertyExpression objectPropertyExpression)
	{
		if (objectPropertyExpression.isAnonymous())
			return objectPropertyExpression.toString();
		else {
			OWLObjectProperty property = objectPropertyExpression.asOWLObjectProperty();
			return visit(property);
		}
	}

	private String visit(OWLObjectProperty property)
	{
		return prefixManager.getShortForm(property.getIRI());
	}

	private String visit(OWLDataPropertyExpression dataPropertyExpression)
	{
		if (dataPropertyExpression.isAnonymous())
			return dataPropertyExpression.toString();
		else {
			OWLDataProperty property = dataPropertyExpression.asOWLDataProperty();
			return visit(property);
		}
	}

	private String visit(OWLDataProperty property)
	{
		return prefixManager.getShortForm(property.getIRI());
	}

	private String visit(OWLDataRange dataRange)
	{
		if (dataRange.isDatatype()) {
			OWLDatatype datatype = dataRange.asOWLDatatype();
			return prefixManager.getShortForm(datatype.getIRI());
		} else
			return dataRange.toString();
	}
}
