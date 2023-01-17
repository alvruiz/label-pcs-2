package com.hiberus.pochavas.infrastructure.adapter.in;

import com.google.gson.Gson;
import com.hiberus.pochavas.domain.models.ExcelTable;
import com.hiberus.pochavas.domain.models.SheetsTables;
import com.hiberus.pochavas.domain.models.TagSchema;
import com.hiberus.pochavas.domain.ports.in.DocumentStorageServiceUseCase;
import com.hiberus.pochavas.infrastructure.adapter.out.tag.ArrayTagSchemaDTO;
import com.hiberus.pochavas.infrastructure.adapter.out.tag.TagSchemaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ExcelControllerImpl implements ExcelController{

    //TODO: IDENTIFICAR SI AL SUBIR UN FICHERO ES NECESARIO COMPROBAR QUE YA EXISTE O REMPLAZARLO
    private final DocumentStorageServiceUseCase documentStorageServiceUseCase;


    @Override
    public ResponseEntity<String> processExcel(String file) {
        InputStream stream = documentStorageServiceUseCase.getFileAPI(file);
        if(stream == null){
            return new ResponseEntity<>("Error getting excel",HttpStatus.BAD_REQUEST);
        }
        ArrayList<SheetsTables> table = documentStorageServiceUseCase.processTables(stream,file);
        Gson gson = new Gson();

        return new ResponseEntity<>(gson.toJson(table),HttpStatus.OK);

    }




    private Boolean checkTagExists(TagSchemaDTO tag) {
        List<TagSchema> list = documentStorageServiceUseCase.getTagsFromSchema(tag.getSchema());
        for(TagSchema t: list){
            if(t.getStandarizedTag().equals(tag.getStandarizedTag()) && t.getTag().equals(tag.getTag())){
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseEntity<String> getTags(String schema) {
        List<TagSchema> result;
        try{
            Gson gson = new Gson();
            result = documentStorageServiceUseCase.getTagsFromSchema(schema);
            return new ResponseEntity<>(gson.toJson(result),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error deleting",HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> getTables(String schema) {
        List<ExcelTable> result;
        try{
            Gson gson = new Gson();
            result = documentStorageServiceUseCase.getTablesFromSchema(schema);
            return new ResponseEntity<>(gson.toJson(result),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error deleting",HttpStatus.BAD_REQUEST);
        }
    }
}
