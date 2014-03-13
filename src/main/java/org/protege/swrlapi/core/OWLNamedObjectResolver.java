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
import org.semanticweb.owlapi.model.OWLEntity;

/**
 * This class is used by rule engine implementations to determine the type of OWL entities using their prefixed name. It
 * also maps from prefixed names to IRIs for those entities.
 * <p>
 * A {@link SWRLOntologyProcessor} implementation will typically create this class.
 */
public class OWLNamedObjectResolver
{
	private final Map<String, IRI> prefixedName2IRI;
	private final Map<IRI, String> iri2PrefixedName;
	private final Set<String> classPrefixedNames;
	private final Set<String> individualPrefixedNames;
	private final Set<String> objectPropertyPrefixedNames;
	private final Set<String> dataPropertyPrefixedNames;
	private final Set<String> annotationPropertyPrefixedNames;
	private final Set<String> datatypePrefixedNames;

	public OWLNamedObjectResolver()
	{
		this.prefixedName2IRI = new HashMap<String, IRI>();
		this.iri2PrefixedName = new HashMap<IRI, String>();

		this.classPrefixedNames = new HashSet<String>();
		this.individualPrefixedNames = new HashSet<String>();
		this.objectPropertyPrefixedNames = new HashSet<String>();
		this.dataPropertyPrefixedNames = new HashSet<String>();
		this.annotationPropertyPrefixedNames = new HashSet<String>();
		this.datatypePrefixedNames = new HashSet<String>();
	}

	public void reset()
	{
		this.prefixedName2IRI.clear();
		this.iri2PrefixedName.clear();
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

	public void recordOWLClass(OWLEntity cls)
	{
		recordPrefixedName2IRIMapping(getPrefixedName(cls), cls.getIRI());

		this.classPrefixedNames.add(getPrefixedName(cls));
	}

	public void recordOWLNamedIndividual(OWLEntity individual)
	{
		recordPrefixedName2IRIMapping(getPrefixedName(individual), individual.getIRI());

		this.individualPrefixedNames.add(getPrefixedName(individual));
	}

	public void recordOWLObjectProperty(OWLEntity property)
	{
		recordPrefixedName2IRIMapping(getPrefixedName(property), property.getIRI());

		this.objectPropertyPrefixedNames.add(getPrefixedName(property));
	}

	public void recordOWLDataProperty(OWLEntity property)
	{
		recordPrefixedName2IRIMapping(getPrefixedName(property), property.getIRI());

		this.dataPropertyPrefixedNames.add(getPrefixedName(property));
	}

	public void recordOWLAnnotationProperty(OWLEntity property)
	{
		recordPrefixedName2IRIMapping(getPrefixedName(property), property.getIRI());

		this.annotationPropertyPrefixedNames.add(getPrefixedName(property));
	}

	public void recordOWLDatatype(OWLEntity datatype)
	{
		recordPrefixedName2IRIMapping(getPrefixedName(datatype), datatype.getIRI());

		this.datatypePrefixedNames.add(getPrefixedName(datatype));
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

	public void recordPrefixedName2IRIMapping(String prefixedName, IRI iri)
	{
		if (!this.prefixedName2IRI.containsKey(prefixedName)) {
			this.prefixedName2IRI.put(prefixedName, iri);
			this.iri2PrefixedName.put(iri, prefixedName);
		}
	}

	public String iri2PrefixedName(IRI iri) throws TargetRuleEngineException
	{
		if (this.iri2PrefixedName.containsKey(iri))
			return this.iri2PrefixedName(iri);
		else
			throw new TargetRuleEngineException("internal error: could not find prefixed name for IRI " + iri);
	}

	public IRI prefixedName2IRI(String prefixedName) throws TargetRuleEngineException
	{
		if (this.prefixedName2IRI.containsKey(prefixedName))
			return this.prefixedName2IRI.get(prefixedName);
		else
			throw new TargetRuleEngineException("internal error: could not find IRI for prefixed name " + prefixedName);
	}

	private String getPrefixedName(OWLEntity owlEntity)
	{
		throw new RuntimeException("Not implemented");
	}
}
