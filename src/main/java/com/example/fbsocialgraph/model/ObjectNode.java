package com.example.fbsocialgraph.model;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.CompositeProperty;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@Node
public class ObjectNode {

    @Id
    @GeneratedValue()
    private Long id;

    @NonNull
    private ObjectType type;

    @Relationship(type = "relate_to")
    @Lazy
    private List<Association> edges = new ArrayList<>();

    @CompositeProperty
    private Map<String, String> data = new HashMap<>();

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}