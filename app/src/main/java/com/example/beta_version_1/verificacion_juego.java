package com.example.beta_version_1;

import android.widget.TextView;

public class verificacion_juego {

    String letra;
    TextView [][] casillas;

    public verificacion_juego(String letra, TextView[][] casillas) {
        this.letra = letra;
        this.casillas = casillas;
    }

    public boolean verificacion_casillas(){
        int contX = 0;
        int contO = 0;
        for (int i = 0; i < casillas.length; i++) {
             contX = 0;
           contO = 0;
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].getText().toString().equals("X")) {
                    contX++;
                    if (contX == casillas[i].length) {
                        System.out.println("GANA EL JUGADOR X!!!");
                        return true;
                    }
                }
                if (casillas[i][j].getText().toString().equals("O")) {
                    contO++;
                    if (contO == casillas[i].length) {
                        System.out.println("GANA EL JUGADOR O!!!");
                        return true;
                    }
                }
            }
        }
        //..............................verificacion 2................................
        int contX2=0;
        int contO2=0;
        for (int i = 0; i < casillas[0].length; i++) {
            contX2 = 0;
            contO2 = 0;
            for (int j = 0; j < casillas.length; j++) {
                if (casillas[i][j].getText().toString().equals("X")) {
                    contX2++;
                    if (contX2 == casillas.length) {
                        System.out.println("GANA EL JUGADOR X!!!");
                        return true;
                    }
                }
                if (casillas[i][j].getText().toString().equals("O")) {
                    contO2++;
                    if (contO2 == casillas.length) {
                        System.out.println("GANA EL JUGADOR O!!!");
                        return true;
                    }
                }
            }
        }

        int diagonal_pri = 0;
        int diagonal_pri2 = 0;
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (i==j) {

                    if (casillas[i][j].getText().toString().equals("X")) {
                        diagonal_pri++;
                        if (diagonal_pri==casillas.length) {
                            System.out.println("gana x");
                            return true;

                        }
                    }
                    if (casillas[i][j].getText().toString().equals("O")) {
                        diagonal_pri2++;
                        if (diagonal_pri2==casillas.length) {
                            System.out.println("gana o");
                            return true;
                        }
                    }
                }

            }
        }
        int diagonal_seg=0;
        int diagonal_seg2=0;
        for (int i = casillas.length-1; i >= 0 ; i--) {
            for (int j = 0; j < casillas.length; j++) {
                if (casillas[i][j].getText().toString().equals("X")) {
                    diagonal_seg++;
                    if (diagonal_seg==3) {
                        System.out.println("gano x");
                        return true;
                    }
                }
                if (casillas[i][j].getText().toString().equals("O")) {
                    diagonal_seg2++;
                    if (diagonal_seg2==3) {
                        System.out.println("gano o");
                        return true;
                    }

                }
            }
        }
        return false;
    }

}
