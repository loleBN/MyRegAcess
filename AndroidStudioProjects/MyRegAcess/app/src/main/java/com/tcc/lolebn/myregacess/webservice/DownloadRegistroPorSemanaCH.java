package com.tcc.lolebn.myregacess.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.tcc.lolebn.myregacess.basics.RegIN;
import com.tcc.lolebn.myregacess.basics.Tag;
import com.tcc.lolebn.myregacess.basics.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lolebn on 08/06/17.
 */

public class DownloadRegistroPorSemanaCH extends AsyncTask<Date, Void, ArrayList<Tag>> {
    ProgressDialog dialog;
    Context c;
    //public static ArrayList<RegIN> registrosArray;
    public static ArrayList<Tag> registrosTags;


    public DownloadRegistroPorSemanaCH(Context c) {
        this.c = c;
        registrosTags = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(c, "Aguarde", "Baixando dados, Por favor aguarde!");
    }


    @Override
    protected ArrayList<Tag> doInBackground(Date... params) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(params[0]);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, -day);

        cal.add(Calendar.DATE, +1);
        String d1=Utils.convertDateToString(cal.getTime());
        cal.add(Calendar.DATE, +1);
        String d2=Utils.convertDateToString(cal.getTime());
        cal.add(Calendar.DATE, +1);
        String d3=Utils.convertDateToString(cal.getTime());
        cal.add(Calendar.DATE, +1);
        String d4=Utils.convertDateToString(cal.getTime());
        cal.add(Calendar.DATE, +1);
        String d5=Utils.convertDateToString(cal.getTime());
        cal.add(Calendar.DATE, +1);
        String d6=Utils.convertDateToString(cal.getTime());
        cal.add(Calendar.DATE, +1);
        String d7 =Utils.convertDateToString(cal.getTime());




        try {
            URL urlRegs = new URL("http://ufam-automation.net/loislene/getTags.php");
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) urlRegs.openConnection();
            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String json = readStream(urlConnection.getInputStream());
                if(!json.equals("-1")) {
                    JSONArray tagsL = new JSONArray(json);
                    JSONObject tag;

                    for (int i = 0; i < tagsL.length(); i++) {

                        ArrayList<RegIN> regs = new ArrayList<RegIN>();
                        ArrayList<String> datas = new ArrayList<String>();

                        tag = new JSONObject(tagsL.getString(i));
                        registrosTags.add(new Tag(tag.getString("tag_rfid"), tag.getString("nome")));

                        urlRegs = new URL("http://ufam-automation.net/loislene/getFHSem.php?tag_rfid=" + tag.getString("tag_rfid") + "&d1=" + d1 + "&d2=" + d2 + "&d3=" + d3 + "&d4=" + d4 + "&d5=" + d5 + "&d6=" + d6 + "&d7=" + d7);
                        urlConnection = (HttpURLConnection) urlRegs.openConnection();
                        int response = urlConnection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            String jsonReg = readStream(urlConnection.getInputStream());
                            if (!jsonReg.equals("-1")) {
                                JSONArray regsL = new JSONArray(jsonReg);
                                JSONObject reg;

                                for (int c = 0; c < regsL.length(); c++) {
                                    reg = new JSONObject(regsL.getString(c));
                                    datas.add(reg.getString("dt"));
                                    regs.add(new RegIN(-2, reg.getString("tag"), reg.getString("nome"), reg.getString("dt"), reg.getInt("status")));
                                }
                                registrosTags.get(i).setFrequencia_semanal(datas);
                                registrosTags.get(i).setRegistros(regs);
                            }
                        }
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
            Log.e("Erro mesmo", "Erro - " + e.getMessage());

        }

        dialog.dismiss();
        return registrosTags;
    }


    private String readStream(InputStream in) throws IOException {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

}
