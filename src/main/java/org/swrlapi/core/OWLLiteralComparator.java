package org.swrlapi.core;

import java.util.Comparator;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.core.arguments.impl.NaturalOrderComparator;

public final class OWLLiteralComparator implements Comparator<OWLLiteral>
{
	private static Comparator<String> naturalOrderComparator = NaturalOrderComparator.NUMERICAL_ORDER;

	public static final Comparator<OWLLiteral> COMPARATOR = new OWLLiteralComparator();

	// TODO This is not correct. Need to do proper comparison considering underlying datatype.
	@Override
	public int compare(OWLLiteral l1, OWLLiteral l2)
	{
		int diff = l1.getDatatype().compareTo(l2.getDatatype());
		if (diff != 0)
			return diff;

		diff = l1.getLang().compareTo(l2.getLang());
		if (diff != 0)
			return diff;

		diff = naturalOrderComparator.compare(l1.getLiteral(), l2.getLiteral());

		return diff;
	}

	private int fred(OWLLiteral l1, OWLLiteral l2)
	{
		if (l1.getDatatype().isInteger()) {

		} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.INT.getIRI())) {
		} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.LONG.getIRI())) {

		} else if (l1.getDatatype().isFloat()) {

		} else if (l1.getDatatype().isDouble()) {

		} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.SHORT.getIRI())) {

		} else if (l1.getDatatype().isBoolean()) {

		} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.BYTE.getIRI())) {
		} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.ANY_URI.getIRI())) {

		} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.TIME.getIRI())) {
		} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.DATE.getIRI())) {
		} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.DATE_TIME.getIRI())) {
		} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.DURATION.getIRI())) {
		} else
			return 0;
		return 0;
	}
	// Boolean Short Integer Int Long Float Double String Byte AnyURI Time Date DateTime Duration
}
