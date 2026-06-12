# Architectural Decision Records

Utilice esta plantilla para registrar las decisiones de arquitectura tomadas
**En el contexto de** ...
**Teniendo en cuenta** ...
**Se decidió** ...
**Y se descartó** ...
**Para conseguir** ...
**Aceptando que** ...



## 4. Uso de S3

**En el contexto de** definir la funcionalidad de renombrar un archivo en S3
**Teniendo en cuenta** que no existe la opcion de renombrar un archivo, y la implementación esta clara en los requerimientos
**Se decidió** tener una carpeta en S3 de llegada, y una carpeta de S3 de salida con el archivo renombrado, y eliminar el archivo de la carpeta original
**Y se descartó** copiar el archivo en el mismo directorio y luego eliminar el anterior
**Para conseguir** que los clientes que requieran los archivos renombrados no los confundan con aquellos que aun no se han renombrado 
**Aceptando que** el requerimiento podria requerir que los archivos finales estene n la misma carpeta   

## 3. Elección base de datos

**En el contexto de** la seleccion de la base de datos a usar
**Teniendo en cuenta** que las opciones son PostgreSQL o DynamoDB, que los requisitos de escalabilidad y rendimiento no son demandantes, y la experiencia del equipo
**Se decidió** por usar PostgreSQL
**Y se descartó** DynamoDB
**Para conseguir** Velocidad en el desarrollo
**Aceptando que** posibles integraciones con AWS podrian facilitarse con DynamoDB


## 2. Arquitectura de la aplicación

**En el contexto de** definir la arquitectura de la aplicación
**Teniendo en cuenta** el tiempo de desarrollo es de 6 horas, que debe poderse ejecutar en un free tier 
**Se decidió** una arquitectura monolítica, separando tanto el front end como el back end
**Y se descartó** arquitecturas por microservicios
**Para conseguir** reducir costos y demostrar habilidades en desarrollo full stack y las tecnologias requeridas
**Aceptando que** la escalabilidad, la disponibilidad y la resilicencia del sistema pueden verse afectadas

## 1. Estimación volumen de archivos

**En el contexto de** poder estimar el volumen de archivos a procesar
**Teniendo en cuenta** que es para una entidad financiera, que son recibidos desde multiples sistemas, que el tiempo de desarrollo de la aplicación son 6 horas, y que debe poderse ejecutar en un AWS de free tier
**Se decidió** aproximar 1000 archivos diaros como volumen esperado de archivos
**Y se descartó** otras cantidades
**Para conseguir** un número razonable de archivos 
**Aceptando que** la estimación puede estar muy por encima o muy por debajo de la realidad