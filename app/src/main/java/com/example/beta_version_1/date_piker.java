package com.example.beta_version_1;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class date_piker {
String resultado;
    public  date_piker(Context a, EditText f) {
        DatePickerDialog fecha=new DatePickerDialog(a, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int dia, int mes, int año) {
                f.setText(String.valueOf(dia+"-"+mes+"-"+año));
            }
        },2022,0,1);
        fecha.show();

    }
}
