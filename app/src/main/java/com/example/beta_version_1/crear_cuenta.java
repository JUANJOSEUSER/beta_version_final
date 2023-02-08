package com.example.beta_version_1;

import static android.content.ContentValues.TAG;
import static com.example.beta_version_1.R.menu.menu_inicio;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.firestore.v1.WriteResult;

import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;


import dialogos.avisos_alerdialog;
import dialogos.date_piker;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import random_user.generador;


public class crear_cuenta extends AppCompatActivity {
    EditText usuario, pass, gmail, telefono, fecha;
    Intent salida;
    avisos_alerdialog alertas;
    FirebaseAuth myAuth;
verificacion verificado=new verificacion();
        FirebaseFirestore registro;
     ProgressBar progresos;
//    String Url="https://randomuser.me/api/";
generador random=new generador();
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




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//para mostrar los menus creados con getmenuinflater
        getMenuInflater().inflate(menu_inicio, menu);
        return true;//true para que siempre se muestre en esta clase
    }

    @Override
    public boolean onSupportNavigateUp() {//aqui se captura el click del boton de atras
        onBackPressed();
        return false;//aqui no hace nada solo devuelve un boolean y se recoge en otros lados
    }



    public void crear(View w) {

    String password = pass.getText().toString();
    String gm = gmail.getText().toString();
    if (verificado.validar(usuario,gmail,pass,telefono,fecha)==true){
        myAuth.createUserWithEmailAndPassword(gm, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progresos.setVisibility(View.GONE);
                //Si no ha habido problemas (la tarea de registrar el usuario ha sido exitosa: Feedback y redireccion a la MainActivity
                if (task.isSuccessful()) {
                    alertas = new avisos_alerdialog("cuentra creada con exito por favor verifica el correo para poder activar la cuenta", "alerta_opciones");//llamamos a los dialogos para que cuando finalice muestre un mensaje personalizado
                    alertas.show(getFragmentManager(), "dialogo");//
                    Toast.makeText(getApplicationContext(),"Cuenta creada por favor confirmar el correo de verificacion", Toast.LENGTH_SHORT ).show();
                    cloud();
                    FirebaseUser usuario_gmail=myAuth.getCurrentUser();
                    usuario_gmail.sendEmailVerification();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    //Toast.makeText(Register.this,"Se ha producido une error en el proceso de registro.", Toast.LENGTH_SHORT ).show();
                    System.out.println("entro");

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

//    public void respuesta(){
//        OkHttpClient client=new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(Url)
//                .method("GET",null)
//                .build();
//            client.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//
//            }
//        });
//    }
public void cloud(){

    Map<String, Object> user = new HashMap<>();
    user.put("Usuario", usuario.getText().toString());
    user.put("Gmail", gmail.getText().toString());
    user.put("Telefono", telefono.getText().toString());
    user.put("Fecha", fecha.getText().toString());
    user.put("Contraseña", pass.getText().toString());

    registro.collection("registros").document(gmail.getText().toString()).set(user);
}

    public void ram_pass(View view) {
        pass.setText(random.generador_password(10));
    }

    public void ram_user(View view) {

        usuario.setText(random.generador_usuario());
    }
}
