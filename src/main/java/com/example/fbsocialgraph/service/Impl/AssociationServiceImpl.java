package com.example.fbsocialgraph.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fbsocialgraph.exception.AssociationNotFoundException;
import com.example.fbsocialgraph.exception.ObjectNotFoundException;
import com.example.fbsocialgraph.model.Association;
import com.example.fbsocialgraph.model.AssociationType;
import com.example.fbsocialgraph.model.ObjectNode;
import com.example.fbsocialgraph.model.ObjectType;
import com.example.fbsocialgraph.repository.ObjectNodeRepository;
import com.example.fbsocialgraph.service.AssociationService;

@Service
public class AssociationServiceImpl implements AssociationService {

    @Autowired
    private ObjectNodeRepository objectNodeRepository;

    @Override
    public void createAssociation(Long startObjId, Long endObjId, AssociationType associationType) {
        if(!objectNodeRepository.existsById(startObjId) || !objectNodeRepository.existsById(endObjId)){
            throw new ObjectNotFoundException(String.format("Object with id %s or %s not found !!", startObjId, endObjId));
        }
        /* 
        boolean exists = objectNodeRepository.associationExists(startObjId, endObjId, associationType);
        if(!exists){
            objectNodeRepository.createAssociation(startObjId, endObjId, associationType, associationType.reverseAssociation());
        }*/
        ObjectNode startNode = findObjectNodeById(startObjId);
        ObjectNode endNode = findObjectNodeById(endObjId);
        var exists = objectNodeRepository.associationExists(startObjId, endObjId, associationType);
        if(!exists){
            startNode.getEdges().add(new Association(associationType, endNode));
            endNode.getEdges().add(new Association(associationType.reverseAssociation(), startNode));
            objectNodeRepository.save(startNode);
        }
    }

    @Override
    public void associationExists(Long id1, Long id2, AssociationType associationType) {
        if(!objectNodeRepository.associationExists(id1, id2, associationType)){
            throw new AssociationNotFoundException(String.format("No association %s found.", associationType));
        }
    }

    @Override
    public long countAssociation(Long objectId, AssociationType associationType, ObjectType objectType) {
        return objectNodeRepository.countAssociation(objectId, associationType, objectType);
    }

    @Override
    public void deleteAssociation(Long objId1, Long objId2, AssociationType associationType) {
         objectNodeRepository.deleteAssociation(objId1, objId2, associationType);
         objectNodeRepository.deleteAssociation(objId1, objId2, associationType.reverseAssociation());
    }

    private ObjectNode findObjectNodeById(Long id){
        //findObjectNodeById
        return objectNodeRepository
                    .findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException(String.format("Object %s not found", id)));
    }
}
