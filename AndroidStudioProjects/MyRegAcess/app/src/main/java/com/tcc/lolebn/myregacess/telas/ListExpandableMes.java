package com.tcc.lolebn.myregacess.telas;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tcc.lolebn.myregacess.R;
import com.tcc.lolebn.myregacess.adapters.CustomExpandableListAdapter;
import com.tcc.lolebn.myregacess.basics.Tag;
import com.tcc.lolebn.myregacess.basics.Utils;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorMes;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorMesCH;
import com.tcc.lolebn.myregacess.webservice.DownloadRegistroPorSemanaCH;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListExpandableMes extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    private List<Tag> ltags;
    private Activity act;
    private Context cont;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_expandable_mes);

        act = this;
        cont = this;
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListViewMes);

        ltags  = new ArrayList<Tag>();
        spinner = (Spinner) findViewById(R.id.spnr_meses_expan);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                try {
                    if(pos<10)
                        ltags = new DownloadRegistroPorMesCH(cont).execute("0"+(pos+1)).get();
                    else
                        ltags = new DownloadRegistroPorMesCH(cont).execute(""+(pos+1)).get();
                    spinner.setSelection(pos);
                    if (ltags != null){
                        expandableListDetail = setData();
                        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                        expandableListAdapter = new CustomExpandableListAdapter(cont, expandableListTitle, expandableListDetail);
                        expandableListView.setAdapter(expandableListAdapter);
                    } else {
                        Log.e("NULO!!!","Tá nulo né;..");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        try {
            ltags = new DownloadRegistroPorMesCH(this).execute(Utils.getSoMes(new Date())).get();
            Log.e("tag0 freq",ltags.get(0).getFrequencia_mensal()+"");
            Log.e("tag0 reg size",ltags.get(0).getRegistros().size()+"");
            spinner.setSelection(Integer.parseInt(Utils.getMes(new Date()))-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        expandableListDetail = setData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);


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
            groupItem=ltags.get(i).getNome()+"              CH: "+ltags.get(i).getFrequencia_mensal()+"h";

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

}
