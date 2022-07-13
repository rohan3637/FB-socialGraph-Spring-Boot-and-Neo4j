package com.example.fbsocialgraph.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.fbsocialgraph.model.AssociationType;
import com.example.fbsocialgraph.model.ObjectNode;
import com.example.fbsocialgraph.model.ObjectType;

public interface ObjectNodeService {
    
    ObjectNode createObjectNode(ObjectType objectType, Map<String, String>data);
    ObjectNode findObjectById(Long objectId);
    List<ObjectNode> findObjectsByType(ObjectType objectType);
    List<ObjectNode> findAdjacentObjectNodes(Long objectId, Optional<Integer> limit, ObjectType objectType, 
                AssociationType associationType);
    Long countAdjacentObjects(Long objectId, ObjectType objectType);
    List<ObjectNode> findMutualObjectNodes(Long objId1, Long objId2, int limit, ObjectType objectType);
    Long countMutualObjectNode(Long objId1, Long objId2, ObjectType objectType);
    List<ObjectNode> findObjectNodesWhere2RelationsNotExist(Long objectId, ObjectType objectType, 
                AssociationType associationType1, AssociationType associationType2);
    List<ObjectNode> findAdjObjectNodesWithFilterRelation(Long objectId, ObjectType objectType, 
                AssociationType associationType1, AssociationType associationType2);            
    ObjectNode updateObjectNode(Long objectId, Map<String, String>data);
    void deleteObjectNode(Long objectId);

    List<ObjectNode> findUserFeed(Long userId);

}
