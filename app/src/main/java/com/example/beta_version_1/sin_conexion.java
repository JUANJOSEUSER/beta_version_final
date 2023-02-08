package com.example.beta_version_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class sin_conexion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_conexion);
    }

    public void inicio_sesion(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void crear_cuenta_p1(View view) {
        Intent ventana_cuenta = new Intent(this, crear_cuenta.class);//es el link que lleva a crear cuentas
        startActivity(ventana_cuenta);
    }

}