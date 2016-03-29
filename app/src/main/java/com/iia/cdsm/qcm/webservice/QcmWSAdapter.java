package com.iia.cdsm.qcm.webservice;

import com.iia.cdsm.qcm.Entity.Category;
import com.iia.cdsm.qcm.Entity.Qcm;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Thom' on 24/02/2016.
 */
public class QcmWSAdapter {

    private static final String BASE_URL = "http://192.168.1.39/app_dev.php/api";
    private static final String ENTITY = "qcm";
    private static final String ENTITY_LIST = "lists";
    private static final int VERSION = 1;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String ID = "id";
    private static final String LIBELLE = "libelle";
    private static final String DATE_PUBLI = "date_Publi";
    private static final String DATE_FIN = "date_Fin";
    private static final String NB_POINTS = "nb_points";
    private static final String CATEGORY_ID = "category_id";


    public static void getAll(int id_category,AsyncHttpResponseHandler resp) {
        String url = String.format("%s/%s/%s/%s", BASE_URL, ENTITY_LIST,id_category,ENTITY);
        client.get(url, resp);
    }

    public static ArrayList<Qcm> jsonArrayToItem(JSONArray json) throws JSONException {

        ArrayList<Qcm> item = new ArrayList<Qcm>();

        for (int i = 0; i < json.length(); i++) {
            Qcm qcm = new Qcm();
            Category category = new Category();
            Date duration = null;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            JSONObject jsonobject = json.getJSONObject(i);
            int idServer = jsonobject.getInt("id");
            String libelle = jsonobject.getString("libelle");
            int id_category = jsonobject.getInt("category");

            category.setIdServer(id_category);
            try {
                 duration = formatter.parse(jsonobject.getString("duration"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int nbPoints = jsonobject.getInt("nbPoints");

            qcm.setIdServer(idServer);
            qcm.setLibelle(libelle);
            qcm.setDuration(duration);
            qcm.setNbPoints(nbPoints);
            qcm.setCategory(category);
            item.add(qcm);
        }

        return item;
    }


}
