package org.subhamsingh.eventservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.subhamsingh.eventservice.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
