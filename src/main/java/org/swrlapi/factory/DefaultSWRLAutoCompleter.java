package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.ui.model.SWRLAutoCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @see org.swrlapi.ui.dialog.SWRLRuleEditorDialog
 */
class DefaultSWRLAutoCompleter implements SWRLAutoCompleter
{
  private static final Logger log = LoggerFactory.getLogger(DefaultSWRLAutoCompleter.class);

  @NonNull private final List<@NonNull String> shortForms;

  public DefaultSWRLAutoCompleter(@NonNull SWRLAPIOWLOntology swrlapiowlOntology)
  {
    DefaultPrefixManager prefixManager = swrlapiowlOntology.getPrefixManager();
    this.shortForms = new ArrayList<>();

    for (OWLEntity owlEntity : swrlapiowlOntology.getOWLOntology().getSignature(Imports.INCLUDED)) {
      //log.info("iri " + owlEntity.getIRI() + ", shortForm " + prefixManager.getShortForm(owlEntity.getIRI()));
      String shortForm = prefixManager.getShortForm(owlEntity.getIRI());
      if (shortForm != null) {
        if (shortForm.startsWith(":")) // Strip leading ":"
          this.shortForms.add(shortForm.substring(1));
        this.shortForms.add(shortForm);
      }
    }

    for (IRI swrlBuiltInIRI : swrlapiowlOntology.getSWRLBuiltInIRIs()) {
      String shortForm = prefixManager.getShortForm(swrlBuiltInIRI);
      if (shortForm != null) {
        if (shortForm.startsWith(":"))
          this.shortForms.add(shortForm.substring(1));
        this.shortForms.add(shortForm);
      }
    }

    for (OWLRDFVocabulary v : OWLRDFVocabulary.values()) {
      String shortForm = v.getPrefixedName();
      if (shortForm != null)
        this.shortForms.add(shortForm);
    }

    this.shortForms.add("sameAs");
    this.shortForms.add("differentFrom");

    Collections.sort(this.shortForms);
  }

  @NonNull @Override public List<@NonNull String> getCompletions(@NonNull String prefix)
  { // TODO Look at - not very efficient
    List<@NonNull String> completions = new ArrayList<>();

    for (String shortForm : shortForms) {
      if (shortForm.startsWith(prefix))
        completions.add(shortForm);
    }
    return completions;
  }
}
