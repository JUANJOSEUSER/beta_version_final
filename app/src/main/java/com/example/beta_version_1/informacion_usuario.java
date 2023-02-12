package com.example.beta_version_1;

import static android.content.Intent.CATEGORY_APP_GALLERY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;


public class informacion_usuario extends AppCompatActivity {

    String usuario, pass;
    TextView user, contra, fecha, telefono, gmail;
    FirebaseFirestore registro;
    FirebaseAuth firebase;
    ImageView perfil;
    StorageReference img;


StorageReference storage;
    private static final int CATEGORY_APP_GALLERY=1;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_informacion_usuario);
        user = findViewById(R.id.select_usuario);
        contra = findViewById(R.id.select_pass);
        fecha = findViewById(R.id.select_fecha);
        telefono = findViewById(R.id.select_telefono);
        gmail = findViewById(R.id.select_gmail);
        firebase = FirebaseAuth.getInstance();
        registro = FirebaseFirestore.getInstance();
        storage= FirebaseStorage.getInstance().getReference();
        perfil=findViewById(R.id.imagen_perfil);
        informacion_de_usuario();
        leer();



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }

    public void informacion_de_usuario() {//esto hace una nueva conexion y hace un select el select para android studio es diferente
        registro.collection("registros").document(sacar_referencias()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){{
                    user.setText(documentSnapshot.getString("Usuario"));
                    contra.setText(documentSnapshot.getString("Contraseña"));
                    fecha.setText(documentSnapshot.getString("Fecha"));
                    telefono.setText(documentSnapshot.getString("Telefono"));
                    gmail.setText(documentSnapshot.getString("Gmail"));
                }}
            }
        }

        );
    }

    public String sacar_referencias() {//abrimos el archivo xml y sacamos la referencia de usuario
        SharedPreferences referencia = getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);
        return referencia.getString("usuario", null);
    }
    public void eliminar_cuenta(View s){
        AlertDialog.Builder save = new AlertDialog.Builder(this);
        save.setMessage("se va a eliminar la cuenta estas seguro?");
        save.setPositiveButton("acceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                eliminar_registro();
                eliminar_registro_partidas();
                eliminar_imagen();
                eliminar_cuenta();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

        });
        save.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        save.show();

    }
    public void eliminar_imagen(){
        StorageReference imge = storage.child("foto/".concat(sacar_referencias()));
        imge.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }
    public void cambiar_password(View s){
        String dirrecion = gmail.getText().toString();
        if (!dirrecion.isEmpty()) {
            firebase.setLanguageCode("es");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cambio de contraseña").setMessage("Porfavor ir a su correo y restablecer su contraseña");
            AlertDialog alert = builder.create();
            alert.show();
            firebase.sendPasswordResetEmail(dirrecion).addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                    } else {
                        Toast.makeText(getApplicationContext(), "error en el envio de correo", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, "error vacio", Toast.LENGTH_SHORT).show();
        }
    }
    public void cerrar_sesion(View s){
        firebase.signOut();
        mandar_datos("");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void mandar_datos(String usuario){//cada vez que se inicia seccion se crea un xml donde guardaremos datos en memoria
        SharedPreferences librito=getSharedPreferences("cuenta_informacio", Context.MODE_PRIVATE);//se coloca el nombre del xml y el context si quiere ser privado o de acceso restringido
        SharedPreferences.Editor libro=librito.edit();//editor hace la funcion de poder escribir en el xml mandadole la clave y el valor
            libro.putString("usuario","vacio");//mandamos los datos
        libro.commit();
    }
    public void eliminar_cuenta(){
         firebase.getCurrentUser();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CATEGORY_APP_GALLERY&&resultCode==RESULT_OK){
            Uri uri=data.getData();

            StorageReference file=storage.child("foto/"+gmail.getText().toString());
            file.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    perfil.setImageURI(uri);
                    Toast.makeText(informacion_usuario.this, "se subio", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void subir(View d){
    Intent img=new Intent(Intent.ACTION_PICK);
    img.setType("image/*");
    startActivityForResult(img,CATEGORY_APP_GALLERY);
    }
    public void eliminar_registro(){
        DocumentReference activar=registro.collection("registros").document(gmail.getText().toString());
        activar.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(informacion_usuario.this, "se ha eliminado la cuenta", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(informacion_usuario.this, "error al eliminar la cuenta tu cuenta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void eliminar_registro_partidas(){
        DocumentReference activar=registro.collection("partidas").document(gmail.getText().toString());
        activar.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(informacion_usuario.this, "se ha eliminado la cuenta", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(informacion_usuario.this, "error al eliminar la cuenta tu cuenta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
public void leer(){
    final long ONE_MEGABYTE = 1024 * 1024;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    StorageReference imge = storageReference.child("foto/"+sacar_referencias());
    imge.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
        @Override
        public void onSuccess(byte[] bytes) {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            perfil.setImageBitmap(bmp);

        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
            Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
        }
    });
}


}