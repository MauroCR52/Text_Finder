package com.example.text_finder;

import lombok.Data;
import lombok.NonNull;

/**
 * Clase que administra el nodo de un arbol avl
 * @param <T>
 */
@Data
public class NodoAVL <T extends Comparable<T>>{
    /**
     * Data del nodo
     */
    @NonNull
    private T data;
    /**
     * Altura del nodo
     */
    private int height = 1;

    private NodoAVL<T> leftChild;
    private NodoAVL<T> rightChild;

}
//créditos para https://www.youtube.com/watch?v=Jj9Mit24CWk
