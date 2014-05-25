package org.swrlapi.core;

import java.net.URI;
import java.util.Comparator;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.builtins.arguments.impl.NaturalOrderComparator;
import org.swrlapi.xsd.XSDDate;
import org.swrlapi.xsd.XSDDateTime;
import org.swrlapi.xsd.XSDDuration;
import org.swrlapi.xsd.XSDTime;

public final class OWLLiteralComparator implements Comparator<OWLLiteral>
{
	private static Comparator<String> naturalOrderComparator = NaturalOrderComparator.NUMERICAL_ORDER;

	public static final Comparator<OWLLiteral> COMPARATOR = new OWLLiteralComparator();

	@Override
	public int compare(OWLLiteral l1, OWLLiteral l2)
	{
		int diff = l1.getDatatype().compareTo(l2.getDatatype());
		if (diff != 0)
			return diff;

		diff = l1.getLang().compareTo(l2.getLang());
		if (diff != 0)
			return diff;

		diff = compareOWLLiterals(l1, l2);

		return diff;
	}

	private int compareOWLLiterals(OWLLiteral l1, OWLLiteral l2)
	{
		if (!l1.getDatatype().equals(l2.getDatatype()))
			return -1;

		try {
			if (l1.getDatatype().isInteger() || l1.getDatatype().getIRI().equals(XSDVocabulary.INT.getIRI())) {
				Integer i1 = Integer.parseInt(l1.getLiteral());
				Integer i2 = Integer.parseInt(l2.getLiteral());
				return i1.compareTo(i2);
			} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.LONG.getIRI())) {
				Long long1 = Long.parseLong(l1.getLiteral());
				Long long2 = Long.parseLong(l2.getLiteral());
				return long1.compareTo(long2);
			} else if (l1.getDatatype().isFloat()) {
				Float f1 = Float.parseFloat(l1.getLiteral());
				Float f2 = Float.parseFloat(l2.getLiteral());
				return f1.compareTo(f2);
			} else if (l1.getDatatype().isDouble()) {
				Double d1 = Double.parseDouble(l1.getLiteral());
				Double d2 = Double.parseDouble(l2.getLiteral());
				return d1.compareTo(d2);
			} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.SHORT.getIRI())) {
				Short s1 = Short.parseShort(l1.getLiteral());
				Short s2 = Short.parseShort(l2.getLiteral());
				return s1.compareTo(s2);
			} else if (l1.getDatatype().isBoolean()) {
				Boolean b1 = Boolean.parseBoolean(l1.getLiteral());
				Boolean b2 = Boolean.parseBoolean(l2.getLiteral());
				return b1.compareTo(b2);
			} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.BYTE.getIRI())) {
				Byte b1 = Byte.parseByte(l1.getLiteral());
				Byte b2 = Byte.parseByte(l2.getLiteral());
				return b1.compareTo(b2);
			} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.ANY_URI.getIRI())) {
				URI u1 = URI.create(l1.getLiteral());
				URI u2 = URI.create(l2.getLiteral());
				return u1.compareTo(u2);
			} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.TIME.getIRI())) {
				XSDTime t1 = new XSDTime(l1.getLiteral());
				XSDTime t2 = new XSDTime(l2.getLiteral());
				return t1.compareTo(t2);
			} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.DATE.getIRI())) {
				XSDDate d1 = new XSDDate(l1.getLiteral());
				XSDDate d2 = new XSDDate(l2.getLiteral());
				return d1.compareTo(d2);
			} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.DATE_TIME.getIRI())) {
				XSDDateTime dt1 = new XSDDateTime(l1.getLiteral());
				XSDDateTime dt2 = new XSDDateTime(l2.getLiteral());
				return dt1.compareTo(dt2);
			} else if (l1.getDatatype().getIRI().equals(XSDVocabulary.DURATION.getIRI())) {
				XSDDuration d1 = new XSDDuration(l1.getLiteral());
				XSDDuration d2 = new XSDDuration(l2.getLiteral());
				return d1.compareTo(d2);
			} else
				// The OWLAPI seems to do a rather odd comparison so we use a natural order comparison
				return naturalOrderComparator.compare(l1.getLiteral(), l2.getLiteral());
		} catch (NumberFormatException e) {
			throw new RuntimeException("Literal " + l1.getLiteral() + " or " + l2.getLiteral() + " not valid "
					+ l1.getDatatype().getIRI());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Literal " + l1.getLiteral() + " or " + l2.getLiteral() + " not valid "
					+ l1.getDatatype().getIRI());
		}
	}
}
