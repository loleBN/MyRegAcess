package com.tcc.lolebn.myregacess.adapters;

/**
 * Created by lolebn on 12/06/17.
 */


import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tcc.lolebn.myregacess.R;
import com.tcc.lolebn.myregacess.adapters.AdapterRegPorData;
import com.tcc.lolebn.myregacess.basics.RegIN;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorMes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

    private ListView lView;
    private Spinner spinner;
    private Context context;
    private Activity acty;

    public CustomOnItemSelectedListener(Context ctx, Activity a, ListView list, Spinner s){
        this.lView = list;
        this.spinner = s;
        this.context = ctx;
        this.acty = a;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {


        List<RegIN> lregs  = new ArrayList<>();
        try {
            if(pos<10)
                lregs = new DownloadRegistroPorMes(context).execute("0"+(pos+1)).get();
            else
                lregs = new DownloadRegistroPorMes(context).execute(""+(pos+1)).get();
            spinner.setSelection(pos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        AdapterRegPorData adapter = new AdapterRegPorData(lregs,acty);
        lView.setAdapter(adapter);
        if (lregs.size()==0){
            Snackbar.make(view, "Nenhum Registro Realizado", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}