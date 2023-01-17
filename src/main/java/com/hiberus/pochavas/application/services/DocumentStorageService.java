package com.hiberus.pochavas.application.services;

import com.hiberus.pochavas.domain.models.ExcelTable;
import com.hiberus.pochavas.domain.models.SheetsTables;
import com.hiberus.pochavas.domain.models.TagSchema;
import com.hiberus.pochavas.domain.ports.in.DocumentStorageServiceUseCase;
import com.hiberus.pochavas.domain.ports.out.FilePersistPort;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.SchemaOutputResolver;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class DocumentStorageService implements DocumentStorageServiceUseCase {


    public static final String HEADER_START = "hs";
    public static final String HEADER_END = "he";
    public static final String TABLE_START = "ts";
    public static final String TABLE_END = "te";
    public static final String XLSX = ".xlsx";

    @Autowired
    private final FilePersistPort filePersistPort;



    RestTemplate restTemplate = new RestTemplate();

    @Override
    public InputStream getFileAPI(String file) {
        String URI ="http://localhost:8081/excel?file="+file;
        try{
            byte[] result = restTemplate.getForObject(URI,byte[].class);
            return new ByteArrayInputStream(result);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public ArrayList<SheetsTables> processTables(InputStream file, String fileName) {
        try{
            ArrayList<SheetsTables> sheetsTables = new ArrayList<>();
            Workbook workbook = new XSSFWorkbook(file);
            //Los tags se obtienen a partir del nombre del fichero
            List<TagSchema> tags = getTagsFromSchema(fileName.split(XLSX)[0]);
            
            for(int i = 0; i<workbook.getNumberOfSheets();i++){
                String sheetName = workbook.getSheetName(i);
                System.out.println(sheetName+"--------------------");
                ArrayList<ArrayList<ExcelTable>> list = processSheet(workbook.getSheetAt(i),fileName,tags);
                sheetsTables.add(new SheetsTables(sheetName,list));
            }
            return sheetsTables;

        }catch (Exception e){
            return null;
        }

    }

    @Override
    public void createTag(TagSchema tagSchema) {
        try{
            filePersistPort.createTag(tagSchema);
        }catch (Exception e){
            throw new IllegalStateException("Error");
        }
    }

    @Override
    public void deleteTag(TagSchema tagSchema) {
        try{
            filePersistPort.deleteTag(tagSchema);
        }catch (Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public List<TagSchema> getTagsFromSchema(String schema) {
        try{
            return filePersistPort.getTagsFromSchema(schema);
        }catch (Exception e){
            throw new IllegalStateException("Error");
        }
    }

    @Override
    public List<ExcelTable> getTablesFromSchema(String schema) {
        try{
            return filePersistPort.getTablesFromSchema(schema);
        }catch (Exception e){
            throw new IllegalStateException("Error");
        }
    }

    private ArrayList<ArrayList<ExcelTable>> processSheet(Sheet sheet,String fileName,List<TagSchema> tags) {
        boolean isHeaderRange = false;
        //Tablas en una sheet, cada cabecera puede tener varias tablas
        ArrayList<ArrayList<ExcelTable>> tablesSheet = new ArrayList<>();

        try {
            Iterator<Row> row = sheet.rowIterator();
            while (row.hasNext()) {
                ArrayList<String> headers = new ArrayList<>();
                Row filas;
                filas = row.next();
                Iterator<Cell> cellIterator1 = filas.cellIterator();
                Cell cellStart = null;
                while (cellIterator1.hasNext() ) {
                    Cell cell = cellIterator1.next();
                    if(isHeaderRange && !switchCellType(cell).equalsIgnoreCase("he")){
                        headers.add(switchCellType(cell));
                    }

                    if (cell.getCellType() == CellType.STRING && (cell.getStringCellValue().equalsIgnoreCase(HEADER_START))) {
                        cellStart = cell;
                        isHeaderRange = true;
                    }

                    if (cell.getCellType() == CellType.STRING && (cell.getStringCellValue().equalsIgnoreCase(HEADER_END))) {

                        isHeaderRange = false;
                        ArrayList<String> copyHeaders = new ArrayList<>();
                        //TODO REVISAR ESTA PARTE
                        for(String s: headers){
                            copyHeaders.add(s);
                        }
                        headers = new ArrayList<>();
                        //Una cabecera puede tener varias tablas

                        ArrayList<ExcelTable> table = processTableFromSheet(sheet,cellStart,cell,fileName,copyHeaders,tags);
                        tablesSheet.add(table);
                        cellStart = null;

                    }

                }


            }
            return tablesSheet;
        }catch (Exception e){
            return null;
        }

    }

    private ArrayList<ExcelTable> processTableFromSheet(Sheet sheet, Cell headerStart, Cell headerEnd,String fileName,ArrayList<String> copyHeaders,List<TagSchema> tags) {

        int columnStart = headerStart.getColumnIndex();
        int columnEnd = headerEnd.getColumnIndex();
        ExcelTable table = null;
        Cell tableStart = null;
        Cell tableEnd;
        ArrayList<ExcelTable> tables = new ArrayList<>();
        try{

            //BUCLE QUE RECORRE LAS FILAS PARA ENCONTRAR LA ETIQUETA DE INICIO Y FIN DE LA TABLA
            for(int i = headerStart.getRowIndex()+1; i<=sheet.getLastRowNum();i++){
                Cell checkStartCell = sheet.getRow(i).getCell(columnStart);
                Cell checkEndCell = sheet.getRow(i).getCell(columnEnd);

                if(checkStartCell!=null || checkEndCell!= null){
                    //Si detecta otro inicio de cabecera termina
                    if (switchCellType(checkStartCell).equalsIgnoreCase(HEADER_START)) {
                        break;
                    }

                    //Detecta comienzo de tabla
                    if(switchCellType(checkStartCell).equalsIgnoreCase(TABLE_START)){
                        System.out.println("AAAA");
                        tableStart = checkStartCell;
                        table = new ExcelTable(fileName,sheet.getSheetName(),copyHeaders);
                        table.setLimitsStart(tableStart.getAddress().toString());
                    }
                    //Detecta fin de tabla
                    if(switchCellType(checkEndCell).equalsIgnoreCase(TABLE_END)){
                        tableEnd = checkEndCell;
                        //Recorremos el contenido de la tabla
                        getValuesFromTable(sheet, headerStart, table, tableStart, tableEnd,tags);
                        table.setLimitsEnd(tableEnd.getAddress().toString());
                        //Almacenar tabla en BD
                        filePersistPort.saveTable(table);
                        tables.add(table);
                    }
                }

            }
            return tables;
        }catch (Exception e){
            return null;
        }
    }

    private void getValuesFromTable(Sheet sheet, Cell headerStart, ExcelTable table, Cell tableStart, Cell tableEnd,List<TagSchema> tags) {

        for(int i = tableStart.getRowIndex(); i<= tableEnd.getRowIndex(); i++){
            for(int j = tableStart.getColumnIndex()+1; j< tableEnd.getColumnIndex(); j++){
                String header = switchCellType(sheet.getRow(headerStart.getRow().getRowNum()).getCell(j));
                //Si es 1 quiere decir que el header hay que procesarlo
                long isProcesable = tags.stream().filter(x -> x.getTag().equals(header)).count();

                if(isProcesable > 0){
                    String standarizedTag = tags.stream().filter(x -> x.getTag().equals(header)).collect(ArrayList<TagSchema>::new,ArrayList::add,ArrayList::addAll).get(0).getStandarizedTag();
                    System.out.println(switchCellType(sheet.getRow(i).getCell(j)));
                    table.addField(i+1,standarizedTag,switchCellType(sheet.getRow(i).getCell(j)));
                }



            }
        }
    }


    private String switchCellType(Cell cell){
        if(cell == null){
            return "";
        }
        switch (cell.getCellType()){
            case BLANK:
                return "";
            case STRING:
                return (cell.getRichStringCellValue())+"";
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)){
                    return (cell.getDateCellValue()).toString();
                }else{
                    return (cell.getNumericCellValue())+"";
                }
            case FORMULA:
                return (cell.getCellFormula());
            case BOOLEAN:
                return (cell.getBooleanCellValue()) +"";
            default:
                return (cell.getCellType().toString());
        }
    }
}
