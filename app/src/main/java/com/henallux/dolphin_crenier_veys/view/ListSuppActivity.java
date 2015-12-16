package com.henallux.dolphin_crenier_veys.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
import com.henallux.dolphin_crenier_veys.dataAccess.Singleton;
import com.henallux.dolphin_crenier_veys.exception.ConnexionException;
import com.henallux.dolphin_crenier_veys.model.Match;
import com.henallux.dolphin_crenier_veys.model.Utilisateur;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ListSuppActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editeur;
    private ArrayList<Match> matchs = new ArrayList<>();
    private ArrayList<Match> matchsSelect = new ArrayList<>();
    private ArrayList<String> matchsAffichage = new ArrayList<>();
    private Match selectM;
    private Utilisateur util;
    private double adrLat;
    private double adrLon;
    private ListView listSupp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_supp);
        preferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        editeur = preferences.edit();
        init();
    }

    public void init() {
        listSupp = (ListView) findViewById(R.id.listSupp);
        adrLat = Double.parseDouble(preferences.getString("adrLat", ""));
        adrLon = Double.parseDouble(preferences.getString("adrLon", ""));
        util = new Utilisateur(preferences.getInt("IdUtil", 0), preferences.getString("prenomUtil", ""), adrLat, adrLon);
        getMatchUtilisateur();

    }


    public void supprimerMatch(int id){
        JsonObjectRequest suppMatch = new JsonObjectRequest(Request.Method.DELETE, "http://dolphinapp.azurewebsites.net/api/match/"+id,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                error.getCause();
            }
        });
        Singleton.getInstance(ListSuppActivity.this).addToRequestQueue(suppMatch);
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
                        matchsAffichage.add(m.getIdMatch() + " / "+m.getDateStr().substring(0, 10) + " / " + m.getLibelleDivision() + " / " + m.getNomPicine());
                        matchsSelect.add(m);
                    }
                    ArrayAdapter<String> adaptaterRech = new ArrayAdapter<String>(ListSuppActivity.this, android.R.layout.simple_spinner_item, matchsAffichage);
                    listSupp.setAdapter(adaptaterRech);
                    listSupp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selectM = matchsSelect.get(position);
                            new AlertDialog.Builder(ListSuppActivity.this)
                                    .setTitle(R.string.suppression)
                                    .setMessage(R.string.phraseSuppression)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            supprimerMatch(selectM.getIdMatch());
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    });
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
