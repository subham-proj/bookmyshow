package org.subhamsingh.eventservice.outbox;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.subhamsingh.eventservice.repository.OutboxRepository;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxRelay {

    private static final Logger log = LoggerFactory.getLogger(OutboxRelay.class);
    private static final int BATCH_SIZE = 100;

    private final OutboxRepository outboxRepository;
    private final EventProducer producer;

    /**
     * Scheduled + @Transactional on the same public method works because Spring's
     * scheduler invokes it through the bean proxy. Running the fetch and the marks
     * in ONE transaction is also what makes the SELECT ... FOR UPDATE SKIP LOCKED
     * in findUnpublished actually protect against concurrent relays.
     */
    @Scheduled(fixedDelay = 10000) // 10s
    @Transactional
    public void publishEvents() {
        List<Outbox> unpublished = outboxRepository.findUnpublished(PageRequest.of(0, BATCH_SIZE));

        for (Outbox outbox : unpublished) {
            try {
                producer.send(String.valueOf(outbox.getAggregateId()), outbox.getPayload());
                // managed entity -> dirty-checked and flushed on commit
                outbox.setPublishedAt(Instant.now());
            } catch (Exception e) {
                // Don't let one bad row block the rest of the batch; leave it
                // unpublished so it's retried on the next poll.
                log.error("Failed to publish outbox id={}", outbox.getId(), e);
            }
        }
    }
}
