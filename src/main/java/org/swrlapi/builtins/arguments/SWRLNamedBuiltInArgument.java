package org.swrlapi.builtins.arguments;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * Represents an OWL named entity argument to a built-in atom.
 * <p>
 * The OWLAPI follows the OWL Specification and does not explicitly allow named OWL properties as arguments to
 * built-ins. However, if OWLAPI parsers encounter OWL properties as parameters they appear to represent them as SWRL
 * variables - with the variable IRI set to the IRI of the entity ({@link OWLEntity} classes represent named OWL
 * concepts so have an IRI).b 8 For this reason we make a {@link SWRLNamedBuiltInArgument} extend a {@link SWRLVariable}.
 * <p>
 * A more principled approach should be identified (which would likely require modifying the OWLAPI).
 * 
 */
public interface SWRLNamedBuiltInArgument extends SWRLBuiltInArgument, SWRLVariable
{
  @NonNull @Override
  IRI getIRI();
}
