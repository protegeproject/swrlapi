package org.swrlapi.builtins.arguments;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.core.impl.DefaultSWRLAPIOWLOntology;

/**
 * Represents an OWL named entity argument to a built-in atom.
 * <p>
 * The OWLAPI follows the OWL Specification and does not explicitly allow named OWL entities as arguments to built-ins.
 * However, if OWLAPI parsers encounter OWL entities as parameters they appear to represent them as SWRL variables -
 * with the variable IRI set to the IRI of the entity ({@link OWLEntity} classes represent named OWL concepts so have an
 * IRI).b 8 For this reason we make a {@link SWRLNamedBuiltInArgument} extend a {@link SWRLVariable}.
 * <p>
 * A more principled approach should be identified (which would likely require modifying the OWLAPI).
 * 
 * @see DefaultSWRLAPIOWLOntology#convertSWRLVariable2SWRLBuiltInArgument
 */
public interface SWRLNamedBuiltInArgument extends SWRLBuiltInArgument, SWRLVariable
{
	@Override
	IRI getIRI();
}
