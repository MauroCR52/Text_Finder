package com.example.text_finder;

import lombok.Data;
import lombok.NonNull;


@Data
public class NodoBin <T extends Comparable<T>> {
    @NonNull
    private T data;

    private NodoBin<T> leftChild;
    private NodoBin<T> rightChild;
    private String posicion=null;

}
//https://www.youtube.com/watch?v=zIX3zQP0khM
