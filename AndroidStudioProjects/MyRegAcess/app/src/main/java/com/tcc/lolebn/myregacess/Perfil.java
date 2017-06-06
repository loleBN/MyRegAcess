package com.tcc.lolebn.myregacess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        TextView txt = (TextView) findViewById(R.id.txtNome);
        txt.setText("Hello it's me Hendrio!");
    }
}
