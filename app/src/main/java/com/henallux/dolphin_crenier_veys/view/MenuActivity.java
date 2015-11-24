package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.henallux.dolphin_crenier_veys.R;

import android.widget.ExpandableListView;




public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
    }

    public void init(){
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0) {
                    switch (childPosition) {
                        case 0:
                            startActivity(new Intent(MenuActivity.this, AjoutActivity.class));
                            break;
                        case 1:
                            startActivity(new Intent(MenuActivity.this, SuppActivity.class));
                            break;
                    }
                } else {
                    if (groupPosition == 2) {
                        switch (childPosition) {
                            case 0:
                                startActivity(new Intent(MenuActivity.this, TotKMActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(MenuActivity.this, TotSalActivity.class));
                                break;
                        }
                    } else {
                        switch (childPosition) {
                            case 0:
                                startActivity(new Intent(MenuActivity.this, StatPiscineActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(MenuActivity.this, StatDivisionActivity.class));
                                break;
                        }
                    }

                }

                return false;

            }
        });

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(groupPosition == 1){
                    startActivity(new Intent(MenuActivity.this, RechActivity.class));
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
        listDataChild.put(listDataHeader.get(1),new ArrayList<String>());
        listDataChild.put(listDataHeader.get(2), subTot);
        listDataChild.put(listDataHeader.get(3), subStat);
    }

    public void onClick(View v){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {

            case R.id.ic_rech:
                startActivity(new Intent(MenuActivity.this, RechActivity.class));
                return true;
            case R.id.ic_ajout:
                startActivity(new Intent(MenuActivity.this, AjoutActivity.class));
                return true;
            case R.id.ic_statDiv:
                startActivity(new Intent(MenuActivity.this, StatDivisionActivity.class));
                return true;
            case R.id.ic_statPisc:
                startActivity(new Intent(MenuActivity.this, StatPiscineActivity.class));
                return true;
            case R.id.ic_supp:
                startActivity(new Intent(MenuActivity.this, SuppActivity.class));
                return true;
            case R.id.ic_totKm:
                startActivity(new Intent(MenuActivity.this, TotKMActivity.class));
                return true;
            case R.id.ic_totSal:
                startActivity(new Intent(MenuActivity.this, TotSalActivity.class));
                return true;

        }


        return super.onOptionsItemSelected(item);
    }
}
