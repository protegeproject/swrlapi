package org.swrlapi.test;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.io.File;
import java.util.Optional;

public class SQWRLQueryEngineMinimalApp
{
  public static void main(@NonNull String[] args)
  {
    if (args.length > 1)
      Usage();

    Optional<File> owlFile = args.length == 0 ? Optional.empty() : Optional.of(new File(args[0]));

    try {
      OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
      OWLOntology ontology = owlFile.isPresent() ?
          ontologyManager.loadOntologyFromOntologyDocument(owlFile.get()) :
          ontologyManager.createOntology();

      SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

      SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:add(?x, 2, 2) -> sqwrl:select(?x)");

      while (result.next()) {
        System.out.println("Name: " + result.getLiteral("x").getInt());
      }
    } catch (OWLOntologyCreationException e) {
      if (owlFile.isPresent())
        System.err.println("Error creating OWL ontology from file " + owlFile.get().getAbsolutePath() + ": " + (
                e.getMessage() != null ? e.getMessage() : ""));
      else
        System.err.println("Error creating OWL ontology: " + (e.getMessage() != null ? e.getMessage() : ""));
      System.exit(-1);
    } catch (SWRLAPIException e) {
      System.err.println("SWRLAPI error: " + (e.getMessage() != null ? e.getMessage() : ""));
      System.exit(-1);
    } catch (SWRLParseException e) {
      System.err.println("SQWRL parse error: " + (e.getMessage() != null ? e.getMessage() : ""));
      System.exit(-1);
    } catch (SQWRLException e) {
      System.err.println("SQWRL error: " + (e.getMessage() != null ? e.getMessage() : ""));
      System.exit(-1);
    }
  }

  private static void Usage()
  {
    System.err.println("Usage: " + SQWRLQueryEngineMinimalApp.class.getName() + " [ <owlFileName> ]");
    System.exit(1);
  }
}
