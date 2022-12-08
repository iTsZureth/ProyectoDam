package com.example.memopills.Objetos;

public class Medicamentos {
    private String id;
    private String nombre;
    private String observaciones;
    private String tiempo;

    public Medicamentos() {
    }

    public Medicamentos(String id, String nombre, String observaciones, String tiempo) {
        this.id = id;
        this.nombre = nombre;
        this.observaciones = observaciones;
        this.tiempo = tiempo;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public String toString() {
        return "Medicamentos{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", tiempo='" + tiempo + '\'' +
                '}';
    }
}
