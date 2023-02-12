package com.example.beta_version_1;


import static com.example.beta_version_1.R.menu.menu_inicio;
import static com.example.beta_version_1.R.menu.menu_v2;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;



import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import dialogos.avisos_alerdialog;
import dialogos.date_piker;

import random_user.generador;


public class crear_cuenta extends AppCompatActivity {
    EditText usuario, pass, gmail, telefono, fecha;

    avisos_alerdialog alertas;
    FirebaseAuth myAuth;
    ProgressBar progreso;
    Handler handler;
    Runnable runi;
    Timer time;
verificacion verificado=new verificacion();
        FirebaseFirestore registro;
     ProgressBar progresos;
//    String Url="https://randomuser.me/api/";
generador random=new generador();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//esto es para el boton de atras
        usuario = findViewById(R.id.usuario_b);
        pass = findViewById(R.id.contra_b);
        gmail = findViewById(R.id.gmail);
        telefono = findViewById(R.id.telefono);
        fecha = findViewById(R.id.fecha_nacimiento);
        myAuth = FirebaseAuth.getInstance();
        registro=FirebaseFirestore.getInstance();
        progreso=findViewById(R.id.progressBar2);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//para mostrar los menus creados con getmenuinflater
        getMenuInflater().inflate(menu_v2, menu);
        return true;//true para que siempre se muestre en esta clase
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
            case R.id.opciones:
                Intent ventana_cuenta = new Intent(this, configuraciones.class);//es el link que lleva a crear cuentas
                startActivity(ventana_cuenta);
                return true;
            case R.id.salir:
                finish();
                return true;
        }
        return false;
    }
    public void ingresar_otra_pantalla (){
        Intent intro = new Intent(this, modo_juego.class);
        startActivity(intro);
    }

    @Override
    public boolean onSupportNavigateUp() {//aqui se captura el click del boton de atras
        onBackPressed();
        return false;//aqui no hace nada solo devuelve un boolean y se recoge en otros lados
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
        }, 1000 ,100);
    }
    public void crear(View w) {

    String password = pass.getText().toString();
    String gm = gmail.getText().toString();
    if (verificado.validar(usuario,gmail,pass,telefono,fecha)==true){
        myAuth.createUserWithEmailAndPassword(gm, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                boolean pantalla=false;
                progreso();
//                progresos.setVisibility(View.GONE);
                //Si no ha habido problemas (la tarea de registrar el usuario ha sido exitosa: Feedback y redireccion a la MainActivity
                if (task.isSuccessful()) {
                            cloud();
                            FirebaseUser usuario_gmail=myAuth.getCurrentUser();
                            usuario_gmail.sendEmailVerification();
                            mandar_datos();
                            limpiar_formulario();
                            Intent intent = new Intent(crear_cuenta.this, MainActivity.class);
                            startActivity(intent);
                } else {
                    alertas = new avisos_alerdialog("erro al crear la cuenta", "generico");//llamamos a los dialogos para que cuando finalice muestre un mensaje personalizado
                    alertas.show(getFragmentManager(), "dialogo");//


                }
            }
        }
        );
    }
    else{
            Toast.makeText(getApplicationContext(),"error en el formulario", Toast.LENGTH_SHORT ).show();
    }
//

    }


    public void fechas(View view) {
        date_piker date = new date_piker(this, fecha);
    }

public void limpiar_formulario(){
        usuario.setText("");
        gmail.setText("");
        telefono.setText("");
        fecha.setText("");
        pass.setText("");
}
public void cloud(){

    Map<String, Object> user = new HashMap<>();
    user.put("Usuario", usuario.getText().toString());
    user.put("Gmail", gmail.getText().toString());
    user.put("Telefono", telefono.getText().toString());
    user.put("Fecha", fecha.getText().toString());
    user.put("Contraseña", pass.getText().toString());
    user.put("activo",false);

    registro.collection("registros").document(gmail.getText().toString()).set(user);
}

    public void ram_pass(View view) {
        pass.setText(random.generador_password(6));
    }

    public void ram_user(View view) {

        usuario.setText(random.generador_usuario());
    }

    public void mensaje(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Instrucciones de creacion de cuenta").setMessage(" 1:usuario de mas de 6 caracteres minimo un numero \n 2:contraseña con al menos una miniscula y mayuscula y un numero que sea mayor a 8 de longitud \n 3:telefono valido en españa \n 4:gmail existente para su comprobacion por medio de un verificador \n 5:fecha ser mayor a 16 años");

        AlertDialog alert = builder.create();
        alert.show();

    }
    public void mandar_datos(){//cada vez que se inicia seccion se crea un xml donde guardaremos datos en memoria
        SharedPreferences librito=getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);//se coloca el nombre del xml y el context si quiere ser privado o de acceso restringido
        SharedPreferences.Editor libro=librito.edit();//editor hace la funcion de poder escribir en el xml mandadole la clave y el valor
        libro.putString("aviso","si");//mandamos los datos
        libro.commit();
    }
}
