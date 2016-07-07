package com.iia.cdsm.qcm.webservice;

import android.content.Context;

import com.google.gson.Gson;
import com.iia.cdsm.qcm.Data.ProposalSqlLiteAdapter;
import com.iia.cdsm.qcm.Data.QcmSqlLiteAdapter;
import com.iia.cdsm.qcm.Data.QuestionSqlliteAdapter;
import com.iia.cdsm.qcm.Data.UserSqlLiteAdapter;
import com.iia.cdsm.qcm.Entity.Proposal;
import com.iia.cdsm.qcm.Entity.ProposalUser;
import com.iia.cdsm.qcm.Entity.Qcm;
import com.iia.cdsm.qcm.Entity.Question;
import com.iia.cdsm.qcm.Entity.User;
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

    private static final String BASE_URL = "https://192.168.100.212/qcm2/web/app_dev.php/api";
    private static final String ENTITY = "questions";
    private static final int VERSION = 1;
    private static AsyncHttpClient client = new AsyncHttpClient(true,80,443);


    public static void post(Context context,ArrayList<ProposalUser> proposalUsers,
                            AsyncHttpResponseHandler responseHandler)
            throws JSONException, UnsupportedEncodingException {

        String url = String.format("%s/%s",BASE_URL,ENTITY);
        String jsonParams = ItemToJsonObject(proposalUsers,context);
        StringEntity entity = new StringEntity(jsonParams.toString());
        client.post(context,url,entity,"application/json",responseHandler);
    }


    public static String ItemToJsonObject( ArrayList<ProposalUser> proposalUsers,Context context) throws JSONException {

        ArrayList<ProposalUser> AnswersToSend = new ArrayList<>();
        // Open qcm
        QcmSqlLiteAdapter qcmSqlLiteAdapter = new QcmSqlLiteAdapter(context);
        qcmSqlLiteAdapter.open();
        // Open user
        UserSqlLiteAdapter userSqlLiteAdapter = new UserSqlLiteAdapter(context);
        userSqlLiteAdapter.open();
        // Open question
        QuestionSqlliteAdapter questionSqlliteAdapter = new QuestionSqlliteAdapter(context);
        questionSqlliteAdapter.open();
        // Open proposal
        ProposalSqlLiteAdapter proposalSqlLiteAdapter = new ProposalSqlLiteAdapter(context);
        proposalSqlLiteAdapter.open();

        for(ProposalUser proposalUser : proposalUsers) {
            ProposalUser answer = new ProposalUser();

            // Get user id server
            User user = userSqlLiteAdapter.getUserById((int)proposalUser.getId_user());
            answer.setId_user(user.getIdServer());

            // Get qcm id server
            Qcm qcm = qcmSqlLiteAdapter.getQcmById(proposalUser.getId_qcm());
            answer.setId_qcm(qcm.getIdServer());

            // Get question id server
            Question question = questionSqlliteAdapter.getQuestionById(proposalUser.getId_question());
            answer.setId_question(question.getIdServer());

            // Get proposal id server
            Proposal proposal = proposalSqlLiteAdapter.getProposal(proposalUser.getId_proposal());
            answer.setId_proposal(proposal.getIdServer());


            AnswersToSend.add(answer);

        }
        // Close all
        qcmSqlLiteAdapter.close();
        userSqlLiteAdapter.close();
        questionSqlliteAdapter.close();
        proposalSqlLiteAdapter.close();
        String jsonold = new Gson().toJson(proposalUsers);
        String json = new Gson().toJson(AnswersToSend);


        return json;
    }

}
