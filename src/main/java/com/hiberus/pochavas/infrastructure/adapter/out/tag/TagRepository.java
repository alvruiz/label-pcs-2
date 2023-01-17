package com.hiberus.pochavas.infrastructure.adapter.out.tag;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends MongoRepository<TagEntity,Long> {
    List<TagEntity> getBySchema(String schema);

    void deleteBySchemaAndTagAndStandarizedTag(String schema,String tag,String standarizedTag);
}
