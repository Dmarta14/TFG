package com.example.tfg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;



import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_menu_principal);

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
    }
}
