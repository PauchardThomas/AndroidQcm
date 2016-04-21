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

    ListView list;
    Category category;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcm_list);

        /**
         * get Items from view
         */
        list = (ListView) this.findViewById(R.id.listViewQcm);
        category = (Category) getIntent().getSerializableExtra(Category.SERIAL);
        user = (User) getIntent().getSerializableExtra(User.SERIAL);

        /**
         * Get all Qcms
         */
        QcmWSAdapter.getAll(category.getIdServer(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<Qcm> qcms = new ArrayList<Qcm>();
                try {
                    qcms = QcmWSAdapter.jsonArrayToItem(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                QcmSqlLiteAdapter qcmSqlLiteAdapter =
                        new QcmSqlLiteAdapter(ListQcmActivity.this);
                qcmSqlLiteAdapter.open();
                for (Qcm qcm : qcms) {
                    Qcm isQcmExist = qcmSqlLiteAdapter.getQcm(qcm.getIdServer());
                    if (isQcmExist == null) {
                        qcmSqlLiteAdapter.insert(qcm);
                    } else {
                        qcmSqlLiteAdapter.update(qcm);
                    }
                }

                ArrayList<Qcm> allqcms = qcmSqlLiteAdapter.getQcms();
                qcmSqlLiteAdapter.close();
                ArrayAdapter<Qcm> adapter = new ArrayAdapter<>(ListQcmActivity.this,
                        android.R.layout.simple_list_item_1, allqcms);
                list.setAdapter(adapter);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


        /**
         * Click on item
         */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Qcm qcm = (Qcm) list.getItemAtPosition(position);
                int idServer = qcm.getIdServer();
                String libelle = qcm.getLibelle();

               // Toast.makeText(getBaseContext(), "idServer :" + idServer + " Libelle : " + libelle, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(ListQcmActivity.this, QcmActivity.class);
                i.putExtra(Qcm.SERIAL, qcm);
                i.putExtra(Category.SERIAL, category);
                i.putExtra(User.SERIAL, user);

                startActivity(i);
            }
        });
    }
}
