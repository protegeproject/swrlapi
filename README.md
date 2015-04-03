SWRLAPI
=======

The SWRLAPI is a development environment for working with the [OWL](http://en.wikipedia.org/wiki/Web_Ontology_Language)-based [SWRL](http://www.w3.org/Submission/SWRL/) rule language. 
It includes graphical tools for editing and executing SWRL rules and provides and array of Java APIs for working with rules. A SWRL-based OWL query language called [SQWRL](https://github.com/protegeproject/swrlapi/wiki/SQWRL) is also provided.

See the [SWRLAPI Wiki](https://github.com/protegeproject/swrlapi/wiki) for documentation.

A [Protégé-based](http://protege.stanford.edu/) [SWRLTab Plugin](https://github.com/protegeproject/swrltab-plugin) is also available. 

#### Building Prerequisites

To build and run this plugin, you must have the following items installed:

+ Apache's [Maven](http://maven.apache.org/index.html).
+ A tool for checking out a [Git](http://git-scm.com/) repository.

#### Building

Get a copy of the latest code:

    git clone https://github.com/protegeproject/swrlapi.git 

Change into the swrlapi directory:

    cd swrlapi

Build it with Maven:

    mvn clean install

On build completion, your local Maven repository will contain the generated swrlapi-${version}.jar file.

