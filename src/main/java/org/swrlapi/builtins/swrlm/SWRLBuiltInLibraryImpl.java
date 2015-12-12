package org.swrlapi.builtins.swrlm;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import org.nfunk.jep.JEP;
import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.exceptions.SWRLBuiltInException;

import java.util.List;
import java.util.Optional;

/**
 * Implementations library for SWRL mathematical built-ins.
 */
public class SWRLBuiltInLibraryImpl extends AbstractSWRLBuiltInLibrary
{
  private static final String SWRLMLibraryName = "SWRLAPIMathematicalBuiltIns";

  @Nullable private JEP jep = null;

  public SWRLBuiltInLibraryImpl()
  {
    super(SWRLMLibraryName);
  }

  @Override public void reset()
  {
    this.jep = null;
  }

  /**
   * Returns true if the first argument is equal to the square root of the second argument. If the first argument is
   * unbound, bind it to the square root of the second argument.
   *
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean sqrt(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsAtLeast(2, arguments.size());

    double argument2 = getArgumentAsADouble(1, arguments);

    if (isUnboundArgument(0, arguments)) {
      arguments.get(0).asVariable().setBuiltInResult(createLiteralBuiltInArgument(java.lang.Math.sqrt(argument2)));
      return true;
    } else {
      double argument1 = getArgumentAsADouble(0, arguments);
      return argument1 == java.lang.Math.sqrt(argument2);
    }
  }

  /**
   * Returns true if the first argument is equal to the natural logarithm (base e) of the second argument. If the first
   * argument is unbound, bind it to the natural logarithm of the second argument.
   *
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean log(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsAtLeast(2, arguments.size());

    double argument2 = getArgumentAsADouble(1, arguments);

    if (isUnboundArgument(0, arguments)) {
      arguments.get(0).asVariable().setBuiltInResult(createLiteralBuiltInArgument(java.lang.Math.log(argument2)));
      return true;
    } else {
      double argument1 = getArgumentAsADouble(0, arguments);
      return argument1 == java.lang.Math.log(argument2);
    }
  }

  /**
   * Returns true if the first argument is equals to the mathematical expression specified in the second argument, which
   * may use the values specified by the variables in the optional subsequent arguments. If the first argument is
   * unbound, bind it to the result of the expression.
   *
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean eval(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsAtLeast(2, arguments.size());

    String expression = getArgumentAsAString(1, arguments);

    if (arguments.size() > 2) {
      List<@NonNull SWRLBuiltInArgument> variableArguments = arguments.subList(2, arguments.size());

      checkForUnboundArguments(variableArguments, "2nd and subequent arguments cannot be unbound");
      checkThatArgumentsWereBoundVariables(variableArguments, "2nd and subequent arguments should be variables");

      for (SWRLBuiltInArgument argument : variableArguments) {
        Optional<String> variableName = argument.getBoundVariableName(); // We have checked that they are all variables
        if (variableName.isPresent()) {
          double variableValue = getArgumentAsADouble(argument);
          getJEP().addVariable(variableName.get(), variableValue);
        }
      }
    }

    getJEP().parseExpression(expression);

    if (getJEP().hasError())
      throw new SWRLBuiltInException("exception parsing expression '" + expression + "': " + getJEP().getErrorInfo());

    double value = getJEP().getValue();

    if (getJEP().hasError())
      throw new SWRLBuiltInException("exception parsing expression '" + expression + "': " + getJEP().getErrorInfo());

    if (isUnboundArgument(0, arguments)) {
      arguments.get(0).asVariable().setBuiltInResult(createLiteralBuiltInArgument(value));
      return true;
    } else {
      return value == getArgumentAsADouble(0, arguments);
    }
  }

  // See. http://www.singularsys.com/jep/doc/javadoc/org/nfunk/jep/JEP.html for JEP API
  @NonNull private JEP getJEP()
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
