SWRLAPI
=======

The SWRLAPI is a Java API for working with the [OWL](http://en.wikipedia.org/wiki/Web_Ontology_Language)-based [SWRL](http://www.w3.org/Submission/SWRL/) rule language. 
It includes graphical tools for editing and executing rules. 
A SWRL-based OWL query language called [SQWRL](https://github.com/protegeproject/swrlapi/wiki/SQWRL) is also provided.

See the [SWRLAPI Wiki](https://github.com/protegeproject/swrlapi/wiki) for documentation.

A standalone [SWRLTab](https://github.com/protegeproject/swrltab) application and a [Protégé-based](http://protege.stanford.edu/) 
[SWRLTab Plugin](https://github.com/protegeproject/swrltab-plugin), both built using this API, are also available. 

#### Building Prerequisites

To build this library you must have the following items installed:

+ [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
+ A tool for checking out a [Git](http://git-scm.com/) repository
+ Apache's [Maven](http://maven.apache.org/index.html)

#### Building

Get a copy of the latest code:

    git clone https://github.com/protegeproject/swrlapi.git 

Change into the swrlapi directory:

    cd swrlapi

Then build it with Maven:

    mvn clean install

On build completion your local Maven repository will contain the generated swrlapi-${version}.jar file.

This JAR is used by the [Protégé](http://protege.stanford.edu/) [SWRLTab Plugin](https://github.com/protegeproject/swrltab-plugin)
and by the standalone [SWRLTab](https://github.com/protegeproject/swrltab) tool.

A [Build Project](https://github.com/protegeproject/swrltab-project) is provided to build core SWRLAPI-related components.

#### Getting Started

The following examples can be used to quickly get started with the API.

This code illustrates how the API can be used to create a SWRL query engine using an ontology 
created by the OWLAPI and then execute rules in that ontology.

```java
 // Create OWLOntology and DefaultPrefixManager instances using the OWLAPI
 File owlFile = new File("/ont/Ont1.owl");
 OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
 OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(owlFile);
 DefaultPrefixManager prefixManager = new DefaultPrefixManager();
 OWLDocumentFormat format = ontology.getOWLOntologyManager().getOntologyFormat(ontology);

 if (format.isPrefixOWLOntologyFormat())
   prefixManager.copyPrefixesFrom(format.asPrefixOWLOntologyFormat().getPrefixName2PrefixMap());

 // Create a SWRL rule engine using the SWRLAPI
 SWRLRuleEngine swrlRuleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology, prefixManager);

 // Run the rule engine
 swrlRuleEngine.infer();
```

This example shows how the API can be used to create a SQWRL query engine, execute a SQWRL query using
this engine, and then process the results.

```java
 // Create OWLOntology and DefaultPrefixManager instances using the OWLAPI
 File owlFile = new File("/ont/Ont1.owl");
 OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
 OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(owlFile);
 DefaultPrefixManager prefixManager = new DefaultPrefixManager();
 OWLDocumentFormat format = ontology.getOWLOntologyManager().getOntologyFormat(ontology);

 if (format.isPrefixOWLOntologyFormat())
   prefixManager.copyPrefixesFrom(format.asPrefixOWLOntologyFormat().getPrefixName2PrefixMap());

 // Create SQWRL query engine using the SWRLAPI
 SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology, prefixManager);

 // Create and execute a SQWRL query using the SWRLAPI
 SQWRLResult result = queryEngine.runSQWRLQuery("q1","swrlb:add(?x, 2, 2) -> sqwrl:select(?x)");

 // Process the SQWRL result
 if (result.next()) 
   System.out.println("Name: " + result.getLiteral("x").getInt());
```

Extensive documentation on the SWRLAPI can be found on the [SWRLAPI Wiki](https://github.com/protegeproject/swrlapi/wiki).
