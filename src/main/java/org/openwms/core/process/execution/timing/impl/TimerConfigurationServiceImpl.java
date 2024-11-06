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
package org.openwms.core.process.execution.timing.impl;

import org.ameba.annotation.Measured;
import org.ameba.annotation.TxService;
import org.openwms.core.process.execution.timing.events.ConfigurationEvent;
import org.openwms.core.process.execution.timing.TimerConfiguration;
import org.openwms.core.process.execution.timing.TimerConfigurationService;
import org.springframework.context.ApplicationEventPublisher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * A TimerConfigurationServiceImpl is a Spring managed transactional service to manage {@link TimerConfiguration} entities.
 *
 * @author Heiko Scherrer
 */
@TxService
class TimerConfigurationServiceImpl implements TimerConfigurationService {

    private final ApplicationEventPublisher publisher;
    private final TimerConfigurationRepository timerConfigurationRepository;

    TimerConfigurationServiceImpl(ApplicationEventPublisher publisher, TimerConfigurationRepository timerConfigurationRepository) {
        this.publisher = publisher;
        this.timerConfigurationRepository = timerConfigurationRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public List<TimerConfiguration> loadConfigurations() {
        return timerConfigurationRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public TimerConfiguration create(@NotNull TimerConfiguration newInstance) {
        var savedInstance = timerConfigurationRepository.save(newInstance);
        publisher.publishEvent(new ConfigurationEvent(savedInstance, ConfigurationEvent.EventType.CREATED));
        return savedInstance;
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public void delete(@NotBlank String pKey) {
        timerConfigurationRepository.deleteBypKey(pKey);
        publisher.publishEvent(new ConfigurationEvent(pKey, ConfigurationEvent.EventType.DELETED));
    }
}
