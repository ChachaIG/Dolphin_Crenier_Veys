package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
import com.henallux.dolphin_crenier_veys.dataAccess.Singleton;
import com.henallux.dolphin_crenier_veys.exception.ConnexionException;
import com.henallux.dolphin_crenier_veys.model.Division;
import com.henallux.dolphin_crenier_veys.model.Match;
import com.henallux.dolphin_crenier_veys.model.Piscine;
import com.henallux.dolphin_crenier_veys.model.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ListRechActivity extends AppCompatActivity {
    //Shared preferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editeur;
    //Utilisateur
    private Utilisateur util;
    private double adrLat;
    private double adrLon;
    //Liste de match
    private ArrayList<Match> matchs = new ArrayList<>();
    private ArrayList<String> matchsAffichage = new ArrayList<>();
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
    //Vue
    private ListView listRech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rech);
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
        init();
    }

    public void init(){
        adrLat = Double.parseDouble(preferences.getString("adrLat",""));
        adrLon = Double.parseDouble(preferences.getString("adrLon", ""));
        util=new Utilisateur(preferences.getInt("idUtilisateur",0),preferences.getString("prenomUtil", ""),adrLat,adrLon);
        listRech = (ListView)findViewById(R.id.listViewRech);
        listRech.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(ListRechActivity.this, ResRechActivity.class);
                intent.putExtra("piscine", matchs.get(position).getNomPicine());
                intent.putExtra("distance",matchs.get(position).getDistance());
                intent.putExtra("cout",matchs.get(position).getCout());
                startActivity(intent);
            }
        });
        getMatchUtilisateur();
    }

    public void getMatchUtilisateur(){
        JsonArrayRequest getMatchUtil = new JsonArrayRequest(Request.Method.GET, "http://dolphinapp.azurewebsites.net/api/match?idUUtilisateur="+util.getIdUtilisateur(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject res = response.getJSONObject(i);
                        Match m = new Match(res.getInt("ID_MATCH"),res.getString("DATE_MATCH").substring(0,10), res.getBoolean("SECOND_MATCH"),res.getInt("ID_UTILISATEUR"),res.getJSONObject("piscine").getString("NOM_PISCINE"),res.getJSONObject("division").getString("LIBELLE_DIVISION"),res.getDouble("DISTANCE"),res.getDouble("COUT"));
                        matchs.add(m);
                    }
                    for (Match m : matchs) {
                        try {
                            dateMatch.setTime(dateFormatyyyyMMdd.parse(m.getDateStr()));
                            m.setDateMatch(dateMatch);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(m.getDateMatch().after(dateDeb2) && m.getDateMatch().before(dateFin2)) {
                            matchsAffichage.add(m.getDateStr() + " / " + m.getLibelleDivision() + " / " + m.getNomPicine());
                        }
                    }
                    ArrayAdapter<String> adaptaterRech = new ArrayAdapter<String>(ListRechActivity.this, android.R.layout.simple_spinner_item, matchsAffichage);
                    listRech.setAdapter(adaptaterRech);
                    if(matchsAffichage.isEmpty())
                        Toast.makeText(ListRechActivity.this,R.string.pasMatch,Toast.LENGTH_SHORT).show();
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
                    if (VerificationConnexionInternet.estConnecteAInternet(ListRechActivity.this)) {
                        startActivity(new Intent(ListRechActivity.this, RechActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_ajout:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ListRechActivity.this)) {
                        startActivity(new Intent(ListRechActivity.this, AjoutActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            /*case R.id.ic_statDiv:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ListRechActivity.this)) {
                        startActivity(new Intent(ListRechActivity.this, StatDivisionActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_statPisc:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ListRechActivity.this)) {
                        startActivity(new Intent(ListRechActivity.this, StatPiscineActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }*/
            case R.id.ic_supp:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ListRechActivity.this)) {
                        startActivity(new Intent(ListRechActivity.this, ListSuppActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_totKm:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ListRechActivity.this)) {
                        startActivity(new Intent(ListRechActivity.this, TotKMActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_totSal:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(ListRechActivity.this)) {
                        startActivity(new Intent(ListRechActivity.this, TotSalActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_deconnect:
                editeur.clear();
                editeur.commit();
                startActivity(new Intent(ListRechActivity.this, ConnexionActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
