package com.henallux.dolphin_crenier_veys.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
import com.henallux.dolphin_crenier_veys.exception.ConnexionException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StatPiscineActivity extends AppCompatActivity implements View.OnClickListener{

    private Switch anneeeSw;
    private Switch moisSw;
    private Switch saisonSw;
    private TextView recupLaps;
    private SimpleDateFormat dateFormat;
    private Button totalBout;
    private Calendar laps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_piscine);
        init();
    }

    public void init(){

        recupLaps = (TextView)findViewById(R.id.recupLaps);
        recupLaps.setOnClickListener(this);
        recupLaps.setHint(R.string.indiceDate);
        totalBout = (Button)findViewById(R.id.buttonTot);
        totalBout.setOnClickListener(this);
        regroupementDesSwitch();


    }

    private void regroupementDesSwitch() {
        anneeeSw = (Switch)findViewById(R.id.switchAnnee);
        moisSw = (Switch)findViewById(R.id.switchMois);
        saisonSw = (Switch)findViewById(R.id.switchSaison);
        anneeeSw.setChecked(true);
        moisSw.setChecked(false);
        saisonSw.setChecked(false);
        anneeeSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    moisSw.setChecked(false);
                    saisonSw.setChecked(false);
                }
            }
        });

        moisSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    anneeeSw.setChecked(false);
                    saisonSw.setChecked(false);
                }

            }
        });

        saisonSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    anneeeSw.setChecked(false);
                    moisSw.setChecked(false);
                }

            }
        });
    }

    public void onClick(View v) {
        setLapsTemps(v);
        if(v.getId() == R.id.buttonTot){
            if(laps != null)
                 startActivity(new Intent(StatPiscineActivity.this, ResStatPiscineActivity.class));
            else
                Toast.makeText(StatPiscineActivity.this,R.string.verifDateAjout,Toast.LENGTH_SHORT).show();
        }
    }

    private void setLapsTemps(View v) {
        if (v.getId() == R.id.recupLaps) {
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                    laps = Calendar.getInstance();
                    laps.set(year, monthOfYear, dayOfMonth);
                    recupLaps.setText(dateFormat.format(laps.getTime()));
                }
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            dialog.show();

        }
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
                    if(VerificationConnexionInternet.estConnecteAInternet(StatPiscineActivity.this)) {
                        startActivity(new Intent(StatPiscineActivity.this, RechActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_ajout:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(StatPiscineActivity.this)) {
                        startActivity(new Intent(StatPiscineActivity.this, AjoutActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statDiv:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(StatPiscineActivity.this)) {
                        startActivity(new Intent(StatPiscineActivity.this, StatDivisionActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statPisc:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(StatPiscineActivity.this)) {
                        startActivity(new Intent(StatPiscineActivity.this, StatPiscineActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_supp:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(StatPiscineActivity.this)) {
                        startActivity(new Intent(StatPiscineActivity.this, ListSuppActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totKm:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(StatPiscineActivity.this)) {
                        startActivity(new Intent(StatPiscineActivity.this, TotKMActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totSal:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(StatPiscineActivity.this)) {
                        startActivity(new Intent(StatPiscineActivity.this, TotSalActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
