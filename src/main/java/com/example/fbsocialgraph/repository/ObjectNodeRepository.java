package com.example.fbsocialgraph.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import com.example.fbsocialgraph.model.AssociationType;
import com.example.fbsocialgraph.model.ObjectNode;
import com.example.fbsocialgraph.model.ObjectType;

public interface ObjectNodeRepository extends Neo4jRepository<ObjectNode, Long> {
    
    @Query("MATCH (n:ObjectNode{id: $objectId}) -[r:relate_to{type: $associationType}]-> (p:ObjectNode{type: $objectType}) return p")
    List<ObjectNode> findAdjacentObjectNodes(Long objectId, ObjectType objectType, AssociationType associationType);

    @Query("MATCH (n:ObjectNode{id:$objectId}) -[:relate_to{type: $associationType}]-> (p:ObjectNode{type:$type}) " +
            "WHERE NOT (p) - [:relate_to{type: $filter}] -> (:ObjectNode) " +
            "return p")
    List<ObjectNode> findAdjacentObjectsWithFilterRelation(
            Long objectId, ObjectType type, AssociationType associationType, AssociationType filter);

    @Query("MATCH (n:ObjectNode{type:$type}) return n;")
    List<ObjectNode> findByType(ObjectType type);

    @Query("MATCH (n:ObjectNode{id:$objectId}) -[r]-> (p:ObjectNode{type:$type}) return count(p)")
    long countAdjacentObjects(Long objectId, ObjectType type);

    @Query("MATCH (n:ObjectNode{id:$objectId}) -[r]-> (p:ObjectNode{type:$type}) return p limit $limit")
    List<ObjectNode> findAdjacentObjectsWithLimit(Long objectId, int limit, ObjectType type);

    @Query("MATCH (:ObjectNode{id: $objId1}) -[:relate_to]->(m:ObjectNode{type: $type})<-[:relate_to]- " +
            "(:ObjectNode{id: $objId2}) return m limit $limit")
    List<ObjectNode> findMutualObjectsWithLimit(Long objId1, Long objId2, int limit, ObjectType type);

    @Query("MATCH (:ObjectNode{id: $objId1}) -[:relate_to]->(m:ObjectNode{type: $type})<-[:relate_to]- " +
            "(:ObjectNode{id: $objId2}) return count(m)")
    long countMutualObjectsWith(Long objId1, Long objId2, ObjectType type);

    @Query("OPTIONAL MATCH (n:ObjectNode), (m:ObjectNode) \n" +
            "WHERE n.id= $startObjId AND m.id= $endObjId\n" +
            "CREATE (n) - [r:relate_to{type: $type}] -> (m) - [re:relate_to{type: $reverseType}] -> (n)\n")
    void createAssociation(Long startObjId, Long endObjId, AssociationType type, AssociationType reverseType);

    @Query("MATCH (n:ObjectNode{id:$startObjId}) -[r:relate_to{type:$type}]- (m:ObjectNode{id:$endObjId}) RETURN COUNT(r) > 0")
    boolean associationExists(Long startObjId, Long endObjId, AssociationType type);

    @Query("MATCH (:ObjectNode{id:$startObjId}) - [r:relate_to{type:$type}] -> (:ObjectNode{type: $objectType}) " +
            "RETURN COUNT(r)")
    long countAssociation(Long startObjId, AssociationType type, ObjectType objectType);

    @Query("MATCH (:ObjectNode{id: $objId1}) -[r:relate_to{type: $type}]- (:ObjectNode{id: $objId2}) delete r")
    void deleteAssociation(Long objId1, Long objId2, AssociationType type);

    @Query("MATCH  (u:ObjectNode{id: $objectId}), (n:ObjectNode{type: $objectType}) " +
            "WHERE NOT (u) - [:relate_to{type: $associationType1}] -> (n) " +
            "AND NOT (u) - [:relate_to{type: $associationType2}] -> (n) " +
            "return n")
    List<ObjectNode> findObjectsWhere2RelationsNotExist(
            Long objectId, ObjectType objectType, AssociationType associationType1, AssociationType associationType2);

    // This is a dummy function to generate user feed just for the sake of demo.
    @Query("MATCH (n:ObjectNode{id: $userId}) - [:relate_to{type: 'FRIEND'}] " +
            "-> (u:ObjectNode{type: 'USER'}) -[:relate_to{type: 'CREATED'}]-> (p:ObjectNode{type: 'POST'}) " +
            "WHERE NOT (p) - [:relate_to] -> (:ObjectNode{type: 'GROUP'}) return p")
    List<ObjectNode> findUserFeed(Long userId);
    
}
