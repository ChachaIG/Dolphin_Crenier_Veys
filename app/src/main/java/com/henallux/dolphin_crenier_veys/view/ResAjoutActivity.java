package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
import com.henallux.dolphin_crenier_veys.exception.ConnexionException;
import com.henallux.dolphin_crenier_veys.model.Piscine;

public class ResAjoutActivity extends AppCompatActivity {

    private Piscine piscine = new Piscine();
    private TextView resPiscine;
    private TextView distance;
    private String distAffichage;
    private TextView cout;
    private String coutAffichage;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_ajout);
        preferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        editeur = preferences.edit();
        init();
    }

    public void init() {

        Bundle bundle = this.getIntent().getExtras();
        piscine.setId(bundle.getInt("IdLieu"));
        piscine.setNom(bundle.getString("NomPiscine"));
        piscine.setAdrLatitude(bundle.getDouble("AdrLat"));
        piscine.setAdrLongitutde(bundle.getDouble("AdrLon"));
        resPiscine = (TextView) findViewById(R.id.resAjout2);
        resPiscine.setText(piscine.getNom());
        distance = (TextView)findViewById(R.id.resAjout4);
        distAffichage = ""+bundle.getDouble("distance")+" KM";
        distance.setText(distAffichage);
        cout=(TextView)findViewById(R.id.resAjout6);
        coutAffichage = ""+bundle.getDouble("cout");
        cout.setText(coutAffichage.substring(0, 5) + " euros");


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
                    if (VerificationConnexionInternet.estConnecteAInternet(ResAjoutActivity.this)) {
                        startActivity(new Intent(ResAjoutActivity.this, RechActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_ajout:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ResAjoutActivity.this)) {
                        startActivity(new Intent(ResAjoutActivity.this, AjoutActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_statDiv:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ResAjoutActivity.this)) {
                        startActivity(new Intent(ResAjoutActivity.this, StatDivisionActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_statPisc:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ResAjoutActivity.this)) {
                        startActivity(new Intent(ResAjoutActivity.this, StatPiscineActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_supp:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ResAjoutActivity.this)) {
                        startActivity(new Intent(ResAjoutActivity.this, ListSuppActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_totKm:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ResAjoutActivity.this)) {
                        startActivity(new Intent(ResAjoutActivity.this, TotKMActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_totSal:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ResAjoutActivity.this)) {
                        startActivity(new Intent(ResAjoutActivity.this, TotSalActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_deconnect:
                editeur.clear();
                editeur.commit();
                startActivity(new Intent(ResAjoutActivity.this, ConnexionActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
