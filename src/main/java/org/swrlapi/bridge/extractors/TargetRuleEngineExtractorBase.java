package org.swrlapi.bridge.extractors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.factory.OWLDatatypeFactory;
import org.swrlapi.factory.OWLLiteralFactory;
import org.swrlapi.factory.SWRLAPIOWLDataFactory;
import org.swrlapi.factory.SWRLBuiltInArgumentFactory;

import java.util.Optional;

/**
 * This interface describes a set of methods that will be needed by a target rule engine to create OWLAPI
 * representations of OWL properties from the rule engine's native rule engine representation of those properties.
 * <p>
 * The corresponding {@link TargetRuleEngineConverterBase} is used when creating native rule engine representations of
 * OWLAPI OWL objects.
 *
 * @see TargetRuleEngineConverterBase
 */
public abstract class TargetRuleEngineExtractorBase implements TargetRuleEngineExtractor
{
  @NonNull private final SWRLRuleEngineBridge bridge;

  protected TargetRuleEngineExtractorBase(@NonNull SWRLRuleEngineBridge bridge)
  {
    this.bridge = bridge;
  }

  @NonNull protected SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
  {
    return getBridge().getSWRLBuiltInArgumentFactory();
  }

  @NonNull protected SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
  {
    return getBridge().getSWRLAPIOWLDataFactory();
  }

  @NonNull protected OWLDataFactory getOWLDataFactory()
  {
    return getBridge().getSWRLAPIOWLDataFactory();
  }

  @NonNull protected OWLDatatypeFactory getOWLDatatypeFactory()
  {
    return getBridge().getOWLDatatypeFactory();
  }

  @NonNull protected OWLLiteralFactory getOWLLiteralFactory()
  {
    return getBridge().getOWLLiteralFactory();
  }

  @NonNull protected IRI prefixedName2IRI(String prefixedName)
  {
    Optional<@NonNull IRI> iri = getBridge().getIRIResolver().prefixedName2IRI(prefixedName);

    if (iri.isPresent())
      return iri.get();
    else
      throw new IllegalArgumentException("could not find IRI for prefixed name " + prefixedName);
  }

  @NonNull private SWRLRuleEngineBridge getBridge()
  {
    return this.bridge;
  }
}
