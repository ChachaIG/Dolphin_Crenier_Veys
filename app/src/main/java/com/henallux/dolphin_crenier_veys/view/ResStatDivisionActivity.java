package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.henallux.dolphin_crenier_veys.R;

public class ResStatDivisionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_stat_division);
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
                startActivity(new Intent(ResStatDivisionActivity.this, RechActivity.class));
                return true;
            case R.id.ic_ajout:
                startActivity(new Intent(ResStatDivisionActivity.this, AjoutActivity.class));
                return true;
            case R.id.ic_statDiv:
                startActivity(new Intent(ResStatDivisionActivity.this, StatDivisionActivity.class));
                return true;
            case R.id.ic_statPisc:
                startActivity(new Intent(ResStatDivisionActivity.this, StatPiscineActivity.class));
                return true;
            case R.id.ic_supp:
                startActivity(new Intent(ResStatDivisionActivity.this, SuppActivity.class));
                return true;
            case R.id.ic_totKm:
                startActivity(new Intent(ResStatDivisionActivity.this, TotKMActivity.class));
                return true;
            case R.id.ic_totSal:
                startActivity(new Intent(ResStatDivisionActivity.this, TotSalActivity.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
