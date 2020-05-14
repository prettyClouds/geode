/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.apache.geode.redis.internal.ParameterRequirements;

import org.apache.geode.redis.internal.Command;
import org.apache.geode.redis.internal.ExecutionHandlerContext;

public class OrMultipleParameterRequirements implements ParameterRequirements {
  private final ParameterRequirements parameterRequirements;
  private final ParameterRequirements moreRequirements;

  public OrMultipleParameterRequirements(ParameterRequirements parameterRequirements,
      ParameterRequirements moreRequirements) {

    this.parameterRequirements = parameterRequirements;
    this.moreRequirements = moreRequirements;
  }

  @Override
  public void checkParameters(Command command, ExecutionHandlerContext context) {
    Exception e1 = null;
    Exception e2 = null;
    try {
      parameterRequirements.checkParameters(command, context);
    } catch (Exception e) {
      e1 = e;
    }
    try {
      moreRequirements.checkParameters(command, context);
    } catch (Exception e) {
      e2 = e;
    }
    if (e1 != null && e2 != null) {
      throw new RedisParametersMismatchException(command.wrongNumberOfArgumentsError());
    }
  }
}
