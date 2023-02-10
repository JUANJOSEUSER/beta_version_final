package com.example.beta_version_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_usuario:
                if (sacar_referencias().equals("vacio")) {
                    Intent informacion = new Intent(this, sin_conexion.class);
                    startActivity(informacion);
                } else {
                    Intent informacion = new Intent(this, informacion_usuario.class);
                    startActivity(informacion);
                }

                return true;
            case R.id.salir://apenas implementado
                finish();
                return true;
            case R.id.soporte:
                Intent a = new Intent(Intent.ACTION_SEND);
                a.setData(Uri.parse("mailto:"));
                a.putExtra(Intent.EXTRA_EMAIL, new String[]{"jjmlj10@gmail.com"});
                a.putExtra(Intent.EXTRA_SUBJECT, "Problema o inconveniente");
                a.putExtra(Intent.EXTRA_TEXT, "descripcion del problema");
                a.setType("message/rfc822");
                startActivity(a);
                return true;
            case R.id.opciones:
                Intent ventana_cuenta = new Intent(this, configuraciones.class);//es el link que lleva a crear cuentas
                startActivity(ventana_cuenta);
                return true;
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_v2, menu);
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return true;
    }
    public String sacar_referencias() {//abrimos el archivo xml y sacamos la referencia de usuario
        SharedPreferences referencia = getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);
        return referencia.getString("usuario", null);
    }
}