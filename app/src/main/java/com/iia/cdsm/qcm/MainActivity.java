package com.iia.cdsm.qcm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iia.cdsm.qcm.Entity.Category;
import com.iia.cdsm.qcm.webservice.CategoryWSAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Test");

        CategoryWSAdapter.getAll(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    ArrayList<Category> cate = CategoryWSAdapter.jsonArrayToItem(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
