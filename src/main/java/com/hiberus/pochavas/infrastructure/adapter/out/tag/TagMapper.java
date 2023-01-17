package com.hiberus.pochavas.infrastructure.adapter.out.tag;

import com.hiberus.pochavas.domain.models.TagSchema;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public TagEntity tagSchemaDomainToEntity(TagSchema tag){
        return new TagEntity(tag.getSchema()+":"+tag.getTag(),tag.getSchema(),tag.getTag(),tag.getStandarizedTag());
    }

    public TagSchema tagSchemaEntityToDomain(TagEntity tag){
        return new TagSchema(tag.getSchema(),tag.getTag(),tag.getStandarizedTag());
    }
}
