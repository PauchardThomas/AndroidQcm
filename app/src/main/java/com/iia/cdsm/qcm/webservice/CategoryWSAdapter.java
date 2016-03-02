package com.iia.cdsm.qcm.webservice;

import com.iia.cdsm.qcm.Entity.Category;
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

    private static final String BASE_URL ="http://192.168.1.39/app_dev.php/api";
    private static final String ENTITY = "categories";
    private static final int VERSION = 1;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String ID = "id";
    private static final String LIBELLE = "libelle";

    public static void getAll(AsyncHttpResponseHandler resp) {
        String url = String.format("%s/%s",BASE_URL,ENTITY);
        client.get(url,resp);
    }

    public static ArrayList<Category> jsonArrayToItem(JSONArray json) throws JSONException {

        ArrayList<Category> item = new ArrayList<Category>();
        Category cate = new Category(0,null);
        for (int i = 0; i < json.length(); i++) {
           int test = json.length();
            JSONObject jsonobject = json.getJSONObject(i);
            int id = jsonobject.getInt("id");
            String libelle = jsonobject.getString("libelle");
            cate.setId(id);
            cate.setLibelle(libelle);
            item.add(cate);
        }

        return item ;
    }

}
