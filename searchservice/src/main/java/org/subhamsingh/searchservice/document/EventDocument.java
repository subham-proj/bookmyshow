package org.subhamsingh.searchservice.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

/**
 * Elasticsearch document for the "events" index.
 * createIndex = false: the index + mapping are managed by hand (Kibana),
 * so Spring must not try to create/alter it.
 */
@Document(indexName = "events", createIndex = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDocument {

    @Id  // maps to ES _id -> set to eventId for idempotency
    private Long eventId;

    @Field(type = FieldType.Text)
    private String eventName;

    @Field(type = FieldType.Long)
    private Long organizerId;

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private Instant startTime;

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private Instant endTime;

    @Field(type = FieldType.Text)
    private String venue;

    @Field(type = FieldType.Keyword)
    private String eventType;

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private Instant createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private Instant updatedAt;
}
