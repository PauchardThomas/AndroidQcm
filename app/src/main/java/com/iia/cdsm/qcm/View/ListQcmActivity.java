package com.iia.cdsm.qcm.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.iia.cdsm.qcm.Data.QcmSqlLiteAdapter;
import com.iia.cdsm.qcm.Entity.Category;
import com.iia.cdsm.qcm.Entity.Qcm;
import com.iia.cdsm.qcm.Entity.User;
import com.iia.cdsm.qcm.R;
import com.iia.cdsm.qcm.webservice.QcmWSAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Thom' on 27/03/2016.
 */
public class ListQcmActivity extends Activity {

    /**
     * ListView
     */
    ListView list;
    /**
     * Category
     */
    Category category;
    /**
     * User
     */
    User user;

    /**
     * Create activity
     *
     * @param savedInstanceState instanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Set view
         */
        setContentView(R.layout.activity_qcm_list);

        /**
         * get Items from view
         */
        list = (ListView) this.findViewById(R.id.listViewQcm);
        category = (Category) getIntent().getSerializableExtra(Category.SERIAL);
        user = (User) getIntent().getSerializableExtra(User.SERIAL);

        /**
         * Get all Qcm
         */
        QcmWSAdapter.getAll(category.getIdServer(), new JsonHttpResponseHandler() {
            /**
             * If server response
             * @param statusCode response statusCode
             * @param headers response headers
             * @param response response
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                //**************************************//
                //** Convert response to list of qcm **//
                //************************************//
                ArrayList<Qcm> qcms = new ArrayList<>();
                try {
                    qcms = QcmWSAdapter.jsonArrayToItem(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //********************//
                //** Open database **//
                //******************//
                QcmSqlLiteAdapter qcmSqlLiteAdapter =
                        new QcmSqlLiteAdapter(ListQcmActivity.this);
                qcmSqlLiteAdapter.open();

                //**************************//
                //** Insert / update qcm **//
                //************************//
                for (Qcm qcm : qcms) {
                    Qcm isQcmExist = qcmSqlLiteAdapter.getQcm(qcm.getIdServer());
                    if (isQcmExist == null) {
                        qcmSqlLiteAdapter.insert(qcm);
                    } else {
                        qcmSqlLiteAdapter.update(qcm);
                    }
                }

                //******************//
                //** Get all Qcm **//
                //****************//
                ArrayList<Qcm> allqcms = qcmSqlLiteAdapter.getQcms();
                qcmSqlLiteAdapter.close();

                //**************************//
                //** Set qcm to listView **//
                //************************//
                ArrayAdapter<Qcm> adapter = new ArrayAdapter<>(ListQcmActivity.this,
                        android.R.layout.simple_list_item_1, allqcms);
                list.setAdapter(adapter);

            }

            /**
             * If server doesn't response
             * @param statusCode statusCode
             * @param headers header
             * @param throwable error
             * @param errorResponse errorResponse
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


        /**
         * Click on item
         */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Click on list's item
             * @param parent parent
             * @param view view
             * @param position position item
             * @param id id item
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //**********************//
                //** Get qcm clicked **//
                //********************//
                Qcm qcm = (Qcm) list.getItemAtPosition(position);

                //********************************************//
                //** Create intent and start next activity **//
                //******************************************//
                Intent i = new Intent(ListQcmActivity.this, QcmActivity.class);
                i.putExtra(Qcm.SERIAL, qcm);
                i.putExtra(Category.SERIAL, category);
                i.putExtra(User.SERIAL, user);

                startActivity(i);
            }
        });
    }
}
