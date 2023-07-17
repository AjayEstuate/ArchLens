# Spring Boot - ArchLens 

Spring Boot project to connect t0 external table and extract  binary data and View or Download it into original file.

## APIs

This project has 5 APIs:
1. ```/ArchLens/ds={datasource}/schemas``` - To get the list of all schemas present in External Table
2. ```/ArchLens/ds={datasource}/schema={schema}/tables``` - To get the list of tables present in the schema.
3. ```/ArchLens/view/ds={datasource}/schema={schema}/tablename={table}/blobColName={blobColName}/fileName={fileName}/{idName}={idVal}``` - To preview the table (get the first 10 records)
4. ```/ArchLens/download/ds={datasource}/schema={schema}/tablename={table}/blobColName={blobColName}/fileName={file_name}/{idName}={idVal}``` - To download a blob data
5. ''` /ArchLens/data-source - To add Server configuration details  '''

---

## Server

By default the server is running on port - ```8080```. 
This can be changed in the ```application.properties``` file.

---

## Connection to External Table

The server's default connection parameters are set as follows: it will attempt to connect to the host - '''localhost''' on port -'''10000''', with the '''username and password''' - both set to - '''null'''.
However, these settings can be modified in the - '''property.json''' file. 
Alternatively, new server details can be added using the - '''Archlens/data-source''' API.


---
