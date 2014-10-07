package org.swrlapi.ui.dialog;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.core.SWRLAPIOWLOntology;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @see org.swrlapi.ui.dialog.SWRLRuleEditorDialog
 */
public class SWRLAutoCompleter
{
	private final List<String> shortForms;

	public SWRLAutoCompleter(SWRLAPIOWLOntology swrlapiowlOntology)
	{
		DefaultPrefixManager prefixManager = swrlapiowlOntology.getPrefixManager();
		this.shortForms = new ArrayList<>();

		for (OWLEntity owlEntity : swrlapiowlOntology.getOWLOntology().getSignature(true)) {
			String shortForm = prefixManager.getShortForm(owlEntity.getIRI());
			if (shortForm.startsWith(":"))
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

	public List<String> getCompletions(String prefix)
	{ // TODO Look at - not very efficient
		List<String> completions = new ArrayList<>();

		for (String shortForm : shortForms) {
			if (shortForm.startsWith(prefix))
				completions.add(shortForm);
		}
		return completions;
	}
}
