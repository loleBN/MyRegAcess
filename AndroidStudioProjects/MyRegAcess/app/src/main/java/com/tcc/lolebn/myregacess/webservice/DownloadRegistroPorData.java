package com.tcc.lolebn.myregacess.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tcc.lolebn.myregacess.basics.RegIN;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lolebn on 08/06/17.
 */

public class DownloadRegistroPorData extends AsyncTask<String, Void, ArrayList<RegIN>> {
    ProgressDialog dialog;
    Context c;
    public static ArrayList<RegIN> registrosArray;

    public DownloadRegistroPorData(Context c) {
        this.c = c;
        registrosArray = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(c, "Aguarde", "Baixando dados, Por favor aguarde!");
    }


    @Override
    protected ArrayList<RegIN> doInBackground(String... params) {
        String server_response;
        try {
            URL url;
            String date = params[0];
            HttpURLConnection urlConnection = null;
            url = new URL("http://ufam-automation.net/loislene/getRegistroByData.php?date=" + date);
            urlConnection = (HttpURLConnection) url.openConnection();

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String json = readStream(urlConnection.getInputStream());

                JSONArray regL = new JSONArray(json);
                JSONObject o_reg;

                for (int i = 0; i < regL.length(); i++) {
                    o_reg = new JSONObject(regL.getString(i));
                    registrosArray.add(new RegIN(i,o_reg.getString("tag_rfid"), o_reg.getString("nome"),o_reg.getString("date_time"), o_reg.getInt("status")));

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