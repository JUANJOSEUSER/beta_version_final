package com.example.beta_version_1;

import static com.example.beta_version_1.R.menu.menu_inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class crear_cuenta extends AppCompatActivity {
base_datos admin;
EditText usuario,pass,gmail,telefono,fecha;
Intent salida;
avisos_alerdialog alertas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        usuario=findViewById(R.id.usuario_b);
        pass=findViewById(R.id.contra_b);
        gmail=findViewById(R.id.gmail);
        telefono=findViewById(R.id.telefono);
        fecha=findViewById(R.id.fecha_nacimiento);
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

    public void enviar_datos(View view) {
        SQLiteDatabase d=admin.getWritableDatabase();
        ContentValues valores=new ContentValues();
        valores.put("nombre",usuario.getText().toString());
        valores.put("pass",pass.getText().toString());
        valores.put("gmail",gmail.getText().toString());
        valores.put("telefono",telefono.getText().toString());
        valores.put("fecha",fecha.getText().toString());
        d.insert("usuario",null,valores);
        d.close();
        alertas=new avisos_alerdialog("cuentra creada con exito","generico");
        alertas.show(getFragmentManager(),"dialogo");
        salida=new Intent(this,MainActivity.class);
        startActivity(salida);
        finish();
    }

    public void fechas(View view) {
        date_piker a=new date_piker(this,fecha);

    }
}