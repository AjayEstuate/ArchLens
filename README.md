# Spring Boot - ArchLens 

Spring Boot project to demonstrate how to connect to Hive.

## APIs

This project has 4 APIs:
1. ```/blob-viewer/schemas``` - To get the list of all schemas in External Table.
2. ```/blob-viewer/{schema}/tables``` - To get the list of all tables in the given schema.
3. ```/blob-viewer/{schema}/preview/{table}``` - To preview the table (get the first 10 records)
4. ```/blob-viewer/schemas{schema}/view/{table}/blobData={blobVaLColName}/fileName={fileNameCN}/extension={extensionCN}/{idName}={idVal}``` - To download a blob data

---

## Server

By default the server is running on port ```8080```. This can be changed in the 
```application.properties``` file.

---

## Connection to Hive

By default the service tries to connect to Hive running at ```localhost```
on port ```10000```, with HTTP as the transport mode and ```cliservice``` as 
the HTTP path. This configuration can be changed in the ```application.properties```
file.

---
