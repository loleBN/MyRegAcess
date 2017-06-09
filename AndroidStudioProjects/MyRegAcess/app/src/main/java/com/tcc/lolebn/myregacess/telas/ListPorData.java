package com.tcc.lolebn.myregacess.telas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ListPorData extends AppCompatActivity {
    private TextView currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpordata);

        ListView listViewReg = (ListView) findViewById(R.id.lvPorData);
        currentDate = (TextView) findViewById(R.id.txt_pordata_data);
        currentDate.setText(Utils.convertDateToString(new Date()));

        List<RegIN> lregs  = new ArrayList<>();
        try {
            lregs = new DownloadRegistroPorData(this).execute(Utils.convertDateToString(new Date())).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        AdapterRegPorData adapter = new AdapterRegPorData(lregs,this);
        listViewReg.setAdapter(adapter);
    }
}
