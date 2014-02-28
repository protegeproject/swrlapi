package org.protege.swrlapi.ext;

import java.net.URI;
import java.util.Set;

import org.protege.owl.portability.axioms.OWLAxiomAdapter;
import org.protege.owl.portability.axioms.OWLDataPropertyAssertionAxiomAdapter;
import org.protege.owl.portability.axioms.OWLObjectPropertyAssertionAxiomAdapter;
import org.protege.owl.portability.model.OWLOntologyAdapter;

/**
 * Interface that provides a high level interface to an OWL ontology.
 */
public interface SWRLAPIOWLOntology extends OWLOntologyAdapter
{
	void startBulkConversion(); // Can be used, for example, to switch off notification during bulk conversion.

	void completeBulkConversion();

	boolean hasOntologyChanged();

	void resetOntologyChanged();

	Set<SWRLAPIRule> getSWRLRules();

	SWRLAPIRule createSWRLRule(String ruleName, String ruleText);

	void putOWLAxiom(OWLAxiomAdapter axiom);

	/**
	 * Take a prefix and generate a unique URI from it in the default namespace of the ontology, e.g., a local name prefix
	 * of "MM_CLASS_" supplied to this method where the default namespace is
	 * "http://bmir.stanford.edu/protege/ontologies/Map" will generate a URI something like
	 * "http://bmir.stanford.edu/protege/ontologies/Map#MM_CLASS_34".
	 */
	URI generateOWLEntityURI(String localNamePrefix);

	String uri2PrefixedName(URI uri);

	// TODO We really do not want all the following methods here. They are convenience methods only.
	boolean isOWLIndividualOfType(URI individualURI, URI classURI);

	Set<OWLObjectPropertyAssertionAxiomAdapter> getOWLObjectPropertyAssertionAxioms(URI individualURI, URI propertyURI);

	Set<OWLDataPropertyAssertionAxiomAdapter> getOWLDataPropertyAssertionAxioms(URI individualURI, URI propertyURI);

	boolean isOWLClass(URI classURI);

	boolean isOWLNamedIndividual(URI individualURI);

	boolean isOWLObjectProperty(URI propertyURI);

	boolean isOWLDataProperty(URI propertyURI);
}
