package com.tcc.lolebn.myregacess.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.tcc.lolebn.myregacess.basics.RegIN;
import com.tcc.lolebn.myregacess.basics.Tag;

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
 * Created by lolebn on 09/06/17.
 */

public class DownloadRegistroPorMesCH extends AsyncTask<String, Void, ArrayList<Tag>> {
    ProgressDialog dialog;
    Context c;
    public static ArrayList<Tag> registrosTags;

    public DownloadRegistroPorMesCH(Context c) {
        this.c = c;
        registrosTags = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(c, "Aguarde", "Baixando dados, Por favor aguarde!");
    }


    @Override
    protected ArrayList<Tag> doInBackground(String... params) {

        try {
            URL url;
            String mes = params[0] + "/2017";

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

                        urlRegs = new URL("http://ufam-automation.net/loislene/getFHMen.php?tag_rfid="+tag.getString("tag_rfid")+"&mes=" + mes);
                        urlConnection = (HttpURLConnection) urlRegs.openConnection();
                        int response = urlConnection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            String jsonReg = readStream(urlConnection.getInputStream());
                            Log.e("jsonReg",jsonReg);
                            if (!jsonReg.equals("-1")) {
                                JSONArray regsL = new JSONArray(jsonReg);
                                JSONObject reg;

                                for (int c = 0; c < regsL.length(); c++) {
                                    reg = new JSONObject(regsL.getString(c));
                                    datas.add(reg.getString("dt"));
                                    regs.add(new RegIN(-2, reg.getString("tag"), reg.getString("nome"), reg.getString("dt"), reg.getInt("status")));
                                }
                                registrosTags.get(i).setFrequencia_mensal(datas);
                                registrosTags.get(i).setRegistros(regs);
                            }
                        }

                    }
                }
            }

        } catch (Exception e) {
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
