package com.example.memopills.Objetos;

public class Pastillas {

    private String id;
    private String iduser;
    private String nombre;
    private String cantidad;
    private String tiempo;

    public Pastillas(String id, String iduser, String nombre, String cantidad, String tiempo) {
        this.id = id;
        this.iduser = iduser;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.tiempo = tiempo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
}
