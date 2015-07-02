package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
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
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
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
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.exceptions.SWRLAPIInternalException;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLQueryRenderer;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.exceptions.SQWRLInvalidQueryNameException;
import org.swrlapi.ui.model.SWRLAutoCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class DefaultSWRLAPIOWLOntology implements SWRLAPIOWLOntology
{
  @NonNull private final OWLOntology ontology;
  @NonNull private final DefaultPrefixManager prefixManager;
  @NonNull private final IRIResolver iriResolver;
  @NonNull private final SWRLParser swrlParser;
  @NonNull private final Set<IRI> swrlBuiltInIRIs;
  @NonNull private final SWRLAPIOWLDataFactory swrlapiOWLDataFactory;

  @NonNull private final Map<String, SWRLAPIRule> swrlRules; // SWRL rules include SQWRL queries
  @NonNull private final Map<String, SWRLRule> owlapiRules; // SWRL rules include SQWRL queries
  @NonNull private final Map<String, SQWRLQuery> sqwrlQueries;

  @NonNull private final Set<OWLAxiom> assertedOWLAxioms; // All asserted OWL axioms extracted from the supplied ontology

  @NonNull private final Map<IRI, OWLDeclarationAxiom> owlClassDeclarationAxioms;
  @NonNull private final Map<IRI, OWLDeclarationAxiom> owlIndividualDeclarationAxioms;
  @NonNull private final Map<IRI, OWLDeclarationAxiom> owlObjectPropertyDeclarationAxioms;
  @NonNull private final Map<IRI, OWLDeclarationAxiom> owlDataPropertyDeclarationAxioms;
  @NonNull private final Map<IRI, OWLDeclarationAxiom> owlAnnotationPropertyDeclarationAxioms;

  public DefaultSWRLAPIOWLOntology(@NonNull OWLOntology ontology, @NonNull DefaultPrefixManager prefixManager)
  {
    this.ontology = ontology;
    this.prefixManager = prefixManager;
    this.iriResolver = new DefaultIRIResolver(this.prefixManager);
    this.swrlParser = new SWRLParser(this);
    this.swrlBuiltInIRIs = new HashSet<>();
    this.swrlapiOWLDataFactory = SWRLAPIFactory.createSWRLAPIOWLDataFactory(this.iriResolver);

    this.swrlRules = new HashMap<>();
    this.owlapiRules = new HashMap<>();
    this.sqwrlQueries = new HashMap<>();

    this.assertedOWLAxioms = new HashSet<>();

    this.owlClassDeclarationAxioms = new HashMap<>();
    this.owlIndividualDeclarationAxioms = new HashMap<>();
    this.owlObjectPropertyDeclarationAxioms = new HashMap<>();
    this.owlDataPropertyDeclarationAxioms = new HashMap<>();
    this.owlAnnotationPropertyDeclarationAxioms = new HashMap<>();

    addDefaultSWRLBuiltIns();
  }

  @Override public void processOntology() throws SQWRLException
  {
    reset();

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

    this.owlClassDeclarationAxioms.clear();
    this.owlIndividualDeclarationAxioms.clear();
    this.owlObjectPropertyDeclarationAxioms.clear();
    this.owlDataPropertyDeclarationAxioms.clear();
    this.owlAnnotationPropertyDeclarationAxioms.clear();
  }

  @NonNull @Override public SWRLAPIRule createSWRLRule(@NonNull String ruleName, @NonNull String rule)
    throws SWRLParseException
  {
    return createSWRLRule(ruleName, rule, "", true);
  }

  @NonNull @Override public SWRLAPIRule createSWRLRule(@NonNull String ruleName, @NonNull String rule,
    @NonNull String comment, boolean isActive) throws SWRLParseException
  {
    Optional<SWRLRule> owlapiRule = this.swrlParser.parseSWRLRule(rule, false, ruleName, comment);

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
    Optional<SWRLRule> owlapiRule = this.swrlParser.parseSWRLRule(queryText, false, queryName, comment);

    if (owlapiRule.isPresent()) {
      SWRLAPIRule swrlapiRule = convertOWLAPIRule2SWRLAPIRule(owlapiRule.get(), queryName, comment, isActive);

      addSWRLRule(swrlapiRule, owlapiRule.get()); // Adds rule to the underlying ontology

      if (swrlapiRule.isSQWRLQuery()) {
        SQWRLQuery query = createSQWRLQueryFromSWRLRule(swrlapiRule);
        this.sqwrlQueries.put(queryName, query);
        return query;
      } else
        throw new SWRLParseException(queryName + " is not a SQWRL query");
    } else
      throw new SWRLParseException("Unknown error - parser failed to generate a query");
  }

  @NonNull @Override public Set<SWRLAPIRule> getSWRLRules()
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
      Optional<String> ruleName = getRuleName(owlapiRule);
      boolean isActive = getIsActive(owlapiRule);
      String comment = getComment(owlapiRule);

      String finalRuleName = ruleName.isPresent() ? ruleName.get() : "R" + ++ruleNameIndex;

      SWRLAPIRule swrlapiRule = convertOWLAPIRule2SWRLAPIRule(owlapiRule, finalRuleName, comment, isActive);
      this.swrlRules.put(finalRuleName, swrlapiRule);
      this.owlapiRules.put(finalRuleName, owlapiRule);

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

  @NonNull @Override public SWRLAPIRule getSWRLRule(@NonNull String ruleName) throws SWRLRuleException
  {
    if (!this.swrlRules.containsKey(ruleName))
      throw new SWRLRuleException("invalid rule name " + ruleName);

    return this.swrlRules.get(ruleName);
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
    return this.owlClassDeclarationAxioms.values().size();
  }

  @Override public int getNumberOfOWLIndividualDeclarationAxioms()
  {
    return this.owlIndividualDeclarationAxioms.values().size();
  }

  @Override public int getNumberOfOWLObjectPropertyDeclarationAxioms()
  {
    return this.owlObjectPropertyDeclarationAxioms.size();
  }

  @Override public int getNumberOfOWLDataPropertyDeclarationAxioms()
  {
    return this.owlDataPropertyDeclarationAxioms.size();
  }

  @Override public int getNumberOfOWLAxioms()
  {
    return this.assertedOWLAxioms.size();
  }


  @NonNull @Override public Set<OWLAxiom> getOWLAxioms()
  {
    return Collections.unmodifiableSet(this.assertedOWLAxioms);
  }

  @Override public boolean hasAssertedOWLAxiom(@NonNull OWLAxiom axiom)
  {
    return this.assertedOWLAxioms.contains(axiom);
  }

  @NonNull @Override public Set<String> getSQWRLQueryNames()
  {
    return new HashSet<>(this.sqwrlQueries.keySet());
  }

  @NonNull @Override public Set<SQWRLQuery> getSQWRLQueries()
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
    SQWRLQuery query = SWRLAPIFactory
      .createSQWRLQuery(queryName, rule.getBodyAtoms(), rule.getHeadAtoms(), active, comment,
        getSWRLAPIOWLDataFactory().getLiteralFactory(), getIRIResolver());

    return query;
  }

  private Optional<String> getRuleName(@NonNull SWRLRule owlapiRule)
  {
    OWLAnnotationProperty labelAnnotation = getOWLDataFactory()
      .getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());

    for (OWLAnnotation annotation : owlapiRule.getAnnotations(labelAnnotation)) {
      if (annotation.getValue() instanceof OWLLiteral) {
        OWLLiteral literal = (OWLLiteral)annotation.getValue();
        return Optional.of(literal.getLiteral()); // TODO We just pick one for the moment
      }
    }
    // TODO Also look for swrla#ruleName annotation
    return Optional.empty();
  }

  private boolean getIsActive(@NonNull SWRLRule owlapiRule)
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

  @NonNull private String getComment(@NonNull SWRLRule owlapiRule)
  {
    OWLAnnotationProperty commentAnnotationProperty = getOWLDataFactory()
      .getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_COMMENT.getIRI());

    for (OWLAnnotation annotation : owlapiRule.getAnnotations(commentAnnotationProperty)) {
      if (annotation.getValue() instanceof OWLLiteral) {
        OWLLiteral literal = (OWLLiteral)annotation.getValue();
        return literal.getLiteral(); // TODO We just pick one for the moment
      }
    }
    return "";
  }

  @NonNull private Set<OWLAnnotation> generateRuleAnnotations(@NonNull String ruleName, String comment,
    boolean isEnabled)
  {
    OWLAnnotation labelAnnotation = getOWLDataFactory()
      .getOWLAnnotation(getOWLDataFactory().getRDFSLabel(), getOWLDataFactory().getOWLLiteral(ruleName));
    OWLAnnotation commentAnnotation = getOWLDataFactory()
      .getOWLAnnotation(getOWLDataFactory().getRDFSComment(), getOWLDataFactory().getOWLLiteral(""));
    // TODO Add isEnabled annotation to rule
    Set<OWLAnnotation> annotations = new HashSet<>();

    annotations.add(labelAnnotation);
    annotations.add(commentAnnotation);

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
    return true; // TODO implement hasOntologyChanged
  }

  @Override public void resetOntologyChanged()
  {
    // TODO implement resetOntologyChanged
  }

  // void addRuleNameAnnotation(SWRLRule rule, String ruleName)
  // {
  // OWLAnnotationProperty labelAnnotationProperty = getOWLDataFactory().getRDFSLabel();
  // OWLLiteral label = getOWLDataFactory().getOWLLiteral(ruleName, "en");
  // OWLAnnotation labelAnnotation = getOWLDataFactory().getOWLAnnotation(labelAnnotationProperty, label);
  //
  // OWLAxiom annotationAssertionAxiom = getOWLDataFactory().getOWLAnnotationAssertionAxiom(rule, labelAnnotation);
  // this.ontologyManager.applyChange(new AddAxiom(this.ontology, annotationAssertionAxiom));
  // }

  @Override public boolean isSWRLBuiltIn(IRI iri)
  {
    return this.swrlBuiltInIRIs.contains(iri);
  }

  @Override public void addSWRLBuiltIn(IRI iri)
  {
    this.swrlBuiltInIRIs.add(iri);
  }

  @NonNull @Override public Set<IRI> getSWRLBuiltInIRIs()
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
    List<SWRLAtom> owlapiBodyAtoms = new ArrayList<>(owlapiRule.getBody());
    List<SWRLAtom> owlapiHeadAtoms = new ArrayList<>(owlapiRule.getHead());
    List<SWRLAtom> swrlapiBodyAtoms = new ArrayList<>();
    List<SWRLAtom> swrlapiHeadAtoms = new ArrayList<>();

    for (SWRLAtom atom : owlapiBodyAtoms) {
      if (isSWRLBuiltInAtom(atom)) {
        SWRLBuiltInAtom builtInAtom = (SWRLBuiltInAtom)atom;
        IRI builtInIRI = builtInAtom.getPredicate();
        String builtInPrefixedName = getIRIResolver().iri2PrefixedName(builtInIRI);
        List<SWRLDArgument> swrlDArguments = builtInAtom.getArguments();
        List<SWRLBuiltInArgument> swrlBuiltInArguments = convertSWRLDArguments2SWRLBuiltInArguments(swrlDArguments);
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
        List<SWRLDArgument> swrlDArguments = builtInAtom.getArguments();
        List<SWRLBuiltInArgument> swrlBuiltInArguments = convertSWRLDArguments2SWRLBuiltInArguments(swrlDArguments);
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
  @NonNull private List<SWRLBuiltInArgument> convertSWRLDArguments2SWRLBuiltInArguments(
    @NonNull List<SWRLDArgument> swrlDArguments)
  {
    List<SWRLBuiltInArgument> swrlBuiltInArguments = new ArrayList<>();

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
   * <p/>
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
   * <p/>
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

    this.ontology.getOWLOntologyManager().addAxiom(this.ontology, owlapiRule);
  }

  /**
   * Process currently supported OWL axioms. The processing consists of recording any OWL properties in the processed
   * axioms (with an instance of the {@link DefaultIRIResolver} class) and generating declaration
   * axioms for these properties.
   * <p/>
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
      this.owlClassDeclarationAxioms.put(cls.getIRI(), axiom);
      this.assertedOWLAxioms.add(axiom);
      recordOWLClass(cls);
    }
  }

  private void processOWLIndividualDeclarationAxioms()
  {
    for (OWLDeclarationAxiom axiom : getOWLIndividualDeclarationAxioms()) {
      OWLEntity individual = axiom.getEntity();
      this.owlIndividualDeclarationAxioms.put(individual.getIRI(), axiom);
      this.assertedOWLAxioms.add(axiom);
      recordOWLNamedIndividual(individual);
    }
  }

  private void processOWLObjectPropertyDeclarationAxioms()
  {
    for (OWLDeclarationAxiom axiom : getOWLObjectPropertyDeclarationAxioms()) {
      OWLEntity property = axiom.getEntity();
      this.owlObjectPropertyDeclarationAxioms.put(property.getIRI(), axiom);
      this.assertedOWLAxioms.add(axiom);
      recordOWLObjectProperty(property);
    }
  }

  private void processOWLDataPropertyDeclarationAxioms()
  {
    for (OWLDeclarationAxiom axiom : getOWLDataPropertyDeclarationAxioms()) {
      OWLEntity property = axiom.getEntity();

      this.owlDataPropertyDeclarationAxioms.put(property.getIRI(), axiom);
      this.assertedOWLAxioms.add(axiom);
      recordOWLDataProperty(property);
    }
  }

  private void processOWLAnnotationPropertyDeclarationAxioms()
  {
    for (OWLDeclarationAxiom axiom : getOWLAnnotationPropertyDeclarationAxioms()) {
      OWLEntity property = axiom.getEntity();

      this.owlAnnotationPropertyDeclarationAxioms.put(property.getIRI(), axiom);
      this.assertedOWLAxioms.add(axiom);
      recordOWLAnnotationProperty(property);
    }
  }

  private void processOWLSameIndividualAxioms()
  {
    Set<OWLSameIndividualAxiom> axioms = getOWLSameIndividualAxioms();
    for (OWLSameIndividualAxiom axiom : axioms) {
      axiom.getIndividuals().forEach(this::generateOWLIndividualDeclarationAxiomIfNecessary);
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLDifferentIndividualsAxioms()
  {
    Set<OWLDifferentIndividualsAxiom> axioms = getOWLDifferentIndividualsAxioms();
    for (OWLDifferentIndividualsAxiom axiom : axioms) {
      axiom.getIndividuals().forEach(this::generateOWLIndividualDeclarationAxiomIfNecessary);
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLSubClassOfAxioms()
  {
    Set<OWLSubClassOfAxiom> axioms = getOWLSubClassOfAxioms();
    for (OWLSubClassOfAxiom axiom : axioms) {
      generateOWLClassDeclarationAxiomIfNecessary(axiom.getSubClass());
      generateOWLClassDeclarationAxiomIfNecessary(axiom.getSuperClass());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLEquivalentClassesAxioms()
  {
    Set<OWLEquivalentClassesAxiom> axioms = getOWLEquivalentClassesAxioms();
    for (OWLEquivalentClassesAxiom axiom : axioms) {
      axiom.getNamedClasses().forEach(this::generateOWLClassDeclarationAxiom);
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLSubObjectPropertyOfAxioms()
  {
    Set<OWLSubObjectPropertyOfAxiom> axioms = getOWLSubObjectPropertyOfAxioms();
    for (OWLSubObjectPropertyOfAxiom axiom : axioms) {
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getSubProperty());
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getSuperProperty());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLSubDataPropertyOfAxioms()
  {
    Set<OWLSubDataPropertyOfAxiom> axioms = getOWLSubDataPropertyOfAxioms();
    for (OWLSubDataPropertyOfAxiom axiom : axioms) {
      generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getSubProperty());
      generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getSuperProperty());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLTransitiveObjectPropertyAxioms()
  {
    Set<OWLTransitiveObjectPropertyAxiom> axioms = getOWLTransitiveObjectPropertyAxioms();
    for (OWLTransitiveObjectPropertyAxiom axiom : axioms)
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLSymmetricObjectPropertyAxioms()
  {
    Set<OWLSymmetricObjectPropertyAxiom> axioms = getOWLSymmetricObjectPropertyAxioms();
    for (OWLSymmetricObjectPropertyAxiom axiom : axioms)
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLFunctionalObjectPropertyAxioms()
  {
    Set<OWLFunctionalObjectPropertyAxiom> axioms = getOWLFunctionalObjectPropertyAxioms();
    for (OWLFunctionalObjectPropertyAxiom axiom : axioms)
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLInverseFunctionalObjectPropertyAxioms()
  {
    Set<OWLInverseFunctionalObjectPropertyAxiom> axioms = getOWLInverseFunctionalObjectPropertyAxioms();
    for (OWLInverseFunctionalObjectPropertyAxiom axiom : axioms)
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLFunctionalDataPropertyAxioms()
  {
    Set<OWLFunctionalDataPropertyAxiom> axioms = getOWLFunctionalDataPropertyAxioms();
    for (OWLFunctionalDataPropertyAxiom axiom : axioms)
      generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLObjectPropertyDomainAxioms()
  {
    Set<OWLObjectPropertyDomainAxiom> axioms = getOWLObjectPropertyDomainAxioms();
    for (OWLObjectPropertyDomainAxiom axiom : axioms) {
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
      generateOWLClassDeclarationAxiomIfNecessary(axiom.getDomain());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLDataPropertyDomainAxioms()
  {
    Set<OWLDataPropertyDomainAxiom> axioms = getOWLDataPropertyDomainAxioms();
    for (OWLDataPropertyDomainAxiom axiom : axioms) {
      generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
      generateOWLClassDeclarationAxiomIfNecessary(axiom.getDomain());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLObjectPropertyRangeAxioms()
  {
    Set<OWLObjectPropertyRangeAxiom> axioms = getOWLObjectPropertyRangeAxioms();
    for (OWLObjectPropertyRangeAxiom axiom : axioms) {
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
      generateOWLClassDeclarationAxiomIfNecessary(axiom.getRange());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLDataPropertyRangeAxioms()
  {
    Set<OWLDataPropertyRangeAxiom> axioms = getOWLDataPropertyRangeAxioms();
    for (OWLDataPropertyRangeAxiom axiom : axioms) {
      generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLIrreflexiveObjectPropertyAxioms()
  {
    Set<OWLIrreflexiveObjectPropertyAxiom> axioms = getOWLIrreflexiveObjectPropertyAxioms();
    for (OWLIrreflexiveObjectPropertyAxiom axiom : axioms)
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLAsymmetricObjectPropertyAxioms()
  {
    Set<OWLAsymmetricObjectPropertyAxiom> axioms = getOWLAsymmetricObjectPropertyAxioms();
    for (OWLAsymmetricObjectPropertyAxiom axiom : axioms)
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLEquivalentObjectPropertiesAxioms()
  {
    Set<OWLEquivalentObjectPropertiesAxiom> axioms = getOWLEquivalentObjectPropertiesAxioms();
    for (OWLEquivalentObjectPropertiesAxiom axiom : axioms) {
      axiom.getProperties().forEach(this::generateOWLObjectPropertyDeclarationAxiomIfNecessary);
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLEquivalentDataPropertiesAxioms()
  {
    Set<OWLEquivalentDataPropertiesAxiom> axioms = getOWLEquivalentDataPropertiesAxioms();
    for (OWLEquivalentDataPropertiesAxiom axiom : axioms) {
      axiom.getProperties().forEach(this::generateOWLDataPropertyDeclarationAxiomIfNecessary);
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLInverseObjectPropertiesAxioms()
  {
    Set<OWLInverseObjectPropertiesAxiom> axioms = getOWLInverseObjectPropertiesAxioms();
    for (OWLInverseObjectPropertiesAxiom axiom : axioms) {
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getFirstProperty());
      generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getSecondProperty());
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLDisjointObjectPropertiesAxioms()
  {
    Set<OWLDisjointObjectPropertiesAxiom> axioms = getOWLDisjointObjectPropertiesAxioms();
    for (OWLDisjointObjectPropertiesAxiom axiom : axioms) {
      axiom.getProperties().forEach(this::generateOWLObjectPropertyDeclarationAxiomIfNecessary);
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void processOWLDisjointDataPropertiesAxioms()
  {
    Set<OWLDisjointDataPropertiesAxiom> axioms = getOWLDisjointDataPropertiesAxioms();
    for (OWLDisjointDataPropertiesAxiom axiom : axioms) {
      axiom.getProperties().forEach(this::generateOWLDataPropertyDeclarationAxiomIfNecessary);
    }
    this.assertedOWLAxioms.addAll(axioms);
  }

  private void generateOWLClassDeclarationAxiom(@NonNull OWLClass cls)
  {
    if (!this.owlClassDeclarationAxioms.containsKey(cls.getIRI())) {
      OWLDeclarationAxiom axiom = getSWRLAPIOWLDataFactory().getOWLClassDeclarationAxiom(cls);
      this.owlClassDeclarationAxioms.put(cls.getIRI(), axiom);
      this.assertedOWLAxioms.add(axiom);
      recordOWLClass(cls);
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
    if (individual.isNamed() && !this.owlIndividualDeclarationAxioms
      .containsKey(individual.asOWLNamedIndividual().getIRI())) {
      OWLDeclarationAxiom axiom = getSWRLAPIOWLDataFactory()
        .getOWLIndividualDeclarationAxiom(individual.asOWLNamedIndividual());
      this.owlIndividualDeclarationAxioms.put(individual.asOWLNamedIndividual().getIRI(), axiom);
      this.assertedOWLAxioms.add(axiom);
      recordOWLNamedIndividual(individual.asOWLNamedIndividual());
    }
  }

  private void generateOWLObjectPropertyDeclarationAxiomIfNecessary(
    @NonNull OWLObjectPropertyExpression propertyExpression)
  {
    if (propertyExpression instanceof OWLObjectProperty) {
      OWLObjectProperty property = (OWLObjectProperty)propertyExpression;
      if (!this.owlObjectPropertyDeclarationAxioms.containsKey(property.getIRI())) {
        OWLDeclarationAxiom axiom = getSWRLAPIOWLDataFactory().getOWLObjectPropertyDeclarationAxiom(property);
        this.owlObjectPropertyDeclarationAxioms.put(property.getIRI(), axiom);
        this.assertedOWLAxioms.add(axiom);
        recordOWLObjectProperty(property);
      }
    }
  }

  private void generateOWLDataPropertyDeclarationAxiomIfNecessary(@NonNull OWLDataPropertyExpression propertyExpression)
  {
    if (propertyExpression instanceof OWLDataProperty) {
      OWLDataProperty property = (OWLDataProperty)propertyExpression;
      if (!this.owlDataPropertyDeclarationAxioms.containsKey(property.getIRI())) {
        OWLDeclarationAxiom axiom = getSWRLAPIOWLDataFactory().getOWLDataPropertyDeclarationAxiom(property);
        this.owlDataPropertyDeclarationAxioms.put(property.getIRI(), axiom);
        this.assertedOWLAxioms.add(axiom);
        recordOWLDataProperty(property);
      }
    }
  }

  @NonNull private Set<OWLSameIndividualAxiom> getOWLSameIndividualAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SAME_INDIVIDUAL, Imports.INCLUDED);
  }

  @NonNull private Set<OWLDifferentIndividualsAxiom> getOWLDifferentIndividualsAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DIFFERENT_INDIVIDUALS, Imports.INCLUDED);
  }

  @NonNull private Set<OWLSubObjectPropertyOfAxiom> getOWLSubObjectPropertyOfAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SUB_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<OWLSubDataPropertyOfAxiom> getOWLSubDataPropertyOfAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SUB_DATA_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<OWLEquivalentClassesAxiom> getOWLEquivalentClassesAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.EQUIVALENT_CLASSES, Imports.INCLUDED);
  }

  @NonNull private Set<OWLClassAssertionAxiom> getOWLClassAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.CLASS_ASSERTION, Imports.INCLUDED);
  }

  @NonNull private Set<OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION, Imports.INCLUDED);
  }

  @NonNull private Set<OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DATA_PROPERTY_ASSERTION, Imports.INCLUDED);
  }

  @NonNull private Set<OWLSubClassOfAxiom> getOWLSubClassOfAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SUBCLASS_OF, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<OWLDisjointClassesAxiom> getOWLDisjointClassesAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DISJOINT_CLASSES, Imports.INCLUDED);
  }

  @NonNull private Set<OWLEquivalentDataPropertiesAxiom> getOWLEquivalentDataPropertiesAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.EQUIVALENT_DATA_PROPERTIES, Imports.INCLUDED);
  }

  @NonNull private Set<OWLEquivalentObjectPropertiesAxiom> getOWLEquivalentObjectPropertiesAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES, Imports.INCLUDED);
  }

  @NonNull private Set<OWLDisjointDataPropertiesAxiom> getOWLDisjointDataPropertiesAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DISJOINT_DATA_PROPERTIES, Imports.INCLUDED);
  }

  @NonNull private Set<OWLDisjointObjectPropertiesAxiom> getOWLDisjointObjectPropertiesAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DISJOINT_OBJECT_PROPERTIES, Imports.INCLUDED);
  }

  @NonNull private Set<OWLObjectPropertyDomainAxiom> getOWLObjectPropertyDomainAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN, Imports.INCLUDED);
  }

  @NonNull private Set<OWLDataPropertyDomainAxiom> getOWLDataPropertyDomainAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DATA_PROPERTY_DOMAIN, Imports.INCLUDED);
  }

  @NonNull private Set<OWLObjectPropertyRangeAxiom> getOWLObjectPropertyRangeAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.OBJECT_PROPERTY_RANGE, Imports.INCLUDED);
  }

  @NonNull private Set<OWLDataPropertyRangeAxiom> getOWLDataPropertyRangeAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DATA_PROPERTY_RANGE, Imports.INCLUDED);
  }

  @NonNull private Set<OWLFunctionalObjectPropertyAxiom> getOWLFunctionalObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.FUNCTIONAL_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<OWLFunctionalDataPropertyAxiom> getOWLFunctionalDataPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.FUNCTIONAL_DATA_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<OWLIrreflexiveObjectPropertyAxiom> getOWLIrreflexiveObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.IRREFLEXIVE_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<OWLInverseFunctionalObjectPropertyAxiom> getOWLInverseFunctionalObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<OWLTransitiveObjectPropertyAxiom> getOWLTransitiveObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.TRANSITIVE_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<OWLSymmetricObjectPropertyAxiom> getOWLSymmetricObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SYMMETRIC_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<OWLAsymmetricObjectPropertyAxiom> getOWLAsymmetricObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.ASYMMETRIC_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<OWLInverseObjectPropertiesAxiom> getOWLInverseObjectPropertiesAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.INVERSE_OBJECT_PROPERTIES, Imports.INCLUDED);
  }

  @NonNull private Set<OWLNegativeDataPropertyAssertionAxiom> getOWLNegativeDataPropertyAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.NEGATIVE_DATA_PROPERTY_ASSERTION, Imports.INCLUDED);
  }

  @NonNull private Set<OWLNegativeObjectPropertyAssertionAxiom> getOWLNegativeObjectPropertyAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION, Imports.INCLUDED);
  }

  @NonNull private Set<OWLReflexiveObjectPropertyAxiom> getOWLReflexiveObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.REFLEXIVE_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull private Set<OWLDisjointUnionAxiom> getOWLDisjointUnionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DISJOINT_UNION, Imports.INCLUDED);
  }

  @NonNull private Set<OWLAnnotationAssertionAxiom> getOWLAnnotationAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.ANNOTATION_ASSERTION, Imports.INCLUDED);
  }

  @NonNull private Set<OWLSubPropertyChainOfAxiom> getOWLSubPropertyChainOfAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF, Imports.INCLUDED);
  }

  @NonNull private Set<OWLHasKeyAxiom> getOWLHasKeyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.HAS_KEY, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<OWLDatatypeDefinitionAxiom> getOWLDatatypeDefinitionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DATATYPE_DEFINITION, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<OWLAnnotationPropertyRangeAxiom> getOWLAnnotationPropertyRangeAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.ANNOTATION_PROPERTY_RANGE, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<OWLAnnotationPropertyDomainAxiom> getOWLAnnotationPropertyDomainAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.ANNOTATION_PROPERTY_DOMAIN, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<OWLSubAnnotationPropertyOfAxiom> getOWLSubAnnotationPropertyOfAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF, Imports.INCLUDED);
  }

  @NonNull private Set<OWLDeclarationAxiom> getOWLClassDeclarationAxioms()
  {
    Set<OWLDeclarationAxiom> owlClassDeclarationAxioms = getOWLOntology()
      .getAxioms(AxiomType.DECLARATION, Imports.INCLUDED).stream()
      .filter(owlDeclarationAxiom -> owlDeclarationAxiom.getEntity().isOWLClass()).collect(Collectors.toSet());

    return owlClassDeclarationAxioms;
  }

  @NonNull private Set<OWLDeclarationAxiom> getOWLIndividualDeclarationAxioms()
  {
    Set<OWLDeclarationAxiom> owlIndividualDeclarationAxioms = getOWLOntology()
      .getAxioms(AxiomType.DECLARATION, Imports.INCLUDED).stream()
      .filter(owlDeclarationAxiom -> owlDeclarationAxiom.getEntity().isOWLNamedIndividual())
      .collect(Collectors.toSet());

    return owlIndividualDeclarationAxioms;
  }

  @NonNull private Set<OWLDeclarationAxiom> getOWLObjectPropertyDeclarationAxioms()
  {
    Set<OWLDeclarationAxiom> owlObjectPropertyDeclarationAxioms = getOWLOntology()
      .getAxioms(AxiomType.DECLARATION, Imports.INCLUDED).stream()
      .filter(owlDeclarationAxiom -> owlDeclarationAxiom.getEntity().isOWLObjectProperty()).collect(Collectors.toSet());

    return owlObjectPropertyDeclarationAxioms;
  }

  @NonNull private Set<OWLDeclarationAxiom> getOWLDataPropertyDeclarationAxioms()
  {
    Set<OWLDeclarationAxiom> owlDataPropertyDeclarationAxioms = getOWLOntology()
      .getAxioms(AxiomType.DECLARATION, Imports.INCLUDED).stream()
      .filter(owlDeclarationAxiom -> owlDeclarationAxiom.getEntity().isOWLDataProperty()).collect(Collectors.toSet());

    return owlDataPropertyDeclarationAxioms;
  }

  @NonNull private Set<OWLDeclarationAxiom> getOWLAnnotationPropertyDeclarationAxioms()
  {
    Set<OWLDeclarationAxiom> owlAnnotationPropertyDeclarationAxioms = getOWLOntology()
      .getAxioms(AxiomType.DECLARATION, Imports.INCLUDED).stream()
      .filter(owlDeclarationAxiom -> owlDeclarationAxiom.getEntity().isOWLAnnotationProperty())
      .collect(Collectors.toSet());

    return owlAnnotationPropertyDeclarationAxioms;
  }

  private void recordOWLClass(@NonNull OWLEntity cls)
  {
    getIRIResolver().recordOWLClass(cls);
  }

  private void recordOWLNamedIndividual(@NonNull OWLEntity individual)
  {
    getIRIResolver().recordOWLNamedIndividual(individual);
  }

  private void recordOWLObjectProperty(@NonNull OWLEntity property)
  {
    getIRIResolver().recordOWLObjectProperty(property);
  }

  private void recordOWLDataProperty(@NonNull OWLEntity property)
  {
    getIRIResolver().recordOWLDataProperty(property);
  }

  private void recordOWLAnnotationProperty(@NonNull OWLEntity property)
  {
    getIRIResolver().recordOWLAnnotationProperty(property);
  }

}
