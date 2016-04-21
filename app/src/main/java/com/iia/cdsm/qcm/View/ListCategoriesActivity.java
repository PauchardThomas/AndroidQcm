package com.iia.cdsm.qcm.View;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.iia.cdsm.qcm.Data.AccessUserCategorySqLiteAdapter;
import com.iia.cdsm.qcm.Data.CategorySqlLiteAdapter;
import com.iia.cdsm.qcm.Data.QcmSqlLiteAdapter;
import com.iia.cdsm.qcm.Entity.Category;
import com.iia.cdsm.qcm.Entity.Qcm;
import com.iia.cdsm.qcm.Entity.User;
import com.iia.cdsm.qcm.R;
import com.iia.cdsm.qcm.webservice.CategoryWSAdapter;
import com.iia.cdsm.qcm.webservice.QcmWSAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Thom' on 22/03/2016.
 */
public class ListCategoriesActivity extends Activity {

    ListView list;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        list = (ListView) this.findViewById(R.id.listViewCategory);
        user = (User) getIntent().getSerializableExtra(User.SERIAL);

        CategoryWSAdapter.getAll(user.getIdServer(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    ArrayList<Category> categories = CategoryWSAdapter.jsonArrayToItem(response);

                    CategorySqlLiteAdapter categorySqlLiteAdapter =
                            new CategorySqlLiteAdapter(ListCategoriesActivity.this);
                    categorySqlLiteAdapter.open();

                    AccessUserCategorySqLiteAdapter accessUserCategorySqLiteAdapter =
                            new AccessUserCategorySqLiteAdapter(ListCategoriesActivity.this);
                    accessUserCategorySqLiteAdapter.open();

                    for (Category category : categories) {
                        Category isCatExist = categorySqlLiteAdapter.getCategory(category.getIdServer());
                        if (isCatExist == null) {
                            category.setId(categorySqlLiteAdapter.insert(category));
                            accessUserCategorySqLiteAdapter.insert(user, category);

                        } else {
                            categorySqlLiteAdapter.update(category);
                        }
                    }
                    categorySqlLiteAdapter.close();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CategorySqlLiteAdapter categorySqlLiteAdapter =
                        new CategorySqlLiteAdapter(ListCategoriesActivity.this);
                categorySqlLiteAdapter.open();
                ArrayList<Category> categories = categorySqlLiteAdapter.getCategories();
                categorySqlLiteAdapter.close();

                List<Category> item = new ArrayList<>();
                for (Category category : categories) {
                    item.add(category);
                }
                ArrayAdapter<Category> adapter = new ArrayAdapter<>(ListCategoriesActivity.this,
                        android.R.layout.simple_list_item_1, item);
                list.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Category cat = (Category) list.getItemAtPosition(position);
                int idServer = cat.getIdServer();
                String libelle = cat.getLibelle();
                //Toast.makeText(getBaseContext(), "idServer :" + idServer + " Libelle : " + libelle, Toast.LENGTH_SHORT).show();


                Intent i = new Intent(ListCategoriesActivity.this, ListQcmActivity.class);
                i.putExtra(Category.SERIAL, cat);
                i.putExtra(User.SERIAL, user);
                startActivity(i);

            }
        });

    }


}
