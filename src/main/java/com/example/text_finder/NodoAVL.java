package com.example.text_finder;

import lombok.Data;
import lombok.NonNull;

@Data
public class NodoAVL <T extends Comparable<T>>{
    @NonNull
    private T data;

    private int height = 1;

    private NodoAVL<T> leftChild;
    private NodoAVL<T> rightChild;

}
//créditos para https://www.youtube.com/watch?v=Jj9Mit24CWk
