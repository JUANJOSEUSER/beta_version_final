package com.example.beta_version_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class crear_cuenta extends AppCompatActivity {
base_datos admin;
EditText usuario,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        usuario=findViewById(R.id.usuario_b);
        pass=findViewById(R.id.contra_b);
        admin=new base_datos(this,"bd1",null,1);
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
        d.insert("usuario",null,valores);
        d.close();
    }
}