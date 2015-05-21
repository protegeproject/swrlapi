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
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.core.LiteralFactory;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIOntologyProcessor;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.exceptions.SQWRLInvalidQueryNameException;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class DefaultSWRLAPIOntologyProcessor implements SWRLAPIOntologyProcessor
{
  private final SWRLAPIOWLOntology swrlapiOWLOntology;

  @NonNull private final Map<String, SWRLAPIRule> swrlRules; // SWRL rules include SQWRL queries
  @NonNull private final Map<String, SQWRLQuery> sqwrlQueries;

  @NonNull private final Set<OWLAxiom> assertedOWLAxioms; // All asserted OWL axioms extracted from the supplied ontology

  @NonNull private final Map<IRI, OWLDeclarationAxiom> owlClassDeclarationAxioms;
  @NonNull private final Map<IRI, OWLDeclarationAxiom> owlIndividualDeclarationAxioms;
  @NonNull private final Map<IRI, OWLDeclarationAxiom> owlObjectPropertyDeclarationAxioms;
  @NonNull private final Map<IRI, OWLDeclarationAxiom> owlDataPropertyDeclarationAxioms;
  @NonNull private final Map<IRI, OWLDeclarationAxiom> owlAnnotationPropertyDeclarationAxioms;

  public DefaultSWRLAPIOntologyProcessor(@NonNull SWRLAPIOWLOntology swrlapiOWLOntology)
  {
    this.swrlapiOWLOntology = swrlapiOWLOntology;

    this.swrlRules = new HashMap<>();
    this.sqwrlQueries = new HashMap<>();

    this.assertedOWLAxioms = new HashSet<>();

    this.owlClassDeclarationAxioms = new HashMap<>();
    this.owlIndividualDeclarationAxioms = new HashMap<>();
    this.owlObjectPropertyDeclarationAxioms = new HashMap<>();
    this.owlDataPropertyDeclarationAxioms = new HashMap<>();
    this.owlAnnotationPropertyDeclarationAxioms = new HashMap<>();
  }

  @Override public void reset()
  {
    this.swrlRules.clear();
    this.sqwrlQueries.clear();

    getIRIResolver().reset();

    this.assertedOWLAxioms.clear();

    this.owlClassDeclarationAxioms.clear();
    this.owlIndividualDeclarationAxioms.clear();
    this.owlObjectPropertyDeclarationAxioms.clear();
    this.owlDataPropertyDeclarationAxioms.clear();
    this.owlAnnotationPropertyDeclarationAxioms.clear();
  }

  @Override public void processOntology() throws SQWRLException
  {
    reset();

    processSWRLRulesAndSQWRLQueries();
    processOWLAxioms();
  }

  @NonNull @Override public SQWRLQuery getSQWRLQuery(@NonNull String queryName) throws SQWRLException
  {
    if (!this.sqwrlQueries.containsKey(queryName))
      throw new SQWRLInvalidQueryNameException("invalid SQWRL query name " + queryName);

    return this.sqwrlQueries.get(queryName);
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

      if (rule.isSQWRLQuery())
        this.sqwrlQueries.remove(ruleName);

      this.swrlRules.remove(ruleName);
    }

    SWRLRule owlapiRule = findRuleNamed(ruleName);

    if (owlapiRule != null)
      this.swrlapiOWLOntology.getOWLOntologyManager().removeAxiom(this.swrlapiOWLOntology.getOWLOntology(), owlapiRule);
  }

  @Override public void addSWRLRule(@NonNull SWRLAPIRule swrlapiRule, @NonNull SWRLRule owlapiRule)
  {
    String ruleName = swrlapiRule.getRuleName();

    this.swrlRules.put(ruleName, swrlapiRule);

    this.swrlapiOWLOntology.getOWLOntologyManager().addAxiom(this.swrlapiOWLOntology.getOWLOntology(), owlapiRule);
  }

  @Override public int getNumberOfSWRLRules()
  {
    return this.swrlRules.values().size();
  }

  @Override public int getNumberOfSQWRLQueries()
  {
    return this.sqwrlQueries.values().size();
  }

  @NonNull @Override public Set<String> getSQWRLQueryNames()
  {
    return new HashSet<>(this.sqwrlQueries.keySet());
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

  @NonNull @Override public Set<SQWRLQuery> getSQWRLQueries()
  {
    return new HashSet<>(this.sqwrlQueries.values());
  }

  @NonNull @Override public Set<OWLAxiom> getOWLAxioms()
  {
    return Collections.unmodifiableSet(this.assertedOWLAxioms);
  }

  @Override public boolean hasAssertedOWLAxiom(@NonNull OWLAxiom axiom)
  {
    return this.assertedOWLAxioms.contains(axiom);
  }

  @NonNull @Override public SQWRLQuery createSWRLQueryFromSWRLRule(@NonNull SWRLAPIRule rule) throws SQWRLException
  {
    String queryName = rule.getRuleName();
    boolean active = rule.isActive();
    String comment = rule.getComment();
    SQWRLQuery query = SWRLAPIFactory
      .getSQWRLQuery(queryName, rule.getBodyAtoms(), rule.getHeadAtoms(), active, comment, getLiteralFactory(),
        getSQWRLResultValueFactory());
    this.sqwrlQueries.put(queryName, query);

    return query;
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

  @NonNull @Override public String getRuleName(@NonNull SWRLRule owlapiRule)
  {
    OWLAnnotationProperty labelAnnotation = getOWLDataFactory()
      .getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());

    for (OWLAnnotation annotation : owlapiRule.getAnnotations(labelAnnotation)) {
      if (annotation.getValue() instanceof OWLLiteral) {
        OWLLiteral literal = (OWLLiteral)annotation.getValue();
        return literal.getLiteral(); // TODO We just pick one for the moment
      }
    }
    // TODO Also look for swrla#ruleName annotation
    return "";
  }

  @Override public boolean getIsActive(@NonNull SWRLRule owlapiRule)
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

  @NonNull @Override public String getComment(@NonNull SWRLRule owlapiRule)
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

  private SWRLRule findRuleNamed(@NonNull String ruleName)
  { // TODO Not efficient - probably a better way
    for (SWRLRule owlapiRule : getOWLOntology().getAxioms(AxiomType.SWRL_RULE, Imports.INCLUDED)) {
      if (ruleName.equals(getRuleName(owlapiRule)))
        return owlapiRule;
    }
    return null;
  }

  @NonNull @SuppressWarnings("unused") private Set<String> getSWRLRuleNames()
  {
    return new HashSet<>(this.swrlRules.keySet());
  }

  @NonNull @SuppressWarnings("unused") private Set<SWRLAPIRule> getSWRLRules()
  {
    return new HashSet<>(this.swrlRules.values());
  }

  /**
   * Process currently supported OWL axioms. The processing consists of recording any OWL properties in the processed
   * axioms (with an instance of the {@link org.swrlapi.core.resolvers.IRIResolver} class) and generating declaration
   * axioms for these properties.
   * <p>
   * TODO The current approach is clunky. A better approach would be to walk the axioms with a visitor and record the
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

  private void processSWRLRulesAndSQWRLQueries() throws SQWRLException
  {
    for (SWRLAPIRule ruleOrQuery : getSWRLAPIOWLOntology().getSWRLRules())
      processSWRLRuleOrSQWRLQuery(ruleOrQuery);
  }

  private void processSWRLRuleOrSQWRLQuery(@NonNull SWRLAPIRule ruleOrQuery) throws SQWRLException
  {
    if (ruleOrQuery.isSQWRLQuery()) {
      createSWRLQueryFromSWRLRule(ruleOrQuery);
      this.swrlRules.put(ruleOrQuery.getRuleName(), ruleOrQuery);
    } else {
      this.swrlRules.put(ruleOrQuery.getRuleName(), ruleOrQuery);
      this.assertedOWLAxioms.add(ruleOrQuery); // A SWRL rule is a type of OWL axiom; a SQWRL query is not.
    }
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

  @NonNull @SuppressWarnings("unused") private Set<OWLNegativeDataPropertyAssertionAxiom> getOWLNegativeDataPropertyAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.NEGATIVE_DATA_PROPERTY_ASSERTION, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<OWLNegativeObjectPropertyAssertionAxiom> getOWLNegativeObjectPropertyAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<OWLReflexiveObjectPropertyAxiom> getOWLReflexiveObjectPropertyAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.REFLEXIVE_OBJECT_PROPERTY, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<OWLDisjointUnionAxiom> getOWLDisjointUnionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.DISJOINT_UNION, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<OWLAnnotationAssertionAxiom> getOWLAnnotationAssertionAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.ANNOTATION_ASSERTION, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<OWLSubPropertyChainOfAxiom> getOWLSubPropertyChainOfAxioms()
  {
    return getOWLOntology().getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF, Imports.INCLUDED);
  }

  @NonNull @SuppressWarnings("unused") private Set<OWLHasKeyAxiom> getOWLHasKeyAxioms()
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

  @NonNull @SuppressWarnings("unused") private Set<OWLDeclarationAxiom> getOWLDatatypeDeclarationAxioms()
  {
    Set<OWLDeclarationAxiom> owlDatatypeDeclarationAxioms = getOWLOntology()
      .getAxioms(AxiomType.DECLARATION, Imports.INCLUDED).stream()
      .filter(owlDeclarationAxiom -> owlDeclarationAxiom.getEntity().isOWLDatatype()).collect(Collectors.toSet());

    return owlDatatypeDeclarationAxioms;
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

  @NonNull private OWLOntology getOWLOntology()
  {
    return getSWRLAPIOWLOntology().getOWLOntology();
  }

  @NonNull private IRIResolver getIRIResolver()
  {
    return getSWRLAPIOWLOntology().getIRIResolver();
  }

  @NonNull private SWRLAPIOWLOntology getSWRLAPIOWLOntology()
  {
    return this.swrlapiOWLOntology;
  }

  @NonNull private SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
  {
    return getSWRLAPIOWLOntology().getSWRLAPIOWLDataFactory();
  }

  @NonNull private OWLDataFactory getOWLDataFactory()
  {
    return getSWRLAPIOWLOntology().getOWLDataFactory();
  }

  @NonNull private LiteralFactory getLiteralFactory()
  {
    return getSWRLAPIOWLDataFactory().getLiteralFactory();
  }

  @NonNull private SQWRLResultValueFactory getSQWRLResultValueFactory()
  {
    return getSWRLAPIOWLDataFactory().getSQWRLResultValueFactory();
  }

}