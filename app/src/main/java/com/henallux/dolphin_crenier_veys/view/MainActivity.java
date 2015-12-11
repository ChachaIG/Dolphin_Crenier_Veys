package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void onClick(View v) {
        if(v.getId()== R.id.welcomeButton) {
            try {
                if(VerificationConnexionInternet.estConnecteAInternet(MainActivity.this))
                    startActivity(new Intent(MainActivity.this, ConnexionActivity.class));

            }catch (ConnexionException ex){
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
