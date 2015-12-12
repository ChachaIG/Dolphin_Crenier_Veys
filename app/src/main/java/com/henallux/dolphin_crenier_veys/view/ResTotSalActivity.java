package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
import com.henallux.dolphin_crenier_veys.exception.ConnexionException;

public class ResTotSalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_tot_sal);
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
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotSalActivity.this)) {
                        startActivity(new Intent(ResTotSalActivity.this, RechActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_ajout:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotSalActivity.this)) {
                        startActivity(new Intent(ResTotSalActivity.this, AjoutActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statDiv:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotSalActivity.this)) {
                        startActivity(new Intent(ResTotSalActivity.this, StatDivisionActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statPisc:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotSalActivity.this)) {
                        startActivity(new Intent(ResTotSalActivity.this, StatPiscineActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_supp:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotSalActivity.this)) {
                        startActivity(new Intent(ResTotSalActivity.this, ListSuppActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totKm:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotSalActivity.this)) {
                        startActivity(new Intent(ResTotSalActivity.this, TotKMActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totSal:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotSalActivity.this)) {
                        startActivity(new Intent(ResTotSalActivity.this, TotSalActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_deconnect:
                startActivity(new Intent(ResTotSalActivity.this, ConnexionActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
