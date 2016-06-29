package com.iia.cdsm.qcm.webservice;

import android.content.Context;

import com.google.gson.Gson;
import com.iia.cdsm.qcm.Entity.Proposal;
import com.iia.cdsm.qcm.Entity.ProposalUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Thom' on 19/04/2016.
 */
public class QuestionWSAdapter {

    private static final String BASE_URL = "http://192.168.100.212/qcm2/web/app_dev.php/api";
    private static final String ENTITY = "question";
    private static final int VERSION = 1;
    private static AsyncHttpClient client = new AsyncHttpClient(true,80,443);


    public static void post(Context context,ArrayList<ProposalUser> proposalUsers,
                            AsyncHttpResponseHandler responseHandler)
            throws JSONException, UnsupportedEncodingException {

        String url = String.format("%s/%s/",BASE_URL,ENTITY);
        String jsonParams = ItemToJsonObject(proposalUsers);
        StringEntity entity = new StringEntity(jsonParams.toString());
        client.post(context,url,entity,"application/json",responseHandler);
    }


    public static String ItemToJsonObject( ArrayList<ProposalUser> proposalUsers) throws JSONException {


        String json = new Gson().toJson(proposalUsers);

        return json;
    }

}
