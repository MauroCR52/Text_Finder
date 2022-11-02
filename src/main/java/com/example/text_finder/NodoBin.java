package com.example.text_finder;

import lombok.Data;
import lombok.NonNull;

/**
 * Clase que administra el nodo de un arbol binario
 * @param <T>
 */
@Data
public class NodoBin <T extends Comparable<T>> {
    /**
     * Data del nodo
     */
    @NonNull
    private T data;

    private NodoBin<T> leftChild;
    private NodoBin<T> rightChild;
    private String posicion=null;

}
//https://www.youtube.com/watch?v=zIX3zQP0khM
