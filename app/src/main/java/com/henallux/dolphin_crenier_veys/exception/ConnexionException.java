package com.henallux.dolphin_crenier_veys.exception;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.henallux.dolphin_crenier_veys.R;

public class ConnexionException extends Exception {

    private Context ctx;

    public ConnexionException(Context ctx){
        this.ctx = ctx;
    }

    public void msgException() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(ctx.getResources().getString(R.string.title_AlertDialogInternet));
        builder.setMessage(ctx.getResources().getString(R.string.msg_AlertDialogInternet))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
