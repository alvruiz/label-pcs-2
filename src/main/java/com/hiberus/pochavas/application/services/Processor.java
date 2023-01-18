package com.hiberus.pochavas.application.services;

import com.hiberus.pochavas.domain.models.ProcessTag;

import java.util.ArrayList;
import java.util.HashMap;

public class Processor {

    public static String isProcessable(int row, int collumn, ArrayList<ProcessTag> tags, String sheetName, HashMap<Integer,String> copyHeaders){
        for(ProcessTag tag: tags){
            if(tag.getSheetName().equals(sheetName)){
                for (String coord: tag.getCoordinates()){
                    int colLetter = coord.charAt(1)-'A'+1;
                    String header = copyHeaders.get(colLetter);
                    if(colLetter-1 == collumn){
                        int rowRangeStart = Integer.parseInt(coord.split("\\$")[2].split(":")[0]);
                        int rowRangeEnd = Integer.parseInt(coord.split("\\$")[4]);
                        if(row >= rowRangeStart-1 && row <= rowRangeEnd-1){
                            return tag.getName();
                        }
                    }
                }
            }
        }
        return null;
    }
    }