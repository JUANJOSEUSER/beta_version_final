package com.example.beta_version_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;

public class configuraciones extends AppCompatActivity {
    Spinner spinner;
    int defaul= Color.BLACK;
    int defaul2=Color.BLACK;
    TextView letrax,letrao;
    String[] lenguaje = {"Español", "Ingles"};
    int[] banderas = {R.drawable.esspainflag_111796, R.drawable.usunitedstatesflag_111929};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);
        spinner = findViewById(R.id.spinner);
        letrao=findViewById(R.id.letrao);
        letrax=findViewById(R.id.letrax);
        paises_adaptador pais = new paises_adaptador();
        spinner.setAdapter(pais);
        defaul= ContextCompat.getColor(this,R.color.black);

    }

    public void confirmar(View view) {
        if (spinner.getSelectedItem().toString().equals("Ingles")) {
            ingles();
            aviso();
            recargar();

        } else {
            español();
            aviso();
            recargar();
        }


    }
public void elegir_color(View c){
    AmbilWarnaDialog color=new AmbilWarnaDialog(this, defaul, new AmbilWarnaDialog.OnAmbilWarnaListener() {
        @Override
        public void onCancel(AmbilWarnaDialog dialog) {
        }
        @Override
        public void onOk(AmbilWarnaDialog dialog, int color) {
            defaul=color;
            letrao.setTextColor(color);
        }
    });
    color.show();
}
    public void elegir_color2(View c){
        AmbilWarnaDialog color=new AmbilWarnaDialog(this, defaul, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaul2=color;
                letrax.setTextColor(color);

            }
        });
        color.show();
    }
    public void ingles() {
        Resources res = configuraciones.this.getResources();
        DisplayMetrics en = res.getDisplayMetrics();
        android.content.res.Configuration enf = res.getConfiguration();
        enf.locale = new Locale("en");
        res.updateConfiguration(enf, en);
    }

    public void español() {
        Resources res = configuraciones.this.getResources();
        DisplayMetrics en = res.getDisplayMetrics();
        android.content.res.Configuration enf = res.getConfiguration();
        enf.locale = new Locale("");
        res.updateConfiguration(enf, en);
    }

    public void cancelar(View view) {
        Intent ventana_cuenta = new Intent(this, MainActivity.class);//es el link que lleva a crear cuentas
        startActivity(ventana_cuenta);
    }
    public void recargar(){
        Intent ventana_cuenta = new Intent(this, MainActivity.class);//es el link que lleva a crear cuentas
        startActivity(ventana_cuenta);
        this.finish();
    }

    class paises_adaptador extends BaseAdapter {

        @Override
        public int getCount() {
            return lenguaje.length;
        }

        @Override
        public Object getItem(int i) {
            return lenguaje[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(configuraciones.this);
            view = inflater.inflate(R.layout.paises, null);
            ImageView v1 = view.findViewById(R.id.banderitas);
            TextView v2 = view.findViewById(R.id.texto_paises);
            v1.setImageResource(banderas[i]);
            v2.setText(lenguaje[i]);
            return view;
        }
    }
    public void aviso(){//cada vez que se inicia seccion se crea un xml donde guardaremos datos en memoria
        SharedPreferences librito=getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);//se coloca el nombre del xml y el context si quiere ser privado o de acceso restringido
        SharedPreferences.Editor libro=librito.edit();//editor hace la funcion de poder escribir en el xml mandadole la clave y el valor
        libro.putInt("colorX",defaul2);
        libro.putInt("colorO",defaul);
        libro.commit();
    }
}