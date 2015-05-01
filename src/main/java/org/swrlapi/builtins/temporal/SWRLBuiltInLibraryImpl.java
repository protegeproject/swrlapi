package org.swrlapi.builtins.temporal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.swrlapi.builtins.SWRLBuiltInBridge;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.exceptions.InvalidSWRLBuiltInArgumentException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInLibraryException;

/**
 * Implementation library for SWRL temporal built-ins.
 */
public class SWRLBuiltInLibraryImpl extends AbstractSWRLBuiltInLibrary
{
  public static final String TemporalLibraryName = "SWRLTemporalBuiltIns";

  public static final String Prefix = "temporal:";
  public static final String Namespace = "http://swrl.stanford.edu/ontologies/built-ins/3.3/temporal.owl#";

  private static final String TemporalEquals = Prefix + "equals";
  private static final String TemporalAfter = Prefix + "after";
  private static final String TemporalBefore = Prefix + "before";
  private static final String TemporalMeets = Prefix + "meets";
  private static final String TemporalMetBy = Prefix + "metBy";
  private static final String TemporalOverlaps = Prefix + "overlaps";
  private static final String TemporalOverlappedBy = Prefix + "overlappedBy";
  private static final String TemporalContains = Prefix + "contains";
  private static final String TemporalDuring = Prefix + "during";
  private static final String TemporalStarts = Prefix + "starts";
  private static final String TemporalStartedBy = Prefix + "startedBy";
  private static final String TemporalFinishes = Prefix + "finishes";
  private static final String TemporalFinishedBy = Prefix + "finishedBy";
  private static final String TemporalIntersects = Prefix + "intersects";

  private static final String ExtendedPropositionClassName = Namespace + "ExtendedProposition";
  private static final String ValidInstantClassName = Namespace + "ValidInstant";
  private static final String ValidPeriodClassName = Namespace + "ValidPeriod";
  private static final String GranularityClassName = Namespace + "Granularity";
  private static final String HasValidTimePropertyName = Namespace + "hasValidTime";
  private static final String HasTimePropertyName = Namespace + "hasTime";
  private static final String HasStartTimePropertyName = Namespace + "hasStartTime";
  private static final String HasFinishTimePropertyName = Namespace + "hasFinishTime";

  private Temporal temporal;

  public SWRLBuiltInLibraryImpl()
  {
    super(TemporalLibraryName);
  }

  @Override
  public void reset() throws SWRLBuiltInLibraryException
  {
    XSDDateTimeStringProcessor d = new XSDDateTimeStringProcessor();

    try {
      this.temporal = new Temporal(d);
      this.temporal.setNow();
    } catch (TemporalException e) {
      throw new SWRLBuiltInLibraryException("error initializing temporal library: " + e.getMessage(), e);
    }
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean equals(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return temporalOperation(TemporalEquals, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean before(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return temporalOperation(TemporalBefore, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean after(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return temporalOperation(TemporalAfter, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean meets(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return temporalOperation(TemporalMeets, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean metBy(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return temporalOperation(TemporalMetBy, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean overlaps(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return temporalOperation(TemporalOverlaps, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean overlappedBy(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return temporalOperation(TemporalOverlappedBy, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean contains(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return temporalOperation(TemporalContains, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean during(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return temporalOperation(TemporalDuring, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean starts(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return temporalOperation(TemporalStarts, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean startedBy(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return temporalOperation(TemporalStartedBy, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean finishes(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return temporalOperation(TemporalFinishes, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean finishedBy(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return temporalOperation(TemporalFinishedBy, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean intersects(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return temporalOperation(TemporalIntersects, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notIntersects(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !temporalOperation(TemporalIntersects, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notEquals(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !temporalOperation(TemporalEquals, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notBefore(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !temporalOperation(TemporalBefore, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notAfter(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !temporalOperation(TemporalAfter, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notMeets(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !temporalOperation(TemporalMeets, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notMetBy(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !temporalOperation(TemporalMetBy, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notOverlaps(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !temporalOperation(TemporalOverlaps, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notOverlappedBy(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !temporalOperation(TemporalOverlappedBy, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notContains(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !temporalOperation(TemporalContains, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notDuring(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !temporalOperation(TemporalDuring, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notStarts(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !temporalOperation(TemporalStarts, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notStartedBy(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !temporalOperation(TemporalStartedBy, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notFinishes(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !temporalOperation(TemporalFinishes, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notFinishedBy(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !temporalOperation(TemporalFinishedBy, arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notDurationLessThan(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !durationLessThan(arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notDurationLessThanOrEqualTo(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !durationLessThanOrEqualTo(arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notDurationEqualTo(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !durationEqualTo(arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notDurationGreaterThan(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !durationGreaterThan(arguments);
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean notDurationGreaterThanOrEqualTo(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    return !durationGreaterThanOrEqualTo(arguments);
  }

  /**
   * Accepts either three or four arguments. Returns true if the first duration argument is equal to the difference
   * between two timestamps at the granularity specified by the final argument. The timestamps are specified as either a
   * mixture of two ValidInstant or datetime arguments or in single ValidPeriod argument. If the duration argument is
   * unbound, it is assigned to the time difference between the two timestamps.
   *
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean duration(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    long operationResult;

    checkNumberOfArgumentsInRange(3, 4, arguments.size());
    checkForUnboundNonFirstArguments(arguments);

    try {
      int numberOfArguments = arguments.size();

      if (numberOfArguments == 3) {
        int granularity = getBuiltInArgumentAsAGranularity(2, arguments);
        Period period = getArgumentAsAPeriod(1, arguments, granularity);
        operationResult = period.duration(granularity);
      } else { // 4 arguments
        int granularity = getBuiltInArgumentAsAGranularity(3, arguments);
        Instant i1 = getArgumentAsAnInstant(1, arguments, granularity);
        Instant i2 = getArgumentAsAnInstant(2, arguments, granularity);
        operationResult = i1.duration(i2, granularity);
      }
    } catch (TemporalException e) {
      throw new SWRLBuiltInException(e.getMessage(), e);
    }

    if (isUnboundArgument(0, arguments)) {
      SWRLLiteralBuiltInArgument resultArgument = createLiteralBuiltInArgument(operationResult);
      arguments.get(0).asVariable().setBuiltInResult(resultArgument); // Bind the result to the first argument
      return true;
    } else {
      long argument1 = getArgumentAsALong(0, arguments);
      return (argument1 == operationResult);
    }
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean durationLessThan(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsInRange(3, 4, arguments.size());
    checkForUnboundArguments(arguments);
    List<SWRLBuiltInArgument> newArguments = cloneArguments(arguments);

    long argument1 = getArgumentAsALong(0, arguments);

    if (newArguments.get(0).isVariable())
      newArguments.get(0).asVariable().setUnbound();

    duration(newArguments);
    long operationResult = getArgumentAsALong(0, newArguments);

    return argument1 < operationResult;
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean durationLessThanOrEqualTo(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsInRange(3, 4, arguments.size());
    checkForUnboundArguments(arguments);
    List<SWRLBuiltInArgument> newArguments = cloneArguments(arguments);

    long argument1 = getArgumentAsALong(0, arguments);

    if (newArguments.get(0).isVariable())
      newArguments.get(0).asVariable().setUnbound();

    duration(newArguments);
    long operationResult = getArgumentAsALong(0, newArguments);

    return argument1 <= operationResult;
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean durationEqualTo(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsInRange(3, 4, arguments.size());
    checkForUnboundArguments(arguments);

    List<SWRLBuiltInArgument> newArguments = cloneArguments(arguments);

    long argument1 = getArgumentAsALong(0, arguments);

    if (newArguments.get(0).isVariable())
      newArguments.get(0).asVariable().setUnbound();

    duration(newArguments);
    long operationResult = getArgumentAsALong(0, newArguments);

    return argument1 == operationResult;
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean durationGreaterThan(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsInRange(3, 4, arguments.size());
    checkForUnboundArguments(arguments);

    List<SWRLBuiltInArgument> newArguments = cloneArguments(arguments);

    long argument1 = getArgumentAsALong(0, arguments);

    if (newArguments.get(0).isVariable())
      newArguments.get(0).asVariable().setUnbound();

    duration(newArguments);
    long operationResult = getArgumentAsALong(0, newArguments);

    return argument1 > operationResult;
  }

  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean durationGreaterThanOrEqualTo(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsInRange(3, 4, arguments.size());
    checkForUnboundArguments(arguments);
    List<SWRLBuiltInArgument> newArguments = cloneArguments(arguments);

    long argument1 = getArgumentAsALong(0, arguments);

    if (newArguments.get(0).isVariable())
      newArguments.get(0).asVariable().setUnbound();

    duration(newArguments);
    long operationResult = getArgumentAsALong(0, newArguments);

    return argument1 >= operationResult;
  }

  /**
   * Returns true if the first timestamp argument is equal to the second timestamps argument plus the third count
   * argument at the granularity specified by the fourth argument. The timestamps are specified as either a
   * ValidInstant, or xsd:dateTime arguments. Arguments of type xsd:string are automatically converted to xsd:dateTime
   * arguments. If the first argument is unbound, it is assigned the result of the addition.
   *
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean add(List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(4, arguments.size());
    checkForUnboundNonFirstArguments(arguments);

    try {
      long granuleCount = getArgumentAsALong(2, arguments);
      int granularity = getBuiltInArgumentAsAGranularity(3, arguments);
      Instant operationResult = getArgumentAsAnInstant(1, arguments, Temporal.FINEST);

      operationResult.addGranuleCount(granuleCount, granularity);

      if (isUnboundArgument(0, arguments)) {
        SWRLLiteralBuiltInArgument resultArgument = createLiteralBuiltInArgument(new XSDDateTime(
            operationResult.toString()));
        arguments.get(0).asVariable().setBuiltInResult(resultArgument); // Bind the result to the first parameter.

        return true;
      } else {
        Instant argument1 = getArgumentAsAnInstant(0, arguments, granularity);
        return (argument1.equals(operationResult, Temporal.FINEST));
      }
    } catch (TemporalException e) {
      throw new SWRLBuiltInException(e.getMessage(), e);
    }
  }

  private boolean temporalOperation(String operation, List<SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsInRange(2, 4, arguments.size());
    checkForUnboundArguments(arguments);

    try {
      int numberOfArguments = arguments.size();
      boolean hasGranularityArgument = isBuiltInArgumentAGranularity(numberOfArguments - 1, arguments);
      boolean has2nd3rdInstantArguments = hasGranularityArgument ? (numberOfArguments > 3) : (numberOfArguments > 2);
      int granularity = hasGranularityArgument ? getBuiltInArgumentAsAGranularity(numberOfArguments - 1, arguments)
          : Temporal.FINEST;
      Period p1 = getArgumentAsAPeriod(0, arguments, granularity);
      Period p2 = has2nd3rdInstantArguments ? getTwoInstantArgumentsAsAPeriod(1, 2, arguments, granularity)
          : getArgumentAsAPeriod(1, arguments, granularity);

      if (operation.equals(TemporalEquals))
        return p1.equals(p2, granularity);
      else if (operation.equals(TemporalBefore))
        return p1.before(p2, granularity);
      else if (operation.equals(TemporalAfter))
        return p1.after(p2, granularity);
      else if (operation.equals(TemporalMeets))
        return p1.meets(p2, granularity);
      else if (operation.equals(TemporalMetBy))
        return p1.met_by(p2, granularity);
      else if (operation.equals(TemporalOverlaps))
        return p1.overlaps(p2, granularity);
      else if (operation.equals(TemporalOverlappedBy))
        return p1.overlapped_by(p2, granularity);
      else if (operation.equals(TemporalContains))
        return p1.contains(p2, granularity);
      else if (operation.equals(TemporalDuring))
        return p1.during(p2, granularity);
      else if (operation.equals(TemporalStarts))
        return p1.starts(p2, granularity);
      else if (operation.equals(TemporalStartedBy))
        return p1.started_by(p2, granularity);
      else if (operation.equals(TemporalFinishes))
        return p1.finishes(p2, granularity);
      else if (operation.equals(TemporalFinishedBy))
        return p1.finished_by(p2, granularity);
      else if (operation.equals(TemporalIntersects))
        return p1.intersects(p2, granularity);
      else
        throw new SWRLBuiltInException("internal error - unknown temporal operator " + operation);
    } catch (TemporalException e) {
      throw new SWRLBuiltInException(e.getMessage(), e);
    }
  }

  private Period getTwoInstantArgumentsAsAPeriod(int firstArgumentNumber, int secondArgumentNumber,
      List<SWRLBuiltInArgument> arguments, int granularity) throws SWRLBuiltInException, TemporalException
  {
    if (firstArgumentNumber >= arguments.size())
      throw new InvalidSWRLBuiltInArgumentException(firstArgumentNumber, "out of range");
    if (secondArgumentNumber >= arguments.size())
      throw new InvalidSWRLBuiltInArgumentException(secondArgumentNumber, "out of range");

    Instant i1 = getArgumentAsAnInstant(firstArgumentNumber, arguments, granularity);
    Instant i2 = getArgumentAsAnInstant(secondArgumentNumber, arguments, granularity);

    return new Period(this.temporal, i1, i2, granularity);
  }

  private Period getArgumentAsAPeriod(int argumentNumber, List<SWRLBuiltInArgument> arguments, int granularity)
      throws SWRLBuiltInException, TemporalException
  {
    if (isArgumentALiteral(argumentNumber, arguments)) {
      String datetimeString;
      if (isArgumentAString(argumentNumber, arguments)) {
        datetimeString = getArgumentAsAString(argumentNumber, arguments);
        // rewriteStringArgumentAsADateOrDateTime(argumentNumber, arguments);
      } else if (isArgumentADate(argumentNumber, arguments)) {
        datetimeString = getArgumentAsADate(argumentNumber, arguments).getContent();
      } else if (isArgumentADateTime(argumentNumber, arguments)) {
        datetimeString = getArgumentAsADateTime(argumentNumber, arguments).getContent();
      } else
        throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
            "expecting xsd:string, xsd:date, or xsd:dateTime got " + arguments.get(argumentNumber));
      return new Period(this.temporal, datetimeString, datetimeString, granularity);
    } else if (isArgumentAnIndividual(argumentNumber, arguments)) {
      IRI individualIRI = getArgumentAsAnIndividualIRI(argumentNumber, arguments);
      if (isOWLIndividualOfType(individualIRI, createIRI(ValidInstantClassName))) {
        Instant instant = validInstantIndividual2Instant(individualIRI, granularity);
        return new Period(this.temporal, instant, granularity);
      } else if (isOWLIndividualOfType(individualIRI, createIRI(ValidPeriodClassName))) {
        return validPeriodIndividual2Period(individualIRI, granularity);
      } else if (isOWLIndividualOfType(individualIRI, createIRI(ExtendedPropositionClassName))) {
        return extendedPropositionIndividual2Period(individualIRI, granularity);
      } else
        throw new InvalidSWRLBuiltInArgumentException(argumentNumber, "individual " + individualIRI + " is not a "
            + ValidInstantClassName + " or " + ValidPeriodClassName + " or " + ExtendedPropositionClassName);
    } else
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber, "expecting xsd:date or xsd:datetime or "
          + ValidInstantClassName + " or " + ValidPeriodClassName + " or " + ExtendedPropositionClassName
          + " individual, got " + arguments.get(argumentNumber));
  }

  private Instant getArgumentAsAnInstant(int argumentNumber, List<SWRLBuiltInArgument> arguments, int granularity)
      throws SWRLBuiltInException, TemporalException
  {
    if (isArgumentALiteral(argumentNumber, arguments)) {
      String datetimeString;
      if (isArgumentAString(argumentNumber, arguments)) {
        datetimeString = getArgumentAsAString(argumentNumber, arguments);
        // rewriteStringArgumentAsADateOrDateTime(argumentNumber, arguments);
      } else if (isArgumentADate(argumentNumber, arguments)) {
        datetimeString = getArgumentAsADate(argumentNumber, arguments).getContent();
      } else if (isArgumentADateTime(argumentNumber, arguments)) {
        datetimeString = getArgumentAsADateTime(argumentNumber, arguments).getContent();
      } else
        throw new InvalidSWRLBuiltInArgumentException(argumentNumber,
            "expecting xsd:string, xsd:date, or xsd:dateTime got " + arguments.get(argumentNumber));
      return new Instant(this.temporal, datetimeString, granularity);
    } else if (isArgumentAnIndividual(argumentNumber, arguments)) {
      IRI individualIRI = getArgumentAsAnIndividualIRI(argumentNumber, arguments);
      if (isOWLIndividualOfType(individualIRI, createIRI(ValidInstantClassName))) {
        return validInstantIndividual2Instant(individualIRI, granularity);
      } else if (isOWLIndividualOfType(individualIRI, createIRI(ExtendedPropositionClassName))) {
        return extendedPropositionIndividual2Instant(individualIRI, granularity);
      } else
        throw new InvalidSWRLBuiltInArgumentException(argumentNumber, "individual " + individualIRI + " is not a "
            + ValidInstantClassName + "or an " + ExtendedPropositionClassName);
    } else
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber, "expecting xsd:date or xsd:datetime or "
          + ValidInstantClassName + " or " + ExtendedPropositionClassName + " individual, got "
          + arguments.get(argumentNumber));
  }

  private int getBuiltInArgumentAsAGranularity(int argumentNumber, List<SWRLBuiltInArgument> arguments)
      throws TemporalException, SWRLBuiltInException
  {
    if (isArgumentALiteral(argumentNumber, arguments)) {
      String granularityName = getArgumentAsAString(argumentNumber, arguments);
      return Temporal.getIntegerGranularityRepresentation(granularityName);
    } else if (isArgumentAnIndividual(argumentNumber, arguments)) {
      SWRLNamedIndividualBuiltInArgument individualArgument = getArgumentAsAnIndividual(argumentNumber, arguments);
      IRI individualIRI = individualArgument.getIRI();
      String fullName = individualIRI.toString();
      String granularityName;
      if (isOWLIndividualOfType(individualIRI, createIRI(GranularityClassName))) {
        int hashIndex = fullName.indexOf('#');
        if (hashIndex == -1)
          granularityName = fullName;
        else
          granularityName = fullName.substring(hashIndex + 1, fullName.length());
        return Temporal.getIntegerGranularityRepresentation(granularityName);
      } else
        throw new InvalidSWRLBuiltInArgumentException(argumentNumber, "individual " + individualIRI + " is not a "
            + GranularityClassName);
    } else
      throw new InvalidSWRLBuiltInArgumentException(argumentNumber, "expecting a " + GranularityClassName
          + " individual" + ", got " + arguments.get(argumentNumber));
  }

  private boolean isBuiltInArgumentAGranularity(int argumentNumber, List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    if (isArgumentAString(argumentNumber, arguments)) {
      String granularityName = getArgumentAsAString(argumentNumber, arguments);
      return Temporal.isValidGranularityString(granularityName);
    } else if (isArgumentAnIndividual(argumentNumber, arguments)) {
      IRI individualIRI = getArgumentAsAnIndividualIRI(argumentNumber, arguments);
      return isOWLIndividualOfType(individualIRI, createIRI(GranularityClassName));
    } else
      return false;
  }

  private Instant validInstantIndividual2Instant(IRI individualIRI, int granularity) throws SWRLBuiltInException,
  TemporalException
  {
    String datetimeString = getDataPropertyValueAsAString(getBuiltInBridge(), individualIRI,
        createIRI(HasTimePropertyName));

    return new Instant(this.temporal, datetimeString, granularity);
  }

  private Period validPeriodIndividual2Period(IRI individualIRI, int granularity) throws SWRLBuiltInException,
  TemporalException
  {
    String startDatetimeString = getDataPropertyValueAsAString(getBuiltInBridge(), individualIRI,
        createIRI(HasStartTimePropertyName));
    String finishDatetimeString = getDataPropertyValueAsAString(getBuiltInBridge(), individualIRI,
        createIRI(HasFinishTimePropertyName));

    return new Period(this.temporal, startDatetimeString, finishDatetimeString, granularity);
  }

  private Period extendedPropositionIndividual2Period(IRI extendedPropositionIRI, int granularity)
      throws SWRLBuiltInException, TemporalException
  {
    IRI validTimeIRI = getObjectPropertyValueAsIRI(getBuiltInBridge(), extendedPropositionIRI,
        createIRI(HasValidTimePropertyName));

    if (isOWLIndividualOfType(validTimeIRI, createIRI(ValidPeriodClassName)))
      return validPeriodIndividual2Period(validTimeIRI, granularity);
    else
      throw new SWRLBuiltInException("expecting valid period value for extended proposition " + extendedPropositionIRI);
  }

  private Instant extendedPropositionIndividual2Instant(IRI extendedPropositionIRI, int granularity)
      throws SWRLBuiltInException, TemporalException
  {
    IRI validTimeIRI = getObjectPropertyValueAsIRI(getBuiltInBridge(), extendedPropositionIRI,
        createIRI(HasValidTimePropertyName));

    if (isOWLIndividualOfType(validTimeIRI, createIRI(ValidInstantClassName)))
      return validInstantIndividual2Instant(validTimeIRI, granularity);
    else
      throw new SWRLBuiltInException("expecting valid instant value for extended proposition " + extendedPropositionIRI);
  }

  private IRI getObjectPropertyValueAsIRI(SWRLBuiltInBridge bridge, IRI individualIRI, IRI propertyIRI)
  {
    Set<OWLObjectPropertyAssertionAxiom> axioms = new HashSet<>(); // TODO Get assertions for this individual/property
    OWLObjectPropertyAssertionAxiom axiom = axioms.toArray(new OWLObjectPropertyAssertionAxiom[0])[0];
    OWLIndividual subject = axiom.getObject();

    return subject.asOWLNamedIndividual().getIRI();
  }

  private boolean isOWLIndividualOfType(IRI individualIRI, IRI classIRI) throws SWRLBuiltInLibraryException
  {
    return true; // TODO Implement isOWLIndividualOfType
  }

  private String getDataPropertyValueAsAString(SWRLBuiltInBridge bridge, IRI individualIRI, IRI propertyIRI)
  {
    Set<OWLDataPropertyAssertionAxiom> axioms = new HashSet<>(); // TODO Get assertions for this individual/property
    OWLDataPropertyAssertionAxiom axiom = axioms.toArray(new OWLDataPropertyAssertionAxiom[0])[0];
    OWLLiteral value = axiom.getObject();

    return value.toString();
  }

  /*
   * For convenience, we allow users to supply xsd:date or xsd:dateTimes to built-ins as strings. We automatically
   * rewrite those arguments to xsd:date or xsd:dateTimes. We must make sure that the generated date or dateTime <p> We
   * would like to rewrite but the XSD types require full time specifications - which may not match the partial
   * specifications supplied.
   */
  @SuppressWarnings("unused")
  private void rewriteStringArgumentAsADateOrDateTime(int argumentNumber, List<SWRLBuiltInArgument> arguments)
      throws SWRLBuiltInException
  {
    String datetimeString = getArgumentAsAString(argumentNumber, arguments);
    XSDDateTimeStringProcessor datetimeProcessor = new XSDDateTimeStringProcessor();

    try {
      if (datetimeProcessor.getFinestSpecifiedGranularity(datetimeString) < Temporal.HOURS) {
        // If no finer than hours, assume it is xsd:date
        XSDDate date = new XSDDate(datetimeProcessor.stripDatetimeString(datetimeString, Temporal.DAYS));
        arguments.set(argumentNumber, createLiteralBuiltInArgument(date));
      } else { // xsd:dateTime
        XSDDateTime dateTime = new XSDDateTime(datetimeProcessor.normalizeDatetimeString(datetimeString));
        arguments.set(argumentNumber, createLiteralBuiltInArgument(dateTime));
      }
    } catch (TemporalException e) {
      throw new SWRLBuiltInException("invalid xsd:date or xsd:dateTime string " + datetimeString);
    }
  }
}
