package com.hiberus.pochavas.infrastructure.adapter.in;

import com.hiberus.pochavas.infrastructure.adapter.out.tag.ArrayTagSchemaDTO;
import com.hiberus.pochavas.infrastructure.adapter.out.tag.TagSchemaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/tags")
public interface ExcelController {
    
    @ApiOperation(value="Permite procesar las tablas de un excel")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Procesado tablas excel con exito"),
            @ApiResponse(code = 400, message = "Ha habido un error"),
    })
    @CrossOrigin(origins = "http://localhost:4200", methods= {RequestMethod.POST})
    @PostMapping(value="",produces = "application/json")
    ResponseEntity<String> processExcel(@RequestParam("file") String file);





    @ApiOperation(value="Permite obtener tags de BD para un schema de un excel")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Obtenido tags con exito"),
            @ApiResponse(code = 400, message = "Ha habido un error"),
    })
    @CrossOrigin(origins = "http://localhost:4200", methods= {RequestMethod.GET})
    @GetMapping(value="/tag")
    ResponseEntity<String> getTags(@RequestParam("schema") String schema);

    @ApiOperation(value="Permite obtener tablas de BD para un schema de un excel")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Obtenido tags con exito"),
            @ApiResponse(code = 400, message = "Ha habido un error"),
    })
    @CrossOrigin(origins = "http://localhost:4200", methods= {RequestMethod.GET})
    @GetMapping(value="/tables",produces = "application/json")
    ResponseEntity<String> getTables(@RequestParam("schema") String schema);
}
