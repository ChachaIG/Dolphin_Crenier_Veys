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

import com.henallux.dolphin_crenier_veys.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RechActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView recupDateDeb;
    private TextView recupDateFin;
    private SimpleDateFormat dateFormatterDeb;
    private SimpleDateFormat dateFormatterFin;
    private Button rechButton;
    private Calendar newDateDeb;
    private long timeDateDeb;
    private Calendar newDateFin;
    private long timeDateFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rech);
        init();
    }

    public void init(){
        recupDateDeb = (TextView)findViewById(R.id.recupDateDeb);
        recupDateDeb.setHint(R.string.indiceDate);
        recupDateDeb.setOnClickListener(this);
        recupDateFin = (TextView)findViewById(R.id.recupDateFin);
        recupDateFin.setHint(R.string.indiceDate);
        recupDateFin.setOnClickListener(this);
        rechButton = (Button)findViewById(R.id.buttonRech);
        rechButton.setOnClickListener(this);
    }

    public void onClick(View v){
        if(v.getId()==R.id.recupDateDeb) {
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    dateFormatterDeb = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

                    newDateDeb = Calendar.getInstance();

                    newDateDeb.set(year, monthOfYear, dayOfMonth);

                    recupDateDeb.setText(dateFormatterDeb.format(newDateDeb.getTime()));

                }
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }

        if(v.getId()==R.id.recupDateFin) {
            DatePickerDialog dialogFin = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                    dateFormatterFin = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

                    newDateFin = Calendar.getInstance();

                    newDateFin.set(year, monthOfYear, dayOfMonth);

                    timeDateDeb = newDateDeb.getTimeInMillis();
                    timeDateFin =newDateFin.getTimeInMillis();

                    if(timeDateFin > timeDateDeb)
                        recupDateFin.setText(dateFormatterFin.format(newDateFin.getTime()));
                    else
                        Toast.makeText(RechActivity.this, R.string.verifDateAjout, Toast.LENGTH_LONG).show();
                }
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

            dialogFin.show();
        }

        if(v.getId()==R.id.buttonRech){
            startActivity(new Intent(RechActivity.this, ListRechActivity.class));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case R.id.ic_rech:
                startActivity(new Intent(RechActivity.this, RechActivity.class));
                return true;
            case R.id.ic_ajout:
                startActivity(new Intent(RechActivity.this, AjoutActivity.class));
                return true;
            case R.id.ic_statDiv:
                startActivity(new Intent(RechActivity.this, StatDivisionActivity.class));
                return true;
            case R.id.ic_statPisc:
                startActivity(new Intent(RechActivity.this, StatPiscineActivity.class));
                return true;
            case R.id.ic_supp:
                startActivity(new Intent(RechActivity.this, SuppActivity.class));
                return true;
            case R.id.ic_totKm:
                startActivity(new Intent(RechActivity.this, TotKMActivity.class));
                return true;
            case R.id.ic_totSal:
                startActivity(new Intent(RechActivity.this, TotSalActivity.class));
                return true;

        }


        return super.onOptionsItemSelected(item);
    }
}
