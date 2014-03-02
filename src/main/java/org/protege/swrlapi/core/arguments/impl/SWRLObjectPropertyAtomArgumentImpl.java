package org.protege.swrlapi.core.arguments.impl;

import org.protege.swrlapi.core.arguments.SWRLObjectPropertyAtomArgument;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectProperty;

class SWRLObjectPropertyAtomArgumentImpl extends SWRLPropertyAtomArgumentImpl implements SWRLObjectPropertyAtomArgument
{
	private static final long serialVersionUID = -2202377265745664328L;

	public SWRLObjectPropertyAtomArgumentImpl(IRI propertyIRI, String prefixedName)
	{
		super(propertyIRI, prefixedName);
	}

	public SWRLObjectPropertyAtomArgumentImpl(OWLObjectProperty property)
	{
		super(property);
	}
}