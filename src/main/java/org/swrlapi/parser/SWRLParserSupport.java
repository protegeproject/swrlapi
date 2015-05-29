package org.swrlapi.parser;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.factory.SWRLBuiltInArgumentFactory;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.factory.OWLLiteralFactory;
import org.swrlapi.factory.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Provides support methods used by the {@link org.swrlapi.parser.SWRLParser}.
 *
 * @see org.swrlapi.parser.SWRLParser
 */
public class SWRLParserSupport
{
  @NonNull private final SWRLAPIOWLOntology swrlapiOWLOntology;
  @NonNull private final DefaultPrefixManager prefixManager;

  public SWRLParserSupport(@NonNull SWRLAPIOWLOntology swrlapiOWLOntology)
  {
    this.swrlapiOWLOntology = swrlapiOWLOntology;
    this.prefixManager = swrlapiOWLOntology.getPrefixManager();
  }

  public boolean isOWLEntity(@NonNull String shortName)
  {
    return isOWLClass(shortName) || isOWLNamedIndividual(shortName) || isOWLObjectProperty(shortName)
      || isOWLDataProperty(shortName) || isOWLAnnotationProperty(shortName) || isOWLDatatype(shortName);
  }

  public boolean isOWLClass(@NonNull String shortName)
  {
    IRI classIRI = getPrefixManager().getIRI(shortName);
    return getOWLOntology().containsClassInSignature(classIRI, Imports.INCLUDED);
  }

  public boolean isOWLNamedIndividual(@NonNull String shortName)
  {
    IRI individualIRI = getPrefixManager().getIRI(shortName);
    return getOWLOntology().containsIndividualInSignature(individualIRI, Imports.INCLUDED);
  }

  public boolean isOWLObjectProperty(@NonNull String shortName)
  {
    IRI propertyIRI = getPrefixManager().getIRI(shortName);
    return getOWLOntology().containsObjectPropertyInSignature(propertyIRI, Imports.INCLUDED);
  }

  public boolean isOWLDataProperty(@NonNull String shortName)
  {
    IRI propertyIRI = getPrefixManager().getIRI(shortName);
    return getOWLOntology().containsDataPropertyInSignature(propertyIRI, Imports.INCLUDED);
  }

  public boolean isOWLAnnotationProperty(@NonNull String shortName)
  {
    IRI propertyIRI = getPrefixManager().getIRI(shortName);
    return getOWLOntology().containsAnnotationPropertyInSignature(propertyIRI, Imports.INCLUDED);
  }

  public boolean isOWLDatatype(@NonNull String shortName)
  {
    try {
      XSDVocabulary.parseShortName(shortName);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  public boolean isSWRLBuiltIn(@NonNull String shortName)
  {
    IRI builtInIRI = getPrefixManager().getIRI(shortName);
    return getSWRLAPIOWLOntology().isSWRLBuiltIn(builtInIRI);
  }

  private boolean isValidSWRLVariableName(@NonNull String candidateVariableName)
  {
    if (candidateVariableName.length() == 0)
      return false;

    if (!Character.isJavaIdentifierStart(candidateVariableName.charAt(0)))
      return false;

    for (int i = 1; i < candidateVariableName.length(); i++) {
      char c = candidateVariableName.charAt(i);
      if (!(Character.isJavaIdentifierPart(c) || c == ':' || c == '-')) {
        return false;
      }
    }
    return true;
  }

  public void checkThatSWRLVariableNameIsValid(@NonNull String variableName) throws SWRLParseException
  {
    if (!isValidSWRLVariableName(variableName))
      throw new SWRLParseException("Invalid SWRL variable name " + variableName);

    if (isOWLEntity(variableName))
      throw new SWRLParseException("Invalid SWRL variable name " + variableName
        + " - cannot use name of existing OWL class, individual, property, or datatype");
  }

  @NonNull public SWRLLiteralArgument getXSDStringSWRLLiteralArgument(@NonNull String lexicalValue)
  {
    OWLLiteral owlLiteral = getOWLLiteralFactory().getOWLLiteral(lexicalValue);
    return getOWLDataFactory().getSWRLLiteralArgument(owlLiteral);
  }

  @NonNull public SWRLLiteralArgument getXSDBooleanSWRLLiteralArgument(@NonNull String lexicalValue)
    throws SWRLParseException
  {
    try {
      Boolean value = Boolean.parseBoolean(lexicalValue);
      OWLLiteral owlLiteral = getOWLLiteralFactory().getOWLLiteral(value);
      return getOWLDataFactory().getSWRLLiteralArgument(owlLiteral);
    } catch (NumberFormatException e) {
      throw new SWRLParseException(lexicalValue + " is not a valid xsd:boolean");
    }
  }

  @NonNull public SWRLLiteralArgument getXSDIntSWRLLiteralArgument(@NonNull String lexicalValue)
    throws SWRLParseException
  {
    try {
      int value = Integer.parseInt(lexicalValue);
      OWLLiteral owlLiteral = getOWLLiteralFactory().getOWLLiteral(value);
      return getOWLDataFactory().getSWRLLiteralArgument(owlLiteral);
    } catch (NumberFormatException e) {
      throw new SWRLParseException(lexicalValue + " is not a valid xsd:int");
    }
  }

  @NonNull public SWRLLiteralArgument getXSDFloatSWRLLiteralArgument(@NonNull String lexicalValue)
    throws SWRLParseException
  {
    try {
      float value = Float.parseFloat(lexicalValue);
      OWLLiteral owlLiteral = getOWLLiteralFactory().getOWLLiteral(value);
      return getOWLDataFactory().getSWRLLiteralArgument(owlLiteral);
    } catch (NumberFormatException e) {
      throw new SWRLParseException(lexicalValue + " is not a valid xsd:float");
    }
  }

  @NonNull public SWRLVariable getSWRLVariable(@NonNull String variableName) throws SWRLParseException
  {
    IRI iri = getPrefixManager().getIRI(variableName);

    if (isOWLEntity(variableName))
      throw new SWRLParseException(
        variableName + " cannot be used as a SWRL variable name because it refers to an existing OWL entity");
    return getOWLDataFactory().getSWRLVariable(iri);
  }

  @NonNull public SWRLLiteralArgument getSWRLLiteralArgument(@NonNull String lexicalValue,
    @NonNull String datatypeShortName) throws SWRLParseException
  {
    OWLDatatype owlDatatype = getOWLDatatype(datatypeShortName);

    try {
      OWLLiteral literal = getOWLLiteralFactory().getOWLLiteral(lexicalValue, owlDatatype);
      return getOWLDataFactory().getSWRLLiteralArgument(literal);
    } catch (RuntimeException e) {
      throw new SWRLParseException(e.getMessage());
    }
  }

  @NonNull public SWRLRule getSWRLRule(@NonNull String ruleName, @NonNull Set<SWRLAtom> head,
    @NonNull Set<SWRLAtom> body, String comment, boolean isEnabled)
  {
    OWLAnnotation labelAnnotation = getOWLDataFactory()
      .getOWLAnnotation(getOWLDataFactory().getRDFSLabel(), getOWLLiteralFactory().getOWLLiteral(ruleName));
    OWLAnnotation commentAnnotation = getOWLDataFactory()
      .getOWLAnnotation(getOWLDataFactory().getRDFSComment(), getOWLLiteralFactory().getOWLLiteral(comment));
    // TODO Pull isEnabled from rule annotation
    Set<OWLAnnotation> annotations = new HashSet<>();

    annotations.add(labelAnnotation);
    annotations.add(commentAnnotation);

    return getOWLDataFactory().getSWRLRule(body, head, annotations);
  }

  @NonNull public Set<SWRLAtom> getSWRLBodyAtomList()
  {
    return new LinkedHashSet<>();
  }

  @NonNull public Set<SWRLAtom> getSWRLHeadAtomList()
  {
    return new LinkedHashSet<>();
  }

  @NonNull public SWRLClassAtom getSWRLClassAtom(@NonNull String classShortName, @NonNull SWRLIArgument iArgument)
    throws SWRLParseException
  {
    OWLClass cls = getOWLClass(classShortName);

    return getOWLDataFactory().getSWRLClassAtom(cls, iArgument);
  }

  @NonNull public SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(@NonNull String objectPropertyShortName,
    @NonNull SWRLIArgument iArgument1, @NonNull SWRLIArgument iArgument2) throws SWRLParseException
  {
    OWLObjectProperty objectProperty = getOWLObjectProperty(objectPropertyShortName);

    return getOWLDataFactory().getSWRLObjectPropertyAtom(objectProperty, iArgument1, iArgument2);
  }

  @NonNull public SWRLDataPropertyAtom getSWRLDataPropertyAtom(@NonNull String dataPropertyShortName,
    @NonNull SWRLIArgument iArgument, @NonNull SWRLDArgument dArgument) throws SWRLParseException
  {
    OWLDataProperty dataProperty = getOWLDataProperty(dataPropertyShortName);

    return getOWLDataFactory().getSWRLDataPropertyAtom(dataProperty, iArgument, dArgument);
  }

  @NonNull public SWRLBuiltInAtom getSWRLBuiltInAtom(@NonNull String builtInPrefixedName,
    @NonNull List<SWRLDArgument> arguments) throws SWRLParseException
  {
    IRI builtInIRI = getSWRLBuiltInIRI(builtInPrefixedName);
    return getOWLDataFactory().getSWRLBuiltInAtom(builtInIRI, arguments);
  }

  @NonNull public SWRLSameIndividualAtom getSWRLSameIndividualAtom(@NonNull SWRLIArgument iArgument1,
    @NonNull SWRLIArgument iArgument2)
  {
    return getOWLDataFactory().getSWRLSameIndividualAtom(iArgument1, iArgument2);
  }

  @NonNull public SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(@NonNull SWRLIArgument iArgument1,
    @NonNull SWRLIArgument iArgument2)
  {
    return getOWLDataFactory().getSWRLDifferentIndividualsAtom(iArgument1, iArgument2);
  }

  @NonNull public SWRLIndividualArgument getSWRLIndividualArgument(@NonNull String individualShortName)
    throws SWRLParseException
  {
    OWLNamedIndividual individual = getOWLNamedIndividual(individualShortName);
    return getOWLDataFactory().getSWRLIndividualArgument(individual);
  }

  @NonNull public SWRLClassBuiltInArgument getSWRLClassBuiltInArgument(@NonNull String classShortName)
    throws SWRLParseException
  {
    OWLClass cls = getOWLClass(classShortName);
    return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(cls);
  }

  @NonNull public SWRLNamedIndividualBuiltInArgument getSWRLNamedIndividualBuiltInArgument(
    @NonNull String individualShortName) throws SWRLParseException
  {
    OWLNamedIndividual individual = getOWLNamedIndividual(individualShortName);
    return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(individual);
  }

  @NonNull public SWRLObjectPropertyBuiltInArgument getSWRLObjectPropertyBuiltInArgument(
    @NonNull String propertyShortName) throws SWRLParseException
  {
    OWLObjectProperty property = getOWLObjectProperty(propertyShortName);
    return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(property);
  }

  @NonNull public SWRLDataPropertyBuiltInArgument getSWRLDataPropertyBuiltInArgument(@NonNull String propertyShortName)
    throws SWRLParseException
  {
    OWLDataProperty property = getOWLDataProperty(propertyShortName);
    return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(property);
  }

  @NonNull public SWRLAnnotationPropertyBuiltInArgument getSWRLAnnotationPropertyBuiltInArgument(
    @NonNull String propertyShortName) throws SWRLParseException
  {
    OWLAnnotationProperty property = getOWLAnnotationProperty(propertyShortName);
    return getSWRLBuiltInArgumentFactory().getAnnotationPropertyBuiltInArgument(property);
  }

  @NonNull public SWRLDatatypeBuiltInArgument getSWRLDatatypeBuiltInArgument(@NonNull String datatypeShortName)
    throws SWRLParseException
  {
    OWLDatatype datatype = getOWLDatatype(datatypeShortName);
    return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(datatype);
  }

  @NonNull public String getShortNameFromIRI(@NonNull String iriString, boolean interactiveParseOnly)
    throws SWRLParseException
  {
    try {
      IRI iri = this.prefixManager.getIRI(iriString);

      return this.prefixManager.getShortForm(iri);
    } catch (RuntimeException e) {
      if (interactiveParseOnly)
        throw new SWRLIncompleteRuleException("IRI " + iriString + " does not refer to a valid OWL entity");
      else
        throw new SWRLParseException("IRI " + iriString + " does not refer to a valid OWL entity");
    }
  }

  public static int findSplittingPoint(@NonNull String ruleText)
  {
    int i = ruleText.length() - 1;

    while (i >= 0 && !(SWRLTokenizer.isOrdinaryChar(ruleText.charAt(i)) || ruleText.charAt(i) == '"'
      || ruleText.charAt(i) == ' '))
      i--;

    return i + 1;
  }

  @NonNull private OWLClass getOWLClass(@NonNull String classShortName) throws SWRLParseException
  {
    if (isOWLClass(classShortName)) {
      IRI classIRI = getPrefixManager().getIRI(classShortName);
      return getOWLDataFactory().getOWLClass(classIRI);
    } else
      throw new SWRLParseException(classShortName + " is not an OWL class");
  }

  @NonNull private OWLNamedIndividual getOWLNamedIndividual(@NonNull String individualShortName) throws SWRLParseException
  {
    if (isOWLNamedIndividual(individualShortName)) {
      IRI individualIRI = getPrefixManager().getIRI(individualShortName);
      return getOWLDataFactory().getOWLNamedIndividual(individualIRI);
    } else
      throw new SWRLParseException(individualShortName + " is not an OWL named individual");
  }

  @NonNull private OWLObjectProperty getOWLObjectProperty(@NonNull String objectPropertyShortName) throws SWRLParseException
  {
    if (isOWLObjectProperty(objectPropertyShortName)) {
      IRI propertyIRI = getPrefixManager().getIRI(objectPropertyShortName);
      return getOWLDataFactory().getOWLObjectProperty(propertyIRI);
    } else
      throw new SWRLParseException(objectPropertyShortName + " is not an OWL object property");
  }

  @NonNull private OWLDataProperty getOWLDataProperty(@NonNull String dataPropertyShortName) throws SWRLParseException
  {
    if (isOWLDataProperty(dataPropertyShortName)) {
      IRI propertyIRI = getPrefixManager().getIRI(dataPropertyShortName);
      return getOWLDataFactory().getOWLDataProperty(propertyIRI);
    } else
      throw new SWRLParseException(dataPropertyShortName + " is not an OWL data property");
  }

  @NonNull private OWLAnnotationProperty getOWLAnnotationProperty(@NonNull String annotationPropertyShortName)
    throws SWRLParseException
  {
    if (isOWLAnnotationProperty(annotationPropertyShortName)) {
      IRI propertyIRI = getPrefixManager().getIRI(annotationPropertyShortName);
      return getOWLDataFactory().getOWLAnnotationProperty(propertyIRI);
    } else
      throw new SWRLParseException(annotationPropertyShortName + " is not an OWL annotation property");
  }

  @NonNull private OWLDatatype getOWLDatatype(@NonNull String datatypeShortName) throws SWRLParseException
  {
    if (isOWLDatatype(datatypeShortName)) {
      IRI datatypeIRI = getPrefixManager().getIRI(datatypeShortName);
      return getOWLDataFactory().getOWLDatatype(datatypeIRI);
    } else
      throw new SWRLParseException(datatypeShortName + " is not a valid datatype");
  }

  @NonNull private IRI getSWRLBuiltInIRI(@NonNull String builtInPrefixedName) throws SWRLParseException
  {
    if (!isSWRLBuiltIn(builtInPrefixedName))
      throw new SWRLParseException(builtInPrefixedName + " is not a SWRL built-in");
    else
      return getPrefixManager().getIRI(builtInPrefixedName);
  }

  @NonNull private SWRLAPIOWLOntology getSWRLAPIOWLOntology()
  {
    return this.swrlapiOWLOntology;
  }

  @NonNull private OWLOntology getOWLOntology()
  {
    return getSWRLAPIOWLOntology().getOWLOntology();
  }

  @NonNull private SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
  {
    return getSWRLAPIOWLOntology().getSWRLAPIOWLDataFactory();
  }

  @NonNull private SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
  {
    return getSWRLAPIOWLDataFactory().getSWRLBuiltInArgumentFactory();
  }

  @NonNull private OWLDataFactory getOWLDataFactory()
  {
    return getSWRLAPIOWLOntology().getSWRLAPIOWLDataFactory();
  }

  @NonNull private OWLLiteralFactory getOWLLiteralFactory()
  {
    return getSWRLAPIOWLOntology().getSWRLAPIOWLDataFactory().getOWLLiteralFactory();
  }

  @NonNull private DefaultPrefixManager getPrefixManager()
  {
    return this.prefixManager;
  }
}
