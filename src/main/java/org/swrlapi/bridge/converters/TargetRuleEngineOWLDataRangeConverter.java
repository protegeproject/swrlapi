package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * @see org.semanticweb.owlapi.model.OWLDataRange
 */
public interface TargetRuleEngineOWLDataRangeConverter<TR> extends TargetRuleEngineConverter
{
	TR convert(OWLDatatype range) throws TargetRuleEngineException;

	TR convert(OWLDataOneOf range) throws TargetRuleEngineException;

	TR convert(OWLDataComplementOf range) throws TargetRuleEngineException;

	TR convert(OWLDataIntersectionOf range) throws TargetRuleEngineException;

	TR convert(OWLDataUnionOf range) throws TargetRuleEngineException;

	TR convert(OWLDatatypeRestriction range) throws TargetRuleEngineException;
}