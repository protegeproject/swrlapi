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
import org.semanticweb.owlapi.model.OWLOntology;
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
import org.semanticweb.owlapi.vocab.XSDVocabulary;
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
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRuleRenderer;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLNames;

/**
 * Default implementation of a renderer for {@link org.swrlapi.core.SWRLAPIRule} and
 * {@link org.swrlapi.sqwrl.SQWRLQuery} objects.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see org.swrlapi.core.SWRLAPIFactory
 */
public class DefaultSWRLAPIRuleRenderer implements SWRLAPIRuleRenderer
{
	private final OWLOntology ontology;
	private final DefaultPrefixManager prefixManager;

	public DefaultSWRLAPIRuleRenderer(SWRLAPIOWLOntology swrlapiowlOntology)
	{
		this.ontology = swrlapiowlOntology.getOWLOntology();
		this.prefixManager = swrlapiowlOntology.getPrefixManager();
	}

	@Override
	public String render(SWRLRule swrlapiRule)
	{
		StringBuilder sb = new StringBuilder();
		boolean collectionMakeEncountered = false;
		boolean collectionOperationEncountered = false;
		boolean isFirst = true;

		for (SWRLAtom atom : swrlapiRule.getBody()) {
			if (isSQWRLCollectionMakeBuiltInAtom(atom)) {
				if (collectionMakeEncountered)
					sb.append(" " + SWRLParser.AND_CHAR + " ");
				else {
					sb.append(SWRLParser.RING_CHAR);
					collectionMakeEncountered = true;
				}
			} else if (isSQWRLCollectionOperateBuiltInAtom(atom)) {
				if (collectionOperationEncountered)
					sb.append(" " + SWRLParser.AND_CHAR + " ");
				else {
					sb.append(SWRLParser.RING_CHAR);
					collectionOperationEncountered = true;
				}
			} else {
				if (!isFirst)
					sb.append(" " + SWRLParser.AND_CHAR + " ");
			}
			sb.append(atom.accept(this));
			isFirst = false;
		}

		sb.append(" " + SWRLParser.IMP_CHAR + " ");

		isFirst = true;
		for (SWRLAtom atom : swrlapiRule.getHead()) {
			if (!isFirst)
				sb.append(" " + SWRLParser.AND_CHAR + " ");
			sb.append(atom.accept(this));
			isFirst = false;
		}
		return sb.toString();
	}

	@Override
	public String visit(SWRLRule swrlRule)
	{
		return render(swrlRule);
	}

	@Override
	public String visit(SWRLClassAtom classAtom)
	{
		OWLClassExpression classExpression = classAtom.getPredicate();
		SWRLIArgument iArgument = classAtom.getArgument();
		StringBuilder sb = new StringBuilder();

		sb.append(visit(classExpression));

		sb.append("(" + visit(iArgument) + ")");

		return sb.toString();
	}

	@Override
	public String visit(SWRLDataRangeAtom dataRangeAtom)
	{
		OWLDataRange dataRange = dataRangeAtom.getPredicate();
		SWRLDArgument dArgument = dataRangeAtom.getArgument();
		StringBuilder sb = new StringBuilder();

		sb.append(visit(dataRange));

		sb.append("(" + visit(dArgument) + ")");

		return sb.toString();
	}

	@Override
	public String visit(SWRLObjectPropertyAtom objectPropertyAtom)
	{
		OWLObjectPropertyExpression objectPropertyExpression = objectPropertyAtom.getPredicate();
		SWRLIArgument iArgument1 = objectPropertyAtom.getFirstArgument();
		SWRLIArgument iArgument2 = objectPropertyAtom.getSecondArgument();
		StringBuilder sb = new StringBuilder();

		sb.append(visit(objectPropertyExpression));

		sb.append("(" + visit(iArgument1) + ", " + visit(iArgument2) + ")");

		return sb.toString();
	}

	@Override
	public String visit(SWRLDataPropertyAtom dataPropertyAtom)
	{
		OWLDataPropertyExpression dataPropertyExpression = dataPropertyAtom.getPredicate();
		SWRLIArgument iArgument1 = dataPropertyAtom.getFirstArgument();
		SWRLDArgument dArgument2 = dataPropertyAtom.getSecondArgument();
		StringBuilder sb = new StringBuilder();

		sb.append(visit(dataPropertyExpression));

		sb.append("(" + visit(iArgument1) + ", " + visit(dArgument2) + ")");

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
			// TODO Look at to get rid of instanceof. accept() in SWRLBuiltInArgument and SWRLObject could apply
			sb.append(argument.accept((SWRLBuiltInArgumentVisitorEx<String>)this));
			isFirst = false;
		}
		sb.append(")");

		return sb.toString();
	}

	@Override
	public String visit(SWRLVariable variable)
	{
		IRI variableIRI = variable.getIRI();

		if (this.ontology.containsEntityInSignature(variableIRI, true)) {
			String shortForm = this.prefixManager.getShortForm(variableIRI);
			return shortForm.startsWith(":") ? shortForm.substring(1) : shortForm;
		} else {
			String variablePrefixedName = prefixManager.getPrefixIRI(variableIRI);
			return variablePrefixedName2VariableName(variablePrefixedName);
		}
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
		SWRLIArgument iArgument1 = sameIndividualAtom.getFirstArgument();
		SWRLIArgument iArgument2 = sameIndividualAtom.getSecondArgument();
		StringBuilder sb = new StringBuilder();

		sb.append("sameAs");

		sb.append("(" + visit(iArgument1) + ", " + visit(iArgument2) + ")");

		return sb.toString();
	}

	@Override
	public String visit(SWRLDifferentIndividualsAtom differentIndividualsAtom)
	{
		SWRLIArgument iArgument1 = differentIndividualsAtom.getFirstArgument();
		SWRLIArgument iArgument2 = differentIndividualsAtom.getSecondArgument();
		StringBuilder sb = new StringBuilder();

		sb.append("differentFrom");

		sb.append("(" + visit(iArgument1) + ", " + visit(iArgument2) + ")");

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
			// TODO Look at to get rid of instanceof. accept() in SWRLBuiltInArgument and SWRLObject could apply
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
			return classExpression.toString(); // TODO Use an OWLAPI renderer
		else {
			OWLClass cls = classExpression.asOWLClass();
			return visit(cls);
		}
	}

	private String visit(OWLClass cls)
	{
		String classNameShortForm = this.prefixManager.getShortForm(cls.getIRI());

		return classNameShortForm.startsWith(":") ? classNameShortForm.substring(1) : classNameShortForm;
	}

	private String visit(OWLIndividual individual)
	{
		if (individual.isNamed()) {
			String individualNameShortForm = this.prefixManager.getShortForm(individual.asOWLNamedIndividual().getIRI());

			return individualNameShortForm.startsWith(":") ? individualNameShortForm.substring(1) : individualNameShortForm;
		} else
			return individual.toString(); // TODO Use an OWLAPI renderer
	}

	private String visit(OWLObjectPropertyExpression objectPropertyExpression)
	{
		if (objectPropertyExpression.isAnonymous())
			return objectPropertyExpression.toString(); // TODO Use an OWLAPI renderer
		else
			return visit(objectPropertyExpression.asOWLObjectProperty());
	}

	private String visit(OWLObjectProperty property)
	{
		String objectPropertyNameShortForm = prefixManager.getPrefixIRI(property.getIRI());

		return objectPropertyNameShortForm.startsWith(":") ?
				objectPropertyNameShortForm.substring(1) :
				objectPropertyNameShortForm;
	}

	private String visit(OWLDataPropertyExpression dataPropertyExpression)
	{
		if (dataPropertyExpression.isAnonymous())
			return dataPropertyExpression.toString(); // TODO Use an OWLAPI renderer
		else
			return visit(dataPropertyExpression.asOWLDataProperty());
	}

	private String visit(OWLDataProperty property)
	{
		String dataPropertyNameShortForm = prefixManager.getPrefixIRI(property.getIRI());

		return dataPropertyNameShortForm.startsWith(":") ?
				dataPropertyNameShortForm.substring(1) :
				dataPropertyNameShortForm;
	}

	private String visit(OWLDataRange dataRange)
	{
		if (dataRange.isDatatype()) {
			OWLDatatype datatype = dataRange.asOWLDatatype();
			return this.prefixManager.getShortForm(datatype.getIRI());
		} else
			return dataRange.toString(); // Use the OWLAPI's rendering
	}

	@Override
	public String visit(SWRLClassBuiltInArgument argument)
	{
		OWLClass cls = argument.getOWLClass();
		String classNameShortForm = this.prefixManager.getShortForm(cls.getIRI());

		return classNameShortForm.startsWith(":") ? classNameShortForm.substring(1) : classNameShortForm;
	}

	@Override
	public String visit(SWRLNamedIndividualBuiltInArgument argument)
	{
		OWLNamedIndividual individual = argument.getOWLNamedIndividual();
		String individualNameShortForm = this.prefixManager.getShortForm(individual.getIRI());

		return individualNameShortForm.startsWith(":") ? individualNameShortForm.substring(1) : individualNameShortForm;
	}

	@Override
	public String visit(SWRLObjectPropertyBuiltInArgument argument)
	{
		OWLObjectProperty property = argument.getOWLObjectProperty();
		String objectPropertyNameShortForm = this.prefixManager.getShortForm(property.getIRI());

		return objectPropertyNameShortForm.startsWith(":") ?
				objectPropertyNameShortForm.substring(1) :
				objectPropertyNameShortForm;
	}

	@Override
	public String visit(SWRLDataPropertyBuiltInArgument argument)
	{
		OWLDataProperty property = argument.getOWLDataProperty();
		String dataPropertyNameShortForm = this.prefixManager.getShortForm(property.getIRI());

		return dataPropertyNameShortForm.startsWith(":") ?
				dataPropertyNameShortForm.substring(1) :
				dataPropertyNameShortForm;
	}

	@Override
	public String visit(SWRLAnnotationPropertyBuiltInArgument argument)
	{
		OWLAnnotationProperty property = argument.getOWLAnnotationProperty();
		String annotationPropertyNameShortForm = this.prefixManager.getShortForm(property.getIRI());

		return annotationPropertyNameShortForm.startsWith(":") ?
				annotationPropertyNameShortForm.substring(1) :
				annotationPropertyNameShortForm;
	}

	@Override
	public String visit(SWRLDatatypeBuiltInArgument argument)
	{
		OWLDatatype datatype = argument.getOWLDatatype();
		return this.prefixManager.getShortForm(datatype.getIRI());
	}

	@Override
	public String visit(SWRLLiteralBuiltInArgument argument)
	{
		return visit(argument.getLiteral());
	}

	@Override
	public String visit(SWRLVariableBuiltInArgument argument)
	{
		IRI variableIRI = argument.getIRI();

		if (this.ontology.containsEntityInSignature(variableIRI, true)) {
			String shortForm = this.prefixManager.getShortForm(variableIRI);
			return shortForm.startsWith(":") ? shortForm.substring(1) : shortForm;
		} else {
			String variablePrefixedName = prefixManager.getPrefixIRI(variableIRI);
			return variablePrefixedName2VariableName(variablePrefixedName);
		}
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

		if (datatype.isString())
			return "\"" + value + "\"";
		else if (datatype.isFloat())
			return value;
		else if (datatype.isBoolean())
			return value;
		else if (datatype.getIRI().equals(XSDVocabulary.INT.getIRI()))
			return value;
		else
			return "\"" + value + "\"^^\"" + visit(datatype) + "\"";
	}

	private String variablePrefixedName2VariableName(String variablePrefixedName)
	{
		if (variablePrefixedName.startsWith(":"))
			return "?" + variablePrefixedName.substring(1);
		else
			return "?" + variablePrefixedName;
	}

	private boolean isSQWRLCollectionMakeBuiltInAtom(SWRLAtom atom)
	{
		return atom instanceof SWRLAPIBuiltInAtom && SQWRLNames
				.isSQWRLCollectionMakeBuiltIn(((SWRLAPIBuiltInAtom)atom).getBuiltInPrefixedName());
	}

	private boolean isSQWRLCollectionOperateBuiltInAtom(SWRLAtom atom)
	{
		return atom instanceof SWRLAPIBuiltInAtom && SQWRLNames
				.isSQWRLCollectionOperationBuiltIn(((SWRLAPIBuiltInAtom)atom).getBuiltInPrefixedName());
	}
}
