/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.solr.morphline;

import org.apache.solr.schema.IndexSchema;

import com.cloudera.cdk.morphline.api.MorphlineContext;
import com.google.common.base.Preconditions;

/**
 * A context that is specific to Solr.
 */
public class SolrMorphlineContext extends MorphlineContext {

  private DocumentLoader loader;
  private IndexSchema schema;
  private FaultTolerance faultTolerance;
  
  /** For public access use {@link Builder#build()} instead */  
  protected SolrMorphlineContext() {}
  
  public DocumentLoader getDocumentLoader() {    
    return loader;
  }

  public IndexSchema getIndexSchema() {    
    return schema;
  }

  public FaultTolerance getFaultTolerance() {    
    return faultTolerance;
  }

  
  ///////////////////////////////////////////////////////////////////////////////
  // Nested classes:
  ///////////////////////////////////////////////////////////////////////////////
  /**
   * Helper to construct a {@link SolrMorphlineContext} instance.
   */
  public static class Builder extends MorphlineContext.Builder {
        
    private DocumentLoader loader;
    private IndexSchema schema;
    private FaultTolerance faultTolerance;
    
    public Builder() {}

    public Builder setDocumentLoader(DocumentLoader loader) {
      this.loader = loader;
      return this;
    }    

    public Builder setIndexSchema(IndexSchema schema) {
      this.schema = schema;
      return this;
    }    

    public Builder setFaultTolerance(FaultTolerance faultTolerance) {
      this.faultTolerance = faultTolerance;
      return this;
    }    

    @Override
    public SolrMorphlineContext build() {
      ((SolrMorphlineContext)context).loader = loader;
      ((SolrMorphlineContext)context).schema = schema;
      Preconditions.checkNotNull(faultTolerance, "build() requires a prior call to setFaultTolerance()");
      ((SolrMorphlineContext)context).faultTolerance = faultTolerance;
      return (SolrMorphlineContext) super.build();
    }

    @Override
    protected SolrMorphlineContext create() {
      return new SolrMorphlineContext();
    }
    
  }
 
}
