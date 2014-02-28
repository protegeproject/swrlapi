package org.protege.swrlapi.core;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.protege.owl.portability.axioms.OWLAnnotationPropertyDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLAsymmetricObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLAxiomAdapter;
import org.protege.owl.portability.axioms.OWLClassAssertionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLClassDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDataPropertyAssertionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDataPropertyDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDataPropertyDomainAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDataPropertyRangeAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDifferentIndividualsAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDisjointDataPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDisjointObjectPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLEquivalentClassesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLEquivalentDataPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLEquivalentObjectPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLFunctionalDataPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLFunctionalObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLIndividualDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLInverseFunctionalObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLInverseObjectPropertiesAxiomAdapter;
import org.protege.owl.portability.axioms.OWLIrreflexiveObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLObjectPropertyAssertionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLObjectPropertyDeclarationAxiomAdapter;
import org.protege.owl.portability.axioms.OWLObjectPropertyDomainAxiomAdapter;
import org.protege.owl.portability.axioms.OWLObjectPropertyRangeAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSameIndividualAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSubClassOfAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSubDataPropertyOfAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSubObjectPropertyOfAxiomAdapter;
import org.protege.owl.portability.axioms.OWLSymmetricObjectPropertyAxiomAdapter;
import org.protege.owl.portability.axioms.OWLTransitiveObjectPropertyAxiomAdapter;
import org.protege.owl.portability.model.OWLAnnotationPropertyAdapter;
import org.protege.owl.portability.model.OWLClassAdapter;
import org.protege.owl.portability.model.OWLClassExpressionAdapter;
import org.protege.owl.portability.model.OWLDataPropertyAdapter;
import org.protege.owl.portability.model.OWLDataPropertyExpressionAdapter;
import org.protege.owl.portability.model.OWLIndividualAdapter;
import org.protege.owl.portability.model.OWLNamedIndividualAdapter;
import org.protege.owl.portability.model.OWLObjectPropertyAdapter;
import org.protege.owl.portability.model.OWLObjectPropertyExpressionAdapter;
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

public class DefaultSWRLOntologyProcessor implements SWRLOntologyProcessor
{
	private final SWRLAPIOWLOntology owlOntology;
	private final SWRLAPIOWLDataFactory dataFactory;
	private final OWLNamedObjectResolver namedObjectResolver;

	private final HashMap<String, SWRLAPIRule> rules;
	private final HashMap<String, SQWRLQuery> queries;

	private final Set<OWLAxiomAdapter> assertedOWLAxioms; // All asserted OWL axioms extracted from the active ontology

	private final HashMap<URI, OWLClassDeclarationAxiomAdapter> owlClassDeclarationAxioms;
	private final HashMap<URI, OWLIndividualDeclarationAxiomAdapter> owlIndividualDeclarationAxioms;
	private final HashMap<URI, OWLObjectPropertyDeclarationAxiomAdapter> owlObjectPropertyDeclarationAxioms;
	private final HashMap<URI, OWLDataPropertyDeclarationAxiomAdapter> owlDataPropertyDeclarationAxioms;
	private final HashMap<URI, OWLAnnotationPropertyDeclarationAxiomAdapter> owlAnnotationPropertyDeclarationAxioms;

	public DefaultSWRLOntologyProcessor(SWRLAPIOWLOntology owlOntology) throws SQWRLException
	{
		this.owlOntology = owlOntology;
		this.dataFactory = new DefaultSWRLAPIOWLDataFactory(owlOntology);
		this.namedObjectResolver = new OWLNamedObjectResolver();

		this.rules = new HashMap<String, SWRLAPIRule>();
		this.queries = new HashMap<String, SQWRLQuery>();

		this.assertedOWLAxioms = new HashSet<OWLAxiomAdapter>();
		this.owlClassDeclarationAxioms = new HashMap<URI, OWLClassDeclarationAxiomAdapter>();
		this.owlObjectPropertyDeclarationAxioms = new HashMap<URI, OWLObjectPropertyDeclarationAxiomAdapter>();
		this.owlDataPropertyDeclarationAxioms = new HashMap<URI, OWLDataPropertyDeclarationAxiomAdapter>();
		this.owlAnnotationPropertyDeclarationAxioms = new HashMap<URI, OWLAnnotationPropertyDeclarationAxiomAdapter>();
		this.owlIndividualDeclarationAxioms = new HashMap<URI, OWLIndividualDeclarationAxiomAdapter>();

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
	public Set<OWLAxiomAdapter> getOWLAxioms()
	{
		return Collections.unmodifiableSet(this.assertedOWLAxioms);
	}

	@Override
	public boolean hasOWLAxiom(OWLAxiomAdapter axiom)
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
		for (OWLClassAssertionAxiomAdapter axiom : getOWLOntology().getOWLClassAssertionAxioms()) {
			generateOWLIndividualDeclarationAxiomIfNecessary(axiom.getIndividual());
			this.assertedOWLAxioms.add(axiom);
		}
	}

	private void processOWLObjectPropertyAssertionAxioms()
	{
		for (OWLObjectPropertyAssertionAxiomAdapter axiom : getOWLOntology().getOWLObjectPropertyAssertionAxioms()) {
			generateOWLIndividualDeclarationAxiomIfNecessary(axiom.getSubject());
			generateOWLIndividualDeclarationAxiomIfNecessary(axiom.getObject());
			this.assertedOWLAxioms.add(axiom);
		}
	}

	private void processOWLDataPropertyAssertionAxioms()
	{
		for (OWLDataPropertyAssertionAxiomAdapter axiom : getOWLOntology().getOWLDataPropertyAssertionAxioms()) {
			generateOWLIndividualDeclarationAxiomIfNecessary(axiom.getSubject());
			this.assertedOWLAxioms.add(axiom);
		}
	}

	private void processOWLClassDeclarationAxioms()
	{
		for (OWLClassDeclarationAxiomAdapter axiom : getOWLOntology().getOWLClassDeclarationAxioms()) {
			OWLClassAdapter cls = axiom.getEntity();
			this.owlClassDeclarationAxioms.put(cls.getURI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			record(cls);
		}
	}

	private void processOWLIndividualDeclarationAxioms()
	{
		for (OWLIndividualDeclarationAxiomAdapter axiom : getOWLOntology().getOWLIndividualDeclarationAxioms()) {
			OWLNamedIndividualAdapter individual = axiom.getEntity();
			this.owlIndividualDeclarationAxioms.put(individual.getURI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			record(individual);
		}
	}

	private void processOWLObjectPropertyDeclarationAxioms()
	{
		for (OWLObjectPropertyDeclarationAxiomAdapter axiom : getOWLOntology().getOWLObjectPropertyDeclarationAxioms()) {
			OWLObjectPropertyAdapter property = axiom.getEntity();
			this.owlObjectPropertyDeclarationAxioms.put(property.getURI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			record(property);
		}
	}

	private void processOWLDataPropertyDeclarationAxioms()
	{
		for (OWLDataPropertyDeclarationAxiomAdapter axiom : getOWLOntology().getOWLDataPropertyDeclarationAxioms()) {
			OWLDataPropertyAdapter property = axiom.getEntity();

			this.owlDataPropertyDeclarationAxioms.put(property.getURI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			record(property);
		}
	}

	private void processOWLAnnotationPropertyDeclarationAxioms()
	{
		for (OWLAnnotationPropertyDeclarationAxiomAdapter axiom : getOWLOntology()
				.getOWLAnnotationPropertyDeclarationAxioms()) {
			OWLAnnotationPropertyAdapter property = axiom.getEntity();

			this.owlAnnotationPropertyDeclarationAxioms.put(property.getURI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			record(property);
		}
	}

	private void processOWLSameIndividualAxioms()
	{
		Set<OWLSameIndividualAxiomAdapter> axioms = getOWLOntology().getOWLSameIndividualAxioms();
		for (OWLSameIndividualAxiomAdapter axiom : axioms) {
			for (OWLIndividualAdapter individual : axiom.getIndividuals())
				generateOWLIndividualDeclarationAxiomIfNecessary(individual);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLDifferentIndividualsAxioms()
	{
		Set<OWLDifferentIndividualsAxiomAdapter> axioms = getOWLOntology().getOWLDifferentIndividualsAxioms();
		for (OWLDifferentIndividualsAxiomAdapter axiom : axioms) {
			for (OWLIndividualAdapter individual : axiom.getIndividuals())
				generateOWLIndividualDeclarationAxiomIfNecessary(individual);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLSubClassOfAxioms()
	{
		Set<OWLSubClassOfAxiomAdapter> axioms = getOWLOntology().getOWLSubClassOfAxioms();
		for (OWLSubClassOfAxiomAdapter axiom : axioms) {
			generateOWLClassDeclarationAxiomIfNecessary(axiom.getSubClass());
			generateOWLClassDeclarationAxiomIfNecessary(axiom.getSuperClass());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLEquivalentClassesAxioms()
	{
		Set<OWLEquivalentClassesAxiomAdapter> axioms = getOWLOntology().getOWLEquivalentClassesAxioms();
		for (OWLEquivalentClassesAxiomAdapter axiom : axioms) {
			for (OWLClassAdapter cls : axiom.getNamedClasses())
				generateOWLClassDeclarationAxiom(cls);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLSubObjectPropertyOfAxioms()
	{
		Set<OWLSubObjectPropertyOfAxiomAdapter> axioms = getOWLOntology().getOWLSubObjectPropertyOfAxioms();
		for (OWLSubObjectPropertyOfAxiomAdapter axiom : axioms) {
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getSubProperty());
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getSuperProperty());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLSubDataPropertyOfAxioms()
	{
		Set<OWLSubDataPropertyOfAxiomAdapter> axioms = getOWLOntology().getOWLSubDataPropertyOfAxioms();
		for (OWLSubDataPropertyOfAxiomAdapter axiom : axioms) {
			generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getSubProperty());
			generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getSuperProperty());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLTransitiveObjectPropertyAxioms()
	{
		Set<OWLTransitiveObjectPropertyAxiomAdapter> axioms = getOWLOntology().getOWLTransitiveObjectPropertyAxioms();
		for (OWLTransitiveObjectPropertyAxiomAdapter axiom : axioms)
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLSymmetricObjectPropertyAxioms()
	{
		Set<OWLSymmetricObjectPropertyAxiomAdapter> axioms = getOWLOntology().getOWLSymmetricObjectPropertyAxioms();
		for (OWLSymmetricObjectPropertyAxiomAdapter axiom : axioms)
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLFunctionalObjectPropertyAxioms()
	{
		Set<OWLFunctionalObjectPropertyAxiomAdapter> axioms = getOWLOntology().getOWLFunctionalObjectPropertyAxioms();
		for (OWLFunctionalObjectPropertyAxiomAdapter axiom : axioms)
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLInverseFunctionalObjectPropertyAxioms()
	{
		Set<OWLInverseFunctionalObjectPropertyAxiomAdapter> axioms = getOWLOntology()
				.getOWLInverseFunctionalObjectPropertyAxioms();
		for (OWLInverseFunctionalObjectPropertyAxiomAdapter axiom : axioms)
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLFunctionalDataPropertyAxioms()
	{
		Set<OWLFunctionalDataPropertyAxiomAdapter> axioms = getOWLOntology().getOWLFunctionalDataPropertyAxioms();
		for (OWLFunctionalDataPropertyAxiomAdapter axiom : axioms)
			generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLObjectPropertyDomainAxioms()
	{
		Set<OWLObjectPropertyDomainAxiomAdapter> axioms = getOWLOntology().getOWLObjectPropertyDomainAxioms();
		for (OWLObjectPropertyDomainAxiomAdapter axiom : axioms) {
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
			generateOWLClassDeclarationAxiomIfNecessary(axiom.getDomain());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLDataPropertyDomainAxioms()
	{
		Set<OWLDataPropertyDomainAxiomAdapter> axioms = getOWLOntology().getOWLDataPropertyDomainAxioms();
		for (OWLDataPropertyDomainAxiomAdapter axiom : axioms) {
			generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
			generateOWLClassDeclarationAxiomIfNecessary(axiom.getDomain());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLObjectPropertyRangeAxioms()
	{
		Set<OWLObjectPropertyRangeAxiomAdapter> axioms = getOWLOntology().getOWLObjectPropertyRangeAxioms();
		for (OWLObjectPropertyRangeAxiomAdapter axiom : axioms) {
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
			generateOWLClassDeclarationAxiomIfNecessary(axiom.getRange());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLDataPropertyRangeAxioms()
	{
		Set<OWLDataPropertyRangeAxiomAdapter> axioms = getOWLOntology().getOWLDataPropertyRangeAxioms();
		for (OWLDataPropertyRangeAxiomAdapter axiom : axioms) {
			generateOWLDataPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLIrreflexiveObjectPropertyAxioms()
	{
		Set<OWLIrreflexiveObjectPropertyAxiomAdapter> axioms = getOWLOntology().getOWLIrreflexiveObjectPropertyAxioms();
		for (OWLIrreflexiveObjectPropertyAxiomAdapter axiom : axioms)
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLAsymmetricObjectPropertyAxioms()
	{
		Set<OWLAsymmetricObjectPropertyAxiomAdapter> axioms = getOWLOntology().getOWLAsymmetricObjectPropertyAxioms();
		for (OWLAsymmetricObjectPropertyAxiomAdapter axiom : axioms)
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getProperty());
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLEquivalentObjectPropertiesAxioms()
	{
		Set<OWLEquivalentObjectPropertiesAxiomAdapter> axioms = getOWLOntology().getOWLEquivalentObjectPropertiesAxioms();
		for (OWLEquivalentObjectPropertiesAxiomAdapter axiom : axioms) {
			for (OWLObjectPropertyExpressionAdapter property : axiom.getProperties())
				generateOWLObjectPropertyDeclarationAxiomIfNecessary(property);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLEquivalentDataPropertiesAxioms()
	{
		Set<OWLEquivalentDataPropertiesAxiomAdapter> axioms = getOWLOntology().getOWLEquivalentDataPropertiesAxioms();
		for (OWLEquivalentDataPropertiesAxiomAdapter axiom : axioms) {
			for (OWLDataPropertyExpressionAdapter property : axiom.getProperties())
				generateOWLDataPropertyDeclarationAxiomIfNecessary(property);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLInverseObjectPropertiesAxioms()
	{
		Set<OWLInverseObjectPropertiesAxiomAdapter> axioms = getOWLOntology().getOWLInverseObjectPropertiesAxioms();
		for (OWLInverseObjectPropertiesAxiomAdapter axiom : axioms) {
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getFirstProperty());
			generateOWLObjectPropertyDeclarationAxiomIfNecessary(axiom.getSecondProperty());
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLDisjointObjectPropertiesAxioms()
	{
		Set<OWLDisjointObjectPropertiesAxiomAdapter> axioms = getOWLOntology().getOWLDisjointObjectPropertiesAxioms();
		for (OWLDisjointObjectPropertiesAxiomAdapter axiom : axioms) {
			for (OWLObjectPropertyExpressionAdapter property : axiom.getProperties())
				generateOWLObjectPropertyDeclarationAxiomIfNecessary(property);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void processOWLDisjointDataPropertiesAxioms()
	{
		Set<OWLDisjointDataPropertiesAxiomAdapter> axioms = getOWLOntology().getOWLDisjointDataPropertiesAxioms();
		for (OWLDisjointDataPropertiesAxiomAdapter axiom : axioms) {
			for (OWLDataPropertyExpressionAdapter property : axiom.getProperties())
				generateOWLDataPropertyDeclarationAxiomIfNecessary(property);
		}
		this.assertedOWLAxioms.addAll(axioms);
	}

	private void generateOWLClassDeclarationAxiom(OWLClassAdapter cls)
	{
		if (!this.owlClassDeclarationAxioms.containsKey(cls.getURI())) {
			OWLClassDeclarationAxiomAdapter axiom = getOWLDataFactory().getOWLClassDeclarationAxiom(cls);
			this.owlClassDeclarationAxioms.put(cls.getURI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			record(cls);
		}
	}

	private void generateOWLClassDeclarationAxiomIfNecessary(OWLClassExpressionAdapter classExpression)
	{
		if (classExpression instanceof OWLClassAdapter) {
			OWLClassAdapter cls = (OWLClassAdapter)classExpression;
			generateOWLClassDeclarationAxiom(cls);
		}
	}

	private void generateOWLIndividualDeclarationAxiomIfNecessary(OWLIndividualAdapter individual)
	{
		if (individual.isNamed()
				&& !this.owlIndividualDeclarationAxioms.containsKey(individual.asNamedIndividual().getURI())) {
			OWLIndividualDeclarationAxiomAdapter axiom = getOWLDataFactory().getOWLIndividualDeclarationAxiom(
					individual.asNamedIndividual());
			this.owlIndividualDeclarationAxioms.put(individual.asNamedIndividual().getURI(), axiom);
			this.assertedOWLAxioms.add(axiom);
			record(individual.asNamedIndividual());
		}
	}

	private void generateOWLObjectPropertyDeclarationAxiomIfNecessary(
			OWLObjectPropertyExpressionAdapter propertyExpression)
	{
		if (propertyExpression instanceof OWLObjectPropertyAdapter) {
			OWLObjectPropertyAdapter property = (OWLObjectPropertyAdapter)propertyExpression;
			if (!this.owlObjectPropertyDeclarationAxioms.containsKey(property.getURI())) {
				OWLObjectPropertyDeclarationAxiomAdapter axiom = getOWLDataFactory().getOWLObjectPropertyDeclarationAxiom(
						property);
				this.owlObjectPropertyDeclarationAxioms.put(property.getURI(), axiom);
				this.assertedOWLAxioms.add(axiom);
				record(property);
			}
		}
	}

	private void generateOWLDataPropertyDeclarationAxiomIfNecessary(OWLDataPropertyExpressionAdapter propertyExpression)
	{
		if (propertyExpression instanceof OWLDataPropertyAdapter) {
			OWLDataPropertyAdapter property = (OWLDataPropertyAdapter)propertyExpression;
			if (!this.owlDataPropertyDeclarationAxioms.containsKey(property.getURI())) {
				OWLDataPropertyDeclarationAxiomAdapter axiom = getOWLDataFactory().getOWLDataPropertyDeclarationAxiom(property);
				this.owlDataPropertyDeclarationAxioms.put(property.getURI(), axiom);
				this.assertedOWLAxioms.add(axiom);
				record(property);
			}
		}
	}

	private void record(OWLClassAdapter cls)
	{
		getOWLNamedObjectResolver().record(cls);
	}

	private void record(OWLNamedIndividualAdapter individual)
	{
		getOWLNamedObjectResolver().record(individual);
	}

	private void record(OWLObjectPropertyAdapter property)
	{
		getOWLNamedObjectResolver().record(property);
	}

	private void record(OWLDataPropertyAdapter property)
	{
		getOWLNamedObjectResolver().record(property);
	}

	private void record(OWLAnnotationPropertyAdapter property)
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