package com.example.fbsocialgraph.service.Impl;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fbsocialgraph.exception.ObjectNotFoundException;
import com.example.fbsocialgraph.model.AssociationType;
import com.example.fbsocialgraph.model.ObjectNode;
import com.example.fbsocialgraph.model.ObjectType;
import com.example.fbsocialgraph.repository.ObjectNodeRepository;
import com.example.fbsocialgraph.service.ObjectNodeService;

@Service
public class ObjectNodeServiceImpl implements ObjectNodeService {

    @Autowired
    private ObjectNodeRepository objectNodeRepository;

    @Override
    public ObjectNode createObjectNode(ObjectType objectType, Map<String, String> data) {
        ObjectNode objectNode = new ObjectNode(objectType);
        if(data != null){
            objectNode.setData(data);
            objectNode.setCreatedAt(Instant.now());
            objectNode.setUpdatedAt(Instant.now());
        }
        return objectNodeRepository.save(objectNode);
    }

    @Override
    public ObjectNode findObjectById(Long objectId) {
        return objectNodeRepository
                    .findById(objectId)
                    .orElseThrow(() -> new ObjectNotFoundException(String.format("Object %s not found.", objectId)));
    }

    @Override
    public List<ObjectNode> findObjectsByType(ObjectType objectType) {
        return objectNodeRepository.findByType(objectType);
    }

    @Override
    public List<ObjectNode> findAdjacentObjectNodes(Long objectId, Optional<Integer> limit, ObjectType objectType,
            AssociationType associationType) {
        findObjectById(objectId);
        return limit
            .map(value -> objectNodeRepository.findAdjacentObjectsWithLimit(objectId, value, objectType))
            .orElse(objectNodeRepository.findAdjacentObjectNodes(objectId, objectType, associationType));
    }

    @Override
    public Long countAdjacentObjects(Long objectId, ObjectType objectType) {
        return objectNodeRepository.countAdjacentObjects(objectId, objectType);
    }

    @Override
    public List<ObjectNode> findMutualObjectNodes(Long objId1, Long objId2, int limit, ObjectType objectType) {
        return objectNodeRepository.findMutualObjectsWithLimit(objId1, objId2, limit, objectType);
    }

    @Override
    public Long countMutualObjectNode(Long objId1, Long objId2, ObjectType objectType) {
        return objectNodeRepository.countMutualObjectsWith(objId1, objId2, objectType);
    }

    @Override
    public List<ObjectNode> findObjectNodesWhere2RelationsNotExist(Long objectId, ObjectType objectType,
            AssociationType associationType1, AssociationType associationType2) {
        return objectNodeRepository.findObjectsWhere2RelationsNotExist(objectId, objectType, associationType1, associationType2);
    }

    @Override
    public List<ObjectNode> findAdjObjectNodesWithFilterRelation(Long objectId, ObjectType objectType,
            AssociationType associationType1, AssociationType associationType2) {
        return objectNodeRepository.findAdjacentObjectsWithFilterRelation(objectId, objectType, associationType1, associationType2);
    }

    @Override
    public ObjectNode updateObjectNode(Long objectId, Map<String, String>data) {
        ObjectNode objectNode = objectNodeRepository.findById(objectId)
                    .orElseThrow(() -> new ObjectNotFoundException(String.format("Object %s not found.", objectId)));
        objectNode.setData(data);
        objectNode.setUpdatedAt(Instant.now());
        return objectNodeRepository.save(objectNode);
    }

    @Override
    public void deleteObjectNode(Long objectId) {
        objectNodeRepository.deleteById(objectId);
    }

    @Override
    public List<ObjectNode> findUserFeed(Long userId) {
        return objectNodeRepository.findUserFeed(userId);
    }

}
