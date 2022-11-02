package com.example.text_finder;

/**
 * Clase de Nodo que gestiona los objetos de Documento a cada nodo de la biblioteca
 */
public class Nodo_Biblioteca {
    /**
     * Data del objeto de documento
     */
    public Documento data;
    public Nodo_Biblioteca next;

    public Nodo_Biblioteca(Documento data, Nodo_Biblioteca next) {
        this.data = data;
        this.next = null;
    }

    /**
     * Metodo que retorna la data de un nodo
     * @return
     */
    public Documento getData() {
        return data;
    }


}
