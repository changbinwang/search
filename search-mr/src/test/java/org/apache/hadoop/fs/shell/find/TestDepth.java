/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.fs.shell.find;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.shell.PathData;
import org.apache.hadoop.fs.shell.find.Depth;
import org.apache.hadoop.fs.shell.find.FindOptions;
import org.apache.hadoop.fs.shell.find.Result;
import org.junit.Test;

public class TestDepth extends TestExpression {
  @Test
  public void initialise() throws IOException{
    FindOptions options = new FindOptions();
    Depth depth = new Depth();
    
    assertFalse(options.isDepth());
    depth.initialise(options);
    assertTrue(options.isDepth());
  }

  @Test
  public void apply() throws IOException{
    Depth depth = new Depth();
    depth.initialise(new FindOptions());
    assertEquals(Result.PASS, depth.apply(new PathData("anything", new Configuration())));
  }
}
