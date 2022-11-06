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
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class act_juego extends AppCompatActivity {
 TextView bon[][]=new TextView[3][3];
String jugador="X";
    MediaPlayer sonido;
    verificacion_juego ganador;
    TextView jugador1,jugador2;
    int contador_partidas=0;
PopupMenu flotante;
FloatingActionButton boton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_juego);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bon[0][0]=findViewById(R.id.uno);
        bon[0][1]=findViewById(R.id.dos);
        bon[0][2]=findViewById(R.id.tres);
        bon[1][0]=findViewById(R.id.cuatro);
        bon[1][1]=findViewById(R.id.cinco);
        bon[1][2]=findViewById(R.id.seis);
        bon[2][0]=findViewById(R.id.siete);
        bon[2][1]=findViewById(R.id.ocho);
        bon[2][2]=findViewById(R.id.nueve);
        jugador1=findViewById(R.id.contador_jugador1);
        jugador2=findViewById(R.id.contador_jugador2);
        boton=findViewById(R.id.menu_flotante);
        recuperar_informacion();
        sonido= MediaPlayer.create(this,R.raw.sonido);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_flotante,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_usuario:
                Intent informacion=new Intent(this,informacion_usuario.class);
                startActivity(informacion);
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menu_inicio,menu);
        getMenuInflater().inflate(R.menu.menu_usuario,menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void jugar(View view) {
        TextView casillas=(TextView) view;
        ganador=new verificacion_juego(jugador,bon);
        if (casillas.getText().toString().equals("")){
            sonidos();
            casillas.setText(jugador);
            empates();
            escritura_cambio_de_letras();
        }
    }
    public void empates(){
        int contador=0;
        for (int i = 0; i < bon.length ; i++) {
            for (int j = 0; j < bon.length ; j++) {
                if (!bon[i][j].getText().toString().equals("")){
                    contador++;
                }else{
                    contador=0;
                }
            }
        }
        if (contador>=9){
            resetear_tablero();
        }
    }

    public void resetear_tablero(){
        for (int i = 0; i < bon.length ; i++) {
            for (int j = 0; j < bon.length ; j++) {
                bon[i][j].setText("");
            }
        }
    }
    public void puntos(String letra){
        if (letra.equals("X")){
            int añadir=Integer.parseInt(jugador1.getText().toString())+1;
            jugador1.setText(String.valueOf(añadir));
        }else{
            int añadir=Integer.parseInt(jugador2.getText().toString())+1;
            jugador2.setText(String.valueOf(añadir));
        }
    }
    public void sonidos(){
        if (sonido!=null){
            sonido.start();
        }
    }
    public void escritura_cambio_de_letras(){
        if (ganador.verificacion_casillas()==true){
            puntos(jugador);
            resetear_tablero();
        }
        if (jugador.equals("X")){
            this.jugador="O";
        }else{
            jugador="X";
        }
    }

    public void menus_flotante_mostrar(View view) {
    flotante=new PopupMenu(this,view);
    flotante.getMenuInflater().inflate(R.menu.menu_flotante,flotante.getMenu());
    flotante.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
           switch (menuItem.getItemId()){
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
           }return false;
        }
    });
    flotante.show();
    }
    public void limpiar_puntuacion(){
        jugador1.setText("0");
        jugador2.setText("0");
    }
 public void guardar_en_memoria(){
     AlertDialog.Builder save=new AlertDialog.Builder(this);
     save.setMessage("se va a guardar la partida con el nombre de Partida "+contador_partidas);
     save.setPositiveButton("acceptar",new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {
             SharedPreferences librito=getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);
             SharedPreferences.Editor libro=librito.edit();
             libro.putString(String.valueOf(contador_partidas),jugador1.getText().toString()+"-"+jugador2.getText().toString());
             contador_partidas++;
             libro.putString("partidas_guardadas",String.valueOf(contador_partidas));
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
    public void recuperar_informacion(){
        SharedPreferences librito=getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);
        String aux=librito.getString("partidas_guardadas",null);
        if (aux!=null){
            contador_partidas=Integer.parseInt(aux);
        }

    }
}