package com.example.text_finder;

public class Biblioteca {
    public Nodo_Biblioteca head;
    public Nodo_Biblioteca tail;
    private int size;

    public Biblioteca() {
        this.head = null;
        this.size = 0;
    }

    public int getSize() {
        return this.size;
    }
    public static Biblioteca biblioteca = new Biblioteca();


    public void InsertarDocumento(Documento documento) {
        Nodo_Biblioteca nodo_biblioteca = new Nodo_Biblioteca(documento, null);
        nodo_biblioteca.next = this.head;
        this.head = nodo_biblioteca;
        this.size++;
    }

    public void ordenar_fecha() {
        if (size > 1) {
            boolean wasChanged;
            do {
                Nodo_Biblioteca current = this.head;
                Nodo_Biblioteca previous = null;
                Nodo_Biblioteca next = this.head.next;
                wasChanged = false;
                while (next != null) if (current.getData().getFecha() < next.getData().getFecha()) {
                    wasChanged = true;
                    if (previous != null) {
                        Nodo_Biblioteca sig = next.next;
                        previous.next = next;
                        next.next = current;
                        current.next = sig;
                    } else {
                        Nodo_Biblioteca sig = next.next;
                        head = next;
                        next.next = current;
                        current.next = sig;
                    }
                    previous = next;
                    next = current.next;
                } else {
                    previous = current;
                    current = next;
                    next = next.next;
                }
            } while (wasChanged);
        }
    }
    public void ordenar_tamano(){

    }

    Nodo_Biblioteca paritionLast(Nodo_Biblioteca start, Nodo_Biblioteca end)
    {
        if (start == end || start == null || end == null)
            return start;

        Nodo_Biblioteca pivot_prev = start;
        Nodo_Biblioteca curr = start;
        Documento pivot = end.data;

        while (start != end) {
            if (start.getData().getNombre().compareTo(pivot.getNombre())< 0){
                // keep tracks of last modified item
                pivot_prev = curr;
                Documento temp = curr.data;
                curr.data = start.data;
                start.data = temp;
                curr = curr.next;
            }
            start = start.next;
        }

        // Swap the position of curr i.e.
        // next suitable index and pivot
        Documento temp = curr.data;
        curr.data = pivot;
        end.data = temp;

        // Return one previous to current
        // because current is now pointing to pivot
        return pivot_prev;
    }
    public void ordenar_nombre(Nodo_Biblioteca start, Nodo_Biblioteca end){
        if(start == null || start == end|| start == end.next )
            return;

        // split list and partition recurse
        Nodo_Biblioteca pivot_prev = paritionLast(start, end);
        ordenar_nombre(start, pivot_prev);

        // if pivot is picked and moved to the start,
        // that means start and pivot is same
        // so pick from next of pivot
        if (pivot_prev != null && pivot_prev == start)
            ordenar_nombre(pivot_prev.next, end);

            // if pivot is in between of the list,
            // start from next of pivot,
            // since we have pivot_prev, so we move two nodes
        else if (pivot_prev != null
                && pivot_prev.next != null)
            ordenar_nombre(pivot_prev.next.next, end);

    }

}
