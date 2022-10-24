package com.example.text_finder;

import java.util.Objects;

public class BinaryTree<T extends Comparable<T>> implements Tree<T> {
    private NodoBin<T> root;
    private Boolean r = false;
    private String textResult = "";
    private int comparaciones=0;

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
        } else {
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
    public int getComparaciones() {
        return comparaciones;
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
        SearchW(root, pal);
        if (!r) {
            System.out.println("La palabra no se encuentra en el texto");
        } else {
            System.out.println("La palabra se encuentra en el texto");
        }
    }

    private void SearchW(NodoBin<T> NodoBin, String x) {

        if (!r) {
            if (NodoBin == null) {
                return;
            }
            SearchW(NodoBin.getLeftChild(), x);
            String t = String.valueOf(NodoBin.getData());
            String temp[] = t.split("¬", -1);
            comparaciones+=1;
            if (Objects.equals(x, String.valueOf(temp[0]))) {
                r = true;
            }
            SearchW(NodoBin.getRightChild(), x);
        }

    }

    @Override
    public void ShowR(String pal) {
        showRAux(root, pal);
        System.out.println(textResult);
    }

    private void showRAux(NodoBin<T> NodoBin, String x) {
        int y = 0;
        if (!r) {
            if (NodoBin == null) {
                return;
            }
            showRAux(NodoBin.getLeftChild(), x);
            String t = String.valueOf(NodoBin.getData());
            String temp[] = t.split("¬", -1);
            comparaciones+=1;
            if (Objects.equals(x, String.valueOf(temp[0]))) {
                String textResult = "";
                y = Integer.valueOf(temp[2]);
                textResult += String.valueOf(temp[0]);
                textResult += " ";
                while ((Integer.valueOf(temp[2]) - y) != -4) {
                    boolean temp2 = false;
                    NextWords(root, y, temp2);
                    y += 1;
                }
                r = true;
            }
            showRAux(NodoBin.getRightChild(), x);
        }
    }

    private void NextWords(NodoBin<T> NodoBin, int x, boolean y) {
        if (!y) {
            if (NodoBin == null) {
                return;
            }
            NextWords(NodoBin.getLeftChild(), x, y);
            String t = String.valueOf(NodoBin.getData());
            String temp[] = t.split("¬", -1);
            comparaciones+=1;
            if (Objects.equals(x, Integer.valueOf(temp[2]))) {
                textResult += String.valueOf(temp[0]) + " ";
                y = true;
            }

            NextWords(NodoBin.getRightChild(), x, y);
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

    @Override
    public void Repet(String pal) {
        r = false;
        int cont = 0;
        Repet2(root, pal, cont);
        if (!r) {
            ShowR(pal);
        } else {
            r = false;
            Repet3(root,pal);
            System.out.println(textResult);
        }
    }

    private void Repet2(NodoBin<T> NodoBin, String x, int y) {
        if (!r) {
            if (NodoBin == null) {
                return;
            }
            Repet2(NodoBin.getLeftChild(), x, y);
            String t = String.valueOf(NodoBin.getData());
            String temp[] = t.split("¬", -1);
            comparaciones+=1;
            if (Objects.equals(x, String.valueOf(temp[0]))) {
                y += 1;
                if (y > 1) {
                    r = true;
                }
            }
            Repet2(NodoBin.getRightChild(), x, y);
        }

    }

    private void Repet3(NodoBin<T> NodoBin, String x) {
        int y;
        if (NodoBin == null) {
            return;
        }
        Repet3(NodoBin.getLeftChild(), x);
        String t = String.valueOf(NodoBin.getData());
        String[] temp = t.split("¬", -1);
        comparaciones+=1;
        if (Objects.equals(x, String.valueOf(temp[0]))) {
            y = Integer.parseInt(temp[2]);
            while ((Integer.parseInt(temp[2]) - y) != -4) {
                boolean temp2 = false;
                NextWords(root, y, temp2);
                y += 1;
            }
            textResult+="\n";
        }
        Repet3(NodoBin.getRightChild(), x);
    }


}
//https://www.youtube.com/watch?v=zIX3zQP0khM


