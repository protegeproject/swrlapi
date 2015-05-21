package org.swrlapi.bridge.extractors;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.core.OWLDatatypeFactory;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.SWRLAPIOWLDataFactory;
import org.swrlapi.core.resolvers.IRIResolver;
import org.swrlapi.core.resolvers.OWLClassExpressionResolver;
import org.swrlapi.core.resolvers.OWLDataPropertyExpressionResolver;
import org.swrlapi.core.resolvers.OWLDataRangeResolver;
import org.swrlapi.core.resolvers.OWLObjectPropertyExpressionResolver;

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

  @NonNull protected OWLClassExpressionResolver getOWLClassExpressionResolver()
  {
    return getBridge().getOWLClassExpressionResolver();
  }

  @NonNull protected OWLObjectPropertyExpressionResolver getOWLObjectPropertyExpressionResolver()
  {
    return getBridge().getOWLObjectPropertyExpressionResolver();
  }

  @NonNull protected OWLDataPropertyExpressionResolver getOWLDataPropertyExpressionResolver()
  {
    return getBridge().getOWLDataPropertyExpressionResolver();
  }

  @NonNull protected OWLDataRangeResolver getOWLDataRangeResolver()
  {
    return getBridge().getOWLDataRangeResolver();
  }

  @NonNull protected IRIResolver getIRIResolver()
  {
    return getBridge().getIRIResolver();
  }

  @NonNull protected IRI prefixedName2IRI(@NonNull String prefixedName)
  {
    return getIRIResolver().prefixedName2IRI(prefixedName);
  }

  @NonNull private SWRLRuleEngineBridge getBridge()
  {
    return this.bridge;
  }
}
