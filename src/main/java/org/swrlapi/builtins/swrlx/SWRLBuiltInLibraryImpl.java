package org.swrlapi.builtins.swrlx;

import java.util.HashMap;
import java.util.List;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.exceptions.BuiltInException;

/**
 * Implementations library for SWRL Extensions built-in methods. See <a
 * href="http://protege.cim3.net/cgi-bin/wiki.pl?SWRLExtensionsBuiltIns">here</a> for documentation on this library.
 * 
 * See <a href="http://protege.cim3.net/cgi-bin/wiki.pl?SWRLBuiltInBridge">here</a> for documentation on defining SWRL
 * built-in libraries.
 */
public class SWRLBuiltInLibraryImpl extends AbstractSWRLBuiltInLibrary
{
	private static final String SWRLXLibraryName = "SWRLExtensionsBuiltIns";
	private final HashMap<String, OWLClass> classInvocationMap;
	private final HashMap<String, OWLNamedIndividual> individualInvocationMap;

	public SWRLBuiltInLibraryImpl()
	{
		super(SWRLXLibraryName);
		this.classInvocationMap = new HashMap<String, OWLClass>();
		this.individualInvocationMap = new HashMap<String, OWLNamedIndividual>();
	}

	@Override
	public void reset()
	{
		this.classInvocationMap.clear();
		this.individualInvocationMap.clear();
	}

	/**
	 * For every pattern of second and subsequent arguments, create an OWL anonymous class and bind it to the first
	 * argument. If the first argument is already bound when the built-in is called, this method returns true.
	 */
	public boolean makeOWLClass(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkNumberOfArgumentsAtLeast(2, arguments.size());

		if (isUnboundArgument(0, arguments)) {
			String createInvocationPattern = createInvocationPattern(getBuiltInBridge(), getInvokingRuleName(),
					getInvokingBuiltInIndex(), getIsInConsequent(), arguments.subList(1, arguments.size()));
			OWLClass cls;

			if (this.classInvocationMap.containsKey(createInvocationPattern)) {
				cls = this.classInvocationMap.get(createInvocationPattern);
			} else {
				cls = getOWLDataFactory().getOWLClass();
				OWLDeclarationAxiom declarationAxiom = getOWLDataFactory().getOWLClassDeclarationAxiom(cls);
				getBuiltInBridge().getOWLNamedObjectResolver().recordOWLClass(cls);
				getBuiltInBridge().injectOWLAxiom(declarationAxiom);
				this.classInvocationMap.put(createInvocationPattern, cls);
			}
			arguments.get(0).setBuiltInResult(createClassBuiltInArgument(cls)); // Bind the result to the first parameter
		}

		return true;
	}

	/**
	 * For every pattern of second and subsequent arguments, create an OWL individual of type OWL:Thing and bind it to the
	 * first argument. If the first argument is already bound when the built-in is called, this method returns true.
	 */
	public boolean makeOWLIndividual(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkNumberOfArgumentsAtLeast(2, arguments.size());

		if (isUnboundArgument(0, arguments)) {
			OWLNamedIndividual individual = null;
			String createInvocationPattern = createInvocationPattern(getBuiltInBridge(), getInvokingRuleName(),
					getInvokingBuiltInIndex(), getIsInConsequent(), arguments.subList(1, arguments.size()));

			if (this.individualInvocationMap.containsKey(createInvocationPattern))
				individual = this.individualInvocationMap.get(createInvocationPattern);
			else {
				individual = getOWLDataFactory().getOWLNamedIndividual();
				OWLDeclarationAxiom declarationAxiom = getOWLDataFactory().getOWLIndividualDeclarationAxiom(individual);
				getBuiltInBridge().getOWLNamedObjectResolver().recordOWLNamedIndividual(individual);
				getBuiltInBridge().injectOWLAxiom(declarationAxiom);
				this.individualInvocationMap.put(createInvocationPattern, individual);
			}
			arguments.get(0).setBuiltInResult(createIndividualBuiltInArgument(individual)); // Bind the result to the first
																																											// parameter
		}

		return true;
	}

	// For backwards compatability
	public boolean makeOWLThing(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		return makeOWLIndividual(arguments);
	}

	// For backwards compatability
	public boolean createOWLThing(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		return makeOWLIndividual(arguments);
	}

	// TODO: check for invocations to swrlx built-ins, which will cause blocking
	public boolean invokeSWRLBuiltIn(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		checkNumberOfArgumentsAtLeast(2, arguments.size());

		String builtInName = getArgumentAsAString(0, arguments);

		List<List<SWRLBuiltInArgument>> argumentPatterns = getBuiltInBridge().invokeSWRLBuiltIn(getInvokingRuleName(),
				builtInName, getInvokingBuiltInIndex(), getIsInConsequent(), arguments.subList(1, arguments.size()));
		return !argumentPatterns.isEmpty();
	}
}
