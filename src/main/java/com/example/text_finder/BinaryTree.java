package com.example.text_finder;

import lombok.NonNull;

import java.util.Objects;

public class BinaryTree<T extends Comparable<T>> implements Tree<T> {
    private NodoBin<T> root;
    private Boolean r = false;

    @Override
    public BinaryTree<T> insert(T data) {
        root = insert(data, root);
        return this;
    }

    private NodoBin<T> insert(T data, NodoBin<T> NodoBin) {
        if (NodoBin == null) {
            return new NodoBin<>(data);
        }
        if (data.compareTo(NodoBin.getData()) < 0) {
            NodoBin.setLeftChild(insert(data, NodoBin.getLeftChild()));

        } else if (data.compareTo(NodoBin.getData()) > 0) {
            NodoBin.setRightChild(insert(data, NodoBin.getRightChild()));
        }
        return NodoBin;
    }

    @Override
    public void delete(T data) {
        root = delete(data, root);
    }

    private NodoBin<T> delete(T data, NodoBin<T> NodoBin) {
        if (NodoBin == null) {
            return null;
        }
        if (data.compareTo(NodoBin.getData()) < 0) {
            NodoBin.setLeftChild(delete(data, NodoBin.getLeftChild()));
        } else if (data.compareTo(NodoBin.getData()) > 0) {
            NodoBin.setRightChild(delete(data, NodoBin.getRightChild()));
        } else {
            // One child or No children
            if (NodoBin.getLeftChild() == null) {
                return NodoBin.getRightChild();
            } else if (NodoBin.getRightChild() == null) {
                return NodoBin.getLeftChild();
            }
            // Two children
            NodoBin.setData(getMax(NodoBin.getLeftChild()));
            NodoBin.setLeftChild(delete(NodoBin.getData(), NodoBin.getLeftChild()));
        }
        return NodoBin;
    }

    @Override
    public void traverse() {
        traverseInOrder(root);
    }

    private void traverseInOrder(NodoBin<T> NodoBin) {
        if (NodoBin == null) {
            return;
        }
        traverseInOrder(NodoBin.getLeftChild());
        System.out.println(NodoBin);
        traverseInOrder(NodoBin.getRightChild());
    }

    @Override
    public void Search(String pal) {
        r = false;
        Search2(root, pal);
        if (!r) {
            System.out.println("La palabra no se encuentra en el texto");
        } else {
            System.out.println("La palabra se encuentra en el texto");
        }
    }

    private void Search2(NodoBin<T> NodoBin, String x) {

        if (r == false) {
            if (NodoBin == null) {
                return;
            }
            Search2(NodoBin.getLeftChild(), x);
            String t = String.valueOf(NodoBin.getData());
            String temp[] = t.split("¬", -1);
            if (Objects.equals(x, String.valueOf(temp[0]))) {
                r = true;
            }
            Search2(NodoBin.getRightChild(), x);
        }

    }

    @Override
    public T getMax() {
        if (isEmpty()) {
            return null;
        }
        return getMax(root);
    }

    private T getMax(NodoBin<T> NodoBin) {
        if (NodoBin.getRightChild() != null) {
            return getMax(NodoBin.getRightChild());
        }
        return NodoBin.getData();
    }

    @Override
    public T getMin() {
        if (isEmpty()) {
            return null;
        }
        return getMin(root);
    }

    private T getMin(NodoBin<T> NodoBin) {
        if (NodoBin.getLeftChild() != null) {
            return getMin(NodoBin.getLeftChild());
        }
        return NodoBin.getData();
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }
}
//https://www.youtube.com/watch?v=zIX3zQP0khM


