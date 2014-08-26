package org.swrlapi.core.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
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
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.core.visitors.SWRLAPIEntityVisitorEx;

public class DefaultSWRLAPIRulePrinter implements SWRLAPIEntityVisitorEx<String>
{
	private final DefaultPrefixManager prefixManager;

	public DefaultSWRLAPIRulePrinter(DefaultPrefixManager prefixManager)
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
		IRI iri = builtInAtom.getPredicate();
		String builtInPrefixedName = prefixManager.getPrefixIRI(iri);
		StringBuilder sb = new StringBuilder();

		sb.append(builtInPrefixedName + "(");

		boolean isFirst = true;
		for (SWRLDArgument argument : builtInAtom.getArguments()) {
			if (!isFirst)
				sb.append(", ");
			sb.append(argument.accept(this));
			isFirst = false;
		}
		sb.append(")");

		return sb.toString();
	}

	@Override
	public String visit(SWRLAPIBuiltInAtom swrlapiBuiltInAtom)
	{
		String builtInPrefixedName = swrlapiBuiltInAtom.getBuiltInPrefixedName();
		StringBuilder sb = new StringBuilder(builtInPrefixedName + "(");
		boolean isFirst = true;

		for (SWRLBuiltInArgument argument : swrlapiBuiltInAtom.getBuiltInArguments()) {
			if (!isFirst)
				sb.append(", ");
			// TODO Look at. accept() in SWRLBuiltInArgument and SWRLObject could apply
			sb.append(argument.accept((SWRLBuiltInArgumentVisitorEx<String>)this));
			isFirst = false;
		}
		sb.append(")");

		return sb.toString();
	}

	@Override
	public String visit(SWRLVariable variable)
	{
		String variablePrefixedName = prefixManager.getPrefixIRI(variable.getIRI());

		return variablePrefixedName2VariableName(variablePrefixedName);
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

		return visit(literal);
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
			sb.append("[Unknown " + SWRLIArgument.class.getName() + " type " + argument.getClass().getName() + "]");

		return sb.toString();
	}

	private String visit(SWRLDArgument argument)
	{
		StringBuilder sb = new StringBuilder();

		if (argument instanceof SWRLBuiltInArgument) {
			SWRLBuiltInArgument builtInArgument = (SWRLBuiltInArgument)argument;
			// TODO Look at. accept() in SWRLBuiltInArgument and SWRLObject could apply
			sb.append(builtInArgument.accept((SWRLBuiltInArgumentVisitorEx<String>)this));
		} else if (argument instanceof SWRLLiteralArgument) {
			SWRLLiteralArgument literalArgument = (SWRLLiteralArgument)argument;
			sb.append(literalArgument.accept(this));
		} else if (argument instanceof SWRLVariable) {
			SWRLVariable variableArgument = (SWRLVariable)argument;
			sb.append(variableArgument.accept(this));
		} else
			sb.append("[Unknown " + SWRLDArgument.class.getName() + " type " + argument.getClass().getName() + "]");

		return sb.toString();
	}

	private String visit(OWLClassExpression classExpression)
	{
		if (classExpression.isAnonymous())
			return classExpression.toString(); // TODO See if we can get an OWLAPI renderer
		else {
			OWLClass cls = classExpression.asOWLClass();
			return visit(cls);
		}
	}

	private String visit(OWLClass cls)
	{
		return prefixManager.getPrefixIRI(cls.getIRI());
	}

	private String visit(OWLIndividual individual)
	{
		if (individual.isNamed())
			return prefixManager.getPrefixIRI(individual.asOWLNamedIndividual().getIRI());
		else
			return individual.toString(); // TODO See if we can get an OWLAPI renderer
	}

	private String visit(OWLObjectPropertyExpression objectPropertyExpression)
	{
		if (objectPropertyExpression.isAnonymous())
			return objectPropertyExpression.toString(); // TODO See if we can get an OWLAPI renderer
		else {
			OWLObjectProperty property = objectPropertyExpression.asOWLObjectProperty();
			return visit(property);
		}
	}

	private String visit(OWLObjectProperty property)
	{
		return prefixManager.getPrefixIRI(property.getIRI());
	}

	private String visit(OWLDataPropertyExpression dataPropertyExpression)
	{
		if (dataPropertyExpression.isAnonymous())
			return dataPropertyExpression.toString(); // TODO See if we can get an OWLAPI renderer
		else {
			OWLDataProperty property = dataPropertyExpression.asOWLDataProperty();
			return visit(property);
		}
	}

	private String visit(OWLDataProperty property)
	{
		return prefixManager.getPrefixIRI(property.getIRI());
	}

	private String visit(OWLDataRange dataRange)
	{
		if (dataRange.isDatatype()) {
			OWLDatatype datatype = dataRange.asOWLDatatype();
			return prefixManager.getPrefixIRI(datatype.getIRI());
		} else
			return dataRange.toString(); // Use the OWLAPI's rendering
	}

	@Override
	public String visit(SWRLClassBuiltInArgument argument)
	{
		OWLClass cls = argument.getOWLClass();
		return prefixManager.getPrefixIRI(cls.getIRI());
	}

	@Override
	public String visit(SWRLNamedIndividualBuiltInArgument argument)
	{
		OWLNamedIndividual individual = argument.getOWLNamedIndividual();
		return prefixManager.getPrefixIRI(individual.getIRI());
	}

	@Override
	public String visit(SWRLObjectPropertyBuiltInArgument argument)
	{
		OWLObjectProperty property = argument.getOWLObjectProperty();
		return prefixManager.getPrefixIRI(property.getIRI());
	}

	@Override
	public String visit(SWRLDataPropertyBuiltInArgument argument)
	{
		OWLDataProperty property = argument.getOWLDataProperty();
		return prefixManager.getPrefixIRI(property.getIRI());
	}

	@Override
	public String visit(SWRLAnnotationPropertyBuiltInArgument argument)
	{
		OWLAnnotationProperty property = argument.getOWLAnnotationProperty();
		return prefixManager.getPrefixIRI(property.getIRI());
	}

	@Override
	public String visit(SWRLDatatypeBuiltInArgument argument)
	{
		OWLDatatype datatype = argument.getOWLDatatype();
		return prefixManager.getPrefixIRI(datatype.getIRI());
	}

	@Override
	public String visit(SWRLLiteralBuiltInArgument argument)
	{
		OWLLiteral literal = argument.getLiteral();
		return visit(literal);
	}

	@Override
	public String visit(SWRLVariableBuiltInArgument argument)
	{
		String variablePrefixedName = argument.getVariablePrefixedName();

		return variablePrefixedName2VariableName(variablePrefixedName);
	}

	@Override
	public String visit(SWRLMultiValueVariableBuiltInArgument argument)
	{
		String variablePrefixedName = argument.getVariablePrefixedName();

		return variablePrefixedName2VariableName(variablePrefixedName);
	}

	@Override
	public String visit(SQWRLCollectionVariableBuiltInArgument argument)
	{
		String variablePrefixedName = argument.getVariablePrefixedName();

		return variablePrefixedName2VariableName(variablePrefixedName);
	}

	private String visit(OWLLiteral literal)
	{
		OWLDatatype datatype = literal.getDatatype();
		String value = literal.getLiteral();

		return "\"" + value + "\"^^" + visit(datatype);
	}

	private String variablePrefixedName2VariableName(String variablePrefixedName)
	{
		if (variablePrefixedName.startsWith(":"))
			return "?" + variablePrefixedName.substring(1);
		else
			return "?" + variablePrefixedName;
	}
}
