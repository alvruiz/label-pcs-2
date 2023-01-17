package com.hiberus.pochavas.infrastructure.adapter.out.table;

import com.hiberus.pochavas.domain.models.ExcelTable;
import org.springframework.stereotype.Component;

@Component
public class TableMapper {


    public TableEntity tableDomainToEntity(ExcelTable table){
        return new TableEntity(table.getFileId()+":"+table.getSheetName()+":"+table.getRangeStart()+":"+table.getRageEnd(),table.getFileId(),table.getSheetName(),table.getRangeStart(),table.getRageEnd(),table.getTable());
    }

    public ExcelTable tableEntityToDomain(TableEntity table) {
        return new ExcelTable(table.getFileId(),table.getSheetName(),table.getRangeStart(),table.getRangeEnd(),table.getTable());
    }
}
