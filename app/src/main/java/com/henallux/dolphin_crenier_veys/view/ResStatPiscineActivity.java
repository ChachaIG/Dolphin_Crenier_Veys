package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
import com.henallux.dolphin_crenier_veys.dataAccess.Singleton;
import com.henallux.dolphin_crenier_veys.exception.ConnexionException;
import com.henallux.dolphin_crenier_veys.model.Piscine;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResStatPiscineActivity extends AppCompatActivity {

    private ArrayList<Piscine> dataPi = new ArrayList<>();
    private ArrayList<String> libellePiscine = new ArrayList<>();
    private TableLayout tableau;
    private int lignes;
    private int col = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_stat_piscine);
        init();
    }

    public void init(){
        tableau = (TableLayout)findViewById(R.id.idTablePiscine);
        getPiscines();
    }

    private void getPiscines() {
        JsonArrayRequest getPisc = new JsonArrayRequest(Request.Method.GET,"http://dolphinapp.azurewebsites.net/api/piscine", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject res =  response.getJSONObject(i);
                        Piscine pi = new Piscine(res.getInt("ID_PISCINE"),res.getString("NOM_PISCINE"),res.getDouble("ADR_LATITUDE"),res.getDouble("ADR_LONGITUDE"));
                        dataPi.add(pi);


                    }
                    for (Piscine p :dataPi) {
                        libellePiscine.add(p.getNom());
                    }
                    lignes = dataPi.size();
                    constructionTableau(lignes, col);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Piscine pi = new Piscine(0,"error",0,0);
                dataPi.add(pi);
            }
        });

        Singleton.getInstance(this).addToRequestQueue(getPisc);
    }
    private void constructionTableau(int ligne, int colonne){

        for (int i = 1; i <= ligne; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));


            for (int j = 1; j <= colonne; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                tv.setPadding(5, 5, 5, 5);
                if(j%2 != 0)
                    tv.setText(libellePiscine.get(i-1));

                row.addView(tv);

            }

            tableau.addView(row);

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
                    if(VerificationConnexionInternet.estConnecteAInternet(ResStatPiscineActivity.this)) {
                        startActivity(new Intent(ResStatPiscineActivity.this, RechActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_ajout:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResStatPiscineActivity.this)) {
                        startActivity(new Intent(ResStatPiscineActivity.this, AjoutActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statDiv:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResStatPiscineActivity.this)) {
                        startActivity(new Intent(ResStatPiscineActivity.this, StatDivisionActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statPisc:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResStatPiscineActivity.this)) {
                        startActivity(new Intent(ResStatPiscineActivity.this, StatPiscineActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_supp:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResStatPiscineActivity.this)) {
                        startActivity(new Intent(ResStatPiscineActivity.this, ListSuppActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totKm:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResStatPiscineActivity.this)) {
                        startActivity(new Intent(ResStatPiscineActivity.this, TotKMActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totSal:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResStatPiscineActivity.this)) {
                        startActivity(new Intent(ResStatPiscineActivity.this, TotSalActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
