package com.hiberus.pochavas.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
@AllArgsConstructor
@Getter
public class RowExcel {

    private int row;
    private HashMap<String,String> fields;

}
