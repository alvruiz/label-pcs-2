package com.hiberus.pochavas.domain.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Getter
public class ProcessTag {
    private String name;
    private ArrayList<String> coordinates;

    private String sheetName;

    public ProcessTag(String name, ArrayList<String> coordinates, String sheetName) {
        this.name = name;
        this.coordinates = coordinates;
        this.sheetName = sheetName;
    }
}
