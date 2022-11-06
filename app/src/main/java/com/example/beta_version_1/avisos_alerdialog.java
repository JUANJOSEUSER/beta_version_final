package com.example.beta_version_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Switch;

@SuppressLint("ValidFragment")
public class avisos_alerdialog extends DialogFragment {
    String mensaje;
    String modo;


    public avisos_alerdialog(String mensaje,String modo) {
        this.mensaje = mensaje;
        this.modo=modo;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {//con esto creamos el dialogo en la clase para encapsular una clase es con esto
        AlertDialog.Builder alerta=new AlertDialog.Builder(getActivity());
        switch (this.modo){
            case "generico":
                alerta.setTitle(this.mensaje);
                break;
            case "alerta_opciones":
                alerta.setMessage(this.mensaje);
                alerta.setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        }

        return alerta.create();
    }
}
