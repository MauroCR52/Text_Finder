package com.example.text_finder;

    public interface Tree<T extends Comparable<T>> {

        Tree<T> insert(T data);

        void delete(T data);

        void traverse();

        T getMax();

        T getMin();

        boolean isEmpty();

    }

