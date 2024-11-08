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
package org.openwms.core.process.execution.spi;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.ameba.LoggingCategories.BOOT;

/**
 * A ActivitiExecutor delegates to Activiti for program execution.
 *
 * @author Heiko Scherrer
 */
@Profile("ACTIVITI")
@Component
class ActivitiExecutor extends AbstractExecutor<ProcessDefinition> {

    private static final Logger BOOT_LOGGER = LoggerFactory.getLogger(BOOT);
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;

    ActivitiExecutor(RuntimeService runtimeService, RepositoryService repositoryService) {
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        BOOT_LOGGER.info("-- w/ Activiti executor");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ProcessDefinition loadProcessDefinition(String processName) {
        return repositoryService.createProcessDefinitionQuery().processDefinitionKey(processName).active().latestVersion().singleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void executeProcessDefinition(ProcessDefinition processDefinition, Map<String, Object> runtimeVariables) {
        runtimeService.startProcessInstanceById(processDefinition.getId(), runtimeVariables);
    }
}
