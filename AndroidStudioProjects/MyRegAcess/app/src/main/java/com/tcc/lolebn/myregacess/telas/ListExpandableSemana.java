package com.tcc.lolebn.myregacess.telas;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.lolebn.myregacess.R;
import com.tcc.lolebn.myregacess.adapters.AdapterRegPorData;
import com.tcc.lolebn.myregacess.adapters.CustomExpandableListAdapter;
import com.tcc.lolebn.myregacess.adapters.ExpandableListDataPump;
import com.tcc.lolebn.myregacess.basics.Utils;
import com.tcc.lolebn.myregacess.basics.Tag;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorSemana;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorSemanaCH;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListExpandableSemana extends AppCompatActivity {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    private List<Tag> ltags;
    private Calendar dateTime = Calendar.getInstance();
    private TextView cDate;
    private Activity act;
    private Context cont;
    private Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_expandable_semana);

        act = this;
        cont = this;
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        cDate = (TextView) findViewById(R.id.txt_select_semana);
        cDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                updateDate();
            }
        });

        ltags  = new ArrayList<Tag>();

        try {
            ltags = new DownloadRegistroPorSemanaCH(this).execute(new Date()).get();
            cal.setTime(new Date());
            int day = cal.get(Calendar.DAY_OF_WEEK);
            cal.add(Calendar.DATE, -day);

            cal.add(Calendar.DATE, +1);
            String d1=Utils.convertDateToString(cal.getTime());
            cal.add(Calendar.DATE, +6);
            String d7 =Utils.convertDateToString(cal.getTime());
            cDate.setText(d1+" - "+d7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (ltags != null){
            expandableListDetail = setData();
            expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
            expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);
        } else {
            Log.e("NULO!!!","Tá nulo né;..");
        }

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
               /* Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
               /* Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();*/
                return false;
            }
        });
    }

    public HashMap<String, List<String>> setData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        String groupItem="";
        List<String> child;
        for (int i=0;i<ltags.size();i++) {
            groupItem=ltags.get(i).getNome()+"              CH: "+ltags.get(i).getFrequencia_semanal()+"h";

            child = new ArrayList<String>();
            for(int j=0; j<ltags.get(i).getRegistros().size();j++){
                if(ltags.get(i).getRegistros().get(j).getStatus() == 1)
                    child.add(Utils.getSoData(ltags.get(i).getRegistros().get(j).getData_hora()) + "      entrada: " +Utils.getSoHorario(ltags.get(i).getRegistros().get(j).getData_hora())+" ");
                else if(ltags.get(i).getRegistros().get(j).getStatus() == 0)
                    child.add(Utils.getSoData(ltags.get(i).getRegistros().get(j).getData_hora()) + "      saida: " +Utils.getSoHorario(ltags.get(i).getRegistros().get(j).getData_hora())+"      ");
            }
            expandableListDetail.put(groupItem, child);
        }
        return expandableListDetail;
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
                cal.setTime(dateOf_);
                int day = cal.get(Calendar.DAY_OF_WEEK);
                cal.add(Calendar.DATE, -day);

                cal.add(Calendar.DATE, +1);
                String d1=Utils.convertDateToString(cal.getTime());
                cal.add(Calendar.DATE, +6);
                String d7 =Utils.convertDateToString(cal.getTime());
                cDate.setText(d1+" - "+d7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (ltags != null){
                expandableListDetail = setData();
                expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                expandableListAdapter = new CustomExpandableListAdapter(act, expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);
            } else {
                Log.e("NULO!!!","Tá nulo né;..");
            }
        }
    };
}
