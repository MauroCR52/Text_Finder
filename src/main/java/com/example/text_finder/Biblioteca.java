package com.example.text_finder;

public class Biblioteca {
    public Nodo_Biblioteca head;
    private int size;

    public Biblioteca(){
        this.head = null;
        this.size = 0;
    }
    public int getSize(){
        return this.size;
    }
    public void InsertarDocumento(Documento documento){
        Nodo_Biblioteca nodo_biblioteca = new Nodo_Biblioteca(documento, null);
        nodo_biblioteca.next = this.head;
        this.head = nodo_biblioteca;
        this.size++;

    }
}
