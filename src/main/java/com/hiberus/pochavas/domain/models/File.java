package com.hiberus.pochavas.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

@AllArgsConstructor
@Getter
public class File {

    private String name;
    private HashMap<String, ArrayList<String>> tags;
}
