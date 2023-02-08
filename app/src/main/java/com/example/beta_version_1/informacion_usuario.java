package com.example.beta_version_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class informacion_usuario extends AppCompatActivity {

    String usuario, pass;
    TextView user, contra, fecha, telefono, gmail;
    FirebaseFirestore registro;
    FirebaseAuth firebase;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_informacion_usuario);
        user = findViewById(R.id.select_usuario);
        contra = findViewById(R.id.select_pass);
        fecha = findViewById(R.id.select_fecha);
        telefono = findViewById(R.id.select_telefono);
        gmail = findViewById(R.id.select_gmail);
        firebase = FirebaseAuth.getInstance();
        registro = FirebaseFirestore.getInstance();
        informacion_de_usuario();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void informacion_de_usuario() {//esto hace una nueva conexion y hace un select el select para android studio es diferente
        registro.collection("registros").document(sacar_referencias()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){{
                    user.setText(documentSnapshot.getString("Usuario"));
                    contra.setText(documentSnapshot.getString("Contraseña"));
                    fecha.setText(documentSnapshot.getString("Fecha"));
                    telefono.setText(documentSnapshot.getString("Telefono"));
                    gmail.setText(documentSnapshot.getString("Gmail"));
                }}
            }
        }

        );
    }

    public String sacar_referencias() {//abrimos el archivo xml y sacamos la referencia de usuario
        SharedPreferences referencia = getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);
        return referencia.getString("usuario", null);
    }
    public void eliminar_cuenta(View s){

    }
    public void cambiar_password(View s){
        Intent intro = new Intent(this, cambio_password.class);
        startActivity(intro);
    }
    public void cerrar_sesion(View s){
        firebase.signOut();
        mandar_datos("");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void mandar_datos(String usuario){//cada vez que se inicia seccion se crea un xml donde guardaremos datos en memoria
        SharedPreferences librito=getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);//se coloca el nombre del xml y el context si quiere ser privado o de acceso restringido
        SharedPreferences.Editor libro=librito.edit();//editor hace la funcion de poder escribir en el xml mandadole la clave y el valor
            libro.putString("usuario","vacio");//mandamos los datos
        libro.commit();
    }
}