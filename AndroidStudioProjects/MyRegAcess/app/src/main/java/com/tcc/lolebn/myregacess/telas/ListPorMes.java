package com.tcc.lolebn.myregacess.telas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tcc.lolebn.myregacess.R;
import com.tcc.lolebn.myregacess.adapters.AdapterRegPorData;
import com.tcc.lolebn.myregacess.basics.RegIN;
import com.tcc.lolebn.myregacess.basics.Utils;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorData;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorMes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListPorMes extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_por_mes);

        // Spinner element
        spinner = (Spinner) findViewById(R.id.spnr_meses_);
        ListView listViewReg = (ListView) findViewById(R.id.lvPorMes);
        List<RegIN> lregs  = new ArrayList<>();
        try {
            lregs = new DownloadRegistroPorMes(this).execute(Utils.getSoMes(new Date())).get();
            spinner.setSelection(Integer.parseInt(Utils.getSoMes(new Date()))-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        AdapterRegPorData adapter = new AdapterRegPorData(lregs,this);
        listViewReg.setAdapter(adapter);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
