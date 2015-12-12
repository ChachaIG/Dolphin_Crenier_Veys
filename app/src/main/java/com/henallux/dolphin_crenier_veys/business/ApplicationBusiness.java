package com.henallux.dolphin_crenier_veys.business;

import android.content.Context;

import com.henallux.dolphin_crenier_veys.dataAccess.DataAccess;
import com.henallux.dolphin_crenier_veys.exception.CryptageMotDePasseException;
import com.henallux.dolphin_crenier_veys.model.Match;
import com.henallux.dolphin_crenier_veys.model.Piscine;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ApplicationBusiness {

    private DataAccess da = new DataAccess();

    public ApplicationBusiness() {
    }

    public void ajoutMatch(Context ctx, Match m){
        da.ajoutMatch(ctx,m);
    }

    public String cryptageMDP (String mdp, Context ctx) throws NoSuchAlgorithmException, CryptageMotDePasseException {
        MessageDigest md;
        String message = "password";
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

}
