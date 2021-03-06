/**
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
 */
package org.apache.drill.optiq;

import org.apache.drill.jdbc.DrillTable;
import org.eigenbase.rel.TableAccessRelBase;
import org.eigenbase.relopt.RelOptCluster;
import org.eigenbase.relopt.RelOptPlanner;
import org.eigenbase.relopt.RelOptTable;
import org.eigenbase.relopt.RelTraitSet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * GroupScan of a Drill table.
 */
public class DrillScan extends TableAccessRelBase implements DrillRel {
  private final DrillTable drillTable;

  /** Creates a DrillScan. */
  public DrillScan(RelOptCluster cluster, RelTraitSet traits, RelOptTable table) {
    super(cluster, traits, table);
    assert getConvention() == CONVENTION;
    this.drillTable = table.unwrap(DrillTable.class);
    assert drillTable != null;
  }

  @Override
  public void register(RelOptPlanner planner) {
    super.register(planner);
    DrillOptiq.registerStandardPlannerRules(planner, drillTable.client);
  }

  public int implement(DrillImplementor implementor) {
    final ObjectNode node = implementor.mapper.createObjectNode();
    node.put("op", "scan");
    node.put("memo", "initial_scan");
    node.put("ref", "_MAP"); // output is a record with a single field, '_MAP'
    node.put("storageengine", drillTable.getStorageEngineName());
    node.put("selection", implementor.mapper.convertValue(drillTable.getSelection(), JsonNode.class));
    implementor.registerSource(drillTable);
    return implementor.add(node);
  }
}
