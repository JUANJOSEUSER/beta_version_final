package com.example.beta_version_1;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class verificacion_juego {

    String letra;
    TextView [][] casillas;

    public verificacion_juego(String letra, TextView[][] casillas) {
        this.letra = letra;
        this.casillas = casillas;
    }
//verificamos filas
    public boolean verificacion_casillas() {
        int contX = 0;
        for (int i = 0; i < casillas.length; i++) {
            contX = 0;
            for (int j = 0; j < casillas[i].length; j++) {

                if (casillas[j][i].getText().toString().equals(this.letra)) {
                    contX++;
                    if (contX == casillas[i].length) {
                        return true;
                    }
                }
            }
        }
        //..............................verificacion 2 columnas................................
        int contX2=0;
        for (int i = 0; i < casillas[0].length; i++) {
            contX2 = 0;
            for (int j = 0; j < casillas.length; j++) {
                if (casillas[i][j].getText().toString().equals(this.letra)) {
                    contX2++;
                    if (contX2 == casillas.length) {
                        System.out.println("GANA EL JUGADOR X!!!");
                        return true;
                    }
                }
            }
        }
//diagonal principal
        int diagonal_pri = 0;
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (i == j) {
                    if (casillas[i][j].getText().toString().equals(this.letra)) {
                        diagonal_pri++;
                        if (diagonal_pri == casillas.length) {
                            return true;

                        }
                    }
//
                }

            }
        }
        //diagonal secundaria
        int diagonal_sec = 0;
        for (int i = 0; i < casillas.length; i++) {

            for (int j = casillas.length - 1; j >= 0; j--) {
                if (i + j == casillas.length - 1) {
                    if (casillas[i][j].getText().toString().equals(this.letra)) {
                        diagonal_sec++;
                        if (diagonal_sec == casillas.length) {
                            return true;

                        }
                    }

                }


            }
        }
        return false;

    }
    public boolean verificacion_modo(String letra, TextView[][] casillas){
        int cont=0;
        for (int i = 0; i <casillas.length; i++) {
            for (int j = 0; j <casillas.length ; j++) {
                if (casillas[i][j].getText().toString().equals(letra)){
                   cont++;
                }
            }
        }
        if (cont==3){
            return true;
        }else{
            return false;
        }
    }
    public boolean letra(TextView casilla,String letra){
        if (casilla.getText().toString().equals(letra)){
            return true;
        }else{
            return false;
        }
    }
}
