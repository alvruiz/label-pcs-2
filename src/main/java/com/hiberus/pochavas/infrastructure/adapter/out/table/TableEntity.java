package com.hiberus.pochavas.infrastructure.adapter.out.table;

import com.hiberus.pochavas.domain.models.RowExcel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "tables")
@Getter
@AllArgsConstructor
public class TableEntity {

    @Id
    private String id;
    private String fileId;
    private String sheetName;
    private String rangeStart;
    private String rangeEnd;
    //Integer: row, String: Header, String: Value
    private ArrayList<RowExcel> table;



}
