package org.subhamsingh.searchservice.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.subhamsingh.searchservice.document.EventDocument;

@Repository
public interface EventSearchRepository extends ElasticsearchRepository<EventDocument, Long> {
    // Used by the indexer (save). Search uses ElasticsearchOperations in the service.
}
