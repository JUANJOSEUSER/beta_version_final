package com.example.beta_version_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

@SuppressLint("ValidFragment")
public class avisos_alerdialog extends DialogFragment {
    String mensaje;


    public avisos_alerdialog(String mensaje) {
        this.mensaje = mensaje;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {//con esto creamos el dialogo en la clase para encapsular una clase es con esto
        AlertDialog.Builder alerta=new AlertDialog.Builder(getActivity());
        alerta.setTitle(this.mensaje);
        return alerta.create();
    }
}
