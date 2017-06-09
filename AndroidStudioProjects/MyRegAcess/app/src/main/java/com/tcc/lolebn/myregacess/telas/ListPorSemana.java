package com.tcc.lolebn.myregacess.telas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.tcc.lolebn.myregacess.R;
import com.tcc.lolebn.myregacess.adapters.AdapterRegPorData;
import com.tcc.lolebn.myregacess.basics.RegIN;
import com.tcc.lolebn.myregacess.basics.Utils;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorData;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorSemana;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lolebn on 08/06/17.
 */

public class ListPorSemana extends AppCompatActivity {
    //private TextView currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listporsemana);

        ListView listViewReg = (ListView) findViewById(R.id.lvPorSemana);
        //currentDate = (TextView) findViewById(R.id.txt_porsemana_);
        //currentDate.setText("Semana Atual");

        List<RegIN> lregs  = new ArrayList<>();
        try {
            lregs = new DownloadRegistroPorSemana(this).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (lregs != null){
            AdapterRegPorData adapter = new AdapterRegPorData(lregs,this);
            listViewReg.setAdapter(adapter);
        } else {
            Log.e("NULO!!!","Tá nulo né;..");
        }
    }
}
