package com.example.text_finder;

import java.util.Objects;

public class AVLTree<T extends Comparable<T>> implements Tree<T> {
    private NodoAVL<T> root;
    private boolean r = false;
    private String textResult = "";
    private int comparaciones = 0;
    public AVLTree next = null;

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
        String phra[] = pal.split(" ", -1);
        if (phra.length > 1) {
            r=false;
            SearchP(root,phra);
            if(!r){
                System.out.println("La frase no se encuentra en el texto");
            }
            else{
                System.out.println("La frase se encuentra en el texto");
            }

        } else {
            r = false;
            SearchW(root, pal);
            if (!r) {
                System.out.println("La palabra no se encuentra en el texto");
                Repet(pal);
            } else {
                System.out.println("La palabra se encuentra en el texto");
            }
        }
    }

    private void SearchW(NodoAVL<T> NodoAVL, String x) {
        if (!r) {
            if (NodoAVL == null) {
                return;
            }
            SearchW(NodoAVL.getLeftChild(), x);
            String t = String.valueOf(NodoAVL.getData());
            String temp[] = t.split("¬", -1);
            comparaciones += 1;
            if (Objects.equals(x, String.valueOf(temp[0]))) {
                r = true;
            }
            SearchW(NodoAVL.getRightChild(), x);
        }
    }

    private void SearchP(NodoAVL<T> NodoAVL, String[] x) {
        if (!r) {
            if (NodoAVL == null) {
                return;
            }
            SearchP(NodoAVL.getLeftChild(), x);
            String t = String.valueOf(NodoAVL.getData());
            String temp[] = t.split("¬", -1);
            comparaciones += 1;
            if (Objects.equals(x[0], String.valueOf(temp[0]))) {
                int i = 1;
                int j= Integer.parseInt(temp[2]) + 1;
                while (i != x.length) {
                    boolean temp2=false;
                    textResult="";
                    SearchPAux(root,j,temp2);
                    System.out.println(textResult+" "+x[i]);
                    if (Objects.equals(x[i], textResult)){
                        i += 1;
                        j+=1;
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

    private void SearchPAux(NodoAVL<T> NodoAVL, int x, boolean y) {
        if(!y) {
            if (NodoAVL == null) {
                return;
            }
            SearchPAux(NodoAVL.getLeftChild(), x, y);
            String t = String.valueOf(NodoAVL.getData());
            String temp[] = t.split("¬", -1);
            comparaciones += 1;
            if (Objects.equals(x, Integer.valueOf(temp[2]))) {
                textResult= temp[0];
            }
            SearchPAux(NodoAVL.getRightChild(), x, y);
        }


    }

    @Override
    public void ShowR(String pal) {
        showRAux(root, pal);
        System.out.println(textResult);
    }

    private void showRAux(NodoAVL<T> NodoAVL, String x) {
        int y = 0;
        if (!r) {
            if (NodoAVL == null) {
                return;
            }
            showRAux(NodoAVL.getLeftChild(), x);
            String t = String.valueOf(NodoAVL.getData());
            String temp[] = t.split("¬", -1);
            comparaciones += 1;
            if (Objects.equals(x, String.valueOf(temp[0]))) {
                String textResult = "";
                y = Integer.valueOf(temp[2]);
                textResult += String.valueOf(temp[0]);
                textResult += " ";
                while ((Integer.valueOf(temp[2]) - y) != -3) {
                    boolean temp2 = false;
                    NextWords(root, y, temp2);
                    y += 1;
                }
                r = true;
            }
            showRAux(NodoAVL.getRightChild(), x);
        }
    }

    private void NextWords(NodoAVL<T> NodoAVL, int x, boolean y) {
        if (!y) {
            if (NodoAVL == null) {
                return;
            }
            NextWords(NodoAVL.getLeftChild(), x, y);
            String t = String.valueOf(NodoAVL.getData());
            String temp[] = t.split("¬", -1);
            comparaciones += 1;
            if (Objects.equals(x, Integer.valueOf(temp[2]))) {
                textResult += String.valueOf(temp[0]) + " ";
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

    @Override
    public void Repet(String pal) {
        r = false;
        int cont = 0;
        Repet2(root, pal, cont);
        if (!r) {
            ShowR(pal);
        } else {
            r = false;
            Repet3(root, pal);
            System.out.println(textResult);
        }
    }

    private void Repet2(NodoAVL<T> NodoAVL, String x, int y) {
        if (!r) {
            if (NodoAVL == null) {
                return;
            }
            Repet2(NodoAVL.getLeftChild(), x, y);
            String t = String.valueOf(NodoAVL.getData());
            String temp[] = t.split("¬", -1);
            comparaciones += 1;
            if (Objects.equals(x, String.valueOf(temp[0]))) {
                y += 1;
                if (y > 1) {
                    r = true;
                }
            }
            Repet2(NodoAVL.getRightChild(), x, y);
        }

    }

    private void Repet3(NodoAVL<T> NodoAVL, String x) {
        int y;
        if (NodoAVL == null) {
            return;
        }
        Repet3(NodoAVL.getLeftChild(), x);
        String t = String.valueOf(NodoAVL.getData());
        String[] temp = t.split("¬", -1);
        comparaciones += 1;
        if (Objects.equals(x, String.valueOf(temp[0]))) {
            y = Integer.parseInt(temp[2]);
            while ((Integer.parseInt(temp[2]) - y) != -4) {
                boolean temp2 = false;
                NextWords(root, y, temp2);
                y += 1;
            }
            textResult += "\n";
        }


        Repet3(NodoAVL.getRightChild(), x);
    }

    @Override
    public int getComparaciones() {
        return comparaciones;
    }
}
//créditos para https://www.youtube.com/watch?v=Jj9Mit24CWk
