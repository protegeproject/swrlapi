package org.swrlapi.factory;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
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
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.core.SQWRLQueryRenderer;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIOntologyProcessor;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.exceptions.SWRLAPIInternalException;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import org.swrlapi.ui.model.SWRLAutoCompleter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class does not directly deal with SQWRL queries. Instead, a {@link org.swrlapi.core.SWRLAPIOntologyProcessor} is
 * used to extract SQWRL queries - which are stored as SWRL rules - from a {@link org.swrlapi.core.SWRLAPIOWLOntology}.
 * 
 */
class DefaultSWRLAPIOWLOntology implements SWRLAPIOWLOntology
{
  private final OWLOntology ontology;
  private final DefaultPrefixManager prefixManager;
  private final IRIResolver iriResolver;
  private final SWRLParser swrlParser;
  private final Set<IRI> swrlBuiltInIRIs;
  private final SWRLAPIOWLDataFactory swrlapiOWLDataFactory;
  private final SWRLAPIOntologyProcessor swrlapiOntologyProcessor;
  private final SQWRLResultValueFactory sqwrlResultValueFactory;

  public DefaultSWRLAPIOWLOntology(OWLOntology ontology, DefaultPrefixManager prefixManager)
  {
    this.ontology = ontology;
    this.prefixManager = prefixManager;
    this.iriResolver = new IRIResolver(this.prefixManager);
    this.swrlParser = new SWRLParser(this);
    this.swrlBuiltInIRIs = new HashSet<>();
    this.swrlapiOWLDataFactory = SWRLAPIFactory.createSWRLAPIOWLDataFactory(this);
    this.swrlapiOntologyProcessor = SWRLAPIFactory.createSWRLAPIOntologyProcessor(this);
    this.sqwrlResultValueFactory = SWRLAPIFactory.createSQWRLResultValueFactory(this);

    addDefaultSWRLBuiltIns();
  }

  @Override
  public OWLOntology getOWLOntology()
  {
    return this.ontology;
  }

  @Override
  public OWLOntologyManager getOWLOntologyManager()
  {
    return this.ontology.getOWLOntologyManager();
  }

  @Override
  public DefaultPrefixManager getPrefixManager()
  {
    return this.prefixManager;
  }

  @Override
  public SWRLAPIRule createSWRLRule(String ruleName, String rule) throws SWRLParseException
  {
    return createSWRLRule(ruleName, rule, "", true);
  }

  @Override
  public SWRLAPIRule createSWRLRule(String ruleName, String rule, String comment, boolean isActive)
      throws SWRLParseException
  {
    SWRLRule owlapiRule = this.swrlParser.parseSWRLRule(rule, false, ruleName, comment);
    SWRLAPIRule swrlapiRule = convertOWLAPIRule2SWRLAPIRule(owlapiRule, ruleName, comment, isActive);

    this.swrlapiOntologyProcessor.addSWRLRule(swrlapiRule, owlapiRule); // Adds rule to the underlying ontology

    return swrlapiRule;
  }

  @Override
  public SWRLParser createSWRLParser()
  {
    return new SWRLParser(this);
  }

  @Override
  public SWRLAutoCompleter createSWRLAutoCompleter()
  {
    return SWRLAPIFactory.getSWRLAutoCompleter(this);
  }

  @Override
  public SWRLRuleRenderer createSWRLRuleRenderer()
  {
    return SWRLAPIFactory.getSWRLRuleRenderer(this);
  }

  @Override
  public SQWRLQueryRenderer createSQWRLQueryRenderer()
  {
    return SWRLAPIFactory.getSQWRLQueryRenderer(this);
  }

  @Override
  public SQWRLQuery createSQWRLQuery(String queryName, String query) throws SWRLParseException, SQWRLException
  {
    return createSQWRLQuery(queryName, query, "", true);
  }

  @Override
  public SQWRLQuery createSQWRLQuery(String queryName, String query, String comment, boolean isActive)
      throws SWRLParseException, SQWRLException
  {
    SWRLRule owlapiRule = this.swrlParser.parseSWRLRule(query, false, queryName, comment);
    SWRLAPIRule swrlapiRule = convertOWLAPIRule2SWRLAPIRule(owlapiRule, queryName, comment, isActive);

    this.swrlapiOntologyProcessor.addSWRLRule(swrlapiRule, owlapiRule); // Adds rule to the underlying ontology

    if (swrlapiRule.isSQWRLQuery()) {
      return this.swrlapiOntologyProcessor.createSWRLQueryFromSWRLRule(swrlapiRule);
    } else
      throw new SWRLParseException(queryName + " is not a SQWRL query");
  }

  @Override
  public SWRLAPIRule getSWRLRule(String ruleName) throws SWRLRuleException
  {
    return this.swrlapiOntologyProcessor.getSWRLRule(ruleName);
  }

  @Override
  public Set<SWRLAPIRule> getSWRLRules()
  {
    Set<SWRLAPIRule> swrlapiRules = new HashSet<>();
    Set<SWRLRule> unannotatedOWLAPIRules = new HashSet<>();
    Set<String> ruleNames = new HashSet<>();

    for (SWRLRule owlapiRule : getOWLOntology().getAxioms(AxiomType.SWRL_RULE, Imports.INCLUDED)) {
      String ruleName = this.swrlapiOntologyProcessor.getRuleName(owlapiRule);
      boolean isActive = this.swrlapiOntologyProcessor.getIsActive(owlapiRule);
      String comment = this.swrlapiOntologyProcessor.getComment(owlapiRule);

      if (ruleName.isEmpty()) {
        unannotatedOWLAPIRules.add(owlapiRule);
      } else {
        SWRLAPIRule swrlapiRule = convertOWLAPIRule2SWRLAPIRule(owlapiRule, ruleName, comment, isActive);
        swrlapiRules.add(swrlapiRule);
        ruleNames.add(ruleName);
      }
    }

    for (SWRLRule owlapiRule : unannotatedOWLAPIRules) {
      String ruleName = generateRuleName(ruleNames);
      String comment = "";
      boolean isEnabled = true;
      SWRLRule annotatedOWLAPIRule = getOWLDataFactory().getSWRLRule(owlapiRule.getBody(), owlapiRule.getHead(),
          generateRuleAnnotations(ruleName, comment, isEnabled));

      SWRLAPIRule swrlapiRule = convertOWLAPIRule2SWRLAPIRule(annotatedOWLAPIRule, ruleName, "", true);
      swrlapiRules.add(swrlapiRule);
      ruleNames.add(ruleName);

      // ontologyManager.removeAxiom(ontology, owlapiRule); // Remove the original annotated rule
      // ontologyManager.addAxiom(ontology, annotatedOWLAPIRule); // Replace with annotated rule
    }

    return swrlapiRules;
  }

  private String generateRuleName(Set<String> ruleNames)
  {
    String ruleNamePrefix = "R";
    int ruleNumber = 1;
    String candidateRuleName = ruleNamePrefix + ruleNumber;

    while (ruleNames.contains(candidateRuleName)) {
      ruleNumber++;
      candidateRuleName = ruleNamePrefix + ruleNumber;
    }
    return candidateRuleName;
  }

  private Set<OWLAnnotation> generateRuleAnnotations(String ruleName, String comment, boolean isEnabled)
  {
    OWLAnnotation labelAnnotation = getOWLDataFactory().getOWLAnnotation(getOWLDataFactory().getRDFSLabel(),
        getOWLDataFactory().getOWLLiteral(ruleName));
    OWLAnnotation commentAnnotation = getOWLDataFactory().getOWLAnnotation(getOWLDataFactory().getRDFSComment(),
        getOWLDataFactory().getOWLLiteral(""));
    // TODO Add isEnabled annotation to rule
    Set<OWLAnnotation> annotations = new HashSet<>();
    annotations.add(labelAnnotation);
    annotations.add(commentAnnotation);

    return annotations;
  }

  @Override
  public void deleteSWRLRule(String ruleName)
  {
    this.swrlapiOntologyProcessor.deleteSWRLRule(ruleName);
  }

  @Override
  public SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
  {
    return this.swrlapiOWLDataFactory;
  }

  @Override
  public OWLDataFactory getOWLDataFactory()
  {
    return this.ontology.getOWLOntologyManager().getOWLDataFactory();
  }

  @Override
  public IRIResolver getIRIResolver()
  {
    return this.iriResolver;
  }

  @Override
  public void startBulkConversion()
  {
    // TODO implement startBulkConversion
  }

  @Override
  public void completeBulkConversion()
  {
    // TODO implement completeBulkConversion
  }

  @Override
  public boolean hasOntologyChanged()
  {
    return true; // TODO implement hasOntologyChanged
  }

  @Override
  public void resetOntologyChanged()
  {
    // TODO implement resetOntologyChanged
  }

  // void addRuleNameAnnotation(SWRLRule rule, String ruleName)
  // {
  // OWLAnnotationProperty labelAnnotationProperty = getOWLDataFactory().getRDFSLabel();
  // OWLLiteral label = getOWLDataFactory().getOWLLiteral(ruleName, "en");
  // OWLAnnotation labelAnnotation = getOWLDataFactory().getOWLAnnotation(labelAnnotationProperty, label);
  //
  // OWLAxiom anotationAssertionAxiom = getOWLDataFactory().getOWLAnnotationAssertionAxiom(rule, labelAnnotation);
  // this.ontologyManager.applyChange(new AddAxiom(this.ontology, anotationAssertionAxiom));
  // }

  @Override
  public boolean isSWRLBuiltIn(IRI iri)
  {
    return this.swrlBuiltInIRIs.contains(iri);
  }

  @Override
  public void addSWRLBuiltIn(IRI iri)
  {
    this.swrlBuiltInIRIs.add(iri);
  }

  @Override
  public Set<IRI> getSWRLBuiltInIRIs()
  {
    return new HashSet<>(this.swrlBuiltInIRIs);
  }

  @Override
  public int getNumberOfSQWRLQueries()
  {
    return this.swrlapiOntologyProcessor.getNumberOfSQWRLQueries();
  }

  @Override
  public Set<String> getSQWRLQueryNames()
  {
    return this.swrlapiOntologyProcessor.getSQWRLQueryNames();
  }

  @Override
  public SQWRLQuery getSQWRLQuery(String queryName) throws SQWRLException
  {
    return this.swrlapiOntologyProcessor.getSQWRLQuery(queryName);
  }

  @Override
  public Set<SQWRLQuery> getSQWRLQueries()
  {
    return this.swrlapiOntologyProcessor.getSQWRLQueries();
  }

  @Override
  public SQWRLResult getSQWRLResult(String queryName) throws SQWRLException
  {
    return this.swrlapiOntologyProcessor.getSQWRLResult(queryName);
  }

  @Override
  public SQWRLResultGenerator getSQWRLResultGenerator(String queryName) throws SQWRLException
  {
    return this.swrlapiOntologyProcessor.getSQWRLResultGenerator(queryName);
  }

  @Override
  public SQWRLResultGenerator createSQWRLResultGenerator()
  {
    return SWRLAPIFactory.createSQWRLResultGenerator(this.sqwrlResultValueFactory);
  }

  @Override
  public void reset()
  {
    this.swrlapiOntologyProcessor.reset();
  }

  @Override
  public void processOntology() throws SQWRLException
  {
    this.swrlapiOntologyProcessor.processOntology();
  }

  @Override
  public boolean hasAssertedOWLAxiom(OWLAxiom axiom)
  {
    return this.swrlapiOntologyProcessor.hasAssertedOWLAxiom(axiom);
  }

  @Override
  public Set<OWLAxiom> getOWLAxioms()
  {
    return this.swrlapiOntologyProcessor.getOWLAxioms();
  }

  @Override
  public int getNumberOfSWRLRules()
  {
    return this.swrlapiOntologyProcessor.getNumberOfSWRLRules();
  }

  @Override
  public int getNumberOfOWLAxioms()
  {
    return this.swrlapiOntologyProcessor.getNumberOfOWLAxioms();
  }

  @Override
  public int getNumberOfOWLClassDeclarationAxioms()
  {
    return this.swrlapiOntologyProcessor.getNumberOfOWLClassDeclarationAxioms();
  }

  @Override
  public int getNumberOfOWLIndividualDeclarationAxioms()
  {
    return this.swrlapiOntologyProcessor.getNumberOfOWLIndividualDeclarationAxioms();
  }

  @Override
  public int getNumberOfOWLObjectPropertyDeclarationAxioms()
  {
    return this.swrlapiOntologyProcessor.getNumberOfOWLObjectPropertyDeclarationAxioms();
  }

  @Override
  public int getNumberOfOWLDataPropertyDeclarationAxioms()
  {
    return this.swrlapiOntologyProcessor.getNumberOfOWLDataPropertyDeclarationAxioms();
  }

  /**
   * We take an OWLAPI {@link org.semanticweb.owlapi.model.SWRLRule} object and for every OWLAPI
   * {@link org.semanticweb.owlapi.model.SWRLBuiltInAtom} in it we create a SWRLAPI
   * {@link org.swrlapi.core.SWRLAPIBuiltInAtom}; all other atoms remain the same.
   *
   * @see org.semanticweb.owlapi.model.SWRLRule
   * @see org.swrlapi.core.SWRLAPIRule
   */
  private SWRLAPIRule convertOWLAPIRule2SWRLAPIRule(SWRLRule owlapiRule, String ruleName, String comment,
      boolean isActive)
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
        SWRLBuiltInAtom swrlapiBuiltInAtom = getSWRLAPIOWLDataFactory().getSWRLAPIBuiltInAtom(ruleName, builtInIRI,
            builtInPrefixedName, swrlBuiltInArguments);
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
        SWRLBuiltInAtom swrlapiBuiltInAtom = getSWRLAPIOWLDataFactory().getSWRLAPIBuiltInAtom(ruleName, builtInIRI,
            builtInPrefixedName, swrlBuiltInArguments);
        swrlapiHeadAtoms.add(swrlapiBuiltInAtom);
      } else
        swrlapiHeadAtoms.add(atom); // Only built-in atoms are converted; other atoms remain the same
    }
    return SWRLAPIFactory.getSWRLAPIRule(ruleName, swrlapiBodyAtoms, swrlapiHeadAtoms, comment, isActive);
  }

  /**
   * Both the OWLAPI and the SWRLAPI use the {@link org.semanticweb.owlapi.model.SWRLBuiltInAtom} class to represent
   * built-in atoms. However, the SWRLAPI has a richer range of possible argument types. The OWLAPI allows
   * {@link org.semanticweb.owlapi.model.SWRLDArgument} built-in arguments only, whereas the SWRLAPI has a range of
   * types. These types are represented buy the {@link org.swrlapi.builtins.arguments.SWRLBuiltInArgument} interface.
   *
   * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
   */
  private List<SWRLBuiltInArgument> convertSWRLDArguments2SWRLBuiltInArguments(List<SWRLDArgument> swrlDArguments)
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
  private SWRLBuiltInArgument convertSWRLDArgument2SWRLBuiltInArgument(SWRLDArgument swrlDArgument)
  {
    if (swrlDArgument instanceof SWRLLiteralArgument) {
      SWRLLiteralArgument swrlLiteralArgument = (SWRLLiteralArgument)swrlDArgument;
      return convertSWRLLiteralArgument2SWRLBuiltInArgument(swrlLiteralArgument);
    } else if (swrlDArgument instanceof SWRLVariable) {
      SWRLVariable swrlVariable = (SWRLVariable)swrlDArgument;
      return convertSWRLVariable2SWRLBuiltInArgument(swrlVariable);
    } else
      throw new SWRLAPIInternalException("Unknown " + SWRLDArgument.class.getName() + " class "
          + swrlDArgument.getClass().getName());
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
  private SWRLBuiltInArgument convertSWRLVariable2SWRLBuiltInArgument(SWRLVariable swrlVariable)
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

  private boolean isOWLClass(IRI iri)
  {
    return getOWLOntology().containsClassInSignature(iri, Imports.INCLUDED)
        || iri.equals(OWLRDFVocabulary.OWL_THING.getIRI()) || iri.equals(OWLRDFVocabulary.OWL_NOTHING.getIRI());
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
  private SWRLBuiltInArgument convertSWRLLiteralArgument2SWRLBuiltInArgument(SWRLLiteralArgument swrlLiteralArgument)
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

  private boolean isURI(OWLDatatype datatype)
  {
    return datatype.getIRI().equals(OWL2Datatype.XSD_ANY_URI.getIRI());
  }

  private SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
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
}
