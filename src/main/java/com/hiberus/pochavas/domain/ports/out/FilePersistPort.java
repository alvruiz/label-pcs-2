package com.hiberus.pochavas.domain.ports.out;

import com.hiberus.pochavas.domain.models.ExcelTable;
import com.hiberus.pochavas.domain.models.TagSchema;

import java.util.List;

public interface FilePersistPort {

    void createTag(TagSchema tagSchema);
    void deleteTag(TagSchema tagSchema);

    List<TagSchema> getTagsFromSchema(String schema);

    void saveTable(ExcelTable table);

    List<ExcelTable> getTablesFromSchema(String schema);
}
