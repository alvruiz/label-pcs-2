package com.hiberus.pochavas.infrastructure.adapter.out.table;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TableRepository extends MongoRepository<TableEntity,Long> {
    List<TableEntity> getByFileId(String schema);
}
