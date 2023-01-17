package com.hiberus.pochavas.infrastructure.adapter.out.table;

import com.hiberus.pochavas.domain.models.RowExcel;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@Getter
public class TableEntityDTO {


    private String fileId;
    private String sheetName;
    private String rangeStart;
    private String rangeEnd;
    //Integer: row, String: Header, String: Value
    private ArrayList<RowExcel> table;

}
