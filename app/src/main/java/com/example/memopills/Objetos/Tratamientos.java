package com.example.memopills.Objetos;

public class Tratamientos {

    private String id;
    private String iduser;
    private String idmed;
    private String nombre;
    private String nombremed;
    private String tiempo;
    private int alarma;

    public Tratamientos() {
    }

    public Tratamientos(String id, String iduser, String idmed, String nombre, String nombremed, String tiempo, int alarma) {
        this.id = id;
        this.iduser = iduser;
        this.idmed = idmed;
        this.nombre = nombre;
        this.nombremed = nombremed;
        this.tiempo = tiempo;
        this.alarma = alarma;
    }

    public Tratamientos(String id, String nombre, String nombremed, String tiempo, int alarma) {
        this.id = id;
        this.nombre = nombre;
        this.nombremed = nombremed;
        this.tiempo = tiempo;
        this.alarma = alarma;
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

    public String getIdmed() {
        return idmed;
    }

    public void setIdmed(String idmed) {
        this.idmed = idmed;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombremed() {
        return nombremed;
    }

    public void setNombremed(String nombremed) {
        this.nombremed = nombremed;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public int getAlarma() {
        return alarma;
    }

    public void setAlarma(int alarma) {
        this.alarma = alarma;
    }

    @Override
    public String toString() {
        return "Tratamientos{" +
                "id='" + id + '\'' +
                ", iduser='" + iduser + '\'' +
                ", idmed='" + idmed + '\'' +
                ", nombre='" + nombre + '\'' +
                ", nombremed='" + nombremed + '\'' +
                ", tiempo='" + tiempo + '\'' +
                ", alarma=" + alarma +
                '}';
    }
}
