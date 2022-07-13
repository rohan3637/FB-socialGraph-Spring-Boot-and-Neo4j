package com.example.fbsocialgraph.controller;

import com.example.fbsocialgraph.dto.ObjectNodeDto;
import com.example.fbsocialgraph.model.AssociationType;
import com.example.fbsocialgraph.model.ObjectNode;
import com.example.fbsocialgraph.model.ObjectType;
import com.example.fbsocialgraph.service.ObjectNodeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/objects")
@CrossOrigin(value = "*")
public class ObjectNodeController {

    @Autowired
    private ObjectNodeService objectNodeService;

    @GetMapping("/{objectId}/adjacents")
    public ResponseEntity<List<ObjectNode>> findAdjacentObjects(
        @PathVariable Long objectId,
        @RequestParam ObjectType objectType,
        @RequestParam AssociationType associationType,
        @RequestParam(required = false) Optional<Integer> limit
    ){
        List<ObjectNode> adjNode = objectNodeService.findAdjacentObjectNodes(objectId, limit, objectType, associationType);
        return new ResponseEntity<>(adjNode, HttpStatus.OK);
    }

    @GetMapping("/{objectId}/adjacents/filter")
    public ResponseEntity<List<ObjectNode>> findAdjacentObjectsWithFilterRelation(
        @PathVariable Long objectId,
        @RequestParam ObjectType objectType,
        @RequestParam AssociationType associationType1,
        @RequestParam AssociationType associationType2
    ){
        List<ObjectNode> adjNodeWithFilterRelation = objectNodeService.findAdjObjectNodesWithFilterRelation(objectId,
                objectType, associationType1, associationType2);
        return new ResponseEntity<>(adjNodeWithFilterRelation, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ObjectNode>> findObjectsByType(
        @RequestParam ObjectType objectType
    ){
        List<ObjectNode> objectNodesByType = objectNodeService.findObjectsByType(objectType);
        return new ResponseEntity<>(objectNodesByType, HttpStatus.OK);
    }  
    
    @GetMapping("/{objectId}")
    public ResponseEntity<ObjectNode> findObjectsById(
        @PathVariable Long objectId
    ){
        ObjectNode objectNode = objectNodeService.findObjectById(objectId);
        return new ResponseEntity<>(objectNode, HttpStatus.OK);
    }

    @GetMapping("/{objectId}/adjacents/count")
    public ResponseEntity<Long> countAdjacentObjects(
        @PathVariable Long objectId,
        @PathVariable ObjectType objectType
    ){
        Long count = objectNodeService.countAdjacentObjects(objectId, objectType);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/{id1}/mutual/{id2}")
    public ResponseEntity<List<ObjectNode>> findMutualObjects(
        @PathVariable Long id1,
        @PathVariable Long id2,
        @RequestParam int limit,
        @RequestParam ObjectType objectType
    ){
        List<ObjectNode> mutualObjects = objectNodeService.findMutualObjectNodes(id1, id2, limit, objectType);
        return new ResponseEntity<>(mutualObjects, HttpStatus.OK); 
    }

    @GetMapping("{id1}/mutual/{id2}/count")
    public ResponseEntity<Long> countMutualObjects(
        @PathVariable Long id1,
        @PathVariable Long id2,
        @RequestParam ObjectType objectType
    ){
        Long count = objectNodeService.countMutualObjectNode(id1, id2, objectType);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ObjectNode> createObject(
        @RequestBody ObjectNodeDto objectNodeDao
    ){
        ObjectNode objectNode = objectNodeService.createObjectNode(objectNodeDao.getType(), objectNodeDao.getData());
        return new ResponseEntity<>(objectNode, HttpStatus.CREATED);
    }

    @GetMapping("/feed/{userId}")
    public ResponseEntity<List<ObjectNode>> findUserFeed(
        @PathVariable Long userId
    ){
        List<ObjectNode> userFeed = objectNodeService.findUserFeed(userId);
        return new ResponseEntity<>(userFeed, HttpStatus.OK);
    }

    @GetMapping("/{objectId}/no_relations")
    public ResponseEntity<List<ObjectNode>> findObjectsWhere2RelationNotExist(
        @PathVariable Long objectId,
        @RequestParam ObjectType objectType,
        @RequestParam AssociationType associationType1,
        @RequestParam AssociationType associationType2
    ){
        List<ObjectNode> found = objectNodeService.findObjectNodesWhere2RelationsNotExist(objectId, objectType, 
                        associationType1, associationType2);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @DeleteMapping("/{objectId}")
    public ResponseEntity<Void> deleteObjectById(
        @PathVariable Long objectId
    ){
        objectNodeService.deleteObjectNode(objectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{objectId}")
    public ResponseEntity<ObjectNode> updateObject(
        @PathVariable Long objectId,
        @RequestBody ObjectNodeDto objectNodeDao
    ){
        ObjectNode objectNode = objectNodeService.updateObjectNode(objectId, objectNodeDao.getData());
        return new ResponseEntity<>(objectNode, HttpStatus.OK);
    }
    
}
