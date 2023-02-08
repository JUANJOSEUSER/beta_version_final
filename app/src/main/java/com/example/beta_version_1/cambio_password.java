package com.example.beta_version_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.core.View;

public class cambio_password extends AppCompatActivity {
    FirebaseAuth firebase;
    EditText Gmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//esto es para el boton de atras
        firebase=FirebaseAuth.getInstance();
        Gmail = findViewById(R.id.gmail_cambio);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void restablecer(android.view.View view) {

        String dirrecion = Gmail.getText().toString();
        if (!dirrecion.isEmpty()) {
           firebase.setLanguageCode("es");
            firebase.sendPasswordResetEmail(dirrecion).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(cambio_password.this, "se ha enviado el correo para la verificacion", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(cambio_password.this, "error en el envio de correo", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, "error vacio", Toast.LENGTH_SHORT).show();
        }

    }

}