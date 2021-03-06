package com.tcc.lolebn.myregacess.telas;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.tcc.lolebn.myregacess.R;
import com.tcc.lolebn.myregacess.adapters.AdapterRegCHPorTag;
import com.tcc.lolebn.myregacess.adapters.AdapterRegPorData;
import com.tcc.lolebn.myregacess.basics.RegIN;
import com.tcc.lolebn.myregacess.basics.Tag;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorSemana;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorSemanaCH;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TagsCHSemanal extends AppCompatActivity {

    private Calendar dateTime = Calendar.getInstance();
    private TextView cDate;
    private List<Tag> ltags;
    ListView listViewTag;
    private Activity act;
    private Context cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_chsemanal);

        act = this;
        cont = this;
        listViewTag = (ListView) findViewById(R.id.lvTagCH);
        cDate = (TextView) findViewById(R.id.txt_porsemana_ch);
        ltags  = new ArrayList<>();
        cDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                updateDate();
            }
        });


        try {
            ltags = new DownloadRegistroPorSemanaCH(this).execute(new Date()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (ltags != null){
            AdapterRegCHPorTag adapter = new AdapterRegCHPorTag(ltags,this);
            listViewTag.setAdapter(adapter);
        } else {
            Log.e("NULO!!!","Tá nulo né;..");
        }
    }
    public void updateDate(){
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            monthOfYear = monthOfYear +1;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date="";
            if(monthOfYear<10)
                date = dayOfMonth +"/0"+monthOfYear+"/"+year;
            else
                date = dayOfMonth +"/"+monthOfYear+"/"+year;
            try {
                Date dateOf_ = sdf.parse(date);
                ltags = new DownloadRegistroPorSemanaCH(cont).execute(dateOf_).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            AdapterRegCHPorTag adapter = new AdapterRegCHPorTag(ltags,act);
            listViewTag.setAdapter(adapter);
            if(ltags.size()==0)
                Snackbar.make(view, "Nenhum Registro Cadastrado", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            else
                Snackbar.make(view, "Tem Registro Cadastrado", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        }
    };
}
