package com.example.beta_version_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class informacion_usuario extends AppCompatActivity {
    base_datos admin;
    String usuario,pass;
    TextView user,contra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_usuario);
        admin=new base_datos(this,"bd1",null,1);
        Intent datos=new Intent();
        usuario=datos.getStringExtra("usuario");
        pass=datos.getStringExtra("pass");
        user=findViewById(R.id.select_usuario);
        contra=findViewById(R.id.select_pass);
        new informacion_usuario();
    }
    public void informacion_de_usuario(){
        SQLiteDatabase d=admin.getWritableDatabase();
        Cursor resultado=d.rawQuery("Select nombre,pass from usuario='"+usuario+"'and pass='"+pass+"'",null);
        if (resultado.moveToFirst()){
        user.setText(resultado.getString(1));
        contra.setText(resultado.getString(2));
        }
    }
}