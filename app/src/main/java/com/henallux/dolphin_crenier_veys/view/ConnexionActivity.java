package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.henallux.dolphin_crenier_veys.InternetConnection.VerificationConnexionInternet;
import com.henallux.dolphin_crenier_veys.R;
import com.henallux.dolphin_crenier_veys.controller.ApplicationController;
import com.henallux.dolphin_crenier_veys.dataAccess.Singleton;
import com.henallux.dolphin_crenier_veys.exception.ConnexionException;
import com.henallux.dolphin_crenier_veys.exception.CryptageMotDePasseException;
import com.henallux.dolphin_crenier_veys.model.Utilisateur;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

public class ConnexionActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText login;
    private EditText motDePasse;
    private String motDePasseStr;
    private String mdpCrypt;
    private Button connexionBouton;
    private String log;
    private String upperLog;
    private ApplicationController ac = new ApplicationController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClick(View v){
        if(v.getId()== R.id.connexionBouton) {
            try {
                if(VerificationConnexionInternet.estConnecteAInternet(ConnexionActivity.this))
                    verificationLogin();

            }catch (ConnexionException ex){
                ex.msgException();
            }
        }

    }

    private void verificationLogin(){
        log = login.getText().toString();
        upperLog = log.toUpperCase();

        JsonObjectRequest getDiv = new JsonObjectRequest(Request.Method.GET,"http://dolphinapp.azurewebsites.net/api/utilisateur?login="+upperLog, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Utilisateur util = new Utilisateur(response.getInt("ID_UTILISATEUR"),
                            response.getString("LOGIN"), response.getString("MOTDEPASSE"),
                            response.getString("NOM"),
                            response.getString("PRENOM"),
                            response.getDouble("ADR_LATITUDE"),
                            response.getDouble("ADR_LONGITUDE"));
                            motDePasseStr = motDePasse.getText().toString();
                            ////////////////////////////////////////////////////////
                            ////A DECOMMENTER POUR PRENDRE EN CHARGE LE CRYPTAGE////
                            ////////////////////////////////////////////////////////
                            /*try {
                                mdpCrypt = ac.cryptageMDP(motDePasseStr, ConnexionActivity.this);
                            }catch(NoSuchAlgorithmException e){
                                e.printStackTrace();
                            }
                            catch(CryptageMotDePasseException e){
                                Toast.makeText(ConnexionActivity.this, e.msgException(),Toast.LENGTH_SHORT).show();
                            }*/
                            ////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////
                            //!\ UNE FOIS CRYPTAGE FONCTIONNEL REMPLACER motDePasseStr PAR mdpCrypt/!\\
                            ///////////////////////////////////////////////////////////////
                            if(motDePasseStr.equals(util.getMotDepasse()) == false) {
                                Toast.makeText(ConnexionActivity.this, R.string.erreurMDP, Toast.LENGTH_SHORT).show();
                            }
                            if(motDePasseStr.equals(util.getMotDepasse())){
                                Intent intent = new Intent(ConnexionActivity.this, MenuActivity.class);
                                intent.putExtra("idUtil", util.getIdUtilisateur());
                                intent.putExtra("prenomUtil",util.getPrenom());
                                intent.putExtra("adrLat",util.getAdrLatitude());
                                intent.putExtra("adrLong",util.getAdrLongitude());
                                startActivity(intent);
                            }
                } catch (JSONException e) {
                    Toast.makeText(ConnexionActivity.this,R.string.erreurJSON,Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        });
        Singleton.getInstance(this).addToRequestQueue(getDiv);
    }

    public void init(){
        connexionBouton = (Button)this.findViewById(R.id.connexionBouton);
        connexionBouton.setOnClickListener(this);
        login = (EditText)this.findViewById(R.id.loginText);
        login.setHint(R.string.login);
        motDePasse = (EditText)this.findViewById(R.id.passwordText);
        motDePasse.setHint(R.string.motDePasse);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();




        return super.onOptionsItemSelected(item);
    }
}
