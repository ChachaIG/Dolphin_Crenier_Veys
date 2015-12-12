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
import com.henallux.dolphin_crenier_veys.model.Division;
import android.widget.TableRow.LayoutParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResStatDivisionActivity extends AppCompatActivity {

    private ArrayList<Division> dataDiv = new ArrayList<>();
    private ArrayList<String> libelleDivision = new ArrayList<>();
    private TableLayout tableau;
    private int lignes;
    private int col = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_stat_division);
        init();
    }

    public void init(){
        tableau = (TableLayout)findViewById(R.id.idTableDiv);
        getDivisions();




    }

    private void getDivisions() {


        JsonArrayRequest getDiv = new JsonArrayRequest(Request.Method.GET,"http://dolphinapp.azurewebsites.net/api/division", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject res =  response.getJSONObject(i);
                        Division di = new Division(res.getInt("ID_DIVISION"),res.getString("NOM_DIVISION"),res.getString("LIBELLE_DIVISION"));
                        dataDiv.add(di);


                    }
                    for (Division d :dataDiv) {
                        libelleDivision.add(d.getNomDivision());
                    }
                    lignes = dataDiv.size();
                    constructionTableu(lignes, col);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Division di = new Division(0,"error","error");
                dataDiv.add(di);
            }
        });

        Singleton.getInstance(this).addToRequestQueue(getDiv);
    }
    private void constructionTableu(int ligne, int colonne){

        for (int i = 1; i <= ligne; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));


            for (int j = 1; j <= colonne; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));

                tv.setPadding(5, 5, 5, 5);
                if(j%2 != 0)
                    tv.setText(libelleDivision.get(i-1));

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
                    if(VerificationConnexionInternet.estConnecteAInternet(ResStatDivisionActivity.this)) {
                        startActivity(new Intent(ResStatDivisionActivity.this, RechActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_ajout:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResStatDivisionActivity.this)) {
                        startActivity(new Intent(ResStatDivisionActivity.this, AjoutActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statDiv:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResStatDivisionActivity.this)) {
                        startActivity(new Intent(ResStatDivisionActivity.this, StatDivisionActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statPisc:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResStatDivisionActivity.this)) {
                        startActivity(new Intent(ResStatDivisionActivity.this, StatPiscineActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_supp:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResStatDivisionActivity.this)) {
                        startActivity(new Intent(ResStatDivisionActivity.this, ListSuppActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totKm:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResStatDivisionActivity.this)) {
                        startActivity(new Intent(ResStatDivisionActivity.this, TotKMActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totSal:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResStatDivisionActivity.this)) {
                        startActivity(new Intent(ResStatDivisionActivity.this, TotSalActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_deconnect:
                startActivity(new Intent(ResStatDivisionActivity.this, ConnexionActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
