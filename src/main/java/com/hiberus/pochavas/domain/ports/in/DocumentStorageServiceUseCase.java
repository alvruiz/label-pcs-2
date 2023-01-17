package com.hiberus.pochavas.domain.ports.in;

import com.hiberus.pochavas.domain.models.ExcelTable;
import com.hiberus.pochavas.domain.models.SheetsTables;
import com.hiberus.pochavas.domain.models.TagSchema;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public interface DocumentStorageServiceUseCase {


    InputStream getFileAPI(String file);

    ArrayList<SheetsTables> processTables(InputStream file, String fileName);

    void createTag(TagSchema tagSchema);

    void deleteTag(TagSchema tagSchema);

    List<TagSchema> getTagsFromSchema(String schema);

    List<ExcelTable> getTablesFromSchema(String schema);
}
