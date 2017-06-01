package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.ui.model.SWRLAutoCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @see org.swrlapi.ui.dialog.SWRLRuleEditorDialog
 */
class DefaultSWRLAutoCompleter implements SWRLAutoCompleter
{
  private static final Logger log = LoggerFactory.getLogger(DefaultSWRLAutoCompleter.class);

  @NonNull private final List<@NonNull String> renderings;

  public DefaultSWRLAutoCompleter(@NonNull SWRLAPIOWLOntology swrlapiowlOntology)
  {
    IRIResolver iriResolver = swrlapiowlOntology.getIRIResolver();
    this.renderings = new ArrayList<>();

    for (OWLEntity owlEntity : swrlapiowlOntology.getOWLOntology().getSignature(Imports.INCLUDED)) {
      //log.info("iri " + owlEntity.getIRI() + ", shortForm " + prefixManager.iri2ShortForm(owlEntity.getIRI()));
      Optional<@NonNull String> shortForm = iriResolver.iri2ShortForm(owlEntity.getIRI());
      if (shortForm.isPresent()) {
        if (shortForm.get().startsWith(":")) // Strip leading ":"
          this.renderings.add(shortForm.get().substring(1));
        this.renderings.add(shortForm.get());
      }
    }

    for (IRI swrlBuiltInIRI : swrlapiowlOntology.getSWRLBuiltInIRIs()) {
      Optional<@NonNull String> prefixedName = iriResolver.iri2PrefixedName(swrlBuiltInIRI);
      if (prefixedName.isPresent()) {
        if (prefixedName.get().startsWith(":"))
          this.renderings.add(prefixedName.get().substring(1));
        this.renderings.add(prefixedName.get());
      }
    }

    for (OWLRDFVocabulary v : OWLRDFVocabulary.values()) {
      String prefixedName = v.getPrefixedName();
      if (prefixedName != null)
        this.renderings.add(prefixedName);
    }

    for (XSDVocabulary v : XSDVocabulary.values()) {
      String prefixedName = v.getPrefixedName();
      if (prefixedName != null)
        this.renderings.add(prefixedName);
    }

    this.renderings.add("sameAs");
    this.renderings.add("differentFrom");
    this.renderings.add("and");
    this.renderings.add("or");
    this.renderings.add("not");

    Collections.sort(this.renderings);
  }

  @NonNull @Override public List<@NonNull String> getCompletions(@NonNull String prefix)
  { // TODO Look at - not very efficient
    List<@NonNull String> completions = new ArrayList<>();

    for (String rendering : renderings) {
      if (rendering.startsWith(prefix))
        completions.add(rendering);
    }
    return completions;
  }
}
