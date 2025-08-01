/*
 * Copyright 2014-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.codecentric.boot.admin.server.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;

/**
 * Notifier that just writes to a logger.
 *
 * @author Johannes Edmeier
 */
public class LoggingNotifier extends AbstractStatusChangeNotifier {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingNotifier.class);

	public LoggingNotifier(InstanceRepository repository) {
		super(repository);
	}

	@Override
	protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
		return Mono.fromRunnable(() -> {
			if (event instanceof InstanceStatusChangedEvent statusChangedEvent) {
				LOGGER.info("Instance {} ({}) is {}", instance.getRegistration().getName(), event.getInstance(),
						statusChangedEvent.getStatusInfo().getStatus());
			}
			else {
				LOGGER.info("Instance {} ({}) {}", instance.getRegistration().getName(), event.getInstance(),
						event.getType());
			}
		});
	}

}
