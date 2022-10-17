package com.example.text_finder;

import lombok.Data;
import lombok.NonNull;


@Data
public class NodoBin <T extends Comparable<T>> {
    @NonNull
    private T data;

    private NodoBin<T> leftChild;
    private NodoBin<T> rightChild;

}
//Créditos para https://www.youtube.com/watch?v=Jgx6Vr1KCsk
