package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
import com.henallux.dolphin_crenier_veys.exception.ConnexionException;
import com.henallux.dolphin_crenier_veys.model.Utilisateur;

import android.widget.ExpandableListView;
import android.widget.Toast;


public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private ExpandableListAdapter listAdaptateur;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private Utilisateur util;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editeur;
    private Double adrLat;
    private Double adrLon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        preferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        editeur = preferences.edit();
        init();
    }

    public void init() {

        Toast.makeText(MenuActivity.this, getString(R.string.bienvenueMess) + " " + preferences.getString("prenomUtil",""), Toast.LENGTH_SHORT).show();
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        prepareListData();
        listAdaptateur = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdaptateur);
        navigationMenu();


    }

    public void onClick(View v) {

    }

    private void navigationMenu() {
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0) {
                    switch (childPosition) {
                        case 0:
                            try {
                                if (VerificationConnexionInternet.estConnecteAInternet(MenuActivity.this)) {
                                    startActivity(new Intent(MenuActivity.this, AjoutActivity.class));
                                    break;
                                }
                            } catch (ConnexionException ex) {
                                ex.msgException();
                            }
                        case 1:
                            try {
                                if (VerificationConnexionInternet.estConnecteAInternet(MenuActivity.this)) {
                                    startActivity(new Intent(MenuActivity.this, ListSuppActivity.class));
                                    break;
                                }
                            } catch (ConnexionException ex) {
                                ex.msgException();
                            }
                    }
                } else {
                    if (groupPosition == 2) {
                        switch (childPosition) {
                            case 0:
                                try {
                                    if (VerificationConnexionInternet.estConnecteAInternet(MenuActivity.this)) {
                                        startActivity(new Intent(MenuActivity.this, TotKMActivity.class));
                                        break;
                                    }
                                } catch (ConnexionException ex) {
                                    ex.msgException();
                                }
                            case 1:
                                try {
                                    if (VerificationConnexionInternet.estConnecteAInternet(MenuActivity.this)) {
                                        startActivity(new Intent(MenuActivity.this, TotSalActivity.class));
                                        break;
                                    }
                                } catch (ConnexionException ex) {
                                    ex.msgException();
                                }
                        }
                    } else {
                        switch (childPosition) {
                            case 0:
                                try {
                                    if (VerificationConnexionInternet.estConnecteAInternet(MenuActivity.this)) {
                                        startActivity(new Intent(MenuActivity.this, StatPiscineActivity.class));
                                        break;
                                    }
                                } catch (ConnexionException ex) {
                                    ex.msgException();
                                }
                            case 1:
                                try {
                                    if (VerificationConnexionInternet.estConnecteAInternet(MenuActivity.this)) {
                                        startActivity(new Intent(MenuActivity.this, StatDivisionActivity.class));
                                        break;
                                    }
                                } catch (ConnexionException ex) {
                                    ex.msgException();
                                }
                        }
                    }
                }
                return false;
            }
        });
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groupPosition == 1) {
                    try {
                        if (VerificationConnexionInternet.estConnecteAInternet(MenuActivity.this))
                            startActivity(new Intent(MenuActivity.this, RechActivity.class));

                    } catch (ConnexionException ex) {
                        ex.msgException();
                    }

                }
                return false;
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add(getString(R.string.gestionMatch));
        listDataHeader.add(getString(R.string.recherche));
        listDataHeader.add(getString(R.string.tot));
        listDataHeader.add(getString(R.string.stat));

        List<String> subGestionMatch = new ArrayList<String>();
        subGestionMatch.add(getString(R.string.ajoutMatch));
        subGestionMatch.add(getString(R.string.suppMatch));

        List<String> subTot = new ArrayList<String>();
        subTot.add(getString(R.string.km));
        subTot.add(getString(R.string.salaire));

        List<String> subStat = new ArrayList<String>();
        subStat.add(getString(R.string.piscine));
        subStat.add(getString(R.string.division));

        listDataChild.put(listDataHeader.get(0), subGestionMatch);
        listDataChild.put(listDataHeader.get(1), new ArrayList<String>());
        listDataChild.put(listDataHeader.get(2), subTot);
        listDataChild.put(listDataHeader.get(3), subStat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.ic_rech:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(MenuActivity.this)) {
                        startActivity(new Intent(MenuActivity.this, RechActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_ajout:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(MenuActivity.this)) {
                        startActivity(new Intent(MenuActivity.this, AjoutActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_statDiv:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(MenuActivity.this)) {
                        startActivity(new Intent(MenuActivity.this, StatDivisionActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_statPisc:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(MenuActivity.this)) {
                        startActivity(new Intent(MenuActivity.this, StatPiscineActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_supp:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(MenuActivity.this)) {
                        startActivity(new Intent(MenuActivity.this, ListSuppActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_totKm:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(MenuActivity.this)) {
                        startActivity(new Intent(MenuActivity.this, TotKMActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_totSal:
                try {
                    if (VerificationConnexionInternet.estConnecteAInternet(MenuActivity.this)) {
                        startActivity(new Intent(MenuActivity.this, TotSalActivity.class));
                        return true;
                    }
                } catch (ConnexionException ex) {
                    ex.msgException();
                }
            case R.id.ic_deconnect:
                editeur.clear();
                editeur.commit();
                startActivity(new Intent(MenuActivity.this, ConnexionActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
