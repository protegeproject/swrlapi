package org.swrlapi.core;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.builtins.arguments.impl.DefaultSWRLBuiltInArgumentFactory;
import org.swrlapi.core.impl.DefaultOWLLiteralFactory;
import org.swrlapi.core.impl.DefaultSWRLAPILiteralFactory;
import org.swrlapi.core.impl.DefaultSWRLAPIOWLDataFactory;
import org.swrlapi.core.impl.DefaultSWRLAPIOWLDatatypeFactory;
import org.swrlapi.core.impl.DefaultSWRLAPIOWLOntology;
import org.swrlapi.core.impl.DefaultSWRLAPIOntologyProcessor;
import org.swrlapi.core.impl.DefaultSWRLAPIRulePrinter;
import org.swrlapi.core.impl.DefaultSWRLRuleEngineFactory;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import org.swrlapi.sqwrl.values.impl.DefaultSQWRLResultValueFactory;
import org.swrlapi.ui.controller.SWRLAPIApplicationController;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;

/**
 * Factory for generating some of the core entities defined by the SWRLAPI.
 */
public class SWRLAPIFactory
{
	private static final String SQWRL_ICON_NAME = "SQWRL.gif";
	private static final String OWL2RL_ICON_NAME = "OWL2RL.gif";

	public static SWRLAPIOWLOntology createSWRLAPIOWLOntology(OWLOntology ontology)
	{
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);

		return new DefaultSWRLAPIOWLOntology(ontology, prefixManager);
	}

	public static SWRLAPIOWLOntology createSWRLAPIOWLOntology(OWLOntology ontology, DefaultPrefixManager prefixManager)
	{
		return new DefaultSWRLAPIOWLOntology(ontology, prefixManager);
	}

	public static SWRLAPIOWLOntology createSWRLAPIOWLOntology(String owlFileName)
	{
		final String[] canned = { "swrl.owl", "swrlb.owl", "swrla.owl", "sqwrl.owl", "swrlm.owl", "temporal.owl",
				"swrlx.owl", "swrlxml.owl" }; // TODO Temporary

		try {
			OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();

			for (String can : canned) { // TODO Temporary hack!
				File f = new File("/tmp/" + can);
				ontologyManager.loadOntologyFromOntologyDocument(f);
			}
			File owlFile = new File(owlFileName);
			OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(owlFile);
			DefaultPrefixManager prefixManager = createPrefixManager(ontology);

			return SWRLAPIFactory.createSWRLAPIOWLOntology(ontology, prefixManager);
		} catch (OWLOntologyCreationException e) {
			throw new RuntimeException("Error creating OWL ontology: " + e.getMessage());
		}
	}

	public static DefaultPrefixManager createPrefixManager(OWLOntology ontology)
	{
		DefaultPrefixManager prefixManager = new DefaultPrefixManager();
		OWLOntologyManager owlOntologyManager = ontology.getOWLOntologyManager();
		OWLOntologyFormat ontologyFormat = owlOntologyManager.getOntologyFormat(ontology);

		if (ontologyFormat.isPrefixOWLOntologyFormat()) {
			PrefixOWLOntologyFormat prefixOntologyFormat = ontologyFormat.asPrefixOWLOntologyFormat();
			String defaultPrefix = prefixOntologyFormat.getDefaultPrefix();
			Map<String, String> map = prefixOntologyFormat.getPrefixName2PrefixMap();
			for (String prefix : map.keySet())
				prefixManager.setPrefix(prefix, map.get(prefix));
			prefixManager.setDefaultPrefix(defaultPrefix);
		}
		return prefixManager;
	}

	public static SWRLAPIApplicationModel createSWRLAPIApplicationModel(SWRLAPIOWLOntology swrlapiOWLOntology,
			SWRLRuleEngine ruleEngine)
	{
		return new SWRLAPIApplicationModel(swrlapiOWLOntology, ruleEngine);
	}

	public static SWRLAPIApplicationController createSWRLAPIApplicationController(SWRLAPIApplicationModel applicationModel)
	{
		return new SWRLAPIApplicationController(applicationModel);
	}

	public static SWRLAPIOntologyProcessor createSWRLAPIOntologyProcessor(SWRLAPIOWLOntology swrlapiOWLOntology)
	{
		return new DefaultSWRLAPIOntologyProcessor(swrlapiOWLOntology);
	}

	public static SWRLRuleEngineFactory createSWRLRuleEngineFactory()
	{
		return new DefaultSWRLRuleEngineFactory();
	}

	public static SWRLParser createSWRLParser(SWRLAPIOWLOntology swrlapiOWLOntology)
	{
		return new SWRLParser(swrlapiOWLOntology);
	}

	public static DefaultSWRLAPIRulePrinter createSWRLAPIRulePrinter(DefaultPrefixManager prefixManager)
	{
		return new DefaultSWRLAPIRulePrinter(prefixManager);
	}

	public static SWRLRulesTableModel createSWRLRulesTableModel(SWRLRuleEngine swrlRuleEngine,
			DefaultSWRLAPIRulePrinter swrlRulePrinter)
	{
		return new SWRLRulesTableModel(swrlRuleEngine, swrlRulePrinter);
	}

	public static SWRLAPIOWLDataFactory createSWRLAPIOWLDataFactory(SWRLAPIIRIResolver iriResolver)
	{
		return new DefaultSWRLAPIOWLDataFactory(iriResolver);
	}

	public static SWRLAPIOWLDatatypeFactory createSWRLAPIOWLDatatypeFactory()
	{
		return new DefaultSWRLAPIOWLDatatypeFactory();
	}

	public static OWLLiteralFactory createOWLLiteralFactory(SWRLAPIOWLDatatypeFactory owlDatatypeFactory)
	{
		return new DefaultOWLLiteralFactory(owlDatatypeFactory);
	}

	public static SWRLAPILiteralFactory createSWRLAPILiteralFactory(OWLLiteralFactory owlLiteralFactory)
	{
		return new DefaultSWRLAPILiteralFactory(owlLiteralFactory);
	}

	public static SWRLBuiltInArgumentFactory createSWRLBuiltInArgumentFactory(SWRLAPIIRIResolver iriResolver,
			OWLLiteralFactory owlLiteralFactory)
	{
		return new DefaultSWRLBuiltInArgumentFactory(iriResolver, owlLiteralFactory);
	}

	public static SQWRLResultValueFactory createSQWRLResultValueFactory(SWRLAPIIRIResolver iriResolver,
			OWLLiteralFactory owlLiteralFactory)
	{
		return new DefaultSQWRLResultValueFactory(iriResolver, owlLiteralFactory);
	}

	public static SWRLRuleEngine createSQWRLQueryEngine(SWRLAPIOWLOntology swrlapiOWLOntology,
			SWRLRuleEngineManager.TargetSWRLRuleEngineCreator swrlRuleEngineCreator)
	{
		try {
			SWRLRuleEngineFactory swrlRuleEngineFactory = SWRLAPIFactory.createSWRLRuleEngineFactory();
			swrlRuleEngineFactory.registerRuleEngine(swrlRuleEngineCreator);

			return swrlRuleEngineFactory.createSWRLRuleEngine(swrlapiOWLOntology);
		} catch (SWRLRuleEngineException e) {
			throw new RuntimeException("Error creating rule engine: " + e.getMessage());
		}
	}

	public static Icon getOWL2RLReasonerIcon()
	{
		URL url = SWRLAPIFactory.class.getResource(OWL2RL_ICON_NAME);

		if (url != null)
			return new ImageIcon(url);
		else
			throw new RuntimeException("No OWL 2 RL icon found!");
	}

	public static Icon getSQWRLIcon()
	{
		URL url = SWRLAPIFactory.class.getResource(SQWRL_ICON_NAME);

		if (url != null)
			return new ImageIcon(url);
		else
			throw new RuntimeException("No SQWRL icon found!");
	}

	@SuppressWarnings("unused")
	private static DefaultPrefixManager createPrefixManager()
	{ // TODO Hard coding hack!
		DefaultPrefixManager prefixManager = new DefaultPrefixManager(
		// "http://swrl.stanford.edu/ontologies/tests/4.3/SWRLSimple.owl#");
		// "http://swrl.stanford.edu/ontologies/tests/4.3/SQWRLCollectionsTests.owl#");
		// "http://swrl.stanford.edu/ontologies/tests/4.3/SQWRLCoreTests.owl#");
		// "http://swrl.stanford.edu/ontologies/tests/4.3/SWRLInferenceTests.owl#");
				"http://swrl.stanford.edu/ontologies/tests/4.3/SWRLCoreTests.owl#");
		prefixManager.setPrefix("swrl:", "http://www.w3.org/2003/11/swrl#");
		prefixManager.setPrefix("swrlb:", "http://www.w3.org/2003/11/swrlb#");
		prefixManager.setPrefix("sqwrl:", "http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#");
		prefixManager.setPrefix("swrlm:", "http://swrl.stanford.edu/ontologies/built-ins/3.4/swrlm.owl#");
		prefixManager.setPrefix("temporal:", "http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl#");
		prefixManager.setPrefix("swrlx:", "http://swrl.stanford.edu/ontologies/built-ins/3.3/swrlx.owl#");
		prefixManager.setPrefix("swrlxml:", "http://swrl.stanford.edu/ontologies/built-ins/3.4/swrlxml.owl#");
		prefixManager.setPrefix("swrla:", "http://swrl.stanford.edu/ontologies/3.3/swrla.owl#");

		return prefixManager;
	}

	@SuppressWarnings("unused")
	private static OWLOntology createOWLOntology(OWLOntologyManager ontologyManager, File file)
	{
		Map<String, String> map = new HashMap<String, String>();

		// TODO Put in resources dir?
		map.put("http://swrl.stanford.edu/ontologies/3.3/swrla.owl", "file:///tmp/swrla.owl");
		map.put("http://swrl.stanford.edu/ontologies/built-ins/3.4/swrlm.owl", "file:///tmp/swrlm.owl");
		map.put("http://swrl.stanford.edu/ontologies/built-ins/3.4/swrlm.owl", "file:///tmp/swrlm.owl");
		map.put("http://swrl.stanford.edu/ontologies/built-ins/3.3/swrlx.owl", "file:///tmp/swrlx.owl");
		map.put("http://swrl.stanford.edu/ontologies/built-ins/3.4/swrlxml.owl", "file:///tmp/swrlxml.owl");
		map.put("http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl", "file:///tmp/temporal.owl");
		map.put("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl", "file:///tmp/sqwrl.owl");

		for (String key : map.keySet())
			ontologyManager.addIRIMapper(new SimpleIRIMapper(IRI.create(key), IRI.create(map.get(key))));

		try {
			return ontologyManager.loadOntologyFromOntologyDocument(file);
		} catch (OWLOntologyCreationException e) {
			throw new RuntimeException("Error create OWL ontology from file " + file.getAbsolutePath() + ": "
					+ e.getMessage());
		}
	}
}
