package org.swrlapi.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
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

	public String iri2PrefixedName(IRI iri)
	{
		if (this.iri2PrefixedName.containsKey(iri))
			return this.iri2PrefixedName(iri);
		else
			throw new RuntimeException("could not find prefixed name for IRI " + iri);
	}

	public IRI prefixedName2IRI(String prefixedName) throws TargetRuleEngineException
	{
		if (this.prefixedName2IRI.containsKey(prefixedName))
			return this.prefixedName2IRI.get(prefixedName);
		else
			throw new RuntimeException("could not find IRI for prefixed name " + prefixedName);
	}

	public void recordOWLClass(OWLEntity cls)
	{
		IRI iri = cls.getIRI();
		String prefixedName = getPrefixedName(iri);

		recordPrefixedName2IRIMapping(prefixedName, iri);

		this.classPrefixedNames.add(prefixedName);
	}

	public void recordOWLNamedIndividual(OWLEntity individual)
	{
		IRI iri = individual.getIRI();
		String prefixedName = getPrefixedName(iri);

		recordPrefixedName2IRIMapping(prefixedName, iri);

		this.individualPrefixedNames.add(prefixedName);
	}

	public void recordOWLObjectProperty(OWLEntity property)
	{
		IRI iri = property.getIRI();
		String prefixedName = getPrefixedName(iri);

		recordPrefixedName2IRIMapping(prefixedName, iri);

		this.objectPropertyPrefixedNames.add(prefixedName);
	}

	public void recordOWLDataProperty(OWLEntity property)
	{
		IRI iri = property.getIRI();
		String prefixedName = getPrefixedName(iri);

		recordPrefixedName2IRIMapping(prefixedName, iri);

		this.dataPropertyPrefixedNames.add(prefixedName);
	}

	public void recordOWLAnnotationProperty(OWLEntity property)
	{
		IRI iri = property.getIRI();
		String prefixedName = getPrefixedName(iri);

		recordPrefixedName2IRIMapping(prefixedName, iri);

		this.annotationPropertyPrefixedNames.add(prefixedName);
	}

	public void recordOWLDatatype(OWLEntity datatype)
	{
		IRI iri = datatype.getIRI();
		String prefixedName = getPrefixedName(iri);

		recordPrefixedName2IRIMapping(prefixedName, iri);

		this.datatypePrefixedNames.add(prefixedName);
	}

	public void record(SWRLClassBuiltInArgument classArgument)
	{
		IRI iri = classArgument.getIRI();
		String prefixedName = getPrefixedName(iri);

		recordPrefixedName2IRIMapping(prefixedName, iri);
		this.classPrefixedNames.add(prefixedName);
	}

	public void record(SWRLIndividualBuiltInArgument individualArgument)
	{
		IRI iri = individualArgument.getIRI();
		String prefixedName = getPrefixedName(iri);

		recordPrefixedName2IRIMapping(prefixedName, iri);
		this.individualPrefixedNames.add(prefixedName);
	}

	public void record(SWRLObjectPropertyBuiltInArgument propertyArgument)
	{
		IRI iri = propertyArgument.getIRI();
		String prefixedName = getPrefixedName(iri);

		recordPrefixedName2IRIMapping(prefixedName, iri);
		this.objectPropertyPrefixedNames.add(prefixedName);
	}

	public void record(SWRLDataPropertyBuiltInArgument propertyArgument)
	{
		IRI iri = propertyArgument.getIRI();
		String prefixedName = getPrefixedName(iri);

		recordPrefixedName2IRIMapping(prefixedName, iri);
		this.dataPropertyPrefixedNames.add(prefixedName);
	}

	public void recordPrefixedName2IRIMapping(String prefixedName, IRI iri)
	{
		if (!this.prefixedName2IRI.containsKey(prefixedName)) {
			this.prefixedName2IRI.put(prefixedName, iri);
			this.iri2PrefixedName.put(iri, prefixedName);
		}
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

	private String getPrefixedName(IRI iri)
	{
		throw new RuntimeException("Not implemented");
	}
}
