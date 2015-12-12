package com.henallux.dolphin_crenier_veys.exception;


import android.content.Context;

import com.henallux.dolphin_crenier_veys.R;

public class CryptageMotDePasseException extends Exception{

    private Context ctx;

    public CryptageMotDePasseException(Context ctx){
        this.ctx = ctx;
    }

    public String msgException(){

        return ctx.getResources().getString(R.string.erreurCrypt);
    }
}
