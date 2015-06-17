package org.swrlapi.sqwrl;

import org.swrlapi.sqwrl.values.SQWRLResultValue;

/**
 * This interface defines an API can be used to generate a result structure and populate it with
 * data; it can also be used to retrieve those data from the result.
 * <p>
 * There are three processing phases:
 * <p>
 * (1) Configuration Phase: In this phase the structure of the result is defined. A newly created object will start in
 * this phase. In this phase the columns are defined; aggregation or ordering is also specified in this phase. This
 * phase is closed by a call to the {@link #configured()} method.
 * <p>
 * (2) Preparation Phase: In this phase data are added to the result. This phase is implicitly opened by the call to the
 * {@link #configured()} method. It is closed by a call to the {@link #prepared()} method.
 * <p>
 * A convenience method {@link #addColumns} that takes a list of column names is also supplied.
 * <p>
 * There is also a convenience method {@link #addRow}, which takes a list of {@link SQWRLResultValue} objects. This
 * method automatically does a row open and close. It is expecting the exact same number of list elements as there are
 * columns in the result.
 * <p>
 * The interface {@link org.swrlapi.sqwrl.SQWRLResultGenerator} defines the calls used in these two phases.
 * <p>
 * (3) Processing Phase: In this phase data may be retrieved from the result. This phase is implicitly opened by the
 * call to the {@link #prepared()} method.
 * <p>
 * The interface {@link org.swrlapi.sqwrl.SQWRLResult} defines the calls used in the processing phase.
 * <p>
 * An example configuration, data generation, and result retrieval is:
 * <p>
 * <p>
 * <pre>
 * private static final String TestPrefix = "test:";
 * private static final String TestNamespace = "http://example.org#";
 * private static final IRI i1IRI = IRI.create(TestNamespace + "i1");
 * private static final IRI 21IRI = IRI.create(TestNamespace + "i2");
 *
 * DefaultPrefixManager prefixManager = new DefaultPrefixManager();
 * SQWRLResultManager resultManager = SWRLAPIFactory.createSQWRLResultManager(prefixManager);
 *
 * prefixManager.setPrefix(TestPrefix, TestNamespace);
 *
 * resultManager.addColumn(&quot;name&quot;);
 * resultManager.addAggregateColumn(&quot;average&quot;, SQWRLResultNames.AvgAggregateFunction);
 * resultManager.configured();
 *
 * resultManager.openRow();
 * resultManager.addCell(valueFactory.getIndividualValue(i1IRI));
 * resultManager.addCell(valueFactory.getLiteralValue(27));
 * resultManager.closeRow();
 * resultManager.openRow();
 * resultManager.addCell(valueFactory.getIndividualValue(i2IRI));
 * resultManager.addCell(valueFactory.getLiteralValue(34));
 * resultManager.closeRow();
 * resultManager.openRow();
 * resultManager.addCell(valueFactory.getIndividualValue(i2IRI));
 * resultManager.addCell(valueFactory.getLiteralValue(21));
 * resultManager.closeRow();
 * resultManager.prepared();
 * </pre>
 * <p>
 * The result is now available for reading. The interface {@link org.swrlapi.sqwrl.SQWRLResult} defines the assessor
 * methods. A row consists of a list of objects defined by the interface
 * {@link org.swrlapi.sqwrl.values.SQWRLResultValue}.
 * <p>
 * The possible types of values are (1) {@link org.swrlapi.sqwrl.values.SQWRLLiteralResultValue}, representing literals;
 * (2) {@link org.swrlapi.sqwrl.values.SQWRLIndividualResultValue}, representing OWL individuals; (3)
 * {@link org.swrlapi.sqwrl.values.SQWRLClassResultValue}, representing OWL classes; (4)
 * {@link org.swrlapi.sqwrl.values.SQWRLObjectPropertyResultValue}, representing OWL object properties, (5)
 * {@link org.swrlapi.sqwrl.values.SQWRLDataPropertyResultValue}, representing OWL data properties, and (6)
 * {@link org.swrlapi.sqwrl.values.SQWRLAnnotationPropertyResultValue}, representing OWL annotation properties.
 * <p>
 * The following is an example of dealing with a {@link org.swrlapi.sqwrl.SQWRLResult}:
 * <p>
 * <p>
 * <pre>
 * SQWRLResult result = ...
 *
 * while (result.next()) {
 * 	SQWRLIndividualResultValue nameValue = result.getIndividual(&quot;name&quot;);
 * 	SQWRLLiteralResultValue averageValue = result.createLiteral(&quot;average&quot;);
 * 	System.out.println(&quot;Name: &quot; + nameValue.getPrefixedName());
 * 	System.out.println(&quot;Average: &quot; + averageValue.getInt());
 * }
 * </pre>
 */
public interface SQWRLResultManager extends SQWRLResultGenerator, SQWRLResult
{}
