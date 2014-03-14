package org.protege.swrlapi.ext;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * Extends the OWLAPI's OWLOntology class with additional methods used by the SWRLAPI. Primarily the
 * {@link #getSWRLRules()} method extracts {@link SWRLAPIRule} objects from an OWL ontology. This class, which extends
 * the standard OWLAPI {@link SWRLRule} class, provide the richer representation of a SWRL rule required by the SWRLAPI.
 * In particular, the SWRLAPI has a range of types extending the OWLAPI's @{link SWRLArgument} interface. A
 * {@link SWRLAPIOWLOntology} will construct SWRLAPI rules from the SWRL rules in an OWLAPI-based ontology to contain
 * these additional types. A {@link SWRLAPIOWLDataFactory} can be used to create {@link SWRLAPIRule} objects from text.
 * <p>
 * The {@link startBulkConversion}, {@link completeBulkConversion}, {@link hasOntologyChanged}, and
 * {@link resetOntologyChanged} methods can be used for optimization purposed. For example, in the Protege-OWL API the
 * {@link startBulkConversion} method turns off listener notification so that bulk transfer of OWL axioms can be
 * performed more efficiently. The {@link hasOntologyChanged} method can be used by rule engines to avoid unnecessary
 * regeneration of knowledge.
 */
public interface SWRLAPIOWLOntology extends OWLOntology
{
	Set<SWRLAPIRule> getSWRLRules();

	void startBulkConversion(); // Can be used, for example, to switch off notification during bulk conversion.

	void completeBulkConversion();

	boolean hasOntologyChanged();

	void resetOntologyChanged();

	// TODO We don't want this method here. It is a convenience method only.
	boolean isOWLIndividualOfType(IRI individualIRI, IRI classIRI);

	// TODO We don't want this method here. It is a convenience method only.
	Set<OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI);

	// TODO We don't want this method here. It is a convenience method only.
	Set<OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI);
}
