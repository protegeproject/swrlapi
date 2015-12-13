package org.swrlapi.factory.resolvers;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.IRIResolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DefaultIRIResolver implements IRIResolver
{
  @NonNull private final DefaultPrefixManager prefixManager;

  @NonNull private final Map<@NonNull String, @NonNull IRI> prefixedName2IRI = new HashMap<>();
  @NonNull private final Map<@NonNull IRI, @NonNull String> iri2PrefixedNameCache = new HashMap<>();
  @NonNull private final Set<@NonNull String> variablePrefixedNames = new HashSet<>();
  @NonNull private final Set<@NonNull String> classPrefixedNames = new HashSet<>();
  @NonNull private final Set<@NonNull String> namedIndividualPrefixedNames = new HashSet<>();
  @NonNull private final Set<@NonNull String> objectPropertyPrefixedNames = new HashSet<>();
  @NonNull private final Set<@NonNull String> dataPropertyPrefixedNames = new HashSet<>();
  @NonNull private final Set<@NonNull String> annotationPropertyPrefixedNames = new HashSet<>();
  @NonNull private final Set<@NonNull String> datatypePrefixedNames = new HashSet<>();

  @NonNull private final Map<@NonNull String, @NonNull String> autogenNamespace2Prefix = new HashMap<>();

  private int autogenPrefixNumber = 0;

  public DefaultIRIResolver(@NonNull DefaultPrefixManager prefixManager)
  {
    this.prefixManager = prefixManager;
  }

  @Override public void reset()
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

    this.autogenNamespace2Prefix.clear();
    this.autogenPrefixNumber = 0;
  }

  @Override @NonNull public String iri2PrefixedName(@NonNull IRI iri)
  { // TODO Need to do something with IRIs where namespace is ontology IRI
    if (this.iri2PrefixedNameCache.containsKey(iri))
      return this.iri2PrefixedNameCache.get(iri);
    else {
      String existingPrefixedName = this.prefixManager.getPrefixIRI(iri);
      if (existingPrefixedName != null)
        return existingPrefixedName;
      else {
        String namespace = iri.getNamespace();
        com.google.common.base.Optional<@NonNull String> remainder = iri.getRemainder();
        if (remainder.isPresent()) {
          if (this.autogenNamespace2Prefix.containsKey(namespace)) {
            String prefixedName = this.autogenNamespace2Prefix.get(namespace) + remainder.get();
            this.iri2PrefixedNameCache.put(iri, prefixedName);
            return prefixedName;
          } else {
            String autogenPrefix = "autogen" + this.autogenPrefixNumber++ + ":";
            String prefixedName = autogenPrefix + remainder.get();
            this.autogenNamespace2Prefix.put(namespace, autogenPrefix);
            this.iri2PrefixedNameCache.put(iri, prefixedName);
            return prefixedName;
          }
        } else
          throw new IllegalArgumentException("could not create prefixed name for IRI " + iri);
      }
    }
  }

  @Override @NonNull public IRI prefixedName2IRI(@NonNull String prefixedName)
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

  @Override public void recordSWRLVariable(@NonNull SWRLVariable variable)
  {
    IRI iri = variable.getIRI();
    String variablePrefixedName = iri2PrefixedName(iri);
    this.prefixedName2IRI.put(variablePrefixedName, iri);
    this.variablePrefixedNames.add(variablePrefixedName);
  }

  @Override public void recordOWLClass(@NonNull OWLEntity cls)
  {
    IRI iri = cls.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);

    this.classPrefixedNames.add(prefixedName);
  }

  @Override public void recordOWLNamedIndividual(@NonNull OWLEntity individual)
  {
    IRI iri = individual.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);

    this.namedIndividualPrefixedNames.add(prefixedName);
  }

  @Override public void recordOWLObjectProperty(@NonNull OWLEntity property)
  {
    IRI iri = property.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);

    this.objectPropertyPrefixedNames.add(prefixedName);
  }

  @Override public void recordOWLDataProperty(@NonNull OWLEntity property)
  {
    IRI iri = property.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);

    this.dataPropertyPrefixedNames.add(prefixedName);
  }

  @Override public void recordOWLAnnotationProperty(@NonNull OWLEntity property)
  {
    IRI iri = property.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);

    this.annotationPropertyPrefixedNames.add(prefixedName);
  }

  @Override public void recordOWLDatatype(@NonNull OWLEntity datatype)
  {
    IRI iri = datatype.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);

    this.datatypePrefixedNames.add(prefixedName);
  }

  @Override public void recordOWLClass(@NonNull SWRLClassBuiltInArgument classArgument)
  {
    IRI iri = classArgument.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);
    this.classPrefixedNames.add(prefixedName);
  }

  @Override public void recordOWLIndividual(@NonNull SWRLNamedIndividualBuiltInArgument individualArgument)
  {
    IRI iri = individualArgument.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);
    this.namedIndividualPrefixedNames.add(prefixedName);
  }

  @Override public void recordOWLObjectProperty(@NonNull SWRLObjectPropertyBuiltInArgument propertyArgument)
  {
    IRI iri = propertyArgument.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);
    this.objectPropertyPrefixedNames.add(prefixedName);
  }

  @Override public void recordOWLDataProperty(@NonNull SWRLDataPropertyBuiltInArgument propertyArgument)
  {
    IRI iri = propertyArgument.getIRI();
    String prefixedName = iri2PrefixedName(iri);

    recordPrefixedName2IRIMapping(prefixedName, iri);
    this.dataPropertyPrefixedNames.add(prefixedName);
  }

  @Override public boolean isOWLClass(@NonNull String prefixedName)
  {
    return this.classPrefixedNames.contains(prefixedName);
  }

  @Override public boolean isOWLNamedIndividual(@NonNull String prefixedName)
  {
    return this.namedIndividualPrefixedNames.contains(prefixedName);
  }

  @Override public boolean isOWLObjectProperty(@NonNull String prefixedName)
  {
    return this.objectPropertyPrefixedNames.contains(prefixedName);
  }

  @Override public boolean isOWLDataProperty(@NonNull String prefixedName)
  {
    return this.dataPropertyPrefixedNames.contains(prefixedName);
  }

  @Override public boolean isOWLAnnotationProperty(@NonNull String prefixedName)
  {
    return this.annotationPropertyPrefixedNames.contains(prefixedName);
  }

  @Override public boolean isOWLDatatype(@NonNull String prefixedName)
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