package org.subhamsingh.searchservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.subhamsingh.searchservice.dto.EventSearchResponse;
import org.subhamsingh.searchservice.service.EventSearchService;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final EventSearchService eventSearchService;

    // GET /api/v1/search?q=concert&eventType=MUSIC&from=2026-01-01T00:00:00Z&to=2027-01-01T00:00:00Z
    @GetMapping
    public ResponseEntity<List<EventSearchResponse>> search(
            @RequestParam("q") String q,
            @RequestParam(value = "eventType", required = false) String eventType,
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(value = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        return ResponseEntity.ok(eventSearchService.search(q, eventType, from, to, page, size));
    }
}
