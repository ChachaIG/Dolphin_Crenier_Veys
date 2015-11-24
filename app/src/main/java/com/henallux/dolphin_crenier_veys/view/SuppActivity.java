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

import com.henallux.dolphin_crenier_veys.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SuppActivity extends AppCompatActivity implements View.OnClickListener{

    private Button voirMath;
    private TextView recupDate;
    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supp);
        init();
    }

    public void init(){
        voirMath = (Button)findViewById(R.id.voirMatchButton);
        voirMath.setOnClickListener(this);
        recupDate = (TextView)findViewById(R.id.recupDateSupp);
        recupDate.setHint(R.string.indiceDate);
        recupDate.setOnClickListener(this);
        
    }

    public void onClick(View v){
        if(v.getId()==R.id.voirMatchButton){
            startActivity(new Intent(SuppActivity.this, ListSuppActivity.class));
        }

        if(v.getId()==R.id.recupDateSupp){
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                recupDate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dialog.show();}

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
                startActivity(new Intent(SuppActivity.this, RechActivity.class));
                return true;
            case R.id.ic_ajout:
                startActivity(new Intent(SuppActivity.this, AjoutActivity.class));
                return true;
            case R.id.ic_statDiv:
                startActivity(new Intent(SuppActivity.this, StatDivisionActivity.class));
                return true;
            case R.id.ic_statPisc:
                startActivity(new Intent(SuppActivity.this, StatPiscineActivity.class));
                return true;
            case R.id.ic_supp:
                startActivity(new Intent(SuppActivity.this, SuppActivity.class));
                return true;
            case R.id.ic_totKm:
                startActivity(new Intent(SuppActivity.this, TotKMActivity.class));
                return true;
            case R.id.ic_totSal:
                startActivity(new Intent(SuppActivity.this, TotSalActivity.class));
                return true;

        }


        return super.onOptionsItemSelected(item);
    }
}
