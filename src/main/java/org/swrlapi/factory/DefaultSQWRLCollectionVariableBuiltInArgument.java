package org.swrlapi.factory;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;

class DefaultSQWRLCollectionVariableBuiltInArgument extends DefaultSWRLVariableBuiltInArgument
  implements SQWRLCollectionVariableBuiltInArgument
{
  private static final long serialVersionUID = 1L;

  private final String queryName, collectionName, collectionGroupID;

  public DefaultSQWRLCollectionVariableBuiltInArgument(IRI variableIRI, String variablePrefixedName, String queryName,
    String collectionName, String collectionGroupID)
  {
    super(variableIRI, variablePrefixedName);
    this.queryName = queryName;
    this.collectionName = collectionName;
    this.collectionGroupID = collectionGroupID;
  }

  @Override public String getGroupID()
  {
    return this.collectionGroupID;
  }

  @Override public String getQueryName()
  {
    return this.queryName;
  }

  @Override public String getCollectionName()
  {
    return this.collectionName;
  }

  @Override public <T> T accept(SWRLBuiltInArgumentVisitorEx<T> visitor)
  {
    return visitor.visit(this);
  }

  @Override public void accept(SWRLBuiltInArgumentVisitor visitor)
  {
    visitor.visit(this);
  }

  @Override public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    DefaultSQWRLCollectionVariableBuiltInArgument impl = (DefaultSQWRLCollectionVariableBuiltInArgument)obj;
    return
      (getQueryName().equals(impl.getQueryName()) || (getQueryName() != null && getQueryName().equals(impl.getQueryName())))
        && (getCollectionName() == impl.getCollectionName() || (getCollectionName() != null && getCollectionName()
        .equals(impl.getCollectionName()))) && (getGroupID().equals(impl.getGroupID()) || (getGroupID() != null
        && getGroupID().equals(impl.getGroupID())));
  }

  @Override public int hashCode()
  {
    int hash = 12;
    hash = hash + (null == getQueryName() ? 0 : getQueryName().hashCode());
    hash = hash + (null == getCollectionName() ? 0 : getCollectionName().hashCode());
    hash = hash + (null == getGroupID() ? 0 : getGroupID().hashCode());
    return hash;
  }
}
