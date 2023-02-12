package dialogos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class date_piker {//clase para mostrar el date piker
String resultado;
    public  date_piker(Context a, EditText f) { //en el propio constructor le entra el context que es como el getclass y un edittext al que quieres modificar
        DatePickerDialog fecha=new DatePickerDialog(a, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int dia, int mes, int año) {
                f.setText(String.valueOf(dia+"-"+mes+"-"+año));
            }
        },2005,1,1);//esto es el dafaul del date piker
        fecha.show();//aqui lo mostramos y caprutamos el elegido con el ondateset

    }
}
