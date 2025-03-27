package com.example.tfg;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AnadirJugador extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_jugadores);
        FirebaseFirestore db = FirebaseFirestore.getInstance ();
        String correoEntrenador  = (String) getIntent ().getSerializableExtra ("CorreoEntrenador");
        BottomNavigationView bottomNavigationView = findViewById (R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener (new BottomNavigationView.OnItemSelectedListener () {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int itemId = item.getItemId();

                if (itemId == R.id.nav_inicio) {
                    selectedFragment = new FragmentInicio();
                } else if (itemId == R.id.nav_chat) {
                    selectedFragment = new FragmentChat();
                } else if (itemId == R.id.nav_calendario) {
                    selectedFragment = new FragmentCalendario();
                } else if (itemId == R.id.nav_equipo) {
                    selectedFragment = new FragmentEquipo();
                } else if (itemId == R.id.nav_menu) {
                    selectedFragment = new FragmentMenu();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager ().beginTransaction ()
                            .replace (R.id.fragment_container, selectedFragment)
                            .commit ();
                }

                return true;
            }
        });

        // Mostrar el primer fragmento al iniciar la app
        if (savedInstanceState == null) {
            getSupportFragmentManager ().beginTransaction ()
                    .replace (R.id.fragment_container, new FragmentInicio ())
                    .commit ();
        }


        EditText nombreJugador  = findViewById (R.id.NombreJugador);
        EditText correo =  findViewById (R.id.Correo);
        EditText posicion  = findViewById (R.id.Posicion);
        EditText fechaCumple =  findViewById (R.id.Fecha);
        Button guardar = findViewById (R.id.guardar);

        guardar.setOnClickListener (v -> {
            String email = correo.getText ().toString ();
            String nombre = nombreJugador.getText ().toString ();
            String posi = posicion.getText ().toString ();
            String cumple = fechaCumple.getText ().toString ();

            if (email.isEmpty() || nombre.isEmpty() || posi.isEmpty() || cumple.isEmpty ()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new user with a first, middle, and last name
            Map<String, Object> jugador = new HashMap<> ();
            jugador.put("nombre", nombre);
            jugador.put( "correo" ,  email);
            jugador.put("posicion" , posi);
            jugador.put( "cumpleanos", cumple);
            jugador.put("entrenador" , correoEntrenador);

           // Comprobar si el correo ya existe en la colección "usuario"
            db.collection("usuario")
                    .whereEqualTo("correo", email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot> () {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (!task.getResult().isEmpty()) {
                                    // El correo existe en la colección "usuario", agregar el nuevo documento a la colección "jugador"
                                    db.collection("jugador")
                                            .add(jugador)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener () {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                }
                                            });
                                } else {
                                    // El correo no existe en la colección "usuario"
                                    Log.d(TAG, "El correo no existe en la colección 'usuario'");
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });

        });
    }
}
