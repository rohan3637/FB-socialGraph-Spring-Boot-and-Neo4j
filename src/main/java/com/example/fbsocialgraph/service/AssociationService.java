package com.example.fbsocialgraph.service;

import com.example.fbsocialgraph.model.AssociationType;
import com.example.fbsocialgraph.model.ObjectType;

public interface AssociationService {
    
    void createAssociation(Long startObjId, Long endObjId, AssociationType associationType);
    void associationExists(Long id1, Long id2, AssociationType associationType);
    long countAssociation(Long objectId, AssociationType associationType, ObjectType objectType);
    void deleteAssociation(Long objId1, Long objId2, AssociationType associationType);
    
}
