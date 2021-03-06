/*
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
package com.cloudera.cdk.morphline.api;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.cloudera.cdk.morphline.base.Compiler;
import com.cloudera.cdk.morphline.base.Notifications;
import com.cloudera.cdk.morphline.stdlib.PipeBuilder;
import com.codahale.metrics.MetricRegistry;
import com.google.common.base.Preconditions;
import com.typesafe.config.Config;

public class AbstractMorphlineTest extends Assert {
  
  protected Collector collector;
  protected Command morphline;
  protected MorphlineContext morphContext;
  
  protected static final String RESOURCES_DIR = "target/test-classes";
  
  @Before
  public void setUp() throws Exception {
    collector = new Collector();
  }
  
  @After
  public void tearDown() throws Exception {
    collector = null;
    morphline = null;
    morphContext = null;
  }
    
  protected Command createMorphline(String file, Config... overrides) throws IOException {
    return createMorphline(parse(file, overrides));
  }

  protected Command createMorphline(Config config) {
    morphContext = createMorphlineContext();
    return new PipeBuilder().build(config, null, collector, morphContext);
  }
  
  protected Config parse(String file, Config... overrides) throws IOException {
    Config config = new Compiler().parse(new File(RESOURCES_DIR + "/" + file + ".conf"), overrides);
    config = config.getConfigList("morphlines").get(0);
    Preconditions.checkNotNull(config);
    return config;
  }
  
  private MorphlineContext createMorphlineContext() {
    return new MorphlineContext.Builder().setMetricRegistry(new MetricRegistry()).build();
  }
  
  protected void deleteAllDocuments() {
    collector.reset();
  }
  
  protected void startSession() {
    Notifications.notifyStartSession(morphline);
  }

  protected static <T> T[] concat(T[]... arrays) {    
    if (arrays.length == 0) throw new IllegalArgumentException();
    Class clazz = null;
    int length = 0;
    for (T[] array : arrays) {
      clazz = array.getClass();
      length += array.length;
    }
    T[] result = (T[]) Array.newInstance(clazz.getComponentType(), length);
    int pos = 0;
    for (T[] array : arrays) {
      System.arraycopy(array, 0, result, pos, array.length);
      pos += array.length;
    }
    return result;
  }

}
