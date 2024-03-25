package Configuracion;

import java.sql.Blob;

public class Transacciones {
    //Nombre de la base de datos
    public static final String DBName = "PMPHOTOS";

    //Creación de las tablas de las bases de datos.
    public static final String TablePhotos = "photos";

    //Creacion de los campos de base de datos

    public static final String id = "id";
    public static final String descripcion = "descripcion";
    public static final String photo = "photo";

    // DDL Create
    public static final String CreateTablePhotos = "Create table "+ TablePhotos +"("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT, descripcion TEXT, photo BLOB )";

    //DDL Drop
    public static final String DropTablePhotos = "DROP TABLE IF EXISTS "+ TablePhotos;

    //DML
    public static final String SelectAllPhotos = "SELECT * FROM " + TablePhotos;
}
