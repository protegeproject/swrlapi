package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLPredicate;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.exceptions.SWRLAPIInternalException;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.factory.resolvers.DefaultIRIResolver;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLQueryRenderer;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.exceptions.SQWRLInvalidQueryNameException;
import org.swrlapi.ui.model.SWRLAutoCompleter;

import javax.annotation.Nonnull;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class DefaultSWRLAPIOWLOntology implements SWRLAPIOWLOntology, OWLOntologyChangeListener
{
  private static final Logger log = LoggerFactory.getLogger(DefaultSWRLAPIOWLOntology.class);

  @NonNull private final OWLOntology ontology;
  @NonNull private final DefaultPrefixManager prefixManager;
  @NonNull private final IRIResolver iriResolver;
  @NonNull private final SWRLAPIOWLDataFactory swrlapiOWLDataFactory;
  @NonNull private final Set<@NonNull IRI> swrlBuiltInIRIs;

  @NonNull private final Map<@NonNull String, @NonNull SWRLAPIRule> swrlRules; // SWRL rules and SQWRL queries extracted from ontolgy
  @NonNull private final Map<@NonNull String, @NonNull SWRLRule> owlapiRules; // All SWRL rules in supplied ontology
  @NonNull private final Map<@NonNull String, @NonNull SQWRLQuery> sqwrlQueries;

  // All asserted OWL axioms extracted from the supplied ontology; includes SWRL rules
  @NonNull private final Set<@NonNull OWLAxiom> assertedOWLAxioms;

  @NonNull private final Map<@NonNull IRI, @NonNull OWLDeclarationAxiom> classDeclarationAxioms;
  @NonNull private final Map<@NonNull IRI, @NonNull OWLDeclarationAxiom> individualDeclarationAxioms;
  @NonNull private final Map<@NonNull IRI, @NonNull OWLDeclarationAxiom> objectPropertyDeclarationAxioms;
  @NonNull private final Map<@NonNull IRI, @NonNull OWLDeclarationAxiom> dataPropertyDeclarationAxioms;
  @NonNull private final Map<@NonNull IRI, @NonNull OWLDeclarationAxiom> annotationPropertyDeclarationAxioms;

  private boolean hasOntologyChanged = true; // Ensure initial processing

  public DefaultSWRLAPIOWLOntology(@NonNull OWLOntology ontology)
  {
    this.ontology = ontology;
    this.prefixManager = new DefaultPrefixManager();
    this.iriResolver = new DefaultIRIResolver(this.prefixManager);
    this.swrlapiOWLDataFactory = SWRLAPIFactory.createSWRLAPIOWLDataFactory(this.iriResolver);
    this.swrlBuiltInIRIs = new HashSet<>();

    this.swrlRules = new HashMap<>();
    this.owlapiRules = new HashMap<>();
    this.sqwrlQueries = new HashMap<>();

    this.assertedOWLAxioms = new HashSet<>();

    this.classDeclarationAxioms = new HashMap<>();
    this.individualDeclarationAxioms = new HashMap<>();
    this.objectPropertyDeclarationAxioms = new HashMap<>();
    this.dataPropertyDeclarationAxioms = new HashMap<>();
    this.annotationPropertyDeclarationAxioms = new HashMap<>();

    addDefaultSWRLBuiltIns();
    addSWRLAPIBuiltInOntologies(this.ontology);

    updatePrefixes(this.ontology, this.prefixManager);

    this.ontology.getOWLOntologyManager().addOntologyChangeListener(this);
  }

  @Override public void processOntology() throws SQWRLException
  { // TODO If ontology has not changed do not reprocess; however, will break currently

    reset(); // Will reset hasOntologyChanged
    updatePrefixes(this.ontology, this.prefixManager);
    processSWRLRulesAndSQWRLQueries();
    processOWLAxioms();
  }

  @Override public void reset()
  {
    this.swrlRules.clear();
    this.owlapiRules.clear();
    this.sqwrlQueries.clear();

    getIRIResolver().reset();

    this.assertedOWLAxioms.clear();

    this.classDeclarationAxioms.clear();
    this.individualDeclarationAxioms.clear();
    this.objectPropertyDeclarationAxioms.clear();
    this.dataPropertyDeclarationAxioms.clear();
    this.annotationPropertyDeclarationAxioms.clear();

    this.hasOntologyChanged = false;
  }

  @NonNull @Override public SWRLAPIRule createSWRLRule(@NonNull String ruleName, @NonNull String rule)
    throws SWRLParseException
  {
    return createSWRLRule(ruleName, rule, "", true);
  }

  @NonNull @Override public SWRLAPIRule createSWRLRule(@NonNull String ruleName, @NonNull String rule,
    @NonNull String comment, boolean isActive) throws SWRLParseException
  {
    Optional<SWRLRule> owlapiRule = createSWRLParser().parseSWRLRule(rule, false, ruleName, comment);

    if (owlapiRule.isPresent()) {
      SWRLAPIRule swrlapiRule = convertOWLAPIRule2SWRLAPIRule(owlapiRule.get(), ruleName, comment, isActive);

      addSWRLRule(swrlapiRule, owlapiRule.get()); // Adds rule to the underlying ontology

      return swrlapiRule;
    } else
      throw new SWRLParseException("Unknown error - parser failed to generate a rule");
  }

  @NonNull @Override public SQWRLQuery createSQWRLQuery(@NonNull String queryName, @NonNull String query)
    throws SWRLParseException, SQWRLException
  {
    return createSQWRLQuery(queryName, query, "", true);
  }

  @NonNull @Override public SQWRLQuery createSQWRLQuery(@NonNull String queryName, @NonNull String queryText,
    @NonNull String comment, boolean isActive) throws SWRLParseException, SQWRLException
  {
    Optional<SWRLRule> owlapiRule = createSWRLParser().parseSWRLRule(queryText, false, queryName, comment);

    if (owlapiRule.isPresent()) {
      SWRLAPIRule swrlapiRule = convertOWLAPIRule2SWRLAPIRule(owlapiRule.get(), queryName, comment, isActive);

      addSWRLRule(swrlapiRule, owlapiRule.get()); // Adds query to the underlying ontology

      if (swrlapiRule.isSQWRLQuery()) {
        SQWRLQuery query = createSQWRLQueryFromSWRLRule(swrlapiRule);
        this.sqwrlQueries.put(queryName, query);
        return query;
      } else
        throw new SWRLParseException(queryName + " is not a SQWRL query");
    } else
      throw new SWRLParseException("Unknown error - parser failed to generate a query");
  }

  @NonNull @Override public Set<@NonNull SWRLAPIRule> getSWRLRules()
  {
    return new HashSet<>(this.swrlRules.values());
  }

  private void processSWRLRulesAndSQWRLQueries() throws SQWRLException
  {
    int ruleNameIndex = 0;

    this.swrlRules.clear();
    this.owlapiRules.clear();
    this.sqwrlQueries.clear();

    for (SWRLRule owlapiRule : getOWLOntology().getAxioms(AxiomType.SWRL_RULE, Imports.INCLUDED)) {
      Optional<@NonNull String> ruleName = getRuleName(owlapiRule);
      boolean isActive = getIsRuleEnabled(owlapiRule);
      String comment = getRuleComment(owlapiRule);

      String finalRuleName = ruleName.isPresent() ? ruleName.get() : "R" + ++ruleNameIndex;

      SWRLAPIRule swrlapiRule = convertOWLAPIRule2SWRLAPIRule(owlapiRule, finalRuleName, comment, isActive);

      this.swrlRules.put(finalRuleName, swrlapiRule);
      this.owlapiRules.put(finalRuleName, owlapiRule);
      this.assertedOWLAxioms.add(swrlapiRule);

      if (swrlapiRule.isSQWRLQuery()) {
        SQWRLQuery query = createSQWRLQueryFromSWRLRule(swrlapiRule);
        this.sqwrlQueries.put(finalRuleName, query);
      }
      // TODO Do we want to add axioms to OWLAPI rule that does not have them?
      // generateRuleAnnotations(ruleName, comment, true)
      // ontologyManager.removeAxiom(ontology, owlapiRule); // Remove the original annotated rule
      // ontologyManager.addAxiom(ontology, annotatedOWLAPIRule); // Replace with annotated rule
    }
  }

  @NonNull @Override public Optional<@NonNull SWRLAPIRule> getSWRLRule(@NonNull String ruleName)
    throws SWRLRuleException
  {
    if (!this.swrlRules.containsKey(ruleName))
      return Optional.<@NonNull SWRLAPIRule>empty();
    else
      return Optional.of(this.swrlRules.get(ruleName));
  }

  @Override public void deleteSWRLRule(@NonNull String ruleName)
  {
    if (this.swrlRules.containsKey(ruleName)) {
      SWRLAPIRule rule = this.swrlRules.get(ruleName);
      SWRLRule owlapiRule = this.owlapiRules.get(ruleName);

      if (rule.isSQWRLQuery())
        this.sqwrlQueries.remove(ruleName);

      this.swrlRules.remove(ruleName);
      this.owlapiRules.remove(ruleName);

      this.ontology.getOWLOntologyManager().removeAxiom(this.ontology, owlapiRule);
    }
  }

  @NonNull @Override public OWLOntology getOWLOntology()
  {
    return this.ontology;
  }

  @NonNull @Override public OWLOntologyManager getOWLOntologyManager()
  {
    return this.ontology.getOWLOntologyManager();
  }

  @NonNull @Override public DefaultPrefixManager getPrefixManager()
  {
    return this.prefixManager;
  }

  @NonNull @Override public SWRLParser createSWRLParser()
  {
    return new SWRLParser(this);
  }

  @NonNull @Override public SWRLAutoCompleter createSWRLAutoCompleter()
  {
    return SWRLAPIFactory.createSWRLAutoCompleter(this);
  }

  @NonNull @Override public SWRLRuleRenderer createSWRLRuleRenderer()
  {
    return SWRLAPIFactory.createSWRLRuleRenderer(this.getOWLOntology(), this.getPrefixManager());
  }

  @NonNull @Override public SQWRLQueryRenderer createSQWRLQueryRenderer()
  {
    return SWRLAPIFactory.createSQWRLQueryRenderer(this.getOWLOntology(), this.getPrefixManager());
  }

  @Override public int getNumberOfSWRLRules()
  {
    return this.swrlRules.values().size();
  }

  @Override public int getNumberOfOWLClassDeclarationAxioms()
  {
    return this.classDeclarationAxioms.values().size();
  }

  @Override public int getNumberOfOWLIndividualDeclarationAxioms()
  {
    return this.individualDeclarationAxioms.values().size();
  }

  @Override public int getNumberOfOWLObjectPropertyDeclarationAxioms()
  {
    return this.objectPropertyDeclarationAxioms.size();
  }

  @Override public int getNumberOfOWLDataPropertyDeclarationAxioms()
  {
    return this.dataPropertyDeclarationAxioms.size();
  }

  @Override public int getNumberOfOWLAxioms()
  {
    return this.assertedOWLAxioms.size();
  }

  @NonNull @Override public Set<@NonNull OWLAxiom> getOWLAxioms()
  {
    return Collections.unmodifiableSet(this.assertedOWLAxioms);
  }

  @Override public boolean hasAssertedOWLAxiom(@NonNull OWLAxiom axiom)
  {
    return this.assertedOWLAxioms.contains(axiom);
  }

  @NonNull @Override public Set<@NonNull String> getSQWRLQueryNames()
  {
    return new HashSet<>(this.sqwrlQueries.keySet());
  }

  @NonNull @Override public Set<@NonNull SQWRLQuery> getSQWRLQueries()
  {
    return new HashSet<>(this.sqwrlQueries.values());
  }

  /**
   * Get the results from a previously executed SQWRL query.
   */
  @NonNull @Override public SQWRLResult getSQWRLResult(@NonNull String queryName) throws SQWRLException
  {
    if (!this.sqwrlQueries.containsKey(queryName))
      throw new SQWRLInvalidQueryNameException(queryName);

    return this.sqwrlQueries.get(queryName).getSQWRLResult();
  }

  /**
   * Get the result generator for a SQWRL query.
   */
  @NonNull @Override public SQWRLResultGenerator getSQWRLResultGenerator(@NonNull String queryName)
    throws SQWRLException
  {
    if (!this.sqwrlQueries.containsKey(queryName))
      throw new SQWRLInvalidQueryNameException(queryName);

    return this.sqwrlQueries.get(queryName).getSQWRLResultGenerator();
  }

  @NonNull private SQWRLQuery createSQWRLQueryFromSWRLRule(@NonNull SWRLAPIRule rule) throws SQWRLException
  {
    String queryName = rule.getRuleName();
    boolean active = rule.isActive();
    String comment = rule.getComment();

    return SWRLAPIFactory.createSQWRLQuery(queryName, rule.getBodyAtoms(), rule.getHeadAtoms(), active, comment,
      getSWRLAPIOWLDataFactory().getLiteralFactory(), getIRIResolver());
  }

  @NonNull private Optional<@NonNull String> getRuleName(@NonNull SWRLRule owlapiRule)
  {
    OWLAnnotationProperty labelAnnotation = getOWLDataFactory()
      .getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());

    for (OWLAnnotation annotation : owlapiRule.getAnnotations(labelAnnotation)) {
      if (annotation.getValue() instanceof OWLLiteral) {
        OWLLiteral literal = (OWLLiteral)annotation.getValue();
        return Optional.of(literal.getLiteral()); //  We pick the first one
      }
    }
    return Optional.<@NonNull String>empty();
  }

  private boolean getIsRuleEnabled(@NonNull SWRLRule owlapiRule)
  {
    OWLAnnotationProperty enabledAnnotationProperty = getOWLDataFactory()
      .getOWLAnnotationProperty(IRI.create("http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled"));

    for (OWLAnnotation annotation : owlapiRule.getAnnotations(enabledAnnotationProperty)) {
      if (annotation.getValue() instanceof OWLLiteral) {
        OWLLiteral literal = (OWLLiteral)annotation.getValue();
        if (literal.isBoolean())
          return literal.parseBoolean();
      }
    }
    return true;
  }

  @NonNull private String getRuleComment(@NonNull SWRLRule owlapiRule)
  {
    OWLAnnotationProperty commentAnnotationProperty = getOWLDataFactory()
      .getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_COMMENT.getIRI());

    for (OWLAnnotation annotation : owlapiRule.getAnnotations(commentAnnotationProperty)) {
      if (annotation.getValue() instanceof OWLLiteral) {
        OWLLiteral literal = (OWLLiteral)annotation.getValue();
        return literal.getLiteral(); // We pick the first one
      }
    }
    return "";
  }

  @NonNull public Set<@NonNull OWLAnnotation> generateRuleAnnotations(@NonNull String ruleName, @NonNull String comment,
    boolean isRuleEnabled)
  {
    OWLAnnotation labelAnnotation = getOWLDataFactory()
      .getOWLAnnotation(getOWLDataFactory().getRDFSLabel(), getOWLDataFactory().getOWLLiteral(ruleName));
    OWLAnnotation commentAnnotation = getOWLDataFactory()
      .getOWLAnnotation(getOWLDataFactory().getRDFSComment(), getOWLDataFactory().getOWLLiteral(comment));
    OWLAnnotationProperty isRuleEnabledAnnotationProperty = getOWLDataFactory()
      .getOWLAnnotationProperty(IRI.create("http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled"));
    OWLAnnotation isRuleEnabledAnnotation = getOWLDataFactory()
      .getOWLAnnotation(isRuleEnabledAnnotationProperty, getOWLDataFactory().getOWLLiteral(isRuleEnabled));

    Set<@NonNull OWLAnnotation> annotations = new HashSet<>();

    annotations.add(labelAnnotation);
    annotations.add(commentAnnotation);
    annotations.add(isRuleEnabledAnnotation);

    return annotations;
  }

  @NonNull @Override public SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
  {
    return this.swrlapiOWLDataFactory;
  }

  @NonNull @Override public OWLDataFactory getOWLDataFactory()
  {
    return this.ontology.getOWLOntologyManager().getOWLDataFactory();
  }

  @NonNull @Override public IRIResolver getIRIResolver()
  {
    return this.iriResolver;
  }

  @Override public void startBulkConversion()
  {
    // TODO implement startBulkConversion
  }

  @Override public void completeBulkConversion()
  {
    // TODO implement completeBulkConversion
  }

  @Override public boolean hasOntologyChanged()
  {
    return this.hasOntologyChanged;
  }

  @Override public void resetOntologyChanged()
  {
    hasOntologyChanged = false;
  }

  // void addRuleNameAnnotation(@NonNull SWRLRule rule, @NonNull String ruleName)
  // {
  // OWLAnnotationProperty labelAnnotationProperty = getOWLDataFactory().getRDFSLabel();
  // OWLLiteral label = getOWLDataFactory().getOWLLiteral(ruleName, "en");
  // OWLAnnotation labelAnnotation = getOWLDataFactory().getOWLAnnotation(labelAnnotationProperty, label);
  //
  // OWLAxiom annotationAssertionAxiom = getOWLDataFactory().getOWLAnnotationAssertionAxiom(rule, labelAnnotation);
  // this.ontologyManager.applyChange(new AddAxiom(this.ontology, annotationAssertionAxiom));
  // }

  @Override public boolean isSWRLBuiltIn(@NonNull IRI iri)
  {
    return this.swrlBuiltInIRIs.contains(iri);
  }

  @Override public void addSWRLBuiltIn(@NonNull IRI iri)
  {
    this.swrlBuiltInIRIs.add(iri);
  }

  @NonNull @Override public Set<@NonNull IRI> getSWRLBuiltInIRIs()
  {
    return new HashSet<>(this.swrlBuiltInIRIs);
  }

  @NonNull @Override public SQWRLResultGenerator createSQWRLResultGenerator()
  {
    return SWRLAPIFactory.createSQWRLResultGenerator(this.iriResolver);
  }

  /**
   * We take an OWLAPI {@link org.semanticweb.owlapi.model.SWRLRule} object and for every OWLAPI
   * {@link org.semanticweb.owlapi.model.SWRLBuiltInAtom} in it we create a SWRLAPI
   * {@link org.swrlapi.core.SWRLAPIBuiltInAtom}; all other atoms remain the same.
   *
   * @see org.semanticweb.owlapi.model.SWRLRule
   * @see org.swrlapi.core.SWRLAPIRule
   */
  @NonNull private SWRLAPIRule convertOWLAPIRule2SWRLAPIRule(@NonNull SWRLRule owlapiRule, @NonNull String ruleName,
    @NonNull String comment, boolean isActive)
  {
    List<@NonNull SWRLAtom> owlapiBodyAtoms = new ArrayList<>(owlapiRule.getBody());
    List<@NonNull SWRLAtom> owlapiHeadAtoms = new ArrayList<>(owlapiRule.getHead());
    List<@NonNull SWRLAtom> swrlapiBodyAtoms = new ArrayList<>();
    List<@NonNull SWRLAtom> swrlapiHeadAtoms = new ArrayList<>();

    for (SWRLAtom atom : owlapiBodyAtoms) {
      if (isSWRLBuiltInAtom(atom)) {
        SWRLBuiltInAtom builtInAtom = (SWRLBuiltInAtom)atom;
        IRI builtInIRI = builtInAtom.getPredicate();
        String builtInPrefixedName = getIRIResolver().iri2PrefixedName(builtInIRI);
        List<@NonNull SWRLDArgument> swrlDArguments = builtInAtom.getArguments();
        List<@NonNull SWRLBuiltInArgument> swrlBuiltInArguments = convertSWRLDArguments2SWRLBuiltInArguments(
          swrlDArguments);
        SWRLBuiltInAtom swrlapiBuiltInAtom = getSWRLAPIOWLDataFactory()
          .getSWRLAPIBuiltInAtom(ruleName, builtInIRI, builtInPrefixedName, swrlBuiltInArguments);
        swrlapiBodyAtoms.add(swrlapiBuiltInAtom);
      } else
        swrlapiBodyAtoms.add(atom); // Only built-in atoms are converted; other atoms remain the same
    }

    for (SWRLAtom atom : owlapiHeadAtoms) {
      if (isSWRLBuiltInAtom(atom)) {
        SWRLBuiltInAtom builtInAtom = (SWRLBuiltInAtom)atom;
        IRI builtInIRI = builtInAtom.getPredicate();
        String builtInPrefixedName = getIRIResolver().iri2PrefixedName(builtInIRI);
        List<@NonNull SWRLDArgument> swrlDArguments = builtInAtom.getArguments();
        List<@NonNull SWRLBuiltInArgument> swrlBuiltInArguments = convertSWRLDArguments2SWRLBuiltInArguments(
          swrlDArguments);
        SWRLBuiltInAtom swrlapiBuiltInAtom = getSWRLAPIOWLDataFactory()
          .getSWRLAPIBuiltInAtom(ruleName, builtInIRI, builtInPrefixedName, swrlBuiltInArguments);
        swrlapiHeadAtoms.add(swrlapiBuiltInAtom);
      } else
        swrlapiHeadAtoms.add(atom); // Only built-in atoms are converted; other atoms remain the same
    }
    return SWRLAPIFactory.createSWRLAPIRule(ruleName, swrlapiBodyAtoms, swrlapiHeadAtoms, comment, isActive);
  }

  /**
   * Both the OWLAPI and the SWRLAPI use the {@link org.semanticweb.owlapi.model.SWRLBuiltInAtom} class to represent
   * built-in atoms. However, the SWRLAPI has a richer range of possible argument types. The OWLAPI allows
   * {@link org.semanticweb.owlapi.model.SWRLDArgument} built-in arguments only, whereas the SWRLAPI has a range of
   * types. These types are represented buy the {@link org.swrlapi.builtins.arguments.SWRLBuiltInArgument} interface.
   *
   * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
   */
  @NonNull private List<@NonNull SWRLBuiltInArgument> convertSWRLDArguments2SWRLBuiltInArguments(
    @NonNull List<@NonNull SWRLDArgument> swrlDArguments)
  {
    List<@NonNull SWRLBuiltInArgument> swrlBuiltInArguments = new ArrayList<>();

    for (SWRLDArgument swrlDArgument : swrlDArguments) {
      SWRLBuiltInArgument swrlBuiltInArgument = convertSWRLDArgument2SWRLBuiltInArgument(swrlDArgument);
      swrlBuiltInArguments.add(swrlBuiltInArgument);
    }

    return swrlBuiltInArguments;
  }

  /**
   * The {@link SWRLBuiltInArgument} interface is the SWRLAPI extension point to the OWLAPI to represent arguments to
   * SWRL built-in atoms. In the OWL Specification only data values or variables referencing them are allowed as
   * parameters to built-in atoms. The SWRLAPI named OWL properties (classes, named individuals, properties, and
   * datatypes) can also be passed to built-ins.
   *
   * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
   * @see org.semanticweb.owlapi.model.SWRLLiteralArgument
   * @see org.semanticweb.owlapi.model.SWRLDArgument
   * @see org.semanticweb.owlapi.model.SWRLVariable
   */
  @NonNull private SWRLBuiltInArgument convertSWRLDArgument2SWRLBuiltInArgument(@NonNull SWRLDArgument swrlDArgument)
  {
    if (swrlDArgument instanceof SWRLLiteralArgument) {
      SWRLLiteralArgument swrlLiteralArgument = (SWRLLiteralArgument)swrlDArgument;
      return convertSWRLLiteralArgument2SWRLBuiltInArgument(swrlLiteralArgument);
    } else if (swrlDArgument instanceof SWRLVariable) {
      SWRLVariable swrlVariable = (SWRLVariable)swrlDArgument;
      return convertSWRLVariable2SWRLBuiltInArgument(swrlVariable);
    } else
      throw new SWRLAPIInternalException(
        "Unknown " + SWRLDArgument.class.getName() + " class " + swrlDArgument.getClass().getName());
  }

  /**
   * The OWLAPI follows the OWL Specification and does not explicitly allow named OWL properties as arguments to
   * built-ins. However, if OWLAPI parsers encounter OWL properties as parameters they appear to represent them as SWRL
   * variables - with the variable IRI set to the IRI of the entity ({@link org.semanticweb.owlapi.model.OWLEntity}
   * classes represent named OWL concepts so have an IRI). So if we are processing built-in parameters and encounter
   * variables with an IRI referring to named OWL properties in the active ontology we can transform them to the
   * appropriate SWRLAPI built-in argument for the named entity.
   * <p>
   * Note: An important restriction here is that variable names do not intersect with named properties in their OWL
   * ontology. A SWRL parser should check for this on inpit and report an error.
   *
   * @see org.swrlapi.builtins.arguments.SWRLNamedBuiltInArgument
   */
  @NonNull private SWRLBuiltInArgument convertSWRLVariable2SWRLBuiltInArgument(@NonNull SWRLVariable swrlVariable)
  {
    IRI iri = swrlVariable.getIRI();

    if (isOWLClass(iri)) {
      OWLClass cls = getOWLDataFactory().getOWLClass(iri);

      return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(cls);
    } else if (getOWLOntology().containsIndividualInSignature(iri, Imports.INCLUDED)) {
      OWLNamedIndividual individual = getOWLDataFactory().getOWLNamedIndividual(iri);

      return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(individual);
    } else if (getOWLOntology().containsObjectPropertyInSignature(iri, Imports.INCLUDED)) {
      OWLObjectProperty property = getOWLDataFactory().getOWLObjectProperty(iri);

      return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(property);
    } else if (getOWLOntology().containsDataPropertyInSignature(iri, Imports.INCLUDED)) {
      OWLDataProperty property = getOWLDataFactory().getOWLDataProperty(iri);

      return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(property);
    } else if (getOWLOntology().containsAnnotationPropertyInSignature(iri, Imports.INCLUDED)) {
      OWLAnnotationProperty property = getOWLDataFactory().getOWLAnnotationProperty(iri);

      return getSWRLBuiltInArgumentFactory().getAnnotationPropertyBuiltInArgument(property);
    } else if (getOWLOntology().containsDatatypeInSignature(iri, Imports.INCLUDED)) {
      OWLDatatype datatype = getOWLDataFactory().getOWLDatatype(iri);

      return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(datatype);
    } else {
      IRI variableIRI = swrlVariable.getIRI();
      SWRLVariableBuiltInArgument argument = getSWRLBuiltInArgumentFactory().getVariableBuiltInArgument(variableIRI);
      getIRIResolver().recordSWRLVariable(swrlVariable);

      return argument;
    }
  }

  private boolean isOWLClass(@NonNull IRI iri)
  {
    return getOWLOntology().containsClassInSignature(iri, Imports.INCLUDED) || iri
      .equals(OWLRDFVocabulary.OWL_THING.getIRI()) || iri.equals(OWLRDFVocabulary.OWL_NOTHING.getIRI());
  }

  /**
   * The OWLAPI permits only variable and literal arguments to built-ins, which conforms with the SWRL Specification.
   * The SWRLAPI also permits OWL classes, individuals, properties, and datatypes as arguments. In order to support
   * these additional argument types in a standards-conformant way, the SWRLAPI treats URI literal arguments specially.
   * It a URI literal argument is passed to a built-in we determine if it refers to an OWL named object in the active
   * ontology and if so we create specific SWRLAPI built-in argument types for it.
   * <p>
   * The SWRLAPI allows SQWRL collection built-in arguments (represented by a
   * {@link org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument}) and multi-value variables
   * (represented by a {@link org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument}). These two argument
   * types are not instantiated directly as built-in argument types. They are created at runtime during rule execution
   * and passed as result values in SWRL variable built-in arguments.
   *
   * @see org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument
   * @see org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument
   * @see org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument
   * @see org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument
   * @see org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument
   * @see org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument
   * @see org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument
   * @see org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument
   * @see org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument
   */
  @NonNull private SWRLBuiltInArgument convertSWRLLiteralArgument2SWRLBuiltInArgument(
    @NonNull SWRLLiteralArgument swrlLiteralArgument)
  {
    OWLLiteral literal = swrlLiteralArgument.getLiteral();
    OWLDatatype literalDatatype = literal.getDatatype();

    if (isURI(literalDatatype)) { // TODO This URI-based approach may not be relevant
      IRI iri = IRI.create(literal.getLiteral());
      if (isOWLClass(iri)) {
        OWLClass cls = getOWLDataFactory().getOWLClass(iri);
        return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(cls);
      } else if (getOWLOntology().containsIndividualInSignature(iri)) {
        OWLNamedIndividual individual = getOWLDataFactory().getOWLNamedIndividual(iri);
        return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(individual);
      } else if (getOWLOntology().containsObjectPropertyInSignature(iri)) {
        OWLObjectProperty property = getOWLDataFactory().getOWLObjectProperty(iri);
        return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(property);
      } else if (getOWLOntology().containsDataPropertyInSignature(iri)) {
        OWLDataProperty property = getOWLDataFactory().getOWLDataProperty(iri);
        return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(property);
      } else if (getOWLOntology().containsAnnotationPropertyInSignature(iri)) {
        OWLAnnotationProperty property = getOWLDataFactory().getOWLAnnotationProperty(iri);
        return getSWRLBuiltInArgumentFactory().getAnnotationPropertyBuiltInArgument(property);
      } else if (getOWLOntology().containsDatatypeInSignature(iri)) {
        OWLDatatype datatype = getOWLDataFactory().getOWLDatatype(iri);
        return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(datatype);
      } else {
        return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
      }
    } else {
      return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
    }
  }

  private boolean isSWRLBuiltInAtom(SWRLAtom atom) // TODO Check implementation of isSWRLBuiltIn atom
  {
    if (atom instanceof SWRLBuiltInAtom)
      return true;
    else {
      SWRLPredicate predicate = atom.getPredicate();
      if (predicate instanceof IRI) {
        IRI iri = (IRI)predicate;
        return isSWRLBuiltIn(iri);
      }
      return false;
    }
  }

  private boolean isURI(@NonNull OWLDatatype datatype)
  {
    return datatype.getIRI().equals(OWL2Datatype.XSD_ANY_URI.getIRI());
  }

  @NonNull private SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
  {
    return getSWRLAPIOWLDataFactory().getSWRLBuiltInArgumentFactory();
  }

  private void addDefaultSWRLBuiltIns()
  {
    addCoreSWRLBuiltIns();
    addSQWRLBuiltIns();
    addTemporalBuiltIns();
    addSWRLXBuiltIns();
    addSWRLMBuiltIns();
  }

  private void addCoreSWRLBuiltIns()
  {
    String prefix = "http://www.w3.org/2003/11/swrlb#";

    addSWRLBuiltIn(IRI.create(prefix, "equal"));
    addSWRLBuiltIn(IRI.create(prefix, "notEqual"));
    addSWRLBuiltIn(IRI.create(prefix, "lessThan"));
    addSWRLBuiltIn(IRI.create(prefix, "lessThanOrEqual"));
    addSWRLBuiltIn(IRI.create(prefix, "greaterThan"));
    addSWRLBuiltIn(IRI.create(prefix, "greaterThanOrEqual"));
    addSWRLBuiltIn(IRI.create(prefix, "add"));
    addSWRLBuiltIn(IRI.create(prefix, "subtract"));
    addSWRLBuiltIn(IRI.create(prefix, "multiply"));
    addSWRLBuiltIn(IRI.create(prefix, "divide"));
    addSWRLBuiltIn(IRI.create(prefix, "integerDivide"));
    addSWRLBuiltIn(IRI.create(prefix, "mod"));
    addSWRLBuiltIn(IRI.create(prefix, "pow"));
    addSWRLBuiltIn(IRI.create(prefix, "unaryPlus"));
    addSWRLBuiltIn(IRI.create(prefix, "unaryMinus"));
    addSWRLBuiltIn(IRI.create(prefix, "abs"));
    addSWRLBuiltIn(IRI.create(prefix, "ceiling"));
    addSWRLBuiltIn(IRI.create(prefix, "floor"));
    addSWRLBuiltIn(IRI.create(prefix, "round"));
    addSWRLBuiltIn(IRI.create(prefix, "roundHalfToEven"));
    addSWRLBuiltIn(IRI.create(prefix, "sin"));
    addSWRLBuiltIn(IRI.create(prefix, "cos"));
    addSWRLBuiltIn(IRI.create(prefix, "tan"));
    addSWRLBuiltIn(IRI.create(prefix, "booleanNot"));
    addSWRLBuiltIn(IRI.create(prefix, "stringEqualIgnoreCase"));
    addSWRLBuiltIn(IRI.create(prefix, "stringConcat"));
    addSWRLBuiltIn(IRI.create(prefix, "substring"));
    addSWRLBuiltIn(IRI.create(prefix, "stringLength"));
    addSWRLBuiltIn(IRI.create(prefix, "normalizeSpace"));
    addSWRLBuiltIn(IRI.create(prefix, "upperCase"));
    addSWRLBuiltIn(IRI.create(prefix, "lowerCase"));
    addSWRLBuiltIn(IRI.create(prefix, "translate"));
    addSWRLBuiltIn(IRI.create(prefix, "contains"));
    addSWRLBuiltIn(IRI.create(prefix, "containsIgnoreCase"));
    addSWRLBuiltIn(IRI.create(prefix, "startsWith"));
    addSWRLBuiltIn(IRI.create(prefix, "endsWith"));
    addSWRLBuiltIn(IRI.create(prefix, "substringBefore"));
    addSWRLBuiltIn(IRI.create(prefix, "substringAfter"));
    addSWRLBuiltIn(IRI.create(prefix, "matches"));
    addSWRLBuiltIn(IRI.create(prefix, "replace"));
    addSWRLBuiltIn(IRI.create(prefix, "yearMonthDuration"));
    addSWRLBuiltIn(IRI.create(prefix, "dayTimeDuration"));
    addSWRLBuiltIn(IRI.create(prefix, "dateTime"));
    addSWRLBuiltIn(IRI.create(prefix, "date"));
    addSWRLBuiltIn(IRI.create(prefix, "time"));
    addSWRLBuiltIn(IRI.create(prefix, "addYearMonthDurations"));
    addSWRLBuiltIn(IRI.create(prefix, "subtractYearMonthDurations"));
    addSWRLBuiltIn(IRI.create(prefix, "multiplyYearMonthDuration"));
    addSWRLBuiltIn(IRI.create(prefix, "divideYearMonthDuration"));
    addSWRLBuiltIn(IRI.create(prefix, "addDayTimeDurations"));
    addSWRLBuiltIn(IRI.create(prefix, "subtractDayTimeDurations"));
    addSWRLBuiltIn(IRI.create(prefix, "multiplyDayTimeDuration"));
    addSWRLBuiltIn(IRI.create(prefix, "divideDayTimeDuration"));
    addSWRLBuiltIn(IRI.create(prefix, "subtractDates"));
    addSWRLBuiltIn(IRI.create(prefix, "subtractTimes"));
    addSWRLBuiltIn(IRI.create(prefix, "addYearMonthDurationToDateTime"));
    addSWRLBuiltIn(IRI.create(prefix, "addDayTimeDurationToDateTime"));
    addSWRLBuiltIn(IRI.create(prefix, "subtractYearMonthDurationFromDateTime"));
    addSWRLBuiltIn(IRI.create(prefix, "subtractDayTimeDurationFromDateTime"));
    addSWRLBuiltIn(IRI.create(prefix, "addYearMonthDurationToDate"));
    addSWRLBuiltIn(IRI.create(prefix, "addDayTimeDurationToDate"));
    addSWRLBuiltIn(IRI.create(prefix, "subtractYearMonthDurationFromDate"));
    addSWRLBuiltIn(IRI.create(prefix, "subtractDayTimeDurationFromDate"));
    addSWRLBuiltIn(IRI.create(prefix, "addDayTimeDurationToTime"));
    addSWRLBuiltIn(IRI.create(prefix, "subtractDayTimeDurationFromTime"));
    addSWRLBuiltIn(IRI.create(prefix, "subtractDateTimesYieldingYearMonthDuration"));
    addSWRLBuiltIn(IRI.create(prefix, "subtractDateTimesYieldingYearMonthDuration"));
    addSWRLBuiltIn(IRI.create(prefix, "resolveURI"));
    addSWRLBuiltIn(IRI.create(prefix, "anyURU"));
    addSWRLBuiltIn(IRI.create(prefix, "listConcat"));
    addSWRLBuiltIn(IRI.create(prefix, "listIntersection"));
    addSWRLBuiltIn(IRI.create(prefix, "listSubtraction"));
    addSWRLBuiltIn(IRI.create(prefix, "member"));
    addSWRLBuiltIn(IRI.create(prefix, "length"));
    addSWRLBuiltIn(IRI.create(prefix, "first"));
    addSWRLBuiltIn(IRI.create(prefix, "rest"));
    addSWRLBuiltIn(IRI.create(prefix, "sublist"));
    addSWRLBuiltIn(IRI.create(prefix, "empty"));
  }

  private void addSQWRLBuiltIns()
  {
    String prefix = "http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#";

    addSWRLBuiltIn(IRI.create(prefix, "selectDistinct"));
    addSWRLBuiltIn(IRI.create(prefix, "select"));
    addSWRLBuiltIn(IRI.create(prefix, "count"));
    addSWRLBuiltIn(IRI.create(prefix, "columnNames"));
    addSWRLBuiltIn(IRI.create(prefix, "orderBy"));
    addSWRLBuiltIn(IRI.create(prefix, "orderByDescending"));
    addSWRLBuiltIn(IRI.create(prefix, "limit"));
    addSWRLBuiltIn(IRI.create(prefix, "min"));
    addSWRLBuiltIn(IRI.create(prefix, "max"));
    addSWRLBuiltIn(IRI.create(prefix, "avg"));
    addSWRLBuiltIn(IRI.create(prefix, "sum"));
    addSWRLBuiltIn(IRI.create(prefix, "median"));
    addSWRLBuiltIn(IRI.create(prefix, "makeSet"));
    addSWRLBuiltIn(IRI.create(prefix, "makeBag"));
    addSWRLBuiltIn(IRI.create(prefix, "groupBy"));
    addSWRLBuiltIn(IRI.create(prefix, "size"));
    addSWRLBuiltIn(IRI.create(prefix, "isEmpty"));
    addSWRLBuiltIn(IRI.create(prefix, "notEmpty"));
    addSWRLBuiltIn(IRI.create(prefix, "element"));
    addSWRLBuiltIn(IRI.create(prefix, "notElement"));
    addSWRLBuiltIn(IRI.create(prefix, "intersects"));
    addSWRLBuiltIn(IRI.create(prefix, "notIntersects"));
    addSWRLBuiltIn(IRI.create(prefix, "equal"));
    addSWRLBuiltIn(IRI.create(prefix, "notEqual"));
    addSWRLBuiltIn(IRI.create(prefix, "contains"));
    addSWRLBuiltIn(IRI.create(prefix, "notContains"));
    addSWRLBuiltIn(IRI.create(prefix, "difference"));
    addSWRLBuiltIn(IRI.create(prefix, "union"));
    addSWRLBuiltIn(IRI.create(prefix, "intersection"));
    addSWRLBuiltIn(IRI.create(prefix, "append"));
    addSWRLBuiltIn(IRI.create(prefix, "last"));
    addSWRLBuiltIn(IRI.create(prefix, "notLast"));
    addSWRLBuiltIn(IRI.create(prefix, "lastN"));
    addSWRLBuiltIn(IRI.create(prefix, "notLastN"));
    addSWRLBuiltIn(IRI.create(prefix, "first"));
    addSWRLBuiltIn(IRI.create(prefix, "notFirst"));
    addSWRLBuiltIn(IRI.create(prefix, "firstN"));
    addSWRLBuiltIn(IRI.create(prefix, "notFirstN"));
    addSWRLBuiltIn(IRI.create(prefix, "nth"));
    addSWRLBuiltIn(IRI.create(prefix, "notNth"));
    addSWRLBuiltIn(IRI.create(prefix, "nthLast"));
    addSWRLBuiltIn(IRI.create(prefix, "notNthLast"));
    addSWRLBuiltIn(IRI.create(prefix, "nthSlice"));
    addSWRLBuiltIn(IRI.create(prefix, "notNthSlice"));
    addSWRLBuiltIn(IRI.create(prefix, "nthLastSlice"));
    addSWRLBuiltIn(IRI.create(prefix, "notNthLastSlice"));
    addSWRLBuiltIn(IRI.create(prefix, "greatest"));
    addSWRLBuiltIn(IRI.create(prefix, "notGreatest"));
    addSWRLBuiltIn(IRI.create(prefix, "greatestN"));
    addSWRLBuiltIn(IRI.create(prefix, "notGreatestN"));
    addSWRLBuiltIn(IRI.create(prefix, "least"));
    addSWRLBuiltIn(IRI.create(prefix, "notLeast"));
    addSWRLBuiltIn(IRI.create(prefix, "leastN"));
    addSWRLBuiltIn(IRI.create(prefix, "notLeastN"));
    addSWRLBuiltIn(IRI.create(prefix, "nthGreatest"));
    addSWRLBuiltIn(IRI.create(prefix, "notNthGreatest"));
    addSWRLBuiltIn(IRI.create(prefix, "nthGreatestSlice"));
    addSWRLBuiltIn(IRI.create(prefix, "notNthGreatestSlice"));
  }

  private void addTemporalBuiltIns()
  {
    String prefix = "http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl#";

    addSWRLBuiltIn(IRI.create(prefix, "notEquals"));
    addSWRLBuiltIn(IRI.create(prefix, "notIntersects"));
    addSWRLBuiltIn(IRI.create(prefix, "notStarts"));
    addSWRLBuiltIn(IRI.create(prefix, "overlappedBy"));
    addSWRLBuiltIn(IRI.create(prefix, "contains"));
    addSWRLBuiltIn(IRI.create(prefix, "equals"));
    addSWRLBuiltIn(IRI.create(prefix, "intersects"));
    addSWRLBuiltIn(IRI.create(prefix, "finishedBy"));
    addSWRLBuiltIn(IRI.create(prefix, "notDurationLessThanOrEqualTo"));
    addSWRLBuiltIn(IRI.create(prefix, "notStartedBy"));
    addSWRLBuiltIn(IRI.create(prefix, "notFinishedBy"));
    addSWRLBuiltIn(IRI.create(prefix, "starts"));
    addSWRLBuiltIn(IRI.create(prefix, "notContains"));
    addSWRLBuiltIn(IRI.create(prefix, "notOverlaps"));
    addSWRLBuiltIn(IRI.create(prefix, "durationLessThanOrEqualTo"));
    addSWRLBuiltIn(IRI.create(prefix, "duration"));
    addSWRLBuiltIn(IRI.create(prefix, "notFinishes"));
    addSWRLBuiltIn(IRI.create(prefix, "metBy"));
    addSWRLBuiltIn(IRI.create(prefix, "notDurationEqualTo"));
    addSWRLBuiltIn(IRI.create(prefix, "before"));
    addSWRLBuiltIn(IRI.create(prefix, "startedBy"));
    addSWRLBuiltIn(IRI.create(prefix, "notMeets"));
    addSWRLBuiltIn(IRI.create(prefix, "durationGreaterThanOrEqualTo"));
    addSWRLBuiltIn(IRI.create(prefix, "notDuring"));
    addSWRLBuiltIn(IRI.create(prefix, "notOverlappedBy"));
    addSWRLBuiltIn(IRI.create(prefix, "during"));
    addSWRLBuiltIn(IRI.create(prefix, "notDurationLessThan"));
    addSWRLBuiltIn(IRI.create(prefix, "notBefore"));
    addSWRLBuiltIn(IRI.create(prefix, "meets"));
    addSWRLBuiltIn(IRI.create(prefix, "notDurationGreaterThan"));
    addSWRLBuiltIn(IRI.create(prefix, "notDurationGreaterThanOrEqualTo"));
    addSWRLBuiltIn(IRI.create(prefix, "add"));
    addSWRLBuiltIn(IRI.create(prefix, "finishes"));
    addSWRLBuiltIn(IRI.create(prefix, "notAfter"));
    addSWRLBuiltIn(IRI.create(prefix, "durationEqualTo"));
    addSWRLBuiltIn(IRI.create(prefix, "overlaps"));
    addSWRLBuiltIn(IRI.create(prefix, "durationGreaterThan"));
    addSWRLBuiltIn(IRI.create(prefix, "durationLessThan"));
    addSWRLBuiltIn(IRI.create(prefix, "after"));
    addSWRLBuiltIn(IRI.create(prefix, "notMetBy"));
  }

  private void addSWRLXBuiltIns()
  {
    String prefix = "http://swrl.stanford.edu/ontologies/built-ins/3.3/swrlx.owl#";

    addSWRLBuiltIn(IRI.create(prefix, "makeOWLClass"));
    addSWRLBuiltIn(IRI.create(prefix, "makeOWLIndividual"));
    addSWRLBuiltIn(IRI.create(prefix, "makeOWLThing"));
    addSWRLBuiltIn(IRI.create(prefix, "createOWLThing"));
    addSWRLBuiltIn(IRI.create(prefix, "invokeSWRLBuiltIn"));
  }

  private void addSWRLMBuiltIns()
  {
    String prefix = "http://swrl.stanford.edu/ontologies/built-ins/3.4/swrlm.owl#";

    addSWRLBuiltIn(IRI.create(prefix, "sqrt"));
    addSWRLBuiltIn(IRI.create(prefix, "eval"));
    addSWRLBuiltIn(IRI.create(prefix, "log"));
  }

  private void addSWRLRule(@NonNull SWRLAPIRule swrlapiRule, @NonNull SWRLRule owlapiRule)
  {
    String ruleName = swrlapiRule.getRuleName();

    this.swrlRules.put(ruleName, swrlapiRule);
    this.owlapiRules.put(ruleName, owlapiRule);
    this.assertedOWLAxioms.add(swrlapiRule);

    this.ontology.getOWLOntologyManager().addAxiom(this.ontology, owlapiRule);
  }

  /**
   * Process currently supported OWL axioms. The processing consists of recording any OWL properties in the processed
   * axioms (with an instance of the {@link DefaultIRIResolver} class) and generating declaration
   * axioms for these properties.
   * <p>
   * TODO The current approach is clunky. A better approach would be to walk the axioms with a visitor and recordOWLClassExpression the
   * properties and generate the declaration axioms.
   */
  private void processOWLAxioms()
  {
    processOWLClassDeclarationAxioms();
    processOWLIndividualDeclarationAxioms();
    processOWLObjectPropertyDeclarationAxioms();
    processOWLDataPropertyDeclarationAxioms();
    processOWLAnnotationPropertyDeclarationAxioms();
    processOWLClassAssertionAxioms();
    processOWLObjectPropertyAssertionAxioms();
    processOWLDataPropertyAssertionAxioms();
    processOWLSameIndividualAxioms();
    processOWLDifferentIndividualsAxioms();
    processOWLSubClassOfAxioms();
    processOWLEquivalentClassesAxioms();
    processOWLSubObjectPropertyOfAxioms();
    processOWLSubDataPropertyOfAxioms();
    processOWLEquivalentDataPropertiesAxioms();
    processOWLEquivalentObjectPropertiesAxioms();
    processOWLTransitiveObjectPropertyAxioms();
    processOWLSymmetricObjectPropertyAxioms();
    processOWLFunctionalObjectPropertyAxioms();
    processOWLInverseFunctionalObjectPropertyAxioms();
    processOWLFunctionalDataPropertyAxioms();
    processOWLObjectPropertyDomainAxioms();
    processOWLDataPropertyDomainAxioms();
    processOWLObjectPropertyRangeAxioms();
    processOWLDataPropertyRangeAxioms();
    processOWLInverseObjectPropertiesAxioms();
    processOWLIrreflexiveObjectPropertyAxioms();
    processOWLAsymmetricObjectPropertyAxioms();
    processOWLDisjointObjectPropertiesAxioms();
    processOWLDisjointDataPropertiesAxioms();
  }

  private void processOWLClassAssertionAxioms()
  {
    for (OWLClassAssertionAxiom axiom : getOWLClassAssertionAxioms()) {
      generateOWLIndividualDeclarationAxiomIfNecessary(axiom.getIndividual());
      this.assertedOWLAxioms.add(axiom);
    }
  }

  private void processOWLObjectPropertyAssertionAxioms()
  {
    for (OWLObjectPropertyAssertionAxiom axiom : getOWLObjectPropertyAssertionAxioms()) {
      generateOWLIndividualDeclarationAxiomIfNecessary(axiom.getSubject());
      generateOWLIndividualDeclarationAxiomIfNecessary(axiom.getObject());
      this.assertedOWLAxioms.add(axiom);
    }
  }

  private void processOWLDataPropertyAssertionAxioms()
  {
    for (OWLDataPropertyAssertionAxiom axiom : getOWLDataPropertyAssertionAxioms()) {
      generateOWLIndividualDeclarationAxiomIfNecessary(axiom.getSubject());
      this.assertedOWLAxioms.add(axiom);
    }
  }

  private void processOWLClassDeclarationAxioms()
  {
    for (OWLDeclarationAxiom axiom : getOWLClassDeclarationAxioms()) {
      OWLEntity cls = axiom.getEntity();
      this.classDeclarationAxioms.put(cls.getIRI(), axiom);
      this.assertedOWLAxioms.add(axiom);
    }
  }

  private void processOWLIndividualDeclarationAxioms()
  {
    for (OWLDeclarationAxiom axiom : getOWLIndividualDeclarationAxioms()) {
      OWLEntity individual = axiom.getEntity();
      this.individualDeclarationAxioms.put(individual.getIRI(), axiom);
      this.assertedOWLAxioms.add(axiom);
    }
  }

  private void processOWLObjectPropertyDeclarationAxioms()
  {
    for (OWLDeclarationAxiom axiom : getOWLObjectPropertyDeclarationAxioms()) {
      OWLEntity property = axiom.getEntity();
      this.objectPropertyDeclarationAxioms.put(property.getIRI(), axiom);
      this.assertedOWLAxioms.add(axiom);
    }
  }

  private void processOWLDataPropertyDeclarationAxioms()
  {
    for (OWLDeclarationAxiom axiom : getOWLDataPropertyDeclarationAxioms()) {
      OWLEntity property = axiom.getEntity();

      this.dataPropertyDeclarationAxioms.put(property.getIRI(), axiom);
      this.assertedOWLAxioms.add(axiom);
    }
  }

  private void processOWLAnnotationPropertyDeclarationAxioms()
  {
    for (OWLDeclarationAxiom axiom : getOWLAnnotationPropertyDeclarationAxioms()) {
      OWLEntity property = axiom.getEntity();

      this.annotationPropertyDeclarationAxioms.put(property.getIRI(), axiom);
      this.assertedOWLAxioms.add(axiom);
    }
  }

  private void processOWLSameIndividualAxioms()
  {
    Set<@NonNull OWLSameIndividualAxiom> axioms = getOWLSameIndividualAxioms();
    for (OWLSameIndividualAxiom axiom : axioms) {
      axiom.getIndividuals().forEach(this::generateOWLIndividualDeclarationAxiomIfNecessary);
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLDifferentIndividualsAxioms()
  {
    Set<@NonNull OWLDifferentIndividualsAxiom> axioms = getOWLDifferentIndividualsAxioms();
    for (OWLDifferentIndividualsAxiom axiom : axioms) {
      axiom.getIndividuals().forEach(this::generateOWLIndividualDeclarationAxiomIfNecessary);
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLSubClassOfAxioms()
  {
    Set<@NonNull OWLSubClassOfAxiom> axioms = getOWLSubClassOfAxioms();
    for (OWLSubClassOfAxiom axiom : axioms) {
      generateOWLClassDeclarationAxiomIfNecessary(axiom.getSubClass());
      generateOWLClassDeclarationAxiomIfNecessary(axiom.getSuperClass());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLEquivalentClassesAxioms()
  {
    Set<@NonNull OWLEquivalentClassesAxiom> axioms = getOWLEquivalentClassesAxioms();
    for (OWLEquivalentClassesAxiom axiom : axioms) {
      axiom.getNamedClasses().forEach(this::generateOWLClassDeclarationAxiom);
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLSubObjectPropertyOfAxioms()
  {
    Set<@NonNull OWLSubObjectPropertyOfAxiom> axioms = getOWLSubObjectPropertyOfAxioms();
    for (OWLSubObjectPropertyOfAxiom axiom : axioms) {
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getSubProperty());
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getSuperProperty());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLSubDataPropertyOfAxioms()
  {
    Set<@NonNull OWLSubDataPropertyOfAxiom> axioms = getOWLSubDataPropertyOfAxioms();
    for (OWLSubDataPropertyOfAxiom axiom : axioms) {
      generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getSubProperty());
      generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getSuperProperty());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLTransitiveObjectPropertyAxioms()
  {
    Set<@NonNull OWLTransitiveObjectPropertyAxiom> axioms = getOWLTransitiveObjectPropertyAxioms();
    for (OWLTransitiveObjectPropertyAxiom axiom : axioms)
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLSymmetricObjectPropertyAxioms()
  {
    Set<@NonNull OWLSymmetricObjectPropertyAxiom> axioms = getOWLSymmetricObjectPropertyAxioms();
    for (OWLSymmetricObjectPropertyAxiom axiom : axioms)
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLFunctionalObjectPropertyAxioms()
  {
    Set<@NonNull OWLFunctionalObjectPropertyAxiom> axioms = getOWLFunctionalObjectPropertyAxioms();
    for (OWLFunctionalObjectPropertyAxiom axiom : axioms)
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLInverseFunctionalObjectPropertyAxioms()
  {
    Set<@NonNull OWLInverseFunctionalObjectPropertyAxiom> axioms = getOWLInverseFunctionalObjectPropertyAxioms();
    for (OWLInverseFunctionalObjectPropertyAxiom axiom : axioms)
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLFunctionalDataPropertyAxioms()
  {
    Set<@NonNull OWLFunctionalDataPropertyAxiom> axioms = getOWLFunctionalDataPropertyAxioms();
    for (OWLFunctionalDataPropertyAxiom axiom : axioms)
      generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLObjectPropertyDomainAxioms()
  {
    Set<@NonNull OWLObjectPropertyDomainAxiom> axioms = getOWLObjectPropertyDomainAxioms();
    for (OWLObjectPropertyDomainAxiom axiom : axioms) {
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
      generateOWLClassDeclarationAxiomIfNecessary(axiom.getDomain());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLDataPropertyDomainAxioms()
  {
    Set<@NonNull OWLDataPropertyDomainAxiom> axioms = getOWLDataPropertyDomainAxioms();
    for (OWLDataPropertyDomainAxiom axiom : axioms) {
      generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
      generateOWLClassDeclarationAxiomIfNecessary(axiom.getDomain());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLObjectPropertyRangeAxioms()
  {
    Set<@NonNull OWLObjectPropertyRangeAxiom> axioms = getOWLObjectPropertyRangeAxioms();
    for (OWLObjectPropertyRangeAxiom axiom : axioms) {
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
      generateOWLClassDeclarationAxiomIfNecessary(axiom.getRange());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLDataPropertyRangeAxioms()
  {
    Set<@NonNull OWLDataPropertyRangeAxiom> axioms = getOWLDataPropertyRangeAxioms();
    for (OWLDataPropertyRangeAxiom axiom : axioms) {
      generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLIrreflexiveObjectPropertyAxioms()
  {
    Set<@NonNull OWLIrreflexiveObjectPropertyAxiom> axioms = getOWLIrreflexiveObjectPropertyAxioms();
    for (OWLIrreflexiveObjectPropertyAxiom axiom : axioms)
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLAsymmetricObjectPropertyAxioms()
  {
    Set<@NonNull OWLAsymmetricObjectPropertyAxiom> axioms = getOWLAsymmetricObjectPropertyAxioms();
    for (OWLAsymmetricObjectPropertyAxiom axiom : axioms)
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLEquivalentObjectPropertiesAxioms()
  {
    Set<@NonNull OWLEquivalentObjectPropertiesAxiom> axioms = getOWLEquivalentObjectPropertiesAxioms();
    for (OWLEquivalentObjectPropertiesAxiom axiom : axioms) {
      axiom.getProperties().forEach(this::generateOWLObjectPropertyDeclarationAxiomIfNecessary);
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLEquivalentDataPropertiesAxioms()
  {
    Set<@NonNull OWLEquivalentDataPropertiesAxiom> axioms = getOWLEquivalentDataPropertiesAxioms();
    for (OWLEquivalentDataPropertiesAxiom axiom : axioms) {
      axiom.getProperties().forEach(this::generateOWLDataPropertyDeclarationAxiomIfNecessary);
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLInverseObjectPropertiesAxioms()
  {
    Set<@NonNull OWLInverseObjectPropertiesAxiom> axioms = getOWLInverseObjectPropertiesAxioms();
    for (OWLInverseObjectPropertiesAxiom axiom : axioms) {
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getFirstProperty());
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getSecondProperty());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLDisjointObjectPropertiesAxioms()
  {
    Set<@NonNull OWLDisjointObjectPropertiesAxiom> axioms = getOWLDisjointObjectPropertiesAxioms();
    for (OWLDisjointObjectPropertiesAxiom axiom : axioms) {
      axiom.getProperties().forEach(this::generateOWLObjectPropertyDeclarationAxiomIfNecessary);
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLDisjointDataPropertiesAxioms()
  {
    Set<@NonNull OWLDisjointDataPropertiesAxiom> axioms = getOWLDisjointDataPropertiesAxioms();
    for (OWLDisjointDataPropertiesAxiom axiom : axioms) {
      axiom.getProperties().forEach(this::generateOWLDataPropertyDeclarationAxiomIfNecessary);
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void generateOWLClassDeclarationAxiom(@NonNull OWLClass cls)
  {
    if (!this.classDeclarationAxioms.containsKey(cls.getIRI())) {
      OWLDeclarationAxiom axiom = getSWRLAPIOWLDataFactory().getOWLClassDeclarationAxiom(cls);
      this.classDeclarationAxioms.put(cls.getIRI(), axiom);
      this.assertedOWLAxioms.add(axiom);
    }
  }

  private void generateOWLClassDeclarationAxiomIfNecessary(@NonNull OWLClassExpression classExpression)
  {
    if (classExpression instanceof OWLClass) {
      OWLClass cls = (OWLClass)classExpression;
      generateOWLClassDeclarationAxiom(cls);
    }
  }

  private void generateOWLIndividualDeclarationAxiomIfNecessary(@NonNull OWLIndividual individual)
  {
    if (individual.isNamed() && !this.individualDeclarationAxioms
      .containsKey(individual.asOWLNamedIndividual().getIRI())) {
      OWLDeclarationAxiom axiom = getSWRLAPIOWLDataFactory()
        .getOWLIndividualDeclarationAxiom(individual.asOWLNamedIndividual());
      this.individualDeclarationAxioms.put(individual.asOWLNamedIndividual().getIRI(), axiom);
      this.assertedOWLAxioms.add(axiom);
    }
  }

  private void generateOWLObjectPropertyDeclarationAxiomIfNecessary(
    @NonNull OWLObjectPropertyExpression propertyExpression)
  {
    if (propertyExpression instanceof OWLObjectProperty) {
      OWLObjectProperty property = (OWLObjectProperty)propertyExpression;
      if (!this.objectPropertyDeclarationAxioms.containsKey(property.getIRI())) {
        OWLDeclarationAxiom axiom = getSWRLAPIOWLDataFactory().getOWLObjectPropertyDeclarationAxiom(property);
        this.objectPropertyDeclarationAxioms.put(property.getIRI(), axiom);
        this.assertedOWLAxioms.add(axiom);
      }
    }
  }

  private void generateOWLDataPropertyDeclarationAxiomIfNecessary(@NonNull OWLDataPropertyExpression propertyExpression)
  {
    if (propertyExpression instanceof OWLDataProperty) {
      OWLDataProperty property = (OWLDataProperty)propertyExpression;
      if (!this.dataPropertyDeclarationAxioms.containsKey(property.getIRI())) {
        OWLDeclarationAxiom axiom = getSWRLAPIOWLDataFactory().getOWLDataPropertyDeclarationAxiom(property);
        this.dataPropertyDeclarationAxioms.put(property.getIRI(), axiom);
        this.assertedOWLAxioms.add(axiom);
      }
    }
  }

  @NonNull private Set<@NonNull OWLSameIndividualAxiom> getOWLSameIndividualAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SAME_INDIVIDUAL, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLDifferentIndividualsAxiom> getOWLDifferentIndividualsAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DIFFERENT_INDIVIDUALS, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLSubObjectPropertyOfAxiom> getOWLSubObjectPropertyOfAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SUB_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLSubDataPropertyOfAxiom> getOWLSubDataPropertyOfAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SUB_DATA_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLEquivalentClassesAxiom> getOWLEquivalentClassesAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.EQUIVALENT_CLASSES, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLClassAssertionAxiom> getOWLClassAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.CLASS_ASSERTION, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DATA_PROPERTY_ASSERTION, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLSubClassOfAxiom> getOWLSubClassOfAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SUBCLASS_OF, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<@NonNull OWLDisjointClassesAxiom> getOWLDisjointClassesAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DISJOINT_CLASSES, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLEquivalentDataPropertiesAxiom> getOWLEquivalentDataPropertiesAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.EQUIVALENT_DATA_PROPERTIES, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLEquivalentObjectPropertiesAxiom> getOWLEquivalentObjectPropertiesAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLDisjointDataPropertiesAxiom> getOWLDisjointDataPropertiesAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DISJOINT_DATA_PROPERTIES, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLDisjointObjectPropertiesAxiom> getOWLDisjointObjectPropertiesAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DISJOINT_OBJECT_PROPERTIES, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLObjectPropertyDomainAxiom> getOWLObjectPropertyDomainAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLDataPropertyDomainAxiom> getOWLDataPropertyDomainAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DATA_PROPERTY_DOMAIN, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLObjectPropertyRangeAxiom> getOWLObjectPropertyRangeAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.OBJECT_PROPERTY_RANGE, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLDataPropertyRangeAxiom> getOWLDataPropertyRangeAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DATA_PROPERTY_RANGE, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLFunctionalObjectPropertyAxiom> getOWLFunctionalObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.FUNCTIONAL_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLFunctionalDataPropertyAxiom> getOWLFunctionalDataPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.FUNCTIONAL_DATA_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLIrreflexiveObjectPropertyAxiom> getOWLIrreflexiveObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.IRREFLEXIVE_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLInverseFunctionalObjectPropertyAxiom> getOWLInverseFunctionalObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLTransitiveObjectPropertyAxiom> getOWLTransitiveObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.TRANSITIVE_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLSymmetricObjectPropertyAxiom> getOWLSymmetricObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SYMMETRIC_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLAsymmetricObjectPropertyAxiom> getOWLAsymmetricObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.ASYMMETRIC_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLInverseObjectPropertiesAxiom> getOWLInverseObjectPropertiesAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.INVERSE_OBJECT_PROPERTIES, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLNegativeDataPropertyAssertionAxiom> getOWLNegativeDataPropertyAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.NEGATIVE_DATA_PROPERTY_ASSERTION, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLNegativeObjectPropertyAssertionAxiom> getOWLNegativeObjectPropertyAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLReflexiveObjectPropertyAxiom> getOWLReflexiveObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.REFLEXIVE_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLDisjointUnionAxiom> getOWLDisjointUnionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DISJOINT_UNION, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLAnnotationAssertionAxiom> getOWLAnnotationAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.ANNOTATION_ASSERTION, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLSubPropertyChainOfAxiom> getOWLSubPropertyChainOfAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLHasKeyAxiom> getOWLHasKeyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.HAS_KEY, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<@NonNull OWLDatatypeDefinitionAxiom> getOWLDatatypeDefinitionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DATATYPE_DEFINITION, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<@NonNull OWLAnnotationPropertyRangeAxiom> getOWLAnnotationPropertyRangeAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.ANNOTATION_PROPERTY_RANGE, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<@NonNull OWLAnnotationPropertyDomainAxiom> getOWLAnnotationPropertyDomainAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.ANNOTATION_PROPERTY_DOMAIN, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<@NonNull OWLSubAnnotationPropertyOfAxiom> getOWLSubAnnotationPropertyOfAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF, Imports.INCLUDED);
  }

  @NonNull private Set<@NonNull OWLDeclarationAxiom> getOWLClassDeclarationAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DECLARATION, Imports.INCLUDED).stream()
      .filter(owlDeclarationAxiom -> owlDeclarationAxiom.getEntity().isOWLClass()).collect(Collectors.toSet());
  }

  @NonNull private Set<@NonNull OWLDeclarationAxiom> getOWLIndividualDeclarationAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DECLARATION, Imports.INCLUDED).stream()
      .filter(owlDeclarationAxiom -> owlDeclarationAxiom.getEntity().isOWLNamedIndividual())
      .collect(Collectors.toSet());
  }

  @NonNull private Set<@NonNull OWLDeclarationAxiom> getOWLObjectPropertyDeclarationAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DECLARATION, Imports.INCLUDED).stream()
      .filter(owlDeclarationAxiom -> owlDeclarationAxiom.getEntity().isOWLObjectProperty()).collect(Collectors.toSet());
  }

  @NonNull private Set<@NonNull OWLDeclarationAxiom> getOWLDataPropertyDeclarationAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DECLARATION, Imports.INCLUDED).stream()
      .filter(owlDeclarationAxiom -> owlDeclarationAxiom.getEntity().isOWLDataProperty()).collect(Collectors.toSet());
  }

  @NonNull private Set<@NonNull OWLDeclarationAxiom> getOWLAnnotationPropertyDeclarationAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DECLARATION, Imports.INCLUDED).stream()
      .filter(owlDeclarationAxiom -> owlDeclarationAxiom.getEntity().isOWLAnnotationProperty())
      .collect(Collectors.toSet());
  }

  @Override public void ontologiesChanged(@Nonnull List<? extends OWLOntologyChange> list) throws OWLException
  {
    this.hasOntologyChanged = true;

    updatePrefixes(this.ontology, this.prefixManager);
  }

  private static void updatePrefixes(@NonNull OWLOntology ontology, @NonNull DefaultPrefixManager prefixManager)
  {
    OWLOntologyManager owlOntologyManager = ontology.getOWLOntologyManager();
    OWLDocumentFormat ontologyFormat = owlOntologyManager.getOntologyFormat(ontology);

    prefixManager.clear();

    if (ontologyFormat != null && ontologyFormat.isPrefixOWLOntologyFormat()) {
      PrefixDocumentFormat prefixOntologyFormat = ontologyFormat.asPrefixOWLOntologyFormat();

      Map<@NonNull String, String> map = prefixOntologyFormat.getPrefixName2PrefixMap();
      for (String prefix : map.keySet())
        prefixManager.setPrefix(prefix, map.get(prefix));

      if (prefixManager.getDefaultPrefix() == null) {
        OWLOntologyID ontologyID = ontology.getOntologyID();
        if (ontologyID.getOntologyIRI().isPresent()) {
          // TODO This is a quick hack!!
          String defaultPrefix = ontologyID.getOntologyIRI().get().toString() + "#";
          prefixManager.setDefaultPrefix(defaultPrefix);
        }
      }
    }
    addSWRLAPIPrefixes(prefixManager);

    //log.info("updated prefixes " + prefixManager.getPrefixName2PrefixMap());
  }

  private static void addSWRLAPIPrefixes(@NonNull DefaultPrefixManager prefixManager)
  {
    prefixManager.setPrefix("owl:", "http://www.w3.org/2002/07/owl#");
    prefixManager.setPrefix("swrl:", "http://www.w3.org/2003/11/swrl#");
    prefixManager.setPrefix("swrlb:", "http://www.w3.org/2003/11/swrlb#");
    prefixManager.setPrefix("sqwrl:", "http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#");
    prefixManager.setPrefix("swrlm:", "http://swrl.stanford.edu/ontologies/built-ins/3.4/swrlm.owl#");
    prefixManager.setPrefix("temporal:", "http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl#");
    prefixManager.setPrefix("swrlx:", "http://swrl.stanford.edu/ontologies/built-ins/3.3/swrlx.owl#");
    prefixManager.setPrefix("swrla:", "http://swrl.stanford.edu/ontologies/3.3/swrla.owl#");
  }

  private static void addSWRLAPIBuiltInOntologies(@NonNull OWLOntology ontology)
  {
    Map<@NonNull String, String> map = new HashMap<>();

    map.put("http://www.w3.org/2003/11/swrl#", resourceName2File("owl/swrl.owl"));
    map.put("http://www.w3.org/2003/11/swrlb#", resourceName2File("owl/swrlb.owl"));
    map.put("http://swrl.stanford.edu/ontologies/3.3/swrla.owl", resourceName2File("owl/swrla.owl"));
    map.put("http://swrl.stanford.edu/ontologies/built-ins/3.4/swrlm.owl", resourceName2File("owl/swrlm.owl"));
    map.put("http://swrl.stanford.edu/ontologies/built-ins/3.3/swrlx.owl", resourceName2File("owl/swrlx.owl"));
    map.put("http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl", resourceName2File("owl/temporal.owl"));
    map.put("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl", resourceName2File("owl/sqwrl.owl"));

    for (String key : map.keySet())
      ontology.getOWLOntologyManager().getIRIMappers()
        .add(new SimpleIRIMapper(IRI.create(key), IRI.create(map.get(key))));
  }

  // TODO This looks dodgy
  @NonNull private static String resourceName2File(@NonNull String resourceName)
  {
    ClassLoader classLoader = SWRLAPIFactory.class.getClassLoader();

    if (classLoader == null)
      throw new SWRLAPIException("Could not find class loader");

    URL url = classLoader.getResource(resourceName);
    if (url == null)
      throw new SWRLAPIException("Could not find resource " + resourceName);

    return "file:///" + url.getFile();
  }
}
