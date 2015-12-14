package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
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

public class ResTotKMActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editeur;
    private Calendar dateDeb = Calendar.getInstance();
    private Calendar dateFin = Calendar.getInstance();
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private String date;
    private String dateToParse;
    private Calendar dateMatch =Calendar.getInstance();
    private ArrayList<Match> matchs = new ArrayList<>();
    private ArrayList<String> matchsAffichage = new ArrayList<>();
    private ListView listRes;
    private Utilisateur util;
    private String dateDebStr;
    private String dateFinStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_tot_km);
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
        util = new Utilisateur(preferences.getInt("idUtil",0),preferences.getString("prenomUtil", ""),Double.parseDouble(preferences.getString("adrLat", "")),Double.parseDouble(preferences.getString("adrLon","")));
        init();
    }

    public void init(){
        listRes = (ListView)findViewById(R.id.listViewRes);
        getMatchUtilisateur();
    }

    public void getMatchUtilisateur(){
        JsonArrayRequest getMatchUtil = new JsonArrayRequest(Request.Method.GET, "http://dolphinapp.azurewebsites.net/api/match?idUUtilisateur="+util.getIdUtilisateur(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject res = response.getJSONObject(i);
                        date = res.getString("DATE_MATCH");
                        dateToParse = date.substring(0,10);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        try {
                            dateMatch.setTime(dateFormat.parse(dateToParse));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Match m = new Match(res.getInt("ID_MATCH"),date, res.getBoolean("SECOND_MATCH"),res.getInt("ID_UTILISATEUR"),res.getJSONObject("piscine").getString("NOM_PISCINE"),res.getJSONObject("division").getString("LIBELLE_DIVISION"),res.getDouble("DISTANCE"),res.getDouble("COUT"));
                        matchs.add(m);
                        m.setDateMatch(dateMatch);
                    }
                    for (Match m : matchs) {
                        if(m.getDateMatch().after(dateDeb) && m.getDateMatch().before(dateFin))
                            matchsAffichage.add(date.substring(0, 10)+" / "+m.getLibelleDivision()+" / "+m.getNomPicine());
                    }
                    ArrayAdapter<String> a = new ArrayAdapter<String>(ResTotKMActivity.this, android.R.layout.simple_list_item_1, matchsAffichage);
                    listRes.setAdapter(a);
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
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotKMActivity.this)) {
                        startActivity(new Intent(ResTotKMActivity.this, RechActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_ajout:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotKMActivity.this)) {
                        startActivity(new Intent(ResTotKMActivity.this, AjoutActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statDiv:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotKMActivity.this)) {
                        startActivity(new Intent(ResTotKMActivity.this, StatDivisionActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_statPisc:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotKMActivity.this)) {
                        startActivity(new Intent(ResTotKMActivity.this, StatPiscineActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_supp:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotKMActivity.this)) {
                        startActivity(new Intent(ResTotKMActivity.this, ListSuppActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totKm:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotKMActivity.this)) {
                        startActivity(new Intent(ResTotKMActivity.this, TotKMActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_totSal:
                try {
                    if(VerificationConnexionInternet.estConnecteAInternet(ResTotKMActivity.this)) {
                        startActivity(new Intent(ResTotKMActivity.this, TotSalActivity.class));
                        return true;
                    }
                }catch (ConnexionException ex){
                    ex.msgException();
                }
            case R.id.ic_deconnect:
                editeur.clear();
                editeur.commit();
                startActivity(new Intent(ResTotKMActivity.this, ConnexionActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
