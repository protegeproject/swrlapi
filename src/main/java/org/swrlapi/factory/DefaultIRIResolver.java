package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.IRIResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultIRIResolver implements IRIResolver
{
  @NonNull private final DefaultPrefixManager prefixManager;

  @NonNull private final Map<@NonNull String, @NonNull String> autogenNamespace2Prefix = new HashMap<>();

  private int autogenPrefixNumber = 0;
  @Nullable private String defaultPrefix;

  public DefaultIRIResolver()
  {
    this.prefixManager = new DefaultPrefixManager();
  }

  public DefaultIRIResolver(@NonNull String defaultPrefix)
  {
    this.prefixManager = new DefaultPrefixManager();
    this.prefixManager.setDefaultPrefix(defaultPrefix);
    this.defaultPrefix = defaultPrefix;
  }

  @Override public void reset()
  {
    this.autogenNamespace2Prefix.clear();
    this.autogenPrefixNumber = 0;
  }

  @Override @NonNull public Optional<@NonNull IRI> prefixedName2IRI(@NonNull String prefixedName)
  {
    IRI iri = this.prefixManager.getIRI(prefixedName);

    if (iri != null)
      return Optional.of(iri);
    else
      return Optional.empty();
  }

  @Override public @NonNull Optional<@NonNull String> iri2PrefixedName(@NonNull IRI iri)
  {
    String existingPrefixedName = this.prefixManager.getPrefixIRI(iri);
    if (existingPrefixedName != null)
      return Optional.of(existingPrefixedName);
    else {
      String namespace = iri.getNamespace();
      com.google.common.base.Optional<@NonNull String> remainder = iri.getRemainder();
      if (remainder.isPresent()) {
        if (namespace.isEmpty()) {
          String prefixedName = remainder.get();
          return Optional.of(prefixedName);
        } else { // OWLAPI prefix manager does not have a prefixed form. We auto-generate a prefix for each namespace.
          //          if (this.autogenNamespace2Prefix.containsKey(namespace)) {
          //            return Optional.of(this.autogenNamespace2Prefix.get(namespace) + remainder.get());
          //          } else {
          //            String autogenPrefix = "autogen" + this.autogenPrefixNumber++ + ":";
          //            this.autogenNamespace2Prefix.put(namespace, autogenPrefix);
          //            this.prefixManager.setPrefix(autogenPrefix, namespace);
          //            return Optional.of(autogenPrefix + remainder.get());
          //          }
          // TODO This autogen seems odd
          return Optional.empty();
        }
      } else
        return Optional.empty();
    }
  }

  @Override @NonNull public Optional<@NonNull String> iri2ShortForm(@NonNull IRI iri)
  {
    String shortForm = this.prefixManager.getShortForm(iri);

    if (shortForm == null || shortForm.isEmpty())
      return Optional.empty();
    else
      return Optional.of(shortForm);
  }

  @Override public void setPrefix(@NonNull String prefix, @NonNull String namespace)
  {
    this.prefixManager.setPrefix(prefix, namespace);
  }

  @Override public void updatePrefixes(@NonNull OWLOntology ontology)
  {
    OWLOntologyManager owlOntologyManager = ontology.getOWLOntologyManager();
    OWLDocumentFormat ontologyFormat = owlOntologyManager.getOntologyFormat(ontology);

    this.prefixManager.clear();
    if (this.defaultPrefix != null)
      this.prefixManager.setDefaultPrefix(this.defaultPrefix);

    if (ontologyFormat != null && ontologyFormat.isPrefixOWLOntologyFormat()) {
      PrefixDocumentFormat prefixOntologyFormat = ontologyFormat.asPrefixOWLOntologyFormat();

      Map<@NonNull String, String> map = prefixOntologyFormat.getPrefixName2PrefixMap();
      for (String prefix : map.keySet())
        this.prefixManager.setPrefix(prefix, map.get(prefix));
    }
    addSWRLAPIPrefixes();

    //log.info("updated prefixes " + prefixManager.getPrefixName2PrefixMap());
  }

  private void addSWRLAPIPrefixes()
  {
    this.prefixManager.setPrefix("owl:", "http://www.w3.org/2002/07/owl#");
    this.prefixManager.setPrefix("swrl:", "http://www.w3.org/2003/11/swrl#");
    this.prefixManager.setPrefix("swrlb:", "http://www.w3.org/2003/11/swrlb#");
    this.prefixManager.setPrefix("sqwrl:", "http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#");
    this.prefixManager.setPrefix("swrlm:", "http://swrl.stanford.edu/ontologies/built-ins/3.4/swrlm.owl#");
    this.prefixManager.setPrefix("temporal:", "http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl#");
    this.prefixManager.setPrefix("swrlx:", "http://swrl.stanford.edu/ontologies/built-ins/3.3/swrlx.owl#");
    this.prefixManager.setPrefix("swrla:", "http://swrl.stanford.edu/ontologies/3.3/swrla.owl#");
  }
}