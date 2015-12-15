package com.henallux.dolphin_crenier_veys.business;

import android.content.Context;

import com.henallux.dolphin_crenier_veys.dataAccess.DataAccess;
import com.henallux.dolphin_crenier_veys.exception.CryptageMotDePasseException;
import com.henallux.dolphin_crenier_veys.model.Division;
import com.henallux.dolphin_crenier_veys.model.Match;
import com.henallux.dolphin_crenier_veys.model.Piscine;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;

public class ApplicationBusiness {

    private DataAccess da = new DataAccess();
    private final double PRIX_AU_KM = 0.3456;
    private final int ALLER_RETOUR = 2;

    public ApplicationBusiness() {
    }

    public void ajoutMatch(Context ctx, Match m){
        da.ajoutMatch(ctx,m);
    }

    public String cryptageMDP (String mdp, Context ctx) throws NoSuchAlgorithmException, CryptageMotDePasseException {
        MessageDigest md;
        String message = mdp;
        String out = "";
        try {
            md= MessageDigest.getInstance("SHA-512");
            md.update(message.getBytes());
            byte[] mb = md.digest();
            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                out += s;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new CryptageMotDePasseException(ctx);
        }

        return out;
    }

    public Calendar getDateDeb(Calendar laps, int switchSelect) {
        Calendar dateDeb = Calendar.getInstance();
        if (switchSelect == 1) {
            dateDeb.set(Calendar.YEAR, laps.get(Calendar.YEAR));
            dateDeb.set(Calendar.DAY_OF_YEAR, 1);

        }
        if (switchSelect == 2) {
            if (laps != null) {
                dateDeb.set(Calendar.YEAR, laps.get(Calendar.YEAR));
                dateDeb.set(Calendar.MONTH, laps.get(Calendar.MONTH));
                dateDeb.set(Calendar.DAY_OF_MONTH, 1);
            }
        }
        if (switchSelect == 3) {
            if (laps != null) {
                if (laps.get(Calendar.MONTH) >= 0 && laps.get(Calendar.MONTH) <= 5) {
                    laps.add(Calendar.YEAR, -1);
                    dateDeb.set(Calendar.YEAR, laps.get(Calendar.YEAR));
                } else {
                    dateDeb.set(Calendar.YEAR, laps.get(Calendar.YEAR));
                }
                dateDeb.set(Calendar.MONTH, 8);
                dateDeb.set(Calendar.DAY_OF_MONTH, 1);

            }
        }
        return dateDeb;
    }




    public Calendar getDateFin(Calendar laps, int switchSelect){
        Calendar dateFin = Calendar.getInstance();
        if (switchSelect == 1) {
            if(laps != null) {
                dateFin.set(Calendar.YEAR, laps.get(Calendar.YEAR));
                dateFin.set(Calendar.MONTH, 11);
                dateFin.set(Calendar.DAY_OF_MONTH, 31);
            }
        }
        if (switchSelect == 2) {
            if (laps != null) {
                dateFin.set(Calendar.YEAR, laps.get(Calendar.YEAR));
                dateFin.set(Calendar.MONTH, laps.get(Calendar.MONTH));
                if (laps.get(Calendar.MONTH) % 2 != 0) {
                    dateFin.set(Calendar.DAY_OF_MONTH, 30);
                } else {
                    dateFin.set(Calendar.DAY_OF_MONTH, 31);
                }
                if (laps.get(Calendar.MONTH) == 2)
                    dateFin.set(Calendar.DAY_OF_MONTH, 28);
            }
        }
        if(switchSelect == 3){
            if (laps != null) {

                if (laps.get(Calendar.MONTH) >= 8 && laps.get(Calendar.MONTH) <= 11) {
                    laps.add(Calendar.YEAR,1);
                    dateFin.set(Calendar.YEAR, laps.get(Calendar.YEAR));
                } else {
                    dateFin.set(Calendar.YEAR, laps.get(Calendar.YEAR));
                }
                dateFin.set(Calendar.MONTH, 5);
                dateFin.set(Calendar.DAY_OF_MONTH, 30);
            }
        }
        return dateFin;
    }

    public double getCoutMatch(Match m){
        double cout = (m.getDistance()*ALLER_RETOUR)*PRIX_AU_KM;
        Division div = m.getDivision();
        switch (div.getIdDivision()){
            case 1 :case 2 : case 16 :
                cout += 50;
                break;
            case 7:case 9:case 11:case 13:
                cout +=37.5;
                break;
            default:
                cout +=25;
                break;
        }
        return cout;
    }

    public String getNomDuMois(Calendar date){
        String nomMois ="";
        switch (date.get(Calendar.MONTH)){
            case 0: nomMois = "Janvier";
                break;
            case 1: nomMois = "Février";
                break;
            case 2: nomMois = "Mars";
                break;
        case 3: nomMois = "Avril";
                break;
            case 4: nomMois = "Mai";
                break;
            case 5: nomMois = "Juin";
                break;
            case 6: nomMois = "Juillet";
                break;
            case 7: nomMois = "Août";
                break;
            case 8: nomMois = "Septembre";
                break;
            case 9: nomMois = "Octobre";
                break;
            case 10: nomMois = "Novembre";
                break;
            case 11: nomMois = "Décembre";
                break;
        }
        return nomMois;
    }

}
