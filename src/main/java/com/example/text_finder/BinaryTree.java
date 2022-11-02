package com.example.text_finder;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree<T extends Comparable<T>> implements Tree<T> {
    private NodoBin<T> root;
    public static String textResult = "";
    public static int comparaciones =0;
    private int apariciones=0;
    private String textResultdef;
    private String textResultTemp="";
    private String ultpal="";
    private int pos=0;
    private List<String> ListOrden= new ArrayList<String>();
    private List<String> porOrden = new ArrayList<String>();

    @Override
    public BinaryTree<T> insert(T data) {
        root = insert(data, root);
        return this;
    }
    private NodoBin<T> insert(T data, NodoBin<T> NodoBin) {
        if (NodoBin == null) {
            return new NodoBin<>(data);
        }
        String t = String.valueOf(NodoBin.getData());
        String[] temp = t.split("¬", -1);
        String[] temp2=temp[0].split("~",-1);
        String[] temp3=String.valueOf(data).split("¬", -1);
        String[] temp4= temp3[0].split("~",-1);
        String comparador1="";
        String comparador2="";
        int b = temp2[0].length() - 1;
        String z = String.valueOf(temp2[0].charAt(b));
        String za= String.valueOf(temp2[0].charAt(0));
        int bb= temp4[0].length() - 1;
        String zz = String.valueOf(temp4[0].charAt(bb));
        String zza = String.valueOf(temp4[0].charAt(0));
        if (za.equals("¿") || z.equals("?") || z.equals(",") || z.equals(".") || z.equals(":") || za.equals("¡") || z.equals("!")) {
            String[] tempx = temp2[0].split("[,.;:¿?¡!*]");
            comparador1 = tempx[0];
        }
        else{
            comparador1 = temp2[0];
        }
        if (zza.equals("¿") || zz.equals("?") || zz.equals(",") || zz.equals(".") || zz.equals(":") || zza.equals("¡") || zz.equals("!")) {
            String[] tempxx = temp4[0].split("[,.;:¿?¡!*]");
            comparador2 = tempxx[0];
        }
        else{
            comparador2 = temp4[0];
        }
        if (comparador2.compareTo(comparador1) < 0) {
            NodoBin.setLeftChild(insert(data, NodoBin.getLeftChild()));
        } else if (comparador2.compareTo(comparador1) > 0) {
            NodoBin.setRightChild(insert(data, NodoBin.getRightChild()));
        } else {
            String y =(String.valueOf(NodoBin.getData()));
            y+="°";
            y+= String.valueOf(data);
            NodoBin.setData((T) y);
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
    public void setComparaciones(int num){
        comparaciones =num;
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
        setComparaciones(0);
        String[] phra = pal.split(" ", -1);
        if (phra.length > 1) {
            apariciones = 0;
            int cont1 = 0;
            textResult="";
            textResultTemp="";
            cont1=0;
            SearchP(phra,root,cont1);
            if (apariciones == 0) {
                Server.encontrado = false;
                System.out.println("La frase no se encuentra en el texto");
            } else if (apariciones == 1) {
                Server.encontrado = true;
                System.out.println("Tu frase fue encontrada");
                System.out.println("Es acompañada por las siguientes palabras");
                System.out.println(textResult);
            } else {
                Server.encontrado = true;
                System.out.println("Tu frase fue encontrada. Además, se encuentra más " +
                        "de una vez en el documento");
                System.out.println("Las palabaras que acompañan a tu resultado son las " +
                        "siguientes");
                System.out.println(textResult);
            }
        } else {
            apariciones = 0;
            textResult = "";
            SearchW(pal,root);
            if (apariciones == 0) {
                Server.encontrado = false;
                System.out.println("La palabra no se encuentra en el texto");
            } else if (apariciones == 1) {
                Server.encontrado = true;

                System.out.println("La palabra se encuentra en el texto");
                System.out.println("Las palabras que las acompañan son las siguientes");
                System.out.println(textResult);
            } else {
                Server.encontrado = true;

                System.out.println("La palabra se encuentra en el texto, Además está repetida");
                System.out.println("Las palabras que acompañan a tu búsqueda son las siguientes:");
                System.out.println(textResult);
            }
        }
    }
    private NodoBin<T> SearchW(String pal, NodoBin<T> NodoBin) {
        if (NodoBin == null) {
            return NodoBin;
        }
        String t = String.valueOf(NodoBin.getData());
        String[] temp= t.split("°",-1);
        String[] temp2 = temp[0].split("¬", -1);
        String[] temp3=temp2[0].split("~",-1);
        String comparador="";
        int b = temp3[0].length() - 1;
        String z = String.valueOf(temp3[0].charAt(b));
        if (z.equals("¿") || z.equals("?") || z.equals(",") || z.equals(".") || z.equals(":") || z.equals("¡") || z.equals("!")) {
            String[] tempx = temp3[0].split("[,.;:¿?¡!*]");
            comparador = tempx[0];
        }
        else{
            comparador = temp3[0];
        }
        if (pal.compareTo(comparador) < 0) {
            NodoBin.setLeftChild(SearchW(pal, NodoBin.getLeftChild()));
            comparaciones++;
        } else if (pal.compareTo(comparador) > 0) {
            NodoBin.setRightChild(SearchW(pal, NodoBin.getRightChild()));
            comparaciones++;
        } else {
            comparaciones++;
            apariciones++;
            int length = temp3.length;
            for (int w =0; w < length; w++) {
                textResult+=temp3[w];
                textResult+=" ";
            }
            if(temp.length>1){
                apariciones++;
                for(int uu=1;uu<temp.length;uu++) {
                    textResult+="\n";
                    temp2 = temp[uu].split("¬", -1);
                    temp3 = temp2[0].split("~", -1);
                    for(int pp=0;pp<temp3.length;pp++){
                        textResult+=temp3[pp];
                        textResult+=" ";
                    }
                }
            }
        }
        return NodoBin;
    }
    private NodoBin<T> SearchP(String[] phra, NodoBin<T> NodoBin, int cont1) {
        if (NodoBin == null) {
            return NodoBin;
        }
        String t = String.valueOf(NodoBin.getData());
        String[] temp = t.split("°", -1);
        String[] temp2;
        if ((temp.length) - cont1 > 0) {
            temp2 = temp[cont1].split("¬", -1);
        } else {
            temp2 = temp[0].split("¬", -1);
        }
        String[] temp3 = temp2[0].split("~", -1);
        String comparador = "";
        String comparada = "";
        int b = temp3[0].length() - 1;
        int bb = phra[0].length() - 1;
        String z = String.valueOf(temp3[0].charAt(b));
        String zcom = String.valueOf(phra[0].charAt(bb));
        String zz = String.valueOf(temp3[0].charAt(0));
        String zzcom = String.valueOf(phra[0].charAt(0));

        if (zz.equals("¿") || z.equals("?") || z.equals(",") || z.equals(".") || z.equals(":") || zz.equals("¡") || z.equals("!")) {
            String[] tempx = temp3[0].split("[,.;:¿?¡!*]");
            comparador = tempx[0];
        } else {
            comparador = temp3[0];
        }
        if (zzcom.equals("¿") || zcom.equals("?") || zcom.equals(",") || zcom.equals(".") || zcom.equals(":") || zzcom.equals("¡") || zcom.equals("!")) {
            String[] temp2x = phra[0].split("[,.;:¿?¡!*]");
            comparada = temp2x[0];
        } else {
            comparada = phra[0];
        }
        if (comparada.compareTo(comparador) < 0) {
            NodoBin.setLeftChild(SearchP(phra, NodoBin.getLeftChild(), cont1));
            comparaciones++;
        } else if (comparada.compareTo(comparador) > 0) {
            NodoBin.setRightChild(SearchP(phra, NodoBin.getRightChild(), cont1));
            comparaciones++;
        } else {
            comparaciones++;
            int i = 1;
            int j = Integer.parseInt(temp2[2]) + 1;
            textResultTemp += temp3[0];
            textResultTemp += " ";
            while (i != phra.length) {
                boolean temp4 = false;
                textResultdef = "";
                SearchPAux(phra[i], root, temp4, cont1);
                System.out.println(textResultdef);
                String[] temp5 = textResultdef.split("¬", -1);
                int u = 0;
                boolean imcrying = false;
                while (temp5.length != u) {
                    String[] temp6=temp5[u].split("°",-1);
                    if (temp6[0] != "" && j == Integer.parseInt(temp6[0])) {
                        i += 1;
                        j += 1;
                        imcrying=true;
                        ultpal=temp6[1];
                        break;
                    }
                    else{
                        u++;
                    }

                }
                if(!imcrying){
                    break;
                }

            }
            if (i == phra.length) {
                apariciones++;
                textResult+=(textResultTemp + ultpal);
                if (temp.length > 1 && (temp.length - cont1 - 1) != 0) {
                    cont1++;
                    textResultTemp="";
                    SearchP(phra, root, cont1);

                }
            }

        }
        return NodoBin;
    }

    private NodoBin<T> SearchPAux(String pal, NodoBin<T> NodoBin, boolean y, int cont1) {
        if (!y) {
            if (NodoBin == null) {
                return NodoBin;
            }
            String t = String.valueOf(NodoBin.getData());
            String[] temp = t.split("°", -1);
            String[] temp2;
            if ((temp.length) - cont1 > 0) {
                temp2 = temp[cont1].split("¬", -1);
            } else {
                temp2 = temp[0].split("¬", -1);
            }
            String[] temp3 = temp2[0].split("~", -1);
            String comparador = "";
            String comparada = "";
            int b = temp3[0].length() - 1;
            int bb = pal.length() - 1;
            String z = String.valueOf(temp3[0].charAt(b));
            String zcom = String.valueOf(pal.charAt(bb));
            String zz = String.valueOf(temp3[0].charAt(0));
            String zzcom = String.valueOf(pal.charAt(0));
            if (zz.equals("¿") || z.equals("?") || z.equals(",") || z.equals(".") || z.equals(":") || zz.equals("¡") || z.equals("!")) {
                String[] tempx = temp3[0].split("[,.;:¿?¡!*]");
                comparador = tempx[0];
            } else {
                comparador = temp3[0];
            }
            if (zzcom.equals("¿") || zcom.equals("?") || zcom.equals(",") || zcom.equals(".") || zcom.equals(":") || zzcom.equals("¡") || zcom.equals("!")) {
                String[] temp2x = pal.split("[,.;:¿?¡!*]");
                comparada = temp2x[0];
            } else {
                comparada = pal;
            }
            if (comparada.compareTo(comparador) < 0) {
                NodoBin.setLeftChild(SearchPAux(pal, NodoBin.getLeftChild(), y, cont1));
                comparaciones++;
            } else if (comparada.compareTo(comparador) > 0) {
                NodoBin.setRightChild(SearchPAux(pal, NodoBin.getRightChild(), y, cont1));
                comparaciones++;
            } else {
                if (temp.length > 1) {
                    int w = 0;
                    textResultTemp+=temp3[0];
                    textResultTemp+=" ";
                    while (temp.length != w) {
                        temp2 = temp[w].split("¬", -1);
                        textResultdef += temp2[2];
                        textResultdef +="°";
                        temp3= temp2[0].split("~",-1);
                        textResultdef+=temp3[1];
                        textResultdef+=" ";
                        textResultdef+=temp3[2];
                        textResultdef+=" ";
                        textResultdef+=temp3[3];
                        textResultdef += "¬";
                        w++;
                    }
                } else {
                    textResultTemp += temp3[0];
                    textResultTemp += " ";
                    comparaciones++;
                    textResultdef += temp2[2];
                    textResultdef += " ";
                    String tempz = "";
                    int length = temp3.length;
                    for (int w = 1; w < length; w++) {
                        tempz += temp3[w];
                        tempz += " ";
                    }
                    ultpal = tempz;
                }
                y = true;
            }
        }
        return NodoBin;
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
    public void backtop() {
        textResult = "";
        pos = 0;
        //porOrden=null;
        backtopAux(root);

        int i=0;
        while(i!=porOrden.size()){
            int j=0;
            String added = "";
            while(j!=porOrden.size()){
                String z= porOrden.get(j);
                System.out.println(z);
                String[] temp= z.split("¬",-1);
                System.out.println(temp[2]);
                if (Integer.parseInt(temp[2])==i){
                    added= temp[0];
                    break;
                }
                else{
                    j++;
                }
            }
            ListOrden.add(added);
            i++;
        }
        System.out.println(ListOrden);

    }

    private void backtopAux(NodoBin<T> NodoBin) {
        if (NodoBin == null) {
            return;
        }
        backtopAux(NodoBin.getLeftChild());
        String t = String.valueOf(NodoBin.getData());
        String[] temp=t.split("°",-1);

        if (temp.length>1) {
            for(int w=0;w< temp.length;w++){
                porOrden.add(temp[w]);
            }
        }
        else{
            porOrden.add(t);
        }
        backtopAux(NodoBin.getRightChild());
    }


}
//https://www.youtube.com/watch?v=zIX3zQP0khM


