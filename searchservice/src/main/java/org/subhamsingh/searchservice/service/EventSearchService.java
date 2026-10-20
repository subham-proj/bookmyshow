package org.subhamsingh.searchservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;
import org.subhamsingh.searchservice.document.EventDocument;
import org.subhamsingh.searchservice.dto.EventSearchResponse;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventSearchService {

    private final ElasticsearchOperations operations;
    private final ObjectMapper objectMapper;

    public List<EventSearchResponse> search(String q, String eventType,
                                            Instant from, Instant to,
                                            int page, int size) {
        StringQuery query = new StringQuery(buildQueryJson(q, eventType, from, to));
        query.setPageable(PageRequest.of(page, size));

        SearchHits<EventDocument> hits = operations.search(query, EventDocument.class);
        return hits.stream()
                .map(hit -> EventSearchResponse.from(hit.getContent()))
                .toList();
    }

    /**
     * Builds a bool query:
     *   must   -> full-text match on q (name/venue)
     *   filter -> optional exact eventType + optional startTime range
     * Built with Jackson (not string concat) so user input is safely escaped.
     */
    private String buildQueryJson(String q, String eventType, Instant from, Instant to) {
        Map<String, Object> multiMatch = Map.of("multi_match", Map.of(
                "query", q,
                "fields", List.of("eventName", "eventName.suggest", "venue")));

        List<Object> filters = new ArrayList<>();

        if (eventType != null && !eventType.isBlank()) {
            filters.add(Map.of("term", Map.of("eventType", eventType)));
        }

        if (from != null || to != null) {
            Map<String, Object> range = new LinkedHashMap<>();
            if (from != null) range.put("gte", from.toString());
            if (to != null) range.put("lte", to.toString());
            filters.add(Map.of("range", Map.of("startTime", range)));
        }

        Map<String, Object> bool = new LinkedHashMap<>();
        bool.put("must", List.of(multiMatch));
        bool.put("filter", filters);

        try {
            return objectMapper.writeValueAsString(Map.of("bool", bool));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to build search query", e);
        }
    }
}
