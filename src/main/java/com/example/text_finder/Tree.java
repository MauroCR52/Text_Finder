package com.example.text_finder;

    public interface Tree<T extends Comparable<T>> {

        Tree<T> insert(T data);

        void delete(T data);

        void traverse();
        void Search(String hola);

        T getMax();

        T getMin();

        boolean isEmpty();
    }
//https://www.youtube.com/watch?v=zIX3zQP0khM

