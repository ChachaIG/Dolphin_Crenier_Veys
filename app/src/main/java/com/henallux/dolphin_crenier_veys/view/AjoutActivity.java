package com.henallux.dolphin_crenier_veys.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
import com.henallux.dolphin_crenier_veys.controller.ApplicationController;
import com.henallux.dolphin_crenier_veys.dataAccess.Singleton;
import com.henallux.dolphin_crenier_veys.exception.ConnexionException;
import com.henallux.dolphin_crenier_veys.model.Division;
import com.henallux.dolphin_crenier_veys.model.Match;
import com.henallux.dolphin_crenier_veys.model.Piscine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AjoutActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView recupDate;
    private Spinner recupDiv;
    private Spinner recupLieu;
    private SimpleDateFormat dateFormat;
    private Button ajoutBout;
    private Calendar nouvDate;
    private Calendar date = Calendar.getInstance();
    private ArrayList<Division> dataDiv = new ArrayList<>();
    private ArrayList<Piscine> dataLieu = new ArrayList<>();
    private ArrayList<String> libelleDivision = new ArrayList<>();
    private ArrayList<String> libelleLieu = new ArrayList<>();
    private long tpsNouvDate;
    private long tpsDate;
    private Boolean estUnSecondMatch;
    private Switch secondMatch;
    private Match m;
    private ApplicationController ac = new ApplicationController();
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);
        init();
    }


    public void init() {
        ApplicationController ac = new ApplicationController();
        try {
            if (VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                getDivisions();
                getPiscines();

            }
        } catch (ConnexionException ex) {
            ex.msgException();
        }


        recupDate = (TextView) findViewById(R.id.recupDate);
        recupDate.setHint(R.string.indiceDate);
        recupDate.setOnClickListener(this);
        ajoutBout = (Button) findViewById(R.id.ajoutMatchBouton);
        ajoutBout.setOnClickListener(this);
        secondMatch = (Switch) findViewById(R.id.secondMatch);
        secondMatch.setChecked(false);

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recupDate:
                setDateMatchAjout();
                break;
            case R.id.ajoutMatchBouton:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {

                        //"DISTANCE","COUT","DATE_MATCH","SECOND_MATCH","ID_PISCINE","ID_UTILISATEUR","ID_DIVISION"
                        estUnSecondMatch = secondMatch.isChecked();
                        Integer idLieu = recupLieu.getSelectedItemPosition();
                        final String idLieuJSON = "" + idLieu + "";
                        Piscine selectPiscine = dataLieu.get(idLieu);
                        Integer idDiv = recupDiv.getSelectedItemPosition();
                        final String idDivJson = "" + idDiv + "";
                        ///!\ A MODIFIER idUser EST UN TEST /!\\\
                        Integer idUser = 1;
                        Division selectDivision = dataDiv.get(idDiv);
                        intent = new Intent(AjoutActivity.this, ResAjoutActivity.class);
                        intent.putExtra("IdLieu", selectPiscine.getId());
                        intent.putExtra("NomPiscine", selectPiscine.getNom());
                        intent.putExtra("AdrLat", selectPiscine.getAdrLatitude());
                        intent.putExtra("AdrLon", selectPiscine.getAdrLongitutde());
                        if (nouvDate != null) {
                            m = new Match(nouvDate, estUnSecondMatch, idLieu, idDiv, idUser, 89.23, 59.23);
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("DATE_MATCH", m.getDateMatch());
                                jsonObject.put("SECOND_MATCH", m.getSecondMatch());
                                jsonObject.put("ID_PISCINE", m.getIdPiscine());
                                jsonObject.put("ID_DIVISION", m.getIdDivision());
                                jsonObject.put("ID_UTILISATEUR", m.getIdUtilisateur());
                                jsonObject.put("COUT", m.getCout());
                                jsonObject.put("DISTANCE", m.getDistance());
                                intent.putExtra("DISTANCE", m.getDistance());
                                intent.putExtra("COUT",m.getCout());
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://dolphinapp.azurewebsites.net/api/match?newMatch=" + jsonObject,
                                        jsonObject,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                               // startActivity(intent);
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }
                                });
                                Singleton.getInstance(AjoutActivity.this).addToRequestQueue(jsonObjectRequest);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            startActivity(intent);


                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle(this.getResources().getString(R.string.verifDateAjout));
                            builder.setMessage(this.getResources().getString(R.string.verifDateAjout))
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }

                        break;
                    }

                } catch (ConnexionException ex) {
                    ex.msgException();
                }
        }
    }

    private void setDateMatchAjout() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
                tpsDate = date.getTimeInMillis();
                dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                nouvDate = Calendar.getInstance();
                nouvDate.set(year, monthOfYear, dayOfMonth);
                tpsNouvDate = nouvDate.getTimeInMillis();
                if (tpsNouvDate > tpsDate)
                    recupDate.setText(dateFormat.format(nouvDate.getTime()));
                else
                    Toast.makeText(AjoutActivity.this, R.string.verifDateAjout, Toast.LENGTH_LONG).show();
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        dialog.show();
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
                    if (VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, RechActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_ajout:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, AjoutActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_statDiv:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, StatDivisionActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_statPisc:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, StatPiscineActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_supp:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, ListSuppActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_totKm:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, TotKMActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_totSal:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(AjoutActivity.this)) {
                        startActivity(new Intent(AjoutActivity.this, TotSalActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_deconnect:
                startActivity(new Intent(AjoutActivity.this, ConnexionActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    private void getDivisions() {


        JsonArrayRequest getDiv = new JsonArrayRequest(Request.Method.GET, "http://dolphinapp.azurewebsites.net/api/division", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject res = response.getJSONObject(i);
                        Division di = new Division(res.getInt("ID_DIVISION"), res.getString("NOM_DIVISION"), res.getString("LIBELLE_DIVISION"));
                        dataDiv.add(di);


                    }
                    for (Division d : dataDiv) {
                        libelleDivision.add(d.getNomDivision());
                    }
                    recupDiv = (Spinner) findViewById(R.id.recupCat);
                    ArrayAdapter<String> adaptaterCat = new ArrayAdapter<String>(AjoutActivity.this, android.R.layout.simple_spinner_item, libelleDivision);
                    recupDiv.setAdapter(adaptaterCat);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Division di = new Division(0, "error", "error");
                dataDiv.add(di);
            }
        });

        Singleton.getInstance(this).addToRequestQueue(getDiv);
    }


    private void getPiscines() {

        JsonArrayRequest getLieu = new JsonArrayRequest(Request.Method.GET, "http://dolphinapp.azurewebsites.net/api/piscine", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject res = response.getJSONObject(i);
                        Piscine pi = new Piscine(res.getInt("ID_PISCINE"), res.getString("NOM_PISCINE"), res.getDouble("ADR_LATITUDE"), res.getDouble("ADR_LONGITUDE"));
                        dataLieu.add(pi);

                    }
                    for (Piscine p : dataLieu) {
                        libelleLieu.add(p.getNom());
                    }
                    recupLieu = (Spinner) findViewById(R.id.recupLieu);
                    ArrayAdapter<String> adaptaterCat = new ArrayAdapter<String>(AjoutActivity.this, android.R.layout.simple_spinner_item, libelleLieu);
                    recupLieu.setAdapter(adaptaterCat);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Piscine pi = new Piscine(0, "error", 0, 0);
                dataLieu.add(pi);

            }
        });

        Singleton.getInstance(this).addToRequestQueue(getLieu);
    }
}
