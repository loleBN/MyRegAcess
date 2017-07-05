package com.tcc.lolebn.myregacess.telas;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.tcc.lolebn.myregacess.R;
import com.tcc.lolebn.myregacess.adapters.AdapterRegPorData;
import com.tcc.lolebn.myregacess.basics.RegIN;
import com.tcc.lolebn.myregacess.basics.Utils;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorData;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorSemana;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListPorData extends AppCompatActivity {

    private static final String TAG = "ListPorData";
    private Calendar dateTime = Calendar.getInstance();
    private TextView currentDate;
    private List<RegIN> lregs;
    ListView listViewReg;
    private Activity act;
    private Context cont;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpordata);

        lregs  = new ArrayList<>();
        act = this;
        cont = this;
        listViewReg = (ListView) findViewById(R.id.lvPorData);
        currentDate = (TextView) findViewById(R.id.txt_pordata_data);
        currentDate.setText(Utils.convertDateToString(new Date()));
        currentDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                updateDate();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month +1;
                String date="";
                if(day<10) {
                    date = date+"0" + day;
                }else {
                    date = date+"" + day;
                }
                if(month<10) {
                    date = date+"/0" + month + "/" + year;
                }else {
                    date = date+"/" + month + "/" + year;
                }
                Log.d(TAG,"onDateSet: dd/mm/yyyy: "+ date);
                currentDate.setText(date);
                try {
                    lregs = new DownloadRegistroPorData(cont).execute(date).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                AdapterRegPorData adapter = new AdapterRegPorData(lregs,act);
                listViewReg.setAdapter(adapter);
            }
        };


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
    public void updateDate(){
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            monthOfYear = monthOfYear +1;
            Log.d(TAG,"onDateSet: dd/mm/yyyy: "+ dayOfMonth +"/"+monthOfYear+"/"+year);
            String date = "";
            if(dayOfMonth<10) {
                date = date+"0" + dayOfMonth;
            }else {
                date = date+"" + dayOfMonth;
            }
            if(monthOfYear<10) {
                date = date+"/0" + monthOfYear + "/" + year;
            }else {
                date = date+"/" + monthOfYear + "/" + year;
            }
            currentDate.setText(date);
            try {
                lregs = new DownloadRegistroPorData(cont).execute(date).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            AdapterRegPorData adapter = new AdapterRegPorData(lregs,act);
            listViewReg.setAdapter(adapter);
            if(lregs.size()==0)
                Snackbar.make(view, "Nenhum Registro Cadastrado", Snackbar.LENGTH_LONG)
                   .setAction("Action", null).show();
        }
    };
}
