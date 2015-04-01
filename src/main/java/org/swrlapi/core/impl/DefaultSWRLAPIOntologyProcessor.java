package org.swrlapi.core.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
import org.swrlapi.core.SWRLAPILiteralFactory;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIOntologyProcessor;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.sqwrl.DefaultSQWRLQuery;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.SQWRLResultGenerator;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.exceptions.SQWRLInvalidQueryNameException;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;

public class DefaultSWRLAPIOntologyProcessor implements SWRLAPIOntologyProcessor
{
	private final SWRLAPIOWLOntology swrlapiOWLOntology;

	private final Map<String, SWRLAPIRule> swrlapiRules; // SWRL rules include SQWRL queries
	private final Map<String, SQWRLQuery> sqwrlQueries;

	private final Set<OWLAxiom> assertedOWLAxioms; // All asserted OWL axioms extracted from the supplied ontology

	private final Map<IRI, OWLDeclarationAxiom> owlClassDeclarationAxioms;
	private final Map<IRI, OWLDeclarationAxiom> owlIndividualDeclarationAxioms;
	private final Map<IRI, OWLDeclarationAxiom> owlObjectPropertyDeclarationAxioms;
	private final Map<IRI, OWLDeclarationAxiom> owlDataPropertyDeclarationAxioms;
	private final Map<IRI, OWLDeclarationAxiom> owlAnnotationPropertyDeclarationAxioms;

	public DefaultSWRLAPIOntologyProcessor(SWRLAPIOWLOntology swrlapiOWLOntology)
	{
		this.swrlapiOWLOntology = swrlapiOWLOntology;

		this.swrlapiRules = new HashMap<>();
		this.sqwrlQueries = new HashMap<>();

		this.assertedOWLAxioms = new HashSet<>();

		this.owlClassDeclarationAxioms = new HashMap<>();
		this.owlIndividualDeclarationAxioms = new HashMap<>();
		this.owlObjectPropertyDeclarationAxioms = new HashMap<>();
		this.owlDataPropertyDeclarationAxioms = new HashMap<>();
		this.owlAnnotationPropertyDeclarationAxioms = new HashMap<>();
	}

	@Override
	public void reset()
	{
		this.swrlapiRules.clear();
		this.sqwrlQueries.clear();

		getIRIResolver().reset();

		this.assertedOWLAxioms.clear();

		this.owlClassDeclarationAxioms.clear();
		this.owlIndividualDeclarationAxioms.clear();
		this.owlObjectPropertyDeclarationAxioms.clear();
		this.owlDataPropertyDeclarationAxioms.clear();
		this.owlAnnotationPropertyDeclarationAxioms.clear();
	}

	@Override
	public void processOntology() throws SQWRLException
	{
		reset();

		processSWRLRulesAndSQWRLQueries();
		processOWLAxioms();
	}

	@Override
	public SQWRLQuery getSQWRLQuery(String queryName) throws SQWRLException
	{
		if (!this.sqwrlQueries.containsKey(queryName))
			throw new SQWRLInvalidQueryNameException("invalid SQWRL query name " + queryName);

		return this.sqwrlQueries.get(queryName);
	}

	@Override
	public SWRLAPIRule getSWRLRule(String ruleName) throws SWRLRuleException
	{
		if (!this.swrlapiRules.containsKey(ruleName))
			throw new SWRLRuleException("invalid rule name " + ruleName);

		return this.swrlapiRules.get(ruleName);
	}

	@Override
	public void deleteSWRLRule(String ruleName)
	{
		if (swrlapiRules.containsKey(ruleName)) {
			SWRLAPIRule rule = swrlapiRules.get(ruleName);

			if (rule.isSQWRLQuery())
				this.sqwrlQueries.remove(ruleName);

			this.swrlapiRules.remove(ruleName);
		}

		SWRLRule owlapiRule = findRuleNamed(ruleName);

		if (owlapiRule != null)
			this.swrlapiOWLOntology.getOWLOntologyManager().removeAxiom(swrlapiOWLOntology.getOWLOntology(), owlapiRule);
	}

	@Override
	public void addSWRLRule(SWRLAPIRule swrlapiRule, SWRLRule owlapiRule)
	{
		String ruleName = swrlapiRule.getRuleName();

		this.swrlapiRules.put(ruleName, swrlapiRule);

		this.swrlapiOWLOntology.getOWLOntologyManager().addAxiom(swrlapiOWLOntology.getOWLOntology(), owlapiRule);
	}

	@Override
	public int getNumberOfSWRLRules()
	{
		return this.swrlapiRules.values().size();
	}

	@Override
	public int getNumberOfSQWRLQueries()
	{
		return this.sqwrlQueries.values().size();
	}

	@Override
	public Set<String> getSQWRLQueryNames()
	{
		return new HashSet<>(this.sqwrlQueries.keySet());
	}

	@Override
	public int getNumberOfOWLClassDeclarationAxioms()
	{
		return this.owlClassDeclarationAxioms.values().size();
	}

	@Override
	public int getNumberOfOWLIndividualDeclarationAxioms()
	{
		return this.owlIndividualDeclarationAxioms.values().size();
	}

	@Override
	public int getNumberOfOWLObjectPropertyDeclarationAxioms()
	{
		return this.owlObjectPropertyDeclarationAxioms.size();
	}

	@Override
	public int getNumberOfOWLDataPropertyDeclarationAxioms()
	{
		return this.owlDataPropertyDeclarationAxioms.size();
	}

	@Override
	public int getNumberOfOWLAxioms()
	{
		return this.assertedOWLAxioms.size();
	}

	@Override
	public Set<SQWRLQuery> getSQWRLQueries()
	{
		return new HashSet<>(this.sqwrlQueries.values());
	}

	@Override
	public Set<OWLAxiom> getOWLAxioms()
	{
		return Collections.unmodifiableSet(this.assertedOWLAxioms);
	}

	@Override
	public boolean hasAssertedOWLAxiom(OWLAxiom axiom)
	{
		return this.assertedOWLAxioms.contains(axiom);
	}

	@Override
	public SQWRLQuery createSWRLQueryFromSWRLRule(SWRLAPIRule rule) throws SQWRLException
	{
		String queryName = rule.getRuleName();
		boolean active = rule.isActive();
		String comment = rule.getComment();
		SQWRLQuery query = new DefaultSQWRLQuery(queryName, rule.getBodyAtoms(), rule.getHeadAtoms(), active, comment,
				getSWRLAPILiteralFactory(), getSQWRLResultValueFactory());
		this.sqwrlQueries.put(queryName, query);

		return query;
	}

	/**
	 * Get the results from a previously executed SQWRL query.
	 */
	@Override
	public SQWRLResult getSQWRLResult(String queryName) throws SQWRLException
	{
		if (!this.sqwrlQueries.containsKey(queryName))
			throw new SQWRLInvalidQueryNameException(queryName);

		return this.sqwrlQueries.get(queryName).getSQWRLResult();
	}

	/**
	 * Get the result generator for a SQWRL query.
	 */
	@Override
	public SQWRLResultGenerator getSQWRLResultGenerator(String queryName) throws SQWRLException
	{
		if (!this.sqwrlQueries.containsKey(queryName))
			throw new SQWRLInvalidQueryNameException(queryName);

		return this.sqwrlQueries.get(queryName).getSQWRLResultGenerator();
	}

	@Override
	public String getRuleName(SWRLRule owlapiRule)
	{
		OWLAnnotationProperty labelAnnotation = getOWLDataFactory().getOWLAnnotationProperty(
				OWLRDFVocabulary.RDFS_LABEL.getIRI());

		for (OWLAnnotation annotation : owlapiRule.getAnnotations(labelAnnotation)) {
			if (annotation.getValue() instanceof OWLLiteral) {
				OWLLiteral literal = (OWLLiteral)annotation.getValue();
				return literal.getLiteral(); // TODO We just pick one for the moment
			}
		}
		// TODO Also look for swrla#ruleName annotation
		return "";
	}

	@Override
	public boolean getIsActive(SWRLRule owlapiRule)
	{
		OWLAnnotationProperty enabledAnnotationProperty = getOWLDataFactory().getOWLAnnotationProperty(
				IRI.create("http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled"));

		for (OWLAnnotation annotation : owlapiRule.getAnnotations(enabledAnnotationProperty)) {
			if (annotation.getValue() instanceof OWLLiteral) {
				OWLLiteral literal = (OWLLiteral)annotation.getValue();
				if (literal.isBoolean())
					return literal.parseBoolean();
			}
		}
		return true;
	}

	@Override
	public String getComment(SWRLRule owlapiRule)
	{
		OWLAnnotationProperty commentAnnotationProperty = getOWLDataFactory().getOWLAnnotationProperty(
				OWLRDFVocabulary.RDFS_COMMENT.getIRI());

		for (OWLAnnotation annotation : owlapiRule.getAnnotations(commentAnnotationProperty)) {
			if (annotation.getValue() instanceof OWLLiteral) {
				OWLLiteral literal = (OWLLiteral)annotation.getValue();
				return literal.getLiteral(); // TODO We just pick one for the moment
			}
		}
		return "";
	}

	private SWRLRule findRuleNamed(String ruleName)
	{ // TODO Not efficient - probably a better way
		for (SWRLRule owlapiRule : getOWLOntology().getAxioms(AxiomType.SWRL_RULE, Imports.INCLUDED)) {
			if (ruleName.equals(getRuleName(owlapiRule)))
				return owlapiRule;
		}
		return null;
	}

	@SuppressWarnings("unused")
	private Set<String> getSWRLRuleNames()
	{
		return new HashSet<>(this.swrlapiRules.keySet());
	}

	@SuppressWarnings("unused")
	private Set<SWRLAPIRule> getSWRLRules()
	{
		return new HashSet<>(this.swrlapiRules.values());
	}

	/**
	 * Process currently supported OWL axioms. The processing consists of recording any OWL properties in the processed
	 * axioms (with an instance of the {@link org.swrlapi.core.resolvers.IRIResolver} class) and generating declaration
	 * axioms for these properties.
	 * <p/>
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
		for (SWRLAPIRule ruleOrQuery : getSWRLAPIOWLOntology().getSWRLAPIRules())
			processSWRLRuleOrSQWRLQuery(ruleOrQuery);
	}

	private void processSWRLRuleOrSQWRLQuery(SWRLAPIRule ruleOrQuery) throws SQWRLException
	{
		if (ruleOrQuery.isSQWRLQuery()) {
			createSWRLQueryFromSWRLRule(ruleOrQuery);
			this.swrlapiRules.put(ruleOrQuery.getRuleName(), ruleOrQuery);
		} else {
			this.swrlapiRules.put(ruleOrQuery.getRuleName(), ruleOrQuery);
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
			for (OWLIndividual individual : axiom.getIndividuals())
				generateOWLIndividualDeclarationAxiomIfNecessary(individual);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLDifferentIndividualsAxioms()
	{
		Set<OWLDifferentIndividualsAxiom> axioms = getOWLDifferentIndividualsAxioms();
		for (OWLDifferentIndividualsAxiom axiom : axioms) {
			for (OWLIndividual individual : axiom.getIndividuals())
				generateOWLIndividualDeclarationAxiomIfNecessary(individual);
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
			for (OWLClass cls : axiom.getNamedClasses())
				generateOWLClassDeclarationAxiom(cls);
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
			for (OWLObjectPropertyExpression property : axiom.getProperties())
				generateOWLObjectPropertyDeclarationAxiomIfNecessary(property);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLEquivalentDataPropertiesAxioms()
	{
		Set<OWLEquivalentDataPropertiesAxiom> axioms = getOWLEquivalentDataPropertiesAxioms();
		for (OWLEquivalentDataPropertiesAxiom axiom : axioms) {
			for (OWLDataPropertyExpression property : axiom.getProperties())
				generateOWLDataPropertyDeclarationAxiomIfNecessary(property);
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
			for (OWLObjectPropertyExpression property : axiom.getProperties())
				generateOWLObjectPropertyDeclarationAxiomIfNecessary(property);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLDisjointDataPropertiesAxioms()
	{
		Set<OWLDisjointDataPropertiesAxiom> axioms = getOWLDisjointDataPropertiesAxioms();
		for (OWLDisjointDataPropertiesAxiom axiom : axioms) {
			for (OWLDataPropertyExpression property : axiom.getProperties())
				generateOWLDataPropertyDeclarationAxiomIfNecessary(property);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void generateOWLClassDeclarationAxiom(OWLClass cls)
	{
		if (!this.owlClassDeclarationAxioms.containsKey(cls.getIRI())) {
			OWLDeclarationAxiom axiom = getSWRLAPIOWLDataFactory().getOWLClassDeclarationAxiom(cls);
			this.owlClassDeclarationAxioms.put(cls.getIRI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			recordOWLClass(cls);
		}
	}

	private void generateOWLClassDeclarationAxiomIfNecessary(OWLClassExpression classExpression)
	{
		if (classExpression instanceof OWLClass) {
			OWLClass cls = (OWLClass)classExpression;
			generateOWLClassDeclarationAxiom(cls);
		}
	}

	private void generateOWLIndividualDeclarationAxiomIfNecessary(OWLIndividual individual)
	{
		if (individual.isNamed()
				&& !this.owlIndividualDeclarationAxioms.containsKey(individual.asOWLNamedIndividual().getIRI())) {
			OWLDeclarationAxiom axiom = getSWRLAPIOWLDataFactory().getOWLIndividualDeclarationAxiom(
					individual.asOWLNamedIndividual());
			this.owlIndividualDeclarationAxioms.put(individual.asOWLNamedIndividual().getIRI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			recordOWLNamedIndividual(individual.asOWLNamedIndividual());
		}
	}

	private void generateOWLObjectPropertyDeclarationAxiomIfNecessary(OWLObjectPropertyExpression propertyExpression)
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

	private void generateOWLDataPropertyDeclarationAxiomIfNecessary(OWLDataPropertyExpression propertyExpression)
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

	private Set<OWLSameIndividualAxiom> getOWLSameIndividualAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.SAME_INDIVIDUAL, Imports.INCLUDED);
	}

	private Set<OWLDifferentIndividualsAxiom> getOWLDifferentIndividualsAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.DIFFERENT_INDIVIDUALS, Imports.INCLUDED);
	}

	private Set<OWLSubObjectPropertyOfAxiom> getOWLSubObjectPropertyOfAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.SUB_OBJECT_PROPERTY, Imports.INCLUDED);
	}

	private Set<OWLSubDataPropertyOfAxiom> getOWLSubDataPropertyOfAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.SUB_DATA_PROPERTY, Imports.INCLUDED);
	}

	private Set<OWLEquivalentClassesAxiom> getOWLEquivalentClassesAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.EQUIVALENT_CLASSES, Imports.INCLUDED);
	}

	private Set<OWLClassAssertionAxiom> getOWLClassAssertionAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.CLASS_ASSERTION, Imports.INCLUDED);
	}

	private Set<OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION, Imports.INCLUDED);
	}

	private Set<OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.DATA_PROPERTY_ASSERTION, Imports.INCLUDED);
	}

	private Set<OWLSubClassOfAxiom> getOWLSubClassOfAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.SUBCLASS_OF, Imports.INCLUDED);
	}

	@SuppressWarnings("unused")
	private Set<OWLDisjointClassesAxiom> getOWLDisjointClassesAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.DISJOINT_CLASSES, Imports.INCLUDED);
	}

	private Set<OWLEquivalentDataPropertiesAxiom> getOWLEquivalentDataPropertiesAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.EQUIVALENT_DATA_PROPERTIES, Imports.INCLUDED);
	}

	private Set<OWLEquivalentObjectPropertiesAxiom> getOWLEquivalentObjectPropertiesAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES, Imports.INCLUDED);
	}

	private Set<OWLDisjointDataPropertiesAxiom> getOWLDisjointDataPropertiesAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.DISJOINT_DATA_PROPERTIES, Imports.INCLUDED);
	}

	private Set<OWLDisjointObjectPropertiesAxiom> getOWLDisjointObjectPropertiesAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.DISJOINT_OBJECT_PROPERTIES, Imports.INCLUDED);
	}

	private Set<OWLObjectPropertyDomainAxiom> getOWLObjectPropertyDomainAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN, Imports.INCLUDED);
	}

	private Set<OWLDataPropertyDomainAxiom> getOWLDataPropertyDomainAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.DATA_PROPERTY_DOMAIN, Imports.INCLUDED);
	}

	private Set<OWLObjectPropertyRangeAxiom> getOWLObjectPropertyRangeAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.OBJECT_PROPERTY_RANGE, Imports.INCLUDED);
	}

	private Set<OWLDataPropertyRangeAxiom> getOWLDataPropertyRangeAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.DATA_PROPERTY_RANGE, Imports.INCLUDED);
	}

	private Set<OWLFunctionalObjectPropertyAxiom> getOWLFunctionalObjectPropertyAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.FUNCTIONAL_OBJECT_PROPERTY, Imports.INCLUDED);
	}

	private Set<OWLFunctionalDataPropertyAxiom> getOWLFunctionalDataPropertyAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.FUNCTIONAL_DATA_PROPERTY, Imports.INCLUDED);
	}

	private Set<OWLIrreflexiveObjectPropertyAxiom> getOWLIrreflexiveObjectPropertyAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.IRREFLEXIVE_OBJECT_PROPERTY, Imports.INCLUDED);
	}

	private Set<OWLInverseFunctionalObjectPropertyAxiom> getOWLInverseFunctionalObjectPropertyAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY, Imports.INCLUDED);
	}

	private Set<OWLTransitiveObjectPropertyAxiom> getOWLTransitiveObjectPropertyAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.TRANSITIVE_OBJECT_PROPERTY, Imports.INCLUDED);
	}

	private Set<OWLSymmetricObjectPropertyAxiom> getOWLSymmetricObjectPropertyAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.SYMMETRIC_OBJECT_PROPERTY, Imports.INCLUDED);
	}

	private Set<OWLAsymmetricObjectPropertyAxiom> getOWLAsymmetricObjectPropertyAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.ASYMMETRIC_OBJECT_PROPERTY, Imports.INCLUDED);
	}

	private Set<OWLInverseObjectPropertiesAxiom> getOWLInverseObjectPropertiesAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.INVERSE_OBJECT_PROPERTIES, Imports.INCLUDED);
	}

	@SuppressWarnings("unused")
	private Set<OWLNegativeDataPropertyAssertionAxiom> getOWLNegativeDataPropertyAssertionAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.NEGATIVE_DATA_PROPERTY_ASSERTION, Imports.INCLUDED);
	}

	@SuppressWarnings("unused")
	private Set<OWLNegativeObjectPropertyAssertionAxiom> getOWLNegativeObjectPropertyAssertionAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION, Imports.INCLUDED);
	}

	@SuppressWarnings("unused")
	private Set<OWLReflexiveObjectPropertyAxiom> getOWLReflexiveObjectPropertyAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.REFLEXIVE_OBJECT_PROPERTY, Imports.INCLUDED);
	}

	@SuppressWarnings("unused")
	private Set<OWLDisjointUnionAxiom> getOWLDisjointUnionAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.DISJOINT_UNION, Imports.INCLUDED);
	}

	@SuppressWarnings("unused")
	private Set<OWLAnnotationAssertionAxiom> getOWLAnnotationAssertionAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.ANNOTATION_ASSERTION, Imports.INCLUDED);
	}

	@SuppressWarnings("unused")
	private Set<OWLSubPropertyChainOfAxiom> getOWLSubPropertyChainOfAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF, Imports.INCLUDED);
	}

	@SuppressWarnings("unused")
	private Set<OWLHasKeyAxiom> getOWLHasKeyAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.HAS_KEY, Imports.INCLUDED);
	}

	@SuppressWarnings("unused")
	private Set<OWLDatatypeDefinitionAxiom> getOWLDatatypeDefinitionAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.DATATYPE_DEFINITION, Imports.INCLUDED);
	}

	@SuppressWarnings("unused")
	private Set<OWLAnnotationPropertyRangeAxiom> getOWLAnnotationPropertyRangeAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.ANNOTATION_PROPERTY_RANGE, Imports.INCLUDED);
	}

	@SuppressWarnings("unused")
	private Set<OWLAnnotationPropertyDomainAxiom> getOWLAnnotationPropertyDomainAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.ANNOTATION_PROPERTY_DOMAIN, Imports.INCLUDED);
	}

	@SuppressWarnings("unused")
	private Set<OWLSubAnnotationPropertyOfAxiom> getOWLSubAnnotationPropertyOfAxioms()
	{
		return getOWLOntology().getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF, Imports.INCLUDED);
	}

	private Set<OWLDeclarationAxiom> getOWLClassDeclarationAxioms()
	{
		Set<OWLDeclarationAxiom> owlClassDeclarationAxioms = new HashSet<>();

		for (OWLDeclarationAxiom owlDeclarationAxiom : getOWLOntology().getAxioms(AxiomType.DECLARATION, Imports.INCLUDED)) {
			if (owlDeclarationAxiom.getEntity().isOWLClass())
				owlClassDeclarationAxioms.add(owlDeclarationAxiom);
		}
		return owlClassDeclarationAxioms;
	}

	private Set<OWLDeclarationAxiom> getOWLIndividualDeclarationAxioms()
	{
		Set<OWLDeclarationAxiom> owlIndividualDeclarationAxioms = new HashSet<>();

		for (OWLDeclarationAxiom owlDeclarationAxiom : getOWLOntology().getAxioms(AxiomType.DECLARATION, Imports.INCLUDED)) {
			if (owlDeclarationAxiom.getEntity().isOWLNamedIndividual())
				owlIndividualDeclarationAxioms.add(owlDeclarationAxiom);
		}
		return owlIndividualDeclarationAxioms;
	}

	private Set<OWLDeclarationAxiom> getOWLObjectPropertyDeclarationAxioms()
	{
		Set<OWLDeclarationAxiom> owlObjectPropertyDeclarationAxioms = new HashSet<>();

		for (OWLDeclarationAxiom owlDeclarationAxiom : getOWLOntology().getAxioms(AxiomType.DECLARATION, Imports.INCLUDED)) {
			if (owlDeclarationAxiom.getEntity().isOWLObjectProperty())
				owlObjectPropertyDeclarationAxioms.add(owlDeclarationAxiom);
		}
		return owlObjectPropertyDeclarationAxioms;
	}

	private Set<OWLDeclarationAxiom> getOWLDataPropertyDeclarationAxioms()
	{
		Set<OWLDeclarationAxiom> owlDataPropertyDeclarationAxioms = new HashSet<>();

		for (OWLDeclarationAxiom owlDeclarationAxiom : getOWLOntology().getAxioms(AxiomType.DECLARATION, Imports.INCLUDED)) {
			if (owlDeclarationAxiom.getEntity().isOWLDataProperty())
				owlDataPropertyDeclarationAxioms.add(owlDeclarationAxiom);
		}
		return owlDataPropertyDeclarationAxioms;
	}

	private Set<OWLDeclarationAxiom> getOWLAnnotationPropertyDeclarationAxioms()
	{
		Set<OWLDeclarationAxiom> owlAnnotationPropertyDeclarationAxioms = new HashSet<>();

		for (OWLDeclarationAxiom owlDeclarationAxiom : getOWLOntology().getAxioms(AxiomType.DECLARATION, Imports.INCLUDED)) {
			if (owlDeclarationAxiom.getEntity().isOWLAnnotationProperty())
				owlAnnotationPropertyDeclarationAxioms.add(owlDeclarationAxiom);
		}
		return owlAnnotationPropertyDeclarationAxioms;
	}

	@SuppressWarnings("unused")
	private Set<OWLDeclarationAxiom> getOWLDatatypeDeclarationAxioms()
	{
		Set<OWLDeclarationAxiom> owlDatatypeDeclarationAxioms = new HashSet<>();

		for (OWLDeclarationAxiom owlDeclarationAxiom : getOWLOntology().getAxioms(AxiomType.DECLARATION, Imports.INCLUDED)) {
			if (owlDeclarationAxiom.getEntity().isOWLDatatype())
				owlDatatypeDeclarationAxioms.add(owlDeclarationAxiom);
		}
		return owlDatatypeDeclarationAxioms;
	}

	private void recordOWLClass(OWLEntity cls)
	{
		getIRIResolver().recordOWLClass(cls);
	}

	private void recordOWLNamedIndividual(OWLEntity individual)
	{
		getIRIResolver().recordOWLNamedIndividual(individual);
	}

	private void recordOWLObjectProperty(OWLEntity property)
	{
		getIRIResolver().recordOWLObjectProperty(property);
	}

	private void recordOWLDataProperty(OWLEntity property)
	{
		getIRIResolver().recordOWLDataProperty(property);
	}

	private void recordOWLAnnotationProperty(OWLEntity property)
	{
		getIRIResolver().recordOWLAnnotationProperty(property);
	}

	private SWRLAPIOWLOntology getSWRLAPIOWLOntology()
	{
		return this.swrlapiOWLOntology;
	}

	private OWLOntology getOWLOntology()
	{
		return getSWRLAPIOWLOntology().getOWLOntology();
	}

	private IRIResolver getIRIResolver()
	{
		return getSWRLAPIOWLOntology().getIRIResolver();
	}

	private SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
	{
		return getSWRLAPIOWLOntology().getSWRLAPIOWLDataFactory();
	}

	private OWLDataFactory getOWLDataFactory()
	{
		return getSWRLAPIOWLOntology().getOWLDataFactory();
	}

	private SWRLAPILiteralFactory getSWRLAPILiteralFactory()
	{
		return getSWRLAPIOWLDataFactory().getSWRLAPILiteralFactory();
	}

	private SQWRLResultValueFactory getSQWRLResultValueFactory()
	{
		return getSWRLAPIOWLDataFactory().getSQWRLResultValueFactory();
	}

}