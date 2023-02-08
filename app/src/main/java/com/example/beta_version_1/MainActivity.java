package com.example.beta_version_1;

import static com.example.beta_version_1.R.menu.menu_inicio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.Touch;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


import java.util.Timer;
import java.util.TimerTask;

import dialogos.avisos_alerdialog;

public class MainActivity extends AppCompatActivity {
    EditText Gmail, contraseña;
    Button ingresar;
    MediaPlayer sonido;
    avisos_alerdialog alertas;
    FirebaseAuth firebase;
    FirebaseFirestore registro;
    ProgressBar progreso;
    Handler handler;
    Runnable runi;
    Timer time;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Gmail = findViewById(R.id.usuario);
        contraseña = findViewById(R.id.pass);
//        sonido=MediaPlayer.create(this,R.raw.sonido);
        firebase = FirebaseAuth.getInstance();
        registro=FirebaseFirestore.getInstance();
        progreso=findViewById(R.id.progressBar);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//mostramos el menu seleccionado
        getMenuInflater().inflate(menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//capturamos el seleccionado por el menu
        switch (item.getItemId()) { // capturamos el que se ha seleccionado con el que coincida se ejecutara
            case R.id.soporte:
                Intent a = new Intent(Intent.ACTION_SEND);
                a.putExtra(Intent.EXTRA_EMAIL, new String[]{"jjmlj10@gmail.com"});
                a.putExtra(Intent.EXTRA_SUBJECT, "Problema o inconveniente");
                a.putExtra(Intent.EXTRA_TEXT, "descripcion del problema");
                a.setType("message/rfc822");
                startActivity(a);
                return true;
            case R.id.conexion:
                ingresar_otra_pantalla();
                mandar_datos("");
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void crear_cuenta_nueva(View view) {
        Intent ventana_cuenta = new Intent(this, crear_cuenta.class);//es el link que lleva a crear cuentas
        startActivity(ventana_cuenta);
    }

    public void ingresar(View view) {

        if (sonido != null) {//cada vez que se da click inicia el sonido
            sonido.start();
        }

        FirebaseUser usuario_gmail=firebase.getCurrentUser();
if (!Gmail.getText().toString().isEmpty()&&!contraseña.getText().toString().isEmpty()){


            firebase.signInWithEmailAndPassword(Gmail.getText().toString(), contraseña.getText().toString()).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                                progreso();
                            if (task.isSuccessful()){
//                                if (usuario_gmail.isEmailVerified()){
                                    mandar_datos(Gmail.getText().toString());
                                ingresar_otra_pantalla();
                                }else{
                                    alertas=new avisos_alerdialog("Email no verificado","generico");
                                    alertas.show(getFragmentManager(),"dialogo");
                                }
//                            }else{
//                                alertas=new avisos_alerdialog("Error usuario no existe o contraseña mal escrita","generico");
//                                alertas.show(getFragmentManager(),"dialogo");
//                            }

                        }
                    }

            );
    }else{
    alertas=new avisos_alerdialog("Campo vacio","generico");
    alertas.show(getFragmentManager(),"dialogo");
    }





    }

    public void ingresar_otra_pantalla (){
        Intent intro = new Intent(this, act_juego.class);
        startActivity(intro);
    }
    public void progreso(){
        progreso.setVisibility(View.VISIBLE);
        handler=new Handler();
        runi=new Runnable() {
            @Override
            public void run() {
                progreso.setVisibility(View.GONE);
                time.cancel();
            }
        };


        time =new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runi);
            }
        }, 10000 ,1000);
    }
    public void mandar_datos(String usuario){//cada vez que se inicia seccion se crea un xml donde guardaremos datos en memoria
        SharedPreferences librito=getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);//se coloca el nombre del xml y el context si quiere ser privado o de acceso restringido
        SharedPreferences.Editor libro=librito.edit();//editor hace la funcion de poder escribir en el xml mandadole la clave y el valor
        if (!Gmail.getText().toString().equals("")){
            libro.putString("usuario",Gmail.getText().toString());//mandamos los datos
        }else{
            libro.putString("usuario","vacio");//mandamos los datos
        }

        libro.commit();
    }
    public void restablecer() {
        firebase.setLanguageCode("es");
        firebase.sendPasswordResetEmail(Gmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()){
                Toast.makeText(MainActivity.this, "se ha enviado el correo para la verificacion", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "error en el envio de correo", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }

    public void restablecer(View view) {
        Intent intro = new Intent(this, cambio_password.class);
        startActivity(intro);
    }
}