package com.example.beta_version_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class modo_juego extends AppCompatActivity {
    TextView intruccion;
    boolean aux = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_juego);
        intruccion = findViewById(R.id.como_jugar);
    }

    public void mandar_datos(String modo) {//cada vez que se inicia seccion se crea un xml donde guardaremos datos en memoria
        SharedPreferences librito = getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);//se coloca el nombre del xml y el context si quiere ser privado o de acceso restringido
        SharedPreferences.Editor libro = librito.edit();//editor hace la funcion de poder escribir en el xml mandadole la clave y el valor
        if (modo.equals("normal")) {
            libro.putString("modo", modo);//mandamos los datos
        }
        if (modo.equals("matrix")) {
            libro.putString("modo", modo);//mandamos los datos
        }
        if (modo.equals("infinito")) {
            libro.putString("modo", modo);//mandamos los datos
        }

        libro.commit();
    }

    public void normal(View view) {
        mandar_datos("normal");
        ingresar_otra_pantalla();
    }

    public void matrix(View view) {
        mandar_datos("matrix");
        ingresar_otra_pantalla();
    }

    public void infinito(View view) {
        mandar_datos("infinito");
        ingresar_otra_pantalla();
    }

    public void ingresar_otra_pantalla() {
        Intent intro = new Intent(this, act_juego.class);
        startActivity(intro);
    }

    public void instrucciones(View view) {
        if (aux == false) {
            intruccion.setVisibility(View.VISIBLE);
            aux = true;
        } else {
            intruccion.setVisibility(View.INVISIBLE);
            aux = false;
        }
    }
}