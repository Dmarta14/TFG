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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        FirebaseFirestore db = FirebaseFirestore.getInstance ();
        Button acceder = findViewById(R.id.Acceder);
        TextView registrar = findViewById(R.id.Registrarse);
        registrar.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), Registro1.class);
            startActivity(intent);

        });
        acceder.setOnClickListener ( v -> {
            String correo = ((EditText) findViewById(R.id.CorreoLogin)).getText().toString();
            String password = ((EditText) findViewById(R.id.PasswordLogin)).getText().toString();

            if (correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.e("Prueba", "Entra aqui?");

            db.collection ("usuario")
                    .whereEqualTo ("email", correo)
                    .whereEqualTo ("pass", password)
                    .get ()
                    .addOnCompleteListener ( task -> {
                        Log.e("Prueba", "Entra aqui?");
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