package com.henallux.dolphin_crenier_veys.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
import com.henallux.dolphin_crenier_veys.exception.ConnexionException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AjoutActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView recupDate;
    private SimpleDateFormat dateFormatter;
    private Button ajoutBout;
    private Calendar newDate;
    private Calendar currentDate = Calendar.getInstance();
    long timeNewDate;
    long timeCurrent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);
        init();
    }

    public void init(){
        recupDate = (TextView)findViewById(R.id.recupDate);
        recupDate.setHint(R.string.indiceDate);
        recupDate.setOnClickListener(this);
        ajoutBout = (Button)findViewById(R.id.addMatchButton);
        ajoutBout.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.recupDate:
                DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        currentDate.set(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
                        timeCurrent = currentDate.getTimeInMillis();
                        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        timeNewDate = newDate.getTimeInMillis();
                        if(timeNewDate > timeCurrent)
                            recupDate.setText(dateFormatter.format(newDate.getTime()));
                        else
                            Toast.makeText(AjoutActivity.this, R.string.verifDateAjout, Toast.LENGTH_LONG).show();
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                dialog.show();
                break;
            case R.id.addMatchButton:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, ResAjoutActivity.class));
                        break;
                    }

                }catch (ConnexionException ex){
                    ex.msgException();
                }
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
                    if(VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, RechActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_ajout:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, AjoutActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statDiv:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, StatDivisionActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statPisc:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, StatPiscineActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_supp:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, SuppActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totKm:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, TotKMActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totSal:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, TotSalActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
