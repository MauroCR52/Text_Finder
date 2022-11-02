package com.example.text_finder;

/**
 * Clase que lleva los atributos necesarios para la gestion de documentos
 */
public class Documento {
    /**
     * Nombre del documento
     */
    public String Nombre;
    /**
     * Formato del documento
     */
    public String tipo;

    public String getNombre() {
        return Nombre;
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

    public String getDireccion() {
        return Direccion;
    }

    public String Direccion;
    public int Tamano;
    public int fecha;
}
