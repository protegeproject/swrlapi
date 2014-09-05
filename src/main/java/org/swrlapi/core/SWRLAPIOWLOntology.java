package org.swrlapi.core;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.resolvers.IRIResolver;

/**
 * Wraps the OWLAPI's {@link org.semanticweb.owlapi.model.OWLOntology} class with additional functionality used by
 * the SWRLAPI. Primarily the {@link #getSWRLAPIRules()} method extracts {@link org.swrlapi.core.SWRLAPIRule} objects
 * from an OWL ontology. This class, which extends the standard OWLAPI {@link org.semanticweb.owlapi.model.SWRLRule}
 * class, provide the richer representation of a SWRL rule required by the SWRLAPI. In particular, the SWRLAPI has a
 * range of types extending the OWLAPI's {@link org.semanticweb.owlapi.model.SWRLDArgument} interface to define
 * arguments to built-in atoms.
 * <p>
 * This extension point is defined by the {@link org.swrlapi.builtins.arguments.SWRLBuiltInArgument} interface,
 * which extends the OWLAPI's {@link org.semanticweb.owlapi.model.SWRLDArgument}. A
 * {@link org.swrlapi.core.SWRLAPIOWLOntology} will construct SWRLAPI rules from the SWRL rules in an
 * OWLAPI-based ontology to contain these additional built-in argument types.
 * <p>
 * The {@link #startBulkConversion()}, {@link #completeBulkConversion()}, {@link #hasOntologyChanged()}, and
 * {@link #resetOntologyChanged()} methods can be used for optimization purposes. For example, in the Protege-OWL API the
 * {@link #startBulkConversion()} method turns off listener notification so that bulk transfer of OWL axioms can be
 * performed more efficiently. The {@link #hasOntologyChanged()} method can be used by rule engines to avoid unnecessary
 * regeneration of knowledge.
 * <p>
 * A SWRLAPI ontology does not directly deal with SQWRL queries. Instead, a
 * {@link org.swrlapi.core.SWRLAPIOntologyProcessor} is used to extract SQWRL queries - which are stored as SWRL
 * rules - from a {@link org.swrlapi.core.SWRLAPIOWLOntology}.
 * 
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 * @see org.swrlapi.core.SWRLAPIOntologyProcessor
 * @see org.swrlapi.core.SWRLAPIOWLDataFactory
 */
public interface SWRLAPIOWLOntology
{
	Set<SWRLAPIRule> getSWRLAPIRules();

	SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory();

	SWRLAPIOntologyProcessor getSWRLAPIOntologyProcessor();

	IRIResolver getIRIResolver();

	void startBulkConversion(); // Can be used, for example, to switch off notification during bulk conversion.

	void completeBulkConversion();

	boolean hasOntologyChanged();

	void resetOntologyChanged();

	boolean isSWRLBuiltIn(IRI iri); // The SWRLAPI provides built-ins beyond the core set defined in the SWRL submission.

	void addSWRLBuiltIn(IRI iri);

	Set<IRI> getSWRLBuiltInIRIs();

	OWLOntologyManager getOWLOntologyManager();

	DefaultPrefixManager getPrefixManager();

	OWLOntology getOWLOntology();

	OWLDataFactory getOWLDataFactory();

	// TODO We don't want this method here. It is a convenience method and used only by the temporal built-in library.
	boolean isOWLIndividualOfType(IRI individualIRI, IRI classIRI);

	// TODO We don't want this method here. It is a convenience method and used only by the temporal built-in library.
	Set<OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI);

	// TODO We don't want this method here. It is a convenience method and used only by the temporal built-in library.
	Set<OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI);
}
