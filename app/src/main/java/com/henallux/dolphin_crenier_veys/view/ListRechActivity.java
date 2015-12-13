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
    private SharedPreferences preferences;
    private SharedPreferences.Editor editeur;
    private Utilisateur util;
    private double adrLat;
    private double adrLon;
    private ArrayList<Match> matchs = new ArrayList<>();
    private ArrayList<String> matchsAffichage = new ArrayList<>();
    private String date;
    private String dateToParse;
    private Calendar dateMatch = Calendar.getInstance();
    private ListView listRech;
    private Calendar dateDeb = Calendar.getInstance();
    private String dateDebStr;
    private Calendar dateFin = Calendar.getInstance();
    private String dateFinStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rech);
        Bundle bundle = this.getIntent().getExtras();
        dateDebStr = bundle.getString("dateDeb");
        dateFinStr = bundle.getString("dateFin");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        try {
            dateDeb.setTime(dateFormat.parse(dateDebStr));
            dateDeb.add(Calendar.DAY_OF_MONTH, -1);
            dateFin.setTime(dateFormat.parse(dateFinStr));
            dateFin.add(Calendar.DAY_OF_MONTH,+1);
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
                        date = res.getString("DATE_MATCH");
                        dateToParse = date.substring(0,10);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        try {
                            dateMatch.setTime(dateFormat.parse(dateToParse));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        /*"ID_MATCH":1,"DATE_MATCH":"2015-10-16T00:00:00","SECOND_MATCH":false,"DISTANCE":100.12,"COUT":53.45,"ID_PISCINE":1,"ID_DIVISION":5,"ID_UTILISATEUR":1}]*/
                        Match m = new Match(res.getInt("ID_MATCH"),date, res.getBoolean("SECOND_MATCH"),res.getInt("ID_UTILISATEUR"),res.getJSONObject("piscine").getString("NOM_PISCINE"),res.getJSONObject("division").getString("LIBELLE_DIVISION"),res.getDouble("DISTANCE"),res.getDouble("COUT"));
                        matchs.add(m);
                        m.setDateMatch(dateMatch);
                    }
                    for (Match m : matchs) {
                        if(m.getDateMatch().after(dateDeb) && m.getDateMatch().before(dateFin))
                            matchsAffichage.add(date.substring(0, 10)+" / "+m.getLibelleDivision()+" / "+m.getNomPicine());
                    }
                    ArrayAdapter<String> adaptaterRech = new ArrayAdapter<String>(ListRechActivity.this, android.R.layout.simple_spinner_item, matchsAffichage);
                    listRech.setAdapter(adaptaterRech);
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
            case R.id.ic_statDiv:
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
                }
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
