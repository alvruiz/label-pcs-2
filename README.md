# Solución POC Havas BackEnd api for Excel tags processing
## Pasos de ejecución  

Pasos para ejecutar el procesado de etiquetas del excel:
- Ejecutar el microservicio de los ficheros
- Tener activo el contenedor de la BD
- Ejecutar la aplicación (PocHavas2Application.java)  
- 
## Diagrama de despliegue
![](https://github.com/alvruiz/label-pcs/blob/main/images/despliegue.png)  

## Aclaraciones para el correcto funcionamiento
Las siguientes consideraciones son necesarias para que el cliente haga un uso correcto del procesado de etiquetas:
- Se procesarán solo aquellas columnas cuya etiqueta haya sido añadida previamente ya sea a través del front o del endpoint creado.
- Para procesar una tabla es necesario que esta tenga  una cabecera.
- Cada cabecera puede pertenecer a varias tablas o dichose de otro modo cada tabla puede tener una misma cabecera.
- Para detectar una cabecera ha de comenzar por HS y terminar por HE, estas etiquetas han de estar justo en la columna previa a la primera cabecera y en la columna posterior a la ultima cabecera respectivamente.
- Para detectar una tabla es necesario que comienze por TS y termine por TE, estando estas etiquetas situadas en la celda anterior al primer elemento de la tabla y en la celda posterior al último elemento de la tabla respectivamente.
- TS y TE tienen que estar a la misma altura que HS y HE respectivamente para poder detectarlo correctamente.
- Puede haber varias tablas en horizontal (con diferentes cabeceras)
- Puede haber varias tablas en vertical (con la misma cabecera o diferente cabecera)
- Respecto a las etiquetas por simplificaciones se ha decido que sean "Capital sensititve" de este modo se diferenciará por ejemplo "Etiqueta1" de "etiqueta1".
- Las etiquetas son el texto contenido en la celda y no la "etiqueta" conocida de excel.
- TS,TE,HS,HE están definidos como variables y se pueden cambiar en el propio código, estas se pueden escribir en máyusculas y minúsculas.
- De momento considerado para fichero .xlsx
## Endpoints
![](https://github.com/alvruiz/label-pcs/blob/main/images/endpoints.png)

## Links

[Documentación swagger](http://localhost:8082/swagger-ui.html#/excel-controller-impl)



