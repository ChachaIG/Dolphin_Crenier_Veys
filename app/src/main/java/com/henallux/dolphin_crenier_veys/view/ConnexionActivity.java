package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
import com.henallux.dolphin_crenier_veys.exception.ConnexionException;

public class ConnexionActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText login;
    private EditText motDePasse;
    private Button connexionBouton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClick(View v){
        if(v.getId()== R.id.connexionBouton) {
            try {
                if(VerificationConnexionInternet.estConnecteAInternet(ConnexionActivity.this))
                    startActivity(new Intent(ConnexionActivity.this, MenuActivity.class));

            }catch (ConnexionException ex){
                ex.msgException();
            }
        }

    }

    public void init(){
        connexionBouton = (Button)this.findViewById(R.id.connexionBouton);
        connexionBouton.setOnClickListener(this);
        login = (EditText)this.findViewById(R.id.loginText);
        login.setHint(R.string.login);
        motDePasse = (EditText)this.findViewById(R.id.passwordText);
        motDePasse.setHint(R.string.motDePasse);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();




        return super.onOptionsItemSelected(item);
    }
}
