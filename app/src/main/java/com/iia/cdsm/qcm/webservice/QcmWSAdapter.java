package com.iia.cdsm.qcm.webservice;

import com.iia.cdsm.qcm.Entity.Qcm;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Thom' on 24/02/2016.
 */
public class QcmWSAdapter {

    private static final String BASE_URL ="http://192.168.1.39/app_dev.php/api";
    private static final String ENTITY = "qcm";
    private static final String ENTITY_LIST ="list";
    private static final int VERSION = 1;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String ID = "id";
    private static final String LIBELLE = "libelle";
    private static final String DATE_PUBLI = "date_Publi";
    private static final String DATE_FIN = "date_Fin";
    private static final String NB_POINTS ="nb_points";
    private static final String CATEGORY_ID ="category_id";


    public static void getAll(AsyncHttpResponseHandler resp) {
        String url = String.format("%s/%s/%s",BASE_URL,ENTITY_LIST,ENTITY);
        client.get(url,resp);
    }

    public static ArrayList<Qcm> jsonArrayToItem(JSONArray json) throws JSONException {

        ArrayList<Qcm> item = new ArrayList<Qcm>();
        Qcm qcm = new Qcm(0,null,null,null,0,null);
        for (int i = 0; i < json.length(); i++) {
            int test = json.length();
            JSONObject jsonobject = json.getJSONObject(i);
            int id = jsonobject.getInt("id");
            String libelle = jsonobject.getString("libelle");
            qcm.setId(id);
            qcm.setLibelle(libelle);
            item.add(qcm);
        }

        return item ;
    }



}
