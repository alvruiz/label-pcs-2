package com.hiberus.pochavas.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class SheetsTables {
    private String sheetName;
    private ArrayList<ArrayList<ExcelTable>> tables;
}
