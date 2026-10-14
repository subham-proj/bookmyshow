package org.subhamsingh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.subhamsingh.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
