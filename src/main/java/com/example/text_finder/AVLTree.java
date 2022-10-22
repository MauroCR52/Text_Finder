package com.example.text_finder;

public class AVLTree <T extends Comparable<T>> implements Tree<T> {
    private NodoAVL<T> root;

    @Override
    public AVLTree<T> insert(T data) {
        root = insert(data, root);
        return this;
    }

    private NodoAVL<T> insert(T data, NodoAVL<T> NodoAVL) {
        if (NodoAVL == null) {
            return new NodoAVL<>(data);
        }
        if (data.compareTo(NodoAVL.getData()) < 0) {
            NodoAVL.setLeftChild(insert(data, NodoAVL.getLeftChild()));
        } else if (data.compareTo(NodoAVL.getData()) > 0) {
            NodoAVL.setRightChild(insert(data, NodoAVL.getRightChild()));
        } else {
            return NodoAVL;
        }
        updateHeight(NodoAVL);
        return applyRotation(NodoAVL);
    }

    @Override
    public void delete(T data) {
        root = delete(data, root);
    }

    private NodoAVL<T> delete(T data, NodoAVL<T> NodoAVL) {
        if (NodoAVL == null) {
            return null;
        }
        if (data.compareTo(NodoAVL.getData()) < 0) {
            NodoAVL.setLeftChild(delete(data, NodoAVL.getLeftChild()));
        } else if (data.compareTo(NodoAVL.getData()) > 0) {
            NodoAVL.setRightChild(delete(data, NodoAVL.getRightChild()));
        } else {
            // One Child or Leaf NodoAVL (no children)
            if (NodoAVL.getLeftChild() == null) {
                return NodoAVL.getRightChild();
            } else if (NodoAVL.getRightChild() == null) {
                return NodoAVL.getLeftChild();
            }
            // Two Children
            NodoAVL.setData(getMax(NodoAVL.getLeftChild()));
            NodoAVL.setLeftChild(delete(NodoAVL.getData(), NodoAVL.getLeftChild()));
        }
        updateHeight(NodoAVL);
        return applyRotation(NodoAVL);
    }

    @Override
    public void traverse() {
        traverseInOrder(root);
    }

    private void traverseInOrder(NodoAVL<T> NodoAVL) {
        if (NodoAVL != null) {
            traverseInOrder(NodoAVL.getLeftChild());
            System.out.println(NodoAVL);
            traverseInOrder(NodoAVL.getRightChild());
        }
    }
    @Override
    public void Search(String pal){
        Search2(root,pal);
    }
    private void Search2(NodoAVL<T> NodoAVL, String x){
        if(NodoAVL==null){
            System.out.println("No fue posible encontrar la palabra");
            return;
        }
        Search2(NodoAVL.getLeftChild(),x);
        String t=  String.valueOf(NodoAVL.getData());
        String temp[]=t.split("¬",-1);
        if(x==temp[1]){
            System.out.println("En este arbol se encuentra la palabra buscada");
            return;
        }
        Search2(NodoAVL.getRightChild(),x);
    }
    @Override
    public T getMax() {
        if (isEmpty()) {
            return null;
        }
        return getMax(root);
    }

    private T getMax(NodoAVL<T> NodoAVL) {
        if (NodoAVL.getRightChild() != null) {
            return getMax(NodoAVL.getRightChild());
        }
        return NodoAVL.getData();
    }

    @Override
    public T getMin() {
        if (isEmpty()) {
            return null;
        }
        return getMin(root);
    }

    private T getMin(NodoAVL<T> NodoAVL) {
        if (NodoAVL.getLeftChild() != null) {
            return getMin(NodoAVL.getLeftChild());
        }
        return NodoAVL.getData();
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    private NodoAVL<T> applyRotation(NodoAVL<T> NodoAVL) {
        int balance = balance(NodoAVL);
        if (balance > 1) {
            if (balance(NodoAVL.getLeftChild()) < 0) {
                NodoAVL.setLeftChild(rotateLeft(NodoAVL.getLeftChild()));
            }
            return rotateRight(NodoAVL);
        }
        if (balance < -1) {
            if (balance(NodoAVL.getRightChild()) > 0) {
                NodoAVL.setRightChild(rotateRight(NodoAVL.getRightChild()));
            }
            return rotateLeft(NodoAVL);
        }
        return NodoAVL;
    }

    private NodoAVL<T> rotateRight(NodoAVL<T> NodoAVL) {
        NodoAVL<T> leftNodoAVL = NodoAVL.getLeftChild();
        NodoAVL<T> centerNodoAVL = leftNodoAVL.getRightChild();
        leftNodoAVL.setRightChild(NodoAVL);
        NodoAVL.setLeftChild(centerNodoAVL);
        updateHeight(NodoAVL);
        updateHeight(leftNodoAVL);
        return leftNodoAVL;
    }

    private NodoAVL<T> rotateLeft(NodoAVL<T> NodoAVL) {
        NodoAVL<T> rightNodoAVL = NodoAVL.getRightChild();
        NodoAVL<T> centerNodoAVL = rightNodoAVL.getLeftChild();
        rightNodoAVL.setLeftChild(NodoAVL);
        NodoAVL.setRightChild(centerNodoAVL);
        updateHeight(NodoAVL);
        updateHeight(rightNodoAVL);
        return rightNodoAVL;
    }

    private void updateHeight(NodoAVL<T> NodoAVL) {
        int maxHeight = Math.max(
                height(NodoAVL.getLeftChild()),
                height(NodoAVL.getRightChild())
        );
        NodoAVL.setHeight(maxHeight + 1);
    }

    private int balance(NodoAVL<T> NodoAVL) {
        return NodoAVL != null ? height(NodoAVL.getLeftChild()) - height(NodoAVL.getRightChild()) : 0;
    }

    private int height(NodoAVL<T> NodoAVL) {
        return NodoAVL != null ? NodoAVL.getHeight() : 0;
    }
}
//créditos para https://www.youtube.com/watch?v=Jj9Mit24CWk
