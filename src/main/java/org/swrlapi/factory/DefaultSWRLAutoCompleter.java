package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.ui.model.SWRLAutoCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @see org.swrlapi.ui.dialog.SWRLRuleEditorDialog
 */
class DefaultSWRLAutoCompleter implements SWRLAutoCompleter
{
  @NonNull private final List<String> shortForms;

  public DefaultSWRLAutoCompleter(@NonNull SWRLAPIOWLOntology swrlapiowlOntology)
  {
    DefaultPrefixManager prefixManager = swrlapiowlOntology.getPrefixManager();
    this.shortForms = new ArrayList<>();

    for (OWLEntity owlEntity : swrlapiowlOntology.getOWLOntology().getSignature(Imports.INCLUDED)) {
      String shortForm = prefixManager.getShortForm(owlEntity.getIRI());
      if (shortForm.startsWith(":")) // Strip leading ":"
        this.shortForms.add(shortForm.substring(1));
      this.shortForms.add(shortForm);
    }

    for (IRI swrlBuiltInIRI : swrlapiowlOntology.getSWRLBuiltInIRIs()) {
      String shortForm = prefixManager.getShortForm(swrlBuiltInIRI);
      if (shortForm.startsWith(":"))
        this.shortForms.add(shortForm.substring(1));
      this.shortForms.add(shortForm);
    }

    for (OWLRDFVocabulary v : OWLRDFVocabulary.values()) {
      String shortForm = v.getPrefixedName();
      this.shortForms.add(shortForm);
    }

    this.shortForms.add("sameAs");
    this.shortForms.add("differentFrom");

    Collections.sort(this.shortForms);
  }

  @NonNull @Override
  public List<String> getCompletions(@NonNull String prefix)
  { // TODO Look at - not very efficient
    return this.shortForms.stream().filter(shortForm -> shortForm.startsWith(prefix)).collect(Collectors.toList());
  }
}
