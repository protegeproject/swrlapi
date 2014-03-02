package org.protege.swrlapi.ext;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Interface that provides a high level interface to an OWL ontology.
 */
public interface SWRLAPIOWLOntology extends OWLOntology
{
	void startBulkConversion(); // Can be used, for example, to switch off notification during bulk conversion.

	void completeBulkConversion();

	boolean hasOntologyChanged();

	void resetOntologyChanged();

	Set<SWRLAPIRule> getSWRLRules();

	SWRLAPIRule createSWRLRule(String ruleName, String ruleText);

	void putOWLAxiom(OWLAxiom axiom);

	/**
	 * Take a prefix and generate a unique IRI from it in the default namespace of the ontology, e.g., a local name prefix
	 * of "MM_CLASS_" supplied to this method where the default namespace is
	 * "http://bmir.stanford.edu/protege/ontologies/Map" will generate a IRI something like
	 * "http://bmir.stanford.edu/protege/ontologies/Map#MM_CLASS_34".
	 */
	IRI generateOWLEntityIRI(String localNamePrefix);

	String uri2PrefixedName(IRI uri);

	// TODO We really do not want all the following methods here. They are convenience methods only.
	boolean isOWLIndividualOfType(IRI individualIRI, IRI classIRI);

	Set<OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI);

	Set<OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI);

	boolean isOWLClass(IRI classIRI);

	boolean isOWLNamedIndividual(IRI individualIRI);

	boolean isOWLObjectProperty(IRI propertyIRI);

	boolean isOWLDataProperty(IRI propertyIRI);
}
