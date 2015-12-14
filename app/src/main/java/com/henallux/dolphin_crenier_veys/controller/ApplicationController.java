package com.henallux.dolphin_crenier_veys.controller;


import android.content.Context;

import com.henallux.dolphin_crenier_veys.business.ApplicationBusiness;
import com.henallux.dolphin_crenier_veys.exception.CryptageMotDePasseException;
import com.henallux.dolphin_crenier_veys.model.Division;
import com.henallux.dolphin_crenier_veys.model.Match;
import com.henallux.dolphin_crenier_veys.model.Piscine;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;

public class ApplicationController {

    private ApplicationBusiness business = new ApplicationBusiness();

    public ApplicationController() {
    }

    public void ajoutMatch(Context ctx, Match m){
        business.ajoutMatch(ctx,m);
    }

    public String cryptageMDP(String mdp, Context ctx) throws CryptageMotDePasseException, NoSuchAlgorithmException {
       return business.cryptageMDP(mdp,ctx);
    }

    public Calendar getDateDeb(Calendar laps, int switchSelect){
        return business.getDateDeb(laps, switchSelect);
    }

    public Calendar getDateFin(Calendar laps, int switchSelect){
        return business.getDateFin(laps, switchSelect);
    }

    public double getCoutMatch(Match m){
        return business.getCoutMatch(m);
    }

}
