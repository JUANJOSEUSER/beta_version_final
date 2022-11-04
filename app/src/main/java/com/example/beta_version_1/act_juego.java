package com.example.beta_version_1;

import static com.example.beta_version_1.R.menu.menu_inicio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class act_juego extends AppCompatActivity {
 TextView bon[][]=new TextView[3][3];
String jugador="X";
    MediaPlayer sonido;
    verificacion_juego ganador;
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


        sonido= MediaPlayer.create(this,R.raw.sonido);
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
        if (casillas.getText().toString().equals("#")){
            if (sonido!=null){
                sonido.start();
            }
            casillas.setText(jugador);
            ganador=new verificacion_juego(jugador,bon);
            if (ganador.verificacion_casillas()==true){
                Toast.makeText(this, "gano el "+jugador, Toast.LENGTH_SHORT).show();
                resetear_tablero();
            }
            if (jugador.equals("X")){
                this.jugador="O";
            }else{
                jugador="X";
            }

        }
    }
    public void resetear_tablero(){
        for (int i = 0; i < bon.length ; i++) {
            for (int j = 0; j < bon.length ; j++) {
                bon[i][j].setText("#");
            }
        }
    }
}