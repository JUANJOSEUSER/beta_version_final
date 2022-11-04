package com.example.beta_version_1;

import static com.example.beta_version_1.R.menu.menu_inicio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.PasswordAuthentication;

public class MainActivity extends AppCompatActivity {
EditText usuario,contraseña;
Button ingresar;
base_datos admin;
MediaPlayer sonido;
avisos_alerdialog alertas;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        usuario=findViewById(R.id.usuario);
        contraseña=findViewById(R.id.pass);
        sonido=MediaPlayer.create(this,R.raw.sonido);
        admin=new base_datos(this,"bd1",null,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menu_inicio,menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void crear_cuenta_nueva(View view) {
        Intent ventana_cuenta =new Intent(this, com.example.beta_version_1.crear_cuenta.class);
        startActivity(ventana_cuenta);
    }

    public void ingresar(View view) {

            if (sonido!=null){
                sonido.start();
            }
        SQLiteDatabase f=admin.getWritableDatabase();
        Cursor d=f.rawQuery("select nombre,pass from usuario where nombre='"+usuario.getText().toString()+"'and pass='"+contraseña.getText().toString()+"'",null);
        if(d.moveToFirst()){
            ingresar_otra_pantalla();
        }else{
            alertas=new avisos_alerdialog("error en la contraseña o usuario");
        }

    }
    public void ingresar_otra_pantalla(){
        Intent intro=new Intent(this,act_juego.class);
        intro.putExtra("usuario",usuario.getText().toString());
        intro.putExtra("pass",contraseña.getText().toString());
        startActivity(intro);
    }
}