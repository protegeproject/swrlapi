package org.swrlapi.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentType;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.exceptions.SWRLBuiltInException;

import java.util.Objects;

class DefaultSQWRLCollectionVariableBuiltInArgument extends DefaultSWRLVariableBuiltInArgument
  implements SQWRLCollectionVariableBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  @NonNull private final String queryName, collectionName, collectionGroupID;

  public DefaultSQWRLCollectionVariableBuiltInArgument(@NonNull IRI variableIRI, @NonNull String queryName,
    @NonNull String collectionName, @NonNull String collectionGroupID)
  {
    super(variableIRI);
    this.queryName = queryName;
    this.collectionName = collectionName;
    this.collectionGroupID = collectionGroupID;
  }

  @NonNull @Override public SWRLBuiltInArgumentType<?> getSWRLBuiltInArgumentType()
  {
    return SWRLBuiltInArgumentType.COLLECTION_VARIABLE;
  }

  @Override public String getGroupID()
  {
    return this.collectionGroupID;
  }

  @NonNull @Override public String getQueryName()
  {
    return this.queryName;
  }

  @NonNull @Override public String getCollectionName()
  {
    return this.collectionName;
  }

  @NonNull @Override public SQWRLCollectionVariableBuiltInArgument asCollectionVariable() throws SWRLBuiltInException
  {
    return this;
  }

  @NonNull @Override public <@NonNull T> T accept(@NonNull SWRLBuiltInArgumentVisitorEx<@NonNull T> visitor)
  {
    return visitor.visit(this);
  }

  @Override public void accept(@NonNull SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    if (!super.equals(o))
      return false;

    DefaultSQWRLCollectionVariableBuiltInArgument that = (DefaultSQWRLCollectionVariableBuiltInArgument)o;

    if (!Objects.equals(queryName, that.queryName))
      return false;
    if (!Objects.equals(collectionName, that.collectionName))
      return false;
    return Objects.equals(collectionGroupID, that.collectionGroupID);

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int result = super.hashCode();
    result = 31 * result + (queryName != null ? queryName.hashCode() : 0);
    result = 31 * result + (collectionName != null ? collectionName.hashCode() : 0);
    result = 31 * result + (collectionGroupID != null ? collectionGroupID.hashCode() : 0);
    return result;
  }
}
