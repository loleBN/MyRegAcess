package com.tcc.lolebn.myregacess.telas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;

import com.tcc.lolebn.myregacess.R;
import com.tcc.lolebn.myregacess.adapters.AdapterRegPorData;
import com.tcc.lolebn.myregacess.adapters.CustomOnItemSelectedListener;
import com.tcc.lolebn.myregacess.basics.RegIN;
import com.tcc.lolebn.myregacess.basics.Utils;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorMes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListPorMes extends AppCompatActivity{
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_por_mes);

        ListView listViewReg = (ListView) findViewById(R.id.lvPorMes);
        List<RegIN> lregs  = new ArrayList<>();
        // Spinner element
        spinner = (Spinner) findViewById(R.id.spnr_meses_);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(this,this,listViewReg, spinner));

        try {
            lregs = new DownloadRegistroPorMes(this).execute(Utils.getSoMes(new Date())).get();
            spinner.setSelection(Integer.parseInt(Utils.getMes(new Date()))-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        AdapterRegPorData adapter = new AdapterRegPorData(lregs,this);
        listViewReg.setAdapter(adapter);

    }
}
