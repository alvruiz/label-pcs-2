package com.hiberus.pochavas.domain.models;

import lombok.Getter;

@Getter
public class TagSchema {

    private String schema;
    private String tag;
    private String standarizedTag;

    public TagSchema(String schema, String tag,String standarizedTag){
        this.schema = schema;
        this.tag = tag;
        this.standarizedTag = standarizedTag;
    }
}
