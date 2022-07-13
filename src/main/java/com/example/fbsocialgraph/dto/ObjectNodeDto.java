package com.example.fbsocialgraph.dto;

import java.util.Map;

import com.example.fbsocialgraph.model.ObjectType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ObjectNodeDto {
    
    private ObjectType type;
    private Map<String, String> data;

}
