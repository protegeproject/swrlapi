package org.swrlapi.bridge.converters;

import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;

/**
 * @see org.semanticweb.owlapi.model.OWLDataRange
 */
public interface TargetRuleEngineOWLDataRangeConverter<TR> extends TargetRuleEngineConverter
{
	TR convert(OWLDatatype range);

	TR convert(OWLDataOneOf range);

	TR convert(OWLDataComplementOf range);

	TR convert(OWLDataIntersectionOf range);

	TR convert(OWLDataUnionOf range);

	TR convert(OWLDatatypeRestriction range);
}