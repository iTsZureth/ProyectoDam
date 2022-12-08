package com.example.memopills.Objetos;

public class User {

    private String nombre;
    private String email;
    private int edad;
    private String contraseña;

    public User() {
    }

    public User(String nombre, String email, int edad, String contraseña) {
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
