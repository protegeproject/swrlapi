package org.swrlapi.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * For simplicity, SWRL rule engine implementations will typically use the prefixed names of OWL named objects to name
 * their representation of those objects. A {@link org.swrlapi.core.SWRLAPIOntologyProcessor} will record all the OWL
 * named objects in an ontology together with their types. Rule engines can then use this class to determine the type of
 * OWL properties using their prefixed name and to map those prefixed names to and from IRIs if necessary.
 *
 * @see org.swrlapi.core.SWRLAPIOntologyProcessor
 */
public class IRIResolver
{
  @NonNull private final DefaultPrefixManager prefixManager;

  @NonNull private final Map<String, IRI> prefixedName2IRI = new HashMap<>();
  @NonNull private final Map<IRI, String> iri2PrefixedNameCache = new HashMap<>();
  @NonNull private final Set<String> variablePrefixedNames = new HashSet<>();
  @NonNull private final Set<String> classPrefixedNames = new HashSet<>();
  @NonNull private final Set<String> namedIndividualPrefixedNames = new HashSet<>();
  @NonNull private final Set<String> objectPropertyPrefixedNames = new HashSet<>();
  @NonNull private final Set<String> dataPropertyPrefixedNames = new HashSet<>();
  @NonNull private final Set<String> annotationPropertyPrefixedNames = new HashSet<>();
  @NonNull private final Set<String> datatypePrefixedNames = new HashSet<>();

  public IRIResolver()
  {
    this.prefixManager = new DefaultPrefixManager();
  }

  public IRIResolver(@NonNull DefaultPrefixManager prefixManager)
  {
    this.prefixManager = prefixManager;
  }

  public void reset()
  {
    this.prefixedName2IRI.clear();
    this.iri2PrefixedNameCache.clear();
    this.variablePrefixedNames.clear();
    this.classPrefixedNames.clear();
    this.namedIndividualPrefixedNames.clear();
    this.objectPropertyPrefixedNames.clear();
    this.dataPropertyPrefixedNames.clear();
    this.annotationPropertyPrefixedNames.clear();
    this.datatypePrefixedNames.clear();
  }

  /**
   * @param iri An IRI
   * @return The prefixed form of the IRI
   * @throws IllegalArgumentException If the IRI cannot be resolved to a prefixed name
   */
  @NonNull public String iri2PrefixedName(@NonNull IRI iri)
  {
    if (this.iri2PrefixedNameCache.containsKey(iri))
      return this.iri2PrefixedNameCache.get(iri);
    else {
      String prefixedName = this.prefixManager.getPrefixIRI(iri);
      if (prefixedName != null)
        return prefixedName;
      else
        throw new IllegalArgumentException("could not find prefixed name for IRI " + iri);
    }
  }

  /**
   * @param prefixedName A prefixed name
   * @return The IRI resolved from the prefixed name
   * @throws IllegalArgumentException If the prefixed name cannot be resolved
   */
  @NonNull public IRI prefixedName2IRI(@NonNull String prefixedName)
  {
    if (this.prefixedName2IRI.containsKey(prefixedName))
      return this.prefixedName2IRI.get(prefixedName);
    else {
      try {
        return this.prefixManager.getIRI(prefixedName);
      } catch (RuntimeException e) {
        throw new IllegalArgumentException("could not find IRI for prefixed name " + prefixedName);
      }
    }
  }

  public void recordSWRLVariable(@NonNull SWRLVariable variable)
  {
    IRI iri = variable.getIRI();
    String variablePrefixedName = iri2PrefixedName(iri);
    this.prefixedName2IRI.put(variablePrefixedName, iri);
    this.variablePrefixedNames.add(variablePrefixedName);
  }

  public void recordOWLClass(@NonNull OWLEntity cls)
  {
    IRI iri = cls.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);

    this.classPrefixedNames.add(prefixedName);
  }

  public void recordOWLNamedIndividual(@NonNull OWLEntity individual)
  {
    IRI iri = individual.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);

    this.namedIndividualPrefixedNames.add(prefixedName);
  }

  public void recordOWLObjectProperty(@NonNull OWLEntity property)
  {
    IRI iri = property.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);

    this.objectPropertyPrefixedNames.add(prefixedName);
  }

  public void recordOWLDataProperty(@NonNull OWLEntity property)
  {
    IRI iri = property.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);

    this.dataPropertyPrefixedNames.add(prefixedName);
  }

  public void recordOWLAnnotationProperty(@NonNull OWLEntity property)
  {
    IRI iri = property.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);

    this.annotationPropertyPrefixedNames.add(prefixedName);
  }

  public void recordOWLDatatype(@NonNull OWLEntity datatype)
  {
    IRI iri = datatype.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);

    this.datatypePrefixedNames.add(prefixedName);
  }

  public void record(@NonNull SWRLClassBuiltInArgument classArgument)
  {
    IRI iri = classArgument.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);
    this.classPrefixedNames.add(prefixedName);
  }

  public void record(@NonNull SWRLNamedIndividualBuiltInArgument individualArgument)
  {
    IRI iri = individualArgument.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);
    this.namedIndividualPrefixedNames.add(prefixedName);
  }

  public void record(@NonNull SWRLObjectPropertyBuiltInArgument propertyArgument)
  {
    IRI iri = propertyArgument.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);
    this.objectPropertyPrefixedNames.add(prefixedName);
  }

  public void record(@NonNull SWRLDataPropertyBuiltInArgument propertyArgument)
  {
    IRI iri = propertyArgument.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);
    this.dataPropertyPrefixedNames.add(prefixedName);
  }

  public boolean isOWLClass(@NonNull String prefixedName)
  {
    return this.classPrefixedNames.contains(prefixedName);
  }

  public boolean isOWLNamedIndividual(@NonNull String prefixedName)
  {
    return this.namedIndividualPrefixedNames.contains(prefixedName);
  }

  public boolean isOWLObjectProperty(@NonNull String prefixedName)
  {
    return this.objectPropertyPrefixedNames.contains(prefixedName);
  }

  public boolean isOWLDataProperty(@NonNull String prefixedName)
  {
    return this.dataPropertyPrefixedNames.contains(prefixedName);
  }

  public boolean isOWLAnnotationProperty(@NonNull String prefixedName)
  {
    return this.annotationPropertyPrefixedNames.contains(prefixedName);
  }

  public boolean isOWLDatatype(@NonNull String prefixedName)
  {
    return this.datatypePrefixedNames.contains(prefixedName);
  }

  private void recordPrefixedName2IRIMapping(@NonNull String prefixedName, @NonNull IRI iri)
  {
    if (!this.prefixedName2IRI.containsKey(prefixedName)) {
      this.prefixedName2IRI.put(prefixedName, iri);
      this.iri2PrefixedNameCache.put(iri, prefixedName);
    }
  }
}
