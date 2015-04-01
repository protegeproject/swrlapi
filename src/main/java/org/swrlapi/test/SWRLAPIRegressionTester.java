package org.swrlapi.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.swrlapi.core.SQWRLQueryRenderer;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.sqwrl.SQWRLQuery;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLEntityResultValue;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

/**
 * Individually execute all SQWRL queries in an ontology and compare the generated result with the expected result
 * stored in the <code>rdfs:comment</code> annotation associated with each query.
 */
public class SWRLAPIRegressionTester
{
	private final SWRLAPIOWLOntology swrlapiOWLOntology;
	private final SQWRLQueryEngine sqwrlQueryEngine;

	public SWRLAPIRegressionTester(SWRLAPIOWLOntology swrlapiOWLOntology, SQWRLQueryEngine sqwrlQueryEngine)
	{
		this.swrlapiOWLOntology = swrlapiOWLOntology;
		this.sqwrlQueryEngine = sqwrlQueryEngine;
	}

	public void run()
	{
		Set<String> failedTests = new HashSet<>();
		int numberOfTests = 0;
		int passedTests = 0;

		SQWRLQueryRenderer renderer = this.swrlapiOWLOntology.createSQWRLQueryRenderer();

		try {
			sqwrlQueryEngine.getOWL2RLEngine().enableAll();
			sqwrlQueryEngine.reset();
			// sqwrlQueryEngine.getOWL2RLEngine().disableAll();
			// sqwrlQueryEngine.getOWL2RLEngine().enableTables(OWL2RLNames.Table.Table5);

			for (SQWRLQuery query : sqwrlQueryEngine.getSQWRLQueries()) {
				String queryName = query.getQueryName();
				System.out.print("\n*****Running test " + queryName + "...\n");
				System.out.print(renderer.renderSQWRLQuery(query) + "\n");
				numberOfTests++;
				try {
					SQWRLResult result = sqwrlQueryEngine.runSQWRLQuery(queryName);
					if (result.isEmpty()) {
						System.out.println("FAILED - no result returned!");
						failedTests.add(queryName);
					} else {
						String comment = query.getComment();
						System.out.println("Result:\n" + result);
						if (comment.length() != 0) {
							if (compare(result, comment)) {
								System.out.println("PASSED");
								passedTests++;
							} else {
								System.out.println(" FAILED");
								failedTests.add(queryName);
							}
						} else {
							System.out.println("FAILED - no rdfs:comment");
							failedTests.add(queryName);
						}
					}
				} catch (SQWRLException e) {
					System.out.println("FAILED with exception: " + e.getMessage());
					failedTests.add(queryName);
				}
			}
			System.out.println("Number of tests: " + numberOfTests);

			if (!failedTests.isEmpty()) {
				System.out.println("Number of failed tests: " + failedTests.size());
				System.out.println("Failed test names: " + failedTests);
			} else
				System.out.println("Passed " + passedTests + " test(s)!");
		} catch (SQWRLException e) {
			System.out.println("SQWRL exception running tests: " + e.getMessage());
		} catch (RuntimeException e) {
			System.out.println("Internal error running tests: " + e.getMessage());
		}
	}

	private boolean compare(SQWRLResult result, String expectedResultString) throws SQWRLException
	{
		StringTokenizer resultTokenizer = new StringTokenizer(expectedResultString, "\n");

		if (result.getNumberOfRows() != resultTokenizer.countTokens()) {
			System.out.print("Number of rows unequal");
			return false;
		}

		while (result.next()) {
			List<SQWRLResultValue> row = result.getRow();
			String rowString = resultTokenizer.nextToken();
			StringTokenizer rowTokenizer = new StringTokenizer(rowString, ",");
			if (row.size() != rowTokenizer.countTokens()) {
				System.out.print("Number of columns unequal");
				return false;
			}
			for (SQWRLResultValue resultValue : row) {
				String testValueString = rowTokenizer.nextToken().trim();
				if (resultValue instanceof SQWRLEntityResultValue) {
					SQWRLEntityResultValue entityResultValue = (SQWRLEntityResultValue)resultValue;
					if (!entityResultValue.getPrefixedName().equals(testValueString)) {
						System.out.print("Named objects unequal - " + entityResultValue.getPrefixedName() + " != "
								+ testValueString);
						return false;
					}
				} else if (resultValue instanceof SQWRLLiteralResultValue) {
					SQWRLLiteralResultValue literalResultValue = (SQWRLLiteralResultValue)resultValue;
					String actualRawLiteral = literalResultValue.getValue();
					@SuppressWarnings("unused")
					OWLDatatype datatype = literalResultValue.getOWLDatatype();
					String actualDatatypePrefixedName = "XXX"; // TODO
					String testRawLiteral = testValueString.substring(1, testValueString.indexOf("^^") - 1);
					String testDatatypePrefixedName = testValueString.substring(testValueString.indexOf("^^") + 2);

					if (!actualRawLiteral.equals(testRawLiteral)) {
						System.out.print("Literal values unequal - " + actualRawLiteral + " != " + testRawLiteral);
						return false;
					}

					if (!actualDatatypePrefixedName.equals(testDatatypePrefixedName)) {
						System.out.print("Types unequal - " + actualDatatypePrefixedName + " != " + testDatatypePrefixedName);
						return false;
					}
				}
			}
		}
		return true;
	}
}
