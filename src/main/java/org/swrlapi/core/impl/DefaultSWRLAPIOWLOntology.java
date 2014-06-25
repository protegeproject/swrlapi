package org.swrlapi.core.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLPredicate;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.core.OWLIRIResolver;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLAPIOntologyProcessor;
import org.swrlapi.core.SWRLAPIRule;

public class DefaultSWRLAPIOWLOntology implements SWRLAPIOWLOntology
{
	private final OWLOntologyManager ontologyManager;
	private final OWLOntology ontology;
	private final DefaultPrefixManager prefixManager;
	private final OWLIRIResolver owlIRIResolver;
	private final SWRLAPIOWLDataFactory swrlapiOWLDataFactory;
	private final SWRLAPIOntologyProcessor swrlapiOntologyProcessor;

	public DefaultSWRLAPIOWLOntology(OWLOntologyManager ontologyManager, OWLOntology ontology,
			DefaultPrefixManager prefixManager)
	{
		this.ontologyManager = ontologyManager;
		this.ontology = ontology;
		this.prefixManager = prefixManager;
		this.owlIRIResolver = new OWLIRIResolver(this.prefixManager);
		this.swrlapiOWLDataFactory = SWRLAPIFactory.createSWRLAPIOWLDataFactory(owlIRIResolver);
		this.swrlapiOntologyProcessor = SWRLAPIFactory.createSWRLAPIOntologyProcessor(this);
	}

	@Override
	public OWLOntology getOWLOntology()
	{
		return this.ontology;
	}

	@Override
	public OWLOntologyManager getOWLOntologyManager()
	{
		return this.ontologyManager;
	}

	@Override
	public DefaultPrefixManager getPrefixManager()
	{
		return this.prefixManager;
	}

	@Override
	public Set<SWRLAPIRule> getSWRLAPIRules()
	{
		Set<SWRLAPIRule> swrlapiRules = new HashSet<SWRLAPIRule>();

		for (SWRLRule owlapiRule : getOWLOntology().getAxioms(AxiomType.SWRL_RULE, true)) {
			SWRLAPIRule swrlapiRule = convertOWLAPIRule2SWRLAPIRule(owlapiRule);
			swrlapiRules.add(swrlapiRule);
		}

		return swrlapiRules;
	}

	@Override
	public SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
	{
		return this.swrlapiOWLDataFactory;
	}

	@Override
	public OWLDataFactory getOWLDataFactory()
	{
		return ontologyManager.getOWLDataFactory();
	}

	@Override
	public SWRLAPIOntologyProcessor getSWRLAPIOntologyProcessor()
	{
		return this.swrlapiOntologyProcessor;
	}

	@Override
	public OWLIRIResolver getOWLIRIResolver()
	{
		return getSWRLAPIOWLDataFactory().getOWLIRIResolver();
	}

	@Override
	public void startBulkConversion()
	{
		// TODO
	}

	@Override
	public void completeBulkConversion()
	{
		// TODO
	}

	@Override
	public boolean hasOntologyChanged()
	{
		return false; // TODO
	}

	@Override
	public void resetOntologyChanged()
	{
		// TODO
	}

	// void addRuleNameAnnotation(SWRLRule rule, String ruleName)
	// {
	// OWLAnnotationProperty labelAnnotationProperty = getOWLDataFactory().getRDFSLabel();
	// OWLLiteral label = getOWLDataFactory().getOWLLiteral(ruleName, "en");
	// OWLAnnotation labelAnnotation = getOWLDataFactory().getOWLAnnotation(labelAnnotationProperty, label);
	//
	// OWLAxiom anotationAssertionAxiom = getOWLDataFactory().getOWLAnnotationAssertionAxiom(rule, labelAnnotation);
	// this.ontologyManager.applyChange(new AddAxiom(this.ontology, anotationAssertionAxiom));
	// }

	@Override
	public OWLClass getInjectedOWLClass()
	{
		// TODO This is incorrect!!
		IRI iri = IRI
		// .create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + UUID.randomUUID().toString());
				.create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + "fred");

		return getSWRLAPIOWLDataFactory().getOWLClass(iri);
	}

	@Override
	public OWLNamedIndividual getInjectedOWLNamedIndividual()
	{
		// TODO This is incorrect!

		IRI iri = IRI
		// .create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + UUID.randomUUID().toString());
				.create("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#" + "fred");

		return getSWRLAPIOWLDataFactory().getOWLNamedIndividual(iri);
	}

	@Override
	public boolean isSWRLBuiltIn(IRI iri)
	{
		return true;
		// throw new RuntimeException("isSWRLBuiltIn not implemented");
	}

	// TODO We really do not want the following three methods here. They are convenience methods only and are used only by
	// a the temporal built-in library.
	@Override
	public boolean isOWLIndividualOfType(IRI individualIRI, IRI classIRI)
	{
		return true; // TODO
		// throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI)
	{
		throw new RuntimeException("Not implemented");
	}

	private String getRuleName(SWRLRule owlapiRule)
	{
		String ruleName = "XX" + owlapiRule.hashCode(); // TODO Get rule name from annotation property if there.

		OWLAnnotationProperty labelAnnotation = getOWLDataFactory().getOWLAnnotationProperty(
				OWLRDFVocabulary.RDFS_LABEL.getIRI());

		for (OWLAnnotation annotation : owlapiRule.getAnnotations(labelAnnotation)) {
			System.err.println("ann: " + annotation);
		}
		return ruleName;
	}

	private boolean getIsActive(SWRLRule owlapiRule)
	{
		OWLAnnotationProperty enabledAnnotation = getOWLDataFactory().getOWLAnnotationProperty(
				IRI.create("http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled"));

		for (OWLAnnotation annotation : owlapiRule.getAnnotations(enabledAnnotation)) {
			if (annotation.getValue() instanceof OWLLiteral) {
				OWLLiteral literal = (OWLLiteral)annotation.getValue();
				if (literal.isBoolean())
					return literal.parseBoolean();
			}
		}
		return true;
	}

	private String getComment(SWRLRule owlapiRule)
	{
		OWLAnnotationProperty commentAnnotation = getOWLDataFactory().getOWLAnnotationProperty(
				OWLRDFVocabulary.RDFS_COMMENT.getIRI());

		for (OWLAnnotation annotation : owlapiRule.getAnnotations(commentAnnotation)) {
			if (annotation.getValue() instanceof OWLLiteral) {
				OWLLiteral literal = (OWLLiteral)annotation.getValue();
				return literal.getLiteral(); // TODO We just pick one for the moment
			}
		}
		return "";
	}

	/**
	 * We basically take an OWLAPI {@link SWRLRule} object and for every OWLAPI {@link SWRLBuiltInAtom} in it we create a
	 * SWRLAPI {@link SWRLAPIBuiltInAtom}; all other atoms remain the same.
	 */
	private SWRLAPIRule convertOWLAPIRule2SWRLAPIRule(SWRLRule owlapiRule)
	{
		List<SWRLAtom> owlapiBodyAtoms = new ArrayList<SWRLAtom>(owlapiRule.getBody());
		List<SWRLAtom> owlapiHeadAtoms = new ArrayList<SWRLAtom>(owlapiRule.getHead());
		List<SWRLAtom> swrlapiBodyAtoms = new ArrayList<SWRLAtom>();
		List<SWRLAtom> swrlapiHeadAtoms = new ArrayList<SWRLAtom>();
		String ruleName = getRuleName(owlapiRule);
		boolean isActive = getIsActive(owlapiRule);
		String comment = getComment(owlapiRule);

		for (SWRLAtom atom : owlapiBodyAtoms) {
			if (isSWRLBuiltInAtom(atom)) {
				SWRLBuiltInAtom builtInAtom = (SWRLBuiltInAtom)atom;
				IRI builtInIRI = builtInAtom.getPredicate();
				String builtInPrefixedName = getOWLIRIResolver().iri2PrefixedName(builtInIRI);
				List<SWRLDArgument> swrlDArguments = builtInAtom.getArguments();
				List<SWRLBuiltInArgument> swrlBuiltInArguments = convertSWRLDArguments2SWRLBuiltInArguments(swrlDArguments);
				SWRLBuiltInAtom swrlapiAtom = getSWRLAPIOWLDataFactory().getSWRLAPIBuiltInAtom(ruleName, builtInIRI,
						builtInPrefixedName, swrlBuiltInArguments);
				swrlapiBodyAtoms.add(swrlapiAtom);
			} else
				swrlapiBodyAtoms.add(atom); // Only built-in atoms are converted; other atoms remain the same
		}

		for (SWRLAtom atom : owlapiHeadAtoms) {
			if (isSWRLBuiltInAtom(atom)) {
				SWRLBuiltInAtom builtInAtom = (SWRLBuiltInAtom)atom;
				IRI builtInIRI = builtInAtom.getPredicate();
				String builtInPrefixedName = getOWLIRIResolver().iri2PrefixedName(builtInIRI);
				List<SWRLDArgument> swrlDArguments = builtInAtom.getArguments();
				List<SWRLBuiltInArgument> swrlBuiltInArguments = convertSWRLDArguments2SWRLBuiltInArguments(swrlDArguments);
				SWRLBuiltInAtom swrlapiAtom = getSWRLAPIOWLDataFactory().getSWRLAPIBuiltInAtom(ruleName, builtInIRI,
						builtInPrefixedName, swrlBuiltInArguments);
				swrlapiHeadAtoms.add(swrlapiAtom);
			} else
				swrlapiHeadAtoms.add(atom); // Only built-in atoms are converted; other atoms remain the same
		}
		return new DefaultSWRLAPIRule(ruleName, swrlapiBodyAtoms, swrlapiHeadAtoms, getOWLIRIResolver(), isActive, comment);
	}

	/**
	 * Both the OWLAPI and the SWRLAPI use the {@link SWRLBuiltInAtom} class to represent built-in atoms. However, the
	 * SWRLAPI has a richer range of possible argument types. The OWLAPI allows {@link SWRLDArgument} built-in arguments
	 * only, whereas the SWRLAPI has a range of types. These types are represented buy the {@link SWRLBuiltInArgument}
	 * interface.
	 * 
	 * @see SWRLBuiltInArgument
	 */
	private List<SWRLBuiltInArgument> convertSWRLDArguments2SWRLBuiltInArguments(List<SWRLDArgument> swrlDArguments)
	{
		List<SWRLBuiltInArgument> swrlBuiltInArguments = new ArrayList<SWRLBuiltInArgument>();

		for (SWRLDArgument swrlDArgument : swrlDArguments) {
			SWRLBuiltInArgument swrlBuiltInArgument = convertSWRLDArgument2SWRLBuiltInArgument(swrlDArgument);
			swrlBuiltInArguments.add(swrlBuiltInArgument);
		}

		return swrlBuiltInArguments;
	}

	/**
	 * The {@link SWRLBuiltInArgument} interface is the SWRLAPI extension point to the OWLAPI to represent arguments to
	 * SWRL built-in atoms. In the OWL Specification only data values or variables referencing them are allowed as
	 * parameters to built-in atoms. The SWRLAPI named OWL entities (classes, named individuals, properties, and
	 * datatypes) can also be passed to built-ins.
	 * <p>
	 * 
	 * @see SWRLBuiltInArgument, SWRLLiteralArgument, SWRLDArgument, SWRLVariable
	 */
	private SWRLBuiltInArgument convertSWRLDArgument2SWRLBuiltInArgument(SWRLDArgument swrlDArgument)
	{
		if (swrlDArgument instanceof SWRLLiteralArgument) {
			SWRLLiteralArgument swrlLiteralArgument = (SWRLLiteralArgument)swrlDArgument;
			SWRLBuiltInArgument swrlBuiltInArgument = convertSWRLLiteralArgument2SWRLBuiltInArgument(swrlLiteralArgument);
			return swrlBuiltInArgument;
		} else if (swrlDArgument instanceof SWRLVariable) {
			SWRLVariable swrlVariable = (SWRLVariable)swrlDArgument;
			return convertSWRLVariable2SWRLBuiltInArgument(swrlVariable);
		} else
			throw new RuntimeException("Unknown " + SWRLDArgument.class.getName() + " class "
					+ swrlDArgument.getClass().getName());
	}

	private boolean hackIRI(IRI iri)
	{
		String prefixedName = prefixManager.getPrefixIRI(iri);

		return !prefixedName.equals(":name");
	}

	/**
	 * The OWLAPI follows the OWL Specification and does not explicitly allow named OWL entities as arguments to
	 * built-ins. However, if OWLAPI parsers encounter OWL entities as parameters they appear to represent them as SWRL
	 * variables - with the variable IRI set to the IRI of the entity ({@link OWLEntity} classes represent named OWL
	 * concepts so have an IRI). So if we are processing built-in parameters and encounter variables with an IRI referring
	 * to named OWL entities in the active ontology we can transform them to the appropriate SWRLAPI built-in argument for
	 * the named entity.
	 * <p>
	 * Note: An important restriction here is that variable names do not intersect with named entities in their OWL
	 * ontology.
	 * 
	 * @see SWRLNamedBuiltInArgument
	 */
	private SWRLBuiltInArgument convertSWRLVariable2SWRLBuiltInArgument(SWRLVariable swrlVariable)
	{
		IRI iri = swrlVariable.getIRI();

		if (isOWLClass(iri)) {
			OWLClass cls = getOWLDataFactory().getOWLClass(iri);

			return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(cls);
		} else if (getOWLOntology().containsIndividualInSignature(iri, true) && hackIRI(iri)) {
			OWLNamedIndividual individual = getOWLDataFactory().getOWLNamedIndividual(iri);

			return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(individual);
		} else if (getOWLOntology().containsObjectPropertyInSignature(iri, true)) {
			OWLObjectProperty property = getOWLDataFactory().getOWLObjectProperty(iri);

			return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(property);
		} else if (getOWLOntology().containsDataPropertyInSignature(iri, true)) {
			OWLDataProperty property = getOWLDataFactory().getOWLDataProperty(iri);

			return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(property);
		} else if (getOWLOntology().containsAnnotationPropertyInSignature(iri, true)) {
			OWLAnnotationProperty property = getOWLDataFactory().getOWLAnnotationProperty(iri);

			return getSWRLBuiltInArgumentFactory().getAnnotationPropertyBuiltInArgument(property);
		} else if (getOWLOntology().containsDatatypeInSignature(iri, true)) {
			OWLDatatype datatype = getOWLDataFactory().getOWLDatatype(iri);

			return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(datatype);
		} else {
			IRI variableIRI = swrlVariable.getIRI();
			SWRLVariableBuiltInArgument argument = getSWRLBuiltInArgumentFactory().getVariableBuiltInArgument(variableIRI);
			getOWLIRIResolver().recordSWRLVariable(swrlVariable);

			return argument;
		}
	}

	private boolean isOWLClass(IRI iri)
	{ // TODO This is likely not robust. Also see similar method in DefaultSWRLBridge
		return getOWLOntology().containsClassInSignature(iri, true) || iri.equals(OWLRDFVocabulary.OWL_THING.getIRI())
				|| iri.equals(OWLRDFVocabulary.OWL_NOTHING.getIRI());
	}

	/**
	 * The OWLAPI permits only variable and literal arguments to built-ins, which conforms with the SWRL Specification.
	 * The SWRLAPI also permits OWL classes, individuals, properties, and datatypes as arguments. In order to support
	 * these additional argument types in a standards-conformant way, the SWRLAPI treats URI literal arguments specially.
	 * It a URI literal argument is passed to a built-in we determine if it refers to an OWL named object in the active
	 * ontology and if so we create specific SWRLAPI built-in argument types for it.
	 * <p>
	 * The SWRLAPI allows SQWRL collection built-in arguments (represented by a
	 * {@link SQWRLCollectionVariableBuiltInArgument}) and multi-value variables (represented by a
	 * {@link SWRLMultiValueVariableBuiltInArgument}). These two argument types are not instantiated directly as built-in
	 * argument types. They are created at runtime during rule execution and passed as result values in SWRL variable
	 * built-in arguments.
	 * 
	 * @see SWRLLiteralBuiltInArgument, SWRLClassBuiltInArgument, SWRLIndividualBuiltInArgument,
	 *      SWRLObjectPropertyBuiltInArgument, SWRLDataPropertyBuiltInArgument, SWRLAnnotationPropertyBuiltInArgument,
	 *      SWRLDatatypeBuiltInArgument, SQWRLCollectionBuiltInArgument, SWRLMultiValueBuiltInArgument
	 */
	private SWRLBuiltInArgument convertSWRLLiteralArgument2SWRLBuiltInArgument(SWRLLiteralArgument swrlLiteralArgument)
	{
		OWLLiteral literal = swrlLiteralArgument.getLiteral();
		OWLDatatype literalDatatype = literal.getDatatype();

		if (isURI(literalDatatype)) { // TODO This URI-based approach may not be relevant
			IRI iri = IRI.create(literal.getLiteral());
			if (isOWLClass(iri)) {
				OWLClass cls = getOWLDataFactory().getOWLClass(iri);
				return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(cls);
			} else if (getOWLOntology().containsIndividualInSignature(iri)) {
				OWLNamedIndividual individual = getOWLDataFactory().getOWLNamedIndividual(iri);
				return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(individual);
			} else if (getOWLOntology().containsObjectPropertyInSignature(iri)) {
				OWLObjectProperty property = getOWLDataFactory().getOWLObjectProperty(iri);
				return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(property);
			} else if (getOWLOntology().containsDataPropertyInSignature(iri)) {
				OWLDataProperty property = getOWLDataFactory().getOWLDataProperty(iri);
				return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(property);
			} else if (getOWLOntology().containsAnnotationPropertyInSignature(iri)) {
				OWLAnnotationProperty property = getOWLDataFactory().getOWLAnnotationProperty(iri);
				return getSWRLBuiltInArgumentFactory().getAnnotationPropertyBuiltInArgument(property);
			} else if (getOWLOntology().containsDatatypeInSignature(iri)) {
				OWLDatatype datatype = getOWLDataFactory().getOWLDatatype(iri);
				return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(datatype);
			} else {
				return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
			}
		} else {
			SWRLLiteralBuiltInArgument argument = getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
			return argument;
		}
	}

	private boolean isSWRLBuiltInAtom(SWRLAtom atom) // TODO
	{
		if (atom instanceof SWRLBuiltInAtom)
			return true;
		else {
			SWRLPredicate predicate = atom.getPredicate();
			if (predicate instanceof IRI) {
				IRI iri = (IRI)predicate;
				return isSWRLBuiltIn(iri);
			}
			return false;
		}
	}

	private boolean isURI(OWLDatatype datatype)
	{
		return datatype.getIRI().equals(OWL2Datatype.XSD_ANY_URI.getIRI());
	}

	private SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
	{
		return getSWRLAPIOWLDataFactory().getSWRLBuiltInArgumentFactory();
	}
}
