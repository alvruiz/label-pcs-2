package com.hiberus.pochavas.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@AllArgsConstructor
public class ExcelTable {

    private String fileId;
    private String sheetName;
    private String rangeStart;
    private String rageEnd;
    //Integer: row, String: Header, String: Value
    private ArrayList<RowExcel> table;


    public ExcelTable(String fileId,String sheetName,HashMap<Integer,String> headers){
        this.fileId = fileId;
        this.sheetName = sheetName;
        this.rangeStart = "";
        this.rageEnd = "";
        table = new ArrayList<>();

    }

    public void setLimitsStart(String start){
        this.rangeStart = start;
    }

    public void setLimitsEnd(String end){
        this.rageEnd = end;
    }
    public void addField(int row, String header, String value){
        boolean found = false;
        for(RowExcel r: table){
            if(r.getRow() == row){
                found = true;
                r.getFields().put(header,value);
            }
        }
        if(!found){
            RowExcel newRow = new RowExcel(row, new HashMap<>());
            newRow.getFields().put(header,value);
            table.add(newRow);
        }
    }

    public ArrayList<RowExcel> getTable(){
        return this.table;
    }
}
