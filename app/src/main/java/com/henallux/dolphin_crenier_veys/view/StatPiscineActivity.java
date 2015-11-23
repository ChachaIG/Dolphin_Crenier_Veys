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

import com.henallux.dolphin_crenier_veys.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StatPiscineActivity extends AppCompatActivity implements View.OnClickListener{

    private Switch anneeeSw;
    private Switch moisSw;
    private Switch saisonSw;
    private TextView recupLaps;
    private SimpleDateFormat dateFormatter;
    private Button totalButt;

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
        totalButt = (Button)findViewById(R.id.buttonTot);
        totalButt.setOnClickListener(this);
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
        if (v.getId() == R.id.recupLaps) {
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    recupLaps.setText(dateFormatter.format(newDate.getTime()));
                }
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            dialog.show();

        }
        if(v.getId() == R.id.buttonTot){
            startActivity(new Intent(StatPiscineActivity.this, ResStatPiscineActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stat_piscine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
