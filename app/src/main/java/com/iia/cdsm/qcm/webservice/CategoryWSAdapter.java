package com.iia.cdsm.qcm.webservice;

import android.content.Context;

import com.iia.cdsm.qcm.Entity.Category;
import com.iia.cdsm.qcm.Entity.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Thom' on 22/02/2016.
 */
public class CategoryWSAdapter {
    /**
     * Category API BASE URL
     */
    private static final String BASE_URL = "http://192.168.1.39/app_dev.php/api";
    /**
     * Entity API URL
     */
    private static final String ENTITY = "categories";
    /**
     * AsyncHttpClient
     */
    private static AsyncHttpClient client = new AsyncHttpClient();
    /**
     * Category ID
     */
    private static final String ID = "id";
    /**
     * Category name
     */
    private static final String LIBELLE = "libelle";
    /**
     * Category ID server
     */
    private static final String ID_SERVER = "idServer";

    /**
     * Get all category for one User from Webservice
     *
     * @param id_user User id
     * @param resp    webservice response
     */
    public static void getAll(int id_user, AsyncHttpResponseHandler resp) {
        String url = String.format("%s/%s/%s", BASE_URL, ENTITY, id_user);
        client.get(url, resp);
        client.setTimeout(3000);
    }


    /**
     * Convert json to list of categories
     *
     * @param json json
     * @return List of categories
     * @throws JSONException
     */
    public static ArrayList<Category> jsonArrayToItem(JSONArray json) throws JSONException {

        ArrayList<Category> item = new ArrayList<Category>();

        for (int i = 0; i < json.length(); i++) {

            Category cate = new Category();

            JSONObject jsonobject = json.getJSONObject(i);

            int idServer = jsonobject.getInt(ID);
            String libelle = jsonobject.getString(LIBELLE);

            cate.setIdServer(idServer);
            cate.setLibelle(libelle);

            item.add(cate);
        }

        return item;
    }


}
