package org.protege.swrlapi.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * This class is used by rule engine implementations to determine the type of OWL entities using their prefixed name. It
 * also maps from prefixed names to IRIs for those entities.
 */
public class OWLNamedObjectResolver
{
	private final Map<String, IRI> prefixedName2IRIMap;
	private final Set<String> classPrefixedNames;
	private final Set<String> individualPrefixedNames;
	private final Set<String> objectPropertyPrefixedNames;
	private final Set<String> dataPropertyPrefixedNames;
	private final Set<String> annotationPropertyPrefixedNames;
	private final Set<String> datatypePrefixedNames;

	public OWLNamedObjectResolver()
	{
		this.prefixedName2IRIMap = new HashMap<String, IRI>();
		this.classPrefixedNames = new HashSet<String>();
		this.individualPrefixedNames = new HashSet<String>();
		this.objectPropertyPrefixedNames = new HashSet<String>();
		this.dataPropertyPrefixedNames = new HashSet<String>();
		this.annotationPropertyPrefixedNames = new HashSet<String>();
		this.datatypePrefixedNames = new HashSet<String>();
	}

	public void reset()
	{
		this.prefixedName2IRIMap.clear();
		this.classPrefixedNames.clear();
		this.individualPrefixedNames.clear();
		this.objectPropertyPrefixedNames.clear();
		this.dataPropertyPrefixedNames.clear();
		this.annotationPropertyPrefixedNames.clear();
		this.datatypePrefixedNames.clear();
	}

	public boolean isOWLClass(String prefixedName)
	{
		return this.classPrefixedNames.contains(prefixedName);
	}

	public boolean isOWLNamedIndividual(String prefixedName)
	{
		return this.individualPrefixedNames.contains(prefixedName);
	}

	public boolean isOWLObjectProperty(String prefixedName)
	{
		return this.objectPropertyPrefixedNames.contains(prefixedName);
	}

	public boolean isOWLDataProperty(String prefixedName)
	{
		return this.dataPropertyPrefixedNames.contains(prefixedName);
	}

	public boolean isOWLAnnotationProperty(String prefixedName)
	{
		return this.annotationPropertyPrefixedNames.contains(prefixedName);
	}

	public boolean isOWLDatatype(String prefixedName)
	{
		return this.datatypePrefixedNames.contains(prefixedName);
	}

	public void record(OWLClass cls)
	{
		recordPrefixedName2IRIMapping(cls.getPrefixedName(), cls.getIRI());

		this.classPrefixedNames.add(cls.getPrefixedName());
	}

	public void record(OWLNamedIndividual individual)
	{
		recordPrefixedName2IRIMapping(individual.getPrefixedName(), individual.getIRI());

		this.individualPrefixedNames.add(individual.getPrefixedName());
	}

	public void record(OWLObjectProperty property)
	{
		recordPrefixedName2IRIMapping(property.getPrefixedName(), property.getIRI());

		this.objectPropertyPrefixedNames.add(property.getPrefixedName());
	}

	public void record(OWLDataProperty property)
	{
		recordPrefixedName2IRIMapping(property.getPrefixedName(), property.getIRI());

		this.dataPropertyPrefixedNames.add(property.getPrefixedName());
	}

	public void record(OWLAnnotationProperty property)
	{
		recordPrefixedName2IRIMapping(property.getPrefixedName(), property.getIRI());

		this.annotationPropertyPrefixedNames.add(property.getPrefixedName());
	}

	public void record(OWLDatatype datatype)
	{
		recordPrefixedName2IRIMapping(datatype.getPrefixedName(), datatype.getIRI());

		this.datatypePrefixedNames.add(datatype.getPrefixedName());
	}

	public void record(SWRLClassBuiltInArgument classArgument)
	{
		recordPrefixedName2IRIMapping(classArgument.getPrefixedName(), classArgument.getIRI());
		this.classPrefixedNames.add(classArgument.getPrefixedName());
	}

	public void record(SWRLIndividualBuiltInArgument individualArgument)
	{
		recordPrefixedName2IRIMapping(individualArgument.getPrefixedName(), individualArgument.getIRI());
		this.individualPrefixedNames.add(individualArgument.getPrefixedName());
	}

	public void record(SWRLObjectPropertyBuiltInArgument propertyArgument)
	{
		recordPrefixedName2IRIMapping(propertyArgument.getPrefixedName(), propertyArgument.getIRI());
		this.objectPropertyPrefixedNames.add(propertyArgument.getPrefixedName());
	}

	public void record(SWRLDataPropertyBuiltInArgument propertyArgument)
	{
		recordPrefixedName2IRIMapping(propertyArgument.getPrefixedName(), propertyArgument.getIRI());
		this.dataPropertyPrefixedNames.add(propertyArgument.getPrefixedName());
	}

	public void recordPrefixedName2IRIMapping(String prefixedName, IRI uri)
	{
		if (!this.prefixedName2IRIMap.containsKey(prefixedName))
			this.prefixedName2IRIMap.put(prefixedName, uri);
	}

	public IRI prefixedName2IRI(String prefixedName) throws TargetRuleEngineException
	{
		if (this.prefixedName2IRIMap.containsKey(prefixedName))
			return this.prefixedName2IRIMap.get(prefixedName);
		else
			throw new TargetRuleEngineException("internal error: could not find IRI for prefixed name " + prefixedName);
	}
}
