SWRLAPI
=======

[![Build Status](https://travis-ci.org/protegeproject/swrlapi.svg?branch=master)](https://travis-ci.org/protegeproject/swrlapi)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/edu.stanford.swrl/swrlapi/badge.svg)](https://maven-badges.herokuapp.com/maven-central/edu.stanford.swrl/swrlapi)

The SWRLAPI is a Java API for working with the [OWL](http://en.wikipedia.org/wiki/Web_Ontology_Language)-based [SWRL](http://www.w3.org/Submission/SWRL/) rule and [SQWRL](https://github.com/protegeproject/swrlapi/wiki/SQWRL) query languages. 
It includes graphical tools for editing and executing rules and queries.

See the [SWRLAPI Wiki](https://github.com/protegeproject/swrlapi/wiki) for documentation.

A standalone [SWRLTab](https://github.com/protegeproject/swrltab) application and a [Protégé-based](http://protege.stanford.edu/) 
[SWRLTab Plugin](https://github.com/protegeproject/swrltab-plugin), both built using this API, are also available. 

#### Getting Started

The following examples can be used to quickly get started with the API. A sample SWRLAPI-based project can also be found [here](https://github.com/protegeproject/swrlapi-example).

The library's dependency information can be found here:

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/edu.stanford.swrl/swrlapi/badge.svg)](https://maven-badges.herokuapp.com/maven-central/edu.stanford.swrl/swrlapi)

If you'd like to be able to execute SWRL rules or SQWRL queries you will need a SWRLAPI-based rule engine implementation. Currently, a [Drools-based SWRL rule engine implementation](https://github.com/protegeproject/swrlapi-drools-engine) is provided. This implementation is also hosted on Maven Central. Its dependency information can be found here:

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/edu.stanford.swrl/swrlapi-drools-engine/badge.svg)](https://maven-badges.herokuapp.com/maven-central/edu.stanford.swrl/swrlapi-drools-engine)

The SWRLAPI uses the [OWLAPI](https://github.com/owlcs/owlapi) to manage OWL ontologies.
The following example illustrates how the library can be used to create a SWRL query engine using an ontology 
created by the OWLAPI and then execute rules in that ontology.

```java
 // Create OWLOntology instance using the OWLAPI
 OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
 OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("/ont/Ont1.owl"));

 // Create a SWRL rule engine using the SWRLAPI
 SWRLRuleEngine swrlRuleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);

 // Run the SWRL rules in the ontology
 swrlRuleEngine.infer();
```

This example shows how the API can be used to create a SQWRL query engine, create and execute a SQWRL query using
this engine, and then process the results.

```java
 // Create OWLOntology instance using the OWLAPI
 OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
 OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("/ont/Ont1.owl"));

 // Create SQWRL query engine using the SWRLAPI
 SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

 // Create and execute a SQWRL query using the SWRLAPI
 SQWRLResult result = queryEngine.runSQWRLQuery("q1","swrlb:add(?x, 2, 2) -> sqwrl:select(?x)");

 // Process the SQWRL result
 if (result.next()) 
   System.out.println("Name: " + result.getLiteral("x").getInteger());
```

Extensive documentation on the SWRLAPI can be found on the [SWRLAPI Wiki](https://github.com/protegeproject/swrlapi/wiki).

#### Building from Source

To build this library you must have the following items installed:

+ [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
+ A tool for checking out a [Git](http://git-scm.com/) repository
+ Apache's [Maven](http://maven.apache.org/index.html)

Get a copy of the latest code:

    git clone https://github.com/protegeproject/swrlapi.git 

Change into the swrlapi directory:

    cd swrlapi

Then build it with Maven:

    mvn clean install

On build completion your local Maven repository will contain the generated swrlapi-${version}.jar file.

This JAR is used by the [Protégé](http://protege.stanford.edu/) [SWRLTab Plugin](https://github.com/protegeproject/swrltab-plugin)
and by the standalone [SWRLTab](https://github.com/protegeproject/swrltab) tool.

A [Build Project](https://github.com/protegeproject/swrlapi-project) is provided to build core SWRLAPI-related components.
A project containing a [library of integration tests](https://github.com/protegeproject/swrlapi-integration-tests) is also provided.

#### License

This software is licensed under the [BSD 2-clause License](https://github.com/protegeproject/swrlapi/blob/master/license.txt).

#### Questions

If you have questions about this library, please go to the main
Protégé website and subscribe to the [Protégé Developer Support
mailing list](http://protege.stanford.edu/support.php#mailingListSupport).
After subscribing, send messages to protege-dev at lists.stanford.edu.
