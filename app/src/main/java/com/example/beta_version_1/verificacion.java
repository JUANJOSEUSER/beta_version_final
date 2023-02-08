package com.example.beta_version_1;

import android.widget.EditText;

public class verificacion {
    String nombre,Gmail,pass,telefono,fecha;
    int cont=0;

    public Boolean validar(EditText nombre, EditText Gmail, EditText pass, EditText telefono, EditText fecha){
        if (nombre.getText().toString().matches("^[A-Za-z]\\w{5,15}$")){
            cont++;
        }else{
            nombre.setError("error");
            return false;
        }
        if(Gmail.getText().toString().matches("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+")&&!Gmail.getText().toString().isEmpty()){
            cont++;
        }else{
            Gmail.setError("error el formato no es el correcto o el campo esta vacio");
            return false;
        }
        if(pass.getText().toString().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,15}$")&&!pass.getText().toString().isEmpty()){
            cont++;
        }else{
            pass.setError("error en el formato de la contraseña o el campo esta vacio");
            return false;
        }
        if(telefono.getText().toString().matches("(\\+34|0034|34)?[ -]*(6|7)[ -]*([0-9][ -]*){8}")&&!telefono.getText().toString().isEmpty()){
            cont++;
        }else{
            telefono.setError("error en elel numero telefonico o el campo esta vacio");
            return false;
        }
        if (!fecha.getText().toString().isEmpty()){
            if (Integer.parseInt(division_fecha(fecha.getText().toString()))<2021&&!fecha.getText().toString().isEmpty()){
                cont++;
            }else{
                fecha.setError("debes ser mayor de 16 y no estar el campo vacio");
                return false;
            }
        }else{
            fecha.setError("el campo vacio");
            return false;
        }


            if (cont==5){
                return true;
            }else{
                return false;
            }

    }
    public String division_fecha(String a){
       String resultado[]=a.split("-");
        String anio=resultado[0];
        return anio;
    }
}
