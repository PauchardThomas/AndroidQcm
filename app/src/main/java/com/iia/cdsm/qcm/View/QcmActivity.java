package com.iia.cdsm.qcm.View;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.cdsm.qcm.Data.ProposalSqlLiteAdapter;
import com.iia.cdsm.qcm.Data.QcmSqlLiteAdapter;
import com.iia.cdsm.qcm.Data.QuestionSqlliteAdapter;
import com.iia.cdsm.qcm.Entity.Proposal;
import com.iia.cdsm.qcm.Entity.Qcm;
import com.iia.cdsm.qcm.Entity.Question;
import com.iia.cdsm.qcm.R;
import com.iia.cdsm.qcm.webservice.QcmWSAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Thom' on 30/03/2016.
 */
public class QcmActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcm);

        final Qcm qcm = (Qcm) getIntent().getSerializableExtra(Qcm.SERIAL);
        TextView tvQcmLibelle = (TextView) findViewById(R.id.tvQcmLibelle);
        TextView tvDureeValue = (TextView) findViewById(R.id.tvDureeValue);
        Button btBegin = (Button) findViewById(R.id.btCommencer);
        String duree = "Durée non définie";

        tvQcmLibelle.setText(qcm.getLibelle());
        if (qcm.getDuration() != null) {
            duree = new SimpleDateFormat("HH:mm:ss").format(qcm.getDuration());
        }
        tvDureeValue.setText(duree);

        btBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                QcmWSAdapter.get(qcm.getIdServer(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            Qcm qcm = QcmWSAdapter.jsonObjectToItem(response);

                            QcmSqlLiteAdapter qcmSqlLiteAdapter = new QcmSqlLiteAdapter(QcmActivity.this);
                            qcmSqlLiteAdapter.open();
                            qcm.setId(qcmSqlLiteAdapter.getQcm(qcm.getIdServer()).getId());
                            qcmSqlLiteAdapter.close();
                            ProposalSqlLiteAdapter proposalSqlLiteAdapter = new ProposalSqlLiteAdapter(QcmActivity.this);
                            proposalSqlLiteAdapter.open();
                            ArrayList<Question> questions = qcm.getQuestions();

                            QuestionSqlliteAdapter questionSqlliteAdapter = new QuestionSqlliteAdapter(QcmActivity.this);
                            questionSqlliteAdapter.open();
                            for (Question question : questions) {
                                question.setQcm(qcm);
                                question.setId(questionSqlliteAdapter.insert(question));
                                ArrayList<Proposal> proposals = question.getProposals();

                                for (Proposal proposal : proposals) {
                                    proposal.setQuestion(question);
                                    proposalSqlLiteAdapter.insert(proposal);
                                }
                            }
                            proposalSqlLiteAdapter.close();
                            questionSqlliteAdapter.close();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getBaseContext(),"OK", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            }
        });


    }
}
