package org.protege.swrlapi.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.protege.swrlapi.exceptions.SWRLRuleException;
import org.protege.swrlapi.ext.SWRLAPIOWLDataFactory;
import org.protege.swrlapi.ext.SWRLAPIOWLOntology;
import org.protege.swrlapi.ext.SWRLAPIRule;
import org.protege.swrlapi.ext.impl.DefaultSWRLAPIOWLDataFactory;
import org.protege.swrlapi.sqwrl.DefaultSQWRLQuery;
import org.protege.swrlapi.sqwrl.SQWRLNames;
import org.protege.swrlapi.sqwrl.SQWRLQuery;
import org.protege.swrlapi.sqwrl.SQWRLResult;
import org.protege.swrlapi.sqwrl.SQWRLResultGenerator;
import org.protege.swrlapi.sqwrl.exceptions.SQWRLException;
import org.protege.swrlapi.sqwrl.exceptions.SQWRLInvalidQueryNameException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

public class DefaultSWRLOntologyProcessor implements SWRLOntologyProcessor
{
	private final SWRLAPIOWLOntology owlOntology;
	private final SWRLAPIOWLDataFactory dataFactory;
	private final OWLNamedObjectResolver namedObjectResolver;

	private final HashMap<String, SWRLAPIRule> rules;
	private final HashMap<String, SQWRLQuery> queries;

	private final Set<OWLAxiom> assertedOWLAxioms; // All asserted OWL axioms extracted from the active ontology

	private final HashMap<IRI, OWLClassDeclarationAxiom> owlClassDeclarationAxioms;
	private final HashMap<IRI, OWLIndividualDeclarationAxiom> owlIndividualDeclarationAxioms;
	private final HashMap<IRI, OWLObjectPropertyDeclarationAxiom> owlObjectPropertyDeclarationAxioms;
	private final HashMap<IRI, OWLDataPropertyDeclarationAxiom> owlDataPropertyDeclarationAxioms;
	private final HashMap<IRI, OWLAnnotationPropertyDeclarationAxiom> owlAnnotationPropertyDeclarationAxioms;

	public DefaultSWRLOntologyProcessor(SWRLAPIOWLOntology owlOntology) throws SQWRLException
	{
		this.owlOntology = owlOntology;
		this.dataFactory = new DefaultSWRLAPIOWLDataFactory(owlOntology);
		this.namedObjectResolver = new OWLNamedObjectResolver();

		this.rules = new HashMap<String, SWRLAPIRule>();
		this.queries = new HashMap<String, SQWRLQuery>();

		this.assertedOWLAxioms = new HashSet<OWLAxiom>();
		this.owlClassDeclarationAxioms = new HashMap<IRI, OWLClassDeclarationAxiom>();
		this.owlObjectPropertyDeclarationAxioms = new HashMap<IRI, OWLObjectPropertyDeclarationAxiom>();
		this.owlDataPropertyDeclarationAxioms = new HashMap<IRI, OWLDataPropertyDeclarationAxiom>();
		this.owlAnnotationPropertyDeclarationAxioms = new HashMap<IRI, OWLAnnotationPropertyDeclarationAxiom>();
		this.owlIndividualDeclarationAxioms = new HashMap<IRI, OWLIndividualDeclarationAxiom>();

		processOntology();
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
		if (!this.queries.containsKey(queryName))
			throw new SQWRLInvalidQueryNameException("invalid SQWRL query name " + queryName);

		return this.queries.get(queryName);
	}

	@Override
	public SWRLAPIRule getSWRLRule(String ruleName) throws SWRLRuleException
	{
		if (!this.rules.containsKey(ruleName))
			throw new SWRLRuleException("invalid rule name " + ruleName);

		return this.rules.get(ruleName);
	}

	@Override
	public int getNumberOfSWRLRules()
	{
		return this.rules.values().size();
	}

	@Override
	public int getNumberOfSQWRLQueries()
	{
		return this.queries.values().size();
	}

	@Override
	public Set<String> getSWRLRuleNames()
	{
		return new HashSet<String>(this.rules.keySet());
	}

	@Override
	public Set<String> getSQWRLQueryNames()
	{
		return new HashSet<String>(this.queries.keySet());
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
	public Set<SWRLAPIRule> getSWRLRules()
	{
		return new HashSet<SWRLAPIRule>(this.rules.values());
	}

	@Override
	public Set<SQWRLQuery> getSQWRLQueries()
	{
		return new HashSet<SQWRLQuery>(this.queries.values());
	}

	@Override
	public Set<OWLAxiom> getOWLAxioms()
	{
		return Collections.unmodifiableSet(this.assertedOWLAxioms);
	}

	@Override
	public boolean hasOWLAxiom(OWLAxiom axiom)
	{
		return this.assertedOWLAxioms.contains(axiom);
	}

	public boolean isSQWRLQuery(String queryName)
	{
		return this.queries.containsKey(queryName);
	}

	/**
	 * Get the results from a previously executed SQWRL query.
	 */
	@Override
	public SQWRLResult getSQWRLResult(String queryName) throws SQWRLException
	{
		if (!this.queries.containsKey(queryName))
			throw new SQWRLInvalidQueryNameException(queryName);

		return this.queries.get(queryName).getResult();
	}

	/**
	 * Get the result generator for a SQWRL query.
	 */
	@Override
	public SQWRLResultGenerator getSQWRLResultGenerator(String queryName) throws SQWRLException
	{
		if (!this.queries.containsKey(queryName))
			throw new SQWRLInvalidQueryNameException(queryName);

		return this.queries.get(queryName).getResultGenerator();
	}

	@Override
	public OWLNamedObjectResolver getOWLNamedObjectResolver()
	{
		return this.namedObjectResolver;
	}

	private void reset()
	{
		this.rules.clear();
		this.queries.clear();
		this.assertedOWLAxioms.clear();
		this.owlClassDeclarationAxioms.clear();
		this.owlObjectPropertyDeclarationAxioms.clear();
		this.owlDataPropertyDeclarationAxioms.clear();
		this.owlAnnotationPropertyDeclarationAxioms.clear();
		this.owlIndividualDeclarationAxioms.clear();
		this.namedObjectResolver.reset();
	}

	/**
	 * Process currently supported OWL axioms. The processing consists of recording any OWL entities in the processed
	 * axioms (with an instance of the {@link OWLNamedObjectResolver} class) and generating declaration axioms for these
	 * entities.
	 * <p>
	 * TODO The current approach is clunky. A better approach would be to walk the axioms with a visitor and record the
	 * entities and generate the declaration axioms.
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
		for (SWRLAPIRule ruleOrQuery : getOWLOntology().getSWRLRules())
			processSWRLRuleOrSQWRLQuery(ruleOrQuery);
	}

	private void processSWRLRuleOrSQWRLQuery(SWRLAPIRule ruleOrQuery) throws SQWRLException
	{
		if (isSQWRLQuery(ruleOrQuery)) {
			SQWRLQuery query = new DefaultSQWRLQuery(ruleOrQuery.getName(), ruleOrQuery.getBodyAtoms(),
					ruleOrQuery.getHeadAtoms());
			this.queries.put(ruleOrQuery.getName(), query);
		} else {
			this.rules.put(ruleOrQuery.getName(), ruleOrQuery);
			this.assertedOWLAxioms.add(ruleOrQuery); // A SWRL rule is a type of OWL axiom; a SQWRL query is not.
		}
	}

	private boolean isSQWRLQuery(SWRLAPIRule ruleOrQuery)
	{
		return !ruleOrQuery.getBuiltInAtomsFromHead(SQWRLNames.getSQWRLBuiltInNames()).isEmpty()
				|| !ruleOrQuery.getBuiltInAtomsFromBody(SQWRLNames.getSQWRLBuiltInNames()).isEmpty();
	}

	private void processOWLClassAssertionAxioms()
	{
		for (OWLClassAssertionAxiom axiom : getOWLOntology().getOWLClassAssertionAxioms()) {
			generateOWLIndividualDeclarationAxiomIfNecessary(axiom.getIndividual());
			this.assertedOWLAxioms.add(axiom);
		}
	}

	private void processOWLObjectPropertyAssertionAxioms()
	{
		for (OWLObjectPropertyAssertionAxiom axiom : getOWLOntology().getOWLObjectPropertyAssertionAxioms()) {
			generateOWLIndividualDeclarationAxiomIfNecessary(axiom.getSubject());
			generateOWLIndividualDeclarationAxiomIfNecessary(axiom.getObject());
			this.assertedOWLAxioms.add(axiom);
		}
	}

	private void processOWLDataPropertyAssertionAxioms()
	{
		for (OWLDataPropertyAssertionAxiom axiom : getOWLOntology().getOWLDataPropertyAssertionAxioms()) {
			generateOWLIndividualDeclarationAxiomIfNecessary(axiom.getSubject());
			this.assertedOWLAxioms.add(axiom);
		}
	}

	private void processOWLClassDeclarationAxioms()
	{
		for (OWLClassDeclarationAxiom axiom : getOWLOntology().getOWLClassDeclarationAxioms()) {
			OWLClass cls = axiom.getEntity();
			this.owlClassDeclarationAxioms.put(cls.getIRI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			record(cls);
		}
	}

	private void processOWLIndividualDeclarationAxioms()
	{
		for (OWLIndividualDeclarationAxiom axiom : getOWLOntology().getOWLIndividualDeclarationAxioms()) {
			OWLNamedIndividual individual = axiom.getEntity();
			this.owlIndividualDeclarationAxioms.put(individual.getIRI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			record(individual);
		}
	}

	private void processOWLObjectPropertyDeclarationAxioms()
	{
		for (OWLObjectPropertyDeclarationAxiom axiom : getOWLOntology().getOWLObjectPropertyDeclarationAxioms()) {
			OWLObjectProperty property = axiom.getEntity();
			this.owlObjectPropertyDeclarationAxioms.put(property.getIRI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			record(property);
		}
	}

	private void processOWLDataPropertyDeclarationAxioms()
	{
		for (OWLDataPropertyDeclarationAxiom axiom : getOWLOntology().getOWLDataPropertyDeclarationAxioms()) {
			OWLDataProperty property = axiom.getEntity();

			this.owlDataPropertyDeclarationAxioms.put(property.getIRI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			record(property);
		}
	}

	private void processOWLAnnotationPropertyDeclarationAxioms()
	{
		for (OWLAnnotationPropertyDeclarationAxiom axiom : getOWLOntology().getOWLAnnotationPropertyDeclarationAxioms()) {
			OWLAnnotationProperty property = axiom.getEntity();

			this.owlAnnotationPropertyDeclarationAxioms.put(property.getIRI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			record(property);
		}
	}

	private void processOWLSameIndividualAxioms()
	{
		Set<OWLSameIndividualAxiom> axioms = getOWLOntology().getOWLSameIndividualAxioms();
		for (OWLSameIndividualAxiom axiom : axioms) {
			for (OWLIndividual individual : axiom.getIndividuals())
				generateOWLIndividualDeclarationAxiomIfNecessary(individual);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLDifferentIndividualsAxioms()
	{
		Set<OWLDifferentIndividualsAxiom> axioms = getOWLOntology().getOWLDifferentIndividualsAxioms();
		for (OWLDifferentIndividualsAxiom axiom : axioms) {
			for (OWLIndividual individual : axiom.getIndividuals())
				generateOWLIndividualDeclarationAxiomIfNecessary(individual);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLSubClassOfAxioms()
	{
		Set<OWLSubClassOfAxiom> axioms = getOWLOntology().getOWLSubClassOfAxioms();
		for (OWLSubClassOfAxiom axiom : axioms) {
			generateOWLClassDeclarationAxiomIfNecessary(axiom.getSubClass());
			generateOWLClassDeclarationAxiomIfNecessary(axiom.getSuperClass());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLEquivalentClassesAxioms()
	{
		Set<OWLEquivalentClassesAxiom> axioms = getOWLOntology().getOWLEquivalentClassesAxioms();
		for (OWLEquivalentClassesAxiom axiom : axioms) {
			for (OWLClass cls : axiom.getNamedClasses())
				generateOWLClassDeclarationAxiom(cls);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLSubObjectPropertyOfAxioms()
	{
		Set<OWLSubObjectPropertyOfAxiom> axioms = getOWLOntology().getOWLSubObjectPropertyOfAxioms();
		for (OWLSubObjectPropertyOfAxiom axiom : axioms) {
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getSubProperty());
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getSuperProperty());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLSubDataPropertyOfAxioms()
	{
		Set<OWLSubDataPropertyOfAxiom> axioms = getOWLOntology().getOWLSubDataPropertyOfAxioms();
		for (OWLSubDataPropertyOfAxiom axiom : axioms) {
			generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getSubProperty());
			generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getSuperProperty());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLTransitiveObjectPropertyAxioms()
	{
		Set<OWLTransitiveObjectPropertyAxiom> axioms = getOWLOntology().getOWLTransitiveObjectPropertyAxioms();
		for (OWLTransitiveObjectPropertyAxiom axiom : axioms)
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLSymmetricObjectPropertyAxioms()
	{
		Set<OWLSymmetricObjectPropertyAxiom> axioms = getOWLOntology().getOWLSymmetricObjectPropertyAxioms();
		for (OWLSymmetricObjectPropertyAxiom axiom : axioms)
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLFunctionalObjectPropertyAxioms()
	{
		Set<OWLFunctionalObjectPropertyAxiom> axioms = getOWLOntology().getOWLFunctionalObjectPropertyAxioms();
		for (OWLFunctionalObjectPropertyAxiom axiom : axioms)
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLInverseFunctionalObjectPropertyAxioms()
	{
		Set<OWLInverseFunctionalObjectPropertyAxiom> axioms = getOWLOntology()
				.getOWLInverseFunctionalObjectPropertyAxioms();
		for (OWLInverseFunctionalObjectPropertyAxiom axiom : axioms)
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLFunctionalDataPropertyAxioms()
	{
		Set<OWLFunctionalDataPropertyAxiom> axioms = getOWLOntology().getOWLFunctionalDataPropertyAxioms();
		for (OWLFunctionalDataPropertyAxiom axiom : axioms)
			generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLObjectPropertyDomainAxioms()
	{
		Set<OWLObjectPropertyDomainAxiom> axioms = getOWLOntology().getOWLObjectPropertyDomainAxioms();
		for (OWLObjectPropertyDomainAxiom axiom : axioms) {
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
			generateOWLClassDeclarationAxiomIfNecessary(axiom.getDomain());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLDataPropertyDomainAxioms()
	{
		Set<OWLDataPropertyDomainAxiom> axioms = getOWLOntology().getOWLDataPropertyDomainAxioms();
		for (OWLDataPropertyDomainAxiom axiom : axioms) {
			generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
			generateOWLClassDeclarationAxiomIfNecessary(axiom.getDomain());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLObjectPropertyRangeAxioms()
	{
		Set<OWLObjectPropertyRangeAxiom> axioms = getOWLOntology().getOWLObjectPropertyRangeAxioms();
		for (OWLObjectPropertyRangeAxiom axiom : axioms) {
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
			generateOWLClassDeclarationAxiomIfNecessary(axiom.getRange());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLDataPropertyRangeAxioms()
	{
		Set<OWLDataPropertyRangeAxiom> axioms = getOWLOntology().getOWLDataPropertyRangeAxioms();
		for (OWLDataPropertyRangeAxiom axiom : axioms) {
			generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLIrreflexiveObjectPropertyAxioms()
	{
		Set<OWLIrreflexiveObjectPropertyAxiom> axioms = getOWLOntology().getOWLIrreflexiveObjectPropertyAxioms();
		for (OWLIrreflexiveObjectPropertyAxiom axiom : axioms)
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLAsymmetricObjectPropertyAxioms()
	{
		Set<OWLAsymmetricObjectPropertyAxiom> axioms = getOWLOntology().getOWLAsymmetricObjectPropertyAxioms();
		for (OWLAsymmetricObjectPropertyAxiom axiom : axioms)
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLEquivalentObjectPropertiesAxioms()
	{
		Set<OWLEquivalentObjectPropertiesAxiom> axioms = getOWLOntology().getOWLEquivalentObjectPropertiesAxioms();
		for (OWLEquivalentObjectPropertiesAxiom axiom : axioms) {
			for (OWLObjectPropertyExpression property : axiom.getProperties())
				generateOWLObjectPropertyDeclarationAxiomIfNecessary(property);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLEquivalentDataPropertiesAxioms()
	{
		Set<OWLEquivalentDataPropertiesAxiom> axioms = getOWLOntology().getOWLEquivalentDataPropertiesAxioms();
		for (OWLEquivalentDataPropertiesAxiom axiom : axioms) {
			for (OWLDataPropertyExpression property : axiom.getProperties())
				generateOWLDataPropertyDeclarationAxiomIfNecessary(property);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLInverseObjectPropertiesAxioms()
	{
		Set<OWLInverseObjectPropertiesAxiom> axioms = getOWLOntology().getOWLInverseObjectPropertiesAxioms();
		for (OWLInverseObjectPropertiesAxiom axiom : axioms) {
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getFirstProperty());
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getSecondProperty());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLDisjointObjectPropertiesAxioms()
	{
		Set<OWLDisjointObjectPropertiesAxiom> axioms = getOWLOntology().getOWLDisjointObjectPropertiesAxioms();
		for (OWLDisjointObjectPropertiesAxiom axiom : axioms) {
			for (OWLObjectPropertyExpression property : axiom.getProperties())
				generateOWLObjectPropertyDeclarationAxiomIfNecessary(property);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLDisjointDataPropertiesAxioms()
	{
		Set<OWLDisjointDataPropertiesAxiom> axioms = getOWLOntology().getOWLDisjointDataPropertiesAxioms();
		for (OWLDisjointDataPropertiesAxiom axiom : axioms) {
			for (OWLDataPropertyExpression property : axiom.getProperties())
				generateOWLDataPropertyDeclarationAxiomIfNecessary(property);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void generateOWLClassDeclarationAxiom(OWLClass cls)
	{
		if (!this.owlClassDeclarationAxioms.containsKey(cls.getIRI())) {
			OWLClassDeclarationAxiom axiom = getOWLDataFactory().getOWLClassDeclarationAxiom(cls);
			this.owlClassDeclarationAxioms.put(cls.getIRI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			record(cls);
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
			OWLIndividualDeclarationAxiom axiom = getOWLDataFactory().getOWLIndividualDeclarationAxiom(
					individual.asOWLNamedIndividual());
			this.owlIndividualDeclarationAxioms.put(individual.asOWLNamedIndividual().getIRI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			record(individual.asOWLNamedIndividual());
		}
	}

	private void generateOWLObjectPropertyDeclarationAxiomIfNecessary(OWLObjectPropertyExpression propertyExpression)
	{
		if (propertyExpression instanceof OWLObjectProperty) {
			OWLObjectProperty property = (OWLObjectProperty)propertyExpression;
			if (!this.owlObjectPropertyDeclarationAxioms.containsKey(property.getIRI())) {
				OWLObjectPropertyDeclarationAxiom axiom = getOWLDataFactory().getOWLObjectPropertyDeclarationAxiom(property);
				this.owlObjectPropertyDeclarationAxioms.put(property.getIRI(), axiom);
				this.assertedOWLAxioms.add(axiom);
				record(property);
			}
		}
	}

	private void generateOWLDataPropertyDeclarationAxiomIfNecessary(OWLDataPropertyExpression propertyExpression)
	{
		if (propertyExpression instanceof OWLDataProperty) {
			OWLDataProperty property = (OWLDataProperty)propertyExpression;
			if (!this.owlDataPropertyDeclarationAxioms.containsKey(property.getIRI())) {
				OWLDataPropertyDeclarationAxiom axiom = getOWLDataFactory().getOWLDataPropertyDeclarationAxiom(property);
				this.owlDataPropertyDeclarationAxioms.put(property.getIRI(), axiom);
				this.assertedOWLAxioms.add(axiom);
				record(property);
			}
		}
	}

	private void record(OWLClass cls)
	{
		getOWLNamedObjectResolver().record(cls);
	}

	private void record(OWLNamedIndividual individual)
	{
		getOWLNamedObjectResolver().record(individual);
	}

	private void record(OWLObjectProperty property)
	{
		getOWLNamedObjectResolver().record(property);
	}

	private void record(OWLDataProperty property)
	{
		getOWLNamedObjectResolver().record(property);
	}

	private void record(OWLAnnotationProperty property)
	{
		getOWLNamedObjectResolver().record(property);
	}

	private SWRLAPIOWLDataFactory getOWLDataFactory()
	{
		return this.dataFactory;
	}

	private SWRLAPIOWLOntology getOWLOntology()
	{
		return this.owlOntology;
	}
}