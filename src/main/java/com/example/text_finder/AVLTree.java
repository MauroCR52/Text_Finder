package com.example.text_finder;

import java.util.Objects;

public class AVLTree<T extends Comparable<T>> implements Tree<T> {
    private NodoAVL<T> root;
    private boolean r = false;
    private String textResult = "";
    private int comparaciones = 0;
    private String ultpal;
    private int pos = 0;
    public AVLTree next = null;
    private int apariciones=0;

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
            NodoAVL.setRightChild(insert(data, NodoAVL.getRightChild()));


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
    public void Search(String pal) {
        String[] phra = pal.split(" ", -1);
        if (phra.length > 1) {
            r = false;
            SearchP(root, phra);
            if (!r) {
                System.out.println("La frase no se encuentra en el texto");
            } else {
                ShowRP(pal);
                System.out.println(textResult);
            }

        } else {
            apariciones=0;
            textResult="";
            SearchW(root, pal);
            if (apariciones==0) {
                System.out.println("La palabra no se encuentra en el texto");
            }
            else if(apariciones==1){
                System.out.println("La palabra se encuentra en el texto");
                System.out.println("Las palabras que las acompañan son las siguientes");
                System.out.println(textResult);
            }
            else {
                System.out.println("La palabra se encuentra en el texto, Además está repetida");
                System.out.println("Las palabras que acompañan a tu búsqueda son las siguientes:");
                System.out.println(textResult);
            }
        }
    }

    private void SearchW(NodoAVL<T> NodoAVL, String x) {
        int y = 0;
        if (NodoAVL == null) {
            return;
        }
        SearchW(NodoAVL.getLeftChild(), x);
        String t = String.valueOf(NodoAVL.getData());
        String[] temp = t.split("¬", -1);
        comparaciones ++;
        int b = temp[0].length() - 1;
        String z = String.valueOf(temp[0].charAt(b));
        if (z.equals("¿") || z.equals("?") || z.equals(",") || z.equals(".") || z.equals(":") || z.equals("¡") || z.equals("!")) {
            String[] tempx = temp[0].split("[,.;:¿?¡!*]");
            if (Objects.equals(x, String.valueOf(tempx[0]))) {
                y = Integer.parseInt(temp[2]);
                while ((Integer.parseInt(temp[2]) - y) != -5) {
                    boolean temp2 = false;
                    NextWords(root, y, temp2);
                    y += 1;

                }
                textResult+="\n";
                apariciones++;
            }
        }

        if (Objects.equals(x, String.valueOf(temp[0]))) {
            y = Integer.parseInt(temp[2]);
            while ((Integer.parseInt(temp[2]) - y) != -5) {
                boolean temp2 = false;
                NextWords(root, y, temp2);
                y += 1;
            }
            apariciones++;
            textResult+="\n";
        }
        SearchW(NodoAVL.getRightChild(), x);
    }

    private void SearchP(NodoAVL<T> NodoAVL, String[] x) {
        if (!r) {
            if (NodoAVL == null) {
                return;
            }
            SearchP(NodoAVL.getLeftChild(), x);
            String t = String.valueOf(NodoAVL.getData());
            String[] temp = t.split("¬", -1);
            comparaciones += 1;
            if (Objects.equals(x[0], String.valueOf(temp[0]))) {
                int i = 1;
                int j = Integer.parseInt(temp[2]) + 1;
                while (i != x.length) {
                    boolean temp2 = false;
                    textResult = "";
                    SearchPAux(root, j, temp2);
                    if (Objects.equals(x[i], textResult)) {
                        i += 1;
                        j += 1;
                    } else {
                        r = false;
                        break;
                    }
                    r = true;
                }
            }
            SearchP(NodoAVL.getRightChild(), x);
        }
    }

    //Muestra las palabras que le siguen a la palabra que coincidió
    private void SearchPAux(NodoAVL<T> NodoAVL, int x, boolean y) {
        if (!y) {
            if (NodoAVL == null) {
                return;
            }
            SearchPAux(NodoAVL.getLeftChild(), x, y);
            String t = String.valueOf(NodoAVL.getData());
            String[] temp = t.split("¬", -1);
            comparaciones += 1;
            if (Objects.equals(x, Integer.valueOf(temp[2]))) {
                textResult = temp[0];
                ultpal = temp[2];
            }
            SearchPAux(NodoAVL.getRightChild(), x, y);
        }


    }

    @Override
    public void ShowR(String pal) {

    }

    private void showWRAux(NodoAVL<T> NodoAVL, String x) {

    }

    private void ShowRP(String pal) {
        textResult = "";
        int y = Integer.parseInt(ultpal) + 1;
        textResult += pal;
        textResult += " ";
        while ((Integer.parseInt(ultpal) - y) != -6) {
            boolean temp2 = false;
            NextWords(root, y, temp2);
            y += 1;

        }
    }

    private void NextWords(NodoAVL<T> NodoAVL, int x, boolean y) {
        if (!y) {
            if (NodoAVL == null) {
                return;
            }
            NextWords(NodoAVL.getLeftChild(), x, y);
            String t = String.valueOf(NodoAVL.getData());
            String[] temp = t.split("¬", -1);
            comparaciones += 1;
            if (Objects.equals(x, Integer.valueOf(temp[2]))) {
                textResult += (temp[0]) + " ";
                y = true;
            }

            NextWords(NodoAVL.getRightChild(), x, y);
        }
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
            //System.out.println(balance(NodoAVL.getLeftChild()));
            if (balance(NodoAVL.getLeftChild()) < 0) {
                NodoAVL.setLeftChild(rotateLeft(NodoAVL.getLeftChild()));
            }
            return rotateRight(NodoAVL);
        }
        if (balance < -1) {
            //System.out.println(balance(NodoAVL.getRightChild()));
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
    public void Repet(String x){

    }
    /*
    @Override
    public void Repet(String pal) {
        r = false;
        int cont = 0;
        String[] phra = pal.split(" ", -1);
        if (phra.length > 1) {
            r = false;
            RepetP(root, phra, cont);
            if (!r) {
                ShowRP(pal);
            } else {
                r = false;
                RepetP2(root, pal);
                System.out.println(textResult);
            }
        } else {
            RepetW(root, pal, cont);
            if (!r) {
                ShowR(pal);
            } else {
                r = false;
                RepetW2(root, pal);
                System.out.println(textResult);
            }
        }
    }

     */

    private void RepetW(NodoAVL<T> NodoAVL, String x, int y) {

    }

    private void RepetW2(NodoAVL<T> NodoAVL, String x) {
    
    }

    @Override
    public int getComparaciones() {
        return comparaciones;
    }

    @Override
    public void backtop() {
        textResult = "";
        pos = 0;
        backtopAux(root);
        System.out.println(textResult);
    }

    private void backtopAux(NodoAVL<T> NodoAVL) {
        if (NodoAVL == null) {
            return;
        }
        backtopAux(NodoAVL.getLeftChild());
        String t = String.valueOf(NodoAVL.getData());
        String[] temp = t.split("¬", -1);
        comparaciones += 1;
        if (pos == Integer.parseInt(temp[2])) {
            textResult += String.valueOf(temp[0]);
            textResult += " ";
            pos++;
            backtopAux(root);
        }
        backtopAux(NodoAVL.getRightChild());
    }
}
//créditos para https://www.youtube.com/watch?v=Jj9Mit24CWk
