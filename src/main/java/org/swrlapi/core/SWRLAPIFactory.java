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
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.values.SQWRLResultValueFactory;
import org.swrlapi.sqwrl.values.impl.DefaultSQWRLResultValueFactory;
import org.swrlapi.ui.controller.SWRLAPIApplicationController;
import org.swrlapi.ui.dialog.SWRLAPIApplicationDialogManager;
import org.swrlapi.ui.model.SWRLAPIApplicationModel;
import org.swrlapi.ui.model.SWRLRulesTableModel;

/**
 * Factory for generating some of the core entities defined by the SWRLAPI.
 */
public class SWRLAPIFactory
{
	private static final String SQWRL_ICON_NAME = "SQWRL.gif";
	private static final String OWL2RL_ICON_NAME = "OWL2RL.gif";

	public static SWRLAPIOWLOntology createOntology(OWLOntology ontology)
	{
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);

		return new DefaultSWRLAPIOWLOntology(ontology, prefixManager);
	}

	public static SWRLAPIOWLOntology createOntology(OWLOntology ontology, DefaultPrefixManager prefixManager)
	{
		return new DefaultSWRLAPIOWLOntology(ontology, prefixManager);
	}

	public static SWRLAPIOWLOntology createOntology(File owlFile) throws SWRLAPIException
	{
		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = createOWLOntology(ontologyManager, owlFile);
		DefaultPrefixManager prefixManager = createPrefixManager(ontology);

		return SWRLAPIFactory.createOntology(ontology, prefixManager);
	}

	public static SWRLAPIOWLOntology createOntology() throws SWRLAPIException
	{
		try {
			OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
			OWLOntology ontology = ontologyManager.createOntology();

			DefaultPrefixManager prefixManager = createPrefixManager(ontology);

			return SWRLAPIFactory.createOntology(ontology, prefixManager);
		} catch (OWLOntologyCreationException e) {
			throw new SWRLAPIException("Error creating OWL ontology", e);
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
			if (defaultPrefix != null)
				prefixManager.setDefaultPrefix(defaultPrefix);
		}
		return prefixManager;
	}

	public static SWRLAPIApplicationModel createApplicationModel(SWRLAPIOWLOntology swrlapiOWLOntology,
			SWRLRuleEngine ruleEngine)
	{
		return new SWRLAPIApplicationModel(swrlapiOWLOntology, ruleEngine);
	}

	public static SWRLAPIApplicationController createApplicationController(SWRLAPIApplicationModel applicationModel)
	{
		return new SWRLAPIApplicationController(applicationModel);
	}

	public static SWRLAPIApplicationDialogManager createApplicationDialogManager(SWRLAPIApplicationModel applicationModel)
	{
		return new SWRLAPIApplicationDialogManager(applicationModel);
	}

	public static SWRLAPIOntologyProcessor createOntologyProcessor(SWRLAPIOWLOntology swrlapiOWLOntology)
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

	public static DefaultSWRLAPIRulePrinter createRulePrinter(DefaultPrefixManager prefixManager)
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

	public static SWRLRuleEngine createQueryEngine(SWRLAPIOWLOntology swrlapiOWLOntology,
			SWRLRuleEngineManager.TargetSWRLRuleEngineCreator swrlRuleEngineCreator) throws SWRLAPIException
	{
		SWRLRuleEngineFactory swrlRuleEngineFactory = SWRLAPIFactory.createSWRLRuleEngineFactory();
		swrlRuleEngineFactory.registerRuleEngine(swrlRuleEngineCreator);

		return swrlRuleEngineFactory.createSWRLRuleEngine(swrlapiOWLOntology);
	}

	public static Icon getOWL2RLReasonerIcon() throws SWRLAPIException
	{
		URL url = SWRLAPIFactory.class.getResource(OWL2RL_ICON_NAME);

		if (url != null)
			return new ImageIcon(url);
		else
			throw new SWRLAPIException("No OWL 2 RL icon found!");
	}

	public static Icon getSQWRLIcon() throws SWRLAPIException
	{
		URL url = SWRLAPIFactory.class.getResource(SQWRL_ICON_NAME);

		if (url != null)
			return new ImageIcon(url);
		else
			throw new SWRLAPIException("No SQWRL icon found!");
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

	private static OWLOntology createOWLOntology(OWLOntologyManager ontologyManager, File file) throws SWRLAPIException
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("http://www.w3.org/2003/11/swrl#", resourceName2File("owl/swrl.owl"));
		map.put("http://www.w3.org/2003/11/swrlb#", resourceName2File("owl/swrlb.owl"));
		map.put("http://swrl.stanford.edu/ontologies/3.3/swrla.owl", resourceName2File("owl/swrla.owl"));
		map.put("http://swrl.stanford.edu/ontologies/built-ins/3.4/swrlm.owl", resourceName2File("owl/swrlm.owl"));
		map.put("http://swrl.stanford.edu/ontologies/built-ins/3.3/swrlx.owl", resourceName2File("owl/swrlx.owl"));
		map.put("http://swrl.stanford.edu/ontologies/built-ins/3.4/swrlxml.owl", resourceName2File("owl/swrlxml.owl"));
		map.put("http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl", resourceName2File("owl/temporal.owl"));
		map.put("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl", resourceName2File("owl/sqwrl.owl"));

		for (String key : map.keySet())
			ontologyManager.addIRIMapper(new SimpleIRIMapper(IRI.create(key), IRI.create(map.get(key))));

		try {
			return ontologyManager.loadOntologyFromOntologyDocument(file);
		} catch (OWLOntologyCreationException e) {
			throw new SWRLAPIException("Error create OWL ontology from file " + file.getAbsolutePath() + ": "
					+ e.getMessage());
		}
	}

	private static String resourceName2File(String resourceName)
	{
		URL url = SWRLAPIFactory.class.getClassLoader().getResource(resourceName);
		if (url == null)
			throw new RuntimeException("Could not find resource " + resourceName);
		return "file:///" + url.getFile();
	}
}
