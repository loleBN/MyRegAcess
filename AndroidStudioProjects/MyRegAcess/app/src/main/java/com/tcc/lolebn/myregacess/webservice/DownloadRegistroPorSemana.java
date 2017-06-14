package com.tcc.lolebn.myregacess.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tcc.lolebn.myregacess.basics.RegIN;
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

public class DownloadRegistroPorSemana extends AsyncTask<Date, Void, ArrayList<RegIN>> {
    ProgressDialog dialog;
    Context c;
    public static ArrayList<RegIN> registrosArray;


    public DownloadRegistroPorSemana(Context c) {
        this.c = c;
        registrosArray = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(c, "Aguarde", "Baixando dados, Por favor aguarde!");
    }


    @Override
    protected ArrayList<RegIN> doInBackground(Date... params) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(params[0]);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        Log.e("day",""+day);
        cal.add(Calendar.DATE, -day);
        Log.e("date inicio", Utils.convertDateToString(cal.getTime()));
        try {
            URL url;
            String date = "";
            HttpURLConnection urlConnection = null;
            for(int it=0;it<7;it++){
                cal.add(Calendar.DATE, +1);
                date = Utils.convertDateToString(cal.getTime());
                Log.e("cal date",date);

                url = new URL("http://ufam-automation.net/loislene/getRegistroByData.php?date=" + date);
                urlConnection = (HttpURLConnection) url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String json = readStream(urlConnection.getInputStream());
                    if(!json.equals("-1")) {
                        JSONArray regL = new JSONArray(json);
                        JSONObject o_reg;

                        for (int i = 0; i < regL.length(); i++) {
                            o_reg = new JSONObject(regL.getString(i));
                            registrosArray.add(new RegIN(i, o_reg.getString("tag_rfid"), o_reg.getString("nome"), o_reg.getString("date_time"), o_reg.getInt("status")));

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Erro mesmo", "Erro - " + e.getMessage());
        }
        dialog.dismiss();
        return registrosArray;
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
