package org.swrlapi.builtins.swrlm;

import java.util.List;

import org.nfunk.jep.JEP;
import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.exceptions.BuiltInException;

/**
 * Implementations library for SWRL mathematical built-ins.
 */
public class SWRLBuiltInLibraryImpl extends AbstractSWRLBuiltInLibrary
{
	private static final String SWRLMLibraryName = "SWRLAPIMathematicalBuiltIns";

	private JEP jep = null;

	public SWRLBuiltInLibraryImpl()
	{
		super(SWRLMLibraryName);
	}

	@Override
	public void reset()
	{
		this.jep = null;
	}

	/**
	 * Returns true if the first argument is equal to the square root of the second argument. If the first argument is
	 * unbound, bind it to the square root of the second argument.
	 */
	public boolean sqrt(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		double argument1, argument2;

		checkNumberOfArgumentsAtLeast(2, arguments.size());

		argument2 = getArgumentAsADouble(1, arguments);

		if (isUnboundArgument(0, arguments)) {
			arguments.get(0).asVariable().setBuiltInResult(createLiteralBuiltInArgument(java.lang.Math.sqrt(argument2)));
			return true;
		} else {
			argument1 = getArgumentAsADouble(0, arguments);
			return argument1 == java.lang.Math.sqrt(argument2);
		}
	}

	/**
	 * Returns true if the first argument is equal to the natural logarithm (base e) of the second argument. If the first
	 * argument is unbound, bind it to the natural logarithm of the second argument.
	 */
	public boolean log(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		double argument1, argument2;

		checkNumberOfArgumentsAtLeast(2, arguments.size());

		argument2 = getArgumentAsADouble(1, arguments);

		if (isUnboundArgument(0, arguments)) {
			arguments.get(0).asVariable().setBuiltInResult(createLiteralBuiltInArgument(java.lang.Math.log(argument2)));
			return true;
		} else {
			argument1 = getArgumentAsADouble(0, arguments);
			return argument1 == java.lang.Math.log(argument2);
		}
	}

	/**
	 * Returns true if the first argument is equals to the mathematical expression specified in the second argument, which
	 * may use the values specified by the variables in the optional subsequent arguments. If the first argument is
	 * unbound, bind it to the result of the expression.
	 */
	public boolean eval(List<SWRLBuiltInArgument> arguments) throws BuiltInException
	{
		double value;
		String expression;

		checkNumberOfArgumentsAtLeast(2, arguments.size());

		expression = getArgumentAsAString(1, arguments);

		if (arguments.size() > 2) {
			List<SWRLBuiltInArgument> variableArguments = arguments.subList(2, arguments.size());

			checkForUnboundArguments(variableArguments, "2nd and subequent arguments cannot be unbound");
			checkThatArgumentsWereBoundVariables(variableArguments, "2nd and subequent arguments should be variables");

			for (SWRLBuiltInArgument argument : variableArguments) {
				String variableName = argument.getBoundVariableName(); // We have checked that they are all variables
				double variableValue = getArgumentAsADouble(argument);
				getJEP().addVariable(variableName, variableValue);
			}
		}

		getJEP().parseExpression(expression);

		if (getJEP().hasError())
			throw new BuiltInException("exception parsing expression '" + expression + "': " + getJEP().getErrorInfo());

		value = getJEP().getValue();

		if (getJEP().hasError())
			throw new BuiltInException("exception parsing expression '" + expression + "': " + getJEP().getErrorInfo());

		if (isUnboundArgument(0, arguments)) {
			arguments.get(0).asVariable().setBuiltInResult(createLiteralBuiltInArgument(value));
			return true;
		} else {
			return value == getArgumentAsADouble(0, arguments);
		}
	}

	// See. http://www.singularsys.com/jep/doc/javadoc/org/nfunk/jep/JEP.html for JEP API
	private JEP getJEP()
	{
		if (this.jep == null) {
			this.jep = new JEP();

			this.jep.addStandardFunctions();
			this.jep.addStandardConstants();
			this.jep.setImplicitMul(true);
		}
		return this.jep;
	}
}
