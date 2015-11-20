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

public class AjoutActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView recupDate;
    private SimpleDateFormat dateFormatter;
    private Button ajoutBout;

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

    public void onClick(View v){
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                recupDate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dialog.show();

        switch(v.getId()){
            case R.id.addMatchButton:
                startActivity(new Intent(AjoutActivity.this,ResAjoutActivity.class));
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ajout, menu);
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