package com.example.beta_version_1;

import static com.example.beta_version_1.R.menu.menu_inicio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class act_juego extends AppCompatActivity {
    TextView casillas[][] = new TextView[3][3];
    String jugador = "X";
    String vacio = " ";
    MediaPlayer sonido;
    verificacion_juego ganador;
    TextView jugador1, jugador2, letra;
    int contador_partidas = 0;
    PopupMenu flotante;
    FloatingActionButton boton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_juego);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        casillas[0][0] = findViewById(R.id.uno);
        casillas[0][1] = findViewById(R.id.dos);
        casillas[0][2] = findViewById(R.id.tres);
        casillas[1][0] = findViewById(R.id.cuatro);
        casillas[1][1] = findViewById(R.id.cinco);
        casillas[1][2] = findViewById(R.id.seis);
        casillas[2][0] = findViewById(R.id.siete);
        casillas[2][1] = findViewById(R.id.ocho);
        casillas[2][2] = findViewById(R.id.nueve);
        jugador1 = findViewById(R.id.contador_jugador1);
        jugador2 = findViewById(R.id.contador_jugador2);
        boton = findViewById(R.id.menu_flotante);
        letra = findViewById(R.id.letra);
        recuperar_informacion();

//        sonido= MediaPlayer.create(this,R.raw.sonido);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_flotante, menu);
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

    public String sacar_referencias() {//abrimos el archivo xml y sacamos la referencia de usuario
        SharedPreferences referencia = getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);
        return referencia.getString("usuario", null);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<String> lista = new ArrayList<>();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas.length; j++) {
                lista.add(casillas[i][j].getText().toString());
            }

        }
        String puntos[]=new String[2];
        puntos[0]=jugador1.getText().toString();
        puntos[1]=jugador2.getText().toString();
        outState.putStringArray("puntos",puntos);
        outState.putStringArrayList("casilla", lista);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<String> lista = savedInstanceState.getStringArrayList("casilla");
        recuperar_instancia(lista);
        String puntos[]=savedInstanceState.getStringArray("puntos");
        jugador1.setText(puntos[0]);
        jugador2.setText(puntos[1]);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_v2, menu);
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void jugar(View view) {
        TextView casilla = (TextView) view;//lo tansformamos en textview para poder modificarlo

        switch (recuperar_informacion()) {
            case "normal":
                normal(casilla);
                break;
            case "matrix":
                letra.setText(jugador);
                matrix(casilla);
                break;
            case "infinito":
                infinito(casilla);
                letra.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void normal(TextView casilla) {
        ganador = new verificacion_juego(jugador, casillas);//hcaemos la verificaciones  pasandole la letra y las casillas la matriz
        if (casilla.getText().toString().equals("")) {//si la casilla esta vacia entra sino no
            sonidos();//llama a iniciar sonido
            casilla.setText(jugador);//mete la letra en la casilla elegida
            empates();//verificamos empates
            escritura_cambio_de_letras();//cambias de letra
        }
    }

    public void infinito(TextView casilla) {

        ganador = new verificacion_juego(jugador, casillas);//hcaemos la verificaciones  pasandole la letra y las casillas la matriz
        sonidos();//llama a iniciar sonido
        if (ganador.verificacion_modo(jugador, casillas)) {
            Toast.makeText(this, "elije una casilla", Toast.LENGTH_SHORT).show();
            while (ganador.letra(casilla, jugador) == true) {
                Toast.makeText(this, "se ha borrado", Toast.LENGTH_SHORT).show();
                casilla.setText(vacio);
                Toast.makeText(this, "elije otra casilla", Toast.LENGTH_SHORT).show();
            }

        } else {

            casilla.setText(jugador);//mete la letra en la casilla elegida
            escritura_cambio_de_letras();//cambias de letra
            letra.setText(jugador);
        }
        empates();//verificamos empates

    }

    public void matrix(TextView casilla) {
        ganador = new verificacion_juego(jugador, casillas);//hcaemos la verificaciones  pasandole la letra y las casillas la matriz

        if (casilla.getText().toString().equals("")) {//si la casilla esta vacia entra sino no
            sonidos();//llama a iniciar sonido
            casilla.setText(jugador);//mete la letra en la casilla elegida
            empates();//verificamos empates
             escritura_cambio_de_letras();
             if (jugador.equals("O")){
                 matrix_modo(casillas);
                 empates();
                 ganador = new verificacion_juego(jugador, casillas);
                 escritura_cambio_de_letras();//cambias de letra

             }




        }
    }

    public void matrix_modo(TextView[][] casillas) {
        int aux1 = (int) (Math.random() * 3);
        int aux2 = (int) (Math.random() * 3);
        boolean verificador=false;
        while(verificador==false){
            if (casillas[aux1][aux2].getText().toString().equals("")) {
                casillas[aux1][aux2].setText(jugador);
                verificador=true;
            }else{
                aux1 = (int) (Math.random() * 3);
                aux2 = (int) (Math.random() * 3);
            }
        }



    }

    public void empates() {
        int contador = 0;
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas.length; j++) {
                if (!casillas[i][j].getText().toString().equals("")) {
                    contador++;
                } else {
                    contador = 0;
                }
            }
        }
        if (contador >= 9) {
            resetear_tablero();
        }
    }

    public void resetear_tablero() {//resetear el tablero a campos vacios
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas.length; j++) {
                casillas[i][j].setText("");
            }
        }
    }

    public void puntos(String letra) {//le entra una letra y reparte los puntos
        if (letra.equals("X")) {
            int añadir = Integer.parseInt(jugador1.getText().toString()) + 1;
            jugador1.setText(String.valueOf(añadir));
        } else {
            int añadir = Integer.parseInt(jugador2.getText().toString()) + 1;
            jugador2.setText(String.valueOf(añadir));
        }
    }

    public void sonidos() {
        if (sonido != null) {
            sonido.start();
        }
    }

    public void escritura_cambio_de_letras() {//
        if (ganador.verificacion_casillas() == true) {
            puntos(jugador);
            resetear_tablero();
        }
        if (this.jugador.equals("X")) {
            this.jugador = "O";
        } else {
            this.jugador = "X";
        }
    }



    public void menus_flotante_mostrar(View view) {
        flotante = new PopupMenu(this, view);
        flotante.getMenuInflater().inflate(R.menu.menu_flotante, flotante.getMenu());
        flotante.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {//muestra el menu
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {//capturamos el elegido por el usuario
                switch (menuItem.getItemId()) {
                    case R.id.limpiar_tablero:
                        resetear_tablero();
                        break;
                    case R.id.limpiar_puntaje:
                        limpiar_puntuacion();
                        resetear_tablero();
                        break;
                    case R.id.guradar_partida:
                        guardar_en_memoria();
                        break;
                    case R.id.cargar_partida:
                        restaurar_partida();
                        break;
                }
                return false;
            }
        });
        flotante.show();
    }

    public void limpiar_puntuacion() {//limpia puntaje
        jugador1.setText("0");
        jugador2.setText("0");
    }

    public void guardar_en_memoria() {
        AlertDialog.Builder save = new AlertDialog.Builder(this);
        save.setMessage("se va a guardar la partida con el nombre de Partida " + contador_partidas);
        save.setPositiveButton("acceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences librito = getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);
                SharedPreferences.Editor libro = librito.edit();
                libro.putString(String.valueOf(contador_partidas), jugador1.getText().toString() + "-" + jugador2.getText().toString());
                contador_partidas++;
                libro.putString("partidas_guardadas", String.valueOf(contador_partidas));
                libro.commit();
            }

        });
        save.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        save.show();
    }


    public String recuperar_informacion() {
        SharedPreferences librito = getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);
        String aux = librito.getString("modo", null);
        return aux;

    }

    public void recuperar_instancia(ArrayList<String> lista) {
        int aux = 0;
        for (int i = 0; i < lista.size(); i++) {
            for (int j = 0; j < casillas.length; j++) {
                for (int k = 0; k < casillas.length; k++) {
                    if (lista.get(i) != null) {
                        casillas[j][k].setText(lista.get(i));
                        i++;
                    }

                }


            }
//
        }
    }

    public Boolean restaurar_partida() {
        SharedPreferences librito = getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);
        String aux = librito.getString("partidas_guardadas", null);
        int tamaño = Integer.parseInt(aux);
        String[] partida = new String[tamaño];
        for (int i = 0; i < tamaño; i++) {
            partida[i] = librito.getString(String.valueOf(i), null);
        }
        AlertDialog.Builder alerta_partidas_guardadas = new AlertDialog.Builder(this);
        alerta_partidas_guardadas.setTitle("elije la partida a cargar");
        alerta_partidas_guardadas.setSingleChoiceItems(partida, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alerta_partidas_guardadas.setPositiveButton("acceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alerta_partidas_guardadas.show();
        return true;
    }
}