package com.example.fbsocialgraph.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.neo4j.core.schema.DynamicLabels;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RelationshipProperties
@RequiredArgsConstructor
public class Association {
    
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private AssociationType type;

    @DynamicLabels
    private List<String> labels = new ArrayList<>();

    @TargetNode
    @NonNull
    private ObjectNode target;
}
