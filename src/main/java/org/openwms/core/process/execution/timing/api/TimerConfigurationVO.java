/*
 * Copyright 2005-2024 the original author or authors.
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
package org.openwms.core.process.execution.timing.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

/**
 * A TimerConfigurationVO.
 *
 * @author Heiko Scherrer
 */
public record TimerConfigurationVO(
        @NotBlank
        @JsonProperty("name")
        String name,
        @JsonProperty("description")
        String description,
        @NotBlank
        @JsonProperty("cronExpression")
        String cronExpression,
        @JsonProperty("runtimeVariables")
        Map<String, String> runtimeVariables
) implements Serializable {
}
