/*******************************************************************************
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
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.apache.drill.exec.record.vector;

import org.apache.drill.exec.memory.BufferAllocator;
import org.apache.drill.exec.record.MaterializedField;

public final class NullableFixed8 extends NullableValueVector<NullableFixed8, Fixed8>{
  static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(NullableFixed8.class);

  public NullableFixed8(MaterializedField field, BufferAllocator allocator) {
    super(field, allocator);
  }

  @Override
  protected Fixed8 getNewValueVector(BufferAllocator allocator) {
    return new Fixed8(this.field, allocator);
  }

  public long get(int index){
    return 1l;
  }
  
  public void set(int index, long value){
    
  }

}