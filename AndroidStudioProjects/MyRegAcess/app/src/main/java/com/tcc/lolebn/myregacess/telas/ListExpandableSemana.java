package com.tcc.lolebn.myregacess.telas;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.tcc.lolebn.myregacess.R;
import com.tcc.lolebn.myregacess.adapters.CustomExpandableListAdapter;
import com.tcc.lolebn.myregacess.adapters.ExpandableListDataPump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListExpandableSemana extends AppCompatActivity {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_expandable_semana);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
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

    /*public void setGroupData() {
        String text="";
        for (int i=0;i<ltags.size();i++) {
            text=ltags.get(i).getNome()+" CH: "+ltags.get(i).getFrequencia_semanal()+"h";
            groupItem.add(text);

            ArrayList<String> child = new ArrayList<String>();
            for(int j=0; j<ltags.get(i).getRegistros().size();j++){
                if(ltags.get(i).getRegistros().get(j).getStatus() == 1)
                    child.add(Utils.getSoData(ltags.get(i).getRegistros().get(j).getData_hora()) + "      entrada: " +Utils.getSoHorario(ltags.get(i).getRegistros().get(j).getData_hora())+" ");
                else if(ltags.get(i).getRegistros().get(j).getStatus() == 0)
                    child.add(Utils.getSoData(ltags.get(i).getRegistros().get(j).getData_hora()) + "      saida: " +Utils.getSoHorario(ltags.get(i).getRegistros().get(j).getData_hora())+"      ");
            }
            childItem.add(child);
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
        }
    };*/
}
