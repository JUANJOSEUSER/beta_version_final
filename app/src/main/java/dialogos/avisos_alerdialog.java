package dialogos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Switch;

@SuppressLint("ValidFragment")
public class avisos_alerdialog extends DialogFragment {//clase que se crea los dialogos
    String mensaje;
    String modo;


    public avisos_alerdialog(String mensaje,String modo) {//constructor que le entra el mensaje a mostrar y el modo en que se va
        this.mensaje = mensaje;//a mostrar
        this.modo=modo;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {//con esto creamos el dialogo en la clase para encapsular una clase es con esto
        AlertDialog.Builder alerta=new AlertDialog.Builder(getActivity());
        switch (this.modo){//aqui se elige el modo en que se quiere mostrar los dialogos
            case "generico":
                alerta.setTitle(this.mensaje);
                alerta.setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
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
    public interface respuesta{
        public void confirmar(DialogFragment dialog);
        public void cancelar(DialogFragment dialog);
    }
    respuesta res;
    public void precesar(respuesta a){
        res=a;
    }
}
