package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
import com.henallux.dolphin_crenier_veys.exception.ConnexionException;

public class ResRechActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editeur;
    private String nomPiscine;
    private double cout;
    private double distance;
    private TextView resRechPi;
    private TextView resRechDist;
    private TextView resRechCout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_rech);
        Bundle bundle = this.getIntent().getExtras();
        nomPiscine = bundle.getString("piscine");
        cout = bundle.getDouble("cout");
        distance = bundle.getDouble("distance");
        preferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        editeur = preferences.edit();
        init();
    }

    public void init(){
        resRechPi = (TextView)findViewById(R.id.resRech2);
        resRechPi.setText(nomPiscine);
        resRechDist = (TextView)findViewById(R.id.resRech4);
        resRechDist.setText(""+distance+" KM");
        resRechCout = (TextView)findViewById(R.id.resRech6);
        resRechCout.setText(""+cout+" euros");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_rech:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResRechActivity.this)) {
                        startActivity(new Intent(ResRechActivity.this, RechActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_ajout:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResRechActivity.this)) {
                        startActivity(new Intent(ResRechActivity.this, AjoutActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statDiv:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResRechActivity.this)) {
                        startActivity(new Intent(ResRechActivity.this, StatDivisionActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statPisc:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResRechActivity.this)) {
                        startActivity(new Intent(ResRechActivity.this, StatPiscineActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_supp:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResRechActivity.this)) {
                        startActivity(new Intent(ResRechActivity.this, ListSuppActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totKm:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResRechActivity.this)) {
                        startActivity(new Intent(ResRechActivity.this, TotKMActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totSal:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResRechActivity.this)) {
                        startActivity(new Intent(ResRechActivity.this, TotSalActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_deconnect:
                editeur.clear();
                editeur.commit();
                startActivity(new Intent(ResRechActivity.this, ConnexionActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
