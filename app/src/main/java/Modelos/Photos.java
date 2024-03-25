package Modelos;

import java.sql.Blob;

public class Photos {
    private Integer id;
    private String descripcion;
    private byte[] photo;
    //Constructores

    public Photos() {
    }

    public Photos(Integer id, String descripcion, byte[] photo) {
        this.id = id;
        this.descripcion = descripcion;
        this.photo = photo;
    }

    //getteer and setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
