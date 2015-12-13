package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class ListSuppActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editeur;
    private ArrayList<Match> matchs = new ArrayList<>();
    private ArrayList<String> matchsAffichage = new ArrayList<>();
    private Utilisateur util;
    private double adrLat;
    private double adrLon;
    private String date;
    private String dateToParse;
    private Calendar dateMatch = Calendar.getInstance();
    private ListView listSupp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_supp);
        preferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        editeur = preferences.edit();
        init();
    }

    public void init(){
        listSupp = (ListView)findViewById(R.id.listSupp);
        adrLat = Double.parseDouble(preferences.getString("adrLat",""));
        adrLon = Double.parseDouble(preferences.getString("adrLon", ""));
        util=new Utilisateur(preferences.getInt("idUtilisateur",0),preferences.getString("prenomUtil", ""),adrLat,adrLon);
        getMatchUtilisateur();
    }

    public void onClick(View v) {

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
                        matchsAffichage.add(date.substring(0, 10)+" / "+m.getLibelleDivision()+" / "+m.getNomPicine());
                    }
                    ArrayAdapter<String> adaptaterRech = new ArrayAdapter<String>(ListSuppActivity.this, android.R.layout.simple_spinner_item, matchsAffichage);
                    listSupp.setAdapter(adaptaterRech);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Match m = new Match(0,null,false,0,0,0,0,0);
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
                    if (VerificationConnexionInternet.estConnecteAInternet(ListSuppActivity.this)) {
                        startActivity(new Intent(ListSuppActivity.this, RechActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_ajout:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ListSuppActivity.this)) {
                        startActivity(new Intent(ListSuppActivity.this, AjoutActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_statDiv:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ListSuppActivity.this)) {
                        startActivity(new Intent(ListSuppActivity.this, StatDivisionActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_statPisc:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ListSuppActivity.this)) {
                        startActivity(new Intent(ListSuppActivity.this, StatPiscineActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_supp:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ListSuppActivity.this)) {
                        startActivity(new Intent(ListSuppActivity.this, ListSuppActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_totKm:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ListSuppActivity.this)) {
                        startActivity(new Intent(ListSuppActivity.this, TotKMActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_totSal:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ListSuppActivity.this)) {
                        startActivity(new Intent(ListSuppActivity.this, TotSalActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_deconnect:
                editeur.clear();
                editeur.commit();
                startActivity(new Intent(ListSuppActivity.this, ConnexionActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
