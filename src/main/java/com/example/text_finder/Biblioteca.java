package com.example.text_finder;

/**
 * Clase biblioteca que será una lista enlazada que contiene los nodos para los documentos
 */
public class Biblioteca {
    public Nodo_Biblioteca head;
    private int size;

    public Biblioteca() {
        this.head = null;
        this.size = 0;
    }
    public static Biblioteca biblioteca = new Biblioteca();

    /**
     * Metodo que inserta un nodo
     * @param documento
     */
    public void InsertarDocumento(Documento documento) {
        Nodo_Biblioteca nodo_biblioteca = new Nodo_Biblioteca(documento, null);
        nodo_biblioteca.next = this.head;
        this.head = nodo_biblioteca;
        this.size++;
    }

}
