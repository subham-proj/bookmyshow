package org.subhamsingh.eventservice.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.subhamsingh.eventservice.outbox.Outbox;

import java.time.Instant;
import java.util.List;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    // Unpublished rows, oldest first, row-locked with SKIP LOCKED so concurrent
    // relay instances never grab the same row.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "-2")) // SKIP LOCKED
    @Query("select o from Outbox o where o.publishedAt is null order by o.id asc")
    List<Outbox> findUnpublished(Pageable pageable);

    @Modifying
    @Query("update Outbox o set o.publishedAt = :now where o.id = :id")
    void markPublished(@Param("id") Long id, @Param("now")Instant now);
}
