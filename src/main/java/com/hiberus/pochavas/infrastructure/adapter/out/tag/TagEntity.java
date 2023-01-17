package com.hiberus.pochavas.infrastructure.adapter.out.tag;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tags")
@Getter
public class TagEntity {

    @Id
    private String id;
    private String schema;
    private String tag;
    private String standarizedTag;


    public TagEntity(String id, String schema, String tag, String standarizedTag) {
        this.id = id;
        this.schema = schema;
        this.tag = tag;
        this.standarizedTag = standarizedTag;
    }



}
