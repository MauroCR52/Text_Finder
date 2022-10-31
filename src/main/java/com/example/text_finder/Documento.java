package com.example.text_finder;

public class Documento {
    public String Nombre;

    public String tipo;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Documento(String nombre, String direccion, int tamano, int fecha, String tipo) {
        this.Nombre = nombre;
        this.Direccion = direccion;
        this.Tamano = tamano;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public int getTamano() {
        return Tamano;
    }

    public void setTamano(int tamano) {
        Tamano = tamano;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public String Direccion;
    public int Tamano;
    public int fecha;
}
