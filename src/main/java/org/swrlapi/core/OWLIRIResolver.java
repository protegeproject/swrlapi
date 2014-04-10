package org.swrlapi.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * For simplicity, SWRL rule engine implementations will typically used the prefixed names of OWL named objects. A
 * {@link SWRLAPIOntologyProcessor} will record all the OWL named objects in an ontology together with their types. Rule
 * engines can then use this class to determine the type of OWL entities using their prefixed name and to map those
 * prefixed names to and from IRIs if necessary.
 * 
 * @see SWRLAPIOntologyProcessor
 */
public class OWLIRIResolver
{
	private final DefaultPrefixManager prefixManager;
	private final Map<String, IRI> shortName2IRI;
	private final Map<IRI, String> iri2ShortNameCache;

	private final Set<String> variableShortNames;
	private final Set<String> classShortNames;
	private final Set<String> namedIndividualShortNames;
	private final Set<String> objectPropertyShortNames;
	private final Set<String> dataPropertyShortNames;
	private final Set<String> annotationPropertyShortNames;
	private final Set<String> datatypeShortNames;

	public OWLIRIResolver(DefaultPrefixManager prefixManager)
	{
		this.prefixManager = prefixManager;

		this.shortName2IRI = new HashMap<String, IRI>();
		this.iri2ShortNameCache = new HashMap<IRI, String>();

		this.variableShortNames = new HashSet<String>();
		this.classShortNames = new HashSet<String>();
		this.namedIndividualShortNames = new HashSet<String>();
		this.objectPropertyShortNames = new HashSet<String>();
		this.dataPropertyShortNames = new HashSet<String>();
		this.annotationPropertyShortNames = new HashSet<String>();
		this.datatypeShortNames = new HashSet<String>();
	}

	public void reset()
	{
		this.shortName2IRI.clear();
		this.iri2ShortNameCache.clear();
		this.variableShortNames.clear();
		this.classShortNames.clear();
		this.namedIndividualShortNames.clear();
		this.objectPropertyShortNames.clear();
		this.dataPropertyShortNames.clear();
		this.annotationPropertyShortNames.clear();
		this.datatypeShortNames.clear();
	}

	public String iri2ShortName(IRI iri)
	{
		if (this.iri2ShortNameCache.containsKey(iri))
			return this.iri2ShortNameCache.get(iri);
		else {
			String shortName = prefixManager.getPrefixIRI(iri);
			if (shortName != null)
				return shortName;
			else
				throw new RuntimeException("could not find short name for IRI " + iri);
		}
	}

	public IRI shortName2IRI(String shortName) throws TargetRuleEngineException
	{
		if (this.shortName2IRI.containsKey(shortName))
			return this.shortName2IRI.get(shortName);
		else
			throw new RuntimeException("could not find IRI for prefixed name " + shortName);
	}

	public void recordSWRLVariable(SWRLVariable variable)
	{
		IRI iri = variable.getIRI();
		String variableShortName = iri2ShortName(iri);
		this.shortName2IRI.put(variableShortName, iri);
		this.variableShortNames.add(variableShortName);
	}

	public void recordOWLClass(OWLEntity cls)
	{
		IRI iri = cls.getIRI();
		String shortName = iri2ShortName(iri);

		recordPrefixedName2IRIMapping(shortName, iri);

		this.classShortNames.add(shortName);
	}

	public void recordOWLNamedIndividual(OWLEntity individual)
	{
		IRI iri = individual.getIRI();
		String shortName = iri2ShortName(iri);

		recordPrefixedName2IRIMapping(shortName, iri);

		this.namedIndividualShortNames.add(shortName);
	}

	public void recordOWLObjectProperty(OWLEntity property)
	{
		IRI iri = property.getIRI();
		String shortName = iri2ShortName(iri);

		recordPrefixedName2IRIMapping(shortName, iri);

		this.objectPropertyShortNames.add(shortName);
	}

	public void recordOWLDataProperty(OWLEntity property)
	{
		IRI iri = property.getIRI();
		String shortName = iri2ShortName(iri);

		recordPrefixedName2IRIMapping(shortName, iri);

		this.dataPropertyShortNames.add(shortName);
	}

	public void recordOWLAnnotationProperty(OWLEntity property)
	{
		IRI iri = property.getIRI();
		String shortName = iri2ShortName(iri);

		recordPrefixedName2IRIMapping(shortName, iri);

		this.annotationPropertyShortNames.add(shortName);
	}

	public void recordOWLDatatype(OWLEntity datatype)
	{
		IRI iri = datatype.getIRI();
		String shortName = iri2ShortName(iri);

		recordPrefixedName2IRIMapping(shortName, iri);

		this.datatypeShortNames.add(shortName);
	}

	public void record(SWRLClassBuiltInArgument classArgument)
	{
		IRI iri = classArgument.getIRI();
		String shortName = iri2ShortName(iri);

		recordPrefixedName2IRIMapping(shortName, iri);
		this.classShortNames.add(shortName);
	}

	public void record(SWRLNamedIndividualBuiltInArgument individualArgument)
	{
		IRI iri = individualArgument.getIRI();
		String shortName = iri2ShortName(iri);

		recordPrefixedName2IRIMapping(shortName, iri);
		this.namedIndividualShortNames.add(shortName);
	}

	public void record(SWRLObjectPropertyBuiltInArgument propertyArgument)
	{
		IRI iri = propertyArgument.getIRI();
		String shortName = iri2ShortName(iri);

		recordPrefixedName2IRIMapping(shortName, iri);
		this.objectPropertyShortNames.add(shortName);
	}

	public void record(SWRLDataPropertyBuiltInArgument propertyArgument)
	{
		IRI iri = propertyArgument.getIRI();
		String shortName = iri2ShortName(iri);

		recordPrefixedName2IRIMapping(shortName, iri);
		this.dataPropertyShortNames.add(shortName);
	}

	public void recordPrefixedName2IRIMapping(String shortName, IRI iri)
	{
		if (!this.shortName2IRI.containsKey(shortName)) {
			this.shortName2IRI.put(shortName, iri);
			this.iri2ShortNameCache.put(iri, shortName);
		}
	}

	public boolean isOWLClass(String shortName)
	{
		return this.classShortNames.contains(shortName);
	}

	public boolean isOWLNamedIndividual(String shortName)
	{
		return this.namedIndividualShortNames.contains(shortName);
	}

	public boolean isOWLObjectProperty(String shortName)
	{
		return this.objectPropertyShortNames.contains(shortName);
	}

	public boolean isOWLDataProperty(String shortName)
	{
		return this.dataPropertyShortNames.contains(shortName);
	}

	public boolean isOWLAnnotationProperty(String shortName)
	{
		return this.annotationPropertyShortNames.contains(shortName);
	}

	public boolean isOWLDatatype(String shortName)
	{
		return this.datatypeShortNames.contains(shortName);
	}
}
