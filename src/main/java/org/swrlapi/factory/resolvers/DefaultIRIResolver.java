package org.swrlapi.factory.resolvers;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.IRIResolver;

import java.util.HashMap;
import java.util.Map;

public class DefaultIRIResolver implements IRIResolver
{
  @NonNull private final DefaultPrefixManager prefixManager;

  @NonNull private final Map<@NonNull String, @NonNull String> autogenNamespace2Prefix = new HashMap<>();

  private int autogenPrefixNumber = 0;

  public DefaultIRIResolver(@NonNull DefaultPrefixManager prefixManager)
  {
    this.prefixManager = prefixManager;
  }

  @Override public void reset()
  {
    this.autogenNamespace2Prefix.clear();
    this.autogenPrefixNumber = 0;
  }

  @Override @NonNull public String iri2PrefixedName(@NonNull IRI iri)
  {
    String existingPrefixedName = this.prefixManager.getPrefixIRI(iri);
    if (existingPrefixedName != null)
      return existingPrefixedName;
    else {
      String namespace = iri.getNamespace();
      com.google.common.base.Optional<@NonNull String> remainder = iri.getRemainder();
      if (remainder.isPresent()) {
        if (namespace.isEmpty()) {
          String prefixedName = remainder.get();
          return prefixedName;
        } else if (this.autogenNamespace2Prefix.containsKey(namespace)) {
          String prefixedName = this.autogenNamespace2Prefix.get(namespace) + remainder.get();
          return prefixedName;
        } else {
          String autogenPrefix = "autogen" + this.autogenPrefixNumber++ + ":";
          String prefixedName = autogenPrefix + remainder.get();
          this.autogenNamespace2Prefix.put(namespace, autogenPrefix);
          return prefixedName;
        }
      } else
        throw new IllegalArgumentException("could not create prefixed name for IRI " + iri);
    }
  }

  @Override @NonNull public IRI prefixedName2IRI(@NonNull String prefixedName)
  {
    try {
      return this.prefixManager.getIRI(prefixedName);
    } catch (RuntimeException e) {
      throw new IllegalArgumentException("could not find IRI for prefixed name " + prefixedName);
    }
  }
}