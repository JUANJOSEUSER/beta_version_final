package com.example.beta_version_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class informacion_usuario extends AppCompatActivity {

    String usuario, pass;
    TextView user, contra, fecha, telefono, gmail;
    FirebaseFirestore registro;
    FirebaseAuth myAuth;

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
        myAuth = FirebaseAuth.getInstance();
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

}