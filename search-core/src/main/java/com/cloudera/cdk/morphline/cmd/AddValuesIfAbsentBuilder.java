/**
 * Copyright 2013 Cloudera Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloudera.cdk.morphline.cmd;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.cloudera.cdk.morphline.api.Command;
import com.cloudera.cdk.morphline.api.CommandBuilder;
import com.cloudera.cdk.morphline.api.MorphlineContext;
import com.cloudera.cdk.morphline.api.Record;
import com.typesafe.config.Config;

/**
 * For each input field value, add the value to the given record output field if the value isn't
 * already contained in that field.
 */
public final class AddValuesIfAbsentBuilder implements CommandBuilder {

  @Override
  public Set<String> getNames() {
    return Collections.singleton("addValuesIfAbsent");
  }

  @Override
  public Command build(Config config, Command parent, Command child, MorphlineContext context) {
    return new AddValuesIfAbsent(config, parent, child, context);
  }
  
  
  ///////////////////////////////////////////////////////////////////////////////
  // Nested classes:
  ///////////////////////////////////////////////////////////////////////////////
  private static final class AddValuesIfAbsent extends AbstractAddValuesCommand {

    public AddValuesIfAbsent(Config config, Command parent, Command child, MorphlineContext context) {
      super(config, parent, child, context);      
    }
    
    @Override
    protected void putAll(Record record, String key, Collection values) {
      for (Object value : values) {
        put(record, key, value);
      }
    }
    
    @Override
    protected void put(Record record, String key, Object value) {
      record.putIfAbsent(key, value);
    }
    
  }
    
}
