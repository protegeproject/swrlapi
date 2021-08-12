package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
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
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLNames;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLQueryRenderer;

import java.util.Iterator;
import java.util.Optional;

/**
 * Default implementation of a renderer for {@link org.swrlapi.core.SWRLAPIRule} and
 * {@link org.swrlapi.sqwrl.SQWRLQuery} objects.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.sqwrl.SQWRLQuery
 * @see SWRLAPIFactory
 */
class DefaultSWRLRuleAndQueryRenderer implements SWRLRuleRenderer, SQWRLQueryRenderer
{
  @NonNull private final OWLOntology ontology;
  @NonNull private final IRIResolver iriResolver;

  public DefaultSWRLRuleAndQueryRenderer(@NonNull OWLOntology ontology, @NonNull IRIResolver iriResolver)
  {
    this.ontology = ontology;
    this.iriResolver = iriResolver;
  }

  @NonNull @Override public String renderSWRLRule(@NonNull SWRLRule rule)
  {
    StringBuilder sb = new StringBuilder();

    sb.append(renderBodyAtoms(rule.getBody().iterator()));

    sb.append(" " + SWRLParser.IMP_COMBINATION + " ");

    sb.append(renderHeadAtoms(rule.getHead().iterator()));

    return sb.toString();
  }

  @NonNull @Override public String renderSQWRLQuery(@NonNull SQWRLQuery query)
  {
    StringBuilder sb = new StringBuilder();

    sb.append(renderBodyAtoms(query.getBodyAtoms().iterator()));

    sb.append(" " + SWRLParser.IMP_COMBINATION + " ");

    sb.append(renderHeadAtoms(query.getHeadAtoms().iterator()));

    return sb.toString();
  }

  @NonNull private StringBuilder renderBodyAtoms(@NonNull Iterator<@NonNull SWRLAtom> bodyAtomIterator)
  {
    StringBuilder sb = new StringBuilder();
    boolean collectionMakeEncountered = false;
    boolean collectionOperationEncountered = false;
    boolean isFirst = true;

    while (bodyAtomIterator.hasNext()) {
      SWRLAtom atom = bodyAtomIterator.next();
      if (isSQWRLCollectionMakeBuiltInAtom(atom)) {
        if (collectionMakeEncountered)
          sb.append(" " + SWRLParser.CONJUNCTION_CHAR + " ");
        else {
          sb.append(" " + SWRLParser.RING_CHAR + " ");
          collectionMakeEncountered = true;
        }
      } else if (isSQWRLCollectionOperateBuiltInAtom(atom)) {
        if (collectionOperationEncountered)
          sb.append(" " + SWRLParser.CONJUNCTION_CHAR + " ");
        else {
          sb.append(" " + SWRLParser.RING_CHAR + " ");
          collectionOperationEncountered = true;
        }
      } else {
        if (!isFirst)
          sb.append(" " + SWRLParser.CONJUNCTION_CHAR + " ");
      }
      sb.append(atom.accept(this));
      isFirst = false;
    }
    return sb;
  }

  @NonNull private StringBuilder renderHeadAtoms(@NonNull Iterator<@NonNull SWRLAtom> headAtomIterator)
  {
    StringBuilder sb = new StringBuilder();
    boolean isFirst = true;

    while (headAtomIterator.hasNext()) {
      SWRLAtom atom = headAtomIterator.next();
      if (!isFirst)
        sb.append(" " + SWRLParser.CONJUNCTION_CHAR + " ");
      sb.append(atom.accept(this));
      isFirst = false;
    }
    return sb;
  }

  @NonNull @Override public String visit(@NonNull SWRLRule swrlRule)
  {
    return renderSWRLRule(swrlRule);
  }

  @NonNull @Override public String visit(@NonNull SWRLClassAtom classAtom)
  {
    OWLClassExpression classExpression = classAtom.getPredicate();
    SWRLIArgument iArgument = classAtom.getArgument();
    StringBuilder sb = new StringBuilder();

    sb.append(visit(classExpression));

    sb.append("(").append(visit(iArgument)).append(")");

    return sb.toString();
  }

  @NonNull @Override public String visit(@NonNull SWRLDataRangeAtom dataRangeAtom)
  {
    OWLDataRange dataRange = dataRangeAtom.getPredicate();
    SWRLDArgument dArgument = dataRangeAtom.getArgument();
    StringBuilder sb = new StringBuilder();

    sb.append(visit(dataRange));

    sb.append("(").append(visit(dArgument)).append(")");

    return sb.toString();
  }

  @NonNull @Override public String visit(@NonNull SWRLObjectPropertyAtom objectPropertyAtom)
  {
    OWLObjectPropertyExpression objectPropertyExpression = objectPropertyAtom.getPredicate();
    SWRLIArgument iArgument1 = objectPropertyAtom.getFirstArgument();
    SWRLIArgument iArgument2 = objectPropertyAtom.getSecondArgument();
    StringBuilder sb = new StringBuilder();

    sb.append(visit(objectPropertyExpression));

    sb.append("(").append(visit(iArgument1)).append(", ").append(visit(iArgument2)).append(")");

    return sb.toString();
  }

  @NonNull @Override public String visit(@NonNull SWRLDataPropertyAtom dataPropertyAtom)
  {
    OWLDataPropertyExpression dataPropertyExpression = dataPropertyAtom.getPredicate();
    SWRLIArgument iArgument1 = dataPropertyAtom.getFirstArgument();
    SWRLDArgument dArgument2 = dataPropertyAtom.getSecondArgument();
    StringBuilder sb = new StringBuilder();

    sb.append(visit(dataPropertyExpression));

    sb.append("(").append(visit(iArgument1)).append(", ").append(visit(dArgument2)).append(")");

    return sb.toString();
  }

  @NonNull @Override public String visit(@NonNull SWRLBuiltInAtom builtInAtom)
  {
    IRI iri = builtInAtom.getPredicate();
    String builtInPrefixedName = getPrefixedName(iri);
    StringBuilder sb = new StringBuilder();

    sb.append(builtInPrefixedName).append("(");

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

  @NonNull @Override public String visit(@NonNull SWRLAPIBuiltInAtom swrlapiBuiltInAtom)
  {
    String builtInPrefixedName = swrlapiBuiltInAtom.getBuiltInPrefixedName();
    StringBuilder sb = new StringBuilder(builtInPrefixedName + "(");
    boolean isFirst = true;

    for (SWRLBuiltInArgument argument : swrlapiBuiltInAtom.getBuiltInArguments()) {
      if (!isFirst)
        sb.append(", ");
      // TODO Look at getting rid of cast; accept() in SWRLBuiltInArgument and SWRLObject could apply
      sb.append(argument.accept((SWRLBuiltInArgumentVisitorEx<String>)this));
      isFirst = false;
    }
    sb.append(")");

    return sb.toString();
  }

  /**
   * The OWLAPI follows the OWL Specification and does not explicitly allow named OWL entities as arguments to
   * built-ins. However, if OWLAPI parsers encounter OWL entities as parameters they appear to represent them as SWRL
   * variables - with the variable IRI set to the IRI of the entity ({@link org.semanticweb.owlapi.model.OWLEntity}
   * classes represent named OWL concepts so have an IRI). So if we are processing built-in parameters and encounter
   * variables with an IRI referring to OWL entities in the active ontology we can transform them to the
   * appropriate short form for the named entity.
   *
   */
  @NonNull @Override public String visit(@NonNull SWRLVariable variable)
  {
    IRI argumentIRI = variable.getIRI();

    if (getOWLOntology().containsEntityInSignature(argumentIRI)) {
      String shortForm = getShortForm(argumentIRI);

      return shortForm.startsWith(":") ? shortForm.substring(1) : shortForm;
    } else {
      com.google.common.base.Optional<String> remainder = argumentIRI.getRemainder();

      if (remainder.isPresent())
        return "?" + remainder.get();
      else
        throw new IllegalArgumentException("SWRL variable with IRI " + argumentIRI + " has no remainder");
    }
  }

  @NonNull @Override public String visit(@NonNull SWRLIndividualArgument individualArgument)
  {
    return visit(individualArgument.getIndividual());
  }

  @NonNull @Override public String visit(@NonNull SWRLLiteralArgument literalArgument)
  {
    OWLLiteral literal = literalArgument.getLiteral();

    return visit(literal);
  }

  @NonNull @Override public String visit(@NonNull SWRLSameIndividualAtom sameIndividualAtom)
  {
    SWRLIArgument iArgument1 = sameIndividualAtom.getFirstArgument();
    SWRLIArgument iArgument2 = sameIndividualAtom.getSecondArgument();
    StringBuilder sb = new StringBuilder();

    sb.append("sameAs");

    sb.append("(").append(visit(iArgument1)).append(", ").append(visit(iArgument2)).append(")");

    return sb.toString();
  }

  @NonNull @Override public String visit(@NonNull SWRLDifferentIndividualsAtom differentIndividualsAtom)
  {
    SWRLIArgument iArgument1 = differentIndividualsAtom.getFirstArgument();
    SWRLIArgument iArgument2 = differentIndividualsAtom.getSecondArgument();
    StringBuilder sb = new StringBuilder();

    sb.append("differentFrom");

    sb.append("(").append(visit(iArgument1)).append(", ").append(visit(iArgument2)).append(")");

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
      sb.append("[Unknown ").append(SWRLIArgument.class.getName()).append(" type ")
        .append(argument.getClass().getName()).append("]");

    return sb.toString();
  }

  private String visit(SWRLDArgument argument)
  {
    StringBuilder sb = new StringBuilder();

    if (argument instanceof SWRLBuiltInArgument) {
      SWRLBuiltInArgument builtInArgument = (SWRLBuiltInArgument)argument;
      // TODO Look at getting rid of cast; accept() in SWRLBuiltInArgument and SWRLObject could apply
      sb.append(builtInArgument.accept((SWRLBuiltInArgumentVisitorEx<String>)this));
    } else if (argument instanceof SWRLLiteralArgument) {
      SWRLLiteralArgument literalArgument = (SWRLLiteralArgument)argument;
      sb.append(literalArgument.accept(this));
    } else if (argument instanceof SWRLVariable) {
      SWRLVariable variableArgument = (SWRLVariable)argument;
      sb.append(variableArgument.accept(this));
    } else
      sb.append("[Unknown ").append(SWRLDArgument.class.getName()).append(" type ")
        .append(argument.getClass().getName()).append("]");

    return sb.toString();
  }

  @NonNull private String visit(@NonNull OWLClassExpression classExpression)
  {
    if (classExpression.isAnonymous())
      return "[Anonymous class expressions not implemented]";
    else {
      OWLClass cls = classExpression.asOWLClass();
      return visit(cls);
    }
  }

  @NonNull private String visit(@NonNull OWLClass cls)
  {
    String classNameShortForm = getShortForm(cls.getIRI());

    return classNameShortForm.startsWith(":") ? classNameShortForm.substring(1) : classNameShortForm;
  }

  @NonNull private String visit(@NonNull OWLIndividual individual)
  {
    if (individual.isNamed()) {
      String individualNameShortForm = getShortForm(individual.asOWLNamedIndividual().getIRI());

      return individualNameShortForm.startsWith(":") ? individualNameShortForm.substring(1) : individualNameShortForm;
    } else
      return this.iriResolver.render(individual);
  }

  @NonNull private String visit(@NonNull OWLObjectPropertyExpression objectPropertyExpression)
  {
    if (objectPropertyExpression.isAnonymous())
      return "[Anonymous object property expressions not implemented]";
    else
      return visit(objectPropertyExpression.asOWLObjectProperty());
  }

  @NonNull private String visit(@NonNull OWLObjectProperty property)
  {
    String objectPropertyNameShortForm = getShortForm(property.getIRI());

    return objectPropertyNameShortForm.startsWith(":") ?
      objectPropertyNameShortForm.substring(1) :
      objectPropertyNameShortForm;
  }

  @NonNull private String visit(@NonNull OWLDataPropertyExpression dataPropertyExpression)
  {
    if (dataPropertyExpression.isAnonymous())
      return "[Anonymous data property expressions not implemented]";
    else
      return visit(dataPropertyExpression.asOWLDataProperty());
  }

  @NonNull private String visit(@NonNull OWLDataProperty property)
  {
    String dataPropertyNameShortForm = getShortForm(property.getIRI());

    return dataPropertyNameShortForm.startsWith(":") ?
      dataPropertyNameShortForm.substring(1) :
      dataPropertyNameShortForm;
  }

  @NonNull private String visit(@NonNull OWLDataRange dataRange)
  {
    if (dataRange.isDatatype()) {
      OWLDatatype datatype = dataRange.asOWLDatatype();
      return getShortForm(datatype.getIRI());
    } else
      return visit(dataRange);
  }

  @NonNull @Override public String visit(@NonNull SWRLClassBuiltInArgument argument)
  {
    OWLClass cls = argument.getOWLClass();
    String classNameShortForm = getShortForm(cls.getIRI());

    return classNameShortForm.startsWith(":") ? classNameShortForm.substring(1) : classNameShortForm;
  }

  @Override public String visit(SWRLClassExpressionBuiltInArgument argument)
  {
    OWLClassExpression ce = argument.getOWLClassExpression();

    return this.iriResolver.render(ce);
  }

  @NonNull @Override public String visit(@NonNull SWRLNamedIndividualBuiltInArgument argument)
  {
    OWLNamedIndividual individual = argument.getOWLNamedIndividual();
    String individualNameShortForm = getShortForm(individual.getIRI());

    return individualNameShortForm.startsWith(":") ? individualNameShortForm.substring(1) : individualNameShortForm;
  }

  @NonNull @Override public String visit(@NonNull SWRLObjectPropertyBuiltInArgument argument)
  {
    OWLObjectProperty property = argument.getOWLObjectProperty();
    String objectPropertyNameShortForm = getShortForm(property.getIRI());

    return objectPropertyNameShortForm.startsWith(":") ?
      objectPropertyNameShortForm.substring(1) :
      objectPropertyNameShortForm;
  }

  @Override public String visit(SWRLObjectPropertyExpressionBuiltInArgument argument)
  {
    OWLObjectPropertyExpression pe = argument.getOWLObjectPropertyExpression();

    return this.iriResolver.render(pe);
  }

  @NonNull @Override public String visit(@NonNull SWRLDataPropertyBuiltInArgument argument)
  {
    OWLDataProperty property = argument.getOWLDataProperty();
    String dataPropertyNameShortForm = getShortForm(property.getIRI());

    return dataPropertyNameShortForm.startsWith(":") ?
      dataPropertyNameShortForm.substring(1) :
      dataPropertyNameShortForm;
  }

  @Override public String visit(SWRLDataPropertyExpressionBuiltInArgument argument)
  {
    OWLDataPropertyExpression pe = argument.getOWLDataPropertyExpression();

    return this.iriResolver.render(pe);
  }

  @NonNull @Override public String visit(@NonNull SWRLAnnotationPropertyBuiltInArgument argument)
  {
    OWLAnnotationProperty property = argument.getOWLAnnotationProperty();
    String annotationPropertyNameShortForm = getShortForm(property.getIRI());

    return annotationPropertyNameShortForm.startsWith(":") ?
      annotationPropertyNameShortForm.substring(1) :
      annotationPropertyNameShortForm;
  }

  @NonNull @Override public String visit(@NonNull SWRLDatatypeBuiltInArgument argument)
  {
    OWLDatatype datatype = argument.getOWLDatatype();
    return getShortForm(datatype.getIRI());
  }

  @NonNull @Override public String visit(@NonNull SWRLLiteralBuiltInArgument argument)
  {
    return visit(argument.getLiteral());
  }

  @NonNull @Override public String visit(@NonNull SWRLVariableBuiltInArgument argument)
  {
    IRI variableIRI = argument.getIRI();

    com.google.common.base.Optional<String> remainder = variableIRI.getRemainder();

    if (remainder.isPresent())
      return "?" + remainder.get();
    else
      throw new IllegalArgumentException("SWRL variable with IRI " + variableIRI + " has no remainder");
  }

  @NonNull @Override public String visit(@NonNull SWRLMultiValueVariableBuiltInArgument argument)
  {
    return argument.getVariableName();
  }

  @NonNull @Override public String visit(@NonNull SQWRLCollectionVariableBuiltInArgument argument)
  {
    return argument.getVariableName();
  }

  @NonNull private String visit(@NonNull OWLLiteral literal)
  {
    OWLDatatype datatype = literal.getDatatype();
    String value = literal.getLiteral();

    if (datatype.isString())
      return "\"" + value + "\"";
    else if (datatype.getIRI().equals(XSDVocabulary.DECIMAL.getIRI()))
      return value;
    else if (datatype.isBoolean())
      return value;
    else if (datatype.getIRI().equals(XSDVocabulary.INTEGER.getIRI()))
      return value;
    else
      return "\"" + value + "\"^^" + visit(datatype);
  }

  @NonNull private String getShortForm(@NonNull IRI iri)
  {
    String shortForm = iri2ShortForm(iri);

    return shortForm != null ? shortForm : iri.getShortForm();
  }

  @NonNull private String getPrefixedName(@NonNull IRI iri)
  {
    String prefixedName = iri2PrefixedName(iri);

    return prefixedName != null ? prefixedName : iri.getShortForm();
  }

  private boolean isSQWRLCollectionMakeBuiltInAtom(@NonNull SWRLAtom atom)
  {
    return atom instanceof SWRLAPIBuiltInAtom && SQWRLNames
      .isSQWRLCollectionMakeBuiltIn(((SWRLAPIBuiltInAtom)atom).getBuiltInPrefixedName());
  }

  private boolean isSQWRLCollectionOperateBuiltInAtom(@NonNull SWRLAtom atom)
  {
    return atom instanceof SWRLAPIBuiltInAtom && SQWRLNames
      .isSQWRLCollectionOperationBuiltIn(((SWRLAPIBuiltInAtom)atom).getBuiltInPrefixedName());
  }

  @NonNull private String iri2PrefixedName(@NonNull IRI iri)
  {
    Optional<@NonNull String> prefixedName = this.iriResolver.iri2PrefixedName(iri);

    if (prefixedName.isPresent())
      return prefixedName.get();
    else
      throw new IllegalArgumentException("could not get prefixed name for IRI " + iri);
  }

  @NonNull private String iri2ShortForm(@NonNull IRI iri)
  {
    Optional<@NonNull String> shortForm = this.iriResolver.iri2ShortForm(iri);

    if (shortForm.isPresent())
      return shortForm.get();
    else
      throw new IllegalArgumentException("could not get short form for IRI " + iri);
  }

  @NonNull private OWLOntology getOWLOntology() { return this.ontology; }
}