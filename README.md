SWRLAPI
=======

The SWRLAPI is a Java API for working with the [OWL](http://en.wikipedia.org/wiki/Web_Ontology_Language)-based [SWRL](http://www.w3.org/Submission/SWRL/) rule language. 
It includes graphical tools for editing and executing rules. A SWRL-based OWL query language called [SQWRL](https://github.com/protegeproject/swrlapi/wiki/SQWRL) is also provided.

See the [SWRLAPI Wiki](https://github.com/protegeproject/swrlapi/wiki) for documentation.

A [Protégé-based](http://protege.stanford.edu/) [SWRLTab Plugin](https://github.com/protegeproject/swrltab-plugin), built using this API, is also available. 

#### Building Prerequisites

To build this library you must have the following items installed:

+ A tool for checking out a [Git](http://git-scm.com/) repository.
+ Apache's [Maven](http://maven.apache.org/index.html).
+ [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

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
