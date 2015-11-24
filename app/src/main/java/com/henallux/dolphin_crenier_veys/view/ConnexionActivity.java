package com.henallux.dolphin_crenier_veys.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import com.henallux.dolphin_crenier_veys.R;

public class ConnexionActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText loginInput;
    private EditText passwordInput;
    private Button connexionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.connexionButton:
                startActivity(new Intent(ConnexionActivity.this,MenuActivity.class));
        }

    }

    public void init(){
        connexionButton = (Button)this.findViewById(R.id.connexionButton);
        connexionButton.setOnClickListener(this);
        loginInput = (EditText)this.findViewById(R.id.loginText);
        loginInput.setHint(R.string.loginInput);
        passwordInput = (EditText)this.findViewById(R.id.passwordText);
        passwordInput.setHint(R.string.passwordInput);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();




        return super.onOptionsItemSelected(item);
    }
}
