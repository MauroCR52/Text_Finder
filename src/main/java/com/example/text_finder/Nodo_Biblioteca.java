package com.example.text_finder;

public class Nodo_Biblioteca {
    public Documento data;
    public Nodo_Biblioteca next;

    public Nodo_Biblioteca(Documento data, Nodo_Biblioteca next) {
        this.data = data;
        this.next = null;
    }

    public Documento getData() {
        return data;
    }

    public void setData(Documento data) {
        this.data = data;
    }

    public Nodo_Biblioteca getNext() {
        return next;
    }

    public void setNext(Nodo_Biblioteca next) {
        this.next = next;
    }

}
