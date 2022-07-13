package com.example.fbsocialgraph.controller;

import com.example.fbsocialgraph.dto.AssociationDto;
import com.example.fbsocialgraph.model.AssociationType;
import com.example.fbsocialgraph.model.ObjectType;
import com.example.fbsocialgraph.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/association")
public class AssociationController {
    
    @Autowired
    private AssociationService associationService;

    @PostMapping("")
    public ResponseEntity<Void> createAssociation(
        @RequestBody AssociationDto associationDao
    ){
        associationService.createAssociation(associationDao.getStartObjectId(), associationDao.getEndObjectId(), associationDao.getType());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id1}/{id2}")
    public ResponseEntity<Void> findAssociation(
        @PathVariable Long id1,
        @PathVariable Long id2,
        @RequestParam AssociationType associationType
    ){
        associationService.associationExists(id1, id2, associationType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{objectId}/count")
    public ResponseEntity<Long> countAssociation(
        @PathVariable Long objectId,
        @RequestParam AssociationType associationType,
        ObjectType objectType
    ){
        Long count = associationService.countAssociation(objectId, associationType, objectType);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @DeleteMapping("/{id1}/{id2}")
    public ResponseEntity<Void> deleteAssociation(
        @PathVariable Long id1,
        @PathVariable Long id2,
        @RequestParam AssociationType associationType
    ){
        associationService.deleteAssociation(id1, id2, associationType);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
