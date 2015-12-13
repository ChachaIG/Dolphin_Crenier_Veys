package com.henallux.dolphin_crenier_veys.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

public class RechActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView recupDateDeb;
    private TextView recupDateFin;
    private SimpleDateFormat dateFormatDeb;
    private SimpleDateFormat dateFormatFin;
    private Button rechBout;
    private Calendar nouvDateDeb;
    private long tpsDateDeb;
    private Calendar nouvDateFin;
    private long tpsDateFin;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editeur;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rech);
        preferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        editeur = preferences.edit();
        init();
    }

    public void init() {
        recupDateDeb = (TextView) findViewById(R.id.recupDateDeb);
        recupDateDeb.setHint(R.string.indiceDate);
        recupDateDeb.setOnClickListener(this);
        recupDateFin = (TextView) findViewById(R.id.recupDateFin);
        recupDateFin.setHint(R.string.indiceDate);
        recupDateFin.setOnClickListener(this);
        rechBout = (Button) findViewById(R.id.boutonRech);
        rechBout.setOnClickListener(this);
    }

    public void onClick(View v) {
        setDateDebRech(v);
        setDateFinRech(v);

        if (v.getId() == R.id.boutonRech) {
            if (nouvDateDeb != null && nouvDateFin != null) {
                intent = new Intent(RechActivity.this, ListRechActivity.class);
                intent.putExtra("dateDeb",dateFormatDeb.format(nouvDateDeb.getTime()));
                intent.putExtra("dateFin",dateFormatFin.format(nouvDateFin.getTime()));
                startActivity(intent);
            }
            else
                Toast.makeText(RechActivity.this, R.string.verifDateAjout, Toast.LENGTH_SHORT).show();
        }

    }

    private void setDateFinRech(View v) {
        if (v.getId() == R.id.recupDateFin) {
            DatePickerDialog dialogFin = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                    dateFormatFin = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

                    nouvDateFin = Calendar.getInstance();

                    nouvDateFin.set(year, monthOfYear, dayOfMonth);

                    if(nouvDateDeb != null)
                         tpsDateDeb = nouvDateDeb.getTimeInMillis();
                    tpsDateFin = nouvDateFin.getTimeInMillis();

                    if (tpsDateFin > tpsDateDeb)
                        recupDateFin.setText(dateFormatFin.format(nouvDateFin.getTime()));
                    else
                        Toast.makeText(RechActivity.this, R.string.verifDateAjout, Toast.LENGTH_LONG).show();
                }
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

            dialogFin.show();
        }
    }

    private void setDateDebRech(View v) {
        if (v.getId() == R.id.recupDateDeb) {
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    dateFormatDeb = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

                    nouvDateDeb = Calendar.getInstance();

                    nouvDateDeb.set(year, monthOfYear, dayOfMonth);

                    if(nouvDateFin != null)
                        tpsDateFin = nouvDateFin.getTimeInMillis();
                    tpsDateDeb = nouvDateDeb.getTimeInMillis();

                    if (tpsDateDeb < tpsDateFin || nouvDateFin == null)
                        recupDateDeb.setText(dateFormatDeb.format(nouvDateDeb.getTime()));
                    else
                        Toast.makeText(RechActivity.this, R.string.verifDateAjout, Toast.LENGTH_LONG).show();

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
                    if (VerificationConnexionInternet.estConnecteAInternet(RechActivity.this)) {
                        startActivity(new Intent(RechActivity.this, RechActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_ajout:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(RechActivity.this)) {
                        startActivity(new Intent(RechActivity.this, AjoutActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_statDiv:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(RechActivity.this)) {
                        startActivity(new Intent(RechActivity.this, StatDivisionActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_statPisc:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(RechActivity.this)) {
                        startActivity(new Intent(RechActivity.this, StatPiscineActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_supp:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(RechActivity.this)) {
                        startActivity(new Intent(RechActivity.this, ListSuppActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_totKm:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(RechActivity.this)) {
                        startActivity(new Intent(RechActivity.this, TotKMActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_totSal:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(RechActivity.this)) {
                        startActivity(new Intent(RechActivity.this, TotSalActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_deconnect:
                editeur.clear();
                editeur.commit();
                startActivity(new Intent(RechActivity.this, ConnexionActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
