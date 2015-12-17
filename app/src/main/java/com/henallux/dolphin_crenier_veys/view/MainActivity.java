package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
import com.henallux.dolphin_crenier_veys.exception.ConnexionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bienvenueBout;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editeur;
    private Boolean estConnecte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        editeur = preferences.edit();
        init();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.welcomeButton) {
            try {
                if (VerificationConnexionInternet.estConnecteAInternet(MainActivity.this)) {
                    estConnecte = preferences.getBoolean("sauvConnexion", false);
                    if (estConnecte)
                        startActivity(new Intent(MainActivity.this, MenuActivity.class));
                    else
                        startActivity(new Intent(MainActivity.this, ConnexionActivity.class));
                }
            } catch (ConnexionException ex) {
                ex.msgException();
            }
        }
    }

    public void init() {
        bienvenueBout = (Button) this.findViewById(R.id.welcomeButton);
        bienvenueBout.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    protected void onDestroy() {
        if (preferences.getBoolean("sauvConnexion", false)) {
            super.onDestroy();
        } else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            super.onDestroy();

        }
    }
}