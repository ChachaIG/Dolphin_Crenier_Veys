package com.henallux.dolphin_crenier_veys.dataAccess;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.henallux.dolphin_crenier_veys.model.Match;


import org.json.JSONException;
import org.json.JSONObject;

public class DataAccess {

    public DataAccess() {
    }


    public void ajoutMatch(Context context, Match m) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DATE_MATCH", m.getDateMatch());
            jsonObject.put("SECOND_MATCH", m.getSecondMatch());
            jsonObject.put("ID_PISCINE", m.getIdPiscine());
            jsonObject.put("ID_DIVISION", m.getIdDivision());
            jsonObject.put("ID_UTILISATEUR", m.getIdUtilisateur());
            jsonObject.put("COUT", m.getCout());
            jsonObject.put("DISTANCE", m.getDistance());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://dolphinapp.azurewebsites.net/api/match?newMatch=" + jsonObject,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
