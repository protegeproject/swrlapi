package org.swrlapi.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.swrlapi.exceptions.SWRLRuleEngineException;
import org.swrlapi.ext.SWRLAPIOWLOntology;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.sqwrl.values.SQWRLNamedResultValue;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

public class SWRLAPIRegressionTester
{
	private final SQWRLQueryEngine queryEngine;

	public SWRLAPIRegressionTester(SWRLAPIOWLOntology swrlapiOWLOntology, SQWRLQueryEngine queryEngine)
	{
		this.queryEngine = queryEngine;
	}

	public void run()
	{
		Set<String> failedTests = new HashSet<String>();
		int numberOfTests = 0;
		int passedTests = 0;

		try {
			queryEngine.getOWL2RLEngine().enableAll();
			queryEngine.reset();
			// queryEngine.getOWL2RLEngine().disableAll();
			// queryEngine.getOWL2RLEngine().enableTables(OWL2RLNames.Table.Table5);

			for (String queryName : queryEngine.getSQWRLQueryNames()) {
				System.out.print("Running test " + queryName + "...");
				numberOfTests++;
				try {
					SQWRLResult result = queryEngine.runSQWRLQuery(queryName);
					if (result.isEmpty()) {
						System.out.println("FAILED - no result returned!");
						failedTests.add(queryName);
					} else {
						Object rdfsComment = getRDFSCommentFromSQWRLQuery();
						if (rdfsComment != null) {
							if (compare(result, rdfsComment.toString())) {
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
		} catch (SWRLRuleEngineException e) {
			System.out.println("Internal error running tests: " + e.getMessage());
		} catch (SQWRLException e) {
			System.out.println("Internal error running tests: " + e.getMessage());
		}
	}

	private String getRDFSCommentFromSQWRLQuery()
	{
		return "TODO";
	}

	private boolean compare(SQWRLResult result, String resultString) throws SQWRLException
	{
		StringTokenizer resultTokenizer = new StringTokenizer(resultString, "\n");

		if (result.getNumberOfRows() != resultTokenizer.countTokens()) {
			System.out.print("Number of rows unequal");
			return false;
		}

		while (result.hasNext()) {
			List<SQWRLResultValue> row = result.getRow();
			String rowString = resultTokenizer.nextToken();
			StringTokenizer rowTokenizer = new StringTokenizer(rowString, ",");
			if (row.size() != rowTokenizer.countTokens()) {
				System.out.print("Number of columns unequal");
				return false;
			}
			for (SQWRLResultValue resultValue : row) {
				String testValueString = rowTokenizer.nextToken().trim();
				if (resultValue instanceof SQWRLNamedResultValue) {
					SQWRLNamedResultValue namedResultValue = (SQWRLNamedResultValue)resultValue;
					if (!namedResultValue.getPrefixedName().equals(testValueString)) {
						System.out
								.print("Named objects unequal - " + namedResultValue.getPrefixedName() + " != " + testValueString);
						return false;
					}
				} else if (resultValue instanceof SQWRLLiteralResultValue) {
					SQWRLLiteralResultValue literalResultValue = (SQWRLLiteralResultValue)resultValue;
					String actualRawLiteral = literalResultValue.getLiteral();
					@SuppressWarnings("unused")
					OWLDatatype datatype = literalResultValue.getDatatype();
					String actualDatatypeShortName = "XXX"; // TODO
					String testRawLiteral = testValueString.substring(1, testValueString.indexOf("^^") - 1);
					String testDatatypeShortName = testValueString.substring(testValueString.indexOf("^^") + 2);

					if (!actualRawLiteral.equals(testRawLiteral)) {
						System.out.print("Literal values unequal - " + actualRawLiteral + " != " + testRawLiteral);
						return false;
					}

					if (!actualDatatypeShortName.equals(testDatatypeShortName)) {
						System.out.print("Types unequal - " + actualDatatypeShortName + " != " + testDatatypeShortName);
						return false;
					}
				}
			}
			result.next();
		}
		return true;
	}
}
