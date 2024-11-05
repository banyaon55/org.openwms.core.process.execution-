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
package org.openwms.tms.routing.timing;

import org.ameba.annotation.Measured;
import org.ameba.annotation.TxService;
import org.springframework.context.ApplicationEventPublisher;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * A TimerConfigurationServiceImpl.
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

    @Measured
    @Override
    public List<TimerConfiguration> loadConfigurations() {
        return timerConfigurationRepository.findAll();
    }

    @Measured
    @Override
    public TimerConfiguration create(@NotNull TimerConfiguration newInstance) {
        var savedInstance = timerConfigurationRepository.save(newInstance);
        publisher.publishEvent(new ConfigurationEvent(savedInstance, ConfigurationEvent.EventType.CREATED));
        return savedInstance;
    }

    @Measured
    @Override
    public void delete(@NotBlank String pKey) {
        timerConfigurationRepository.deleteBypKey(pKey);
        publisher.publishEvent(new ConfigurationEvent(pKey, ConfigurationEvent.EventType.DELETED));
    }
}
