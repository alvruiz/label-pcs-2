package com.hiberus.pochavas.application.services;

import com.hiberus.pochavas.domain.models.ProcessTag;

import java.util.ArrayList;

public class Processor {

    public static String isProcessable(int row, int collumn, ArrayList<ProcessTag> tags, String sheetName){
        for(ProcessTag tag: tags){
            if(tag.getSheetName().equals(sheetName)){
                for (String coord: tag.getCoordinates()){
                    int colLetter = coord.charAt(1)-'A'+1;

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