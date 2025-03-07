package com.example.tfg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class Login extends AppCompatActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView mail = findViewById(R.id.UsuarioIni);
        TextView contrasena1 = findViewById(R.id.PasswordIni);
        TextView registrar = findViewById(R.id.Registrarse);


        Button iniciarSesion = findViewById(R.id.Acceder);
        iniciarSesion.setOnClickListener(v -> {
            String email = mail.getText().toString();
            String contra1 = contrasena1.getText().toString();
            obtenerUsuario(email, contra1);
        });

        registrar.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), Registro1.class);
            startActivity(intent);

        });
    }


    public void obtenerUsuario(String usuario, String contra) {
        Data param = new Data.Builder()
                .putString("param", "ExisteUsuarioContra")
                .putString("mail", usuario)
                .putString("contrasena", contra).build();
        Log.d("Prueba inicio", "" + param);
        OneTimeWorkRequest oneTimeWorkRequest =
                new OneTimeWorkRequest.Builder(BD.class).setInputData(param).build();
        WorkManager.getInstance(Login.this).enqueue(oneTimeWorkRequest);
        WorkManager.getInstance(Login.this)
                .getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(Login.this, workInfo -> {
                    if (workInfo != null && workInfo.getState().isFinished()) {
                        if (workInfo.getState() != WorkInfo.State.SUCCEEDED) {
                            Toast.makeText(getApplicationContext(), "ERROR",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Data d = workInfo.getOutputData();
                            boolean b = d.getBoolean("existe", false);

                            if (b) {
                                Toast.makeText(getApplicationContext(), "existe un " +
                                        "usuario", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),
                                        MenuPrincipal.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                                saveSession(usuario);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder(Login.this);
                                builder.setTitle("Usuario o contraseña incorrectos");
                                builder.setMessage("Introduce el usuario o contraseña " +
                                        "correctamente o registrese en caso de no tener" +
                                        " un usuario creado");
                                builder.setPositiveButton("Volver", (dialogInterface,
                                                                     i) -> {
                                    Intent intent = new Intent(getApplicationContext(),
                                            Login.class);
                                    startActivity(intent);
                                });
                                AlertDialog alert = builder.create();
                                alert.show();


                            }
                        }
                    }
                });
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        FirebaseFirestore db = FirebaseFirestore.getInstance ();
        Button acceder = findViewById(R.id.Acceder);

        acceder.setOnClickListener ( v -> {
            String correo = ((EditText) findViewById(R.id.CorreoLogin)).getText().toString();
            String password = ((EditText) findViewById(R.id.PasswordLogin)).getText().toString();

            if (correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            db.collection ("usuario")
                    .whereEqualTo ("email", correo)
                    .whereEqualTo ("pass", password)
                    .get ()
                    .addOnCompleteListener ( task -> {

                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            // Usuario encontrado -> Ir a pantalla de inicio
                            Intent intent = new Intent(this, MenuPrincipal.class);
                            startActivity(intent);
                            finish();  // Cierra la pantalla de login
                        } else {
                            // Usuario no encontrado -> Mostrar mensaje de error
                            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error al conectar con la base de datos", Toast.LENGTH_SHORT).show());

        });

       }
    public void saveSession(String mail) {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(openFileOutput("config.txt",
                            Context.MODE_PRIVATE));
            outputStreamWriter.write(mail);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e);
        }
    }
}