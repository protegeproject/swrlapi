package org.swrlapi.builtins.swrlb;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import org.swrlapi.core.xsd.XSDTimeUtil;
import org.swrlapi.exceptions.InvalidSWRLBuiltInArgumentException;
import org.swrlapi.exceptions.InvalidSWRLBuiltInNameException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInNotImplementedException;

/**
 * Implementations library for the core SWRL built-in methods. These built-ins are defined <a
 * href="http://www.daml.org/2004/04/swrl/builtins.html">here</a>.
 * <p>
 * Built-ins for lists and URIs not yet implemented.
 */
public class SWRLBuiltInLibraryImpl extends AbstractSWRLBuiltInLibrary
{
  private static final String SWRLBLibraryName = "SWRLCoreBuiltIns";
  private static final String SWRLBPrefix = "swrlb:";

  private static final String SWRLB_ADD = SWRLBPrefix + "add";
  private static final String SWRLB_SUBTRACT = SWRLBPrefix + "subtract";
  private static final String SWRLB_MULTIPLY = SWRLBPrefix + "multiply";
  private static final String SWRLB_DIVIDE = SWRLBPrefix + "divide";
  private static final String SWRLB_INTEGER_DIVIDE = SWRLBPrefix + "integerDivide";
  private static final String SWRLB_MOD = SWRLBPrefix + "mod";
  private static final String SWRLB_POW = SWRLBPrefix + "pow";
  private static final String SWRLB_UNARY_PLUS = SWRLBPrefix + "unaryPlus";
  private static final String SWRLB_UNARY_MINUS = SWRLBPrefix + "unaryMinus";
  private static final String SWRLB_ABS = SWRLBPrefix + "abs";
  private static final String SWRLB_CEILING = SWRLBPrefix + "ceiling";
  private static final String SWRLB_FLOOR = SWRLBPrefix + "floor";
  private static final String SWRLB_ROUND = SWRLBPrefix + "round";
  private static final String SWRLB_ROUND_HALF_TO_EVEN = SWRLBPrefix + "roundHalfToEven";
  private static final String SWRLB_SIN = SWRLBPrefix + "sin";
  private static final String SWRLB_COS = SWRLBPrefix + "cos";
  private static final String SWRLB_TAN = SWRLBPrefix + "tan";

  public SWRLBuiltInLibraryImpl()
  {
    super(SWRLBLibraryName);
  }

  @Override
  public void reset()
  {
  }

  // Built-ins for comparison, defined in Section 8.1. of http://www.daml.org/2004/04/swrl/builtins.html.

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean greaterThan(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    if (isArgumentAString(0, arguments)) {
      String s1 = getArgumentAsAString(0, arguments);
      if (isArgumentAString(1, arguments)) {
        String s2 = getArgumentAsAString(1, arguments);
        return s1.compareTo(s2) > 0;
      } else
        throw new InvalidSWRLBuiltInArgumentException(1, "expecting string argument for comparison, got "
            + getArgumentAsAString(1, arguments));
    } else if (isArgumentNumeric(0, arguments)) {
      if (isArgumentNumeric(1, arguments))
        return compareTwoNumericArguments(arguments) > 0;
      else
        throw new InvalidSWRLBuiltInArgumentException(1, "expecting numeric argument for comparison, got "
            + getArgumentAsAString(1, arguments));
    } else
      throw new InvalidSWRLBuiltInArgumentException(0, "expecting string or numeric argument for comparison, got "
          + getArgumentAsAString(0, arguments));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean lessThan(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    if (isArgumentAString(0, arguments)) {
      String s1 = getArgumentAsAString(0, arguments);
      if (isArgumentAString(1, arguments)) {
        String s2 = getArgumentAsAString(1, arguments);
        return s1.compareTo(s2) < 0;
      } else
        throw new InvalidSWRLBuiltInArgumentException(1, "expecting string argument for comparison, got "
            + getArgumentAsAString(1, arguments));
    } else if (isArgumentNumeric(0, arguments)) {
      if (isArgumentNumeric(1, arguments))
        return compareTwoNumericArguments(arguments) < 0;
      else
        throw new InvalidSWRLBuiltInArgumentException(1, "expecting numeric argument for comparison, got "
            + getArgumentAsAString(1, arguments));
    } else
      throw new InvalidSWRLBuiltInArgumentException(0, "expecting string or numeric argument for comparison, got "
          + getArgumentAsAString(0, arguments));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean equal(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    if (hasUnboundArguments(arguments))
      throw new InvalidSWRLBuiltInArgumentException(0, "comparison built-ins do not support argument binding");

    if (isArgumentABoolean(0, arguments)) {
      boolean b1 = getArgumentAsABoolean(0, arguments);
      if (isArgumentABoolean(1, arguments)) {
        boolean b2 = getArgumentAsABoolean(1, arguments);
        return b1 == b2;
      } else
        return false;
    } else if (isArgumentAString(0, arguments)) {
      String s1 = getArgumentAsAString(0, arguments);
      if (isArgumentAString(1, arguments)) {
        String s2 = getArgumentAsAString(1, arguments);
        return s1.compareTo(s2) == 0;
      } else
        return false;
    } else if (isArgumentADateTime(0, arguments)) {
      XSDDateTime dt1 = getArgumentAsADateTime(0, arguments);
      if (isArgumentADateTime(1, arguments)) {
        XSDDateTime dt2 = getArgumentAsADateTime(1, arguments);
        return dt1.compareTo(dt2) == 0;
      } else
        return false;
    } else if (isArgumentADate(0, arguments)) {
      XSDDate d1 = getArgumentAsADate(0, arguments);
      if (isArgumentADate(1, arguments)) {
        XSDDate d2 = getArgumentAsADate(1, arguments);
        return d1.compareTo(d2) == 0;
      } else
        return false;
    } else if (isArgumentATime(0, arguments)) {
      XSDTime t1 = getArgumentAsATime(0, arguments);
      if (isArgumentATime(1, arguments)) {
        XSDTime t2 = getArgumentAsATime(1, arguments);
        return t1.compareTo(t2) == 0;
      } else
        return false;
    } else if (isArgumentADuration(0, arguments)) {
      XSDDuration d1 = getArgumentAsADuration(0, arguments);
      if (isArgumentADuration(1, arguments)) {
        XSDDuration d2 = getArgumentAsADuration(1, arguments);
        return d1.compareTo(d2) == 0;
      } else
        return false;
    } else if (isArgumentNumeric(0, arguments)) {
      if (isArgumentNumeric(1, arguments))
        return compareTwoNumericArguments(arguments) == 0;
      else
        throw new InvalidSWRLBuiltInArgumentException(1, "expecting numeric argument for comparison, got "
            + getArgumentAsAString(1, arguments));
    } else
      throw new InvalidSWRLBuiltInArgumentException(0,
          "expecting string, numeric or boolean argument for comparison, got " + getArgumentAsAString(0, arguments));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notEqual(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !equal(arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean lessThanOrEqual(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return equal(arguments) || lessThan(arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean greaterThanOrEqual(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return equal(arguments) || greaterThan(arguments);
  }

  // Math Built-ins, defined in Section 8.2. of http://www.daml.org/2004/04/swrl/builtins.html.

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean add(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsAtLeast(2, arguments.size());

    return mathOperation(SWRLB_ADD, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean subtract(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    return mathOperation(SWRLB_SUBTRACT, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean multiply(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsAtLeast(2, arguments.size());

    return mathOperation(SWRLB_MULTIPLY, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean divide(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    return mathOperation(SWRLB_DIVIDE, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean integerDivide(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    return mathOperation(SWRLB_INTEGER_DIVIDE, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean mod(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    return mathOperation(SWRLB_MOD, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean pow(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    return mathOperation(SWRLB_POW, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean unaryPlus(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    return mathOperation(SWRLB_UNARY_PLUS, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean unaryMinus(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    return mathOperation(SWRLB_UNARY_MINUS, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean abs(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    return mathOperation(SWRLB_ABS, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean ceiling(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    return mathOperation(SWRLB_CEILING, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean floor(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    return mathOperation(SWRLB_FLOOR, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean round(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    return mathOperation(SWRLB_ROUND, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean roundHalfToEven(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    return mathOperation(SWRLB_ROUND_HALF_TO_EVEN, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean sin(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    return mathOperation(SWRLB_SIN, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean cos(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    return mathOperation(SWRLB_COS, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean tan(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    return mathOperation(SWRLB_TAN, arguments);
  }

  // Built-ins for Booleans. cf. Section 8.3 of http://www.daml.org/2004/04/swrl/builtins.html

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean booleanNot(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());
    checkForUnboundNonFirstArguments(arguments);

    if (isUnboundArgument(0, arguments)) {
      if (!areAllArgumentsBooleans(arguments.subList(1, arguments.size())))
        throw new InvalidSWRLBuiltInArgumentException(1, "expecting a Boolean");

      boolean operationResult = !getArgumentAsABoolean(1, arguments);
      SWRLLiteralBuiltInArgument resultArgument = createLiteralBuiltInArgument(operationResult);

      arguments.get(0).asVariable().setBuiltInResult(resultArgument); // Bind the result to the first argument

      return true;
    } else {
      if (!areAllArgumentsBooleans(arguments))
        throw new InvalidSWRLBuiltInArgumentException("expecting all Boolean arguments");

      return !equal(arguments);
    }
  }

  // Built-ins for Strings. See: Section 8.4 of http://www.daml.org/2004/04/swrl/builtins.html

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean stringEqualIgnoreCase(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    String argument1, argument2;

    checkNumberOfArgumentsEqualTo(2, arguments.size());
    checkForUnboundArguments(arguments);

    argument1 = getArgumentAsAString(0, arguments);
    argument2 = getArgumentAsAString(1, arguments);

    return argument1.equalsIgnoreCase(argument2);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean stringConcat(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    String operationResult = "";

    checkNumberOfArgumentsAtLeast(2, arguments.size());

    for (int argumentNumber = 1; argumentNumber < arguments.size(); argumentNumber++) { // Exception thrown if argument
      // is not a literal.
      operationResult = operationResult.concat(getArgumentAsAnOWLLiteral(argumentNumber, arguments).getLiteral());
    }

    return processResultArgument(arguments, 0, operationResult);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean substring(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    String argument2, operationResult;
    int startIndex, length;

    checkNumberOfArgumentsAtLeast(3, arguments.size());
    checkNumberOfArgumentsAtMost(4, arguments.size());

    argument2 = getArgumentAsAString(1, arguments);
    startIndex = getArgumentAsAnInt(2, arguments);

    if (arguments.size() == 4) {
      length = getArgumentAsAnInt(3, arguments);
      operationResult = argument2.substring(startIndex, length);
    } else
      operationResult = argument2.substring(startIndex);

    return processResultArgument(arguments, 0, operationResult);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean stringLength(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    String argument2 = getArgumentAsAString(1, arguments);
    int operationResult = argument2.length();

    return processResultArgument(arguments, 0, operationResult);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean upperCase(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    String argument2 = getArgumentAsAString(1, arguments);
    String operationResult = argument2.toUpperCase();

    return processResultArgument(arguments, 0, operationResult);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean lowerCase(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    String argument2 = getArgumentAsAString(1, arguments);
    String operationResult = argument2.toLowerCase();

    return processResultArgument(arguments, 0, operationResult);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean contains(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    String argument1 = getArgumentAsAString(0, arguments);
    String argument2 = getArgumentAsAString(1, arguments);

    return argument1.lastIndexOf(argument2) != -1;
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean containsIgnoreCase(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    String argument1 = getArgumentAsAString(0, arguments);
    String argument2 = getArgumentAsAString(1, arguments);

    return argument1.toLowerCase().lastIndexOf(argument2.toLowerCase()) != -1;
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean startsWith(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    String argument1 = getArgumentAsAString(0, arguments);
    String argument2 = getArgumentAsAString(1, arguments);

    return argument1.startsWith(argument2);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean endsWith(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    String argument1 = getArgumentAsAString(0, arguments);
    String argument2 = getArgumentAsAString(1, arguments);

    return argument1.endsWith(argument2);
  }

  // TODO This is not correct. See http://www.w3.org/TR/xpath-functions/#func-translate
  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean translate(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(4, arguments.size());

    String argument2 = getArgumentAsAString(1, arguments);
    String argument3 = getArgumentAsAString(2, arguments);
    String argument4 = getArgumentAsAString(3, arguments);
    String operationResult = argument2.replace(argument3, argument4);

    return processResultArgument(arguments, 0, operationResult);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean substringAfter(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    String argument2 = getArgumentAsAString(1, arguments);
    String argument3 = getArgumentAsAString(2, arguments);
    String operationResult = substringAfter(argument2, argument3);

    return processResultArgument(arguments, 0, operationResult);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean substringBefore(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    String argument2 = getArgumentAsAString(1, arguments);
    String argument3 = getArgumentAsAString(2, arguments);
    String operationResult = substringBefore(argument2, argument3);

    return processResultArgument(arguments, 0, operationResult);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean matches(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    String argument1 = getArgumentAsAString(0, arguments);
    String argument2 = getArgumentAsAString(1, arguments);

    try {
      return Pattern.matches(argument2, argument1);
    } catch (PatternSyntaxException e) {
      throw new InvalidSWRLBuiltInArgumentException(1, "invalid regular expression '" + argument2 + "': "
          + e.getMessage(), e);
    }
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean replace(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(4, arguments.size());

    String input = getArgumentAsAString(1, arguments);
    String regex = getArgumentAsAString(2, arguments);
    String replacement = getArgumentAsAString(3, arguments);

    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(input);
    String operationResult = m.replaceAll(replacement);

    return processResultArgument(arguments, 0, operationResult);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean normalizeSpace(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    String input = getArgumentAsAString(1, arguments);

    Pattern p = Pattern.compile("\\s+");
    Matcher m = p.matcher(input);
    String operationResult = m.replaceAll(" ").trim();

    return processResultArgument(arguments, 0, operationResult);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean tokenize(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    if (!isUnboundArgument(0, arguments))
      throw new InvalidSWRLBuiltInArgumentException(0, "unexpected bound argument found");

    checkNumberOfArgumentsEqualTo(3, arguments.size());
    checkForUnboundNonFirstArguments(arguments);

    String inputString = getArgumentAsAString(1, arguments);
    String delimeters = getArgumentAsAString(2, arguments);
    StringTokenizer tokenizer = new StringTokenizer(inputString.trim(), delimeters);

    IRI variableIRI = arguments.get(0).asVariable().getIRI();
    SWRLMultiValueVariableBuiltInArgument multiValueBuiltInArgument = createSWRLMultiValueVariableBuiltInArgument(variableIRI);
    while (tokenizer.hasMoreTokens()) {
      String token = tokenizer.nextToken();
      multiValueBuiltInArgument.addArgument(createLiteralBuiltInArgument(token));
    }

    arguments.get(0).asVariable().setBuiltInResult(multiValueBuiltInArgument);

    return !multiValueBuiltInArgument.hasNoArguments();
  }

  // Built-ins for date, time and duration.

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean yearMonthDuration(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());
    int year = getArgumentAsAnInt(1, arguments);
    int month = getArgumentAsAnInt(2, arguments);
    org.apache.axis.types.Duration duration = new org.apache.axis.types.Duration();

    duration.setYears(year);
    duration.setMonths(month);

    return processResultArgument(arguments, 0, axisDuration2XSDDuration(duration));
  }

  @SuppressWarnings("deprecation")
  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean dayTimeDuration(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(5, arguments.size());
    int days = getArgumentAsAnInt(1, arguments);
    int hours = getArgumentAsAnInt(2, arguments);
    int minutes = getArgumentAsAnInt(3, arguments);
    int seconds = getArgumentAsAnInt(4, arguments);
    org.apache.axis.types.Duration duration = new org.apache.axis.types.Duration();

    duration.setDays(days);
    duration.setHours(hours);
    duration.setMinutes(minutes);
    duration.setSeconds(seconds);

    return processResultArgument(arguments, 0, axisDuration2XSDDuration(duration));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean dateTime(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(8, arguments.size());

    int year = getArgumentAsAnInt(1, arguments);
    int month = getArgumentAsAnInt(2, arguments);
    int days = getArgumentAsAnInt(3, arguments);
    int hours = getArgumentAsAnInt(4, arguments);
    int minutes = getArgumentAsAnInt(5, arguments);
    int seconds = getArgumentAsAnInt(6, arguments);
    String timeZone = getArgumentAsAString(7, arguments);
    Calendar calendar = new GregorianCalendar();

    calendar.set(year, month, days, hours, minutes, seconds);
    calendar.setTimeZone(TimeZone.getTimeZone(timeZone));

    String operationResult = "" + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-"
        + calendar.get(Calendar.DAY_OF_MONTH) + "T" + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE)
        + ":" + calendar.get(Calendar.SECOND) + calendar.getTimeZone().getID();

    return processResultArgument(arguments, 0, dateTimeString2XSDDateTime(operationResult));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean date(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(5, arguments.size());

    int year = getArgumentAsAnInt(1, arguments);
    int month = getArgumentAsAnInt(2, arguments);
    int days = getArgumentAsAnInt(3, arguments);
    String timeZone = getArgumentAsAString(4, arguments);
    Calendar calendar = new GregorianCalendar();

    calendar.set(year, month, days);
    calendar.setTimeZone(TimeZone.getTimeZone(timeZone));

    String operationResult = "" + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-"
        + calendar.get(Calendar.DAY_OF_MONTH) + calendar.getTimeZone().getID();

    return processResultArgument(arguments, 0, dateString2XSDDate(operationResult));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean time(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(5, arguments.size());

    int hours = getArgumentAsAnInt(1, arguments);
    int minutes = getArgumentAsAnInt(2, arguments);
    int seconds = getArgumentAsAnInt(3, arguments);
    String timeZone = getArgumentAsAString(4, arguments);
    String operationResult = "" + hours + ":" + minutes + ":" + seconds + timeZone;

    return processResultArgument(arguments, 0, timeString2XSDTime(operationResult));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean addYearMonthDurations(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsAtLeast(3, arguments.size());

    org.apache.axis.types.Duration operationDuration = new org.apache.axis.types.Duration();

    for (int i = 1; i < arguments.size(); i++) {
      org.apache.axis.types.Duration duration = getArgumentAsAnAxisDuration(i, arguments);
      operationDuration = XSDTimeUtil.addYearMonthDurations(operationDuration, duration);
    }

    return processResultArgument(arguments, 0, axisDuration2XSDDuration(operationDuration));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean subtractYearMonthDurations(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    org.apache.axis.types.Duration duration2 = getArgumentAsAnAxisDuration(1, arguments);
    org.apache.axis.types.Duration duration3 = getArgumentAsAnAxisDuration(2, arguments);
    org.apache.axis.types.Duration operationDuration = XSDTimeUtil.subtractYearMonthDurations(duration2, duration3);

    return processResultArgument(arguments, 0, axisDuration2XSDDuration(operationDuration));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean multiplyYearMonthDuration(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    org.apache.axis.types.Duration duration2 = getArgumentAsAnAxisDuration(1, arguments);
    org.apache.axis.types.Duration duration3 = getArgumentAsAnAxisDuration(2, arguments);
    org.apache.axis.types.Duration operationDuration = XSDTimeUtil.multiplyYearMonthDurations(duration2, duration3);

    return processResultArgument(arguments, 0, axisDuration2XSDDuration(operationDuration));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean divideYearMonthDuration(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    org.apache.axis.types.Duration duration2 = getArgumentAsAnAxisDuration(1, arguments);
    org.apache.axis.types.Duration duration3 = getArgumentAsAnAxisDuration(2, arguments);
    org.apache.axis.types.Duration operationDuration = XSDTimeUtil.divideYearMonthDurations(duration2, duration3);

    return processResultArgument(arguments, 0, axisDuration2XSDDuration(operationDuration));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean addDayTimeDurations(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    org.apache.axis.types.Duration operationDuration = new org.apache.axis.types.Duration();

    checkNumberOfArgumentsAtLeast(3, arguments.size());

    for (int i = 1; i < arguments.size(); i++) {
      org.apache.axis.types.Duration duration = getArgumentAsAnAxisDuration(i, arguments);
      operationDuration = XSDTimeUtil.addDayTimeDurations(operationDuration, duration);
    }

    return processResultArgument(arguments, 0, axisDuration2XSDDuration(operationDuration));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean subtractDayTimeDurations(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    org.apache.axis.types.Duration duration2 = getArgumentAsAnAxisDuration(1, arguments);
    org.apache.axis.types.Duration duration3 = getArgumentAsAnAxisDuration(2, arguments);
    org.apache.axis.types.Duration operationDuration = XSDTimeUtil.subtractDayTimeDurations(duration2, duration3);

    return processResultArgument(arguments, 0, axisDuration2XSDDuration(operationDuration));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean multiplyDayTimeDuration(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    org.apache.axis.types.Duration duration2 = getArgumentAsAnAxisDuration(1, arguments);
    org.apache.axis.types.Duration duration3 = getArgumentAsAnAxisDuration(2, arguments);
    org.apache.axis.types.Duration operationDuration = XSDTimeUtil.multiplyDayTimeDurations(duration2, duration3);

    return processResultArgument(arguments, 0, axisDuration2XSDDuration(operationDuration));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean divideDayTimeDuration(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    org.apache.axis.types.Duration duration2 = getArgumentAsAnAxisDuration(1, arguments);
    org.apache.axis.types.Duration duration3 = getArgumentAsAnAxisDuration(2, arguments);
    org.apache.axis.types.Duration operationDuration = XSDTimeUtil.divideDayTimeDurations(duration2, duration3);

    return processResultArgument(arguments, 0, axisDuration2XSDDuration(operationDuration));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean subtractDates(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    java.util.Date date2 = getXSDDateArgumentAsAUtilDate(1, arguments);
    java.util.Date date3 = getXSDDateArgumentAsAUtilDate(2, arguments);
    org.apache.axis.types.Duration operationDuration = XSDTimeUtil.subtractDates(date2, date3);

    return processResultArgument(arguments, 0, axisDuration2XSDDuration(operationDuration));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean subtractTimes(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    org.apache.axis.types.Time time2 = getArgumentAsAnAxisTime(1, arguments);
    org.apache.axis.types.Time time3 = getArgumentAsAnAxisTime(2, arguments);
    org.apache.axis.types.Duration operationDuration = XSDTimeUtil.subtractTimes(time2, time3);

    return processResultArgument(arguments, 0, axisDuration2XSDDuration(operationDuration));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean addYearMonthDurationToDateTime(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    java.util.Date date = getXSDDateTimeArgumentAsAUtilDate(1, arguments);
    org.apache.axis.types.Duration duration = getArgumentAsAnAxisDuration(2, arguments);
    java.util.Date operationUtilDate = XSDTimeUtil.addYearMonthDurationToUtilDate(date, duration);

    return processResultArgument(arguments, 0, utilDate2XSDDateTime(operationUtilDate));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean subtractYearMonthDurationFromDateTime(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    java.util.Date date = getXSDDateTimeArgumentAsAUtilDate(1, arguments);
    org.apache.axis.types.Duration duration = getArgumentAsAnAxisDuration(2, arguments);
    java.util.Date operationUtilDate = XSDTimeUtil.subtractYearMonthDurationFromUtilDate(date, duration);

    return processResultArgument(arguments, 0, utilDate2XSDDateTime(operationUtilDate));
  }

  public boolean /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  addDayTimeDurationToDateTime(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    java.util.Date date = getXSDDateTimeArgumentAsAUtilDate(1, arguments);
    org.apache.axis.types.Duration duration = getArgumentAsAnAxisDuration(2, arguments);
    java.util.Date operationUtilDate = XSDTimeUtil.addDayTimeDurationToUtilDate(date, duration);

    return processResultArgument(arguments, 0, utilDate2XSDDateTime(operationUtilDate));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean subtractDayTimeDurationFromDateTime(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    java.util.Date date = getXSDDateTimeArgumentAsAUtilDate(1, arguments);
    org.apache.axis.types.Duration duration = getArgumentAsAnAxisDuration(2, arguments);
    java.util.Date operationUtilDate = XSDTimeUtil.subtractDayTimeDurationFromUtilDate(date, duration);

    return processResultArgument(arguments, 0, utilDate2XSDDateTime(operationUtilDate));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean addYearMonthDurationToDate(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    java.util.Date date = getXSDDateArgumentAsAUtilDate(1, arguments);
    org.apache.axis.types.Duration duration = getArgumentAsAnAxisDuration(2, arguments);
    java.util.Date operationUtilDate = XSDTimeUtil.addYearMonthDurationToUtilDate(date, duration);

    return processResultArgument(arguments, 0, utilDate2XSDDate(operationUtilDate));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean subtractYearMonthDurationFromDate(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    java.util.Date date = getXSDDateArgumentAsAUtilDate(1, arguments);
    org.apache.axis.types.Duration duration = getArgumentAsAnAxisDuration(2, arguments);
    java.util.Date operationUtilDate = XSDTimeUtil.subtractYearMonthDurationFromUtilDate(date, duration);

    return processResultArgument(arguments, 0, utilDate2XSDDate(operationUtilDate));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean addDayTimeDurationToDate(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    java.util.Date date = getXSDDateArgumentAsAUtilDate(1, arguments);
    org.apache.axis.types.Duration duration = getArgumentAsAnAxisDuration(2, arguments);
    java.util.Date operationUtilDate = XSDTimeUtil.addDayTimeDurationToUtilDate(date, duration);

    return processResultArgument(arguments, 0, utilDate2XSDDate(operationUtilDate));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean subtractDayTimeDurationFromDate(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    java.util.Date date = getXSDDateArgumentAsAUtilDate(1, arguments);
    org.apache.axis.types.Duration duration = getArgumentAsAnAxisDuration(2, arguments);
    java.util.Date operationUtilDate = XSDTimeUtil.subtractDayTimeDurationFromUtilDate(date, duration);

    return processResultArgument(arguments, 0, utilDate2XSDDate(operationUtilDate));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean addDayTimeDurationToTime(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    org.apache.axis.types.Time time = getArgumentAsAnAxisTime(1, arguments);
    org.apache.axis.types.Duration duration = getArgumentAsAnAxisDuration(2, arguments);
    org.apache.axis.types.Time operationTime = XSDTimeUtil.addDayTimeDurationToTime(time, duration);

    return processResultArgument(arguments, 0, axisTime2XSDTime(operationTime));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean subtractDayTimeDurationFromTime(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    org.apache.axis.types.Time time = getArgumentAsAnAxisTime(1, arguments);
    org.apache.axis.types.Duration duration = getArgumentAsAnAxisDuration(2, arguments);
    org.apache.axis.types.Time operationTime = XSDTimeUtil.subtractDayTimeDurationFromTime(time, duration);

    return processResultArgument(arguments, 0, axisTime2XSDTime(operationTime));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean subtractDateTimesYieldingYearMonthDuration(List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    java.util.Date date1 = getXSDDateTimeArgumentAsAUtilDate(1, arguments);
    java.util.Date date2 = getXSDDateTimeArgumentAsAUtilDate(2, arguments);
    org.apache.axis.types.Duration operationDuration = XSDTimeUtil.subtractUtilDatesYieldingYearMonthDuration(date1,
        date2);

    return processResultArgument(arguments, 0, axisDuration2XSDDuration(operationDuration));
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean subtractDateTimesYieldingDayTimeDuration(List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    java.util.Date date1 = getXSDDateTimeArgumentAsAUtilDate(1, arguments);
    java.util.Date date2 = getXSDDateTimeArgumentAsAUtilDate(2, arguments);
    org.apache.axis.types.Duration operationDuration = XSDTimeUtil.subtractUtilDatesYieldingDayTimeDuration(date1,
        date2);

    return processResultArgument(arguments, 0, axisDuration2XSDDuration(operationDuration));
  }

  // Built-ins for URIs

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean resolveURI(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    throw new SWRLBuiltInNotImplementedException();
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean anyURI(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    throw new SWRLBuiltInNotImplementedException();
  }

  // Built-ins for Lists

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean listConcat(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    throw new SWRLBuiltInNotImplementedException();
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean listIntersection(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    throw new SWRLBuiltInNotImplementedException();
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean listSubtraction(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    throw new SWRLBuiltInNotImplementedException();
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean member(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    throw new SWRLBuiltInNotImplementedException();
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean length(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    throw new SWRLBuiltInNotImplementedException();
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean first(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    throw new SWRLBuiltInNotImplementedException();
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean rest(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    throw new SWRLBuiltInNotImplementedException();
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean sublist(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    throw new SWRLBuiltInNotImplementedException();
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean empty(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    throw new SWRLBuiltInNotImplementedException();
  }

  // Private methods

  private int compareTwoNumericArguments(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    int result = 0;

    checkThatAllArgumentsAreNumeric(arguments);

    if (isWidestNumericArgumentAShort(arguments)) {
      short s1 = getArgumentAsAShort(0, arguments);
      short s2 = getArgumentAsAShort(1, arguments);
      if (s1 < s2)
        result = -1;
      else if (s1 > s2)
        result = 1;
      else
        result = 0;
    } else if (isWidestNumericArgumentAnInt(arguments)) {
      int i1 = getArgumentAsAnInt(0, arguments);
      int i2 = getArgumentAsAnInt(1, arguments);
      if (i1 < i2)
        result = -1;
      else if (i1 > i2)
        result = 1;
      else
        result = 0;
    } else if (isWidestNumericArgumentALong(arguments)) {
      long l1 = getArgumentAsALong(0, arguments);
      long l2 = getArgumentAsALong(1, arguments);
      if (l1 < l2)
        result = -1;
      else if (l1 > l2)
        result = 1;
      else
        result = 0;
    } else if (isWidestNumericArgumentAFloat(arguments)) {
      float f1 = getArgumentAsAFloat(0, arguments);
      float f2 = getArgumentAsAFloat(1, arguments);
      if (f1 < f2)
        result = -1;
      else if (f1 > f2)
        result = 1;
      else
        result = 0;
    } else {
      double d1 = getArgumentAsADouble(0, arguments);
      double d2 = getArgumentAsADouble(1, arguments);
      if (d1 < d2)
        result = -1;
      else if (d1 > d2)
        result = 1;
      else
        result = 0;
    }

    return result;
  }

  private boolean mathOperation(String builtInName, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    double argument1 = 0.0, argument2, argument3, operationResult = 0.0;
    boolean hasUnbound1stArgument = false;

    checkForUnboundNonFirstArguments(arguments); // Only supports binding of first argument

    if (isUnboundArgument(0, arguments))
      hasUnbound1stArgument = true;

    // Argument number checking will have been performed by invoking method.
    if (!hasUnbound1stArgument)
      argument1 = getArgumentAsADouble(0, arguments);
    argument2 = getArgumentAsADouble(1, arguments);

    if (builtInName.equalsIgnoreCase(SWRLB_ADD)) {
      operationResult = 0.0;
      for (int argumentNumber = 1; argumentNumber < arguments.size(); argumentNumber++) {
        operationResult += getArgumentAsADouble(argumentNumber, arguments);
      }
    } else if (builtInName.equalsIgnoreCase(SWRLB_MULTIPLY)) {
      operationResult = 1.0;
      for (int argumentNumber = 1; argumentNumber < arguments.size(); argumentNumber++) {
        operationResult *= getArgumentAsADouble(argumentNumber, arguments);
      }
    } else if (builtInName.equalsIgnoreCase(SWRLB_SUBTRACT)) {
      argument3 = getArgumentAsADouble(2, arguments);
      operationResult = argument2 - argument3;
    } else if (builtInName.equalsIgnoreCase(SWRLB_DIVIDE)) {
      argument3 = getArgumentAsADouble(2, arguments);
      operationResult = (argument2 / argument3);
    } else if (builtInName.equalsIgnoreCase(SWRLB_INTEGER_DIVIDE)) {
      argument3 = getArgumentAsADouble(2, arguments);
      if (argument3 == 0)
        throw new InvalidSWRLBuiltInArgumentException(2, "zero passed as divisor");
      if (argument3 >= 0)
        operationResult = argument2 + argument3 + 1 / argument3;
      else
        operationResult = argument2 / argument3;
    } else if (builtInName.equalsIgnoreCase(SWRLB_MOD)) {
      argument3 = getArgumentAsADouble(2, arguments);
      operationResult = argument2 % argument3;
    } else if (builtInName.equalsIgnoreCase(SWRLB_POW)) {
      argument3 = getArgumentAsADouble(2, arguments);
      operationResult = java.lang.Math.pow(argument2, argument3);
    } else if (builtInName.equalsIgnoreCase(SWRLB_UNARY_PLUS))
      operationResult = argument2;
    else if (builtInName.equalsIgnoreCase(SWRLB_UNARY_MINUS))
      operationResult = -argument2;
    else if (builtInName.equalsIgnoreCase(SWRLB_ABS))
      operationResult = java.lang.Math.abs(argument2);
    else if (builtInName.equalsIgnoreCase(SWRLB_CEILING))
      operationResult = java.lang.Math.ceil(argument2);
    else if (builtInName.equalsIgnoreCase(SWRLB_FLOOR))
      operationResult = java.lang.Math.floor(argument2);
    else if (builtInName.equalsIgnoreCase(SWRLB_ROUND))
      operationResult = java.lang.Math.rint(argument2);
    else if (builtInName.equalsIgnoreCase(SWRLB_ROUND_HALF_TO_EVEN))
      operationResult = java.lang.Math.rint(argument2);
    else if (builtInName.equalsIgnoreCase(SWRLB_SIN))
      operationResult = java.lang.Math.sin(argument2);
    else if (builtInName.equalsIgnoreCase(SWRLB_COS))
      operationResult = java.lang.Math.cos(argument2);
    else if (builtInName.equalsIgnoreCase(SWRLB_TAN))
      operationResult = java.lang.Math.tan(argument2);
    else
      throw new InvalidSWRLBuiltInNameException(builtInName);

    if (hasUnbound1stArgument) { // Bind the result to the first argument.
      List<SWRLBuiltInArgument> boundInputArguments = arguments.subList(1, arguments.size());

      if (builtInName.equalsIgnoreCase(SWRLB_SIN) || builtInName.equalsIgnoreCase(SWRLB_COS)
          || builtInName.equalsIgnoreCase(SWRLB_TAN))
        arguments.get(0).asVariable().setBuiltInResult(createLiteralBuiltInArgument(operationResult));
      else {
        SWRLBuiltInArgument resultArgument = createLeastNarrowNumericLiteralBuiltInArgument(operationResult,
            boundInputArguments);
        arguments.get(0).asVariable().setBuiltInResult(resultArgument);
      }
      return true;
    } else
      return (argument1 == operationResult);
  }

  private org.apache.axis.types.Duration getArgumentAsAnAxisDuration(int argumentNumber,
      List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    String duration = "";

    try {
      duration = getArgumentAsAString(argumentNumber, arguments);
      return XSDTimeUtil.xsdDurationString2AxisDuration(duration);
    } catch (IllegalArgumentException e) {
      throw new SWRLBuiltInException("invalid xsd:duration " + duration + ": " + e.getMessage(), e);
    }
  }

  private java.util.Date getXSDDateArgumentAsAUtilDate(int argumentNumber, List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    String date = "";

    try {
      date = getArgumentAsAString(argumentNumber, arguments);
      return XSDTimeUtil.xsdDateString2Date(date);
    } catch (IllegalArgumentException e) {
      throw new SWRLBuiltInException("invalid xsd:date " + date + ": " + e.getMessage(), e);
    }
  }

  private java.util.Date getXSDDateTimeArgumentAsAUtilDate(int argumentNumber, List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    String date = "";

    try {
      date = getArgumentAsAString(argumentNumber, arguments);
      return XSDTimeUtil.xsdDateTimeString2Date(date);
    } catch (IllegalArgumentException e) {
      throw new SWRLBuiltInException("invalid xsd:dateTime " + date + ": " + e.getMessage(), e);
    }
  }

  private org.apache.axis.types.Time getArgumentAsAnAxisTime(int argumentNumber, List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    String time = getArgumentAsAString(argumentNumber, arguments);

    try {
      return XSDTimeUtil.xsdTimeString2AxisTime(time);
    } catch (NumberFormatException e) {
      throw new SWRLBuiltInException("invalid xsd:time " + time + ": " + e.getMessage(), e);
    }
  }

  private XSDDate utilDate2XSDDate(java.util.Date utilDate) throws SWRLBuiltInException
  {
    return new XSDDate(XSDTimeUtil.utilDate2XSDDateString(utilDate));
  }

  private XSDDate dateString2XSDDate(String date) throws SWRLBuiltInException
  {
    return new XSDDate(date);
  }

  private XSDDateTime utilDate2XSDDateTime(java.util.Date utilDate) throws SWRLBuiltInException
  {
    return new XSDDateTime(XSDTimeUtil.utilDate2XSDDateTimeString(utilDate));
  }

  private XSDDateTime dateTimeString2XSDDateTime(String dateTime) throws SWRLBuiltInException
  {
    return new XSDDateTime(dateTime);
  }

  private XSDDuration axisDuration2XSDDuration(org.apache.axis.types.Duration duration) throws SWRLBuiltInException
  {
    return new XSDDuration(duration.toString());
  }

  private XSDTime axisTime2XSDTime(org.apache.axis.types.Time time) throws SWRLBuiltInException
  {
    return new XSDTime(time.toString());
  }

  private XSDTime timeString2XSDTime(String time) throws SWRLBuiltInException
  {
    return new XSDTime(time);
  }

  private String substringAfter(String str, String separator)
  {
    if (str == null || str.length() == 0) {
      return str;
    }
    if (separator == null) {
      return "";
    }
    int pos = str.indexOf(separator);
    if (pos == -1) {
      return "";
    }
    return str.substring(pos + separator.length());
  }

  private String substringBefore(String str, String separator)
  {
    if (str == null || str.length() == 0 || separator == null) {
      return str;
    }
    if (separator.length() == 0) {
      return "";
    }
    int pos = str.indexOf(separator);
    if (pos == -1) {
      return str;
    }
    return str.substring(0, pos);
  }

}
