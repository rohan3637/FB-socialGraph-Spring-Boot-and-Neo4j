package com.example.fbsocialgraph.dto;

import com.example.fbsocialgraph.model.AssociationType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AssociationDto {

    private Long startObjectId;
    private Long endObjectId;
    private AssociationType type;
}
