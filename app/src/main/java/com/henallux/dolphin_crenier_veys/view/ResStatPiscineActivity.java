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

    private SharedPreferences preferences;
    private SharedPreferences.Editor editeur;
    private ArrayList<Piscine> dataPi = new ArrayList<>();
    private ArrayList<String> libellePiscine = new ArrayList<>();
    private ArrayList<Match> matchs = new ArrayList<>();
    private ArrayList<Match> matchsTri = new ArrayList<>();
    private TableLayout tableau;
    private int lignes;
    private int col = 2;
    private String dateDebStr;
    private String dateFinStr;
    private String date;
    private String dateToParse;
    private Calendar dateDeb = Calendar.getInstance();
    private Calendar dateFin = Calendar.getInstance();
    private Calendar dateMatch = Calendar.getInstance();
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private Utilisateur util;
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
            dateDeb.setTime(dateFormat2.parse(dateDebStr));
            dateFin.setTime(dateFormat2.parse(dateFinStr));
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
        getMatchUtilisateur();
    }

    public void initialisationCpt(){
        for(int i= 0; i<39;i++){
            cptMatchParPiscine[i] = 0;
        }
    }

    public void getMatchUtilisateur() {
        JsonArrayRequest getMatchUtil = new JsonArrayRequest(Request.Method.GET, "http://dolphinapp.azurewebsites.net/api/match?idUUtilisateur=" + util.getIdUtilisateur(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject res = response.getJSONObject(i);
                        date = res.getString("DATE_MATCH");
                        dateToParse = date.substring(0, 10);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        try {
                            dateMatch.setTime(dateFormat.parse(dateToParse));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Match m = new Match(res.getInt("ID_MATCH"), date, res.getBoolean("SECOND_MATCH"), res.getInt("ID_UTILISATEUR"), res.getJSONObject("piscine").getString("NOM_PISCINE"), res.getJSONObject("division").getString("LIBELLE_DIVISION"), res.getDouble("DISTANCE"), res.getDouble("COUT"));
                        matchs.add(m);
                        m.setDateMatch(dateMatch);
                    }
                    for (Match m : matchs) {
                        if (m.getDateMatch().after(dateDeb) && m.getDateMatch().before(dateFin))
                            matchsTri.add(m);
                    }
                    for (Match m : matchsTri){
                            cptMatchParPiscine[m.getIdDivision()-1] += 1;
                    }
                    completerTableau(cptMatchParPiscine,tableau);
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

    public void completerTableau(int[] tabCpt, TableLayout tableau){
        for(int i=0;i<tableau.getChildCount();i++)
        {
            TableRow row = (TableRow)tableau.getChildAt(i);
            for(int j=0;j<2;j++){
                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setPadding(5, 5, 5, 5);
                if(j%2 != 0)
                    tv.setText(""+tabCpt[i]);
                row.addView(tv);
            }
        }
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
            case R.id.ic_deconnect:
                editeur.clear();
                editeur.commit();
                startActivity(new Intent(ResStatPiscineActivity.this, ConnexionActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
