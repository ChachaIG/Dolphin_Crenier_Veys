package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
import com.henallux.dolphin_crenier_veys.controller.ApplicationController;
import com.henallux.dolphin_crenier_veys.dataAccess.Singleton;
import com.henallux.dolphin_crenier_veys.exception.ConnexionException;
import com.henallux.dolphin_crenier_veys.model.Match;
import com.henallux.dolphin_crenier_veys.model.Piscine;
import com.henallux.dolphin_crenier_veys.model.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ResStatPiscineActivity extends AppCompatActivity {
    ///////////////////////////////////////////////////
    /////////////////non terminé///////////////////////
    ///////////////////////////////////////////////////
    private SharedPreferences preferences;
    private SharedPreferences.Editor editeur;
    private ArrayList<Piscine> dataPi = new ArrayList<>();
    private ArrayList<String> libellePiscine = new ArrayList<>();
    private ArrayList<Match> matchs = new ArrayList<>();
    private ArrayList<Match> matchsTri = new ArrayList<>();
    private TableLayout tableau;
    private int lignes;
    private int col = 2;
    private Utilisateur util;
    //Date début pour intervalle
    private Calendar dateMatch = Calendar.getInstance();
    private Calendar dateDeb = Calendar.getInstance();
    private Calendar dateDeb2 = Calendar.getInstance();
    private String dateDebStr;
    private String dateDebStr2;
    //Date fin pour intervalle
    private Calendar dateFin = Calendar.getInstance();
    private String dateFinStr;
    private String dateFinStr2;
    private Calendar dateFin2 = Calendar.getInstance();
    //Date format
    SimpleDateFormat dateFormatddMMyyyy = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    SimpleDateFormat dateFormatyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private ApplicationController ac;
    private int cptMatchParPiscine [] = new int[39];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_stat_piscine);
        Bundle bundle = this.getIntent().getExtras();
        dateDebStr = bundle.getString("dateDeb");
        dateFinStr = bundle.getString("dateFin");
        try {
            dateDeb.setTime(dateFormatddMMyyyy.parse(dateDebStr));
            dateDebStr2 = dateFormatyyyyMMdd.format(dateDeb.getTime());
            dateDeb2.setTime(dateFormatyyyyMMdd.parse(dateDebStr2));
            dateFin.setTime(dateFormatddMMyyyy.parse(dateFinStr));
            dateFinStr2 = dateFormatyyyyMMdd.format(dateFin.getTime());
            dateFin2.setTime(dateFormatyyyyMMdd.parse(dateFinStr2));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        preferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        editeur = preferences.edit();
        util = new Utilisateur(preferences.getInt("idUtil", 0), preferences.getString("prenomUtil", ""), Double.parseDouble(preferences.getString("adrLat", "")), Double.parseDouble(preferences.getString("adrLon", "")));
        init();
    }

    public void init(){
        ac = new ApplicationController();
        initialisationCpt();
        tableau = (TableLayout)findViewById(R.id.idTablePiscine);
        getPiscines();

    }

    public void initialisationCpt(){
        for(int i= 0; i<39;i++){
            cptMatchParPiscine[i] = 0;
        }
    }

    public void getMatchUtilisateur( final TableRow row) {
        final JsonArrayRequest getMatchUtil = new JsonArrayRequest(Request.Method.GET, "http://dolphinapp.azurewebsites.net/api/match?idUUtilisateur=" + util.getIdUtilisateur(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject res = response.getJSONObject(i);
                        Match m = new Match(res.getInt("ID_MATCH"),res.getString("DATE_MATCH"), res.getBoolean("SECOND_MATCH"), res.getInt("ID_UTILISATEUR"), res.getJSONObject("piscine").getInt("ID_PISCINE"), res.getJSONObject("division").getInt("ID_DIVISION"), res.getDouble("DISTANCE"), res.getDouble("COUT"));
                        matchs.add(m);
                    }
                    for (Match m : matchs) {
                        try {
                            dateMatch.setTime(dateFormatyyyyMMdd.parse(m.getDateStr()));
                            m.setDateMatch(dateMatch);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (m.getDateMatch().after(dateDeb) && m.getDateMatch().before(dateFin))
                            matchsTri.add(m);
                    }
                    for (Match m : matchsTri){
                        int idPi = m.getIdPiscine();
                        cptMatchParPiscine[idPi - 1] += 1;
                    }
                    for(int cpt=1; cpt<=39;cpt++) {
                        TextView tv2 = new TextView(ResStatPiscineActivity.this);
                        int id = cptMatchParPiscine[cpt-1];
                        tv2.setText(""+id);
                        row.removeView(tv2);
                        row.addView(tv2);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Match m = new Match();
                matchs.add(m);
            }
        });
        Singleton.getInstance(this).addToRequestQueue(getMatchUtil);
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
                else{
                    getMatchUtilisateur(row);

                }
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
           /* case R.id.ic_statDiv:
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
                }*/
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
            case R.id.ic_deconnect:
                editeur.clear();
                editeur.commit();
                startActivity(new Intent(ResStatPiscineActivity.this, ConnexionActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
