package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.henallux.dolphin_crenier_veys.model.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ResTotSalActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editeur;
    private int switchSelect;
    private Utilisateur util;
    private ApplicationController ac;
    private TextView resTotSal2;
    private TextView resTotSal4;
    private ArrayList<Match> matchs = new ArrayList<>();
    private ArrayList<Match> matchsTri = new ArrayList<>();
    private double cout;
    private String coutStr;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_tot_sal);
        Bundle bundle = this.getIntent().getExtras();
        switchSelect = bundle.getInt("switchSelect");
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
        resTotSal2 = (TextView)findViewById(R.id.resTotSal2);
        resTotSal4 = (TextView)findViewById(R.id.resTotSal4);
        switch (switchSelect){
            case 1: resTotSal2.setText(""+dateDeb.get(Calendar.YEAR));
                break;
            case 2: resTotSal2.setText(ac.getNomDuMois(dateDeb)+" "+dateDeb.get(Calendar.YEAR));
                break;
            case 3: resTotSal2.setText(getString(R.string.saison)+" "+dateDeb.get(Calendar.YEAR)+"/"+dateFin.get(Calendar.YEAR));
                break;
        }
        getMatchUtilisateur();
    }

    public void getMatchUtilisateur() {
        JsonArrayRequest getMatchUtil = new JsonArrayRequest(Request.Method.GET, "http://dolphinapp.azurewebsites.net/api/match?idUUtilisateur=" + util.getIdUtilisateur(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject res = response.getJSONObject(i);
                        Match m = new Match(res.getInt("ID_MATCH"),res.getString("DATE_MATCH"), res.getBoolean("SECOND_MATCH"), res.getInt("ID_UTILISATEUR"), res.getJSONObject("piscine").getString("NOM_PISCINE"), res.getJSONObject("division").getString("LIBELLE_DIVISION"), res.getDouble("DISTANCE"), res.getDouble("COUT"));
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
                        cout += m.getCout();
                    }
                    coutStr =""+cout;
                    resTotSal4.setText(""+coutStr.substring(0,5)+" euros");
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
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotSalActivity.this)) {
                        startActivity(new Intent(ResTotSalActivity.this, RechActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_ajout:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotSalActivity.this)) {
                        startActivity(new Intent(ResTotSalActivity.this, AjoutActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            /*case R.id.ic_statDiv:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotSalActivity.this)) {
                        startActivity(new Intent(ResTotSalActivity.this, StatDivisionActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statPisc:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotSalActivity.this)) {
                        startActivity(new Intent(ResTotSalActivity.this, StatPiscineActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }*/
            case R.id.ic_supp:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotSalActivity.this)) {
                        startActivity(new Intent(ResTotSalActivity.this, ListSuppActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totKm:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotSalActivity.this)) {
                        startActivity(new Intent(ResTotSalActivity.this, TotKMActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totSal:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotSalActivity.this)) {
                        startActivity(new Intent(ResTotSalActivity.this, TotSalActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_deconnect:
                editeur.clear();
                editeur.commit();
                startActivity(new Intent(ResTotSalActivity.this, ConnexionActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
